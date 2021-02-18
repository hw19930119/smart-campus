/*
 * @(#) FieldConfigUpServiceImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-18 10:13:27
 */

package com.sunsharing.smartcampus.service.impl.filed;

import com.google.common.collect.Lists;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.fastjson.JSON;
import com.sunsharing.share.common.base.exception.ShareResponseCode;
import com.sunsharing.share.webex.entity.ShareResponse;
import com.sunsharing.smartcampus.model.vo.filed.ExcelCheckErrDto;
import com.sunsharing.smartcampus.model.vo.filed.ExcelCheckResult;
import com.sunsharing.smartcampus.model.vo.filed.FieldItemDto;
import com.sunsharing.smartcampus.model.vo.filed.FieldUploadDto;
import com.sunsharing.smartcampus.model.vo.filed.FildItemSelectDto;
import com.sunsharing.smartcampus.service.filed.EasyExcelListener;
import com.sunsharing.smartcampus.service.filed.FieldConfigUpService;
import com.sunsharing.smartcampus.utils.EasyExcelUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

/**
 * Created by wuxw on 2020/5/25.
 */
@Log4j
@Service(value = "fieldConfigUpService")
public class FieldConfigUpServiceImpl implements FieldConfigUpService {

    @Autowired
    private FieldConfigUpService fieldConfigUpService;

    @Override
    public ExcelCheckResult checkImportExcel(List<Map> objects) {
        //错误数组
        List<ExcelCheckErrDto<Map>> errList = new ArrayList<>();
        //可重写校验方法
        return new ExcelCheckResult<>(objects, errList);
    }

    @Override
    public ShareResponse downLoadTemplate(HttpServletResponse response) throws IOException {
        String tempName = "申报字段批量录入";
        FieldUploadDto object = new FieldUploadDto();
        List<FieldUploadDto> objectList = Lists.newArrayList();
        objectList.add(object);
        EasyExcelUtils.webWriteExcel(response, objectList, object.getClass(), tempName);
        return ShareResponse.success("下载成功");
    }

    @Override
    public ShareResponse batchProcessingUpload(HttpServletResponse response, MultipartFile file) throws IOException {

        String name = file.getOriginalFilename();
        if (!StringUtils.isEmpty(name)) {
            try {
                int begin = name.indexOf(".");
                int last = name.length();
                String a = name.substring(begin, last);
                if (!a.endsWith(".xlsx")) {
                    return ShareResponse.fail(ShareResponseCode.BIZ_ILLEGAL, "请上传xlsx类型文件");
                }
            } catch (Exception ex) {
                return ShareResponse.fail(ShareResponseCode.BIZ_ILLEGAL, "请上传xlsx类型文件");
            }
        }

        EasyExcelListener listener = new EasyExcelListener(fieldConfigUpService);
        EasyExcelFactory.read(file.getInputStream(), null, listener).sheet().doRead();
        List<Map<Integer, String>> dataList = listener.getSuccessList();

        List<String> heads = listener.getHeads();
        if (heads.isEmpty() || heads.size() != 2 || !Objects.equals("选项", heads.get(0))) {
            return ShareResponse.fail(ShareResponseCode.BIZ_ILLEGAL, "请上传正确的EXCEL文件");
        }

        if (dataList.isEmpty()) {
            return ShareResponse.fail(ShareResponseCode.BIZ_ILLEGAL, "无上传数据");
        }

        long count = dataList.stream().distinct().count();
        boolean isRepeat = count < dataList.size();
        if (isRepeat) {
            return ShareResponse.fail(ShareResponseCode.BIZ_ILLEGAL, "数据重复");
        }

        List<FildItemSelectDto> itemList = Lists.newArrayList();

        for (int i = 0; i < dataList.size(); i++) {
            FildItemSelectDto dto = new FildItemSelectDto();
            Map<Integer, String> td = dataList.get(i);
            String val = td.get(0);
            String key=td.get(1);
            if (StringUtils.isEmpty(val)) {
                continue;
            }
            //dto.setLabel(val).setValue((i + 1) + "");
            dto.setLabel(val).setValue(key);
            itemList.add(dto);
        }
        FieldItemDto fieldItemDto = new FieldItemDto();
        fieldItemDto.setOptionType("3").setItemList(itemList);

        return ShareResponse.success(JSON.toJSONString(fieldItemDto));
    }


}
