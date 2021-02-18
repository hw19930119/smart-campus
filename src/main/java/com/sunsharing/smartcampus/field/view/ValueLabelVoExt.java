/*
 * @(#) ValueLabelVoExt
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:33
 */

package com.sunsharing.smartcampus.field.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ValueLabelVoExt extends ValueLabelVo {
    /**
     * 字段中文名
     */
    private String fieldCnName;
    /**
     * 真实值
     */
    private String allValue;
    /**
     * 分组排序号
     */
    private String groupSort;

    /**
     * 控件类型
     */
    private String type;
}
