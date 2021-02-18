/*
 * @(#) SelectProduct
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:33
 */

package com.sunsharing.smartcampus.field.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.field.AbstractFieldProductProduct;
import com.sunsharing.smartcampus.field.FormField;
import com.sunsharing.smartcampus.field.IFieldProduct;
import com.sunsharing.smartcampus.field.view.Select;
import com.sunsharing.smartcampus.field.view.ValueLabelVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author: cxy
 * @date: 2020/3/3
 * @description: 下拉单选
 */
@Component(IFieldProduct.BEAN_PREFIX + SelectProduct.TAG_ID)
public class SelectProduct extends AbstractFieldProductProduct {

    public static final String TAG_ID = "6";
    public static final String TAG_TYPE = "select";
    static final String TAG_NAME = "下拉单选";

    @Override
    protected void doProduct(String fieldConfigJson, String promptMsg, FormField formField) throws Exception {
        formField.setType(TAG_TYPE).setName(TAG_NAME);

        Select select = JSONArray.parseObject(fieldConfigJson, Select.class);
        if (null == select) {
            return;
        }
        List<Map<String, Object>> itemList = select.getItemList();
        tranField(itemList, formField);

        // tranProps(formField);
    }

    @Override
    public String getValue(String value) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        ValueLabelVo valueLabelVo = JSONObject.parseObject(value, ValueLabelVo.class);
        return valueLabelVo.getValue();
    }

    @Override
    public String getLabel(String fieldId, String value) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        ValueLabelVo valueLabelVo = JSONObject.parseObject(value, ValueLabelVo.class);
        return valueLabelVo.getLabel();
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
