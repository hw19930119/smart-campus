/*
 * @(#) TzhxyPc
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-03 16:50:40
 */

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
 * 批次信息表
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@TableName("T_ZHXY_PC")
public class TzhxyPc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 批次号
     */
    @TableId("PC_NO")
    private String pcNo;

    /**
     * 批次名称
     */
    @TableField("PC_NAME")
    private String pcName;

    /**
     * 状态 0 待启用 1 启用 2 归档
     */
    @TableField("STATE")
    private String state;

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
