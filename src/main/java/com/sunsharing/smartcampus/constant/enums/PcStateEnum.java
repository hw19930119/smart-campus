/*
 * @(#) PcStateEnum
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-03 17:03:01
 */

package com.sunsharing.smartcampus.constant.enums;

import com.sunsharing.share.common.base.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PcStateEnum implements IEnum{
    待启用("0","待启用"),
    启用("1","启用"),
    归档("2","归档");

    private String value;

    private String label;

}
