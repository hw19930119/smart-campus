package com.sunsharing.smartcampus.service.audit;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.model.pojo.audit.ReturnBase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 专家退回的申报 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-11-17
 */
public interface ReturnBaseService extends IService<ReturnBase> {

    JSONObject returnBaseInfoList(JSONObject jsonObject);
}
