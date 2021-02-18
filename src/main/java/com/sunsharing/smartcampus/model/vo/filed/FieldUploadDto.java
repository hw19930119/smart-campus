/*
 * @(#) FieldUploadDto
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 17:01:24
 */

package com.sunsharing.smartcampus.model.vo.filed;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by wuxw on 2020/5/25.
 */
@Data
public class FieldUploadDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(index = 0, value = "选项")
    @NotBlank(message = "选项")
    @ColumnWidth(22)
    private String name;

    @ExcelProperty(index = 1, value = "选项值")
    @NotBlank(message = "选项值")
    @ColumnWidth(22)
    private String value;

}
