/*
 * @(#) ScurdApiImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 10:37:22
 */

package com.sunsharing.smartcampus.scurd.api;

import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.service.common.DictInfoService;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import com.sunsharing.zeus.scurd.configure.api.ScurdApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ScurdApiImpl implements ScurdApi {

    @Autowired
    DictInfoService dictInfoService;

    @Override
    public List<Map<String, Object>> getDmData(String dmName) {
        return dictInfoService.getAllData(dmName);

    }


    @Override
    public Map getLoginUserInfo(HttpServletRequest httpServletRequest) {
        // SessionUser sessionUser = SessionUser.load();
        // if (sessionUser != null) {
        //     log.debug("登录用户:{}", sessionUser);
        //     return MapUtil.objectToMapKeepType(sessionUser);
        // }
        IeduUserVo ieduUserVo=IeduUserController.load(httpServletRequest,null);

        Map<String,String> map=new HashMap<>();
        String userId="";
        String idNum="";
        String xzqh="";
        String cityBelong="";
        String name="";
        String admin="";
        String roleId="";
        String roleCode="";
        String unitCode="";
        if(ieduUserVo.getApplyUserVo()!=null){
            ApplyUserVo vo=ieduUserVo.getApplyUserVo();
            userId=vo.getId();
            idNum=vo.getIdNum();
            xzqh=vo.getXzqh();
            cityBelong=vo.getCityBelong();
            name=vo.getName();
            unitCode=vo.getUnitCode();
            admin="";
            roleId="";
        }else if(ieduUserVo.getAuditUserVo()!=null){
            AuditUserVo vo=ieduUserVo.getAuditUserVo();
            userId=vo.getId();
            idNum=vo.getIdNum();
            xzqh=vo.getXzqh();
            cityBelong="";
            name=vo.getName();
            admin=vo.getAdmin();
            roleId=vo.getRoleId();
            unitCode="";
            roleCode=vo.getRoleCode();
        }else{
            return null;
        }
        map.put("userId",userId);
        map.put("userName",name);
        map.put("idNum",idNum);
        map.put("xzqh",xzqh);
        map.put("cityBelong",cityBelong);
        map.put("name",name);
        map.put("admin",admin);
        map.put("roleId",roleId);
        map.put("roleCode",roleCode);
        map.put("unitCode",unitCode);
        return map;

    }
}
