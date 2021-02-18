/*
 * @(#) TimerProduct
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:32
 */

package com.sunsharing.smartcampus.field.impl;

import com.alibaba.fastjson.JSONArray;
import com.sunsharing.smartcampus.constant.enums.TimerTypeEnum;
import com.sunsharing.smartcampus.field.AbstractFieldProductProduct;
import com.sunsharing.smartcampus.field.FormField;
import com.sunsharing.smartcampus.field.IFieldProduct;
import com.sunsharing.smartcampus.field.view.Timer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author: cxy
 * @date: 2020/5/13
 * @description:
 */
@Component(IFieldProduct.BEAN_PREFIX + TimerProduct.TAG_ID)
public class TimerProduct extends AbstractFieldProductProduct {

    public static final String TAG_ID = "11";
    static final String TAG_TYPE = "calendar";
    static final String TAG_NAME = "日期组件";

    @Override
    protected void doProduct(String fieldConfigJson, String promptMsg, FormField formField) throws Exception {
        formField.setType(TAG_TYPE).setName(TAG_NAME);

        if (StringUtils.isEmpty(fieldConfigJson)) {
            return;
        }
        Timer timer = JSONArray.parseObject(fieldConfigJson, Timer.class);
        if (null == timer) {
            return;
        }
        String timerType = timer.getTimer();
        Map<String, Object> componentProps = formField.getComponentProps();
        componentProps.put("format", TimerTypeEnum.getFormatObject(timerType));
        componentProps.put("viewMode", TimerTypeEnum.getViewModeObject(timerType));
        formField.setComponentProps(componentProps);
    }

    @Override
    public String getValue(String value) throws Exception {
        return value;
    }

    @Override
    public String getLabel(String fieldId, String value) throws Exception {
        return value;
    }

    @Override
    public String getType() throws Exception {
        return "text";
    }

    @Override
    public List<String> getFileList(String value) throws Exception {
        return null;
    }
}
