package com.sunsharing.smartcampus.service.impl.comment;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.common.utils.StringUtils;
import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.smartcampus.mapper.comment.TzhxyDpConfigMapper;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyDpConfig;
import com.sunsharing.smartcampus.model.pojo.filed.CategoryConfig;
import com.sunsharing.smartcampus.model.vo.comment.DpTreeDto;
import com.sunsharing.smartcampus.model.vo.comment.ExpertVo;
import com.sunsharing.smartcampus.service.audit.AuditUserService;
import com.sunsharing.smartcampus.service.comment.TzhxyCommentService;
import com.sunsharing.smartcampus.service.comment.TzhxyDpConfigService;
import com.sunsharing.smartcampus.service.filed.CategoryConfigService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * 点评专家配置信息表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-10-29
 */
@Service
@Log4j2
public class TzhxyDpConfigServiceImpl extends ServiceImpl<TzhxyDpConfigMapper, TzhxyDpConfig> implements TzhxyDpConfigService {

    @Autowired
    TzhxyDpConfigMapper tzhxyDpConfigMapper;

    @Autowired
    CategoryConfigService categoryConfigService;

    @Autowired
    AuditUserService auditUserService;

    @Autowired
    TzhxyCommentService tzhxyCommentService;


    @Override
    public Map<String, Object> queryTreeForCategory(String templateId) {
        List<DpTreeDto> treeDtos = selectTreeDto(templateId);
        //查询系统所有专家角色
        List<ExpertVo> zjUsers = auditUserService.queryCommentExperts(templateId);
        //查询分配情况
        LambdaQueryWrapper<TzhxyDpConfig> dpCofigWrapper = new LambdaQueryWrapper<>();
        dpCofigWrapper.eq(TzhxyDpConfig::getDel, "0")
            .eq(TzhxyDpConfig::getTemplateId, templateId);
        List<TzhxyDpConfig> configList = this.list(dpCofigWrapper);
        tzhxyCommentService.createDistribution(templateId, "", treeDtos);
        //过滤出所有已被选择的指标小项
        List<String> alreadySelectIds = configList.stream().map(item -> {
            String fieldIds = item.getFieldIds();
            return fieldIds.split(",");
        }).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        return MapUtil.ofHashMap("treeDtos", treeDtos, "experts", zjUsers,
            "alreadySelectIds", alreadySelectIds);
    }

    @Override
    public Integer queryWhetherDp(String templateId, String expertId) {
        return this.tzhxyDpConfigMapper.queryWhetherDp(templateId, expertId);
    }

    @Transactional
    @Override
    public Map<String, Object> saveCommentConfig(String pcNo, String templateId,
                                                 String expertId, String fieldIds) {
        //校验专家是否已点评
        Integer count = this.queryWhetherDp(templateId, expertId);
        if (count > 0) {
            return MapUtil.ofHashMap("result", false, "msg", "该专家已对本模板进行点评，不能重复分配");
        }
        //查询数据库中该专家被分配的数据
        LambdaQueryWrapper<TzhxyDpConfig> dpCofigWrapper = new LambdaQueryWrapper<>();
        dpCofigWrapper.eq(TzhxyDpConfig::getDel, "0")
            .eq(TzhxyDpConfig::getTemplateId, templateId)
            .eq(TzhxyDpConfig::getExpertId, expertId);
        List<TzhxyDpConfig> repeatList = this.list(dpCofigWrapper);
        TzhxyDpConfig saveEntity = null;
        //查询数据库中已分配的指标ID，新增数据查询全部
        dpCofigWrapper.clear();
        dpCofigWrapper.eq(TzhxyDpConfig::getDel, "0")
            .eq(TzhxyDpConfig::getTemplateId, templateId);
        if (!CollectionUtils.isEmpty(repeatList) && repeatList.size() > 0) {//不是新增排除本身被分配的指标
            saveEntity = repeatList.get(0);
            if (StringUtils.isBlank(fieldIds)) {
                saveEntity.setDel("1");//删除
            } else {
                saveEntity.setFieldIds(fieldIds);
            }
            saveEntity.setUpdateTime(new Date());
            dpCofigWrapper.ne(TzhxyDpConfig::getDpId, saveEntity.getDpId());
        } else {
            saveEntity = new TzhxyDpConfig();
            saveEntity.setDpId(StringUtils.genUUID());
            saveEntity.setExpertId(expertId);
            saveEntity.setFieldIds(fieldIds);
            saveEntity.setTemplateId(templateId);
            saveEntity.setPcNo(pcNo);
        }
        //先进行校验有无重复添加指标
        List<TzhxyDpConfig> configList = this.list(dpCofigWrapper);
        List<String> alreadySelectIds = configList.stream().map(item -> {
            String ids = item.getFieldIds();
            return ids.split(",");
        }).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        //进行迭代判断
        List<String> nowSelectIds = Arrays.asList(fieldIds.split(","));
        for (String ids : alreadySelectIds) {
            if (nowSelectIds.contains(ids)) {
                return MapUtil.ofHashMap("result", false, "msg", "本次勾选的指标已被分配过，不能重复分配");
            }
        }
        this.saveOrUpdate(saveEntity);
        return MapUtil.ofHashMap("result", true, "msg", "分配成功");
    }

    @Override
    public List<DpTreeDto> selectTreeDto(String templateId) {
        List<CategoryConfig> categoryConfigList = categoryConfigService.getCategoryByTemplateId(templateId);
        List<DpTreeDto> treeDtos = new ArrayList<>();
        //迭代创建树形结构
        categoryConfigList.forEach(item -> {
            if (item.getParentId().equals("0")) {//顶级
                DpTreeDto dpTreeDto = DpTreeDto.builder()
                    .key(item.getKey())
                    .title(item.getTitle())
                    .build();
                addChildren(categoryConfigList, item.getKey(), dpTreeDto);
                treeDtos.add(dpTreeDto);
            }
        });
        return treeDtos;
    }

    @Override
    public TzhxyDpConfig queryDpConfigForDeclareIdAndExpertId(String bussinessKey, String userId) {
        return this.tzhxyDpConfigMapper.getExpertCategoryIdList(userId, bussinessKey);
    }


    public void addChildren(List<CategoryConfig> treeDtos, String key, DpTreeDto treeDto) {
        treeDtos.forEach(item -> {
            if (item.getParentId().equals(key)) {
                DpTreeDto childrenDto = DpTreeDto.builder()
                    .key(item.getKey())
                    .title(item.getTitle())
                    .build();
                addChildren(treeDtos, item.getKey(), childrenDto);
                treeDto.addDpTreeDto(childrenDto);
            }
        });
    }
}
