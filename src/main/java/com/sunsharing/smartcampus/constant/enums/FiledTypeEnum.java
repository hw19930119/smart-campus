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
public enum FiledTypeEnum implements IEnum {

    单文本输入框("1", "单文本输入框"),
    多文本输入框("2", "多文本输入框"),
    图片上传("3", "图片上传"),
    单选框("4", "单选框"),
    多选框("5", "多选框"),
    下拉单选("6", "下拉单选"),
    下拉多选("7", "下拉多选"),
    附件上传("8", "附件上传"),
    数据读取("9", "数据读取"),
    地址库("10", "地址库"),
    时间("11", "时间");

    private String value;
    private String label;

}
