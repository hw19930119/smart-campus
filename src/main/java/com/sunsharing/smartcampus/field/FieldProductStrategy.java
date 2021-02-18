/*
 * @(#) FieldProductStrategy
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:33
 */

package com.sunsharing.smartcampus.field;

import org.springframework.stereotype.Component;

/**
 * @author: cxy
 * @date: 2019-12-30 9:21:50
 * @description: 字段生成策略
 */
@Component
public class FieldProductStrategy extends BaseStrategy<IFieldProduct> {

    @Override
    protected String beanPrefix() {
        return IFieldProduct.BEAN_PREFIX;
    }

    @Override
    protected String strategyName() {
        return "字段生成器";
    }
}
