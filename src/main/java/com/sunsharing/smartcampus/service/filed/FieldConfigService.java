/*
 * @(#) FieldConfigService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:46:58
 */

package com.sunsharing.smartcampus.service.filed;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.filed.FieldConfig;
import com.sunsharing.smartcampus.model.vo.filed.GroupFieldsConfigDto;
import com.sunsharing.smartcampus.model.vo.filed.MaterialFieldDto;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: cxy
 * @date: 2020-1-16 10:23:30
 * @description: 字段配置表管理业务逻辑接口
 */
public interface FieldConfigService extends IService<FieldConfig> {

    List<GroupFieldsConfigDto> findFieldGroupConfigByFields(List<String> fields);

    /**
     * 获取提交材料字段信息
     * @param policyId 政策id
     * @return {}
     */
    List<MaterialFieldDto> getMaterialFieldInfo(@Param("policyId") String policyId);
}
