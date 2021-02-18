/*
 * @(#) DictInfo
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 17:30:47
 */

package com.sunsharing.smartcampus.model.pojo.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 */
@Data
@Accessors(chain = true)
public class DictInfo {

    /**
     * 字典ID
     */
    private String dictId;
    /**
     * 类别英文名称
     */
    private String classEnName;
    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 备注说明
     */
    private String remark;
    /**
     * 注销标识
     */
    private String zxbs;
    /**
     * 排序号
     */
    private String sort;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建人
     */
    private String createUserId;
    /**
     * 操作时间
     */
    private String updateTime;
    /**
     * 操作人
     */
    private String updateUserId;
}
