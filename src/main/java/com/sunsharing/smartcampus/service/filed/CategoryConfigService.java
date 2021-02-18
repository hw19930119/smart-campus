/*
 * @(#) CategoryConfigService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-07-30 11:58:00
 */

package com.sunsharing.smartcampus.service.filed;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyTemplate;
import com.sunsharing.smartcampus.model.pojo.filed.CategoryConfig;
import com.sunsharing.smartcampus.model.vo.filed.CategoryConfigDto;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

public interface CategoryConfigService {
    void updateCategoryState(String categoryId);

    List<CategoryConfigDto> selectAllCategory(String templateId,List<String> categoryIdList);

    Map findBasicFieldList(String templateId);

    void addCategory(String key,String templateId,String categoryId,String type,String name,String score);

    void updateCategory(String categoryId,String name,String score);

    void deleteCategory(JSONObject jsonObject);

    void updateCategoryStateById(String id,String zpScore);

    Pair<Boolean, TzhxyTemplate> calculateCategoryScore(String templateId);

    List<CategoryConfig> getCategoryByTemplateId(String templateId);
}
