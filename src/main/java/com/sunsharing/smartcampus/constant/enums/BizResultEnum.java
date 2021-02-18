/*
 * @(#) FiledTypeEnum
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:41:51
 */

package com.sunsharing.smartcampus.constant.enums;

import com.sunsharing.share.common.base.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by wuxw on 2020/03/18
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum BizResultEnum implements IEnum {
    草稿("8"),
    已提交("9"),
    区教育局审核中("10"),
    初审通过("11"),
    初审不通过("12"),
    区教育局退回补充材料("13"),
    受理中心受理中("14"),
    受理中心已受理("15"),
    专家评审中("16"),
    专家已评审("17"),
    专家退回补充材料("18"),
    受理中心复审中("19"),
    受理中心复审通过("20"),
    受理中心复审不通过("21"),
    市教育局审批中("22"),
    审批通过("23"),
    审批不通过("24"),
    受理中心退回补充("25"),
    受理中心复审退回补充("26"),
    市教育局终审退回补充("27"),
    退回受理中心("28"),
    撤回已办("99");//审核端撤销
    private String value;


}
