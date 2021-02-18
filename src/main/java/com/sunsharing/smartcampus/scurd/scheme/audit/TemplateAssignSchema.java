/*
 * @(#) DeclarationSchema
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-05 10:41:39
 */

package com.sunsharing.smartcampus.scurd.scheme.audit;

import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

@Component
@Scurd(schemaKey = "VW_AUDIT_TEMPLATE_ASSIGN_FA")
@Log4j2
public class TemplateAssignSchema extends ScurdAopDeclare {
    @Override
    public void afterSearch(Map reqData, Map result) {

        //开始过滤
        Map data = (Map) org.apache.commons.collections.MapUtils.getObject(result, "data");
        List<Map> list = (List<Map>) MapUtils.getObject(data, "list");
        if (list == null) {
            return;
        }
        for (Map dataMap : list) {
            int CONFIG_COUNT_EXPERT = Integer.parseInt((String) dataMap.get("CONFIG_COUNT_EXPERT"));
            int COMMENT_COUNT_EXPERT = Integer.parseInt((String) dataMap.get("COMMENT_COUNT_EXPERT"));

            if (CONFIG_COUNT_EXPERT == 0 || COMMENT_COUNT_EXPERT == 0) {
                dataMap.put("COMMENT_STATUS", "未评审");
                dataMap.put("__COMMENT_STATUS", "0");
            } else if (COMMENT_COUNT_EXPERT < CONFIG_COUNT_EXPERT) {
                dataMap.put("COMMENT_STATUS", "评审中");
                dataMap.put("__COMMENT_STATUS", "1");
            } else if (COMMENT_COUNT_EXPERT == CONFIG_COUNT_EXPERT) {
                dataMap.put("COMMENT_STATUS", "评审完成");
                dataMap.put("__COMMENT_STATUS", "2");
            }
        }
    }
}
