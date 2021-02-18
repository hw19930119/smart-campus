package com.sunsharing.smartcampus.service.impl.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunsharing.common.utils.StringUtils;
import com.sunsharing.share.common.base.exception.ShareBusinessException;
import com.sunsharing.smartcampus.mapper.filed.CategoryConfigMapper;
import com.sunsharing.smartcampus.mapper.filed.FieldConfigMapper;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyTemplate;
import com.sunsharing.smartcampus.mapper.common.TzhxyTemplateMapper;
import com.sunsharing.smartcampus.model.pojo.filed.CategoryConfig;
import com.sunsharing.smartcampus.model.pojo.filed.FieldConfig;
import com.sunsharing.smartcampus.service.common.TzhxyTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.utils.ResultDataUtils;
import com.sunsharing.smartcampus.utils.SeqGenerateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 模板信息表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-03
 */
@Service
public class TzhxyTemplateServiceImpl extends ServiceImpl<TzhxyTemplateMapper, TzhxyTemplate> implements TzhxyTemplateService {
    @Autowired
    TzhxyTemplateMapper tzhxyTemplateMapper;

    @Autowired
    CategoryConfigMapper categoryConfigMapper;

    @Autowired
    FieldConfigMapper configMapper;

    @Transactional
    public Map copyTemp(String oldPcId,String newPcId,String templateIds) {
        //根据模板id查询出list,复制模板
        if(StringUtils.isBlank(templateIds)) {
            throw new ShareBusinessException(1500,"未选中模板");
        }
        if(StringUtils.isBlank(oldPcId) || StringUtils.isBlank(newPcId)) {
            throw new ShareBusinessException(1500,"批次号异常");
        }
        List<String> listIds = Arrays.asList(templateIds.split(","));
        List<TzhxyTemplate> tzhxyTemplates = selectTemplateByPcId(oldPcId);
        String templateTypeStr = tzhxyTemplates.stream().
                map(TzhxyTemplate::getTemplateType).collect(Collectors.joining(","));
        //循环复制模板
        listIds.forEach(tempId -> {
            TzhxyTemplate tzhxyTemplate = tzhxyTemplateMapper.selectByIdDiy(tempId);
            List<CategoryConfig> categoryConfigs = categoryConfigMapper.selectByTemplateId(tempId);
            List<FieldConfig> fieldConfigs = !CollectionUtils.isEmpty(categoryConfigs) ? configMapper.selectByConfig(categoryConfigs) : new ArrayList<>();
            String randomStr = ResultDataUtils.randomStr(5);
            //考评对象组校验,如果是模板复制需要校验,批次复制不需要校验
            if(oldPcId.equals(newPcId)) {
                String currentTemplateType = tzhxyTemplate.getTemplateType();
                if(StringUtils.isNotEmpty(templateTypeStr) && StringUtils.isNotEmpty(currentTemplateType)) {
                    List<String> templateTypes = Arrays.asList(currentTemplateType.split(","));
                    String resultTemplateType = templateTypes.stream().filter(s -> !templateTypeStr.contains(s))
                            .collect(Collectors.joining(","));
                    tzhxyTemplate.setTemplateType(resultTemplateType);
                }
            }

            //复制模板
            String tempIdNew = SeqGenerateUtil.getId() + "";
            tzhxyTemplate.setTemplateNo(tempIdNew);
            tzhxyTemplate.setPcId(newPcId);
            tzhxyTemplate.setPcState("0");//0待启用
            //复制指标类型
            categoryConfigs.forEach(c -> {
                String parentId = c.getParentId();
                if(!StringUtils.isBlank(parentId)  && !"0".equals(parentId)){
                    c.setParentId(subStr(parentId) + randomStr);
                }
                c.setKey(subStr(c.getKey()) + randomStr);
                c.setTemplateId(tempIdNew);
            });
            //复制指标
            fieldConfigs.forEach(f -> {
                f.setFieldCategory(subStr(f.getFieldCategory()) + randomStr);
                f.setFieldId(StringUtils.genUUID());
            });
            //模板入库
            tzhxyTemplateMapper.insert(tzhxyTemplate);
            //指标类型入库
            if(!CollectionUtils.isEmpty(categoryConfigs)) {
                categoryConfigMapper.insertBatch(categoryConfigs);
            }
            //指标入库
            if(!CollectionUtils.isEmpty(fieldConfigs)) {
                configMapper.insertBatch(fieldConfigs);
            }

        });


        return ResultDataUtils.packParams(true,"复制成功");
    }

    public List<TzhxyTemplate> selectTemplateByPcId(String pcId) {
        //查询当前批次下的所有模板并获取到所有考评对象组
        QueryWrapper<TzhxyTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PC_ID",pcId);
        queryWrapper.eq("DEL","0");
        return tzhxyTemplateMapper.selectList(queryWrapper);
    }

    //字符串截取前27位
    private String subStr(String str) {
        return str.length() >= 32 ? str.substring(0,32) : str;
    }
}
