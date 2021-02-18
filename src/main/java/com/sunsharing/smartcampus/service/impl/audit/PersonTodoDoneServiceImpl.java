package com.sunsharing.smartcampus.service.impl.audit;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sunsharing.smartcampus.constant.enums.*;
import com.sunsharing.smartcampus.mapper.apply.TzhxyBaseInfoMapper;
import com.sunsharing.smartcampus.mapper.audit.*;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyBaseInfo;
import com.sunsharing.smartcampus.model.pojo.audit.*;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.service.audit.AuditRecordService;
import com.sunsharing.smartcampus.service.audit.PersonTodoDoneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.service.audit.ProcInstService;
import com.sunsharing.smartcampus.utils.ResultDataUtils;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 候选人待办已办表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
@Service
@Transactional
public class PersonTodoDoneServiceImpl extends ServiceImpl<PersonTodoDoneMapper, PersonTodoDone> implements PersonTodoDoneService {
    //@Autowired
    //TodoDoneMapper todoDoneMapper;
    @Autowired
    PersonTodoDoneMapper personTodoDoneMapper;
    @Autowired
    NodePersonMapper nodePersonMapper;
    @Autowired
    ProcInstMapper procInstMapper;
    @Autowired
    PdNodeMapper pdNodeMapper;
    @Autowired
    TzhxyBaseInfoMapper tzhxyBaseInfoMapper;
    //---------------------------------
    @Autowired
    ProcInstService procInstService;
    @Autowired
    TodoDoneServiceImpl  todoDoneService;
    @Autowired
    AuditRecordService auditRecordService;

    //当前节点是否都同意
    @Override
    public boolean currentNodeIsAllAggree(String piId, String roleId, PdNode pdNode, String userId){
        boolean currentNodeAllAggree=true;
        QueryWrapper<NodePerson> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("PI_ID",piId);
        queryWrapper.eq("NODE_ID",pdNode.getNodeId());
        List<NodePerson> list=nodePersonMapper.selectList(queryWrapper);
        for(NodePerson nodePerson:list){
            if(!StringUtils.equals(nodePerson.getState(), AuditEnum.通过.getValue())){
                currentNodeAllAggree=false;
                break;
            }
        }
        return currentNodeAllAggree;
    }
    //当前节点是否都已办
    @Override
    public boolean currentNodeIsAllDone(String piId, String roleId, PdNode pdNode, String userId){
        boolean currentNodeAllDone=true;
        QueryWrapper<NodePerson> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("PI_ID",piId);
        queryWrapper.eq("NODE_ID",pdNode.getNodeId());
        List<NodePerson> list=nodePersonMapper.selectList(queryWrapper);
        for(NodePerson nodePerson:list){
            if(StringUtils.equals(nodePerson.getState(), AuditEnum.待审核.getValue())){
                currentNodeAllDone=false;
                break;
            }
        }
        return currentNodeAllDone;
    }


    @Override
    public JSONObject cancelOneDoneOfAgree(JSONObject jsonObject) {
        JSONObject json = new JSONObject();
        //参数-----------------------------------------
        String id = jsonObject.getString("id");
        //参数校验
        if (StringUtils.isAnyBlank(id)) {
            json.put("status", false);
            json.put("msg", "缺少参数id");
            return json;
        }
        AuditUserVo auditUserVo = IeduUserController.load(null, null).getAuditUserVo();
        String auditUserId = auditUserVo.getId();
        //
        ProcInst procInst = procInstMapper.selectById(id);
        //
        String curNodeId = procInst.getCurNodeId();
        PdNode pdNode = pdNodeMapper.selectById(curNodeId);
        String preNodeId=pdNode.getPreNodeId();
        PdNode prePdNode = pdNodeMapper.selectById(preNodeId);


        boolean isInCurrentNode =procInstService.checkIsInCurrentNode(id);
        boolean isInNextNodeNoStart=procInstService.checkIsInNextNodeNoStart(id);
        if (isInCurrentNode == false&&isInNextNodeNoStart==false) {
            json.put("status", false);
            json.put("msg", "该审批发生变化，请刷新列表");
            return json;
        }
        boolean isGuidang =procInstService.checkIsGuidang(id);
        if (isGuidang == true) {
            json.put("status", false);
            json.put("msg", "数据已归档，不能撤回");
            return json;
        }
        //
        if(StringUtils.equals(procInst.getCurResult(),AuditEnum.驳回补充.getValue())){
            json.put("status", false);
            json.put("msg", "该审批发生变化，请刷新列表");
            return json;
        }
        if(isInNextNodeNoStart==true){
            //修改流程实例
            ProcInst newProcInst=new ProcInst();
            newProcInst.setPiId(id);
            newProcInst.setCurNodeId(preNodeId);
            newProcInst.setCurResult(AuditEnum.待审核.getValue());
            newProcInst.setFinalResult(FinalResultEnum.待审核.getValue());
            procInstMapper.updateById(newProcInst);
            //修改业务表状态
            changeBusinessStateToPreNode(procInst.getBussinessKey(),preNodeId);
            //删除待办
            todoDoneService.deleteAllTodoDoneList(id,pdNode.getRoleId(),HandleStateEnum.待办);
        }
        //修改待办已办
        PdNode node=null;
        if(isInNextNodeNoStart==true){
            node=prePdNode;
        }else{
            node=pdNode;
        }
        createOneTodo(id,node, auditUserId,auditUserId);
        //记录审批记录
        auditRecordService.saveAuditRecord(procInst.getPiId(),node.getNodeId(), BizResultEnum.撤回已办.getValue(),BizResultEnum.撤回已办.getValue(),"",auditUserVo);
        json.put("status", true);
        json.put("msg", "操作成功");
        return json;
    }
    public void changeBusinessStateToPreNode(String businessKey,String preNodeId) {
        //查询指标信息
        PdNode pdNode = pdNodeMapper.selectById(preNodeId);
        PdNode prePdNode = pdNodeMapper.selectById(pdNode.getPreNodeId());
        //解析json
        String bizOtption = pdNode.getBizOtption();
        if("{}".equals(bizOtption) || com.sunsharing.common.utils.StringUtils.isBlank(bizOtption)) {
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(bizOtption);
        String bizState = jsonObject.getString(AuditEnum.待审核.getValue());
        //记录上一次的状态
        String preNodeBizOtption = prePdNode.getBizOtption();
        if("{}".equals(preNodeBizOtption) || com.sunsharing.common.utils.StringUtils.isBlank(preNodeBizOtption)) {
            return;
        }
        JSONObject preNodeBizOtptionJSON = JSONObject.parseObject(preNodeBizOtption);
        String preNodeBizState = preNodeBizOtptionJSON.getString(AuditEnum.通过.getValue());

        //查询业务基础信息
        TzhxyBaseInfo tzhxyBaseInfo = tzhxyBaseInfoMapper.selectById(businessKey);
        tzhxyBaseInfo.setState(bizState); //更新业务表中state状态
        tzhxyBaseInfo.setLastState(preNodeBizState);
        tzhxyBaseInfoMapper.updateById(tzhxyBaseInfo);
    }

    @Override
    public void createOneTodo(String piId, PdNode pdNode, String userId, String createUserId) {
        //先删除一个已办######################
        deleteOneTodoDone(piId,userId,HandleStateEnum.已办);
        //先删除，再插入待办
        deleteOneTodoDone(piId,userId,HandleStateEnum.待办);

        //
        insertByType(piId,userId,createUserId,HandleStateEnum.待办);
        //初始化节点人员状态
        initNodePersonState(piId,pdNode.getNodeId(),userId);
    }
    @Override
    public void createOneDone(String piId, String userId, String createUserId, String result) {
        //先删除待办######################
        /*if(!StringUtils.equals(result,AuditEnum.通过.getValue())){//不通过，删除所有待办
            deleteAllTodoDoneList(piId,HandleStateEnum.待办);
        }else{*/
            deleteOneTodoDone(piId,userId,HandleStateEnum.待办);
        /*}*/
        ////先删除，插入已办
        deleteOneTodoDone(piId,userId,HandleStateEnum.已办);
        //
        insertByType(piId,userId,createUserId,HandleStateEnum.已办);
    }
    @Override
    public void createDoneList(String piId, String roleId,PdNode pdNode, String userId) {
        //查询出当前待办，插入已办
        //先删除所有待办######################
        deleteAllTodoDoneList(piId,HandleStateEnum.待办);
        //先删除，插入已办
        QueryWrapper<NodePerson> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("PI_ID",piId);
        queryWrapper.eq("NODE_ID",pdNode.getNodeId());
        List<NodePerson> nodePersonList=nodePersonMapper.selectList(queryWrapper);
        for(NodePerson nodePerson:nodePersonList){
            createOneDone(piId,userId,userId,AuditEnum.通过.getValue());
        }
    }
    @Override
    public void createTodoList(String piId, PdNode pdNode, String createUserId) {
        //添加待办###
        //先查询会签人员
        QueryWrapper<NodePerson> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("PI_ID",piId);
        queryWrapper.eq("NODE_ID",pdNode.getNodeId());
        List<NodePerson> nodePersonList=nodePersonMapper.selectList(queryWrapper);
        //将所有人员的状态初始化
        initNodePersonState(piId,pdNode.getNodeId(),null);

        for(NodePerson nodePerson:nodePersonList){
            createOneTodo(piId,pdNode,nodePerson.getUserId(),createUserId);
        }
    }

    //将人员的状态初始化
    private void initNodePersonState(String piId,String nodeId,String userId){
        UpdateWrapper<NodePerson> nodePersonUpdateWrapper=new UpdateWrapper();
        nodePersonUpdateWrapper.eq("PI_ID",piId);
        nodePersonUpdateWrapper.eq("NODE_ID",nodeId);
        if(userId!=null){
            nodePersonUpdateWrapper.eq("USER_ID",userId);
        }
        NodePerson newNodePerson=new NodePerson();
        newNodePerson.setState(AuditEnum.待审核.getValue());
        nodePersonMapper.update(newNodePerson,nodePersonUpdateWrapper);
    }
    private void deleteAllTodoDoneList(String piId, HandleStateEnum type){
        UpdateWrapper<PersonTodoDone> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("PI_ID",piId);
        updateWrapper.eq("TYPE",type.getValue());
        personTodoDoneMapper.delete(updateWrapper);
    }
    private void deleteOneTodoDone(String piId,String userId,HandleStateEnum type){
        UpdateWrapper<PersonTodoDone> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("PI_ID",piId);
        updateWrapper.eq("TYPE",type.getValue());
        updateWrapper.eq("USER_ID",userId);
        personTodoDoneMapper.delete(updateWrapper);
    }
    private void insertByType(String piId,String userId,String createUserId,HandleStateEnum type){
        String uuid = com.sunsharing.component.utils.base.StringUtils.generateUUID();
        PersonTodoDone personTodoDone=new PersonTodoDone();
        personTodoDone.setId(uuid);
        personTodoDone.setPiId(piId);
        personTodoDone.setUserId(userId);
        personTodoDone.setType(type.getValue());
        personTodoDone.setCreateTime(new Date());
        personTodoDone.setCreatePerson(createUserId);
        personTodoDoneMapper.insert(personTodoDone);
    }
}
