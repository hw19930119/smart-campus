/*
 * @(#) MaterialFieldDto
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:53:03
 */

package com.sunsharing.smartcampus.model.vo.filed;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 补交材料字段信息
 * @author cjh
 */
@Data
@Accessors(chain = true)
public class MaterialFieldDto {
    /**
     * 分组名
     */
    private String groupName;
    /**
     * 分组id
     */
    private String groupId;
    /**
     * 字段中文名
     */
    private String fieldCnName;
    /**
     * 字段id
     */
    private String fieldId;
    /**
     * 父级分组id
     */
    private String upperGroupId;
}
