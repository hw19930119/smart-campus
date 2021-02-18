/*
 * @(#) CategoryConfigServiceImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-07-30 11:59:15
 */

package com.sunsharing.smartcampus.service.impl.filed;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.field.FormField;
import com.sunsharing.smartcampus.mapper.common.TzhxyTemplateMapper;
import com.sunsharing.smartcampus.mapper.filed.CategoryConfigMapper;
import com.sunsharing.smartcampus.mapper.filed.DeclareConfigMapper;
import com.sunsharing.smartcampus.mapper.filed.FieldConfigMapper;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyTemplate;
import com.sunsharing.smartcampus.model.pojo.filed.CategoryConfig;
import com.sunsharing.smartcampus.model.vo.filed.CategoryConfigDto;
import com.sunsharing.smartcampus.model.vo.filed.FieldConfigDto;
import com.sunsharing.smartcampus.service.common.DictInfoService;
import com.sunsharing.smartcampus.service.common.TzhxyTemplateService;
import com.sunsharing.smartcampus.service.filed.CategoryConfigService;
import com.sunsharing.smartcampus.service.filed.DeclareConfigService;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j;

@Log4j
@Service(value = "categoryConfigServiceImpl")
public class CategoryConfigServiceImpl implements CategoryConfigService{
    @Autowired
    TzhxyTemplateService tzhxyTemplateService;
    @Autowired
    CategoryConfigService categoryConfigService;
    @Autowired
    DeclareConfigService declareConfigService;
    @Autowired
    DictInfoService dictInfoService;
    @Autowired
    DeclareConfigMapper declareConfigMapper;
    @Autowired
    private CategoryConfigMapper categoryConfigMapper;
    @Autowired
    private FieldConfigMapper fieldConfigMapper;
    @Autowired
    TzhxyTemplateMapper tzhxyTemplateMapper;

    @Override
    public void updateCategoryState(String categoryId) {
        categoryConfigMapper.updateCategoryState(categoryId);
    }

    @Override
    public List<CategoryConfigDto> selectAllCategory(String templateId,List<String> categoryIdList) {

        return categoryConfigMapper.seelctAllCategory(templateId,categoryIdList);
    }
    @Override
    public Map findBasicFieldList(String templateId) {

        return tranMap(declareConfigMapper.findBasicFieldList(),templateId);
    }

    @Override
    public void addCategory(String key,String templateId, String categoryId, String type, String name, String score) {

        if("0".equals(type)){//新增目录（树形结构）
            categoryConfigMapper.addCategory(key,templateId,categoryId,type,name,score);
        }else if("1".equals(type)){ //新增控件
            fieldConfigMapper.addField(key,categoryId,name,score);
            //修改类型状态为 未自评
            categoryConfigService.updateCategoryState(categoryId);
        }
    }

    @Override
    public void updateCategory(String categoryId, String name, String score) {
        categoryConfigMapper.updateCategory(categoryId,name,score);
    }

    @Override
    public void deleteCategory(JSONObject jsonObject) {
        String categoryType=jsonObject.getString("categoryType");
        String key=jsonObject.getString("key");
        if("0".equals(categoryType)){
            //找出该目录的所有下级
            CategoryConfig categoryConfig = categoryConfigMapper.selectCategoryById(key);
            String templateId=categoryConfig.getTemplateId();
            List<CategoryConfig> categoryByTemplateId = categoryConfigMapper.getCategoryByTemplateId(templateId);
            List<String> deleteCategoryList=new ArrayList<>();
            getChildId(categoryConfig,categoryByTemplateId,deleteCategoryList);
            if(!deleteCategoryList.isEmpty()){
                for(String id:deleteCategoryList){
                    categoryConfigMapper.deleteById(id);
                }
            }
            categoryConfigMapper.deleteById(key);
        }else if("1".equals(categoryType)){
            fieldConfigMapper.deleteFieldById(key);
        }
    }
    private void getChildId(CategoryConfig parentCategoryConfig
        ,List<CategoryConfig> categoryByTemplateId,List<String> deleteCategoryList){
        for(CategoryConfig categoryConfig:categoryByTemplateId){
            if(categoryConfig.getParentId().equals(parentCategoryConfig.getKey())){//是下级
                deleteCategoryList.add(categoryConfig.getKey());
                getChildId(categoryConfig,categoryByTemplateId,deleteCategoryList);
            }
        }
    }
    @Override
    public void updateCategoryStateById(String id, String zpScore) {
        categoryConfigMapper.updateCategoryStateById(id,zpScore);
    }

    @Override
    public Pair<Boolean, TzhxyTemplate> calculateCategoryScore(String templateId) {
        /*String pcNo=byId.getPcNo();
        String schoolType=byId.getSchoolType();
        //获取自评模板信息
        List<TzhxyTemplate> tzhxyTemplate = tzhxyTemplateMapper.selectTemplateByPcnoAndSchooltype(pcNo, schoolType);
        if (tzhxyTemplate.isEmpty()) {
            throw new ShareBusinessException(1500, "未获取到模板信息");
        }
        String templateId = tzhxyTemplate.get(0).getTemplateNo();*/
        boolean b=false;
        /*TzhxyBaseInfo byId = tzhxyBaseInfoService.getById(declareId);
        if(byId==null){
            throw new ShareBusinessException(1500, "未获取到基础信息表信息");
        }else{
            b=categoryConfigService.calculateCategoryScore(byId);
            Pair.of(true,true);
        }*/
        //根据模板id查询模板信息
        TzhxyTemplate template = tzhxyTemplateService.getById(templateId);
        //查询所有类型信息
        List<CategoryConfigDto> categoryConfigDtos=categoryConfigService.selectAllCategory(templateId,null);
        //获取所有父节点
        List<CategoryConfigDto> parentCategorys=categoryConfigDtos.stream().filter(item->"0".equals(item.getParentId())).collect(Collectors.toList());
        for(CategoryConfigDto parentCategoryVo:parentCategorys){
            //递归组装树
            assembleTree(parentCategoryVo,categoryConfigDtos);
        }
        boolean flag=true;
        for(CategoryConfigDto parentCategoryVo:parentCategorys){
            flag=addScore(parentCategoryVo,true);
            if(!flag){
                break;
            }
        }
        if(!flag){
            return Pair.of(false,template);
        }else {
            return Pair.of(true,template);
        }
    }

    @Override
    public List<CategoryConfig> getCategoryByTemplateId(String templateId) {
        return categoryConfigMapper.getCategoryByTemplateId(templateId);
    }

    boolean addScore(CategoryConfigDto parentCategoryVo,boolean flag){
        if(flag==false){
                return flag;
        }
        double parentScore= Double.parseDouble(parentCategoryVo.getScore());
        double childrenScore=0.00;
        List<CategoryConfigDto> childrenList = parentCategoryVo.getChildren();
        if(!childrenList.isEmpty()){
            for(CategoryConfigDto children:childrenList){
                childrenScore += Double.parseDouble(children.getScore());
            }
            if(parentScore!=childrenScore){
                return false;
            }else {
                for(CategoryConfigDto child:childrenList){
                    flag=addScore(child,flag);
                    if(flag==false){
                        break;
                    }
                }
                return flag;
            }
        }else{
            return true;
        }
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
                              Map<String, List<FieldConfigDto>> fieldConfigMap) {
        List<CategoryConfigDto> childTnormVoList = new ArrayList<>();
        // 父级指标平安指数
        for (CategoryConfigDto tnormVo : tnorms) {
            if (tnormVo.getParentId().equals(fatherCategoryVo.getKey())) {
                childTnormVoList.add(tnormVo);
                assembleTree(tnormVo, tnorms,lastList,fieldConfigMap);
            }
        }
        fatherCategoryVo.setChildren(childTnormVoList);
        getLastCatefory(lastList,fatherCategoryVo,fieldConfigMap);
    }
    private void getLastCatefory(List<CategoryConfigDto> lastlist,CategoryConfigDto fatherCategoryVo,
                                 Map<String, List<FieldConfigDto>> fieldConfigMap){
        if(fatherCategoryVo.getChildren().isEmpty()){
            lastlist.add(fatherCategoryVo);
            Map<String,Object> result = new HashMap();
            List<FieldConfigDto> fieldConfigDtoList = fieldConfigMap.get(fatherCategoryVo.getKey());
            //把字段list转成类型的树形结构的最后一级
            List<CategoryConfigDto> formFieldList=new ArrayList<>();
            //这层级下的所有的指标列表
            List<FormField> formFields = declareConfigService.generalFormFiledList(fieldConfigDtoList);
            for(FormField formField:formFields){
                CategoryConfigDto lastCategory=new CategoryConfigDto();
                lastCategory.setTitle(formField.getTitle());
                lastCategory.setCategoryType("1");
                lastCategory.setKey(formField.getKey());
                lastCategory.setScore(formField.getScore());
                formFieldList.add(lastCategory);
            }
            fatherCategoryVo.setChildren(formFieldList);
            fatherCategoryVo.setChild(declareConfigService.generalFormFiledList(fieldConfigDtoList));

        }
    }
    private Map tranMap(List<FieldConfigDto> fieldConfigList,String templateId) {
        List<CategoryConfigDto> lastList=new ArrayList();
        List<CategoryConfigDto> treeList = new ArrayList();
        List resultList = new ArrayList();
        //查询模板信息
        String templateName=fieldConfigMapper.selectTemplateNameById(templateId);
        List<Map<String, Object>> fieldCategoryList = dictInfoService.getAllData("CG_FIELD_CATEGORY");
        //查询所有类型信息
        List<CategoryConfigDto> categoryConfigDtos=selectAllCategory(templateId,null);
        //获取所有父节点
        List<CategoryConfigDto> parentCategorys=categoryConfigDtos.stream().filter(item->"0".equals(item.getParentId())).collect(Collectors.toList());

        if (fieldConfigList.isEmpty()) {
            for(CategoryConfigDto parentCategoryVo:parentCategorys){
                //递归组装树
                assembleTree(parentCategoryVo,categoryConfigDtos);
                treeList.add(parentCategoryVo);
            }
            for(CategoryConfigDto categoryConfigDto:categoryConfigDtos){
                if(categoryConfigDto.getChildren().isEmpty()){
                    lastList.add(categoryConfigDto);
                    Map<String,Object> result = new HashMap();
                    categoryConfigDto.setChild(new ArrayList<>());
                }
            }
        } else {
            Map<String, List<FieldConfigDto>> fieldConfigMap =
                fieldConfigList.stream().collect(Collectors.groupingBy(FieldConfigDto::getFieldCategory));

            for(CategoryConfigDto parentCategoryVo:parentCategorys){
                //递归组装树
                assembleTree(parentCategoryVo,categoryConfigDtos,lastList,fieldConfigMap);
                treeList.add(parentCategoryVo);
            }
        }
        Map map=new HashMap();
        map.put("fieldConfig",treeList);
        map.put("lastList",lastList);
        map.put("templateName",templateName);
        return map;
    }
}
