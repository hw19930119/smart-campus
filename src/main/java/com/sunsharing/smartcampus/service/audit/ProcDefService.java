package com.sunsharing.smartcampus.service.audit;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.model.pojo.audit.ProcDef;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 流程定义 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface ProcDefService extends IService<ProcDef> {
    JSONObject publish(JSONObject jsonObject);
}
