/*
 * @(#) RadiosProduct
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:32
 */

package com.sunsharing.smartcampus.field.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.field.AbstractFieldProductProduct;
import com.sunsharing.smartcampus.field.FormField;
import com.sunsharing.smartcampus.field.IFieldProduct;
import com.sunsharing.smartcampus.field.view.Radios;
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
 * @description: 单选框
 */
@Component(IFieldProduct.BEAN_PREFIX + RadiosProduct.TAG_ID)
public class RadiosProduct extends AbstractFieldProductProduct {

    public static final String TAG_ID = "4";
    public static final String TAG_TYPE = "radios";
    static final String TAG_NAME = "单选框";

    @Autowired
    private FieldConfigService fieldConfigService;

    @Override
    protected void doProduct(String fieldConfigJson, String promptMsg, FormField formField) throws Exception {
        formField.setType(TAG_TYPE).setName(TAG_NAME);

        if (StringUtils.isEmpty(fieldConfigJson)) {
            return;
        }

        Radios radios = JSONArray.parseObject(fieldConfigJson, Radios.class);
        if (null == radios) {
            return;
        }
        List<Map<String, Object>> itemList = radios.getItemList();
        tranField(itemList, formField);

        // tranProps(formField);

    }

    @Override
    public String getValue(String value) throws Exception {
        return value;
    }

    @Override
    public String getLabel(String fieldId, String value) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        FieldConfig fieldConfig = this.fieldConfigService.getById(fieldId);
        String fieldConfigJson = fieldConfig.getFieldConfigJson();
        JSONObject jsonObject = JSONObject.parseObject(fieldConfigJson);
        JSONArray jsonArray = jsonObject.getJSONArray("itemList");
        StringBuffer result = new StringBuffer();
        jsonArray.stream().forEach(jsonobject -> {
            JSONObject j = (JSONObject) jsonobject;
            if (value.equals(j.getString("value"))) {
                result.append(j.getString("label"));
                return;
            }
        });
        return result.toString();
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
