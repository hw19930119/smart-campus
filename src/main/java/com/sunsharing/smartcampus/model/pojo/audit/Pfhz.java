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
 * 评分汇总表
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_SCF_PFHZ")
public class Pfhz implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("HZ_ID")
    private String hzId;

    /**
     * 业务主键关联BASEINFO
     */
    @TableField("BUSSINESS_KEY")
    private String bussinessKey;

    /**
     * 节点ID
     */
    @TableField("NODE_ID")
    private String nodeId;

    /**
     * 流程实列ID
     */
    @TableField("P_ID")
    private String pId;

    /**
     * 评分角色ID
     */
    @TableField("PF_ROLE")
    private String pfRole;

    /**
     * 评分人ID
     */
    @TableField("PF_PERSON_ID")
    private String pfPersonId;

    /**
     * 评分人姓名
     */
    @TableField("PF_PERSON_NAME")
    private String pfPersonName;

    /**
     * 评分人证件号
     */
    @TableField("PF_PERSON_IDNUM")
    private String pfPersonIdnum;

    /**
     * 总分
     */
    @TableField("PF_SUM")
    private Double pfSum;

    /**
     * 评分时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 变更过时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;


}
