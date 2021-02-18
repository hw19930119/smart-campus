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
 * 专家点评信息表
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-10-29
 */
@Data
@Accessors(chain = true)
@TableName("T_ZHXY_COMMENT")
public class TzhxyComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("COMMENT_ID")
    private String commentId;

    /**
     * 申报学校ID
     */
    @TableField("DECLARE_ID")
    private String declareId;

    /**
     * 评论内容
     */
    @TableField("DECLARE_TEXT")
    private String declareText;

    /**
     * 点评ID
     */
    @TableField("DP_ID")
    private String dpId;

    /**
     * 专家ID,关联T_SC_AUDIT_USER
     */
    @TableField("EXPERT_ID")
    private String expertId;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("CREATE_PERSON")
    private String createPerson;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField("UPDATE_PERSON")
    private String updatePerson;

    /**
     * 是否有效 0 有效 1 无效
     */
    @TableField("DEL")
    private String del;


}
