/*
 * @(#) AbstractFieldProductProduct
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:33
 */

package com.sunsharing.smartcampus.field;


import com.sunsharing.component.utils.base.MapHelper;
import com.sunsharing.share.common.base.IEnum;
import com.sunsharing.smartcampus.constant.constants.PropsSchemaConstant;
import com.sunsharing.smartcampus.constant.enums.FiledRequiredEnum;
import com.sunsharing.smartcampus.field.view.Input;
import com.sunsharing.smartcampus.model.vo.filed.FieldConfigDto;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: cxy
 * @date: 2020-03-03 9:21:50
 * @description: 字段生成抽象类
 */
public abstract class AbstractFieldProductProduct implements IFieldProduct {

    /**
     * 执行采集
     * @return
     */
    protected abstract void doProduct(String fieldConfigJson, String promptMsg, FormField formField) throws Exception;


    private FormField beforeProduct(FieldConfigDto fieldConfigDto) throws Exception {
        return new FormField()
            .setScore(fieldConfigDto.getScore())
            .setKey(fieldConfigDto.getFieldId())
            .setTitle(fieldConfigDto.getFieldCnName())
            .setParentKey(fieldConfigDto.getFieldCategory())
            .setTabKey(fieldConfigDto.getTabKey())
            //.setComponentProps(MapHelper.ofHashMap(PropsSchemaConstant.REQUIRED, true, "isPass", ""));
            .setComponentProps(MapHelper.ofHashMap(PropsSchemaConstant.REQUIRED,
                IEnum.getEnum(FiledRequiredEnum.class,fieldConfigDto.getFieldRequired()).getFalg(),
                "isPass", ""));
    }

    @Override
    public FormField product(FieldConfigDto fieldConfigDto) throws Exception {
        FormField formField = beforeProduct(fieldConfigDto);
        this.doProduct(fieldConfigDto.getFieldConfigJson(), fieldConfigDto.getPromptMsg(), formField);
        return formField;
    }

    protected FormField tranField(List<Map<String, Object>> itemList, FormField formField) {
        if (itemList.isEmpty()) {
            return formField;
        }

        List<Map<String, String>> options = new ArrayList<>();

        List<String> enums = new ArrayList<>(itemList.size());
        List<String> enumNames = new ArrayList<>(itemList.size());

        itemList.stream().forEach(map -> {
            map.keySet().forEach(key -> {
                if ("value".equals(key)) {
                    enums.add(MapUtils.getString(map, key, ""));
                } else if ("label".equals(key)) {
                    enumNames.add(MapUtils.getString(map, key, ""));
                }
            });
            options.add(MapHelper.ofHashMap("value", MapUtils.getString(map, "value", ""), "label", MapUtils.getString(map, "label", "")));
        });

        formField.setEnums(enums.toArray(new String[enums.size()]));
        formField.setEnumNames(enumNames.toArray(new String[enumNames.size()]));
        Map<String, Object> map = formField.getComponentProps();
        map.put("options", options);
        return formField;
    }

    protected FormField tranProps(FormField formField) {
        Map<String, Object> map = formField.getComponentProps();
        map.put("labelKey", "key");
        map.put("valueKey", "code");
        formField.setComponentProps(map);
        return formField;
    }


    /**
     * 设置单文本框和多文本框的属性
     * @param promptMsg 文本框占用提示文本
     * @param formField 字段对象
     * @param b 是否为空
     * @param characterSize 长度
     * @param input 输入格式
     */
    protected void setTextAttribute(String promptMsg, FormField formField, boolean b, String characterSize, Input input) {
        Map<String, Object> componentProps = formField.getComponentProps();
        componentProps.put("placeholder", promptMsg);

        if (b) {
            if (StringUtils.isNotEmpty(characterSize)) {
                componentProps.put("maxlength", characterSize);
            }

        }
    }

}
