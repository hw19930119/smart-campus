package com.sunsharing.smartcampus.scurd.scheme.audit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunsharing.share.common.base.exception.ShareBusinessException;
import com.sunsharing.smartcampus.constant.enums.RoleCodeEnum;
import com.sunsharing.smartcampus.mapper.audit.ApplyUserMapper;
import com.sunsharing.smartcampus.mapper.audit.AuditUserMapper;
import com.sunsharing.smartcampus.mapper.audit.RoleMapper;
import com.sunsharing.smartcampus.model.pojo.audit.ApplyUser;
import com.sunsharing.smartcampus.model.pojo.audit.AuditUser;
import com.sunsharing.smartcampus.model.pojo.audit.Role;
import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import lombok.extern.log4j.Log4j2;

@Component
@Scurd(schemaKey = "T_SC_AUDIT_USER_FA")
@Log4j2
public class AuditUserSchema extends ScurdAopDeclare {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    AuditUserMapper auditUserMapper;

    @Autowired
    ApplyUserMapper applyUserMapper;

    @Override
    public void beforeSave(Map reqData) {
        String gId = MapUtils.getString(reqData, "g_id", "");
        String idNum = MapUtils.getString(reqData, "ID_NUM", "");
        String roleId = MapUtils.getString(reqData, "ROLE_ID", "");
        String xzqh = MapUtils.getString(reqData, "XZQH", "");
        //本表查重----------
        QueryWrapper<AuditUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("ID",gId);
        queryWrapper.eq("ID_NUM",idNum);
        queryWrapper.eq("DEL","0");
        if(!CollectionUtils.isEmpty(auditUserMapper.selectList(queryWrapper))){
            throw new ShareBusinessException(1500,"用户身份证号重复");
        }
        //学校表查重---------
        QueryWrapper<ApplyUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("ID_NUM",idNum);
        queryWrapper1.eq("DEL","0");
        if(!CollectionUtils.isEmpty(applyUserMapper.selectList(queryWrapper1))){
            throw new ShareBusinessException(1500,"审核用户身份证号与学校用户重复");
        }
        reqData.put("ID_NUM", idNum.toUpperCase());
        //角色查重----------
        //具体区教育局角色只能一个，具体市教育局、受理中心只能一个
        /*String roleCode=getRoleCodeByRoleId(roleId);
        if(StringUtils.equalsAny(roleCode, RoleCodeEnum.市教育局.getValue(),
                                            RoleCodeEnum.受理中心.getValue())){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ROLE_ID",roleId);
            queryWrapper.eq("XZQH",xzqh);
            if(StringUtils.isNotBlank(gId)){
                queryWrapper.ne("ID",gId);
            }
            queryWrapper.eq("DEL","0");
            int otherCount=auditUserMapper.selectCount(queryWrapper);
            if(otherCount>0){
                throw new ShareBusinessException(1500,"所选角色被占用");
            }
        }*/
    }


    private String getRoleCodeByRoleId(String roleId){
        String roleCode="";
        QueryWrapper<Role> queryWrapper=new QueryWrapper();
        queryWrapper.eq("ROLE_ID",roleId);
        Role role=roleMapper.selectOne(queryWrapper);
        if(role!=null){
            roleCode=role.getCode();
        }
        return roleCode;
    }



}
