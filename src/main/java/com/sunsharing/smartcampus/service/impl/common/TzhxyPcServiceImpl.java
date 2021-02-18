/*
 * @(#) TzhxyPcServiceImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-03 16:51:01
 */

package com.sunsharing.smartcampus.service.impl.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.share.common.base.exception.ResponseCode;
import com.sunsharing.share.common.base.exception.ShareBusinessException;
import com.sunsharing.smartcampus.constant.enums.BizResultEnum;
import com.sunsharing.smartcampus.constant.enums.PcStateEnum;
import com.sunsharing.smartcampus.mapper.common.TzhxyPcMapper;
import com.sunsharing.smartcampus.mapper.common.TzhxyTemplateMapper;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyBaseInfo;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyPc;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyTemplate;
import com.sunsharing.smartcampus.model.pojo.filed.CategoryConfig;
import com.sunsharing.smartcampus.model.vo.apply.DeclarationInitVo;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;
import com.sunsharing.smartcampus.service.apply.TzhxyBaseInfoService;
import com.sunsharing.smartcampus.service.common.TzhxyPcService;
import com.sunsharing.smartcampus.service.filed.CategoryConfigService;
import com.sunsharing.smartcampus.service.filed.DeclareConfigService;
import com.sunsharing.smartcampus.utils.ResultDataUtils;
import com.sunsharing.smartcampus.utils.SeqGenerateUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 批次信息表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-03
 */
@Service
public class TzhxyPcServiceImpl extends ServiceImpl<TzhxyPcMapper, TzhxyPc> implements TzhxyPcService {

    private static String templateMsg = "编号:[%s]的模板，分数设置校验不通过\n";

    @Autowired
    DeclareConfigService declareConfigService;
    @Autowired
    TzhxyPcMapper tzhxyPcMapper;
    @Autowired
    TzhxyTemplateMapper tzhxyTemplateMapper;

    @Autowired
    TzhxyBaseInfoService tzhxyBaseInfoService;

    @Autowired
    TzhxyTemplateServiceImpl templateService;

    @Autowired
    CategoryConfigService categoryConfigService;


    @Override
    @Transactional
    public ResponseCode addOrUpdate(String pcNo, String pcName, String mark) {
        if (StringUtils.isBlank(pcNo) || StringUtils.isBlank(pcName)) {
            return ResponseCode.of(1500, "非法入参");
        }
        LambdaQueryWrapper<TzhxyPc> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TzhxyPc::getPcNo, pcNo);
        queryWrapper.eq(TzhxyPc::getDel,"0");
        Integer count = this.count(queryWrapper);
        if (count > 0 && "add".equals(mark)) {
            return ResponseCode.of(1500, "该批次号已使用");
        }
        TzhxyPc pc = new TzhxyPc().setPcNo(pcNo).setPcName(pcName).setState(PcStateEnum.待启用.getValue());
        this.saveOrUpdate(pc);
        return ResponseCode.of(1200, "保存成功");
    }

    @Override
    @Transactional
    public ResponseCode enablePc(String pcNo) {
        //查询批次下面的模板
        List<TzhxyTemplate> tzhxyTemplates = tzhxyTemplateMapper.selectByPcId(pcNo);
        for (TzhxyTemplate tzhxyTemplate : tzhxyTemplates) {
            List<CategoryConfig> categoryByTemplateId = categoryConfigService
                .getCategoryByTemplateId(tzhxyTemplate.getTemplateNo());
            if (categoryByTemplateId.isEmpty()) {
                return ResponseCode.of(1500, "模板未设置考评项目，请设置完再启用！");
            }
        }
        for (TzhxyTemplate tzhxyTemplate : tzhxyTemplates) {
            if (StringUtils.isEmpty(tzhxyTemplate.getTemplateType())) {
                return ResponseCode.of(1500, "模板" + tzhxyTemplate.getTemplateNo() + "没有配置考评对象,请配置后再启用!");
            }
        }
        if (StringUtils.isBlank(pcNo)) {
            return ResponseCode.of(1500, "非法入参");
        }
        TzhxyPc oldPc = this.getById(pcNo);
        if (oldPc == null) {
            return ResponseCode.of(1500, "未获取到该批次信息");
        }
        if (PcStateEnum.归档.getValue().equals(oldPc.getState())) {
            return ResponseCode.of(1500, "该批次已归档");
        }
        LambdaQueryWrapper<TzhxyPc> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TzhxyPc::getState, PcStateEnum.启用.getValue());
        Integer count = this.count(queryWrapper);
        if (count > 0) {
            return ResponseCode.of(1500, "已有正在使用的批次，请先归档");
        }
        //批量更新模板
        LambdaQueryWrapper<TzhxyTemplate> templateWrapper = new LambdaQueryWrapper<>();
        templateWrapper.eq(TzhxyTemplate::getDel, "0").eq(TzhxyTemplate::getPcId, pcNo);
        List<TzhxyTemplate> templateList = templateService.list(templateWrapper);
        if (CollectionUtils.isEmpty(templateList)) {
            return ResponseCode.of(1500, "模板为空不能启用");
        }
        StringBuffer msg = new StringBuffer();
        templateList.forEach(item -> {
            Pair<Boolean, TzhxyTemplate> pair = categoryConfigService.calculateCategoryScore(item.getTemplateNo());
            if (!pair.getLeft()) {
                msg.append(String.format(templateMsg, item.getTemplateNo()));
            }
        });
        //模板校验不通过，跑出异常
        if (msg.length() > 0) {
            throw new ShareBusinessException(1500, msg.toString());
        }
        TzhxyPc newPc = new TzhxyPc().setPcNo(pcNo).setState(PcStateEnum.启用.getValue());
        this.saveOrUpdate(newPc);
        LambdaUpdateWrapper<TzhxyTemplate> templateUpdateWrapper = new LambdaUpdateWrapper<>();
        templateUpdateWrapper.set(TzhxyTemplate::getPcState, PcStateEnum.启用.getValue());
        templateUpdateWrapper.eq(TzhxyTemplate::getPcId, pcNo).eq(TzhxyTemplate::getDel, "0");
        templateService.update(templateUpdateWrapper);
        return ResponseCode.of(1200, "启用成功");
    }


    @Override
    @Transactional
    public ResponseCode placeOnfile(String pcNo) {
        //归档业务，不考虑正在审核的数据，直接归档
        LambdaUpdateWrapper<TzhxyPc> pcUpdateWrapper = new LambdaUpdateWrapper<>();
        pcUpdateWrapper.set(TzhxyPc::getState, PcStateEnum.归档.getValue());
        pcUpdateWrapper.eq(TzhxyPc::getPcNo, pcNo);
        this.update(pcUpdateWrapper);
        LambdaUpdateWrapper<TzhxyTemplate> templateUpdateWrapper = new LambdaUpdateWrapper<>();
        templateUpdateWrapper.set(TzhxyTemplate::getPcState, PcStateEnum.归档.getValue());
        templateUpdateWrapper.eq(TzhxyTemplate::getPcId, pcNo).eq(TzhxyTemplate::getDel, "0");
        templateService.update(templateUpdateWrapper);
        LambdaUpdateWrapper<TzhxyBaseInfo> baseInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        baseInfoLambdaUpdateWrapper.set(TzhxyBaseInfo::getPcState, PcStateEnum.归档.getValue());
        baseInfoLambdaUpdateWrapper.eq(TzhxyBaseInfo::getPcNo, pcNo).eq(TzhxyBaseInfo::getDel, "0");
        tzhxyBaseInfoService.update(baseInfoLambdaUpdateWrapper);
        tzhxyBaseInfoService.updateBatchJcssPlaceOnfile(pcNo);
        return ResponseCode.of(1200, "归档成功");
    }

    @Override
    @Transactional(readOnly = true)
    public DeclarationInitVo laodDeclarationInit(ApplyUserVo userVo, String declareId) {
        String pcNo = "";
        String g_id = "";
        String state = "";
        String pcState = "";
        boolean falg = false;
        String schoolType = userVo.getSchoolType();
        if (StringUtils.isBlank(declareId)) {
            TzhxyPc tzhxyPc = tzhxyPcMapper.queryNowAvailablePC(PcStateEnum.启用.getValue());
            if (Objects.isNull(tzhxyPc)) {
                throw new ShareBusinessException(1500, "未获取到有效批次信息");
            }
            //获取pcNo
            pcNo = tzhxyPc.getPcNo();
            //获取草稿初始化数据
            LambdaQueryWrapper<TzhxyBaseInfo> lambdaQuery = new LambdaQueryWrapper();
            lambdaQuery.eq(TzhxyBaseInfo::getDel, "0").eq(TzhxyBaseInfo::getUnitCode, userVo.getUnitCode())
                .eq(TzhxyBaseInfo::getPcNo, pcNo).eq(TzhxyBaseInfo::getState, BizResultEnum.草稿.getValue())
                .orderByDesc(TzhxyBaseInfo::getUpdateTime);
            List<TzhxyBaseInfo> baseInfoList = tzhxyBaseInfoService.list(lambdaQuery);
            if (baseInfoList.size() == 0) {//该批次号没有草稿，直接获取最新草稿数据
                TzhxyBaseInfo baseInfo = tzhxyBaseInfoService.queryFirstForUnitCode(userVo.getUnitCode());
                if (!Objects.isNull(baseInfo)) {
                    g_id = baseInfo.getDeclareId();
                    state = baseInfo.getState();
                    pcState = baseInfo.getPcState();
                    falg = true;
                }
            } else {
                g_id = baseInfoList.get(0).getDeclareId();
                state = baseInfoList.get(0).getState();
                pcState = baseInfoList.get(0).getPcState();
            }
        } else {
            TzhxyBaseInfo baseInfo = tzhxyBaseInfoService.getById(declareId);
            pcNo = baseInfo.getPcNo();
            schoolType = baseInfo.getSchoolType();
            g_id = declareId;
            state = baseInfo.getState();
            pcState = baseInfo.getPcState();
        }
        //获取自评模板信息
        List<TzhxyTemplate> tzhxyTemplate = tzhxyTemplateMapper.selectTemplateByPcnoAndSchooltype(pcNo, schoolType);
        if (tzhxyTemplate.isEmpty()) {
            throw new ShareBusinessException(1500, "未获取到模板信息");
        }
        String templateId = tzhxyTemplate.get(0).getTemplateNo();
        Map<String, Object> basicFieldList = declareConfigService.findBasicFieldList(templateId, falg ? null : g_id);
        return new DeclarationInitVo()
            .setPcNo(pcNo)
            .setTreeData(basicFieldList)
            .setG_id(g_id)
            .setState(state)
            .setApplyUserVo(userVo.toApplyLoginUser())
            .setPcState(pcState)
            .setYxsb(this.tzhxyBaseInfoService.checkCommitBaseInfoForPcNo(pcNo, userVo.getUnitCode()));
    }

    @Override
    @Transactional
    public Map copyPc(String pcIds) {
        //根据模板id查询出list,复制模板
        if (StringUtils.isBlank(pcIds)) {
            throw new ShareBusinessException(1500, "未选中批次");
        }
        List<String> listIds = Arrays.asList(pcIds.split(","));
        //循环复制批次
        listIds.forEach(s -> {
            //查询出当前复制批次下所有模板
            List<TzhxyTemplate> tzhxyTemplates = templateService.selectTemplateByPcId(s);
            String templateIds = tzhxyTemplates.stream().
                map(TzhxyTemplate::getTemplateNo).collect(Collectors.joining(","));
            //查询批次并复制
            TzhxyPc tzhxyPc = tzhxyPcMapper.selectDiyById(s);
            String newPcId = SeqGenerateUtil.getId() + "";
            tzhxyPc.setState("0");
            tzhxyPc.setPcNo(newPcId);
            tzhxyPcMapper.insert(tzhxyPc);
            //模板如果为空不复制
            if (StringUtils.isBlank(templateIds)) {
                return;
            }
            templateService.copyTemp(s, newPcId, templateIds);
        });
        return ResultDataUtils.packParams(true, "复制成功");

    }

    @Override
    public Map listAllPc() {
        return ResultDataUtils.packParams(tzhxyPcMapper.selectAll(), "查询成功");
    }
}
