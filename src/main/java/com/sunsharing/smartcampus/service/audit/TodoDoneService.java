package com.sunsharing.smartcampus.service.audit;

import com.sunsharing.smartcampus.constant.enums.HandleStateEnum;
import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.sunsharing.smartcampus.model.pojo.audit.TodoDone;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 待办已办表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface TodoDoneService extends IService<TodoDone> {
    //当前节点是否都已办
    boolean currentNodeIsAllDone(String piId, String roleId, PdNode pdNode, String userId);

    /**
     * 修改待办为已办
     * @param piId 流程实例Id
     * @param roleId 角色Id
     * @param userId 审批人Id
     */
    void createDoneList(String piId, String roleId, PdNode pdNode, String userId,String result);

    /**
     * 创建待办
     * @param piId 流程实例Id
     * @param pdNode 要生成待办的节点
     */
    void createTodoList(String piId, PdNode pdNode,String createUserId);

    void deleteAllTodoDoneList(String piId, String roleId, HandleStateEnum type);
}
