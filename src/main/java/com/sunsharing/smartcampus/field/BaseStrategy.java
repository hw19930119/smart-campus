/*
 * @(#) BaseStrategy
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 17:18:36
 */

package com.sunsharing.smartcampus.field;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;

public abstract class BaseStrategy<T> {

    @Autowired(required = false)
    private Map<String, T> strategyBeanMap;

    /**
     * 获取bean前缀
     * @return bean前缀
     */
    protected abstract String beanPrefix();

    /**
     * 获取策略名称
     * @return 策略名称
     */
    protected abstract String strategyName();

    /*
     * 根据key获取策略
     *
     * @param beanName 字段适配器策略名称
     */
    public Optional<T> get(String beanName) {
        if (StringUtils.isEmpty(beanName) || strategyBeanMap == null) {
            return Optional.empty();
        }
        String beanFullName = this.beanPrefix().concat(beanName);
        return Optional.ofNullable(strategyBeanMap.get(beanFullName));
    }

    /**
     * 根据key获取策略
     * @param beanName bean名称
     * @return 存在返回, 不存在则抛异常
     */
    public T getOrThrow(String beanName) throws Exception {
        return this.get(beanName.toUpperCase())
            .orElseThrow(() -> new Exception(String.format("找不到指定的%s:%s", this.strategyName(), beanName)));
    }

}
