/*
 * @(#) FieldConfigSchema
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:46:25
 */

package com.sunsharing.smartcampus.scurd.scheme.filed;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sunsharing.share.common.base.exception.ShareBusinessException;
import com.sunsharing.share.common.base.exception.ShareResponseCode;
import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.share.common.mapper.JsonMapper;
import com.sunsharing.smartcampus.constant.constants.Zxbs;
import com.sunsharing.smartcampus.constant.enums.FiledTypeEnum;
import com.sunsharing.smartcampus.constant.enums.InputTypeEnum;
import com.sunsharing.smartcampus.model.pojo.filed.FieldConfig;
import com.sunsharing.smartcampus.model.vo.filed.ChangeFieldItemDto;
import com.sunsharing.smartcampus.model.vo.filed.FieldItemDto;
import com.sunsharing.smartcampus.model.vo.filed.FildItemSelectDto;
import com.sunsharing.smartcampus.service.filed.FieldConfigService;
import com.sunsharing.smartcampus.utils.common.ScurdHelper;
import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

/**
 * @author: cxy
 * @date: 2020-1-16 10:23:30
 * @description: 字段配置表在线表单配置
 */
@Component
@Log4j2
@Scurd(schemaKey = "T_POLICY_CG_FIELD_CONFIG_01")
public class FieldConfigSchema extends ScurdAopDeclare {

    @Autowired
    private FieldConfigService fieldConfigService;

    @Override
    public void afterSearch(Map reqData, Map result) {
        Map data = MapUtils.getObject(result, "data", Collections.EMPTY_MAP);
        List<Map<String, Object>> list = MapUtils.getObject(data, "list", Collections.EMPTY_LIST);
        if (list.isEmpty()) {
            return;
        }
    }

    @Override
    public void beforeSave(Map reqData) {
        String keyId = getDataByCode(reqData, "g_id");
        if (StringUtils.isEmpty(keyId)) {
            //新增英文名字段重复校验
            String fieldEnName = getDataByCode(reqData, "FIELD_EN_NAME");
            LambdaQueryWrapper<FieldConfig> queryWrapper = new LambdaQueryWrapper<FieldConfig>();
            queryWrapper.eq(FieldConfig::getFieldEnName, fieldEnName);
            queryWrapper.eq(FieldConfig::getZxbs, Zxbs.exist.getValue());
            List<FieldConfig> fieldConfigs = fieldConfigService.list(queryWrapper);
            if (!fieldConfigs.isEmpty()) {
                throw new RuntimeException("英文名称重复,请重新填写");
            }
        }
        String fieldControl = getDataByCode(reqData, "FIELD_CONTROL");
        Map paramMap = Maps.newHashMap();
        if (Objects.equals(fieldControl, FiledTypeEnum.单文本输入框.getValue())) {
            //百分比单位或是金额单位
            String unit = Objects.equals(getDataByCode(reqData, "inputFormat"), "4")
                ? getDataByCode(reqData, "per_unit") : getDataByCode(reqData, "money_unit");
            paramMap = MapUtil.ofHashMap("characterSize", getDataByCode(reqData, "characterSize"),
                "inputFormat", getDataByCode(reqData, "inputFormat"),
                "decimalPlaces", getDataByCode(reqData, "decimalPlaces"),
                "unit", unit);
        } else if (Objects.equals(fieldControl, FiledTypeEnum.多文本输入框.getValue())) {
            paramMap = MapUtil.ofHashMap("characterSize", getDataByCode(reqData, "characterSize"),
                "inputFormat", getDataByCode(reqData, "inputFormat"));
        } else if (Objects.equals(fieldControl, FiledTypeEnum.图片上传.getValue())) {
            String imageTypeOption = getDataByCode(reqData, "imageTypeOption");
            paramMap = MapUtil.ofHashMap("size", getDataByCode(reqData, "size"),
                "num", getDataByCode(reqData, "num"),
                "imageTypeOption", imageTypeOption,
                "imageTypeOptionAll", String.format("%s,%s", imageTypeOption, imageTypeOption.toLowerCase()));
        } else if (Objects.equals(fieldControl, FiledTypeEnum.单选框.getValue())
            || Objects.equals(fieldControl, FiledTypeEnum.多选框.getValue())
            || Objects.equals(fieldControl, FiledTypeEnum.下拉单选.getValue())
            || Objects.equals(fieldControl, FiledTypeEnum.下拉多选.getValue())) {
            String json = getDataByCode(reqData, "choiceOption");
            if (!StringUtils.isEmpty(json)) {
                JsonMapper maper = JsonMapper.nonNullMapper();
                FieldItemDto filedItem = maper.fromJson(json, FieldItemDto.class);
                List<FildItemSelectDto> itemList = filedItem.getItemList();
                itemList.stream().forEach(item -> {
                    Optional<FildItemSelectDto> first =
                        itemList.stream().filter(filedItemVo ->
                            !filedItemVo.getLabel().equals(item.getLabel()) && filedItemVo.getValue().equals(item.getValue()))
                            .findFirst();
                    if (first.isPresent()) {
                        throw new ShareBusinessException(ShareResponseCode.BIZ_ILLEGAL.getCode(),
                            String.format("选项【%s】和选项【%s】的key一样，请重新配置", item.getLabel(), first.get().getLabel()));
                    }
                });
                paramMap = MapUtil.objectToMapKeepType(filedItem);
            }
        } else if (Objects.equals(fieldControl, FiledTypeEnum.附件上传.getValue())) {
            String fileTypeOption = getDataByCode(reqData, "fileTypeOption");
            paramMap = MapUtil.ofHashMap("fileType", getDataByCode(reqData, "fileType"),
                "qtsclx", getDataByCode(reqData, "qtsclx"), "fileTypeOption", fileTypeOption,
                "fileTemplateAddress", getDataByCode(reqData, "fileTemplateAddress"));
        } else if (Objects.equals(fieldControl, FiledTypeEnum.地址库.getValue())) {
            String address = getDataByCode(reqData, "ADDRESS");
            paramMap = MapUtil.ofHashMap("address", address);
        } else if (Objects.equals(fieldControl, FiledTypeEnum.时间.getValue())) {
            String timer = getDataByCode(reqData, "TIMER");
            paramMap = MapUtil.ofHashMap("timer", timer);
        } else if (Objects.equals(fieldControl, FiledTypeEnum.数据读取.getValue())) {
            String dataRead = getDataByCode(reqData, "DATA_READ");
            paramMap = MapUtil.ofHashMap("dynamicDataId", dataRead);
        }

        if (paramMap.size() > 0) {
            String jsonString = JSON.toJSONString(paramMap);
            reqData.put("FIELD_CONFIG_JSON", jsonString);
        } else {
            reqData.put("FIELD_CONFIG_JSON", "");
        }

    }

    @Override
    public void afterGetSchema(Map reqData, Map result) {
        String keyId = MapUtils.getString(reqData, "g_id", "");//主键
        if (StringUtils.isEmpty(keyId)) {
            return;
        }
        String jsonConfig = MapUtils.getString(reqData, "FIELD_CONFIG_JSON", "");//配置json数据
        if (StringUtils.isEmpty(jsonConfig)) {
            return;
        }
        JsonMapper maper = JsonMapper.nonNullMapper();
        ChangeFieldItemDto filedItem = maper.fromJson(jsonConfig, ChangeFieldItemDto.class);


        String fieldControl = MapUtils.getString(reqData, "FIELD_CONTROL", "");//字段控件

        if (Objects.equals(fieldControl, FiledTypeEnum.单文本输入框.getValue())
            || Objects.equals(fieldControl, FiledTypeEnum.多文本输入框.getValue())) {
            String characterSize = filedItem.getCharacterSize();
            String inputFormat = filedItem.getInputFormat();
            ScurdHelper.updateDetailLabelValue(result, "characterSize", characterSize);
            ScurdHelper.updateDetailValue(result, "characterSize", characterSize);
            ScurdHelper.updateDetailLabelValue(result, "inputFormat", InputTypeEnum.getInputTypeName(inputFormat));
            ScurdHelper.updateDetailValue(result, "inputFormat", inputFormat);

            if (Objects.equals(fieldControl, FiledTypeEnum.单文本输入框.getValue())) {
                String decimalPlaces = filedItem.getDecimalPlaces();
                String unit = filedItem.getUnit();
                String unitKey = Objects.equals(inputFormat, "4") ? "per_unit" : "money_unit";
                ScurdHelper.updateDetailValue(result, "decimalPlaces", decimalPlaces);
                ScurdHelper.updateDetailLabelValue(result, "decimalPlaces", decimalPlaces + " 位");
                ScurdHelper.updateDetailValue(result, unitKey, unit);
            }
        } else if (Objects.equals(fieldControl, FiledTypeEnum.图片上传.getValue())) {
            String size = filedItem.getSize();
            String num = filedItem.getNum();
            ScurdHelper.updateDetailLabelValue(result, "size", size);
            ScurdHelper.updateDetailValue(result, "size", size);
            ScurdHelper.updateDetailLabelValue(result, "num", num);
            ScurdHelper.updateDetailValue(result, "num", num);
            ScurdHelper.updateDetailLabelValue(result, "num", num);
            ScurdHelper.updateDetailValue(result, "imageTypeOption", filedItem.getImageTypeOption());
        } else if (Objects.equals(fieldControl, FiledTypeEnum.单选框.getValue())
            || Objects.equals(fieldControl, FiledTypeEnum.多选框.getValue())
            || Objects.equals(fieldControl, FiledTypeEnum.下拉单选.getValue())
            || Objects.equals(fieldControl, FiledTypeEnum.下拉多选.getValue())) {

            List<FildItemSelectDto> selectDtos = filedItem.getItemList();
            List<String> resultList = selectDtos.stream().map(FildItemSelectDto::getLabel).collect(Collectors.toList());
            ScurdHelper.updateDetailLabelValue(result, "choiceOption", Joiner.on(",").join(resultList));
            ScurdHelper.updateDetailValue(result, "choiceOption", jsonConfig);

        } else if (Objects.equals(fieldControl, FiledTypeEnum.附件上传.getValue())) {
            ScurdHelper.updateDetailLabelValue(result, "fileType", filedItem.getFileType());
            ScurdHelper.updateDetailValue(result, "fileType", filedItem.getFileType());
            ScurdHelper.updateDetailLabelValue(result, "fileTypeOption", filedItem.getFileTypeOption());
            ScurdHelper.updateDetailValue(result, "fileTypeOption", filedItem.getFileTypeOption());
            ScurdHelper.updateDetailLabelValue(result, "qtsclx", filedItem.getQtsclx());
            ScurdHelper.updateDetailValue(result, "qtsclx", filedItem.getQtsclx());
            ScurdHelper.updateDetailValue(result, "fileTemplateAddress", filedItem.getFileTemplateAddress());
        } else if (Objects.equals(fieldControl, FiledTypeEnum.地址库.getValue())) {
            ScurdHelper.updateDetailValue(result, "ADDRESS", filedItem.getAddress());

        } else if (Objects.equals(fieldControl, FiledTypeEnum.时间.getValue())) {
            ScurdHelper.updateDetailValue(result, "TIMER", filedItem.getTimer());
        } else if (Objects.equals(fieldControl, FiledTypeEnum.数据读取.getValue())) {
            ScurdHelper.updateDetailValue(result, "DATA_READ", filedItem.getDynamicDataId());
        }

    }

    private String getDataByCode(Map reqData, String codeKey) {
        return MapUtils.getString(reqData, codeKey, "");
    }


    private String judgeQxFlag(String userDept, String listDept) {
        if (StringUtils.isEmpty(userDept) || StringUtils.isEmpty(listDept)) {
            return "0";
        }
        if (Objects.equals(userDept, listDept)) {
            return "1";
        }
        return "0";
    }
}
