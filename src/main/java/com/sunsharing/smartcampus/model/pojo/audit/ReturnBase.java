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
 * 专家退回的申报
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("T_SCF_RETURN_BASE")
public class ReturnBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 申报ID
     */
    @TableField("DECLARE_ID")
    private String declareId;

    @TableField("CREATE_TIME")
    private Date createTime;


}
