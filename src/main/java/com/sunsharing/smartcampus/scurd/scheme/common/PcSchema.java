package com.sunsharing.smartcampus.scurd.scheme.common;

import com.sunsharing.share.common.base.exception.ShareBusinessException;
import com.sunsharing.smartcampus.mapper.common.TzhxyPcMapper;
import com.sunsharing.smartcampus.mapper.common.TzhxyTemplateMapper;
import com.sunsharing.smartcampus.mapper.filed.CategoryConfigMapper;
import com.sunsharing.smartcampus.mapper.filed.FieldConfigMapper;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyPc;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyTemplate;
import com.sunsharing.smartcampus.service.impl.common.TzhxyTemplateServiceImpl;
import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scurd(schemaKey = "T_ZHXY_PC_FA")
@Log4j2
public class PcSchema extends ScurdAopDeclare {
    @Autowired
    TzhxyPcMapper tzhxyPcMapper;

    @Autowired
    TzhxyTemplateMapper tzhxyTemplateMapper;

    @Autowired
    TzhxyTemplateServiceImpl tzhxyTemplateService;
    @Autowired
    TemplateSchema templateSchema;

    public void beforeDelete(Map reqData) {
        String gId = MapUtils.getString(reqData, "g_id", "");
        TzhxyPc tzhxyPc = tzhxyPcMapper.selectById(gId);
        if(!"0".equals(tzhxyPc.getState())) {
            throw new ShareBusinessException(1500,"无法删除已经启用用或者归档的批次");
        }
    }
    public void afterDelete(Map reqData, boolean result) {
        Map<String, String>  afterMap = new HashMap<>();
        String gId = MapUtils.getString(reqData, "g_id", "");
        List<TzhxyTemplate> tzhxyTemplates = tzhxyTemplateService.selectTemplateByPcId(gId);
        tzhxyTemplates.forEach(t -> {
            String templateNo = t.getTemplateNo();
            tzhxyTemplateMapper.delById(templateNo);
            afterMap.put("g_id",templateNo);
            templateSchema.afterDelete(afterMap,true);
        });
    }
}
