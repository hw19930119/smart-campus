/*
 * @(#) GroupFieldConfigVo
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 17:01:24
 */

package com.sunsharing.smartcampus.model.vo.filed;

import com.google.common.collect.Lists;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by wuxw on 2020/5/21.
 */
@Data
@Accessors(chain = true)
public class GroupFieldConfigVo {

    private List<String> name = Lists.newArrayList();//字段名
    private List<String> id = Lists.newArrayList(); //字段ID
    private List<String> uniquenessFlag = Lists.newArrayList();// 唯一性标识： 1是
    private List<String> showNameFlag = Lists.newArrayList(); // 显示名称标识： 1是
    private List<String> sort = Lists.newArrayList(); // 排序号
}
