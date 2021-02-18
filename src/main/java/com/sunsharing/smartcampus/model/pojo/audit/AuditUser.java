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
 * 审核用户
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_SC_AUDIT_USER")
public class AuditUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 姓名
     */
    @TableField("NAME")
    private String name;

    /**
     * 身份证
     */
    @TableField("ID_NUM")
    private String idNum;

    /**
     * 角色id
     */
    @TableField("ROLE_ID")
    private String roleId;

    /**
     * 行政区划
     */
    @TableField("XZQH")
    private String xzqh;

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

    /**
     * 0不是 1是
     */
    @TableField("ADMIN")
    private String admin;


}
