/*
 * @(#) TextFormatEnum
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 17:08:35
 */

package com.sunsharing.smartcampus.constant.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: cxy
 * @date: 2020/5/6
 * @description:
 */
@Getter
@AllArgsConstructor
public enum TextFormatEnum {

    ALL("1", "常规", "", ""),
    WORD("2", "文字", "^[\\u4e00-\\u9fa5a-zA-Z]{0,}$", ""),
    // SYMBOL("3", "符号", "[`~!@#$%^&*()_\\-+=<>?:\"{}|,.\\/;'\\\\[\\]·~！@#￥%……&*（）——\\-+={}|《》？：“”【】、；‘'，。、]"),
    NUMBER("3", "数字", "^(([1-9]{1}\\d*)|(0{1}))(\\.\\d{%s})$", "^(([1-9]{1}\\d*)|(0{1}))$"),
    // SPACE("5", "空格", "^\\s+$"),
    PERCENTAGE("4", "百分比", "^(([1-9]{1}\\d*)|(0{1}))(\\.\\d{%s})$", "^(([1-9]{1}\\d*)|(0{1}))$"),
    MONEY("5", "金额", "^(([1-9]{1}\\d*)|(0{1}))(\\.\\d{%s})$", "^(([1-9]{1}\\d*)|(0{1}))$"),
    ;

    private String value;
    private String label;
    private String pattern;
    private String zeroPattern;

    public static boolean isContainsAll(String formatValues) {
        List<String> formatValueList = Arrays.asList(formatValues.split(","));
        return formatValueList.contains(ALL.getValue());
    }

    public static String getPattern(String formatValue, String decimalPlaces) {
        String pattern = null;
        TextFormatEnum textFormatEnum = getEnum(formatValue);
        if (textFormatEnum != null) {
            if (NUMBER.getValue().equals(textFormatEnum.getValue())
                || PERCENTAGE.getValue().equals(textFormatEnum.getValue())
                || MONEY.getValue().equals(textFormatEnum.getValue())
            ) {
                if ("0".equals(decimalPlaces)) {
                    pattern = textFormatEnum.getZeroPattern();
                } else {
                    pattern = String.format(textFormatEnum.getPattern(), decimalPlaces);
                }
            } else {
                pattern = textFormatEnum.getPattern();
            }
        }
        return pattern;
    }

    public static String getMessage(String formatValue, String decimalPlaces) {
        TextFormatEnum textFormatEnum = getEnum(formatValue);
        if (textFormatEnum == null) {
            return "";
        }
        if (NUMBER.getValue().equals(textFormatEnum.getValue())
            || PERCENTAGE.getValue().equals(textFormatEnum.getValue())
            || MONEY.getValue().equals(textFormatEnum.getValue())
        ) {
            if ("0".equals(decimalPlaces)) {
                return String.format("请输入%s, 且不含有小数点", textFormatEnum.getLabel());
            } else {
                return String.format("请输入%s, 且必须为%s位小数点", textFormatEnum.getLabel(), decimalPlaces);
            }
        } else if (ALL.getValue().equals(textFormatEnum.getValue())) {
            return "";
        } else {
            return String.format("请输入%s", textFormatEnum.getLabel());
        }

    }

    public static TextFormatEnum getEnum(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (TextFormatEnum textFormatEnum : TextFormatEnum.values()) {
            if (textFormatEnum.getValue().equals(value)) {
                return textFormatEnum;
            }
        }
        return null;
    }

}
