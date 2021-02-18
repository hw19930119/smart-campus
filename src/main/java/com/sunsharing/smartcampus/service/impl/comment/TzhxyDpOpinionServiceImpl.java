package com.sunsharing.smartcampus.service.impl.comment;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.component.utils.base.StringUtils;
import com.sunsharing.share.common.text.StringUtil;
import com.sunsharing.smartcampus.mapper.comment.TzhxyCommentMapper;
import com.sunsharing.smartcampus.mapper.comment.TzhxyDpOpinionMapper;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyComment;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyDpConfig;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyDpOpinion;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.service.comment.TzhxyDpConfigService;
import com.sunsharing.smartcampus.service.comment.TzhxyDpOpinionService;
import com.sunsharing.smartcampus.service.impl.common.DmService;
import com.sunsharing.smartcampus.web.common.IeduUserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 末级指标点评详情 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-11-04
 */
@Service
public class TzhxyDpOpinionServiceImpl extends ServiceImpl<TzhxyDpOpinionMapper, TzhxyDpOpinion> implements TzhxyDpOpinionService {

    @Autowired
    TzhxyDpConfigService tzhxyDpConfigService;

    @Autowired
    TzhxyCommentMapper tzhxyCommentMapper;

    @Autowired
    TzhxyDpOpinionMapper tzhxyDpOpinionMapper;

    @Transactional
    @Override
    public Map<String, Object> saveDpOpinion(JSONObject reqParams) {
        JSONObject result = DmService.getDefaultReturnJSON();
        String bussinessKey = reqParams.getString("bussinessKey");
        String codeKey = reqParams.getString("codeKey");
        String codeLabel = reqParams.getString("codeLabel");
        String fieldId = reqParams.getString("fieldId");
        String dpConfigUpdateTime = reqParams.getString("dpConfigUpdateTime");
        long lastUpateTime = StringUtils.isBlank(dpConfigUpdateTime) == true ? 0 : Long.valueOf(dpConfigUpdateTime);
        //参数验证
        if (StringUtil.isAllBlank(bussinessKey, codeKey, codeLabel, fieldId)) {
            result.put("status", false);
            result.put("refresh", false);
            result.put("msg", "入参错误");
            return result;
        }
        //登录验证
        IeduUserVo iEduUserVo = IeduUserController.load(null, null);
        AuditUserVo auditUserVo = iEduUserVo.getAuditUserVo();
        //获取专家分配的DP_ID
        TzhxyDpConfig dpConfig = this.tzhxyDpConfigService.queryDpConfigForDeclareIdAndExpertId(bussinessKey,
            auditUserVo.getId());
        if (checkDpConfigWhetherUpdate(dpConfig, lastUpateTime)) {
            result.put("status", false);
            result.put("refresh", true);
            result.put("msg", "数据状态已经改变请刷新");
            return result;
        }
        //保存点评汇总表
        QueryWrapper<TzhxyComment> queryWrapper = new QueryWrapper();
        queryWrapper.eq("DECLARE_ID", bussinessKey);
        queryWrapper.eq("EXPERT_ID", auditUserVo.getId());
        queryWrapper.eq("DEL", "0");
        TzhxyComment tzhxyComment = tzhxyCommentMapper.selectOne(queryWrapper);
        String commentId = "";
        if (!Objects.isNull(tzhxyComment)) {
            commentId = tzhxyComment.getCommentId();
            tzhxyComment.setCommentId(commentId);
            tzhxyComment.setUpdatePerson(auditUserVo.getId());
            tzhxyComment.setUpdateTime(new Date());
            tzhxyCommentMapper.updateById(tzhxyComment);
        } else {
            commentId = StringUtils.generateUUID();
            tzhxyComment = new TzhxyComment();
            tzhxyComment.setCommentId(commentId);
            tzhxyComment.setDeclareId(bussinessKey);
            tzhxyComment.setDpId(dpConfig.getDpId());
            tzhxyComment.setExpertId(auditUserVo.getId());
            tzhxyComment.setCreatePerson(auditUserVo.getId());
            tzhxyCommentMapper.insert(tzhxyComment);
        }
        //保存末级指标的评价
        LambdaQueryWrapper<TzhxyDpOpinion> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TzhxyDpOpinion::getExpertId, auditUserVo.getId())
            .eq(TzhxyDpOpinion::getDeclareId, bussinessKey)
            .eq(TzhxyDpOpinion::getDel, "0")
            .eq(TzhxyDpOpinion::getDpId, dpConfig.getDpId())
            .eq(TzhxyDpOpinion::getFieldId, fieldId);
        List<TzhxyDpOpinion> tzhxyDpOpinionList = tzhxyDpOpinionMapper.selectList(lambdaQueryWrapper);
        TzhxyDpOpinion tzhxyDpOpinion = null;
        if (Objects.isNull(tzhxyDpOpinionList) || tzhxyDpOpinionList.size() == 0) {
            tzhxyDpOpinion = new TzhxyDpOpinion();
            tzhxyDpOpinion.setBh0000(codeKey)
                .setMc0000(codeLabel)
                .setCommentId(commentId)
                .setCreatePerson(auditUserVo.getId())
                .setDeclareId(bussinessKey)
                .setDpId(dpConfig.getDpId())
                .setExpertId(auditUserVo.getId())
                .setFieldId(fieldId)
                .setOpinionId(StringUtils.generateUUID());
        } else {
            tzhxyDpOpinion = tzhxyDpOpinionList.get(0);
            tzhxyDpOpinion.setBh0000(codeKey)
                .setMc0000(codeLabel)
                .setUpdatePerson(auditUserVo.getId())
                .setUpdateTime(new Date());

        }
        this.saveOrUpdate(tzhxyDpOpinion);
        result.put("status", true);
        result.put("refresh", true);
        result.put("msg", "保存成功");
        return result;
    }

    private boolean checkDpConfigWhetherUpdate(TzhxyDpConfig dpConfig, long lastDpTime) {
        if (Objects.isNull(dpConfig)) {//配置被删掉的情况
            return true;
        } else {
            Date oldUpdateTime = dpConfig.getUpdateTime();
            if (oldUpdateTime == null) {
                if (lastDpTime != 0) {
                    return true;
                }
            } else {
                if (oldUpdateTime.getTime() != lastDpTime) {
                    return true;
                }
            }
        }
        return false;
    }
}
