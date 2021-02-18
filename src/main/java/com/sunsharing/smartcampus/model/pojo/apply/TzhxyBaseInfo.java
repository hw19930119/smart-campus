/*
 * @(#) TzhxyBaseInfo
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-10 15:16:31
 */

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
 * 学校申报信息
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-10
 */
@Data
@Accessors(chain = true)
@TableName("T_ZHXY_BASEINFO")
public class TzhxyBaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 申报主键ID
     */
    @TableId("DECLARE_ID")
    private String declareId;

    /**
     * 批次号
     */
    @TableField("PC_NO")
    private String pcNo;

    /**
     * 学校统一信用代码
     */
    @TableField("UNIT_CODE")
    private String unitCode;

    /**
     * 学校名称
     */
    @TableField("SCHOOL_NAME")
    private String schoolName;

    /**
     * 学校类型关联DM_SCHOOL_TYPE
     */
    @TableField("SCHOOL_TYPE")
    private String schoolType;

    /**
     * 学校类型名称
     */
    @TableField("SCHOOL_TYPE_NAME")
    private String schoolTypeName;

    /**
     * 星级
     */
    @TableField("STAR_LEVEL")
    private String starLevel;

    /**
     * 行政区划
     */
    @TableField("XZQH")
    private String xzqh;

    /**
     * 状态 关联DM_BIZ_RESULT
     */
    @TableField("STATE")
    private String state;

    /**
     * 上一次状态
     */
    @TableField("LAST_STATE")
    private String lastState;

    /**
     * 通信地址
     */
    @TableField("CONTACT_ADDRESS")
    private String contactAddress;

    /**
     * 邮编
     */
    @TableField("POST_CODE")
    private String postCode;

    /**
     * 学校网址
     */
    @TableField("SCHOOL_WEBSITE")
    private String schoolWebsite;

    /**
     * 学校校长
     */
    @TableField("SHCOOL_XZ")
    private String shcoolXz;

    /**
     * 学校联系电话
     */
    @TableField("SHCOOL_PHONE")
    private String shcoolPhone;

    /**
     * 分管领导姓名
     */
    @TableField("LD_NAME")
    private String ldName;

    /**
     * 分管领导职务
     */
    @TableField("LD_ZW")
    private String ldZw;

    /**
     * 分管领导电话
     */
    @TableField("LD_PHONE")
    private String ldPhone;

    /**
     * 分管领导邮箱
     */
    @TableField("LD_DZYX")
    private String ldDzyx;

    /**
     * 工作联系人姓名
     */
    @TableField("LXR_NAME")
    private String lxrName;

    /**
     * 工作联系人职务
     */
    @TableField("LXR_ZW")
    private String lxrZw;

    /**
     * 联系人电话
     */
    @TableField("LXR_PHONE")
    private String lxrPhone;

    /**
     * 电子邮箱
     */
    @TableField("LXR_DZYX")
    private String lxrDzyx;

    /**
     * 申报重点DM_SCHOOL_TYPE
     */
    @TableField("SBZD")
    private String sbzd;

    /**
     * 学生总数
     */
    @TableField("STUDENT_COUNT")
    private String studentCount;

    /**
     * 教职工总数
     */
    @TableField("FACULTY_COUNT")
    private String facultyCount;

    /**
     * 教学班数
     */
    @TableField("CLASS_COUNT")
    private String classCount;

    /**
     * 高级教师总数
     */
    @TableField("GJJS_COUNT")
    private String gjjsCount;

    /**
     * 提交时间
     */
    @TableField("COMMIT_TIME")
    private String commitTime;

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
     * 状态 1 正在使用 2 归档
     */
    @TableField("PC_STATE")
    private String pcState;

    /**
     * 是否有效 0 有效 1 无效
     */
    @TableField("DEL")
    private String del;


}
