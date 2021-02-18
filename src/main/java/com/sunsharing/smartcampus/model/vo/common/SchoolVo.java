/*
 * @(#) ShoolVo
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-11 16:18:25
 */

package com.sunsharing.smartcampus.model.vo.common;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SchoolVo implements Serializable {

    private String label;

    private String id;

    private String xzqh;

    private String schoolType;

    private String schoolTypeName;
}
