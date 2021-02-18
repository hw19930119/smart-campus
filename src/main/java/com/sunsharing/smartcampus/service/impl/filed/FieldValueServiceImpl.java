/*
 * @(#) FieldValueServiceImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-08-05 15:41:35
 */

package com.sunsharing.smartcampus.service.impl.filed;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.mapper.filed.FieldValueMapper;
import com.sunsharing.smartcampus.model.pojo.filed.FieldValue;
import com.sunsharing.smartcampus.service.audit.SupplyIndexService;
import com.sunsharing.smartcampus.service.filed.FieldValueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.extern.log4j.Log4j;

@Log4j
@Service(value = "fieldValueService")
public class FieldValueServiceImpl extends ServiceImpl<FieldValueMapper, FieldValue> implements FieldValueService {

    @Autowired
    FieldValueMapper fieldValueMapper;

    @Autowired
    SupplyIndexService supplyIndexService;

    @Override
    public void savaFieldValue(String categoryId, String schoolId, String value, String score, String message) {
        fieldValueMapper.savaFieldValue(categoryId, schoolId, value, score, message);
    }

    @Override
    public List<FieldValue> selectFieldValueBySchoolIdCategoryId(String g_id, String categoryId) {
        return fieldValueMapper.selectFieldValueBySchoolIdCategoryId(g_id, categoryId);
    }

    @Override
    public List<FieldValue> selectFieldValueBySchoolId(String g_id) {
        return fieldValueMapper.selectFieldValueBySchoolId(g_id);
    }

    @Override
    public void updateFieldValue(String id, String categoryId, String schoolId, String value, String score, String message, String supFiles) {
        fieldValueMapper.updateFieldValue(id, categoryId, schoolId, value, score, message, supFiles);
    }

    @Override
    public List<FieldValue> selectFieldValueByDeclareId(String g_id) {
        return fieldValueMapper.selectFieldValueByDeclareId(g_id);
    }

    @Override
    @Transactional
    public void saveAssessment(String categoryId, String declareId, String fieldValue, String score, String message, String supFiles) {
        List<FieldValue> fieldValueList = selectFieldValueBySchoolIdCategoryId(declareId, categoryId);
        if (!fieldValueList.isEmpty()) {
            String valueId = fieldValueList.get(0).getId();
            updateFieldValue(valueId, categoryId, declareId, fieldValue, score, message, supFiles);
        } else {
            savaFieldValue(categoryId, declareId, fieldValue, score, message);
        }
        supplyIndexService.updateIndexState(declareId, categoryId, "1");
    }

    @Override
    public Integer queryCheckFieldValueForDeclareId(String declareId) {

        return this.fieldValueMapper.queryCheckFieldValueForDeclareId(declareId);
    }

}
