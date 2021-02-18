package com.sunsharing.smartcampus.mapper.audit;

import com.sunsharing.smartcampus.model.pojo.audit.Pfhz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.vo.audit.PfhzVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评分汇总表 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
public interface PfhzMapper extends BaseMapper<Pfhz> {
    List<PfhzVo> queryPfhzList(@Param("bussinessKey") String bussinessKey);
}
