/*
 * @(#) SysConfigSchema
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-11-17 17:55:55
 */

package com.sunsharing.smartcampus.scurd.scheme.common;

import com.sunsharing.smartcampus.service.apply.TzhxyBaseInfoService;
import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import lombok.extern.log4j.Log4j2;

@Component
@Scurd(schemaKey = "T_ZHXY_SYS_FA")
@Log4j2
public class SysConfigSchema extends ScurdAopDeclare {

    @Autowired
    TzhxyBaseInfoService tzhxyBaseInfoService;

    @Override
    public void beforeSave(Map reqData) {
        String endTime = MapUtils.getString(reqData, "SYS_BACK_ENDTIME");
        Map<String, Object> sysMap = tzhxyBaseInfoService.querySystemConfigBackEndTime();
        String backEndTime = MapUtils.getString(sysMap, "endTime", "");
        if (!StringUtils.equals(backEndTime, endTime)) {
            reqData.put("SYS_BACK_MARK", "0");
        }
    }
}
