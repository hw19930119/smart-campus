/*
 * @(#) FiledRequired
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-24 14:44:33
 */

package com.sunsharing.smartcampus.constant.enums;


import com.sunsharing.share.common.base.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum FiledRequiredEnum implements IEnum{

    必填("0","必填",true),
    不必填("1","非必填",false);


    private String value;

    private String label;

    private Boolean falg;
}
