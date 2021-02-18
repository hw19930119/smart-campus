package com.sunsharing.smartcampus.mapper.comment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyDpConfig;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 点评专家配置信息表 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-10-29
 */
public interface TzhxyDpConfigMapper extends BaseMapper<TzhxyDpConfig> {

    Integer queryWhetherDp(@Param("templateId") String templateId,
                           @Param("expertId") String expertId);

    TzhxyDpConfig getExpertCategoryIdList(@Param("userId") String userId,
                                          @Param("declareId") String declareId);

    String getAllParentNodeList(@Param("id") String id);

}
