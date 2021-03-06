/*
 * @(#) WebMvcConfigure
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-20 11:00:18
 */

package com.sunsharing.smartcampus.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

import lombok.extern.log4j.Log4j2;

/**
 *
 * @author 吴宸勖
 */
@Log4j2
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = WebMvcConfigure.class)
public class WebMvcConfigure extends WebMvcConfigurerAdapter {

    /*Logger log = Logger.getLogger(WebMvcConfigure.class);*/

    public WebMvcConfigure() {
        log.info("WebMvcConfigure created");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.extendMessageConverters(converters);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/public/");
    }
}
