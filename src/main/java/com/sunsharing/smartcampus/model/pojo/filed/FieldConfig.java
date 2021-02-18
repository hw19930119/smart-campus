/*
 * @(#) FieldConfig
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:43:57
 */

package com.sunsharing.smartcampus.model.pojo.filed;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: cxy
 * @date: 2020-1-16 10:23:30
 * @description: 字段配置表实体
 */
@Data
@Accessors(chain = true)
@TableName("T_POLICY_CG_FIELD_CONFIG")
public class FieldConfig {

    /**
     * 字段ID
     */
    @TableId(type = IdType.UUID)
    private String fieldId;
    /**
     * 所属分类
     */
    private String fieldCategory;
    /**
     * 中文名称
     */
    private String fieldCnName;
    /**
     * 英文名称
     */
    private String fieldEnName;
    /**
     * 字段控件
     */
    private String fieldControl;
    /**
     * 提示语
     */
    private String promptMsg;
    /**
     * 字段配置json
     */
    private String fieldConfigJson;
    /**
     * 入库标识 0 是 1：否
     */
    private String inStockFlag;
    /**
     * 注销标识1：注销
     */
    private String zxbs;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 创建人
     */
    private String createUserId;
    /**
     * 操作时间
     */
    private java.util.Date updateTime;
    /**
     * 操作人
     */
    private String updateUserId;
    /**
     * 创建部门
     */
    private String createDeptId;

    /**
     * 是否必填 0 必填 1 不必填
     */
    private String fieldRequired;

    /**
     * 分数
     */
    private String score;

}
