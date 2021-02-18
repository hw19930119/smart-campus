package com.sunsharing.smartcampus.service.impl.comment;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.mapper.apply.TzhxyBaseInfoMapper;
import com.sunsharing.smartcampus.mapper.comment.TzhxyCommentMapper;
import com.sunsharing.smartcampus.mapper.comment.TzhxyDpConfigMapper;
import com.sunsharing.smartcampus.mapper.common.TzhxyTemplateMapper;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyBaseInfo;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyComment;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyDpConfig;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyTemplate;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.comment.CommentVo;
import com.sunsharing.smartcampus.model.vo.comment.DpTreeDto;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.service.comment.TzhxyCommentService;
import com.sunsharing.smartcampus.service.comment.TzhxyDpConfigService;
import com.sunsharing.smartcampus.service.impl.common.DmService;
import com.sunsharing.smartcampus.web.common.IeduUserController;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 专家点评信息表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-10-29
 */
@Service
public class TzhxyCommentServiceImpl extends ServiceImpl<TzhxyCommentMapper, TzhxyComment> implements TzhxyCommentService {

    public static final String strMsg = "%s:%s\n";

    @Autowired
    TzhxyCommentMapper tzhxyCommentMapper;

    @Autowired
    TzhxyDpConfigMapper tzhxyDpConfigMapper;

    @Autowired
    TzhxyDpConfigService tzhxyDpConfigService;
    @Autowired
    TzhxyTemplateMapper tzhxyTemplateMapper;

    @Autowired
    TzhxyBaseInfoMapper tzhxyBaseInfoMapper;

    @Override
    public JSONObject commitComment(JSONObject jsonObject) {
        JSONObject result = DmService.getDefaultReturnJSON();
        String bussinessKey = jsonObject.getString("bussinessKey");
        String comment = jsonObject.getString("comment");
        long dpConfigUpdateTime = jsonObject.getLongValue("dpConfigUpdateTime");

        IeduUserVo iEduUserVo = IeduUserController.load(null, null);
        AuditUserVo auditUserVo = iEduUserVo.getAuditUserVo();
        boolean checkDpConfigChange = checkDpConfigChangeByBussinessKeyAndExpertId(bussinessKey, auditUserVo.getId(), dpConfigUpdateTime);
        if (checkDpConfigChange == true) {
            result.put("status", false);
            result.put("refresh", true);
            result.put("msg", "数据状态已经改变请刷新");
            return result;
        }

        TzhxyBaseInfo tzhxyBaseInfo = tzhxyBaseInfoMapper.selectById(bussinessKey);
        String pcNo = tzhxyBaseInfo.getPcNo();
        String schoolType = tzhxyBaseInfo.getSchoolType();


        //获取templateId
        List<TzhxyTemplate> tzhxyTemplate = tzhxyTemplateMapper.selectTemplateByPcnoAndSchooltype(pcNo, schoolType);
        if (tzhxyTemplate.isEmpty()) {
            result.put("status", false);
            result.put("msg", "未获取到模板信息");
            return result;
        }
        String templateId = tzhxyTemplate.get(0).getTemplateNo();
        String dpId = getDpIdByExpert(auditUserVo.getId(), pcNo, templateId);
        if (dpId == null) {
            result.put("status", false);
            result.put("msg", "未获取分配信息");
            return result;
        }


        QueryWrapper<TzhxyComment> queryWrapper = new QueryWrapper();
        queryWrapper.eq("DECLARE_ID", bussinessKey);
        queryWrapper.eq("EXPERT_ID", auditUserVo.getId());
        queryWrapper.eq("DEL", "0");
        TzhxyComment tzhxyComment = tzhxyCommentMapper.selectOne(queryWrapper);
        if (tzhxyComment != null) {
            TzhxyComment tzhxyCommentNew = new TzhxyComment();
            tzhxyCommentNew.setCommentId(tzhxyComment.getCommentId());
            tzhxyCommentNew.setUpdatePerson(auditUserVo.getId());
            //tzhxyCommentNew.setUpdateTime(new Date());
            tzhxyCommentNew.setDeclareText(comment);
            tzhxyCommentMapper.updateById(tzhxyCommentNew);
        } else {
            tzhxyComment = new TzhxyComment();
            String id = com.sunsharing.component.utils.base.StringUtils.generateUUID();
            tzhxyComment.setCommentId(id);
            tzhxyComment.setDeclareId(bussinessKey);
            tzhxyComment.setDeclareText(comment);
            tzhxyComment.setDpId(dpId);
            tzhxyComment.setExpertId(auditUserVo.getId());
            //tzhxyComment.setCreateTime(new Date());
            tzhxyComment.setCreatePerson(auditUserVo.getId());
            tzhxyCommentMapper.insert(tzhxyComment);
        }
        return result;
    }

    private String getDpIdByExpert(String expertId, String pcNo, String templateId) {
        QueryWrapper<TzhxyDpConfig> queryWrapper = new QueryWrapper();
        queryWrapper.eq("EXPERT_ID", expertId);
        queryWrapper.eq("PC_NO", pcNo);
        queryWrapper.eq("TEMPLATE_ID", templateId);
        queryWrapper.eq("DEL", "0");
        List<TzhxyDpConfig> tzhxyDpConfigList = tzhxyDpConfigMapper.selectList(queryWrapper);
        if (tzhxyDpConfigList.isEmpty()) {
            return null;
        }
        return tzhxyDpConfigList.get(0).getDpId();
    }

    private TzhxyDpConfig getDpConfigByBussinessKeyAndExpertId(String bussinessKey, String expertId) {
        TzhxyBaseInfo tzhxyBaseInfo = tzhxyBaseInfoMapper.selectById(bussinessKey);
        if (tzhxyBaseInfo == null) {
            return null;
        }
        List<TzhxyTemplate> tzhxyTemplate
            = tzhxyTemplateMapper.selectTemplateByPcnoAndSchooltype(tzhxyBaseInfo.getPcNo(), tzhxyBaseInfo.getSchoolType());
        if (tzhxyTemplate.isEmpty()) {
            return null;
        }
        String templateId = tzhxyTemplate.get(0).getTemplateNo();

        String dpId = getDpIdByExpert(expertId, tzhxyBaseInfo.getPcNo(), templateId);
        if (dpId == null) {
            return null;
        }
        TzhxyDpConfig config = tzhxyDpConfigMapper.selectById(dpId);
        return config;
    }

    private boolean checkDpConfigChangeByBussinessKeyAndExpertId(String bussinessKey, String expertId, long dpConfigUpdateTime) {
        boolean changeFlag = false;
        TzhxyDpConfig dpConfig = getDpConfigByBussinessKeyAndExpertId(bussinessKey, expertId);
        if (dpConfig == null) {
            if (dpConfigUpdateTime != 0) {
                changeFlag = true;
            }
        } else {
            Date updateDateNew = dpConfig.getUpdateTime();
            if (updateDateNew == null) {
                if (dpConfigUpdateTime != 0) {
                    changeFlag = true;
                }
            } else {
                if (updateDateNew.getTime() != dpConfigUpdateTime) {
                    changeFlag = true;
                }
            }
        }
        return changeFlag;
    }


    @Override
    public JSONObject getCommentByExpert(JSONObject jsonObject) {
        JSONObject result = DmService.getDefaultReturnJSON();
        String bussinessKey = jsonObject.getString("bussinessKey");

        IeduUserVo iEduUserVo = IeduUserController.load(null, null);
        AuditUserVo auditUserVo = iEduUserVo.getAuditUserVo();

        QueryWrapper<TzhxyComment> queryWrapper = new QueryWrapper();
        queryWrapper.eq("DECLARE_ID", bussinessKey);
        queryWrapper.eq("EXPERT_ID", auditUserVo.getId());
        queryWrapper.eq("DEL", "0");
        TzhxyComment tzhxyComment = tzhxyCommentMapper.selectOne(queryWrapper);
        result.put("tzhxyComment", tzhxyComment);
        TzhxyDpConfig dpConfig = getDpConfigByBussinessKeyAndExpertId(bussinessKey, auditUserVo.getId());
        if (dpConfig != null && dpConfig.getUpdateTime() != null) {
            result.put("dpConfigUpdateTime", dpConfig.getUpdateTime().getTime());
        } else {
            result.put("dpConfigUpdateTime", 0);
        }
        return result;
    }

    @Override
    public JSONObject queryCommentClass(JSONObject jsonObject) {
        JSONObject result = DmService.getDefaultReturnJSON();
        String bussinessKey = jsonObject.getString("bussinessKey");
        List<TzhxyTemplate> templateList = this.tzhxyTemplateMapper.selectByDeclarId(bussinessKey);
        if (CollectionUtils.isEmpty(templateList) || templateList.size() == 0 || templateList.size() > 1) {
            result.put("status", false);
            result.put("msg", "未获取到正确的模板信息");
            return result;
        }
        TzhxyTemplate tzhxyTemplate = templateList.get(0);
        // 查询树形结构
        List<DpTreeDto> treeDtoList = tzhxyDpConfigService.selectTreeDto(tzhxyTemplate.getTemplateNo());
        //查询点评信息
        createDistribution(tzhxyTemplate.getTemplateNo(), bussinessKey, treeDtoList);
        result.put("status", true);
        result.put("commentList", treeDtoList);
        return result;
    }

    public void createDistribution(String templateId, String declareId, List<DpTreeDto> treeDtos) {
        //查询专家分配的评论
        List<CommentVo> commentList = this.tzhxyCommentMapper.queryCommentForTemplateId(templateId, declareId);
        List<DpTreeDto> newList = treeDtos.stream().map(item -> {
            List<DpTreeDto> var1 = item.getChildren();
            return CollectionUtils.isNotEmpty(var1) ? var1 : new ArrayList<DpTreeDto>();
        }).flatMap(List::stream).map(item -> {
            List<DpTreeDto> var2 = item.getChildren();
            return CollectionUtils.isNotEmpty(var2) ? var2 : new ArrayList<DpTreeDto>();
        }).flatMap(List::stream).collect(Collectors.toList());
        //过滤出二级目录
        newList.forEach(item -> {
            filterGroupDistribution(commentList, item);
        });
    }

    private void filterGroupDistribution(List<CommentVo> commentList, DpTreeDto dto) {
        //总共三级目录过滤
        List<DpTreeDto> dtoList = dto.getChildren();
        dtoList.forEach(item -> {
            String key = item.getKey();
            CommentVo config = includedFieldId(key, commentList);
            if (!Objects.isNull(config)) {
                boolean falg = dto.addExpertName(config.getUserName());
                if (falg && StringUtils.isNotBlank(config.getContext())) {
                    dto.addContext(String.format(strMsg, config.getUserName(), config.getContext()));
                }
            }
        });
    }

    private CommentVo includedFieldId(String key, List<CommentVo> configList) {
        for (CommentVo item : configList) {
            if (item.getFieldIds().contains(key)) {
                return item;
            }
        }
        return null;
    }
}
