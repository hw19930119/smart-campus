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
 * 角色
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_SC_ROLE")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ROLE_ID")
    private String roleId;

    /**
     * 角色名称
     */
    @TableField("NAME")
    private String name;

    @TableField("CODE")
    private String code;
    /**
     * 是否按行政区划过滤待办数据 0否 1是
     */
    @TableField("FILTER_DATA")
    private String filterData;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("CREATE_PERSON")
    private String createPerson;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("UPDATE_PERSON")
    private String updatePerson;

    /**
     * 逻辑删除
     */
    @TableField("DEL")
    private String del;


}
