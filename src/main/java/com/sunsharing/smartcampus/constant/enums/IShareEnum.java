/*
 * @(#) IShareEnum
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 12:06:31
 */

package com.sunsharing.smartcampus.constant.enums;

public interface IShareEnum {
    /**
     * 获取枚举的值。（字符串）
     * 如：性别枚举中Man，值=1
     * @return 枚举值
     */
    String getValue();

    /**
     * 获取枚举的值。（字符串）
     * 如：性别枚举中Man，值=男
     * @return 枚举值
     */
    String getLabel();
}
