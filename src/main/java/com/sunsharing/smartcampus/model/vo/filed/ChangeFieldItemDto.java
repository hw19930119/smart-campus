/*
 * @(#) ChangeFieldItemDto
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:58:41
 */

package com.sunsharing.smartcampus.model.vo.filed;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by wuxw on 2020/3/18.
 */
@Data
@Accessors(chain = true)
public class ChangeFieldItemDto {
    private String optionType; //选项类型
    private String dynamicData; //读取数据id
    private String inputFormat; //输入内容格式
    private List<FildItemSelectDto> itemList; //选项数据json
    private String characterSize; //字符长度
    private String size; //图片大小
    private String num; //图片张数
    private String imageTypeOption; //上传图片类型
    private String qtsclx; //上传文件其他类型
    private String fileTemplateAddress; //上传文件地址
    private String fileType; //上传文件类型
    private String fileTypeOption; //上传文件类型
    private String decimalPlaces; //小数点位数
    private String unit; //单位
    private String address; //地址库样式
    private String timer; //时间样式
    private String dynamicDataId; //时间样式
}
