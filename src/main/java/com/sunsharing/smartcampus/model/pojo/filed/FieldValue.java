/*
 * @(#) FieldValue
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-08-05 15:35:55
 */

package com.sunsharing.smartcampus.model.pojo.filed;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("T_FIELD_VALUE")
public class FieldValue {

    @TableId("ID")
    private String id;
    /**
     * 类型id
     */
    @TableField("CATEGORY_ID")
    private String categoryId;
    /**
     * 内容
     */
    @TableField("FIELD_CONTENT")
    private String fieldContent;

    @TableField("SUP_FILES")
    private String supFiles;

    @TableField("CREATE_TIME")
    private String createTime;

    @TableField("CREATE_PERSON")
    private String createPerson;

    @TableField("UPDATE_TIME")
    private String updateTime;

    @TableField("UPDATE_PERSON")
    private String updatePerson;

    @TableField("DEL")
    private String del = "0";

    @TableField("DECLARE_ID")
    private String schoolId;

    @TableField("ZP_SCORE")
    private String zpScore;

    /**
     * 0 已自评 1 未自评
     */
    @TableField("STATE")
    private String state;

    @TableField("MESSAGE")
    private String message;

    @TableField(exist = false)
    private String codeKey;

    @TableField(exist = false)
    private String codeLabel;
}
