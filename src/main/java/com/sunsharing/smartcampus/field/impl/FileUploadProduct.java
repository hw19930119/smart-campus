/*
 * @(#) FileUploadProduct
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:33
 */

package com.sunsharing.smartcampus.field.impl;

import com.alibaba.fastjson.JSONArray;
import com.sunsharing.component.utils.base.MapHelper;
import com.sunsharing.smartcampus.constant.constants.CoConstant;
import com.sunsharing.smartcampus.field.AbstractFieldProductProduct;
import com.sunsharing.smartcampus.field.FormField;
import com.sunsharing.smartcampus.field.IFieldProduct;
import com.sunsharing.smartcampus.field.view.FileDto;
import com.sunsharing.smartcampus.field.view.FileUpload;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: cxy
 * @date: 2020/3/3
 * @description: 文件上传
 */
@Component(IFieldProduct.BEAN_PREFIX + FileUploadProduct.TAG_ID)
public class FileUploadProduct extends AbstractFieldProductProduct {

    public static final String TAG_ID = "8";
    public static final String TAG_TYPE = "fileUpload";
    static final String TAG_NAME = "文件上传";

    @Override
    protected void doProduct(String fieldConfigJson, String promptMsg, FormField formField) throws Exception {
        formField.setType(TAG_TYPE).setName(TAG_NAME);

        FileUpload fileUpload = null;
        if (StringUtils.isNotEmpty(fieldConfigJson)) {
            fileUpload = JSONArray.parseObject(fieldConfigJson, FileUpload.class);
        }

        Map<String, Object> componentProps = formField.getComponentProps();
        componentProps.put("placeholder", promptMsg);
        componentProps.put("fileName", "original");

        if (fileUpload != null) {
            Map<String, String> uploadProps = new HashMap<>();
            uploadProps.put("accept", fileUpload.getFileType());
            formField.setUploadProps(uploadProps);
            String fileTemplate = "";
            String[] fileTemplates = fileUpload.getFileTemplateAddress().split(",");
            if (fileTemplates.length == 2) {
                fileTemplate = String.format("%s?name=%s", fileTemplates[0], fileTemplates[1].substring(0, fileTemplates[1].indexOf("$$")));
            }
            componentProps.put("fileTemplate", fileTemplate);
        }

        formField.setComponentProps(componentProps);

        formField.setUrl(CoConstant.FILE_UPLOAD);
    }

    @Override
    public String getValue(String value) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        List<FileDto> fileDtoList = JSONArray.parseArray(value, FileDto.class);
        fileDtoList.stream().forEach(fileDto -> {
            sb.append(String.format("%s,%s$$%s;", fileDto.getUrl(), fileDto.getOriginal(), fileDto.getFileSize()));
        });
        return sb.toString();
    }

    @Override
    public String getLabel(String fieldId, String value) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        List<Map> label = new ArrayList<>();
        List<FileDto> fileDtoList = JSONArray.parseArray(value, FileDto.class);
        fileDtoList.stream().forEach(fileDto -> {
            label.add(MapHelper.ofHashMap("name", fileDto.getOriginal(), "src", fileDto.getUrl()));
        });
        return JSONArray.toJSONString(label);
    }

    @Override
    public String getType() throws Exception {
        return "file";
    }

    @Override
    public List<String> getFileList(String value) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        List<FileDto> fileDtoList = JSONArray.parseArray(value, FileDto.class);
        return fileDtoList.stream().map(FileDto::getUrl).collect(Collectors.toList());
    }
}
