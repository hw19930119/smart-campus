package com.sunsharing.smartcampus.model.pojo.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 模板信息表
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@TableName("T_ZHXY_TEMPLATE")
public class TzhxyTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板编号
     */
    @TableId("TEMPLATE_NO")
    private String templateNo;

    /**
     * 模板名称
     */
    @TableField("TEMPLATE_NAME")
    private String templateName;

    /**
     * 适用范围，学校类型DM_SCHOOL_TYPE
     */
    @TableField("TEMPLATE_TYPE")
    private String templateType;

    /**
     * 备注
     */
    @TableField("REMARKS")
    private String remarks;

    /**
     * 所属批次
     */
    @TableField("PC_ID")
    private String pcId;

    /**
     * 批次状态
     */
    @TableField("PC_STATE")
    private String pcState = "0";

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
     * 是否有效 0 有效 1 无效
     */
    @TableField("DEL")
    private String del = "0";


}
