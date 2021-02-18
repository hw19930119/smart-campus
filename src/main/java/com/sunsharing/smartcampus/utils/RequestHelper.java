package com.sunsharing.smartcampus.utils;

import com.sunsharing.component.utils.web.CookieUtils;
import com.sunsharing.share.webex.exception.ShareBusinessException;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHelper {
    public static String getValueFromRequestParamOrCookie(String key, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String cookieValue = CookieUtils.getCookieValue(httpServletRequest, key);//警告：还不能得到特殊符号的cookie！！！！！！！！！！！！！
        if (StringUtils.isBlank(cookieValue)) {
            cookieValue = httpServletRequest.getParameter(key);//调试方便
            /*if (StringUtils.isBlank(cookieValue)) {
                CookieUtils.setCookie(httpServletResponse, key, reqValue);
            }*/
        }
        if (StringUtils.isNotBlank(cookieValue)) {
            try {
                cookieValue = URLDecoder.decode(cookieValue, "UTF-8");//cookie中的值不能存储 分号等号空格号，反解析
            } catch (UnsupportedEncodingException e) {
                throw new ShareBusinessException(1500, "cookie解码异常");
            }
        }
        return cookieValue;
    }
}
