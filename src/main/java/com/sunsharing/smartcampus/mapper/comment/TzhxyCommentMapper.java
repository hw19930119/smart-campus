package com.sunsharing.smartcampus.mapper.comment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyComment;
import com.sunsharing.smartcampus.model.vo.comment.CommentVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 专家点评信息表 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-10-29
 */
public interface TzhxyCommentMapper extends BaseMapper<TzhxyComment> {

    List<CommentVo> queryCommentForTemplateId(@Param("templateNo") String templateNo,
                                              @Param("declareId") String declareId);
}
