package com.sunsharing.smartcampus.mapper.apply;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyBaseInfo;
import com.sunsharing.smartcampus.model.vo.apply.BaseInfoVo;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 学校基本信息 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-04
 */
public interface TzhxyBaseInfoMapper extends BaseMapper<TzhxyBaseInfo> {

    TzhxyBaseInfo queryFirstForUnitCode(@Param("unitCode") String unitCode);

    BaseInfoVo selectVoById(@Param("businessKey") String businessKey);

    Integer updateBatchJcssPlaceOnfile(@Param("pcNo") String pcNo, @Param("pcState") String pcState);

    Map<String, Object> querySystemConfigBackEndTime();

    void updateSysConfigMack();

    Map<String, Object> queryReviewResultForBusinessKey(@Param("businessKey") String businessKey);
}
