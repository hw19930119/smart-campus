package com.sunsharing.smartcampus.service.impl.apply;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.common.utils.StringUtils;
import com.sunsharing.share.common.base.DateUtil;
import com.sunsharing.share.common.base.exception.ResponseCode;
import com.sunsharing.share.common.base.exception.ShareBusinessException;
import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.smartcampus.constant.enums.BizResultEnum;
import com.sunsharing.smartcampus.constant.enums.JcssDmEnum;
import com.sunsharing.smartcampus.constant.enums.PcStateEnum;
import com.sunsharing.smartcampus.mapper.apply.TzhxyBaseInfoMapper;
import com.sunsharing.smartcampus.mapper.apply.TzhxyJcssMapper;
import com.sunsharing.smartcampus.mapper.audit.ProcInstMapper;
import com.sunsharing.smartcampus.mapper.common.DmDataMapper;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyBaseInfo;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyJcss;
import com.sunsharing.smartcampus.model.vo.apply.BaseInfoVo;
import com.sunsharing.smartcampus.model.vo.apply.FieldVo;
import com.sunsharing.smartcampus.model.vo.apply.JcssVo;
import com.sunsharing.smartcampus.model.vo.apply.SubmitParamVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.service.apply.TzhxyBaseInfoService;
import com.sunsharing.smartcampus.service.apply.TzhxyJcssService;
import com.sunsharing.smartcampus.service.audit.ProcInstService;
import com.sunsharing.smartcampus.service.audit.SupplyIndexService;
import com.sunsharing.smartcampus.service.biz.UserService;
import com.sunsharing.smartcampus.service.filed.FieldValueService;
import com.sunsharing.smartcampus.service.impl.dm.SmartDmServiceImpl;
import com.sunsharing.smartcampus.utils.ReflectUtils;
import com.sunsharing.smartcampus.utils.ResultDataUtils;
import com.sunsharing.smartcampus.web.common.IeduUserController;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 学校基本信息 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-04
 */
@Service
public class TzhxyBaseInfoServiceImpl extends ServiceImpl<TzhxyBaseInfoMapper, TzhxyBaseInfo> implements TzhxyBaseInfoService {

    @Autowired
    SmartDmServiceImpl smartDmService;

    @Autowired
    TzhxyBaseInfoMapper baseInfoMapper;

    @Autowired
    ProcInstMapper procInstMapper;

    @Autowired
    DmDataMapper dmDataMapper;

    @Autowired
    TzhxyJcssMapper tzhxyJcssMapper;

    @Autowired
    TzhxyJcssService tzhxyJcssService;

    @Autowired
    ProcInstService procInstService;

    @Autowired
    FieldValueService fieldValueService;
    @Autowired
    SupplyIndexService supplyIndexService;
    @Autowired
    UserService userService;

    @Override
    public TzhxyBaseInfo queryFirstForUnitCode(String unitCode) {
        TzhxyBaseInfo baseInfo = baseInfoMapper.queryFirstForUnitCode(unitCode);
        return baseInfo;
    }

    @Override
    public Map queryApplyAllByBusinessKey(String businessKey) {
        //查询学校基本情况
        Map<String, Object> baseInfoMap = queryBaseInfoByBusinessKey(businessKey);
        //查询学校信息化建设基础设施基本信息
        Map<String, Object> jcssMap = queryJcxxByBusinessKey(businessKey);
        //查询星级与评分
        Map<String, Object> reviewRusult = baseInfoMapper.queryReviewResultForBusinessKey(businessKey);
        return ResultDataUtils.packParams(MapUtil.ofHashMap("baseInfo", baseInfoMap, "jcss", jcssMap, "reviewResult", reviewRusult), "查询成功");
    }

    @Override
    public Map<String, Object> queryBaseInfoByBusinessKey(String businessKey) {
        //查询出值
        BaseInfoVo baseInfoVo = baseInfoMapper.selectVoById(businessKey);
        //转换为Map方便循环
        Map<String, Object> columnMap = MapUtil.objectToMapKeepType(baseInfoVo);
        //构建返回值
        Map<String, Object> resultMap = new HashMap<>();
        //遍历字段
        columnMap.forEach((k, v) -> {
            //创建字段封装对象
            FieldVo fieldVo = new FieldVo();
            fieldVo.setColumnName(k);
            fieldVo.setColumnValue(v);
            resultMap.put(k, fieldVo);
        });
        //学校类型字段封装
        String schoolType = baseInfoVo.getSchoolType();
        List<Map<String, Object>> dmSchoolTypeList = smartDmService.loadSmartDm("DM_SCHOOL_TYPE");
        packageBaseInfoField(resultMap, dmSchoolTypeList, "schoolType", schoolType);
        //申报重点字段封装
        String sbzd = baseInfoVo.getSbzd();
        List<Map<String, Object>> dmSchoolTypeList4 = dmDataMapper.querySchoolTypeHeadFour();
        packageBaseInfoField(resultMap, dmSchoolTypeList4, "sbzd", sbzd);
        return resultMap;
    }

    @Override
    public Map<String, Object> queryJcxxByBusinessKey(String businessKey) {
        JcssVo jcssVo = tzhxyJcssMapper.selectVoByBusinessKey(businessKey);
        if (jcssVo == null) {
            jcssVo = new JcssVo();
        }
        //为了方便stream方式调用,stream调用的变量只能是final
        JcssVo handle = jcssVo;
        //转换为Map方便循环
        Map<String, Object> columnMap = MapUtil.objectToMapKeepType(handle);
        //构建返回值
        Map<String, Object> resultMap = new HashMap<>();

        //遍历字段
        columnMap.forEach((k, v) -> {
            //创建字段封装对象
            FieldVo fieldVo = new FieldVo();
            fieldVo.setColumnName(k);
            fieldVo.setColumnValue(v);
            resultMap.put(k, fieldVo);
        });
        //表码字段包装
        //网络接入方式DM_ZH_SELECT-WLJR,wlJrfs
        //网络覆盖范围DM_ZH_SELECT-WLFW,wlFgfw
        //网络中心DM_ZH_SELECT-WLZX,wlZx
        //门户管理DM_ZH_SELECT-MHGL,xtMhgl
        //门户管理DM_ZH_SELECT-SJGL,xtSjgl
        //门户管理DM_ZH_SELECT-KFYJR,xtKfyjr
        //教育管理系统DM_ZH_SELECT-JYGLXT,rjJyglxt
        //教育应用及管理系统DM_ZH_SELECT-JYYYGLXT,rjJyyyjglxt
        //学习管理系统DM_ZH_SELECT-XXGLXT,rjXxglxt
        //师生成长管理系统DM_ZH_SELECT-SSCZGLXT,rjSsczglxt
        //教研平台DM_ZH_SELECT-JYPT,rjJypt
        //家校互通学习平台DM_ZH_SELECT-JYPT,rjJxhtpt
        //数字图上资源DM_ZH_SELECT-SZTS,zySzts
        //选修课程DM_ZH_SELECT-XXKC,zyXxkc
        //校门课程DM_ZH_SELECT-XMKC,zyXmkc
        //个性话学习资源DM_ZH_SELECT-GXHXXZY,zyGxhxxzy
        Arrays.stream(JcssDmEnum.values())
            .forEach(en -> {
                String columnName = en.getName();
                List<Map<String, Object>> dmList = smartDmService.loadJcssZhSelectDm(en.getValue());
                String value = ReflectUtils.get(handle, en.getName(), String.class);
                FieldVo fieldVo = new FieldVo();
                fieldVo.setDmList(dmList);
                if (!StringUtils.isBlank(value)) {
                    List<String> vals = new ArrayList<>();
                    if (value.indexOf(",") > 0) {
                        vals.addAll(Arrays.asList(value.split(",")));
                    } else {
                        vals.add(value);
                    }
                    List<Map<String, Object>> mapValue = dmList.stream()
                        .filter(m -> vals.contains(MapUtils.getString(m, "id", "")))
                        .collect(Collectors.toList());
                    fieldVo.setColumnValue(mapValue);
                }
                fieldVo.setColumnName(columnName);
                fieldVo.setIsDm(1);
                resultMap.put(columnName, fieldVo);
            });
        return resultMap;

    }

    @Override
    @Transactional
    public Integer updateBatchJcssPlaceOnfile(String pcNo) {
        return this.baseInfoMapper.updateBatchJcssPlaceOnfile(pcNo, PcStateEnum.归档.getValue());
    }

    @Override
    @Transactional
    public ResponseCode delBaseInfo(String declareId) {
        TzhxyBaseInfo baseInfo = baseInfoMapper.selectById(declareId);
        if (!baseInfo.getState().equals(BizResultEnum.草稿.getValue())) {
            return ResponseCode.of(1500, "只有草稿状态能够删除");
        }
        LambdaUpdateWrapper<TzhxyBaseInfo> updateWrapperBaseInfo = new LambdaUpdateWrapper<>();
        updateWrapperBaseInfo.set(TzhxyBaseInfo::getDel, "1");
        updateWrapperBaseInfo.eq(TzhxyBaseInfo::getDeclareId, declareId);
        this.update(updateWrapperBaseInfo);
        LambdaUpdateWrapper<TzhxyJcss> updateWrapperJcss = new LambdaUpdateWrapper<>();
        updateWrapperJcss.set(TzhxyJcss::getDel, "1");
        updateWrapperJcss.eq(TzhxyJcss::getDeclareId, declareId);
        this.tzhxyJcssService.update(updateWrapperJcss);
        return ResponseCode.of(1200, "删除成功");
    }

    @Override
    public boolean checkCommitBaseInfoForPcNo(String pcNo, String unitCode) {
        LambdaQueryWrapper<TzhxyBaseInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TzhxyBaseInfo::getPcNo, pcNo)
            .eq(TzhxyBaseInfo::getDel, "0")
            .eq(TzhxyBaseInfo::getUnitCode, unitCode)
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
                BizResultEnum.市教育局审批中.getValue(),
                BizResultEnum.审批通过.getValue(),
                BizResultEnum.受理中心退回补充.getValue(),
                BizResultEnum.受理中心复审退回补充.getValue(),
                BizResultEnum.市教育局终审退回补充.getValue(),
                BizResultEnum.退回受理中心.getValue()
            });
        Integer count = this.count(queryWrapper);
        return count == 0;
    }

    /** 撤销 */
    @Override
    @Transactional
    public ResponseCode revokeDeclaration(SubmitParamVo paramVo) {
        if (StringUtils.isBlank(paramVo.getDeclareId())) {
            return ResponseCode.of(200, "入参异常");
        }
        TzhxyBaseInfo baseInfo = this.getById(paramVo.getDeclareId());
        if (Objects.isNull(baseInfo)) {
            return ResponseCode.of(200, "未获取到申报信息");
        }
        String lastState = baseInfo.getLastState();
        if (StringUtils.isBlank(lastState) || StringUtils.isEquals(lastState, BizResultEnum.草稿.getValue())) {
            procInstMapper.clearAudit(paramVo.getDeclareId());
            return ResponseCode.of(100, "撤销成功");
        } else {
            return ResponseCode.of(200, "只有已提交状态可以进行撤销");
        }
    }

    @Override
    public Map<String, Object> querySystemConfigBackEndTime() {
        return baseInfoMapper.querySystemConfigBackEndTime();
    }

    @Override
    public void updateSysConfigMack() {
        baseInfoMapper.updateSysConfigMack();
    }

    @Override
    @Transactional
    public ResponseCode submitDeclare(SubmitParamVo submitParamVo) {
        //判断同批次号是否提交过申报
        String declareId = submitParamVo.getDeclareId();
        TzhxyBaseInfo baseInfo = this.baseInfoMapper.selectById(declareId);
        if (!checkCommitBaseInfoForPcNo(baseInfo.getPcNo(), baseInfo.getUnitCode())
            && (StringUtils.isBlank(baseInfo.getState())
            || StringUtils.isEquals(baseInfo.getState(), BizResultEnum.草稿.getValue()))) {
            return ResponseCode.of(200, "该批次号已提交过申报，不能重复提交");
        }
        //判断是否所有指标都已自评
        Integer count = fieldValueService.queryCheckFieldValueForDeclareId(declareId);
        if (count > 0) {
            return ResponseCode.of(200, "未完成自评，请返回继续，完成后再提交");
        }
        /*//检查退回指标是否改完
        if (supplyIndexService.checkGivebackChangedFinish(declareId) == false) {
            return ResponseCode.of(200, "请先完成退回指标的修改");
        }*/

        //启动流程，设置已提交步骤
        JSONObject params = new JSONObject();
        params.put("bussinessKey", baseInfo.getDeclareId());
        params.put("varJson", JSONObject.toJSONString(baseInfo));
        IeduUserVo iEduUserVo = null;
        if (submitParamVo.isSfshttj()) {
            //重构ApplyUserVo
            String userId = baseInfo.getCreatePerson();
            iEduUserVo = userService.getIeduUserVoByIdNum(null, userId);
        } else {
            iEduUserVo = IeduUserController.load(null, null);
        }
        JSONObject result = procInstService.startOrRestartProcess(params, iEduUserVo);
        if (result.getBooleanValue("status") == false) {
            throw new ShareBusinessException(200, result.getString("msg"));
        }
        //更新业务表状态
        String oldState = baseInfo.getState();
        LambdaUpdateWrapper<TzhxyBaseInfo> updateWrapperBaseInfo = new LambdaUpdateWrapper<>();
        updateWrapperBaseInfo.eq(TzhxyBaseInfo::getDeclareId, declareId);
        updateWrapperBaseInfo.set(TzhxyBaseInfo::getLastState, oldState)
            .set(TzhxyBaseInfo::getState, BizResultEnum.已提交.getValue());
        if (BizResultEnum.草稿.getValue().equals(oldState)) {
            updateWrapperBaseInfo.set(TzhxyBaseInfo::getCommitTime, DateUtil.toString(new Date(), DateUtil.DATETIME_FORMAT_14));
        }
        this.update(updateWrapperBaseInfo);
        LambdaUpdateWrapper<TzhxyJcss> updateWrapperJcss = new LambdaUpdateWrapper<>();
        updateWrapperJcss.eq(TzhxyJcss::getDeclareId, declareId);
        updateWrapperJcss.set(TzhxyJcss::getLastState, oldState).set(TzhxyJcss::getState, BizResultEnum.已提交.getValue());
        this.tzhxyJcssService.update(updateWrapperJcss);
        return ResponseCode.of(100, result.getString("msg"));
    }

    private void packageBaseInfoField(Map<String, Object> resultMap,
                                      List<Map<String, Object>> dmList, String columnName, String value) {
        List<String> vals = new ArrayList<>();
        if (value.indexOf(",") > 0) {
            vals.addAll(Arrays.asList(value.split(",")));
        } else {
            vals.add(value);
        }
        List<Map<String, Object>> mapValue = dmList.stream()
            .filter(m -> vals.contains(MapUtils.getString(m, "id", "")))
            .collect(Collectors.toList());
        FieldVo fieldVo = new FieldVo();
        fieldVo.setDmList(dmList);
        fieldVo.setColumnName(columnName);
        fieldVo.setIsDm(1);
        fieldVo.setColumnValue(mapValue);
        resultMap.put(columnName, fieldVo);
    }


}
