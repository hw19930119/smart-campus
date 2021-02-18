package com.sunsharing.smartcampus.model.pojo.comment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 点评专家配置信息表
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-10-29
 */
@Data
@Accessors(chain = true)
@TableName("T_ZHXY_DPCONFIG")
public class TzhxyDpConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("DP_ID")
    private String dpId;

    /**
     * 专家ID,关联T_SC_AUDIT_USER
     */
    @TableField("EXPERT_ID")
    private String expertId;

    /**
     * 所属批次
     */
    @TableField("PC_NO")
    private String pcNo;

    /**
     * 模板ID的集合
     */
    @TableField("TEMPLATE_ID")
    private String templateId;

    /**
     * 指标IDS,关联FIELD_CONFIG
     */
    @TableField("FIELD_IDS")
    private String fieldIds;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("CREATE_PERSON")
    private String createPerson;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField("UPDATE_PERSON")
    private String updatePerson;

    /**
     * 逻辑删除0有效 1 无效
     */
    @TableField("DEL")
    private String del;


}
