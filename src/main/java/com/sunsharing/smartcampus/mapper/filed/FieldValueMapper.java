/*
 * @(#) FieldValueMapper
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-08-05 15:34:47
 */

package com.sunsharing.smartcampus.mapper.filed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.pojo.filed.FieldValue;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字段内容值
 */
@Repository
public interface FieldValueMapper extends BaseMapper<FieldValue> {
    void updateFieldValue(@Param("id") String id,
                          @Param("categoryId") String categoryId,
                          @Param("schoolId") String schoolId, @Param("value") String value,
                          @Param("score") String score,
                          @Param("message") String message,
                          @Param("supFiles") String supFiles);

    List<FieldValue> selectFieldValueBySchoolIdCategoryId(@Param("g_id") String g_id, @Param("categoryId") String categoryId);

    List<FieldValue> selectFieldValueBySchoolId(@Param("g_id") String g_id);

    void savaFieldValue(@Param("categoryId") String categoryId, @Param("schoolId") String schoolId, @Param("value") String value, @Param("score") String score,
                        @Param("message") String message);

    List<FieldValue> selectFieldValueByDeclareId(String g_id);

    Integer queryCheckFieldValueForDeclareId(@Param("declareId") String declareId);
}
