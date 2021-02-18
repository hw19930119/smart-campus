/*
 * @(#) FildItemSelectDto
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:56:16
 */

package com.sunsharing.smartcampus.model.vo.filed;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by wuxw on 2020/3/18.
 */
@Data
@Accessors(chain = true)
public class FildItemSelectDto implements Serializable {
    private String label;
    private String value;
}
