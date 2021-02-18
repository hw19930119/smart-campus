/*
 * @(#) EasyExcelListener
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-18 10:52:24
 */

package com.sunsharing.smartcampus.service.filed;

import com.google.common.collect.Lists;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.util.StringUtils;
import com.sunsharing.smartcampus.model.vo.filed.ExcelCheckErrDto;
import com.sunsharing.smartcampus.model.vo.filed.ExcelCheckResult;
import com.sunsharing.smartcampus.utils.EasyExcelValiHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

/**
 *  @description: easyExcel监听器
 * Created by wuxw on 2020/4/4.
 */
@Log4j2
@Data
@EqualsAndHashCode(callSuper = false)
public class EasyExcelListener<T> extends AnalysisEventListener<T> {

    //成功结果集
    private List<T> successList = new ArrayList<>();

    //失败结果集
    private List<ExcelCheckErrDto<T>> errList = new ArrayList<>();

    //处理逻辑service
    private ExcelCheckManager<T> excelCheckManager;

    private List<T> list = new ArrayList<>();

    private List<String> heads = Lists.newArrayList();


    //excel对象的反射类
    private Class<T> clazz;

    public EasyExcelListener(Class<T> clazz) {
        this.clazz = clazz;
    }

    public EasyExcelListener(ExcelCheckManager<T> excelCheckManager) {
        this.excelCheckManager = excelCheckManager;
    }

    public EasyExcelListener(ExcelCheckManager<T> excelCheckManager, Class<T> clazz) {
        this.excelCheckManager = excelCheckManager;
        this.clazz = clazz;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        log.info("启动easyExcel监听");
        String errMsg;
        if (((LinkedHashMap) t).size() == 0) {
            return;
        }
        try {
            //根据excel数据实体中的javax.validation + 正则表达式来校验excel数据
            errMsg = EasyExcelValiHelper.validateEntity(t);
        } catch (Exception e) {
            errMsg = "解析数据出错";
            log.info("EXCEL解析出错", e.getMessage());
        }
        if (!StringUtils.isEmpty(errMsg)) {
            ExcelCheckErrDto excelCheckErrDto = new ExcelCheckErrDto(t, errMsg);
            errList.add(excelCheckErrDto);
        } else {
            list.add(t);
        }
        //每1000条处理一次
        if (list.size() > 1000) {
            //校验
            ExcelCheckResult result = excelCheckManager.checkImportExcel(list);
            successList.addAll(result.getSuccessData());
            errList.addAll(result.getErrData());
            list.clear();
        }
    }

    //所有数据解析完成了 都会来调用
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        ExcelCheckResult result = excelCheckManager.checkImportExcel(list);
        successList.addAll(result.getSuccessData());
        errList.addAll(result.getErrData());
        list.clear();
    }


    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        headMap.forEach((key, cellData) -> {
            this.heads.add(cellData.getStringValue());
        });
    }

    /**
     * @description: 校验excel头部格式，必须完全匹配
     * @param headMap 传入excel的头部（第一行数据）数据的index,name
     * @param context 解析上下文
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        if (clazz != null) {
            try {
                Map<Integer, String> indexNameMap = getIndexNameMap(clazz);
                Set<Integer> keySet = indexNameMap.keySet();
                for (Integer key : keySet) {
                    if (StringUtils.isEmpty(headMap.get(key))) {
                        throw new ExcelAnalysisException("解析excel出错，请传入正确格式的excel");
                    }
                    if (!headMap.get(key).equals(indexNameMap.get(key))) {
                        throw new ExcelAnalysisException("解析excel出错，请传入正确格式的excel");
                    }
                }

            } catch (Exception e) {
                log.info("EXCEL解析出错", e.getMessage());
            }
        }
    }

    /**
     * @description: 获取注解里ExcelProperty的value，用作校验excel
     * @param clazz 类
     */
    @SuppressWarnings("rawtypes")
    public Map<Integer, String> getIndexNameMap(Class clazz) throws NoSuchFieldException {
        Map<Integer, String> result = new HashMap<>();
        Field field;
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            field = clazz.getDeclaredField(fields[i].getName());
            field.setAccessible(true);
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty != null) {
                int index = excelProperty.index();
                String[] values = excelProperty.value();
                StringBuilder value = new StringBuilder();
                for (String v : values) {
                    value.append(v);
                }
                result.put(index, value.toString());
            }
        }
        return result;
    }


}
