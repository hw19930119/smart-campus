/*
 * @(#) CheckboxProduct
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:33
 */

package com.sunsharing.smartcampus.field.impl;

import com.google.common.base.Joiner;

import com.alibaba.fastjson.JSONArray;
import com.sunsharing.smartcampus.field.AbstractFieldProductProduct;
import com.sunsharing.smartcampus.field.FormField;
import com.sunsharing.smartcampus.field.IFieldProduct;
import com.sunsharing.smartcampus.field.view.Checkbox;
import com.sunsharing.smartcampus.field.view.ValueLabelVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: cxy
 * @date: 2020/3/3
 * @description: 多选框
 */
@Component(IFieldProduct.BEAN_PREFIX + CheckboxProduct.TAG_ID)
public class CheckboxProduct extends AbstractFieldProductProduct {

    public static final String TAG_ID = "5";
    public static final String TAG_TYPE = "checkboxes";
    static final String TAG_NAME = "多选框";

    @Override
    protected void doProduct(String fieldConfigJson, String promptMsg, FormField formField) throws Exception {
        formField.setType(TAG_TYPE).setName(TAG_NAME);

        if (StringUtils.isEmpty(fieldConfigJson)) {
            return;
        }
        Checkbox checkbox = JSONArray.parseObject(fieldConfigJson, Checkbox.class);
        if (null == checkbox) {
            return;
        }
        List<Map<String, Object>> itemList = checkbox.getItemList();
        tranField(itemList, formField);

        // tranProps(formField);
    }

    @Override
    public String getValue(String value) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        List<ValueLabelVo> valueLabelVoList = JSONArray.parseArray(value, ValueLabelVo.class);
        List<String> valueList = valueLabelVoList.stream().map(ValueLabelVo::getValue).collect(Collectors.toList());
        return Joiner.on(",").skipNulls().join(valueList);
    }

    @Override
    public String getLabel(String fieldId, String value) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        List<ValueLabelVo> valueLabelVoList = JSONArray.parseArray(value, ValueLabelVo.class);
        List<String> valueList = valueLabelVoList.stream().map(ValueLabelVo::getLabel).collect(Collectors.toList());
        return Joiner.on(",").skipNulls().join(valueList);
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
