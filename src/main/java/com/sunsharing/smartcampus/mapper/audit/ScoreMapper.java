package com.sunsharing.smartcampus.mapper.audit;

import com.sunsharing.smartcampus.model.pojo.audit.Score;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.vo.audit.ScoreVo;
import com.sunsharing.smartcampus.model.vo.filed.FieldValueVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 审核端评分记录表 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-14
 */
public interface ScoreMapper extends BaseMapper<Score> {
    List<ScoreVo> queryCategoryIdScoreList(@Param("bussinessKey") String bussinessKey,
                                           @Param("categoryId")String categoryId,
                                           @Param("onlyGiveBackStr")String onlyGiveBackStr);
    List<FieldValueVo> getAllIndexPinjiaByUser(@Param("userId") String userId, @Param("roleId")String roleId,@Param("bussinessKey")String bussinessKey);


    List<ScoreVo> queryExpertIndexScoreList(@Param("bussinessKey") String bussinessKey);
}
