package com.sunsharing.smartcampus.model.pojo.audit;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 流程实例
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_SCF_PROCINST")
public class ProcInst implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程实例ID
     */
    @TableId("PI_ID")
    private String piId;

    /**
     * 流程定义id
     */
    @TableField("PD_ID")
    private String pdId;

    /**
     * 业务id
     */
    @TableField("BUSSINESS_KEY")
    private String bussinessKey;

    /**
     * 当前节点ID
     */
    @TableField("CUR_NODE_ID")
    private String curNodeId;

    /**
     * 10审核中 11通过 12拒绝 13驳回补充
     */
    @TableField("CUR_RESULT")
    private String curResult;

    /**
     * 申报用户id
     */
    @TableField("COMMIT_USER_ID")
    private String commitUserId;

    /**
     * 申报时间
     */
    @TableField("COMMIT_TIME")
    private Date commitTime;

    /**
     * 申报学校ID
     */
    @TableField("COMMIT_SCHOOL_ID")
    private String commitSchoolId;

    /**
     * 9已提交 10 审核中 11通过 12拒绝 13驳回补充 14撤销
     */
    @TableField("FINAL_RESULT")
    private String finalResult;

    /**
     * 流程变量存储
     */
    @TableField("VAR_JSON")
    private String varJson;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("CREATE_PERSON")
    private String createPerson;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("UPDATE_PERSON")
    private String updatePerson;


}
