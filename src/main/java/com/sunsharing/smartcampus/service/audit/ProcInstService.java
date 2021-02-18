package com.sunsharing.smartcampus.service.audit;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.model.pojo.audit.ProcInst;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;

import java.util.Map;

/**
 * <p>
 * 流程实例 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface ProcInstService extends IService<ProcInst> {
    JSONObject clearAudit(JSONObject jsonObject);

    boolean checkIsInCurrentTodoListAndNode(String piId);

    boolean checkIsInCurrentNode(String piId);

    boolean checkIsInNextNodeNoStart(String piId);

    boolean checkIsGuidang(String piId);
    /**
     * 审批接口
     * @return 包装结果
     */
    Map auditSubmit(JSONObject jsonObject);
    /**
     * 根据角色查询审批过的数量
     */
    Map countDoneByRole();

    JSONObject getSelectButonForAudit(JSONObject jsonObject);

    //开始或重启流程
    //bussinessKey,varJson
    JSONObject startOrRestartProcess(JSONObject jsonObject, IeduUserVo iEduUserVo);
    //进入审核中
    //bussinessKey,varJson
    JSONObject enterProcess(JSONObject jsonObject);
    //查询审批节点
    //bussinessKey
    JSONObject queryProcessNodeList(JSONObject jsonObject);


    boolean queryCounterSignByPiId(String piId);

    void changeBizState(String bussinessKey, String state);
}
