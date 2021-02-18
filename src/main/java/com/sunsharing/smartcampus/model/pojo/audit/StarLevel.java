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
 * 节点评星
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_SCF_STAR_LEVEL")
public class StarLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("ID")
    private String id;

    /**
     * BUSSINESS_KEY
     */
    @TableField("BUSSINESS_KEY")
    private String bussinessKey;

    /**
     * 节点id
     */
    @TableField("NODE_ID")
    private String nodeId;

    /**
     * 评分用户id
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 评价分
     */
    @TableField("AV_SCORE")
    private Double avScore;

    /**
     * 星级
     */
    @TableField("STAR_LEVEL")
    private Integer starLevel;

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

    @TableField("REVIEW_MATERIALS")
    private String reviewMaterials;

}
