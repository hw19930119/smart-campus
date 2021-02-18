package com.sunsharing.smartcampus.mapper.audit;

import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 流程定义节点 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface PdNodeMapper extends BaseMapper<PdNode> {
    List<Map> getNodeAll(String businessKey);

    Map getNodeLastNoPass(String bussinessKey);
}
