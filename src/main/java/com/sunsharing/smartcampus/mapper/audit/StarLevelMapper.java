package com.sunsharing.smartcampus.mapper.audit;

import com.sunsharing.smartcampus.model.pojo.audit.StarLevel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 节点评星 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
public interface StarLevelMapper extends BaseMapper<StarLevel> {

    List<Map> listStarLevel(@Param("businessKey") String businessKey);

    Map expertPoint(@Param("businessKey") String businessKey);

}
