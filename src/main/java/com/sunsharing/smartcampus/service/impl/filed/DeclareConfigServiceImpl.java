/*
 * @(#) DeclareConfigServiceImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 19:18:08
 */

package com.sunsharing.smartcampus.service.impl.filed;

import com.sunsharing.smartcampus.field.FieldProductStrategy;
import com.sunsharing.smartcampus.field.FormField;
import com.sunsharing.smartcampus.field.IFieldProduct;
import com.sunsharing.smartcampus.mapper.comment.TzhxyDpConfigMapper;
import com.sunsharing.smartcampus.mapper.filed.DeclareConfigMapper;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyDpConfig;
import com.sunsharing.smartcampus.model.pojo.filed.FieldValue;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.model.vo.filed.CategoryConfigDto;
import com.sunsharing.smartcampus.model.vo.filed.FieldConfigDto;
import com.sunsharing.smartcampus.service.audit.ScoreService;
import com.sunsharing.smartcampus.service.common.DictInfoService;
import com.sunsharing.smartcampus.service.filed.CategoryConfigService;
import com.sunsharing.smartcampus.service.filed.DeclareConfigService;
import com.sunsharing.smartcampus.service.filed.FieldValueService;
import com.sunsharing.smartcampus.web.common.IeduUserController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeclareConfigServiceImpl implements DeclareConfigService {
    @Autowired
    ScoreService scoreService;
    @Autowired
    DeclareConfigMapper declareConfigMapper;

    @Autowired
    DictInfoService dictInfoService;

    @Autowired
    FieldProductStrategy fieldProductStrategy;
    @Autowired
    TzhxyDpConfigMapper tzhxyDpConfigMapper;


    @Autowired
    CategoryConfigService categoryConfigService;
    @Autowired
    FieldValueService fieldValueService;

    @Override
    public Map getTreePreviewByTemplateId(String templateId) {

        return tranMap(this.declareConfigMapper.findBasicFieldList(), templateId, "", false, true, "",
            null);
    }

    @Override
    public Map getTreePreview(String templateId, String declareId, String piId, boolean isShenBao,
                              String isExpertCommentPage) {
        List<FieldConfigDto> fieldConfigList = this.declareConfigMapper.findBasicFieldList();
        return tranMap(fieldConfigList, templateId, declareId, true, isShenBao, piId, isExpertCommentPage);
    }

    @Override
    public Map findBasicFieldList(String templateId, String g_id) {

        return tranMap(this.declareConfigMapper.findBasicFieldList(), templateId, g_id, false, true, "",
            null);
    }

    /**
     * 递归组装树形结构
     * @param fatherCategoryVo
     * @param tnorms
     */
    private void assembleTree(CategoryConfigDto fatherCategoryVo, List<CategoryConfigDto> tnorms) {
        List<CategoryConfigDto> childTnormVoList = new ArrayList<>();

        // 父级指标平安指数
        for (CategoryConfigDto tnormVo : tnorms) {
            if (tnormVo.getParentId().equals(fatherCategoryVo.getKey())) {
                childTnormVoList.add(tnormVo);
                assembleTree(tnormVo, tnorms);
            }
        }

        fatherCategoryVo.setChildren(childTnormVoList);
    }

    /**
     * 递归组装树形结构
     * @param fatherCategoryVo
     * @param tnorms
     */
    private void assembleTree(CategoryConfigDto fatherCategoryVo, List<CategoryConfigDto> tnorms,
                              List<CategoryConfigDto> lastList,
                              Map<String, List<FieldConfigDto>> fieldConfigMap,
                              Map<String, List<FieldValue>> fieldValueMap, boolean isZuZhuangZhiBiao) {
        List<CategoryConfigDto> childTnormVoList = new ArrayList<>();
        double fatherZpScore = Optional.ofNullable(fatherCategoryVo.getZpScore()).orElse(0.00);
        double fatherAuditScore = Optional.ofNullable(fatherCategoryVo.getAuditScore()).orElse(0.00);
        String state = "1";
        // 组装树形结构
        for (CategoryConfigDto tnormVo : tnorms) {
            if (tnormVo.getParentId().equals(fatherCategoryVo.getKey())) {
                childTnormVoList.add(tnormVo);
                assembleTree(tnormVo, tnorms, lastList, fieldConfigMap, fieldValueMap, isZuZhuangZhiBiao);
            }
        }
        //把字段值表中的分数拿出来，赋值给最后一级目录
        if (childTnormVoList.isEmpty()) {//说明自己就是最后一级目录了
            FieldValue a = new FieldValue();
            String key = fatherCategoryVo.getKey();
            List<FieldValue> fieldValueList = Optional.ofNullable(fieldValueMap.get(key)).orElse(null);
            double zpscore = 0.00;
            if (fieldValueList != null) {
                a = fieldValueMap.get(fatherCategoryVo.getKey()).get(0);
                zpscore = Optional.ofNullable(Double.parseDouble(a.getZpScore())).orElse(0.00);
                state = Optional.ofNullable(a.getState()).orElse("1");
            }
            fatherZpScore = fatherZpScore + zpscore;
            fatherCategoryVo.setZpScore(fatherZpScore);
        }
        //父级分数=所有子分数相加
        for (CategoryConfigDto tnormVo : childTnormVoList) {
            fatherZpScore = fatherZpScore + Optional.ofNullable(tnormVo.getZpScore()).orElse(0.0);
            fatherAuditScore = fatherAuditScore + Optional.ofNullable(tnormVo.getAuditScore()).orElse(0.0);
            if ("0".equals(tnormVo.getState())) {
                //                fatherCategoryVo.setState("0");
                state = "0";
            }
            if ("1".equals(tnormVo.getShState())) {
                fatherCategoryVo.setShState("1");
            }
        }
        fatherCategoryVo.setState(state);
        fatherCategoryVo.setZpScore(fatherZpScore);
        fatherCategoryVo.setAuditScore(fatherAuditScore);
        fatherCategoryVo.setChildren(childTnormVoList);
        if (isZuZhuangZhiBiao) {
            getLastCatefory2(lastList, fatherCategoryVo, fieldConfigMap, fieldValueMap);
        } else {
            getLastCatefory(lastList, fatherCategoryVo, fieldConfigMap, fieldValueMap);
        }

    }

    private void getLastCatefory(List<CategoryConfigDto> lastlist, CategoryConfigDto fatherCategoryVo,
                                 Map<String, List<FieldConfigDto>> fieldConfigMap,
                                 Map<String, List<FieldValue>> fieldValueMap) {
        if (fatherCategoryVo.getChildren().isEmpty()) {
            lastlist.add(fatherCategoryVo);
            Map<String, Object> result = new HashMap();
            List<FieldConfigDto> fieldConfigDtoList = fieldConfigMap.get(fatherCategoryVo.getKey());
            //获取这个类型下指标的值
            List<FieldValue> fieldValueList = fieldValueMap.get(fatherCategoryVo.getKey());
            // double zpscore= Double.parseDouble(fieldValueList.get(0).getZpScore());
            fatherCategoryVo.setDefaultValue(fieldValueList);
            // fatherCategoryVo.setZpScore(zpscore);
            //这层级下的所有的指标列表
            List<FormField> formFields = generalFormFiledList(fieldConfigDtoList);
            if (fieldConfigDtoList == null) {
                fatherCategoryVo.setChild(null);
            } else {
                fatherCategoryVo.setChild(formFields);
            }

        }
    }

    /**
     * 把评分项加入树形结构
     * @param lastlist
     * @param fatherCategoryVo
     * @param fieldConfigMap
     * @param fieldValueMap
     */
    private void getLastCatefory2(List<CategoryConfigDto> lastlist, CategoryConfigDto fatherCategoryVo,
                                  Map<String, List<FieldConfigDto>> fieldConfigMap,
                                  Map<String, List<FieldValue>> fieldValueMap) {
        if (fatherCategoryVo.getChildren().isEmpty()) {
            lastlist.add(fatherCategoryVo);
            Map<String, Object> result = new HashMap();
            List<FieldConfigDto> fieldConfigDtoList = fieldConfigMap.get(fatherCategoryVo.getKey());
            //获取这个类型下指标的值
            List<FieldValue> fieldValueList = fieldValueMap.get(fatherCategoryVo.getKey());
            // double zpscore= Double.parseDouble(fieldValueList.get(0).getZpScore());
            fatherCategoryVo.setDefaultValue(fieldValueList);
            // fatherCategoryVo.setZpScore(zpscore);
            //这层级下的所有的指标列表
            List<FormField> formFields = generalFormFiledList(fieldConfigDtoList);
            //把字段list转成类型的树形结构的最后一级
            List<CategoryConfigDto> formFieldList = new ArrayList<>();
            //这层级下的所有的指标列表
            for (FormField formField : formFields) {
                CategoryConfigDto lastCategory = new CategoryConfigDto();
                lastCategory.setTitle(formField.getTitle());
                lastCategory.setCategoryType("1");
                lastCategory.setKey(formField.getKey());
                lastCategory.setScore(formField.getScore());
                lastCategory.setParentId(fatherCategoryVo.getKey());
                formFieldList.add(lastCategory);
            }
            fatherCategoryVo.setChildren(formFieldList);
            fatherCategoryVo.setChild(formFields);
        }
    }

    /**
     *
     * @param fieldConfigList
     * @param templateId
     * @param g_id
     * @param isZuZhuangZhiBiao 是否把评分项加入树形结构，ture 是，false 否
     * @return
     */
    private Map tranMap(List<FieldConfigDto> fieldConfigList, String templateId, String g_id, boolean isZuZhuangZhiBiao,
                        boolean isShenbao, String piId, String isExpertCommentPage) {
        List<CategoryConfigDto> lastList = new ArrayList();
        List<CategoryConfigDto> treeList = new ArrayList();
        List resultList = new ArrayList();
        List<FieldValue> fieldValue = null;
        if (StringUtils.equals(isExpertCommentPage, "expert")
            || StringUtils.equals(isExpertCommentPage, "yes")) {
            //查询字段值
            fieldValue = fieldValueService.selectFieldValueByDeclareId(g_id);
        } else {
            //查询字段值
            fieldValue = fieldValueService.selectFieldValueBySchoolId(g_id);
        }

        List<String> categoryIdList = null;
        if (StringUtils.equals(isExpertCommentPage, "yes")) {
            categoryIdList = new ArrayList<>();
            IeduUserVo iEduUserVo = IeduUserController.load(null, null);
            AuditUserVo auditUserVo = iEduUserVo.getAuditUserVo();
            TzhxyDpConfig tzhxyDpConfig = tzhxyDpConfigMapper.getExpertCategoryIdList(auditUserVo.getId(), g_id);
            if (tzhxyDpConfig == null) {
                categoryIdList.add("XXX");
            } else {
                String[] categoryIdArray = tzhxyDpConfig.getFieldIds().split(",");
                for (String categoryId : categoryIdArray) {
                    categoryIdList.add(categoryId);
                    String allRefStr = tzhxyDpConfigMapper.getAllParentNodeList(categoryId);
                    categoryIdList.addAll(Arrays.asList(allRefStr.split(",")));
                }
            }
        }
        //查询所有类型信息
        List<CategoryConfigDto> categoryConfigDtos = categoryConfigService.selectAllCategory(templateId, categoryIdList);
        //获取平均分
        Map<String, Double> stringDoubleMap = scoreService.querExpertIndexAvScoreMap(g_id);
        //组装平均分
        for (CategoryConfigDto categoryConfigDto : categoryConfigDtos) {
            for (String key : stringDoubleMap.keySet()) {
                if (categoryConfigDto.getKey().equals(key)) {
                    categoryConfigDto.setAvgScore(stringDoubleMap.get(key));
                }
            }
        }
        //替换指标值表中的状态值
        if (isShenbao) {//是否申报端
            //获取退回状态
            Map<String, String> stringScoreMap = scoreService.queryFinalIndexScoreMap(g_id);
            for (CategoryConfigDto categoryConfigDto : categoryConfigDtos) {
                //组装退回状态
                for (String key : stringScoreMap.keySet()) {
                    if (categoryConfigDto.getKey().equals(key)) {
                        String giveback = Optional.ofNullable(stringScoreMap.get(key))
                            .orElse("");
                        categoryConfigDto.setIfEdit(stringScoreMap.get(key));
                        break;
                    }
                }

            }
        } else {
            Map<String, Map<String, Object>> stringScoreMap = scoreService.queryCurrentIndexScoreMap(g_id, piId);
            for (CategoryConfigDto categoryConfigDto : categoryConfigDtos) {
                for (String key : stringScoreMap.keySet()) {
                    if (categoryConfigDto.getKey().equals(key)) {
                        Map<String, Object> map = Optional.ofNullable(stringScoreMap.get(key))
                            .orElse(null);
                        String giveback = (String) map.get("giveBack");
                        double auditScore = (double) map.get("myAuditScore");
                        categoryConfigDto.setAuditScore(auditScore);
                        if ("0".equals(giveback)) {
                            categoryConfigDto.setGiveback("0");//未退回
                            categoryConfigDto.setShState("1");//已经审核
                        } else if ("1".equals(giveback)) {
                            categoryConfigDto.setGiveback("1");//退回
                            categoryConfigDto.setShState("1");//已经审核
                        } else if ("".equals(giveback)) {
                            categoryConfigDto.setGiveback("");//退回
                            categoryConfigDto.setShState("0");//未审核
                        }
                        break;
                    }
                }

            }
        }
        Map<String, List<FieldValue>> fieldValueMap =
            fieldValue.stream().collect(Collectors.groupingBy(FieldValue::getCategoryId));
        List<Map<String, Object>> fieldCategoryList = this.dictInfoService.getAllData("CG_FIELD_CATEGORY");

        //获取所有父节点
        List<CategoryConfigDto> parentCategorys = categoryConfigDtos.stream().filter(item -> "0".equals(item.getParentId())).collect(Collectors.toList());

        if (fieldConfigList.isEmpty()) {
            for (CategoryConfigDto parentCategoryVo : parentCategorys) {
                //递归组装树
                assembleTree(parentCategoryVo, categoryConfigDtos);
                treeList.add(parentCategoryVo);
            }
            for (CategoryConfigDto categoryConfigDto : categoryConfigDtos) {
                if (categoryConfigDto.getChildren().isEmpty()) {
                    lastList.add(categoryConfigDto);
                    categoryConfigDto.setChild(new ArrayList<>());
                }
            }
        } else {
            Map<String, List<FieldConfigDto>> fieldConfigMap =
                fieldConfigList.stream().collect(Collectors.groupingBy(FieldConfigDto::getFieldCategory));
            for (CategoryConfigDto parentCategoryVo : parentCategorys) {
                //递归组装树
                assembleTree(parentCategoryVo, categoryConfigDtos, lastList, fieldConfigMap, fieldValueMap, isZuZhuangZhiBiao);
                treeList.add(parentCategoryVo);
            }
        }

        Map map = new HashMap();
        map.put("fieldConfig", treeList);
        map.put("lastList", lastList);
        return map;
    }

    @Override
    public List<FormField> generalFormFiledList(List<FieldConfigDto> fieldConfigDtoList) {
        List<FormField> formFieldList = new ArrayList<>();
        if (null != fieldConfigDtoList) {
            fieldConfigDtoList.stream().forEach(fieldConfigDto -> {
                try {
                    if (StringUtils.isBlank(fieldConfigDto.getFieldControl())) {
                        FormField formField = new FormField();
                        formField.setScore(fieldConfigDto.getScore());
                        formField.setKey(fieldConfigDto.getFieldId());
                        formField.setTitle(fieldConfigDto.getFieldCnName());
                        formField.setParentKey(fieldConfigDto.getFieldCategory());
                        formField.setTabKey(fieldConfigDto.getTabKey());
                        formFieldList.add(formField);
                    } else {
                        IFieldProduct fieldProduct = fieldProductStrategy.getOrThrow(fieldConfigDto.getFieldControl());
                        formFieldList.add(fieldProduct.product(fieldConfigDto));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return formFieldList;
    }
}