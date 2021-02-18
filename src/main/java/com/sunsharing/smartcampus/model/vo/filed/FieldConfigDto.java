/*
 * @(#) FieldConfigDto
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 17:16:30
 */

package com.sunsharing.smartcampus.model.vo.filed;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: cxy
 * @date: 2020/3/3
 * @description:
 */
@Data
@Accessors(chain = true)
public class FieldConfigDto {
    private String score;
    /**
     * 字段ID
     */
    private String fieldId;
    /**
     * 所属分类
     */
    private String fieldCategory;
    /**
     * 中文名称
     */
    private String fieldCnName;
    /**
     * 英文名称
     */
    private String fieldEnName;
    /**
     * 字段控件
     */
    private String fieldControl;
    /**
     * 提示语
     */
    private String promptMsg;
    /**
     * 字段配置json
     */
    private String fieldConfigJson;
    /**
     * 字段配置json
     */
    private String tabKey;
    /**
     * 是否必填 0 必填 1 不必填
     */
    private String fieldRequired;
}
