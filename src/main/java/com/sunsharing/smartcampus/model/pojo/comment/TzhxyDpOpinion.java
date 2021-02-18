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
 * 末级指标点评详情
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-11-04
 */

@Data
@Accessors(chain = true)
@TableName("T_ZHXY_DPOPINION")
public class TzhxyDpOpinion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 专家末级指标点评ID
     */
    @TableId("OPINION_ID")
    private String opinionId;

    /**
     * 申报ID
     */
    @TableField("DECLARE_ID")
    private String declareId;

    /**
     * 点评配置的T_ZHXY_CONFIG_ID
     */
    @TableField("DP_ID")
    private String dpId;

    /**
     * 点评汇总T_ZHXY_COMMENT的ID
     */
    @TableField("COMMENT_ID")
    private String commentId;

    /**
     * 自评T_FIELD_VALUE表ID
     */
    @TableField("FIELD_ID")
    private String fieldId;

    /**
     * 专家ID
     */
    @TableField("EXPERT_ID")
    private String expertId;

    /**
     * 点评DM_DP_OPINION的ID
     */
    @TableField("BH0000")
    private String bh0000;

    /**
     * 点评内容
     */
    @TableField("MC0000")
    private String mc0000;

    /**
     * 点评
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
     * 是否有效0有效1无效
     */
    @TableField("DEL")
    private String del;


}
