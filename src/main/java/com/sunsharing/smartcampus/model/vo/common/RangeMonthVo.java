/*
 * @(#) RangeMonthVo
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 12:12:25
 */

package com.sunsharing.smartcampus.model.vo.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019/2/25.
 */
@Data
@Accessors(chain = true)
public class RangeMonthVo {
    private String startMonth; //开始月份
    private String endMonth; //结束月份
    private String endDay; //结束日期
}
