/*
 * @(#) FieldValueService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-08-05 15:41:00
 */

package com.sunsharing.smartcampus.service.filed;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.filed.FieldValue;

import java.util.List;

public interface FieldValueService extends IService<FieldValue> {
    void savaFieldValue(String categoryId, String schoolId, String value, String score, String message);

    List<FieldValue> selectFieldValueBySchoolIdCategoryId(String g_id, String categoryId);

    List<FieldValue> selectFieldValueBySchoolId(String g_id);

    void updateFieldValue(String id, String categoryId, String schoolId, String value, String score, String message, String supFiles);

    List<FieldValue> selectFieldValueByDeclareId(String g_id);

    void saveAssessment(String categoryId, String declareId, String fieldValue, String score, String message, String supFiles);

    Integer queryCheckFieldValueForDeclareId(String declareId);
}
