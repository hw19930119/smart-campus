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
 * 流程定义节点
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_SCF_PDNODE")
public class PdNode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 节点id
     */
    @TableId("NODE_ID")
    private String nodeId;

    /**
     * 流程定义id
     */
    @TableField("PD_ID")
    private String pdId;

    /**
     * 节点名称
     */
    @TableField("NODE_NAME")
    private String nodeName;

    /**
     * 前一个节点ID,start
     */
    @TableField("PRE_NODE_ID")
    private String preNodeId;

    /**
     * 下一个节点ID,end
     */
    @TableField("NEXT_NODE_ID")
    private String nextNodeId;

    /**
     * 节点唯一标识
     */
    @TableField("NODE_IDENTIFIER")
    private String nodeIdentifier;

    /**
     * 角色id
     */
    @TableField("ROLE_ID")
    private String roleId;

    /**
     * 顺序值
     */
    @TableField("SEQ_NUM")
    private Integer seqNum;

    /**
     * 审批按钮选项JSON
     */
    @TableField("OTPTION")
    private String otption;

    /**
     * 选项对应的业务状态JSON
     */
    @TableField("BIZ_OTPTION")
    private String bizOtption;

    /**
     * 跳过表达式
     */
    @TableField("SKIP_EXPRESSION")
    private String skipExpression;

    /**
     * 是否会签
     */
    @TableField("COUNTER_SIGN")
    private String counterSign;

    /**
     * 评分按钮 0没有 1详情 2评分
     */
    @TableField("SCORE_BUTTON")
    private String scoreButton;

    /**
     * 在某个选项上是否必须完成评分
     */
    @TableField("MUST_SCORE_ON_OPTION")
    private String mustScoreOnOption;

    /**
     * 是否存储评星平均分 0否 1是
     */
    @TableField("SAVE_AV_SCORE")
    private String saveAvScore;

    /**
     * 评星平均分标题
     */
    @TableField("AV_SCORE_LABEL")
    private String avScoreLabel;

    /**
     * 评星方式 0不要 1手动 2自动
     */
    @TableField("STAR_LEVEL_WAY")
    private String starLevelWay;

    /**
     * 评星标题
     */
    @TableField("STAR_LEVEL_LABEL")
    private String starLevelLabel;

    /**
     * 是否选择下一步用户 0否 1是
     */
    @TableField("SELECT_NEXT_USER")
    private String selectNextUser;

    /**
     * 下一步用户候选角色
     */
    @TableField("NEXT_USER_ROLE")
    private String nextUserRole;

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
