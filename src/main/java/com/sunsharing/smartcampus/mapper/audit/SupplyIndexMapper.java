package com.sunsharing.smartcampus.mapper.audit;

import com.sunsharing.smartcampus.model.pojo.audit.Score;
import com.sunsharing.smartcampus.model.pojo.audit.SupplyIndex;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.vo.audit.ScoreVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 需要补充的指标 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface SupplyIndexMapper extends BaseMapper<SupplyIndex> {
    int updateIndexState(@Param("businessKey") String businessKey,
                         @Param("mainIndexId") String mainIndexId,
                         @Param("status") String status);

    int delIndexByBusinessKey(@Param("businessKey") String businessKey);

    List<ScoreVo> queryFinalIndexScoreMap(@Param("businessKey") String businessKey,
                                          @Param("roleId") String roleId);
    List<ScoreVo> queryFinalIndexScoreMapForOnlyGiveBack(@Param("businessKey") String businessKey);
}
