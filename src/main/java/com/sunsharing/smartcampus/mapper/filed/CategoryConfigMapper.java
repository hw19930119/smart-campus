/*
 * @(#) CategoryConfigMapper
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-07-30 12:05:16
 */

package com.sunsharing.smartcampus.mapper.filed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.pojo.filed.CategoryConfig;
import com.sunsharing.smartcampus.model.pojo.filed.FieldConfig;
import com.sunsharing.smartcampus.model.vo.filed.CategoryConfigDto;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CategoryConfigMapper extends BaseMapper<CategoryConfig> {

    CategoryConfigDto selectCategoryById(String key);

    void updateCategoryState(String categoryId);

    List<CategoryConfigDto> seelctAllCategory(@Param("templateId") String templateId,
                                              @Param("categoryIdList") List<String> categoryIdList);

    List<CategoryConfig> selectByTemplateId(@Param("templateId") String templateId);

    void addCategory(@Param("key") String key,@Param("templateId") String templateId,
                                    @Param("categoryId") String categoryId,
                                    @Param("type") String type, @Param("name") String name,
                                    @Param("score") String score);

    void insertBatch(@Param("configList") List<CategoryConfig> configList);

   void updateCategory(@Param("categoryId") String categoryId, @Param("name") String name,
                       @Param("score") String score);

   void deleteById(@Param("key") String key);

   void delById(@Param("templateId") String templateId);

   void updateCategoryStateById(@Param("id") String id,@Param("zpScore") String zpScore);

   List<CategoryConfig> getCategoryByTemplateId(String templateId);
}
