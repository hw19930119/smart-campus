/*
 * @(#) InputProduct
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:32
 */

package com.sunsharing.smartcampus.field.impl;

import com.alibaba.fastjson.JSONArray;

import com.sunsharing.component.utils.base.MapHelper;
import com.sunsharing.smartcampus.constant.enums.TextFormatEnum;
import com.sunsharing.smartcampus.constant.enums.UnitTypeEnum;
import com.sunsharing.smartcampus.field.AbstractFieldProductProduct;
import com.sunsharing.smartcampus.field.FormField;
import com.sunsharing.smartcampus.field.IFieldProduct;
import com.sunsharing.smartcampus.field.view.Input;
import com.sunsharing.smartcampus.model.pojo.filed.FieldConfig;
import com.sunsharing.smartcampus.service.filed.FieldConfigService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author: cxy
 * @date: 2020/3/3
 * @description: 单文本输入框
 */
@Component(IFieldProduct.BEAN_PREFIX + InputProduct.TAG_ID)
public class InputProduct extends AbstractFieldProductProduct {

    public static final String TAG_ID = "1";
    public static final String TAG_TYPE = "string";
    static final String TAG_NAME = "单文本输入框";

    @Autowired
    private FieldConfigService fieldConfigService;

    @Override
    protected void doProduct(String fieldConfigJson, String promptMsg, FormField formField) throws Exception {
        formField.setType(TAG_TYPE).setName(TAG_NAME);
        Input input = null;
        if (StringUtils.isNotEmpty(fieldConfigJson)) {
            input = JSONArray.parseObject(fieldConfigJson, Input.class);
        }

        Map<String, Object> componentProps = formField.getComponentProps();
        componentProps.put("placeholder", promptMsg);

        if (null == input) {
            return;
        }

        componentProps.put("maxlength", input.getCharacterSize());

        if (!TextFormatEnum.isContainsAll(input.getInputFormat())) {
            formField.setPattern(TextFormatEnum.getPattern(input.getInputFormat(),
                StringUtils.isEmpty(input.getDecimalPlaces()) ? "0" : input.getDecimalPlaces()));
            formField.setMessage(MapHelper.ofHashMap("pattern",
                TextFormatEnum.getMessage(input.getInputFormat(),
                    StringUtils.isEmpty(input.getDecimalPlaces()) ? "0" : input.getDecimalPlaces())));
        }

        if (StringUtils.isNotEmpty(input.getUnit())) {
            String addon = UnitTypeEnum.getLabel(input.getUnit());
            if (StringUtils.isNotEmpty(addon)) {
                componentProps.put("addon", addon);
            }
        }

        formField.setComponentProps(componentProps);

    }

    @Override
    public String getValue(String value) throws Exception {
        return value;
    }

    @Override
    public String getLabel(String fieldId, String value) throws Exception {
        String unit = "";
        FieldConfig fieldConfig = this.fieldConfigService.getById(fieldId);
        if (fieldConfig != null && StringUtils.isNotEmpty(fieldConfig.getFieldConfigJson())) {
            Input input = JSONArray.parseObject(fieldConfig.getFieldConfigJson(), Input.class);
            if (null != input && StringUtils.isNotEmpty(input.getUnit())) {
                unit = UnitTypeEnum.getLabel(input.getUnit());
            }
        }
        return value + unit;
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
