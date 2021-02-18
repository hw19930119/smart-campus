/*
 * @(#) AllocationController
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-08-03 15:58:21
 */

package com.sunsharing.smartcampus.web.common;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.share.webex.annotation.ShareRest;
import com.sunsharing.smartcampus.constant.constants.Constants;
import com.sunsharing.smartcampus.model.pojo.common.TLable;
import com.sunsharing.smartcampus.model.vo.apply.FieldVo;
import com.sunsharing.smartcampus.service.apply.TzhxyBaseInfoService;
import com.sunsharing.smartcampus.service.common.ZhxyLableService;
import com.sunsharing.smartcampus.service.filed.CategoryConfigService;
import com.sunsharing.smartcampus.utils.ReportUtils;
import com.sunsharing.smartcampus.utils.ResultDataUtils;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;

@RestController
@ShareRest
@RequestMapping("/allocation")
@Log4j2
public class AllocationController {
    @Autowired
    ZhxyLableService zhxyLableService;

    @Autowired
    CategoryConfigService categoryConfigService;
    @Autowired
    TzhxyBaseInfoService tzhxyBaseInfoService;

    @RequestMapping("/addLable")
    @ResponseBody
    public Map addLable(String name) {
        //查询该名字是否存在
        List<TLable> lables = zhxyLableService.getLableByName(name);
        if (lables.isEmpty()) {
            //新增标签
            int i = zhxyLableService.addLable(UUID.randomUUID().toString().replaceAll("-", ""), name);
            if (i > 0) {
                return ResultDataUtils.packParams(true, "新增自定义标签成功！");
            }
            return ResultDataUtils.packParams(false, "新增自定义标签失败！");
        } else {
            return ResultDataUtils.packParams(false, "该标签名字存在，新增失败！");
        }
    }

    @GetMapping("/findBasicFileList")
    @ResponseBody
    public Map findBasicFileList(HttpServletRequest request) {
        String templateId = request.getParameter("templateId");
        return categoryConfigService.findBasicFieldList(templateId);
    }

    @RequestMapping(value = "/changeCategory", method = RequestMethod.POST)
    @ResponseBody
    public Map changeCategory(@RequestBody JSONObject jsonObject) {
        Map<String, String> resultMap = new HashMap<>();
        String templateId = jsonObject.getString("templateId");//模板id
        String categoryId = jsonObject.getString("key");//目录id
        String type = jsonObject.getString("categoryType");//0,：目录，1：评分项目
        String name = jsonObject.getString("title"); //名称
        String score = jsonObject.getString("score");//分数
        String isAdd = jsonObject.getString("isAdd");//0:新增，1：修改
       /* String templateId="1";
        String categoryId="5";
        String type="1";
        String name="工商注册控件==";
        String score="55.00";
        String isAdd="1";*/
        try {
            String key = com.sunsharing.component.utils.base.StringUtils.generateUUID();
            if ("0".equals(isAdd)) {//新增
                categoryConfigService.addCategory(key, templateId, categoryId, type, name, score);
            } else if ("1".equals(isAdd)) {//修改
                categoryConfigService.updateCategory(categoryId, name, score);
                key = categoryId;
            }
            resultMap.put("key", key);
            return ResultDataUtils.packParams(resultMap, "操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDataUtils.packParams(false, "操作失败");
        }
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
    @ResponseBody
    public Map deleteCategory(@RequestBody JSONObject jsonObject) {
        try {
            categoryConfigService.deleteCategory(jsonObject);
            return ResultDataUtils.packParams(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDataUtils.packParams(false, "删除失败");
        }
    }

    @GetMapping("/exportJbqkPdf")
    public void exportJbqkPdf(HttpServletResponse response, String businessKey) throws Exception {
        Map<String, Object> originalMap = tzhxyBaseInfoService.queryBaseInfoByBusinessKey(businessKey);
        Map<String, Object> data = new HashMap<>();
        FieldVo fieldVo = (FieldVo) originalMap.get("schoolType");
        List<Map> columnValue = (List) fieldVo.getColumnValue();
        int count = 0;
        for (int i = 0; i < columnValue.size(); i++) {
            if (!"S06".equals(columnValue.get(i).get("id"))) {
                count++;
            }
        }
        if (count == columnValue.size()) {
            FieldVo typeName = (FieldVo) originalMap.get("schoolTypeName");
            typeName.setColumnValue(" ");
        }
        exportBeforeDataHandle(originalMap, data);
        String templateUrl = "template/xxjbqk.docx";
        String exportFileName = "学校基本情况.pdf";
        ReportUtils.exportWordToPdf(response, data, templateUrl, exportFileName);
    }

    @GetMapping("/exportJbxxPdf")
    public void exportJbxxPdf(HttpServletResponse response, String businessKey) throws Exception {
        Map<String, Object> originalMap = tzhxyBaseInfoService.queryJcxxByBusinessKey(businessKey);
        Map<String, Object> data = new HashMap<>();
        exportBeforeDataHandle(originalMap, data);
        String templateUrl = "template/jbxx.docx";
        String exportFileName = "学校信息化建设基础设施基本信息.pdf";
        ReportUtils.exportWordToPdf(response, data, templateUrl, exportFileName);
    }

    @RequestMapping("calculateCategoryScore")
    @ResponseBody
    public Pair calculateCategoryScore(String templateId) {
        /*TzhxyBaseInfo byId = tzhxyBaseInfoService.getById(declareId);
        if(byId==null){
            throw new ShareBusinessException(1500, "未获取到基础信息表信息");
        }else{
            b=categoryConfigService.calculateCategoryScore(byId);
            Pair.of(true,true);
        }*/
        Pair pair = categoryConfigService.calculateCategoryScore(templateId);
        return pair;
    }

    private void exportBeforeDataHandle(Map<String, Object> originalMap, Map<String, Object> data) {
        originalMap.forEach((k, v) -> {
            //普通字段处理,一个字段一个变量
            FieldVo value = (FieldVo) v;
            Object columnValue = value.getColumnValue();
            if (value.getIsDm() == 0) {
                data.put(k, columnValue == null || "".equals(columnValue) ? " " : columnValue);
                return;
            }
            //表码字段处理,有多少表码就有几个变量
            List<Map> dmList = value.getDmList();
            dmList.forEach(m -> {
                String curKey = MapUtils.getString(m, "id", "");
                String variateName = k + curKey;
                data.put(variateName, Constants.WXZ_SYMBOL);
                if (columnValue == null) {
                    return;
                }
                List<Map> caseColumnValue = (List) columnValue;
                caseColumnValue.forEach(m1 -> {
                    String dmVal = MapUtils.getString(m1, "id", "");
                    if (curKey.equals(dmVal)) {
                        data.put(variateName, Constants.XZ_SYMBOL);
                    }
                });
            });
        });
    }

}
