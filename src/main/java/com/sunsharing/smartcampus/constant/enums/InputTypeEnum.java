/*
 * @(#) InputTypeEnum
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 12:05:48
 */

package com.sunsharing.smartcampus.constant.enums;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxw on 2020/3/18.
 */
public enum InputTypeEnum implements IShareEnum {
    常规("1", "常规"),
    文字("2", "文字"),
    数字("3", "数字"),
    百分比("4", "百分比"),
    金额("5", "金额");

    private String value;
    private String label;

    InputTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

    /**
     * 获取输入类型 多个已,隔开
     * @param values 值
     * @return
     */
    public static String getInputTypeName(String values) {
        InputTypeEnum[] statusEnums = InputTypeEnum.values();
        List<String> resultList = new ArrayList<>();
        for (InputTypeEnum enmu : statusEnums) {
            if (values.contains(enmu.getValue())) {
                resultList.add(enmu.getLabel());
            }
        }
        return Joiner.on(",").join(resultList);
    }
}
