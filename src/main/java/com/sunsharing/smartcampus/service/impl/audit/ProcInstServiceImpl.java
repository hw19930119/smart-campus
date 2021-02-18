package com.sunsharing.smartcampus.service.impl.audit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.share.common.base.exception.ShareBusinessException;
import com.sunsharing.smartcampus.constant.enums.AuditEnum;
import com.sunsharing.smartcampus.constant.enums.FinalResultEnum;
import com.sunsharing.smartcampus.constant.enums.HandleStateEnum;
import com.sunsharing.smartcampus.constant.enums.NodeIdentifierEnum;
import com.sunsharing.smartcampus.constant.enums.PcStateEnum;
import com.sunsharing.smartcampus.constant.enums.StarLevelWayEnum;
import com.sunsharing.smartcampus.mapper.apply.TzhxyBaseInfoMapper;
import com.sunsharing.smartcampus.mapper.audit.AuditRecordMapper;
import com.sunsharing.smartcampus.mapper.audit.AuditUserMapper;
import com.sunsharing.smartcampus.mapper.audit.NodePersonMapper;
import com.sunsharing.smartcampus.mapper.audit.PdNodeMapper;
import com.sunsharing.smartcampus.mapper.audit.PersonTodoDoneMapper;
import com.sunsharing.smartcampus.mapper.audit.ProcDefMapper;
import com.sunsharing.smartcampus.mapper.audit.ProcInstMapper;
import com.sunsharing.smartcampus.mapper.audit.ReturnBaseMapper;
import com.sunsharing.smartcampus.mapper.audit.RoleMapper;
import com.sunsharing.smartcampus.mapper.audit.ScoreMapper;
import com.sunsharing.smartcampus.mapper.audit.SupplyIndexMapper;
import com.sunsharing.smartcampus.mapper.audit.TodoDoneMapper;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyBaseInfo;
import com.sunsharing.smartcampus.model.pojo.audit.AuditRecord;
import com.sunsharing.smartcampus.model.pojo.audit.AuditUser;
import com.sunsharing.smartcampus.model.pojo.audit.NodePerson;
import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.sunsharing.smartcampus.model.pojo.audit.PersonTodoDone;
import com.sunsharing.smartcampus.model.pojo.audit.ProcDef;
import com.sunsharing.smartcampus.model.pojo.audit.ProcInst;
import com.sunsharing.smartcampus.model.pojo.audit.ReturnBase;
import com.sunsharing.smartcampus.model.pojo.audit.Role;
import com.sunsharing.smartcampus.model.pojo.audit.Score;
import com.sunsharing.smartcampus.model.pojo.audit.TodoDone;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;
import com.sunsharing.smartcampus.model.vo.audit.AuditOption;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.audit.PdNodeVo;
import com.sunsharing.smartcampus.model.vo.audit.SelectButonVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.service.audit.AuditRecordService;
import com.sunsharing.smartcampus.service.audit.NodePersonService;
import com.sunsharing.smartcampus.service.audit.PdNodeService;
import com.sunsharing.smartcampus.service.audit.PersonTodoDoneService;
import com.sunsharing.smartcampus.service.audit.PfhzService;
import com.sunsharing.smartcampus.service.audit.ProcInstService;
import com.sunsharing.smartcampus.service.audit.ScoreService;
import com.sunsharing.smartcampus.service.audit.StarLevelService;
import com.sunsharing.smartcampus.service.audit.SupplyIndexService;
import com.sunsharing.smartcampus.service.audit.TodoDoneService;
import com.sunsharing.smartcampus.service.biz.UserService;
import com.sunsharing.smartcampus.service.dm.SmartDmService;
import com.sunsharing.smartcampus.utils.ExpressionResolveUtils;
import com.sunsharing.smartcampus.utils.ResultDataUtils;
import com.sunsharing.smartcampus.web.common.IeduUserController;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 流程实例 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
@Service
@Transactional
public class ProcInstServiceImpl extends ServiceImpl<ProcInstMapper, ProcInst> implements ProcInstService {

    @Autowired
    ProcDefMapper procDefMapper;
    @Autowired
    PdNodeMapper pdNodeMapper;
    @Autowired
    ProcInstMapper procInstMapper;
    @Autowired
    AuditRecordMapper auditRecordMapper;
    @Autowired
    AuditUserMapper auditUserMapper;
    @Autowired
    TzhxyBaseInfoMapper tzhxyBaseInfoMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    SupplyIndexMapper supplyIndexMapper;
    @Autowired
    PersonTodoDoneMapper personTodoDoneMapper;
    @Autowired
    TodoDoneMapper todoDoneMapper;
    @Autowired
    ReturnBaseMapper returnBaseMapper;
    @Autowired
    ScoreMapper scoreMapper;
    @Autowired
    NodePersonMapper nodePersonMapper;

    //-----------------------------------
    @Autowired
    UserService userService;
    @Autowired
    TodoDoneService todoDoneService;
    @Autowired
    PersonTodoDoneService personTodoDoneService;
    @Autowired
    PdNodeService pdNodeService;
    @Autowired
    ScoreService scoreService;
    @Autowired
    NodePersonService nodePersonService;
    @Autowired
    StarLevelService starLevelService;
    @Autowired
    PfhzService pfhzService;
    @Autowired
    SupplyIndexService supplyIndexService;
    @Autowired
    SmartDmService smartDmService;
    @Autowired
    AuditRecordService auditRecordService;

    @Override
    public JSONObject clearAudit(JSONObject jsonObject) {
        JSONObject result = new JSONObject();
        String bussinessKey = jsonObject.getString("bussinessKey");
        procInstMapper.clearAudit(bussinessKey);
        result.put("status", true);
        result.put("msg", "流程清除成功");
        return result;
    }

    @Override
    public boolean checkIsInCurrentTodoListAndNode(String piId) {
        boolean isInCurrentTodoList = true;
        AuditUserVo auditUserVo = IeduUserController.load(null, null).getAuditUserVo();
        String auditUserId = auditUserVo.getId();
        //
        ProcInst procInst = procInstMapper.selectById(piId);
        //
        String curNodeId = procInst.getCurNodeId();
        PdNode pdNode = pdNodeMapper.selectById(curNodeId);
        boolean counterSign = false;
        if (StringUtils.equals(pdNode.getCounterSign(), "1")) {
            counterSign = true;
        }
        //判断
        //检查节点
        boolean isInCurrentNode = checkIsInCurrentNode(piId);
        if (isInCurrentNode == false) {
            isInCurrentTodoList = false;
        }

        //检查待办
        if (counterSign == true) {
            QueryWrapper<PersonTodoDone> queryWrapperToDo = new QueryWrapper<>();
            queryWrapperToDo.eq("PI_ID", piId);
            queryWrapperToDo.eq("TYPE", HandleStateEnum.待办.getValue());
            queryWrapperToDo.eq("USER_ID", auditUserId);
            int count = personTodoDoneMapper.selectCount(queryWrapperToDo);
            if (count != 1) {
                isInCurrentTodoList = false;
            }
        } else {
            QueryWrapper<TodoDone> queryWrapperToDo = new QueryWrapper<>();
            queryWrapperToDo.eq("PI_ID", piId);
            queryWrapperToDo.eq("TYPE", HandleStateEnum.待办.getValue());
            queryWrapperToDo.eq("ROLE_ID", auditUserVo.getRoleId());
            int count = todoDoneMapper.selectCount(queryWrapperToDo);
            if (count != 1) {
                isInCurrentTodoList = false;
            }
        }
        return isInCurrentTodoList;
    }

    @Override
    public boolean checkIsInCurrentNode(String piId) {
        boolean isInCurrentNode = true;
        AuditUserVo auditUserVo = IeduUserController.load(null, null).getAuditUserVo();
        String auditRoleId = auditUserVo.getRoleId();
        //
        ProcInst procInst = procInstMapper.selectById(piId);
        //
        String curNodeId = procInst.getCurNodeId();
        PdNode pdNode = pdNodeMapper.selectById(curNodeId);
        if (!StringUtils.equals(auditRoleId, pdNode.getRoleId())) {
            isInCurrentNode = false;
        }
        return isInCurrentNode;
    }

    @Override
    public boolean checkIsInNextNodeNoStart(String piId) {
        boolean isInNextNodeNoStart = false;
        AuditUserVo auditUserVo = IeduUserController.load(null, null).getAuditUserVo();
        //
        ProcInst procInst = procInstMapper.selectById(piId);
        //
        String curNodeId = procInst.getCurNodeId();
        PdNode pdNode = pdNodeMapper.selectById(curNodeId);
        String preNodeId = pdNode.getPreNodeId();
        PdNode prePdNode = pdNodeMapper.selectById(preNodeId);
        //
        JSONObject preNodeBizOtptionJSON = JSONObject.parseObject(prePdNode.getBizOtption());
        String preNodeBizState = preNodeBizOtptionJSON.getString(AuditEnum.通过.getValue());
        //
        TzhxyBaseInfo tzhxyBaseInfo = tzhxyBaseInfoMapper.selectById(procInst.getBussinessKey());
        String bizState = tzhxyBaseInfo.getState();
        //
        if (StringUtils.equals(bizState, preNodeBizState)
            && StringUtils.equals(auditUserVo.getRoleId(), prePdNode.getRoleId())) {
            isInNextNodeNoStart = true;
        }
        return isInNextNodeNoStart;
    }

    @Override
    public boolean checkIsGuidang(String piId) {
        boolean isGuidang = false;
        //
        ProcInst procInst = procInstMapper.selectById(piId);
        //检查是否已归档
        TzhxyBaseInfo baseInfo = tzhxyBaseInfoMapper.selectById(procInst.getBussinessKey());
        if (baseInfo != null && StringUtils.equalsAny(baseInfo.getPcState(),
            PcStateEnum.归档.getValue(),
            PcStateEnum.待启用.getValue())) {
            isGuidang = true;
        }
        return isGuidang;
    }


    @Override
    public Map auditSubmit(JSONObject jsonObject) {
        //参数-----------------------------------------
        String id = jsonObject.getString("id");
        String result = jsonObject.getString("result");
        String opinion = jsonObject.getString("opinion");
        String star = jsonObject.getString("star");

        String reviewMaterials = jsonObject.getString("reviewMaterials");
        Double avScore = jsonObject.getDouble("avScore");

        JSONArray nextNodeAuditUserList = jsonObject.getJSONArray("nextNodeAuditUserList");

        String procInstCurResult = result;//选项状态 //会签成功，要全部
        String procInstCurResultPre = result;//用于判断的值
        String procInstFinalResult = result;//节点最终状态
        String newCurNodeId = "";

        //参数校验
        if (StringUtils.isAnyBlank(id)) {
            return ResultDataUtils.packParams(false, "缺少参数piId");
        }
        if (StringUtils.isAnyBlank(result)) {
            return ResultDataUtils.packParams(false, "请选择‘审批结果’");
        }
        /*if (StringUtils.isAnyBlank(opinion)) {
            return ResultDataUtils.packParams(false, "审批意见未填写");
        }*/

        boolean isInCurrentTodoList = checkIsInCurrentTodoListAndNode(id);
        if (isInCurrentTodoList == false) {
            return ResultDataUtils.packParams(false, "该审批发生变化，请刷新待办列表");
        }
        boolean isGuidang = checkIsGuidang(id);
        if (isGuidang == true) {
            return ResultDataUtils.packParams(false, "数据已归档，不能操作");
        }

        //获取审批人Id,审批角色Id
        AuditUserVo auditUserVo = IeduUserController.load(null, null).getAuditUserVo();
        String auditUserId = auditUserVo.getId();
        String auditRoleId = auditUserVo.getRoleId();

        //根据流程id查询出流程实例
        ProcInst procInst = procInstMapper.selectById(id);
        String businessKey = procInst.getBussinessKey();
        newCurNodeId = procInst.getCurNodeId();

        //根据流程实例查询流程节点
        String curNodeId = procInst.getCurNodeId();
        PdNode pdNode = pdNodeMapper.selectById(curNodeId);
        String nextNodeId = pdNode.getNextNodeId();
        PdNode nextNode = pdNodeMapper.selectById(nextNodeId);
        String preNodeId = pdNode.getPreNodeId();
        PdNode preNode = pdNodeMapper.selectById(preNodeId);

        boolean isFinalNode = NodeIdentifierEnum.结束节点.eq(nextNodeId) ? true : false;
        //查询下一个节点
        PdNode nextPdNode = pdNodeMapper.selectById(nextNodeId);
        //参数校验
        if (StringUtils.equals(result, AuditEnum.通过.getValue())
            && StringUtils.isNotBlank(pdNode.getNextUserRole())
            && (nextNodeAuditUserList == null || nextNodeAuditUserList.isEmpty())) {
            return ResultDataUtils.packParams(false, "请选择专家");
        }
        if (StringUtils.equals(result, AuditEnum.通过.getValue())
            && StringUtils.equals(pdNode.getStarLevelWay(), StarLevelWayEnum.手动.getValue())
            && (StringUtils.isBlank(star) || StringUtils.equals("0", star))) {
            return ResultDataUtils.packParams(false, "请进行星级评价");
        }
        if (avScore != null && (avScore.doubleValue() > 200 || avScore.doubleValue() < 0)) {
            return ResultDataUtils.packParams(false, "分数请在0-200分范围内");
        }

        //参数校验：校验是否能够提交-----------------------------------------------------------------
        //提交审核‘通过’时,按照节点配置检查是否有汇总（完成所有指标评分），否则给予错误提示
        boolean myPfhzComplete = pfhzService.checkMyPfhzCompleteByConfig(id, pdNode, result, auditUserId);
        if (myPfhzComplete == false) {
            return ResultDataUtils.packParams(false, "评分没有汇总，请先汇总");
        }
        //提交审核‘通过’时,如果有打回指标需要修改，就只能选择退回，否则给予错误提示-----------生成了汇总，就应该没有退回了
        //checkMyScoreHasGiveBack();
        //审批通过,评分了就必须把评分填完
        boolean myPfhzCompleteAllOnAgree = pfhzService.checkMyScoreCompleteAllOnAgreeByConfig(id, pdNode, result, auditUserId);
        if (myPfhzCompleteAllOnAgree == false) {
            return ResultDataUtils.packParams(false, "如果已经对指标进行评分，请完成所有指标评分,并汇总提交");
        }
        boolean checkMyScoreCompleteAllOnAgreeOrNoAgreeByConfig = pfhzService.checkMyScoreCompleteAllOnAgreeOrNoAgreeByConfig(id, pdNode, result, auditUserId);
        if (checkMyScoreCompleteAllOnAgreeOrNoAgreeByConfig == false) {
            return ResultDataUtils.packParams(false, "请完成所有指标评价");
        }


        boolean counterSign = queryCounterSignByPiId(id);
        boolean counterSignAllDone = false;
        boolean counterSignAllAgree = false;

        //如果当前节点是会签，修改相应候选人状态
        if (counterSign == true) {
            nodePersonService.updateStateWhenCommitAudit(id, pdNode, auditUserId, result);
            counterSignAllAgree = personTodoDoneService.currentNodeIsAllAggree(id, pdNode.getRoleId(), pdNode, auditUserId);
            counterSignAllDone = personTodoDoneService.currentNodeIsAllDone(id, pdNode.getRoleId(), pdNode, auditUserId);

            if (counterSignAllDone == false) {
                procInstCurResultPre = AuditEnum.待审核.getValue();
                procInstFinalResult = FinalResultEnum.待审核.getValue();
            } else {
                if (counterSignAllAgree == true) {
                    procInstCurResultPre = AuditEnum.通过.getValue();
                    procInstFinalResult = FinalResultEnum.待审核.getValue();//还没完
                } else {
                    procInstCurResultPre = AuditEnum.退回上一步.getValue();
                    procInstFinalResult = FinalResultEnum.待审核.getValue();//还没完
                }
            }
        }
        //更新待办为已办-----------------------------------------------------------------
        todoDoneService.createDoneList(id, pdNode.getRoleId(), pdNode, auditUserId, result);

        supplyIndexService.assignSupplyIndex(procInst.getBussinessKey(), auditUserVo.getRoleId(), counterSign);

        //如果是拒绝,更新最终状态为拒绝,如果是最后一个节点直接更新为当前审核状态
        if (AuditEnum.拒绝.eq(procInstCurResultPre)) {
            newCurNodeId = procInst.getCurNodeId();
            procInstCurResult = AuditEnum.拒绝.getValue();
            procInstFinalResult = FinalResultEnum.拒绝.getValue();//结束
        } else if (AuditEnum.通过.eq(procInstCurResultPre)) {//如果是通过,直接更新到下一个节点,状态为待审核
            //受理中心提交初审，归挡'该节点'所有打回记录
            if (pdNode.getSeqNum().intValue() == 2) {
                UpdateWrapper<Score> scoreUpdateWrapper = new UpdateWrapper<>();
                scoreUpdateWrapper.eq("BUSSINESS_KEY", procInst.getBussinessKey());
                scoreUpdateWrapper.eq("GIVE_BACK", "1");
                scoreUpdateWrapper.eq("HISTORY_FLAG", "0");
                scoreUpdateWrapper.eq("AUDIT_ROLE", pdNode.getRoleId());
                Score scoreHistory = new Score();
                scoreHistory.setHistoryFlag("1");
                scoreMapper.update(scoreHistory, scoreUpdateWrapper);
            }

            //按照配置生成星级评分-------------------------------------------------------------------
            starLevelService.createStarLevel(id, procInst.getBussinessKey(), pdNode, star, auditUserId, reviewMaterials, avScore);
            //生成节点候选人------------------------------------------------------------------------
            nodePersonService.createNodePersonList(id, nextPdNode, nextNodeAuditUserList, auditUserId);

            if (isFinalNode) {
                newCurNodeId = procInst.getCurNodeId();
                procInstCurResult = AuditEnum.通过.getValue();
                procInstFinalResult = FinalResultEnum.通过.getValue();//结束

                //更新业务主表星级字段
                TzhxyBaseInfo tzhxyBaseInfo = tzhxyBaseInfoMapper.selectById(businessKey);
                tzhxyBaseInfo.setStarLevel(star);
                tzhxyBaseInfoMapper.updateById(tzhxyBaseInfo);
            } else {
                newCurNodeId = nextNodeId;//创建新的待办
                procInstCurResult = AuditEnum.待审核.getValue();
                procInstFinalResult = FinalResultEnum.待审核.getValue();//还没完

                todoDoneService.createTodoList(id, nextPdNode, auditUserId);
            }
        } else if (AuditEnum.驳回补充.eq(procInstCurResultPre)) {
            newCurNodeId = procInst.getCurNodeId();
            procInstCurResult = AuditEnum.驳回补充.getValue();
            procInstFinalResult = FinalResultEnum.驳回补充.getValue();//

            /*if(StringUtils.equals(pdNode.getScoreButton(),"2")){
                //重新将所有专家打回的指标汇总到，退回补充里面
                supplyIndexService.assignSupplyIndex(businessKey,auditUserId,counterSign);
            }*/
            //如果是会签，看要不要移除所有待办,现在测试要求只移除一个人的待办
            //在上面createDoneList统一处理
        } else if (AuditEnum.退回上一步.eq(procInstCurResultPre)) {//
            //如果是专家节点，需保存一个退回申报
            if (counterSign == true) {
                saveReturnBase(businessKey);
                //如果所有专家都完成了，并且至少有一个是‘退回上一步’或者‘退回补充’，则修改流程状态，否者 不修改

                newCurNodeId = preNodeId;
                procInstCurResult = AuditEnum.待审核.getValue();
                procInstFinalResult = FinalResultEnum.待审核.getValue();

            } else {
                newCurNodeId = preNodeId;
                procInstCurResult = AuditEnum.待审核.getValue();
                procInstFinalResult = FinalResultEnum.待审核.getValue();
                //创建新的待办
                PdNode prePdNode = pdNodeMapper.selectById(preNodeId);
                todoDoneService.createTodoList(id, prePdNode, auditUserId);
            }
        } else if (AuditEnum.待审核.eq(procInstCurResultPre)) {
            newCurNodeId = procInst.getCurNodeId();
            procInstCurResult = AuditEnum.待审核.getValue();
            procInstFinalResult = FinalResultEnum.待审核.getValue();
        }


        procInst.setCurNodeId(newCurNodeId);
        procInst.setCurResult(procInstCurResult);
        procInst.setFinalResult(procInstFinalResult);

        String auditResult = result;
        //改变业务状态
        pdNodeService.setBusinessState(procInstCurResultPre, businessKey, curNodeId);
        //改变实例状态
        procInstMapper.updateById(procInst);
        String baseInfoBizResult = getBizResultByResult(pdNode, procInstCurResultPre);

        //创建审批记录
        auditRecordService.saveAuditRecord(procInst.getPiId(), curNodeId, auditResult, baseInfoBizResult, opinion, auditUserVo);

        return ResultDataUtils.packParams(true, "审核成功");
    }


    private String getBizResultByResult(PdNode pdNode, String result) {
        String bizOtption = pdNode.getBizOtption();
        if ("{}".equals(bizOtption) || com.sunsharing.common.utils.StringUtils.isBlank(bizOtption)) {
            return "";
        }
        //解析json
        JSONObject jsonObject = JSONObject.parseObject(bizOtption);
        String bizResult = jsonObject.getString(result);
        return bizResult;
    }

    /*private String getBizResultNameByResult(PdNode pdNode,String result){
        String bizResult =getBizResultByResult(pdNode,result);
        String bizResultName="";
        List<Map<String, Object>>  bizResultMap=smartDmService.loadSmartDm("DM_BIZ_RESULT");
        for(Map<String, Object> dm:bizResultMap){
            if(StringUtils.equals(bizResult,(String)dm.get("id"))){
                bizResultName=(String)dm.get("label");
                break;
            }
        }
        return bizResultName;
    }*/
    private void saveReturnBase(String bussinessKey) {
        UpdateWrapper<ReturnBase> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("DECLARE_ID", bussinessKey);
        returnBaseMapper.delete(updateWrapper);
        String uuid = com.sunsharing.component.utils.base.StringUtils.generateUUID();

        ReturnBase returnBase = new ReturnBase();
        returnBase.setDeclareId(bussinessKey);
        returnBase.setId(uuid);
        returnBaseMapper.insert(returnBase);
    }

    @Override
    public Map countDoneByRole() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DEL", "0");
        List<Role> roles = roleMapper.selectList(queryWrapper);
        Map map = new HashMap();
        roles.forEach(r -> {
            int count = procInstMapper.countDoneByRole(r.getRoleId());
            map.put(r.getName(), count);
        });
        return ResultDataUtils.packParams(map, "查询成功");
    }


    //是否有评分按钮（有就没有 评分详情按钮）
    //
    //
    @Override
    public JSONObject getSelectButonForAudit(JSONObject jsonObject) {
        JSONObject json = new JSONObject();
        String id = jsonObject.getString("id");

        boolean isInCurrentTodoList = checkIsInCurrentTodoListAndNode(id);
        if (isInCurrentTodoList == false) {
            json.put("status", false);
            json.put("msg", "该审批发生变化，请刷新待办列表");
            return json;
        }


        IeduUserVo iEduUserVo = IeduUserController.load(null, null);
        AuditUserVo auditUserVo = iEduUserVo.getAuditUserVo();
        ProcInst where = new ProcInst();
        where.setPiId(id);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(where);
        ProcInst currProcInst = this.getOne(queryWrapper);
        String curNodeId = currProcInst.getCurNodeId();
        QueryWrapper nodeDefQueryWrapper = new QueryWrapper();
        nodeDefQueryWrapper.eq("NODE_ID", curNodeId);
        PdNode pdNode = pdNodeMapper.selectOne(nodeDefQueryWrapper);
        //
        String otptionStr = pdNode.getOtption();

        if (StringUtils.isBlank(otptionStr)) {
            return json;
        }
        SelectButonVo selectButonVo = JSONObject.parseObject(otptionStr, SelectButonVo.class);
        json.put("selectButonVo", selectButonVo);
        selectButonVo.setScoreButton(pdNode.getScoreButton());
        //如果有下一步候选人，查询出来
        if (StringUtils.isNotBlank(pdNode.getNextUserRole())) {
            QueryWrapper<AuditUser> auditUserQueryWrapper = new QueryWrapper<>();
            auditUserQueryWrapper.eq("ROLE_ID", pdNode.getNextUserRole());
            auditUserQueryWrapper.eq("DEL", "0");
            auditUserQueryWrapper.orderByAsc("NAME");
            List<AuditUser> auditUserList = auditUserMapper.selectList(auditUserQueryWrapper);

            QueryWrapper<NodePerson> nodePersonQueryWrapper = new QueryWrapper<>();
            nodePersonQueryWrapper.eq("PI_ID", currProcInst.getPiId());
            nodePersonQueryWrapper.eq("NODE_ID", pdNode.getNextNodeId());
            List<NodePerson> existNodePersonList = nodePersonMapper.selectList(nodePersonQueryWrapper);
            if (!existNodePersonList.isEmpty()) {
                selectButonVo.setDisableNextNodeAuditUserSelect(true);
            }
            List<AuditUserVo> auditUserVoList = new ArrayList<>();
            for (AuditUser auditUser : auditUserList) {
                AuditUserVo auditUserVoTemp = new AuditUserVo();
                BeanUtils.copyProperties(auditUser, auditUserVoTemp);
                auditUserVoTemp.setSelected(false);//初始化
                for (NodePerson nodePerson : existNodePersonList) {
                    if (StringUtils.equals(nodePerson.getUserId(), auditUser.getId())) {
                        auditUserVoTemp.setSelected(true);
                        break;
                    }
                }
                auditUserVoList.add(auditUserVoTemp);
            }
            selectButonVo.setNextNodeAuditUserList(auditUserVoList);
        }
        //如果是会签，要查询出历史意见
        boolean counterSign = queryCounterSignByPiId(id);
        String result = null;
        if (counterSign == true) {
            QueryWrapper<AuditRecord> auditRecordQueryWrapper = new QueryWrapper();
            auditRecordQueryWrapper.eq("PI_ID", id);
            auditRecordQueryWrapper.eq("NODE_ID", curNodeId);
            auditRecordQueryWrapper.eq("AUDIT_USER_ID", auditUserVo.getId());
            auditRecordQueryWrapper.orderByDesc("AUDIT_TIME");
            List<AuditRecord> auditRecordList = auditRecordMapper.selectList(auditRecordQueryWrapper);
            if (!auditRecordList.isEmpty()) {
                AuditRecord newestAuditRecord = auditRecordList.get(0);
                selectButonVo.setOpinion(newestAuditRecord.getOpinon());
                result = newestAuditRecord.getResult();
            }
        }
        //如果指标评分有退回补充，只能退回补充，默认选中
        boolean existCategoryIdGiveBackByMySelf = scoreService.queryExistCategoryIdGiveBackByMySelf(currProcInst.getBussinessKey(), auditUserVo.getId(), auditUserVo.getRoleId(), counterSign);
        List<AuditOption> auditOptionList = selectButonVo.getOptions();
        for (AuditOption auditOption : auditOptionList) {
            if (existCategoryIdGiveBackByMySelf == true) {//
                if (counterSign == true) {
                    if (StringUtils.equals(auditOption.getValue(), AuditEnum.退回上一步.getValue())) {
                        auditOption.setSelected(true);
                    } else {
                        auditOption.setSelected(false);
                        auditOption.setDisabled(true);
                        auditOption.setDisabledMsg("有退回的指标");
                    }
                } else {
                    if (pdNode.getSeqNum().intValue() == 2) {
                        auditOption.setSelected(true);
                    } else {
                        if (StringUtils.equals(auditOption.getValue(), AuditEnum.驳回补充.getValue())) {
                            auditOption.setSelected(true);
                        } else {
                            auditOption.setSelected(false);
                            auditOption.setDisabled(true);
                            auditOption.setDisabledMsg("有退回的指标");
                        }
                    }
                }
            } else {
               /* if (StringUtils.equals(auditOption.getValue(), result)) {
                    auditOption.setSelected(true);
                }*/
            }
        }
        json.put("auditUserVo", auditUserVo);
        return json;
    }

    private ProcInst getProcInstByBussinessKeyForRestart(String bussinessKey) {
        ProcInst where = new ProcInst();
        where.setBussinessKey(bussinessKey);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(where);
        queryWrapper.in("FINAL_RESULT",
            Arrays.asList(FinalResultEnum.已提交.getValue(),
                FinalResultEnum.驳回补充.getValue(),
                FinalResultEnum.撤销.getValue()
            ));
        return this.getOne(queryWrapper);
    }

    private boolean checkNewProcessAllow(String bussinessKey) {
        //不可新建提交：0 审核中 1通过
        ProcInst where = new ProcInst();
        where.setBussinessKey(bussinessKey);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(where);
        queryWrapper.in("FINAL_RESULT",
            Arrays.asList(FinalResultEnum.待审核.getValue(),
                FinalResultEnum.通过.getValue()));
        ProcInst old = this.getOne(queryWrapper);
        if (old != null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public JSONObject startOrRestartProcess(JSONObject jsonObject, IeduUserVo iEduUserVo) {
        //可重新提交：-1已提交 3驳回补充 4撤销
        //不可重新提交：0 审核中 1通过 2拒绝
        String bussinessKey = jsonObject.getString("bussinessKey");
        String varJson = jsonObject.getString("varJson");
        //兼容
        ApplyUserVo applyUserVo = null;
        if (iEduUserVo != null) {
            applyUserVo = iEduUserVo.getApplyUserVo();
        }

        JSONObject json = new JSONObject();
        json.put("status", true);
        json.put("msg", "提交成功");
        //查询该业务key是否存在被驳回的流程实例
        ProcInst procInst = getProcInstByBussinessKeyForRestart(bussinessKey);
        if (procInst == null) {
            if (checkNewProcessAllow(bussinessKey) == false) {
                json.put("status", false);
                json.put("msg", "目前已有申请正在审核中或已审核通过，请勿重复提交申请！");
            } else {
                json = startProcess(bussinessKey, varJson, applyUserVo);
                //supplyIndexMapper.updateIndexState(bussinessKey);
            }
        } else {
            //如果存在，就重启流程
            restartProcess(procInst, procInst.getPiId(), varJson, applyUserVo);
            //supplyIndexMapper.updateIndexState(bussinessKey);
        }
        //删除打回
        UpdateWrapper<ReturnBase> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("DECLARE_ID", bussinessKey);
        returnBaseMapper.delete(updateWrapper);
        return json;
    }

    @Override
    public JSONObject enterProcess(JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        String varJson = jsonObject.getString("varJson");
        IeduUserVo iEduUserVo = IeduUserController.load(null, null);
        String commitUserId = iEduUserVo.getAuditUserVo().getId();
        JSONObject json = new JSONObject();
        //查询流程实例
        ProcInst where = new ProcInst();
        where.setPiId(id);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(where);
        ProcInst result = this.getOne(queryWrapper);

        //标记最终审核状态为审核中
        if (result != null) {
            PdNode pdNode = pdNodeMapper.selectById(result.getCurNodeId());
            String bizOptionStr = pdNode.getBizOtption();
            JSONObject bizOption = JSONObject.parseObject(bizOptionStr);
            if (StringUtils.equalsAny(result.getCurResult(),
                FinalResultEnum.已提交.getValue(),
                FinalResultEnum.待审核.getValue())) {
                String currWaitState = bizOption.getString(AuditEnum.待审核.getValue());
                ProcInst dto = new ProcInst();
                dto.setPiId(result.getPiId());
                dto.setFinalResult(FinalResultEnum.待审核.getValue());
                dto.setUpdatePerson(iEduUserVo.getAuditUserVo().getId());
                dto.setUpdateTime(new Date());
                this.updateById(dto);
                //
                changeBizState(result.getBussinessKey(), currWaitState);
            }
            json.put("status", true);
            json.put("msg", "进入成功");
        } else {
            json.put("status", false);
            json.put("msg", "没有找到对应的流程");
        }
        return json;
    }

    @Override
    public JSONObject queryProcessNodeList(JSONObject jsonObject) {
        String bussinessKey = jsonObject.getString("bussinessKey");
        JSONObject json = new JSONObject();
        List<PdNodeVo> nodeList = procInstMapper.queryProcessNodeList(bussinessKey);
        json.put("nodeList", nodeList);
        return json;
    }

    private JSONObject startProcess(String bussinessKey, String varJson, ApplyUserVo applyUserVo) {
        JSONObject json = new JSONObject();
        json.put("status", true);
        json.put("msg", "提交成功");

        JSONObject varJsonObject = JSONObject.parseObject(varJson);
        String commitUserId = applyUserVo.getId();
        ProcDef procDef = getProdefFirst(applyUserVo);
        PdNode pdNode = getFirstPdNode(applyUserVo, procDef);
        //生成流程实例
        String uuid = com.sunsharing.component.utils.base.StringUtils.generateUUID();
        ProcInst procInst = new ProcInst();
        procInst.setPiId(uuid);
        procInst.setPdId(procDef.getPdId());
        procInst.setBussinessKey(bussinessKey);
        procInst.setCurNodeId(pdNode.getNodeId());
        procInst.setCurResult(AuditEnum.待审核.getValue());
        procInst.setCommitUserId(commitUserId);
        procInst.setCommitTime(new Date());
        procInst.setCommitSchoolId(applyUserVo.getUnitCode());
        procInst.setVarJson(varJson);
        procInst.setFinalResult(FinalResultEnum.已提交.getValue());
        procInst.setCreatePerson(commitUserId);
        procInstMapper.insert(procInst);
        //生成待办
        todoDoneService.createTodoList(uuid, pdNode, commitUserId);
        return json;
    }

    private ProcDef getProdefFirst(ApplyUserVo applyUserVo) {
        ProcDef procDef = null;
        //查询提交人所在学校类型
        String commitUserId = applyUserVo.getId();
        //根据类型查询对应的流程定义
        QueryWrapper pdQueryWrapper = new QueryWrapper();
        pdQueryWrapper.eq("PUBLISH", "1");
        pdQueryWrapper.eq("DEL", "0");
        pdQueryWrapper.orderByDesc("PUBLISH_TIME");
        List<ProcDef> procDefList = procDefMapper.selectList(pdQueryWrapper);
        if (procDefList.isEmpty()) {
            throw new ShareBusinessException(1500, "没有流程定义");
            /*json.put("status",false);
            json.put("msg","没有流程定义");
            return json;*/
        }
        procDef = procDefList.get(0);
        return procDef;
    }

    private PdNode getFirstPdNode(ApplyUserVo applyUserVo, ProcDef procDef) {
        PdNode pdNode;//根据流程定义查询对应的定义节点
        QueryWrapper pdNodeQueryWrapper = new QueryWrapper();
        pdNodeQueryWrapper.eq("PD_ID", procDef.getPdId());
        pdNodeQueryWrapper.eq("DEL", "0");
        pdNodeQueryWrapper.orderByAsc("SEQ_NUM");
        List<PdNode> pdNodeList = pdNodeMapper.selectList(pdNodeQueryWrapper);
        if (pdNodeList.isEmpty()) {
            throw new ShareBusinessException(1500, "没有对应的流程定义的流程");
            /*json.put("status",false);
            json.put("msg","没有对应的流程定义的流程");
            return json;*/
        }
        pdNode = pdNodeList.get(0);
        for (int i = 0; i < pdNodeList.size(); i++) {
            pdNode = pdNodeList.get(i);
            if (StringUtils.isBlank(pdNode.getSkipExpression())) {
                break;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("cityBelong", applyUserVo.getCityBelong());
            if (ExpressionResolveUtils.analyzeExpression(pdNode.getSkipExpression(), map) == false) {
                break;//确定节点
            }
        }
        return pdNode;
    }

    private void restartProcess(ProcInst procInst, String piId, String varJson, ApplyUserVo applyUserVo) {
        //自己查询
        if (applyUserVo == null) {
            String userId = procInst.getCommitUserId();
            applyUserVo = userService.getApplyUserVoByUserId(userId);
        }
        ProcDef procDef = procDefMapper.selectById(procInst.getPdId());
        PdNode pdNode = getFirstPdNode(applyUserVo, procDef);
        //查询流程实例
        //ProcInst dto=this.getById(piId);
        //修改实例当前审核状态为待审核，最终结果为已提交
        ProcInst dtoForUpdate = new ProcInst();
        dtoForUpdate.setPiId(piId);
        dtoForUpdate.setCurNodeId(pdNode.getNodeId());
        dtoForUpdate.setCurResult(AuditEnum.待审核.getValue());
        dtoForUpdate.setFinalResult(FinalResultEnum.已提交.getValue());
        dtoForUpdate.setUpdatePerson(applyUserVo.getId());
        dtoForUpdate.setUpdateTime(new Date());
        dtoForUpdate.setVarJson(varJson);
        this.updateById(dtoForUpdate);
        //生成待办
        todoDoneService.createTodoList(piId, pdNode, applyUserVo.getId());
    }

    @Override
    public boolean queryCounterSignByPiId(String piId) {
        boolean counterSign = false;
        //查询流程实例
        ProcInst procInst = procInstMapper.selectById(piId);
        String curNodeId = procInst.getCurNodeId();
        PdNode pdNode = pdNodeMapper.selectById(procInst.getCurNodeId());
        if (org.apache.commons.lang3.StringUtils.equals(pdNode.getCounterSign(), "1")) {
            counterSign = true;
        }
        return counterSign;
    }

    @Override
    public void changeBizState(String bussinessKey, String state) {
        //
        QueryWrapper<TzhxyBaseInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("DECLARE_ID", bussinessKey);
        TzhxyBaseInfo tzhxyBaseInfoOld = tzhxyBaseInfoMapper.selectOne(queryWrapper);
        //
        TzhxyBaseInfo tzhxyBaseInfo = new TzhxyBaseInfo();
        tzhxyBaseInfo.setDeclareId(bussinessKey);
        tzhxyBaseInfo.setState(state);
        tzhxyBaseInfo.setLastState(tzhxyBaseInfoOld.getState());
        tzhxyBaseInfoMapper.updateById(tzhxyBaseInfo);
    }


}
