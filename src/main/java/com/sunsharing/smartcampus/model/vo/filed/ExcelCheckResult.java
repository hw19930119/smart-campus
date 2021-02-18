/*
 * @(#) ExcelCheckResult
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-18 10:48:32
 */

package com.sunsharing.smartcampus.model.vo.filed;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @description: excel数据导入结果
 * Created by wuxw on 2020/4/4.
 */
@Data
public class ExcelCheckResult<T> {

    private List<T> successData;

    private List<ExcelCheckErrDto<T>> errData;

    public ExcelCheckResult(List<T> successData, List<ExcelCheckErrDto<T>> errData) {
        this.successData = successData;
        this.errData = errData;
    }

    public ExcelCheckResult(List<ExcelCheckErrDto<T>> errData) {
        this.successData = new ArrayList<>();
        this.errData = errData;
    }

}
