/*
 * @(#) UnitTypeEnum
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 17:08:36
 */

package com.sunsharing.smartcampus.constant.enums;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author: cxy
 * @date: 2020/5/19
 * @description:
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum UnitTypeEnum {

    BAI_FEN_BI("2", "%"),
    YUAN("0", "元"),
    WAN_YUAN("1", "万元");
    private String value;
    private String label;


    public static String getLabel(String value) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        UnitTypeEnum[] values = UnitTypeEnum.values();
        for (UnitTypeEnum unitTypeEnum : values) {
            if (value.equals(unitTypeEnum.getValue())) {
                return unitTypeEnum.getLabel();
            }
        }
        return "";
    }

}
