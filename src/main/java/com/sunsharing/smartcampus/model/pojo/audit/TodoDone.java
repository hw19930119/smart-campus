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
 * 待办已办表
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_SCF_TODO_DONE")
public class TodoDone implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 流程实例ID
     */
    @TableField("PI_ID")
    private String piId;

    /**
     * 角色id：已办存储，待办存储
     */
    @TableField("ROLE_ID")
    private String roleId;

    /**
     * 用户id：已办存储
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 0待办 1已办
     */
    @TableField("TYPE")
    private String type;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("CREATE_PERSON")
    private String createPerson;


}
