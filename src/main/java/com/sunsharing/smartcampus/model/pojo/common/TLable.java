/*
 * @(#) TLable
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-08-17 10:50:19
 */

package com.sunsharing.smartcampus.model.pojo.common;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("T_ZHXY_LABLE")
public class TLable implements Serializable {
    private String id;

    private String name;

    private String createTime;

    private String createPerson;

    private String updateTime;

    private String updatePerson;

    private String del;
}
