package com.sunsharing.smartcampus.service.audit;

import com.alibaba.fastjson.JSONArray;
import com.sunsharing.smartcampus.model.pojo.audit.NodePerson;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.audit.PdNode;

/**
 * <p>
 * 节点候选人 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
public interface NodePersonService extends IService<NodePerson> {
    void createNodePersonList(String piId,PdNode nextNode, JSONArray nextNodeAuditUserList,String userId);

    void updateStateWhenCommitAudit(String piId, PdNode pdNode, String userId, String state);
}
