/*
 * @(#) TextareaProduct
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:33
 */

package com.sunsharing.smartcampus.field.impl;

import com.alibaba.fastjson.JSONArray;
import com.sunsharing.smartcampus.field.AbstractFieldProductProduct;
import com.sunsharing.smartcampus.field.FormField;
import com.sunsharing.smartcampus.field.IFieldProduct;
import com.sunsharing.smartcampus.field.view.Textarea;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author: cxy
 * @date: 2020/3/3
 * @description: 多文本输入框
 */
@Component(IFieldProduct.BEAN_PREFIX + TextareaProduct.TAG_ID)
public class TextareaProduct extends AbstractFieldProductProduct {

    public static final String TAG_ID = "2";
    public static final String TAG_TYPE = "string";
    static final String TAG_FORMAT = "textarea";
    static final String TAG_NAME = "多文本输入框";

    @Override
    protected void doProduct(String fieldConfigJson, String promptMsg, FormField formField) throws Exception {
        formField.setType(TAG_FORMAT).setName(TAG_NAME);
        Textarea textarea = null;
        if (StringUtils.isNotEmpty(fieldConfigJson)) {
            textarea = JSONArray.parseObject(fieldConfigJson, Textarea.class);
        }
        Map<String, Object> componentProps = formField.getComponentProps();
        componentProps.put("placeholder", promptMsg);
        if (null != textarea) {
            componentProps.put("maxlength", textarea.getCharacterSize());
        }

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
