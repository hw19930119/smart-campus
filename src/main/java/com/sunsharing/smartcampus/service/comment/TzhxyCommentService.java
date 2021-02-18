package com.sunsharing.smartcampus.service.comment;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyComment;
import com.sunsharing.smartcampus.model.vo.comment.DpTreeDto;

import java.util.List;

/**
 * <p>
 * 专家点评信息表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-10-29
 */
public interface TzhxyCommentService extends IService<TzhxyComment> {

    JSONObject commitComment(JSONObject jsonObject);

    JSONObject getCommentByExpert(JSONObject jsonObject);

    JSONObject queryCommentClass(JSONObject jsonObject);

    /**
     * 查询分组及其点评
     * @param templateId
     * @param declareId
     * @param treeDtos
     */
    void createDistribution(String templateId, String declareId, List<DpTreeDto> treeDtos);
}
