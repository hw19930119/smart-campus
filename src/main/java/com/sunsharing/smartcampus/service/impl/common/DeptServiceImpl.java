/*
 * @(#) DeptServiceImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 11:07:59
 */

package com.sunsharing.smartcampus.service.impl.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.constant.constants.Zxbs;
import com.sunsharing.smartcampus.mapper.common.DeptMapper;
import com.sunsharing.smartcampus.model.pojo.common.Dept;
import com.sunsharing.smartcampus.service.common.DeptService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;

/**
 * @author: cxy
 * @date: 2020/3/18
 * @description:
 */
@Log4j
@Service(value = "deptService")
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    //集美行政区划简写
    public static final String JM_XZQH = "350211";

    @Override
    public List<Dept> list() {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dept::getZxbs, Zxbs.exist.getValue());
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Map<String, String>> findSelectList() {
        return this.baseMapper.findSelectList();
    }

    @Override
    public List<Map<String, String>> getAuthDeptList() {
        return this.baseMapper.getAuthDeptList(JM_XZQH);
    }

    @Override
    public String findAuthDeptNameById(String deptId) {
        return this.baseMapper.findAuthDeptNameById(deptId);
    }

}
