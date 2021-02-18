/*
 * @(#) DeclareConfigController
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 15:33:00
 */

package com.sunsharing.smartcampus.web.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sunsharing.share.webex.annotation.ShareRest;
import com.sunsharing.smartcampus.service.filed.FieldValueService;
import com.sunsharing.smartcampus.utils.ResultDataUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

@RestController
@ShareRest
@RequestMapping("/declare")
@Log4j2
public class DeclareConfigController {


    @Autowired
    FieldValueService fieldValueService;


    @GetMapping("/findBasicFileList")
    @ResponseBody
    public Map findBasicFileList(String templateId) {
        //return declareConfigService.findBasicFieldList(templateId);
        return null;
    }

    @RequestMapping("/save")
    public Map save(@RequestBody JSONObject paramObject) {
        log.info("申报指标自评保存：{}", paramObject);
        Iterator<Map.Entry<String, Object>> iterator = paramObject.entrySet().iterator();
        String categoryId = null;
        Object value = null;
        String schoolId = null;

        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object object = next.getValue();
            if ("schoolId".equals(key)) {
                schoolId = (String) object;
            } else {
                categoryId = key;
                value = object;
            }
            /*if(object instanceof HashMap){
                value=(Map) object;
                Iterator<Map.Entry<String,Object>> iterator1 = ((HashMap) object).entrySet().iterator();
                while(iterator1.hasNext()){
                    Map.Entry<String,Object> mapEntry=iterator1.next();
                    String key= mapEntry.getKey();
                    Object value1= mapEntry.getValue();
                    System.out.println("第三种情况");
                    System.out.println(key);
                    System.out.println(value1);
                }
            }else if (value instanceof List){
                value= (ArrayList) value;
            }*/
        }
        if (StringUtils.isBlank(schoolId)) {
            return ResultDataUtils.packParams(false, "未获取到正确的申报信息，刷新页面后重试！");
        }
        String fieldValue = JSONObject.toJSONString(value);
        JSONObject jsonObject = JSON.parseObject(fieldValue);
        String score = jsonObject.getString("score");
        String message = jsonObject.getString("message");
        String supFiles = jsonObject.getString("supFiles");
        try {
            fieldValueService.saveAssessment(categoryId, schoolId, fieldValue, score, message, supFiles);
            //修改类型表状态为已经自评,并修改自评分数
            //categoryConfigService.updateCategoryStateById(categoryId,score);
            return ResultDataUtils.packParams(true, "保存成功！");
        } catch (RuntimeException e) {
            log.error(e);
            return ResultDataUtils.packParams(false, "保存失败！");
        }
    }


}
