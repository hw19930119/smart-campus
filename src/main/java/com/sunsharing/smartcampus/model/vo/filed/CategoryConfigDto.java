/*
 * @(#) CategoryConfigDto
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-07-30 14:15:18
 */

package com.sunsharing.smartcampus.model.vo.filed;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.field.FormField;
import com.sunsharing.smartcampus.model.pojo.filed.CategoryConfig;
import com.sunsharing.smartcampus.model.pojo.filed.FieldValue;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class CategoryConfigDto extends CategoryConfig{
    private List<CategoryConfigDto> children;

    private List<FormField> child;
    private String templateName;
    private  List<FieldValue> defaultValue;
    private String shState;
    private String giveback;
    private double auditScore;
    private double avgScore;
    private String ifEdit;
}
