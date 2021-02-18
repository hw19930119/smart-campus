package com.sunsharing.smartcampus.service.impl.audit;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.smartcampus.constant.enums.AttestStateEnum;
import com.sunsharing.smartcampus.constant.enums.BizResultEnum;
import com.sunsharing.smartcampus.mapper.apply.TzhxySchoolMapper;
import com.sunsharing.smartcampus.mapper.audit.ApplyUserMapper;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyBaseInfo;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxySchool;
import com.sunsharing.smartcampus.model.pojo.audit.ApplyUser;
import com.sunsharing.smartcampus.model.vo.apply.ApplyLoginUser;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.service.apply.TzhxyBaseInfoService;
import com.sunsharing.smartcampus.service.audit.ApplyUserService;
import com.sunsharing.smartcampus.web.common.IeduUserController;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class ApplyUserServiceImpl extends ServiceImpl<ApplyUserMapper, ApplyUser> implements ApplyUserService {

    private static String errorMsg = "【%s】用户已与【%s】绑定认证关系 ";

    @Autowired
    ApplyUserMapper applyUserMapper;
    @Autowired
    TzhxySchoolMapper tzhxySchoolMapper;

    @Autowired
    TzhxyBaseInfoService baseInfoService;
    public static JSONObject getDefaultReturnJSON(){
        JSONObject result=new JSONObject();
        result.put("status",true);
        result.put("msg","成功");
        return result;
    }
    @Override
    public Map<String, Object> submitAttestation(ApplyUser userVo, ApplyLoginUser attestationVo) {
        //重复提交验证
        LambdaQueryWrapper<ApplyUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApplyUser::getDel, "0")
            .eq(ApplyUser::getIdNum, userVo.getIdNum())
            .eq(ApplyUser::getState, AttestStateEnum.认证中.getValue());
        List<ApplyUser> list = this.applyUserMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(list) || list.size() > 0) {
            return MapUtil.ofHashMap("status", false, "msg", "用户正在认证中，不能重复提交");
        }

        //认证通过往下走，认证不通过与
        if (!StringUtils.isBlank(userVo.getId())
            && (!AttestStateEnum.认证通过.getValue().equals(attestationVo.getState())
            || !StringUtils.equals(userVo.getId(), attestationVo.getId()))) {
            return MapUtil.ofHashMap("status", false, "msg", "登录账号与提交账号不一致");
        }
        //判断用户未正在认证中
        if (StringUtils.isBlank(userVo.getId())
            && !StringUtils.isBlank(attestationVo.getId())
            && AttestStateEnum.认证通过.getValue().equals(attestationVo.getState())) {
            return MapUtil.ofHashMap("status", false, "msg", "该登录用户状态异常");
        }

        if (AttestStateEnum.认证通过.getValue().equals(userVo.getState())) {//修改认证
            Integer count = queryCountBaseInfo(userVo.getUnitCode());
            if (count > 0) {
                return MapUtil.ofHashMap("status", false, "msg", "目前还有未处理的审核数据，不能修改认证");
            }
            //如果是变更学校，那么判断另一个学校是否有未完成的业务
            if (!userVo.getUnitCode().equals(attestationVo.getUnitCode())) {
                Integer count2 = queryCountBaseInfo(attestationVo.getUnitCode());
                if (count2 > 0) {
                    return MapUtil.ofHashMap("status", false, "msg", "变更的目标学校还有未处理的审核数据，不能修改认证");
                }
            }
        }

        ApplyUser applyUser = new ApplyUser()
            .setId(StringUtils.isBlank(attestationVo.getId())
                ? com.sunsharing.common.utils.StringUtils.genUUID() : attestationVo.getId())
            .setIdNum(userVo.getIdNum())
            .setMobileNum(userVo.getMobileNum())
            .setName(userVo.getName())
            .setState(AttestStateEnum.认证中.getValue())
            .setUnitCode(attestationVo.getUnitCode())
            .setMaterial(attestationVo.getMaterial())
            .setSchoolType(attestationVo.getSchoolType())
            .setSchoolTypeName(attestationVo.getSchoolTypeName())
            .setOpinion("");
        this.saveOrUpdate(applyUser);
        return MapUtil.ofHashMap("status", true, "msg", "成功");
    }

    @Override
    @Transactional(readOnly = true)
    public ApplyLoginUser queryLoginUser(String idNum) {
        return applyUserMapper.queryLoginUser(idNum);
    }

    @Override
    public Map<String, Object> revokeAttestation(ApplyUserVo applyUserVo, ApplyLoginUser attestationVo) {
        if (StringUtils.isBlank(attestationVo.getId())) {
            return MapUtil.ofHashMap("status", false, "msg", "未获取到登录账号信息，不能撤销");
        }
        ApplyUser user = this.getById(attestationVo.getId());
        if (!AttestStateEnum.认证中.getValue().equals(user.getState())) {
            return MapUtil.ofHashMap("status", false, "msg", "账号状态已经不在认证中，不能撤销");
        }
        LambdaUpdateWrapper<ApplyUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ApplyUser::getId, attestationVo.getId())
            .set(ApplyUser::getDel, "1")
            .set(ApplyUser::getOpinion, "主动撤销");
        this.update(updateWrapper);
        return MapUtil.ofHashMap("status", true, "msg", "撤销成功");
    }

    @Override
    public String queryForSbSwitch() {
        return this.applyUserMapper.queryForSbSwitch();
    }


    private boolean checkUnitCodeExitsOtherUser(String unitCode, String userId) {
        List<ApplyUser> applyUser = getUnitCodeExistApplyUser(unitCode, userId);
        return CollectionUtils.isNotEmpty(applyUser);
    }


    private List<ApplyUser> getUnitCodeExistApplyUser(String unitCode, String userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.ne("ID", userId);
        queryWrapper.eq("UNIT_CODE", unitCode);
        queryWrapper.eq("DEL", "0");
        queryWrapper.eq("STATE", AttestStateEnum.认证通过.getValue());
        return applyUserMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public JSONObject queryUnitCodeExitsOtherUser(JSONObject jsonObject) {
        JSONObject json = new JSONObject();
        //查询被审核的用户
        String applyUserId = jsonObject.getString("id");
        ApplyUser currApplyUser = applyUserMapper.selectById(applyUserId);
        TzhxySchool school = tzhxySchoolMapper.selectById(currApplyUser.getUnitCode());
        List<ApplyUser> applyUser = getUnitCodeExistApplyUser(currApplyUser.getUnitCode(), applyUserId);
        if (CollectionUtils.isNotEmpty(applyUser)) {
            StringBuffer buffer = new StringBuffer();
            applyUser.forEach(item -> {
                buffer.append(String.format(errorMsg, item.getName(), school.getSchoolName()));
            });
            json.put("msg", buffer.toString());
            json.put("status", true);
        } else {
            json.put("msg", "");
            json.put("status", false);
        }
        return json;
    }

    @Override
    public JSONObject auditApplyUser(JSONObject jsonObject) {
        JSONObject json = new JSONObject();
        boolean state = jsonObject.getBooleanValue("state");
        String opinion = jsonObject.getString("option");
        String applyUserId = jsonObject.getString("id");
        AuditUserVo auditUserVo = IeduUserController.load(null, null).getAuditUserVo();
        String auditUserId = auditUserVo.getId();
        //查询被审核的用户
        ApplyUser currApplyUser = applyUserMapper.selectById(applyUserId);
        //更新学校
        if (state == true) {
            //审核之前做判断，是否有正在处理的数据
            Integer count = queryCountBaseInfo(currApplyUser.getUnitCode());
            if (count > 0) {
                json.put("msg", "该学校目前还有未处理的审核数据，不能修改认证");
                json.put("status", false);
                return json;
            }

            List<ApplyUser> otherApplyUser = getUnitCodeExistApplyUser(currApplyUser.getUnitCode(), applyUserId);
            //判断被覆盖的用户所对应的学校是否有未审核的数据
            for (ApplyUser item : otherApplyUser) {
                Integer count2 = queryCountBaseInfo(item.getUnitCode());
                if (count2 > 0) {
                    json.put("msg", "该校目前还有未处理的审核数据，不能修改认证");
                    json.put("status", false);
                    return json;
                }
            }

            //挤用户下去
            if (CollectionUtils.isNotEmpty(otherApplyUser)) {
                LambdaUpdateWrapper<ApplyUser> userUpdateWrapper = new LambdaUpdateWrapper<>();
                userUpdateWrapper.set(ApplyUser::getDel, "1");
                userUpdateWrapper.set(ApplyUser::getOpinion, "被[" + applyUserId + "]覆盖");
                userUpdateWrapper.in(ApplyUser::getId, otherApplyUser.stream().map(item -> item.getId()).collect(Collectors.toList()));
                this.update(userUpdateWrapper);
            }

            TzhxySchool tzhxySchool = new TzhxySchool();
            tzhxySchool.setSchoolType(currApplyUser.getSchoolType());
            tzhxySchool.setSchoolTypeName(currApplyUser.getSchoolTypeName());

            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("UNIT_CODE", currApplyUser.getUnitCode());

            tzhxySchoolMapper.update(tzhxySchool, updateWrapper);
        }
        //审批
        ApplyUser currApplyUserUpdate = new ApplyUser();
        currApplyUserUpdate.setId(applyUserId);
        if (state) {
            currApplyUserUpdate.setState(AttestStateEnum.认证通过.getValue());
        } else {
            currApplyUserUpdate.setState(AttestStateEnum.认证不通过.getValue());
        }
        currApplyUserUpdate.setOpinion(opinion);
        currApplyUserUpdate.setReviewTime(new Date());
        currApplyUserUpdate.setReviewPerson(auditUserId);
        applyUserMapper.updateById(currApplyUserUpdate);
        json.put("msg", "成功");
        json.put("status", true);
        return json;
    }

    private Integer queryCountBaseInfo(String unitCode) {
        LambdaQueryWrapper<TzhxyBaseInfo> baseInfoQueryWrapper = new LambdaQueryWrapper<>();
        baseInfoQueryWrapper.eq(TzhxyBaseInfo::getUnitCode, unitCode)
            .eq(TzhxyBaseInfo::getDel, "0")
            .eq(TzhxyBaseInfo::getPcState, "1")
            .in(TzhxyBaseInfo::getState, new String[]{
                BizResultEnum.区教育局审核中.getValue(),
                BizResultEnum.已提交.getValue(),
                BizResultEnum.专家已评审.getValue(),
                BizResultEnum.初审通过.getValue(),
                BizResultEnum.专家评审中.getValue(),
                BizResultEnum.专家退回补充材料.getValue(),
                BizResultEnum.区教育局退回补充材料.getValue(),
                BizResultEnum.受理中心受理中.getValue(),
                BizResultEnum.受理中心复审中.getValue(),
                BizResultEnum.受理中心已受理.getValue(),
                BizResultEnum.受理中心复审通过.getValue(),
                BizResultEnum.市教育局审批中.getValue()
            });
        Integer count = baseInfoService.count(baseInfoQueryWrapper);
        return count;
    }
}
