/*
 * @(#) DeptService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 11:03:13
 */

package com.sunsharing.smartcampus.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.common.Dept;

import java.util.List;
import java.util.Map;

/**
 * @author: cxy
 * @date: 2020/3/18
 * @description:
 */
public interface DeptService extends IService<Dept> {

    @Override
    List<Dept> list();

    List<Map<String, String>> findSelectList();

    List<Map<String, String>> getAuthDeptList();

    String findAuthDeptNameById(String deptId);

}
