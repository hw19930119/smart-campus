package com.sunsharing.smartcampus.mapper.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyTemplate;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 模板信息表 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-03
 */
@Repository
public interface TzhxyTemplateMapper extends BaseMapper<TzhxyTemplate> {

    List<TzhxyTemplate> selectTemplateByPcnoAndSchooltype(@Param("pcNo") String pcNo, @Param("schoolType") String schoolType);

    TzhxyTemplate selectByIdDiy(String id);

    List<TzhxyTemplate> selectByPcId(String pcId);

    void delById(@Param("templateId") String templateId);

    List<TzhxyTemplate> selectByDeclarId(@Param("declarId") String declarId);
}
