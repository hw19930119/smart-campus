/*
 * @(#) ExcelCheckManager
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-18 10:16:33
 */

package com.sunsharing.smartcampus.service.filed;


import com.sunsharing.smartcampus.model.vo.filed.ExcelCheckResult;

import java.util.List;

/**
 * Created by wuxw on 2020/4/4.
 */
public interface ExcelCheckManager<T> {

    /**
     * @description: 校验方法
     */
    ExcelCheckResult checkImportExcel(List<T> objects);
}
