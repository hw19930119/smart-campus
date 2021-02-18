/*
 * @(#) IXMServiceConfig
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-03-25 15:33:53
 */

package com.sunsharing.smartcampus.configuration.properties;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019/7/17.
 */
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@Configuration
@Data
public class IeduServiceConfig {
    //接口调用地址
    @Value("${iedu.idsUrl}")
    private String idsUrl;
    //idsUrlHttpPrefix
    @Value("${iedu.idsUrlHttpPrefix}")
    private String idsUrlHttpPrefix;

    //协作应用名
    @Value("${iedu.appId}")
    private String appId;
    //加密密钥
    @Value("${iedu.appSecret}")
    private String appSecret;
    //消息摘要加密算法
    @Value("${iedu.aesKey}")
    private String aesKey;
    //token名称
    @Value("${iedu.aesIv}")
    private String aesIv;
}
