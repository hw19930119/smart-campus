package com.sunsharing.smartcampus.service.impl.audit;


import com.sunsharing.common.utils.StringUtils;
import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.smartcampus.constant.enums.RoleCodeEnum;
import com.sunsharing.smartcampus.mapper.audit.HomePageMapper;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.service.audit.HomePageService;
import com.sunsharing.smartcampus.service.impl.dm.SmartDmServiceImpl;
import com.sunsharing.smartcampus.utils.ResultDataUtils;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 申请用户 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
@Service
@Transactional
public class HomePageServiceImpl implements HomePageService {
    @Autowired
    HomePageMapper homePageMapper;

    @Autowired
    SmartDmServiceImpl smartDmService;

    public Map countAuditByUser(){
        //获取审批人Id,审批角色Id
        AuditUserVo auditUserVo = IeduUserController.load(null, null).getAuditUserVo();
        String userId = auditUserVo.getId();
        String roleId = auditUserVo.getRoleId();
        String roleCode = auditUserVo.getRoleCode();
        String xzqh = null;
        //区教育局通过行政区划过滤
        if(RoleCodeEnum.区教育局.eq(roleCode)) {
            xzqh = auditUserVo.getXzqh();
        }
        //专家通过账号过滤待审
        if(RoleCodeEnum.专家.eq(roleCode)) {
            return ResultDataUtils.packParams(homePageMapper.countAuditExpertByUser(userId),"查询成功");
        }
        return ResultDataUtils.packParams(homePageMapper.countAuditByUser(roleId, xzqh),"查询成功");
    }
    public Map countAlreadyAuditByNode(){
        //获取审批人Id,审批角色Id
        AuditUserVo auditUserVo = IeduUserController.load(null, null).getAuditUserVo();
        String roleCode = auditUserVo.getRoleCode();
        String xzqh = null;

        //区教育局通过行政区划过滤
        if(RoleCodeEnum.区教育局.eq(roleCode)) {
            xzqh = auditUserVo.getXzqh();
        }

        return ResultDataUtils.packParams(homePageMapper.countAlreadyAuditByNode(xzqh),"查询成功");
    }

    public Map countAuditAll(String pcNo,String year){
        String pcState = null;
        if(StringUtils.isBlank(pcNo) && StringUtils.isBlank(year)) {
            //标识,标记是否查询已经归档数据,当没有输入查询条件的时候查询未归档的数据
            pcState = "1";
        }
        Map auditAll = homePageMapper.countAuditAll(pcNo, year, pcState);
        Map auditStar = homePageMapper.countAuditPassStar(pcNo,year,pcState);
        return ResultDataUtils.packParams(MapUtil.ofHashMap("auditAll",auditAll,"auditStar",auditStar),"查询成功");
    }

    public Map  countAuditPass(String pcNo,String year) {
        String pcState = null;
        if(StringUtils.isBlank(pcNo) && StringUtils.isBlank(year)) {
            //标识,标记是否查询已经归档数据,当没有输入查询条件的时候查询未归档的数据
            pcState = "1";
        }
        //查询厦门市几个区的行政区划,根据行政区划分组统计
        List<Map<String, Object>> dmXzqh = smartDmService.loadSmartDm("DM_SS_XZQH");
        Map xzqhResult = new HashMap();
        for(Map currentMap : dmXzqh) {
            String xzqh = MapUtils.getString(currentMap,"id","");
            Map xzqhMap = homePageMapper.countAuditPassByXzqh(pcNo, year,xzqh, pcState);
            xzqhResult.put(xzqh,xzqhMap);
        }
        //查询学校类型,根据学校类型分组统计
        List<Map<String, Object>> dmSchoolType = smartDmService.loadSmartDm("DM_SCHOOL_TYPE");
        Map schoolTypeResult = new HashMap();
        for(Map currentMap : dmSchoolType) {
            List<Map> schoolTypeList = homePageMapper.countAuditPassBySchoolType(pcNo, year, pcState);
            String type = MapUtils.getString(currentMap,"id","");
            int counter = 0;
            for(int i = 0;i < schoolTypeList.size();i++) {
                String type1 = MapUtils.getString(schoolTypeList.get(i),"schoolType","");
                int num = MapUtils.getInteger(schoolTypeList.get(i),"num",0);
                if(type.equals(type1)) {
                    schoolTypeResult.put(type,num);
                    break;
                }
                counter++;
            }
            if(counter == schoolTypeList.size()) {
                schoolTypeResult.put(type,0);
            }
        }

        return ResultDataUtils.packParams(MapUtil.ofHashMap("xzqhResult",xzqhResult,"schoolTypeResult",schoolTypeResult),"查询成功");
    }

}
