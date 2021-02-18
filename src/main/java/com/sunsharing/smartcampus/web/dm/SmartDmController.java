/*
 * @(#) SmartDmComtroller
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-27 14:46:02
 */

package com.sunsharing.smartcampus.web.dm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunsharing.share.webex.annotation.ShareRest;
import com.sunsharing.zeus.scurd.configure.model.SelectItem;
import com.sunsharing.zeus.scurd.configure.util.ServiceLocator;
import com.sunsharing.zeus.scurd.configure.util.SpringBeanMethodUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

@ShareRest
@RestController
@RequestMapping("/dm")
@Log4j2
public class SmartDmController {

    /**
     * url:/dm/loadSmartDm
     * 从controller获取业务DM
     * @param param {"service_name":"","method_name":"","params":["参数1","参数2"]}
     * @return
     */
    @PostMapping("/loadSmartDm")
    public List<SelectItem> loadSmartDm(@RequestBody Map<String,Object> param){
        Object service = ServiceLocator.getBean((String)param.get("service_name"));
        String methodName = (String)param.get("method_name");
        if (service != null) {
            try {
                JSONArray params = JSONObject.parseArray(JSONObject.toJSONString(param.get("params")));
                Object[] object = new Object[0];
                if (params != null) {
                    object = params.toArray();
                }

                Object obj = SpringBeanMethodUtil.invokeMethod(service, methodName, object);
                String jsonArray = JSONArray.toJSONString(obj);
                return JSONArray.parseArray(jsonArray, SelectItem.class);
            } catch (Exception var8) {
                log.error("[/dm/loadSmartDm]访问出错，service:" + service + ":" + methodName + ",", var8);
            }
        }
        return new ArrayList();
    }

}
