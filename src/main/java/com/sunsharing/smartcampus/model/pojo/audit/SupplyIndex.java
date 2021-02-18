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
 * 需要补充的指标
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_SCF_SUPPLY_INDEX")
public class SupplyIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("ID")
    private String id;

    /**
     * 业务ID
     */
    @TableField("BUSSINESS_KEY")
    private String bussinessKey;

    /**
     * 主指标ID
     */
    @TableField("MAIN_INDEX_ID")
    private String mainIndexId;

    /**
     * 主指标综合意见
     */
    @TableField("OPINON")
    private String opinon;

    /**
     * 0可修改 1可以查看（申请端提交后，就变成审核端查看，再次评分就删除）
     */
    @TableField("STATUS")
    private String status;

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


}
