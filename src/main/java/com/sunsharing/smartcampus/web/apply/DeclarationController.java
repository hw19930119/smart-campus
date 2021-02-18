/*
 * @(#) DeclarationController
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-04 19:53:12
 */

package com.sunsharing.smartcampus.web.apply;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.share.common.base.exception.ResponseCode;
import com.sunsharing.share.common.base.exception.ShareBusinessException;
import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.share.webex.annotation.ShareRest;
import com.sunsharing.smartcampus.constant.enums.AttestStateEnum;
import com.sunsharing.smartcampus.mapper.common.TzhxyTemplateMapper;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyBaseInfo;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyTemplate;
import com.sunsharing.smartcampus.model.vo.apply.ApplyLoginUser;
import com.sunsharing.smartcampus.model.vo.apply.DeclarationInitVo;
import com.sunsharing.smartcampus.model.vo.apply.SubmitParamVo;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.service.apply.TzhxyBaseInfoService;
import com.sunsharing.smartcampus.service.audit.ApplyUserService;
import com.sunsharing.smartcampus.service.comment.TzhxyDpConfigService;
import com.sunsharing.smartcampus.service.common.TzhxyPcService;
import com.sunsharing.smartcampus.service.dm.SmartDmService;
import com.sunsharing.smartcampus.service.filed.DeclareConfigService;
import com.sunsharing.smartcampus.web.common.IeduUserController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;

@RestController
@ShareRest
@RequestMapping("/declaration")
@Log4j2
public class DeclarationController {
    @Autowired
    TzhxyTemplateMapper tzhxyTemplateMapper;
    @Autowired
    DeclareConfigService declareConfigService;
    @Autowired
    TzhxyPcService tzhxyPcService;

    @Autowired
    TzhxyBaseInfoService tzhxyBaseInfoService;

    @Autowired
    SmartDmService smartDmService;

    @Autowired
    ApplyUserService applyUserService;

    @Autowired
    TzhxyDpConfigService tzhxyDpConfigService;

    /**
     * 获取当前可用批次及其模板信息
     */
    @GetMapping("/loadDeclareInit")
    @ResponseBody
    public DeclarationInitVo laodDeclarationInit(HttpServletRequest httpServletRequest,
                                                 HttpServletResponse httpServletResponse,
                                                 String declareId) {
        IeduUserVo userVo = IeduUserController.load(httpServletRequest, httpServletResponse);
        return tzhxyPcService.laodDeclarationInit(userVo.getApplyUserVo(), declareId);
    }

    @GetMapping("/getTreePreviewByTemplateId")
    @ResponseBody
    public Map getTreePreviewByTemplateId(String templateId) {

        Map basicFieldList = declareConfigService.getTreePreviewByTemplateId(templateId);
        return basicFieldList;
    }

    @GetMapping("/getTreePreview")
    @ResponseBody
    public Map getTreePreview(String declareId, String piId, boolean isShenBao,
                              @RequestParam(required = false) String isExpertCommentPage) {
        TzhxyBaseInfo tzhxyBaseInfo = tzhxyBaseInfoService.getById(declareId);
        if (Objects.isNull(tzhxyBaseInfo)) {
            throw new ShareBusinessException(1500, "未获取到基础表信息");
        }
        String pcNo = tzhxyBaseInfo.getPcNo();
        String schoolType = tzhxyBaseInfo.getSchoolType();
        //获取自评模板信息
        List<TzhxyTemplate> tzhxyTemplate = tzhxyTemplateMapper.selectTemplateByPcnoAndSchooltype(pcNo, schoolType);
        if (tzhxyTemplate.isEmpty()) {
            throw new ShareBusinessException(1500, "未获取到模板信息");
        }
        String templateId = tzhxyTemplate.get(0).getTemplateNo();
        Map treePreview = declareConfigService.getTreePreview(templateId, declareId, piId, isShenBao, isExpertCommentPage);
        treePreview.put("schoolName", tzhxyBaseInfo.getSchoolName());
        Date commitTime = tzhxyBaseInfo.getCreateTime();
        treePreview.put("updateTime", commitTime);
        treePreview.put("dpOpinion", smartDmService.loadDpOpinion());
        return treePreview;
    }

    /**
     * 提交自评申报
     * @param submitParamVo
     * @return
     */
    @PostMapping("/submitDeclare")
    @ResponseBody
    public ResponseCode submitDeclare(@RequestBody SubmitParamVo submitParamVo) {
        try {
            if (StringUtils.isBlank(submitParamVo.getDeclareId())) {
                return ResponseCode.of(200, "入参错误");
            }
            ResponseCode result = tzhxyBaseInfoService.submitDeclare(submitParamVo);
            return result;
        } catch (ShareBusinessException e) {
            return ResponseCode.of(200, e.getMessage());
        } catch (RuntimeException e) {
            log.error("提交自评申报，发生异常", e);
            return ResponseCode.of(200, "提交申报发生异常，请联系系统管理员");
        }
    }

    /**
     * 获取当前登录账号与学校的认证信息
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @PostMapping("/getLoginUser")
    @ResponseBody
    public Map getLoginUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        IeduUserVo userVo = IeduUserController.load(httpServletRequest, httpServletResponse);
        List<Map<String, Object>> listMap = smartDmService.loadAttestStateEnum();
        List<Map<String, Object>> xzqhMap = smartDmService.loadSmartDm("DM_SS_XZQH");
        List<Map<String, Object>> schoolMap = smartDmService.loadSchoolTypeDm();
        ApplyUserVo applyUser = userVo.getApplyUserVo();
        ApplyLoginUser loginUser = applyUserService.queryLoginUser(applyUser.getIdNum());
        if (!StringUtils.isBlank(applyUser.getId())) {//认证通过
            boolean falg = false;
            if (AttestStateEnum.认证中.getValue().equals(loginUser.getState())) {
                falg = true;
            }
            loginUser = userVo.getApplyUserVo().toApplyLoginUser();
            loginUser.setSftz(falg);
        } else {
            if (loginUser == null) {
                loginUser = new ApplyLoginUser();
                loginUser.setName(applyUser.getName());
            } else {
                if (AttestStateEnum.认证中.getValue().equals(loginUser.getState())) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", loginUser.getId());
                    JSONObject resutl = applyUserService.queryUnitCodeExitsOtherUser(obj);
                    if (resutl.getBoolean("status")) {
                        loginUser.setOpinion(resutl.getString("msg"));
                    }
                }
            }
            //加强判断，如果是认证通过了，刷新接口时，通过前端跳转来刷新session
            if (StringUtils.isBlank(applyUser.getId())
                && AttestStateEnum.认证通过.getValue().equals(loginUser.getState())) {
                loginUser.setSftz(true);
            }
        }
        //添加申报开关，当申报结束后由用户决定是否开启或者关闭，关闭后前端不能进行学校认证
        String sbSwitch = applyUserService.queryForSbSwitch();
        return MapUtil.ofHashMap("loginUser", loginUser,
            "attestStateEnum", listMap, "xzqhs", xzqhMap, "schoolEnum", schoolMap, "sbSwitch", sbSwitch);
    }

    /**
     *  撤销申报信息
     * @param paramVo
     * @return
     */
    @PostMapping("/revokeDeclaration")
    @ResponseBody
    public ResponseCode revokeDeclaration(@RequestBody SubmitParamVo paramVo) {
        try {
            return tzhxyBaseInfoService.revokeDeclaration(paramVo);
        } catch (RuntimeException e) {
            log.error("撤销申报发生异常", e);
            return ResponseCode.of(200, "撤销申报失败");
        }
    }

    /**
     * 提交认证信息
     * @param attestationVo
     * @param httpServletRequest
     * @param httpServletResponse
     */
    @PostMapping("/submitAttestation")
    @ResponseBody
    public Map<String, Object> submitAttestation(@RequestBody ApplyLoginUser attestationVo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            IeduUserVo userVo = IeduUserController.load(httpServletRequest, httpServletResponse);
            return applyUserService.submitAttestation(userVo.getApplyUserVo(), attestationVo);
        } catch (RuntimeException e) {
            log.error("提交人员认证发生异常", e);
            return MapUtil.ofHashMap("status", false, "msg", "提交申报失败");
        }
    }


    /**
     * 撤销认证功能
     * @param attestationVo
     * @param httpServletRequest
     * @param httpServletResponse
     */
    @PostMapping("/revokeAttestation")
    @ResponseBody
    public Map<String, Object> revokeAttestation(@RequestBody ApplyLoginUser attestationVo, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            IeduUserVo userVo = IeduUserController.load(httpServletRequest, httpServletResponse);
            return applyUserService.revokeAttestation(userVo.getApplyUserVo(), attestationVo);
        } catch (RuntimeException e) {
            log.error("撤销人员认证发生异常", e);
            return MapUtil.ofHashMap("status", false, "msg", "提交申报发生异常，请联系系统管理员");
        }
    }


}
