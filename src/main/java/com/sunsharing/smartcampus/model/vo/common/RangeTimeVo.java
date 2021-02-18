/*
 * @(#) RangeTimeVo
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 12:12:26
 */

package com.sunsharing.smartcampus.model.vo.common;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RangeTimeVo {
    private LocalDateTime startTime;//开始时间
    private LocalDateTime endTime;//结束时间
}
