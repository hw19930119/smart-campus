/*
 * @(#) Zxbs
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 12:00:03
 */

package com.sunsharing.smartcampus.constant.constants;

import com.sunsharing.share.common.base.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 删除枚举类
 * @author yope
 * @date 2018/8/19
 */

@Getter
@AllArgsConstructor
public enum Zxbs implements IEnum {
    /**
     * 已删除
     */
    deleted("1"),
    /**
     * 未删除 （即存在）
     */
    exist("0");
    private String value;

    @Override
    public String getValue() {
        return this.value;
    }

}
