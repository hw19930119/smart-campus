/*
 * @(#) SchoolTypeEnum
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-04 18:37:46
 */

package com.sunsharing.smartcampus.constant.enums;

import com.sunsharing.share.common.base.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SchoolTypeEnum implements IEnum{

    小学("S01","小学"),
    初中("S02","初中"),
    高中("S03","高中"),
    中职("S04","中职"),
    九年制学校("S05","九年制学校"),
    其它("S06","其它");

    private String value;

    private String label;

}
