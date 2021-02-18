/*
 * @(#) IFieldProduct
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:33
 */

package com.sunsharing.smartcampus.field;

import com.sunsharing.smartcampus.model.vo.filed.FieldConfigDto;

import java.util.List;

/**
 * @author: cxy
 * @date: 2020-03-03 9:21:50
 * @description: 字段生成接口
 */
public interface IFieldProduct {

    String BEAN_PREFIX = "FIELD:";

    /**
     * 字段配置信息实体
     * @return 生成字段对象
     */
    FormField product(FieldConfigDto fieldConfigDto) throws Exception;

    String getValue(String value) throws Exception;

    String getLabel(String fieldId, String value) throws Exception;

    String getType() throws Exception;

    List<String> getFileList(String value) throws Exception;
}
