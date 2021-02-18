/*
 * @(#) ExcelCheckErrDto
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-18 10:49:00
 */

package com.sunsharing.smartcampus.model.vo.filed;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by wuxw on 2020/4/4.
 */
@Data
@Accessors(chain = true)
public class ExcelCheckErrDto<T> {

    private T obj;

    private String errMsg;

    public ExcelCheckErrDto() {
    }

    public ExcelCheckErrDto(T obj, String errMsg) {
        this.obj = obj;
        this.errMsg = errMsg;
    }

}
