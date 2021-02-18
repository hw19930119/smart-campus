/*
 * @(#) CategoryConfig
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-07-30 12:01:17
 */

package com.sunsharing.smartcampus.model.pojo.filed;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("T_CATEGORY_CONFIG")
public class CategoryConfig {
    /**
     * 字段ID
     */
    @TableId(type = IdType.UUID)
    private String key;
    /**
     * 父级id
     */
    private String parentId;
    /**
     * 类型名称
     */
    private String title;
    /**
     * 排序字段
     */
    private String categoryOrder;

    /**
     * 是否有效,0有效,1无效
     */
    private String del;
    /**
     * 类型：0：目录，1：评分项目
     */
    private String categoryType;

    private String templateId;

    private String score;

    private double zpScore;

    private String state;

    /**
     * 创建时间
     */
    private java.util.Date createTime;
}
