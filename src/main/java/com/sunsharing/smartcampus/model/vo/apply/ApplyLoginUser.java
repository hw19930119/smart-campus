/*
 * @(#) ApplyLoginUser
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-11 10:23:21
 */

package com.sunsharing.smartcampus.model.vo.apply;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApplyLoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 姓名 -- 一直都存在值
     */
    private String name;

    /**
     * 学校唯一标识，关联T_ZHXY_SCHOOL
     */
    private String unitCode;

    /**
     * 申报端账号审核状态，DM_ATTEST_STATE
     */
    private String state;

    /**
     * 认证材料
     */
    private String material;

    private String cityBelong;

    private String schoolType;

    private String schoolTypeName;

    private String schoolName;

    private String xzqh;

    private Boolean sftz = false;

    private String opinion;
}
