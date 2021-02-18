package com.sunsharing.smartcampus.service.audit;

import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 流程定义节点 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface PdNodeService extends IService<PdNode> {
    /**
     * 获取所有的审批记录和未审批的节点
     * @param businessKey 业务主键
     * @return 查询结果
     */
    Map getNodeAll(String businessKey);
    /**
     * 查询最新一条审批不通过的记录
     * @param businessKey 业务主键
     * @return 查询结果
     */
    Map getNodeLastNoPass(String businessKey);
    /**
     * 检查是否跳过
     * @param nodeId 节点ID
     * @param xzqh   行政区划
     * @return 结果
     */
    String skipNode(String nodeId,String xzqh);
    /**
     * 更新业务表状态
     * @param result 审核结果
     * @param businessKey   业务主键
     * @param nodeId   节点ID
     */
    void setBusinessState(String result,String businessKey,String nodeId);
}
