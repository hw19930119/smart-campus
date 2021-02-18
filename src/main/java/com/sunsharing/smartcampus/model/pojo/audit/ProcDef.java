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
 * 流程定义
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_SCF_PROCDEF")
public class ProcDef implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程定义id
     */
    @TableId("PD_ID")
    private String pdId;

    /**
     * 流程定义名称
     */
    @TableField("PD_NAME")
    private String pdName;

    /**
     * 流程定义CODE
     */
    @TableField("PD_CODE")
    private String pdCode;

    /**
     * 是否发布 0未发布 1发布
     */
    @TableField("PUBLISH")
    private String publish;

    /**
     * 发布时间
     */
    @TableField("PUBLISH_TIME")
    private Date publishTime;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("CREATE_PERSON")
    private String createPerson;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("UPDATE_PERSON")
    private String updatePerson;

    @TableField("DEL")
    private String del;


}
