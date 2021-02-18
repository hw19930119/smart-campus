/*
 * @(#) Input
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:33
 */

package com.sunsharing.smartcampus.field.view;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: cxy
 * @date: 2020/3/3
 * @description: 单文本输入框
 */
@Data
@Accessors(chain = true)
public class Input {

    /**
     * 字符长度
     */
    private String characterSize;
    /**
     * 内容格式
     */
    private String inputFormat;
    /**
     * 单位
     */
    private String unit;
    /**
     * 小数点位数
     */
    private String decimalPlaces;

}
