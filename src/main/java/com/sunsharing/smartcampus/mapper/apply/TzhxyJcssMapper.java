package com.sunsharing.smartcampus.mapper.apply;

import com.sunsharing.smartcampus.model.pojo.apply.TzhxyJcss;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.vo.apply.JcssVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学校基础实施信息 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-04
 */
public interface TzhxyJcssMapper extends BaseMapper<TzhxyJcss> {

    JcssVo selectVoByBusinessKey(@Param("businessKey") String businessKey);

}
