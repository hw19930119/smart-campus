package com.sunsharing.smartcampus.web.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunsharing.share.scurd.context.ScurdContext;
import com.sunsharing.share.webex.annotation.ShareRest;
import com.sunsharing.smartcampus.configuration.properties.IeduServiceConfig;
import com.sunsharing.smartcampus.constant.constants.Constants;
import com.sunsharing.smartcampus.constant.enums.UserTypeEnum;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.service.biz.UserService;
import com.sunsharing.smartcampus.utils.IeduUtil;
import com.sunsharing.smartcampus.utils.RedisCacheUtil;
import com.sunsharing.smartcampus.utils.RequestHelper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@ShareRest
public class IeduUserController {
    @Autowired
    IeduServiceConfig iEduServiceConfig;

    @Value("${dev.frontPath}")
    String frontPath;
    @Autowired
    UserService userService;

    public static boolean isNeedLoginSSO(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        boolean isNeedLoginSSO = false;
        String iEduCookieValue = RequestHelper.getValueFromRequestParamOrCookie(Constants.iEduCookieName, httpServletRequest, httpServletResponse);
        String jsonStr = (String) RedisCacheUtil.getCache(Constants.iEduCacheKeyPrefix + ":" + iEduCookieValue);
        if (jsonStr == null) {
            isNeedLoginSSO = true;
        }
        renewalSessionCache(isNeedLoginSSO, jsonStr, httpServletRequest, httpServletResponse);//会话缓存续期
        return isNeedLoginSSO;
    }


    public static void renewalSessionCache(boolean isNeedLoginSSO, String jsonStr, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (isNeedLoginSSO == true) {
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String idNum = jsonObject.getString("idNum");

        String iEduCookieValue = RequestHelper.getValueFromRequestParamOrCookie(Constants.iEduCookieName, httpServletRequest, httpServletResponse);

        //sessionId对应idNum(方便排查问题)
        RedisCacheUtil.expireByKeyAndSecond(Constants.iEduCacheKeyToIdNum + ":" + iEduCookieValue,
            2400);
        //idNum对应IeduUserVo（方便排查问题）
        RedisCacheUtil.expireByKeyAndSecond(Constants.iEduIdNumCacheKeyPrefix + ":" + idNum,
            2400);
        //sessionId对应IeduUserVo
        RedisCacheUtil.expireByKeyAndSecond(Constants.iEduCacheKeyPrefix + ":" + iEduCookieValue,
            2400);

    }

    public static IeduUserVo load(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (httpServletRequest == null) {
            httpServletRequest = ScurdContext.getRequest();
        }
        if (httpServletRequest == null) {
            httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }
        IeduUserVo iEduUserVo = null;
        String iEduCookieValue = RequestHelper.getValueFromRequestParamOrCookie(Constants.iEduCookieName, httpServletRequest, httpServletResponse);
        String jsonStr = (String) RedisCacheUtil.getCache(Constants.iEduCacheKeyPrefix + ":" + iEduCookieValue);
        if (StringUtils.isNotBlank(jsonStr)) {
            iEduUserVo = JSONObject.parseObject(jsonStr, IeduUserVo.class);
        }
        return iEduUserVo;
    }

    //1.获取用户会话信息
    @GetMapping("/iEduUser/getIeduUserLoginCache")
    public Object getIeduUserLoginCache(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("请求 /iEduUser/getIeduUserLoginCache");
        String iEduCookieValue = RequestHelper.getValueFromRequestParamOrCookie(Constants.iEduCookieName, httpServletRequest, httpServletResponse);
        String jsonStr = (String) RedisCacheUtil.getCache(Constants.iEduCacheKeyPrefix + ":" + iEduCookieValue);
        JSONObject result = new JSONObject();
        if (StringUtils.isNotBlank(jsonStr)) {
            result = JSON.parseObject(jsonStr);
        }
        return result;
    }

    @GetMapping("/iEduUser/getAiXiaMenConfig")
    public Object getAiXiaMenConfig(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("请求 /iEduUser/getAiXiaMenConfig");
        String idsUrlHttp = "${idsUrlHttpPrefix}/custom/xiamen/login_xm.jsp?loginType=Enterprise&returnUrl=${idsUrlHttpPrefix}/custom/xiamen/autoSubmitForProxyLogin.jsp?coAppName=${appName}";
        idsUrlHttp = idsUrlHttp.replace("${idsUrlHttpPrefix}", iEduServiceConfig.getIdsUrlHttpPrefix())
            .replace("${appName}", iEduServiceConfig.getAppId());
        JSONObject result = new JSONObject();
        result.put("idsUrl", iEduServiceConfig.getIdsUrl());
        result.put("idsUrlHttpPrefix", iEduServiceConfig.getIdsUrlHttpPrefix());
        result.put("appId", iEduServiceConfig.getAppId());
        result.put("appSecret", iEduServiceConfig.getAppSecret());
        result.put("aesKey", iEduServiceConfig.getAesKey());
        result.put("aesIv", iEduServiceConfig.getAesIv());
        result.put("idsUrlHttp", idsUrlHttp);
        return result;
    }


    public static String formatQueryParamValue(String paramValue) {
        if (paramValue == null) {
            paramValue = "";
        }
        try {
            paramValue = URLEncoder.encode(paramValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("IeduUserController.formatQueryParamValue", e);
        }
        return paramValue;
    }

    public static JSONObject parseReturnJson(JSONObject jsonObject) {
        JSONObject needJsonObject = new JSONObject();
        JSONArray groupJsonArray = jsonObject.getJSONObject("data").getJSONArray("enterprises");
        if (null != groupJsonArray && groupJsonArray.size() > 0) {
            JSONObject enterpriseJsonObject = groupJsonArray.getJSONObject(0);
            needJsonObject.put("name", enterpriseJsonObject.getString("enterpriseName"));
            JSONObject customProperties = enterpriseJsonObject.getJSONObject("customProperties");
            if (Objects.nonNull(customProperties)) {
                needJsonObject.put("uscc", customProperties.getString("IDSEXT_UNIFIEDCREDITCODE"));
            }
        }
        return needJsonObject;
    }


    public String getBasePath(HttpServletRequest httpServletRequest) {
        String contextPath = httpServletRequest.getContextPath();
        String basePath = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort()
            + contextPath;
        return basePath;
    }


    //上传uscc和name,前端回写cookie和跳转路径
    @PostMapping("/iEduUser/realLogin")
    public JSONObject realLogin(HttpServletRequest request, HttpServletResponse response,
                                @RequestBody JSONObject jsonObject) throws Exception {
        String code = jsonObject.getString("code");

        String idNum = jsonObject.getString("idNum");
        String name = jsonObject.getString("name");
        String mobileNum = jsonObject.getString("mobileNum");
        String userId = jsonObject.getString("userId");

        if (StringUtils.isNotBlank(code)) {
            //走正式
            JSONObject jsonObjectFromIedu = IeduUtil.getUserInfo(code, iEduServiceConfig);
            idNum = jsonObjectFromIedu.getString("cardId");
            name = jsonObjectFromIedu.getString("name");
            mobileNum = jsonObjectFromIedu.getString("mobile");
        }
        String sessionId = com.sunsharing.component.utils.base.StringUtils.generateUUID();
        IeduUserVo iEduUserVo = setIeduSessionCache(sessionId, idNum, name, mobileNum, userId, request);
        JSONObject resultJson = new JSONObject();
        resultJson.put("sessionId", sessionId);
        if (iEduUserVo != null) {
            if (iEduUserVo.getAuditUserVo() != null) {
                iEduUserVo.getAuditUserVo().setIdNum("");
            } else if (iEduUserVo.getApplyUserVo() != null) {
                iEduUserVo.getApplyUserVo().setIdNum("");
            }
        }
        resultJson.put("iEduUserVo", iEduUserVo);
        return resultJson;
    }

    //2.过滤器里用于创建会话缓存信息
    public IeduUserVo setIeduSessionCache(String sessionId, String idNum, String name, String mobileNum, String userId, HttpServletRequest request) {
        log.info("请求 setIeduSessionCache idNum:" + idNum);
        IeduUserVo iEduUserVo = getIeduUserVoFromDb(idNum, name, mobileNum, userId, request);
        //缓存会话信息
        //sessionId对应idNum(方便排查问题)
        RedisCacheUtil.saveCacheByPriod(Constants.iEduCacheKeyToIdNum + ":" + sessionId, idNum,
            "2400");
        //idNum对应IeduUserVo（方便排查问题）
        RedisCacheUtil.saveCacheByPriod(Constants.iEduIdNumCacheKeyPrefix + ":" + idNum, JSON.toJSONString(iEduUserVo),
            "2400");
        //sessionId对应IeduUserVo
        RedisCacheUtil.saveCacheByPriod(Constants.iEduCacheKeyPrefix + ":" + sessionId, JSON.toJSONString(iEduUserVo),
            "2400");
        return iEduUserVo;
    }

    private IeduUserVo getIeduUserVoFromDb(String idNum, String name, String mobileNum, String userId, HttpServletRequest request) {
        IeduUserVo iEduUserVo = userService.getIeduUserVoByIdNum(idNum, userId);
        if (StringUtils.isBlank(iEduUserVo.getType())) {
            ApplyUserVo applyUserVo = new ApplyUserVo();
            applyUserVo.setIdNum(idNum);
            applyUserVo.setName(name);
            applyUserVo.setMobileNum(mobileNum);
            iEduUserVo.setApplyUserVo(applyUserVo);
            iEduUserVo.setType(UserTypeEnum.APPLY.getValue());
        }
        ;
        return iEduUserVo;
    }


    //4.登出接口
    @GetMapping("/iEduUser/logout")
    public JSONObject logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("请求 /iEduUser/logout");
        String iEduCookieValue = RequestHelper.getValueFromRequestParamOrCookie(Constants.iEduCookieName, httpServletRequest, httpServletResponse);
        RedisCacheUtil.expireByKey(Constants.iEduCacheKeyPrefix + ":" + iEduCookieValue);

        JSONObject result = new JSONObject();
        result.put("message", "登出成功！！!");
        result.put("sucess", true);
        return result;
    }

    @GetMapping("/iEduUser/getMenusByLoginRoleId")
    public Object getMenusByLoginRoleId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("请求 /iEduUser/getMenusByLoginRoleId");
        IeduUserVo iEduUserVo = load(null, null);
        String roleId = "applyUser";
        if (iEduUserVo.getAuditUserVo() == null) {
            roleId = "applyUser";
        } else {
            roleId = iEduUserVo.getAuditUserVo().getRoleId();
        }
        JSONObject jsonObject = userService.getMenusByLoginRoleId(roleId);
        return jsonObject;
    }
}
