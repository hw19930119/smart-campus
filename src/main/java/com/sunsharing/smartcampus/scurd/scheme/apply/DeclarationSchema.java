/*
 * @(#) DeclarationSchema
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-05 10:41:39
 */

package com.sunsharing.smartcampus.scurd.scheme.apply;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyBaseInfo;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.service.apply.TzhxyBaseInfoService;
import com.sunsharing.smartcampus.service.apply.TzhxyJcssService;
import com.sunsharing.smartcampus.utils.common.ScurdHelper;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

@Component
@Scurd(schemaKey = "T_ZHXY_BASEINFO_FA")
@Log4j2
public class DeclarationSchema extends ScurdAopDeclare {

    @Autowired
    TzhxyBaseInfoService tzhxyBaseInfoService;

    @Autowired
    TzhxyJcssService tzhxyJcssService;

    @Override
    public void beforeSave(Map reqData) {
        IeduUserVo loginUser = IeduUserController.load(null,null);
        ApplyUserVo applyUser = loginUser.getApplyUserVo();

        //设置行政区划
        String xzqh = MapUtils.getString(reqData,"XZQH","");
        if(StringUtils.isBlank(xzqh)){
            reqData.put("XZQH",applyUser.getXzqh());
            reqData.put("UNIT_CODE", applyUser.getUnitCode());
            xzqh = applyUser.getXzqh();
        }
        //设置工作联系人
        String LXR_NAME = MapUtils.getString(reqData,"LXR_NAME",null);
        String LXR_PHONE = MapUtils.getString(reqData,"LXR_PHONE",null);
        if(LXR_NAME==null) {
            LXR_NAME=applyUser.getName();
        }
        if(LXR_PHONE==null) {
            LXR_PHONE=applyUser.getMobileNum();
        }
        reqData.put("LXR_NAME",LXR_NAME);
        reqData.put("LXR_PHONE", LXR_PHONE);

        //设置上一次状态
        String g_id = MapUtils.getString(reqData,"g_id","");
        String state = MapUtils.getString(reqData,"STATE","");
        String oldState = state;
        String child_id = "";
        if(StringUtils.isBlank(g_id)){
            reqData.put("LAST_STATE", state);
        }else {
            TzhxyBaseInfo baseInfo = tzhxyBaseInfoService.getById(g_id);
            reqData.put("LAST_STATE", baseInfo.getState());
            oldState = baseInfo.getState();
            child_id = tzhxyJcssService.getJcssIdForBaseInfoId(g_id);

        }
        //设置子表 T_ZHXY_JCSS_FA 的状态与行政区划
        JSONArray array = (JSONArray) MapUtils.getObject(reqData, "g_child_schemas");
        if(array.size() > 0){
            JSONObject childObject = array.getJSONObject(0);
            childObject.put("XZQH", xzqh);
            childObject.put("LAST_STATE",oldState);
            childObject.put("STATE",state);
            childObject.put("g_id",child_id);
        }
    }
    @Override
    public void afterGetSchema(Map reqData, Map result) {
        String keyId = org.apache.commons.collections4.MapUtils.getString(reqData, "g_id", "");//主键
        if(StringUtils.isBlank(keyId)){
            IeduUserVo loginUser = IeduUserController.load(null,null);
            ApplyUserVo applyUser = loginUser.getApplyUserVo();

            String LXR_NAME=ScurdHelper.getLabelValue(result,"LXR_NAME");
            String LXR_PHONE=ScurdHelper.getLabelValue(result,"LXR_PHONE");
            if(StringUtils.isBlank(LXR_NAME)) {
                LXR_NAME=applyUser.getName();
            }
            if(StringUtils.isBlank(LXR_PHONE)) {
                LXR_PHONE=applyUser.getMobileNum();
            }
            updateColumnData(result,"LXR_NAME",LXR_NAME);
            updateColumnData(result,"LXR_PHONE",LXR_PHONE);
        }
    }
    private void updateColumnData(Map result, String columnKey, String value){
        ScurdHelper.updateLabelValue(result,columnKey,value);
        ScurdHelper.updateDetailValue(result,columnKey,value);
        ScurdHelper.updateDetailLabelValue(result,columnKey,value);
    }
}
