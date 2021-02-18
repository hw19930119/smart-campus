/*
 * @(#) FieldConfigServiceImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:47:10
 */

package com.sunsharing.smartcampus.service.impl.filed;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.mapper.filed.FieldConfigMapper;
import com.sunsharing.smartcampus.model.pojo.filed.FieldConfig;
import com.sunsharing.smartcampus.model.vo.filed.GroupFieldsConfigDto;
import com.sunsharing.smartcampus.model.vo.filed.MaterialFieldDto;
import com.sunsharing.smartcampus.service.filed.FieldConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.log4j.Log4j;

/**
 * @author: cxy
 * @date: 2020-1-16 10:23:30
 * @description: 字段配置表管理业务逻辑层
 */
@Log4j
@Service(value = "fieldConfigService")
public class FieldConfigServiceImpl extends ServiceImpl<FieldConfigMapper, FieldConfig> implements FieldConfigService {

    private final FieldConfigMapper fieldConfigMapper;

    @Autowired
    public FieldConfigServiceImpl(FieldConfigMapper fieldConfigMapper) {
        this.fieldConfigMapper = fieldConfigMapper;
    }

    @Override
    public List<GroupFieldsConfigDto> findFieldGroupConfigByFields(List<String> fields) {

        return fieldConfigMapper.findFieldGroupConfigByFields(fields);
    }

    @Override
    public List<MaterialFieldDto> getMaterialFieldInfo(String policyId) {
        return fieldConfigMapper.getMaterialFieldInfo(policyId);
    }
}
