/*
 * @(#) TimerTypeEnum
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 17:08:35
 */

package com.sunsharing.smartcampus.constant.enums;

import com.alibaba.fastjson.JSONObject;

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
public enum TimerTypeEnum {

    YEAR("1", "年", "YYYY", "years"),
    YEAR_MONTH("2", "年月", "YYYY-MM", "months"),
    DATE("3", "日期", "YYYY-MM-DD", "years"),
    TIME("4", "时间", "HH:mm:ss", "time"),
    DATA_TIME("5", "日期-时间", "YYYY-MM-DD HH:mm:ss", "years");

    private String value;
    private String label;
    private String format;
    private String viewMode;


    public static JSONObject getFormatObject(String value) {
        if (StringUtils.isEmpty(value)) {
            return new JSONObject();
        }
        JSONObject jsonObject = new JSONObject();
        TimerTypeEnum[] values = TimerTypeEnum.values();
        for (TimerTypeEnum timerTypeEnum : values) {
            if (value.equals(timerTypeEnum.getValue())) {
                jsonObject.put("data", timerTypeEnum.getFormat());
                jsonObject.put("display", timerTypeEnum.getFormat());
                return jsonObject;
            }
        }
        return jsonObject;
    }

    public static String getViewModeObject(String value) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        TimerTypeEnum[] values = TimerTypeEnum.values();
        for (TimerTypeEnum timerTypeEnum : values) {
            if (value.equals(timerTypeEnum.getValue())) {
                return timerTypeEnum.getViewMode();
            }
        }
        return "";
    }
}
