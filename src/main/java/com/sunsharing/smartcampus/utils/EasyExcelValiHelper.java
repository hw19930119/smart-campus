/*
 * @(#) EasyExcelValiHelper
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-18 10:55:24
 */

package com.sunsharing.smartcampus.utils;

import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.reflect.Field;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;


/**
 * 根据实体类中的注解来通过正则表达式判断当前单元格内的数据是否符合标准
 * Created by wuxw on 2020/4/4.
 */
public class EasyExcelValiHelper {
    private EasyExcelValiHelper() {
    }

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> String validateEntity(T obj) throws NoSuchFieldException {
        StringBuilder result = new StringBuilder();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (set != null && !set.isEmpty()) {
            for (ConstraintViolation<T> cv : set) {
                Field declaredField = obj.getClass().getDeclaredField(cv.getPropertyPath().toString());
                ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
                //拼接错误信息，包含当前出错数据的标题名字+错误信息
                result.append(annotation.value()[0] + cv.getMessage()).append(";");
            }
        }
        return result.toString();
    }

}
