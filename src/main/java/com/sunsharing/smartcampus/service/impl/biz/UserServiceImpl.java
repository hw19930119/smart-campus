/*
 * @(#) FieldConfigServiceImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:47:10
 */

package com.sunsharing.smartcampus.service.impl.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunsharing.smartcampus.constant.enums.AttestStateEnum;
import com.sunsharing.smartcampus.constant.enums.UserTypeEnum;
import com.sunsharing.smartcampus.mapper.audit.ApplyUserMapper;
import com.sunsharing.smartcampus.mapper.audit.AuditUserMapper;
import com.sunsharing.smartcampus.mapper.biz.MenuMapper;
import com.sunsharing.smartcampus.model.pojo.biz.Menu;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.service.biz.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j2;


@Service(value = "userService")
@Log4j2
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    ApplyUserMapper applyUserMapper;
    @Autowired
    AuditUserMapper auditUserMapper;
    @Autowired
    MenuMapper menuMapper;

    @Override
    public IeduUserVo getIeduUserVoByIdNum(String idNum,String userId) {
        IeduUserVo ieduUserVo=new IeduUserVo();
        AuditUserVo auditUserVo=auditUserMapper.getAuditUserVoByIdNum(idNum,userId);
        if(auditUserVo!=null){
            ieduUserVo.setAuditUserVo(auditUserVo);
            ieduUserVo.setType(UserTypeEnum.AUDIT.getValue());
        }else {
            ApplyUserVo applyUserVo=applyUserMapper.getApplyUserByIdNum(idNum,userId);
            if(applyUserVo!=null) {
                if(StringUtils.equals(applyUserVo.getState(), AttestStateEnum.认证通过.getValue())){
                    ieduUserVo.setApplyUserVo(applyUserVo);
                }else{
                    ApplyUserVo applyUserVoTemp=new ApplyUserVo();
                    applyUserVoTemp.setName(applyUserVo.getName());
                    applyUserVoTemp.setIdNum(applyUserVo.getIdNum());
                    applyUserVoTemp.setMobileNum(applyUserVo.getMobileNum());
                    ieduUserVo.setApplyUserVo(applyUserVoTemp);
                }
                ieduUserVo.setType(UserTypeEnum.APPLY.getValue());
            }else{
                //
            }
        }
        return ieduUserVo;
    }
    @Override
    public ApplyUserVo getApplyUserVoByUserId(String userId) {
        ApplyUserVo applyUserVo=applyUserMapper.getApplyUserVoByUserId(userId);
        return applyUserVo;
    }
    @Override
    public JSONObject getMenusByLoginRoleId(String roleId) {
        QueryWrapper<Menu> menuWrapper=new QueryWrapper<>();
        menuWrapper.eq("ROLE_ID",roleId);
        Menu menu=menuMapper.selectOne(menuWrapper);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("menu",menu);
        if(menu!=null&& StringUtils.isNotBlank(menu.getMenuJson())){
            JSONObject menuJson=JSONObject.parseObject(menu.getMenuJson(),JSONObject.class);
            jsonObject.put("menuJson",menuJson);
        }
        return jsonObject;
    }
}
