/*
 * @(#) AddressVo
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
 * @date: 2020/6/2
 * @description:
 */
@Data
@Accessors(chain = true)
public class AddressVo {

    private String pinyin;
    private String py;
    private String label;
    private String value;
}
