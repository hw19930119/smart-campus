/*
 * @(#) ImageUploaderProduct
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
import com.sunsharing.smartcampus.field.view.ImageUploader;

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
 * @description: 图片上传
 */
@Component(IFieldProduct.BEAN_PREFIX + ImageUploaderProduct.TAG_ID)
public class ImageUploaderProduct extends AbstractFieldProductProduct {

    public static final String TAG_ID = "3";
    public static final String TAG_TYPE = "imageUploader";
    static final String TAG_NAME = "图片上传";

    @Override
    protected void doProduct(String fieldConfigJson, String promptMsg, FormField formField) throws Exception {
        formField.setType(TAG_TYPE).setName(TAG_NAME);

        ImageUploader imageUploader = null;
        if (StringUtils.isNotEmpty(fieldConfigJson)) {
            imageUploader = JSONArray.parseObject(fieldConfigJson, ImageUploader.class);
        }

        Map<String, Object> componentProps = formField.getComponentProps();

        if (imageUploader != null) {
            Map<String, String> uploadProps = new HashMap<>();
            uploadProps.put("accept", imageUploader.getImageTypeOptionAll());
            componentProps.put("imgSum", imageUploader.getNum());
            componentProps.put("imageSize",imageUploader.getSize());
            formField.setUploadProps(uploadProps);
        }

        componentProps.put("placeholder", promptMsg);
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
            sb.append(String.format("%s,", fileDto.getUrl()));
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
            label.add(MapHelper.ofHashMap("src", fileDto.getUrl()));
        });
        return JSONArray.toJSONString(label);
    }

    @Override
    public String getType() throws Exception {
        return "img";
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
