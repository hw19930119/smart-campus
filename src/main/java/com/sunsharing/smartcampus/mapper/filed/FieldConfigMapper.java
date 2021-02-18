/*
 * @(#) FieldConfigMapper
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:48:53
 */

package com.sunsharing.smartcampus.mapper.filed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.pojo.filed.CategoryConfig;
import com.sunsharing.smartcampus.model.pojo.filed.FieldConfig;
import com.sunsharing.smartcampus.model.vo.filed.GroupFieldsConfigDto;
import com.sunsharing.smartcampus.model.vo.filed.MaterialFieldDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: cxy
 * @date: 2020-1-16 10:23:30
 * @description: 字段配置表管理数据访问层
 */
@Repository
public interface FieldConfigMapper extends BaseMapper<FieldConfig> {

    List<GroupFieldsConfigDto> findFieldGroupConfigByFields(List<String> fields);

    /**
     * 获取可提交材料字段信息
     * @param policyId 政策id
     * @return {}
     */
    List<MaterialFieldDto> getMaterialFieldInfo(@Param("policyId") String policyId);

    List<FieldConfig> selectByConfig(@Param("categoryConfigs") List<CategoryConfig> categoryConfigs);

   void addField(@Param("key")String key,@Param("categoryId") String categoryId,@Param("name") String name,
                 @Param("score") String score);

   void deleteFieldById(@Param("key") String key);

   String selectTemplateNameById(@Param("templateId") String templateId);

    void insertBatch(@Param("fieldConfigs")List<FieldConfig> fieldConfigs);
}
