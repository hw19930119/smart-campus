package com.sunsharing.smartcampus.service.comment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyDpConfig;
import com.sunsharing.smartcampus.model.vo.comment.DpTreeDto;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 点评专家配置信息表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-10-29
 */
public interface TzhxyDpConfigService extends IService<TzhxyDpConfig> {

    Map<String, Object> queryTreeForCategory(String templateId);

    /**
     * 查询指定模板 、专家是否已点评
     * @param templateId
     * @param expertId
     * @return
     */
    Integer queryWhetherDp(String templateId, String expertId);

    /**
     * 分配专家
     * @param pcNo 批次号
     * @param templateId -- 模板ID
     * @param expertId 专家ID
     * @param fieldIds 最末级指标ID集合
     * @return
     */
    Map<String, Object> saveCommentConfig(String pcNo, String templateId, String expertId, String fieldIds);

    /**
     * 查询树形结构
     * @param templateId -- 模板ID
     * @return
     */
    List<DpTreeDto> selectTreeDto(String templateId);

    /**
     * 根据申报ID 与  用户ID 查询DpConfig
     * @param bussinessKey
     * @param userId
     * @return
     */
    TzhxyDpConfig queryDpConfigForDeclareIdAndExpertId(String bussinessKey, String userId);
}
