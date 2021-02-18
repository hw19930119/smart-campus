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
 * 流程实例节点
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_SCF_AUDIT_RECORD")
public class AuditRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 流程实例ID
     */
    @TableField("PI_ID")
    private String piId;

    /**
     * 节点id
     */
    @TableField("NODE_ID")
    private String nodeId;

    /**
     * 11通过 12拒绝 13驳回补充
     */
    @TableField("RESULT")
    private String result;

    @TableField("BIZ_RESULT")
    private String bizResult;

    /**
     * 审批角色ID
     */
    @TableField("ROLE_ID")
    private String roleId;

    /**
     * 意见
     */
    @TableField("OPINON")
    private String opinon;

    /**
     * 审核用户id
     */
    @TableField("AUDIT_USER_ID")
    private String auditUserId;

    /**
     * 审核时间
     */
    @TableField("AUDIT_TIME")
    private Date auditTime;


}
