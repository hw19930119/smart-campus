/*
 * @(#) DataRead
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:33
 */

package com.sunsharing.smartcampus.field.view;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: cxy
 * @date: 2020/3/3
 * @description: 数据读取字段控件属性
 */
@Data
@Accessors(chain = true)
public class DataRead {

    /**
     * 动态数据ID
     */
    private String dynamicDataId;
}
