package com.sunsharing.smartcampus.model.pojo.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单配置
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_ZHXY_MENU")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    @TableField("ROLE_ID")
    private String roleId;

    @TableField("MENU_JSON")
    private String menuJson;

    @TableField("MODULE")
    private String module;

    @TableField("POWER")
    private String power;

    @TableField("DEL")
    private String del;


}
