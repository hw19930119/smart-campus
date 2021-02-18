package com.sunsharing.smartcampus.model.pojo.audit;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 审核端评分记录表
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_SCF_SCORE")
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableField("ID")
    private String id;

    /**
     * 关联baseInfo表主键id
     */
    @TableField("BUSSINESS_KEY")
    private String bussinessKey;

    /**
     * 类型id关联T_CATEGORY_CONFIG表主键id
     */
    @TableField("CATEGORY_ID")
    private String categoryId;

    /**
     * 字段内容
     */
    @TableField("FIELD_CONTENT")
    private String fieldContent;

    /**
     * 自评分数
     */
    @TableField("ZP_SCORE")
    private Double zpScore;

    /**
     * 审批意见
     */
    @TableField("OPINION")
    private String opinion;

    /**
     * 是否自评状态 0 已自评 1 未自评
     */
    @TableField("STATE")
    private String state;

    /**
     * 是否退回 0否 1是
     */
    @TableField("GIVE_BACK")
    private String giveBack;

    /**
     * 审核时间
     */
    @TableField("AUDIT_TIME")
    private Date auditTime;

    /**
     * 审核账号，关联审批端账号表ID
     */
    @TableField("AUDIT_PERSON")
    private String auditPerson;

    /**
     * 审核人角色ID
     */
    @TableField("AUDIT_ROLE")
    private String auditRole;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 是否是历史 0否 1是
     */
    @TableField("HISTORY_FLAG")
    private String historyFlag;

    /**
     * 是否有效 0 有效 1 无效
     */
    @TableField("DEL")
    private String del;


}
