/*
 * @(#) GroupFieldsConfigDto
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:53:45
 */

package com.sunsharing.smartcampus.model.vo.filed;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by wuxw on 2020/5/21.
 */
@Data
@Accessors(chain = true)
public class GroupFieldsConfigDto implements Serializable {

    private String name;//字段名
    private String id; //字段ID
    private String uniquenessFlag;// 唯一性标识： 1是
    private String showNameFlag; // 显示名称标识： 1是
    private String sort; // 排序号
}

