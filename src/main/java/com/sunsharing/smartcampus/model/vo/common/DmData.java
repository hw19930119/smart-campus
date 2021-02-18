/*
 * @(#) DmData
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:56:12
 */

package com.sunsharing.smartcampus.model.vo.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by wuxw on 2020/5/21.
 */
@Data
@Accessors(chain = true)
public class DmData {

    private String code;
    private String name;
}
