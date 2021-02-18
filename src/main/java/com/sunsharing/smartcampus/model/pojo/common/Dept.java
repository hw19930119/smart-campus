/*
 * @(#) Dept
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 11:08:48
 */

package com.sunsharing.smartcampus.model.pojo.common;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("T_POLICY_DEPT_INFO")
public class Dept {

    @TableId
    private String deptId;

    private String deptName;

    private String shortName;

    private String xzqh;

    private String upperDeptId;

    private String contacts;

    private String tel;

    private String remark;

    private Integer sort;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;

    private String zxbs;


}
