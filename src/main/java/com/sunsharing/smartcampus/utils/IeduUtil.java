package com.sunsharing.smartcampus.utils;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.share.common.base.DateUtil;
import com.sunsharing.smartcampus.configuration.properties.IeduServiceConfig;
import com.sunsharing.smartcampus.constant.constants.Constants;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class IeduUtil {

    public static String getAccessToken(IeduServiceConfig ieduServiceConfig){
        String accessToken= (String)RedisCacheUtil.getCache(Constants.iEduCacheKeyAccessToken);
        if(StringUtils.isNotBlank(accessToken)){
            return accessToken;
        }

        String timeStamp=DateUtil.toString(new Date(), DateUtil.DATETIME_FORMAT_STR_14);;
        String keyInfoSrc=ieduServiceConfig.getAppId()+ieduServiceConfig.getAppSecret()+timeStamp;
        log.info("getAccessToken keyInfoSrc：" + keyInfoSrc);
        String keyInfo=MD5Encrypt.encode(keyInfoSrc);
        String getAccessTokenUrl="http://www.xmedu.cn/ixmedu/Api/Login/getAccessToken";
        JSONObject params=new JSONObject();
        params.put("appId",ieduServiceConfig.getAppId());
        params.put("timeStamp",timeStamp);
        params.put("keyInfo",keyInfo);
        String jsonStr = HttpUtils.postJson(getAccessTokenUrl, params);
        log.info("getAccessTokenUrl return：" + jsonStr);
        JSONObject json = JSONObject.parseObject(jsonStr);
        JSONObject result = json.getJSONObject("result");
        accessToken=result.getString("accessToken");
        Long validTime=result.getLong("validTime");
        validTime=(validTime-new Date().getTime()-30)/1000;
        RedisCacheUtil.saveCacheByPriod(Constants.iEduCacheKeyAccessToken,accessToken,validTime.toString());
        return accessToken;
    }

    public static JSONObject getUserInfo(String code,IeduServiceConfig ieduServiceConfig) throws Exception{
        JSONObject jsonObject=new JSONObject();
        //1.获取accessToken
        String accessToken=getAccessToken(ieduServiceConfig);
        //2.获取用户信息
        String getUserUrl="http://www.xmedu.cn/ixmedu/Api/Login/validaTicket?accessToken="+accessToken;
        JSONObject params=new JSONObject();
        params.put("ticket",code);
        String jsonStr = HttpUtils.postJson(getUserUrl, params);
        log.info("getUserInfo return：" + jsonStr);
        JSONObject json = JSONObject.parseObject(jsonStr);
        String resultEncrypted = json.getString("result");
        String resultDecrypted=AESUtil.Decrypt(resultEncrypted,ieduServiceConfig.getAesKey().getBytes(),ieduServiceConfig.getAesIv().getBytes());
        log.info("getUserInfo resultDecrypted：" + resultDecrypted);
        jsonObject=JSONObject.parseObject(resultDecrypted);
        return jsonObject;
    }

}
