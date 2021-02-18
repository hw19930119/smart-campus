/*
 * @(#) SevletCofinguration
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-20 11:00:18
 */

package com.sunsharing.smartcampus.configuration;

import com.sunsharing.smartcampus.configuration.properties.SysParams;

import com.sunsharing.smartcampus.filter.IeduFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SysParams.class)
@Log4j2
public class SevletCofinguration {

    @Autowired
    SysParams sysParams;

    // @Bean
    // public FilterRegistrationBean createSsoFilter() {
    //     FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    //     filterRegistrationBean.setFilter(new SSOFilter());
    //     filterRegistrationBean.addInitParameter("excludePatterns", "login.html|css|js|images|png|jpg");
    //     filterRegistrationBean.addInitParameter("ssoserver", sysParams.getEosServer());
    //     filterRegistrationBean.addUrlPatterns("/*");
    //     filterRegistrationBean.setOrder(1);
    //     return filterRegistrationBean;
    // }
    //申请端和审核端
    @Bean
    public FilterRegistrationBean createIeduSessUserLoadFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new IeduFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("excludePatterns", "css|js|images|png|jpg|gif|bmp|jpeg" +
                "|/iEduUser/login|/iEduUser/realLogin|/iEduUser/getAiXiaMenConfig"+
                "|/changeUser.html|/realLogin.html");
        filterRegistrationBean.setName("createIeduSessUserLoadFilter");
        filterRegistrationBean.setOrder(2);
        log.info("iEdu sessionUser过滤器被创建");
        return filterRegistrationBean;
    }
}
