/*
 * @(#) AddressProduct
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
import com.sunsharing.smartcampus.field.view.Address;
import com.sunsharing.smartcampus.field.view.AddressVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: cxy
 * @date: 2020/5/13
 * @description:
 */
@Component(IFieldProduct.BEAN_PREFIX + AddressProduct.TAG_ID)
public class AddressProduct extends AbstractFieldProductProduct {

    public static final String TAG_ID = "10";
    static final String TAG_TYPE = "xzqh";
    static final String TAG_NAME = "行政区划";

    @Override
    protected void doProduct(String fieldConfigJson, String promptMsg, FormField formField) throws Exception {
        formField.setType(TAG_TYPE).setName(TAG_NAME);

        if (StringUtils.isEmpty(fieldConfigJson)) {
            return;
        }
        Address address = JSONArray.parseObject(fieldConfigJson, Address.class);
        if (null == address) {
            return;
        }
        String addressType = address.getAddress();
        Map<String, Object> componentProps = formField.getComponentProps();
        componentProps.put("level", addressType);
        formField.setComponentProps(componentProps);
    }

    @Override
    public String getValue(String value) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        List<AddressVo> addressVoList = JSONArray.parseArray(value, AddressVo.class);
        List<String> valueList = addressVoList.stream().map(AddressVo::getValue).collect(Collectors.toList());
        return String.join(",", valueList);
    }

    @Override
    public String getLabel(String fieldId, String value) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        List<AddressVo> addressVoList = JSONArray.parseArray(value, AddressVo.class);
        List<String> labelList = addressVoList.stream().map(AddressVo::getLabel).collect(Collectors.toList());
        return String.join(",", labelList);
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
