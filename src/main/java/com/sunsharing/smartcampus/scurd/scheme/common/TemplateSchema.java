package com.sunsharing.smartcampus.scurd.scheme.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sunsharing.common.utils.StringUtils;
import com.sunsharing.share.common.base.exception.ShareBusinessException;
import com.sunsharing.smartcampus.mapper.common.TzhxyTemplateMapper;
import com.sunsharing.smartcampus.mapper.filed.CategoryConfigMapper;
import com.sunsharing.smartcampus.mapper.filed.FieldConfigMapper;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyTemplate;
import com.sunsharing.smartcampus.model.pojo.filed.CategoryConfig;
import com.sunsharing.smartcampus.model.pojo.filed.FieldConfig;
import com.sunsharing.smartcampus.utils.SeqGenerateUtil;
import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Scurd(schemaKey = "T_ZHXY_TEMPLATE_FA")
@Log4j2
public class TemplateSchema extends ScurdAopDeclare {

    @Autowired
    TzhxyTemplateMapper tzhxyTemplateMapper;

    @Autowired
    CategoryConfigMapper categoryConfigMapper;

    @Autowired
    FieldConfigMapper fieldConfigMapper;


    public String roundSave(Map reqData) {
        String gId = MapUtils.getString(reqData, "g_id", "");
        String pcId = MapUtils.getString(reqData, "PC_ID", "");
        String templateType = MapUtils.getString(reqData, "TEMPLATE_TYPE", "");
        String remarks = MapUtils.getString(reqData, "REMARKS", "");
        String templateName = MapUtils.getString(reqData, "TEMPLATE_NAME", "");

        TzhxyTemplate tzhxyTemplate = new TzhxyTemplate();
        tzhxyTemplate.setRemarks(remarks);
        tzhxyTemplate.setTemplateName(templateName);
        tzhxyTemplate.setTemplateType(templateType);
        //适用范围校验
        String[] templateTypeArr = templateType.split(",");
        int tempLen = templateTypeArr.length;
        for(int i = 0; i < tempLen;i++) {
            QueryWrapper<TzhxyTemplate> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PC_ID",pcId);
            queryWrapper.ne("TEMPLATE_NO",gId);
            queryWrapper.like("TEMPLATE_TYPE",templateTypeArr[i]);
            queryWrapper.eq("DEL","0");
            List<TzhxyTemplate> tzhxyTemplates = tzhxyTemplateMapper.selectList(queryWrapper);
            if(!CollectionUtils.isEmpty(tzhxyTemplates)) {
                throw new ShareBusinessException(1500,"当前批次模板适用范围出现重复,请修改");
            }
        }

        if(StringUtils.isBlank(gId)) {//新增
            gId = SeqGenerateUtil.getId() + "";
            tzhxyTemplate.setPcId(pcId);
            tzhxyTemplate.setTemplateNo(gId);
            tzhxyTemplateMapper.insert(tzhxyTemplate);
        }else {//编辑
            tzhxyTemplate.setTemplateNo(gId);
            tzhxyTemplateMapper.updateById(tzhxyTemplate);
        }
        return gId;
    }

    public void beforeDelete(Map reqData) {
        String gId = MapUtils.getString(reqData, "g_id", "");
        TzhxyTemplate tzhxyTemplate = tzhxyTemplateMapper.selectById(gId);
        if(!"0".equals(tzhxyTemplate.getPcState())) {
            throw new ShareBusinessException(1500,"无法删除已经以用或者归档的模板");
        }
    }
    public void afterDelete(Map reqData, boolean result) {
        if(result) {
            String gId = MapUtils.getString(reqData, "g_id", "");
            List<CategoryConfig> categoryConfigs = categoryConfigMapper.selectByTemplateId(gId);
            List<String> categoryIds = categoryConfigs.stream().map(c -> c.getKey()).collect(Collectors.toList());
            //删除指标
            categoryConfigMapper.delById(gId);

            //删除指标信息
            if(!CollectionUtils.isEmpty(categoryIds)) {
                UpdateWrapper<FieldConfig> updateWrapper1 = new UpdateWrapper();
                updateWrapper1.in("FIELD_CATEGORY",categoryIds);
                fieldConfigMapper.update(new FieldConfig().setZxbs("1"),updateWrapper1);
            }
        }
    }

}
