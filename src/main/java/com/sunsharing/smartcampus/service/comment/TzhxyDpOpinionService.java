package com.sunsharing.smartcampus.service.comment;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.comment.TzhxyDpOpinion;

import java.util.Map;

/**
 * <p>
 * 末级指标点评详情 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-11-04
 */
public interface TzhxyDpOpinionService extends IService<TzhxyDpOpinion> {

    Map<String, Object> saveDpOpinion(JSONObject reqParams);

}
