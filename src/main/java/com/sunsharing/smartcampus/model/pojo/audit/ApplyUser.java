package com.sunsharing.smartcampus.model.pojo.audit;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 申报用户信息表
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("T_SC_APPLY_USER")
public class ApplyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;

    /**
     * 账号身份证
     */
    @TableField("ID_NUM")
    private String idNum;

    /**
     * 登录用户手机号
     */
    @TableField("MOBILE_NUM")
    private String mobileNum;

    /**
     * 学校唯一标识，关联T_ZHXY_SCHOOL
     */
    @TableField("UNIT_CODE")
    private String unitCode;

    /**
     * 学校类型DM_SCHOOL_TYPE（审批时缓存）
     */
    @TableField("SCHOOL_TYPE")
    private String schoolType;

    /**
     * 学校类型名称（审批时缓存）
     */
    @TableField("SCHOOL_TYPE_NAME")
    private String schoolTypeName;

    /**
     * 认证材料
     */
    @TableField("MATERIAL")
    private String material;

    /**
     * 申报端账号审核状态，DM_ATTEST_STATE
     */
    @TableField("STATE")
    private String state;

    /**
     * 提交审核时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 审核时间
     */
    @TableField("REVIEW_TIME")
    private Date reviewTime;

    /**
     * 审核人
     */
    @TableField("REVIEW_PERSON")
    private String reviewPerson;

    /**
     * 审批意见
     */
    @TableField("OPINION")
    private String opinion;

    /**
     * 修改时间，修改时更新
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("DEL")
    private String del;


}
