package com.sunsharing.smartcampus.service.audit;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.constant.enums.HandleStateEnum;
import com.sunsharing.smartcampus.constant.enums.YESNOEnum;
import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.sunsharing.smartcampus.model.pojo.audit.PersonTodoDone;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 候选人待办已办表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
public interface PersonTodoDoneService extends IService<PersonTodoDone> {
    //当前节点是否都已办
    boolean currentNodeIsAllAggree(String piId, String roleId, PdNode pdNode, String userId);



    void createDoneList(String piId, String roleId, PdNode pdNode, String userId);

    //当前节点是否都已办
    boolean currentNodeIsAllDone(String piId, String roleId, PdNode pdNode, String userId);

    JSONObject cancelOneDoneOfAgree(JSONObject jsonObject);

    void createOneTodo(String piId, PdNode pdNode, String userId, String createUserId);
    void createOneDone(String piId, String userId, String createUserId, String result);

    void createTodoList(String piId, PdNode pdNode, String createUserId);

}
