/*
 * @(#) DeptMapper
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 11:09:33
 */

package com.sunsharing.smartcampus.mapper.common;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.pojo.common.Dept;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DeptMapper extends BaseMapper<Dept> {

    List<Map<String, String>> findSelectList();

    List<Map<String, String>> getAuthDeptList(@Param("xzqh") String xzqh);

    String findAuthDeptNameById(String deptId);

    /**
     * 批量查询网格用户信息(暂时放这里)
     * @param deptId 部门id
     * @return
     */
    List<Map> batchQueryUserInfo(@Param("list") List<String> list);
}
