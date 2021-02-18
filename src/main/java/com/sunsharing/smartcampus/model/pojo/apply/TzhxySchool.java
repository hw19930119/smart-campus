package com.sunsharing.smartcampus.model.pojo.apply;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 内置学校基础信息
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-10
 */
@Data
@Accessors(chain = true)
@TableName("T_ZHXY_SCHOOL")
public class TzhxySchool implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学校唯一标识
     */
    @TableId("UNIT_CODE")
    private String unitCode;

    /**
     * 学校名称
     */
    @TableField("SCHOOL_NAME")
    private String schoolName;

    /**
     * 所属行政区划
     */
    @TableField("XZQH")
    private String xzqh;

    /**
     * 是否市属 0否 1是
     */
    @TableField("CITY_BELONG")
    private String cityBelong;

    /**
     * 学校类型DM_SCHOOL_TYPE
     */
    @TableField("SCHOOL_TYPE")
    private String schoolType;

    /**
     * 学校类型名称
     */
    @TableField("SCHOOL_TYPE_NAME")
    private String schoolTypeName;

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
