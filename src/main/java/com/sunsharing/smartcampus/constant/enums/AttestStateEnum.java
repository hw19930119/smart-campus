/*
 * @(#) AttestStateEnum
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-10 16:14:57
 */

package com.sunsharing.smartcampus.constant.enums;

import com.sunsharing.share.common.base.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AttestStateEnum implements IEnum {

    未认证("A01", "未认证"),

    认证中("A02", "认证中"),

    认证通过("A03", "认证通过"),

    认证不通过("A04", "认证不通过");

    private String value;

    private String label;
}
