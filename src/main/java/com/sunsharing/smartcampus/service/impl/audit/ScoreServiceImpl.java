package com.sunsharing.smartcampus.service.impl.audit;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sunsharing.smartcampus.constant.enums.RoleCodeEnum;
import com.sunsharing.smartcampus.mapper.audit.*;
import com.sunsharing.smartcampus.mapper.filed.FieldValueMapper;
import com.sunsharing.smartcampus.model.pojo.audit.*;
import com.sunsharing.smartcampus.model.pojo.filed.FieldValue;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.audit.ScoreVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.model.vo.filed.FieldValueVo;
import com.sunsharing.smartcampus.service.audit.PfhzService;
import com.sunsharing.smartcampus.service.audit.ProcInstService;
import com.sunsharing.smartcampus.service.audit.ScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.service.audit.SupplyIndexService;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 审核端评分记录表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-14
 */
@Service
@Transactional
@Log4j2
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {
    @Autowired
    AuditUserMapper auditUserMapper;
    @Autowired
    ScoreMapper scoreMapper;
    @Autowired
    ProcInstMapper procInstMapper;
    @Autowired
    ProcDefMapper procDefMapper;
    @Autowired
    PdNodeMapper pdNodeMapper;
    @Autowired
    PfhzMapper pfhzMapper;
    @Autowired
    SupplyIndexMapper supplyIndexMapper;
    @Autowired
    FieldValueMapper fieldValueMapper;
    @Autowired
    ReturnBaseMapper returnBaseMapper;

    //------------------------------------
    @Autowired
    ProcInstService procInstService;
    @Autowired
    PfhzService pfhzService;
    @Autowired
    SupplyIndexService supplyIndexService;

    @Override
    public JSONObject queryCategoryIdScoreDetail(JSONObject jsonObject) {
        JSONObject result=new JSONObject();
        String bussinessKey=jsonObject.getString("bussinessKey");
        String categoryId=jsonObject.getString("categoryId");
        String piId=jsonObject.getString("piId");//审批时必填 //流程实例id
        boolean onlyGiveBack=jsonObject.getBooleanValue("onlyGiveBack");
        String onlyGiveBackStr=null;
        if(onlyGiveBack==true){
            onlyGiveBackStr="1";
        }


        //指标评分历史
        List<ScoreVo> list=scoreMapper.queryCategoryIdScoreList(bussinessKey,categoryId,onlyGiveBackStr);
        for(ScoreVo scoreVo:list){
            if(StringUtils.equals(scoreVo.getGiveBack(),"0")){
                scoreVo.setGiveBackLabel("否");
            }else{
                scoreVo.setGiveBackLabel("是");
            }
        }
        result.put("list",list);


        //当前登录用户，角色的指标评分
        IeduUserVo iEduUserVo=IeduUserController.load(null,null);
        AuditUserVo auditUserVo=iEduUserVo.getAuditUserVo();
        if(StringUtils.isNotBlank(piId)&&auditUserVo!=null){
            String userId=auditUserVo.getId();
            String roleId=auditUserVo.getRoleId();

            ProcInst procInst = procInstMapper.selectById(piId);
            //根据流程实例查询流程节点
            String curNodeId = procInst.getCurNodeId();
            PdNode pdNode = pdNodeMapper.selectById(curNodeId);
            //
            boolean counterSign=procInstService.queryCounterSignByPiId(piId);
            //当前登录用户，角色的指标评分
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("BUSSINESS_KEY",bussinessKey);
            queryWrapper.eq("CATEGORY_ID",categoryId);
            if(counterSign){
                queryWrapper.eq("AUDIT_PERSON",userId);
            }else{
                queryWrapper.eq("AUDIT_ROLE",roleId);
            }
            queryWrapper.eq("HISTORY_FLAG","0");
            queryWrapper.eq("DEL","0");
            List<Score> myScoreList=scoreMapper.selectList(queryWrapper);
            Score myScore=null;
            if(!myScoreList.isEmpty()){
                myScore=myScoreList.get(0);
            }
            result.put("myScore",myScore);
            // b、如某一项目，已被某专家操作退回补充材料，其他专家不可对该项目进行评分，提示其“该项目已被要求重新提交材料”
            boolean canCommit=true;
            String message="";
            /*if(counterSign==true){
                boolean existCategoryIdGiveBackByOtherPerson=queryExistCategoryIdGiveBackByOtherPerson(bussinessKey,categoryId,userId,pdNode.getRoleId());
                if(existCategoryIdGiveBackByOtherPerson==true){
                    canCommit=false;
                    message="该项目已被要求重新提交材料";
                }
            }*/
            result.put("canCommit", canCommit);
            result.put("message", message);
            String scoreTitleLabel="评分";
            if(StringUtils.equals(RoleCodeEnum.区教育局.getValue(),auditUserVo.getRoleCode())){
                scoreTitleLabel="初审评分";
            }else if(StringUtils.equals(RoleCodeEnum.专家.getValue(),auditUserVo.getRoleCode())){
                scoreTitleLabel="专家组评分";
            }
            result.put("scoreTitleLabel",scoreTitleLabel);
        }

        return result;
    }
    @Override
    public boolean queryExistCategoryIdGiveBackByMySelf(String bussinessKey, String myUserId,String roleId,boolean counterSign){
        boolean existCategoryIdGiveBackByMySelf=false;
        QueryWrapper<Score> queryWrapper=new QueryWrapper();
        queryWrapper.eq("BUSSINESS_KEY",bussinessKey);
        queryWrapper.eq("HISTORY_FLAG","0");
        queryWrapper.eq("GIVE_BACK","1");
        queryWrapper.eq("DEL","0");
        if(counterSign==true){
            queryWrapper.eq("AUDIT_PERSON",myUserId);
        }else{
            queryWrapper.eq("AUDIT_ROLE",roleId);
        }
        List<Score> list=scoreMapper.selectList(queryWrapper);
        if(!list.isEmpty()){
            existCategoryIdGiveBackByMySelf=true;
        }
        return existCategoryIdGiveBackByMySelf;
    }


    private boolean queryExistCategoryIdGiveBackByOtherPerson(String bussinessKey,String categoryId,String myUserId,String roleId){
        boolean existCategoryIdGiveBackByOtherPerson=false;
        QueryWrapper<Score> queryWrapper=new QueryWrapper();
        queryWrapper.eq("BUSSINESS_KEY",bussinessKey);
        queryWrapper.eq("CATEGORY_ID",categoryId);
        queryWrapper.eq("HISTORY_FLAG","0");
        queryWrapper.ne("AUDIT_PERSON",myUserId);
        queryWrapper.eq("GIVE_BACK","1");
        queryWrapper.eq("DEL","0");
        queryWrapper.eq("AUDIT_ROLE",roleId);
        List<Score> list=scoreMapper.selectList(queryWrapper);
        if(!list.isEmpty()){
            existCategoryIdGiveBackByOtherPerson=true;
        }
        return existCategoryIdGiveBackByOtherPerson;
    }
    private  List<Score> queryExistCategoryIdAgreeByOtherPerson(String bussinessKey,String categoryId,String myUserId,String roleId){
        QueryWrapper<Score> queryWrapper=new QueryWrapper();
        queryWrapper.eq("BUSSINESS_KEY",bussinessKey);
        queryWrapper.eq("CATEGORY_ID",categoryId);
        queryWrapper.eq("HISTORY_FLAG","0");
        queryWrapper.ne("AUDIT_PERSON",myUserId);
        queryWrapper.eq("GIVE_BACK","0");
        queryWrapper.eq("DEL","0");
        queryWrapper.eq("AUDIT_ROLE",roleId);
        List<Score> list=scoreMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public JSONObject commitCategoryIdScore(JSONObject jsonObject) {
        JSONObject result=new JSONObject();
        String piId=jsonObject.getString("id");//流程实例id
        String bussinessKey=jsonObject.getString("bussinessKey");
        String categoryId=jsonObject.getString("categoryId");
        String tongyiTuihuiPage=jsonObject.getString("tongyiTuihuiPage");


        Double score=jsonObject.getDouble("score");
        String giveBack=jsonObject.getString("giveBack");
        String opinion=jsonObject.getString("opinion");
        IeduUserVo iEduUserVo=IeduUserController.load(null,null);
        AuditUserVo auditUserVo=iEduUserVo.getAuditUserVo();

        ProcInst procInst = procInstMapper.selectById(piId);
        //根据流程实例查询流程节点
        String curNodeId = procInst.getCurNodeId();
        PdNode pdNode = pdNodeMapper.selectById(curNodeId);
        //节点状态检验
        if(StringUtils.equals(tongyiTuihuiPage,"1")){
            QueryWrapper<ReturnBase> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("DECLARE_ID",bussinessKey);
            int countBussinessKey=returnBaseMapper.selectCount(queryWrapper);
            if(countBussinessKey!=1){
                result.put("status", false);
                result.put("msg", "该审批发生变化，请刷新待办列表");
                return result;
            }
        }else{
            boolean isInCurrentTodoList= procInstService.checkIsInCurrentTodoListAndNode(piId);
            if(isInCurrentTodoList==false){
                result.put("status", false);
                result.put("msg", "该审批发生变化，请刷新待办列表");
                return result;
            }
        }

        boolean counterSign=procInstService.queryCounterSignByPiId(piId);
        if(counterSign==true){
            //需限制每个专家针对单所学校退回补充材料的项目数，目前暂定可退回补充材料的项目上限为20条，
            //如专家选择第21条需退回项目时，弹窗提示专家“允许退回补充材料的项目上限为20条，您已超过限制。“
            if(StringUtils.equals(giveBack,"1")){
                int countMyOrNodeGiveBack=countMyOrNodeGiveBack(counterSign,auditUserVo,bussinessKey);
                if(countMyOrNodeGiveBack>=20){
                    result.put("status", false);
                    result.put("msg", "允许退回补充材料的项目上限为20条，您已超过限制。");
                    return result;
                }
            }
            // b、如某一项目，已被某专家操作退回补充材料，其他专家不可对该项目进行评分，提示其“该项目已被要求重新提交材料”
            /*boolean existCategoryIdGiveBackByOtherPerson=queryExistCategoryIdGiveBackByOtherPerson(bussinessKey,categoryId,auditUserVo.getId(),pdNode.getRoleId());
            if(existCategoryIdGiveBackByOtherPerson==true){
                result.put("status", false);
                result.put("msg", "该项目已被要求重新提交材料");
                return result;
            }*/
            //a、如果某一项目，已有专家进行评分，该项目其他专家不可进行退回补充材料操作，
            // 在选择退回时，给予提示“已有专家xxx，对此项目进行打分，请撤销评分后，再进行退回操作”。
            /*if(StringUtils.equals(giveBack,"1")){
                List<Score> list=queryExistCategoryIdAgreeByOtherPerson(bussinessKey,categoryId,auditUserVo.getId(),pdNode.getRoleId());
                if(!list.isEmpty()){
                    QueryWrapper<AuditUser> auditUserQueryWrapper=new QueryWrapper<>();
                    auditUserQueryWrapper.eq("ID",list.get(0).getAuditPerson());
                    AuditUser auditUser=auditUserMapper.selectOne(auditUserQueryWrapper);
                    result.put("status", false);
                    result.put("msg", "已有专家'"+auditUser.getName()+"'，对此项目进行打分，请撤销评分后，再进行退回操作");
                    return result;
                }
            }*/
        }
        //非统一退回界面
        if(!StringUtils.equals(tongyiTuihuiPage,"1")){
            QueryWrapper<FieldValue> fvQueryWrapper = new QueryWrapper<>();
            fvQueryWrapper.eq("DECLARE_ID", bussinessKey);
            fvQueryWrapper.eq("CATEGORY_ID", categoryId);
            fvQueryWrapper.eq("DEL", "0");
            FieldValue fieldValue = fieldValueMapper.selectOne(fvQueryWrapper);
            if (score != null && score.doubleValue() != Double.parseDouble(fieldValue.getZpScore())
                    && StringUtils.isBlank(opinion)) {
                result.put("status", false);
                result.put("msg", "您修改了学校的自评得分，请填写说明后，再提交。");
                return result;
            }
        }
        //如果第二次提交的状态跟前一次一致，就先删除
        boolean chongfu=false;
        QueryWrapper<Score> queryWrapper=new QueryWrapper();
        queryWrapper.eq("BUSSINESS_KEY",bussinessKey);
        queryWrapper.eq("CATEGORY_ID",categoryId);
        queryWrapper.eq("AUDIT_PERSON",auditUserVo.getId());
        queryWrapper.eq("HISTORY_FLAG","0");
        queryWrapper.eq("DEL","0");
        queryWrapper.eq("GIVE_BACK",giveBack);
        List<Score> list=scoreMapper.selectList(queryWrapper);
        if(list.size()==1){
            chongfu=true;
        }
        if(chongfu==true){
            UpdateWrapper<Score> updateWrapper=new UpdateWrapper();
            updateWrapper.eq("BUSSINESS_KEY",bussinessKey);
            updateWrapper.eq("CATEGORY_ID",categoryId);
            updateWrapper.eq("AUDIT_PERSON",auditUserVo.getId());
            updateWrapper.eq("HISTORY_FLAG","0");
            updateWrapper.eq("DEL","0");
            scoreMapper.delete(updateWrapper);//删除上一次与这次相同的审批结果
        }

        //备份指标历史打分
        UpdateWrapper<Score> updateWrapper=new UpdateWrapper();
        updateWrapper.eq("BUSSINESS_KEY",bussinessKey);
        updateWrapper.eq("CATEGORY_ID",categoryId);
        if(counterSign==true){
            updateWrapper.eq("AUDIT_PERSON",auditUserVo.getId());
        }else{
            updateWrapper.eq("AUDIT_ROLE",auditUserVo.getRoleId());
        }
        updateWrapper.eq("HISTORY_FLAG","0");
        updateWrapper.eq("DEL","0");
        Score oldScore=new Score();
        oldScore.setHistoryFlag("1");
        oldScore.setUpdateTime(new Date());
        scoreMapper.update(oldScore,updateWrapper);//备份
        //新增指标打分
        String newScoreUuid=com.sunsharing.component.utils.base.StringUtils.generateUUID();
        Score newScore=new Score();
        newScore.setId(newScoreUuid);
        newScore.setBussinessKey(bussinessKey);
        newScore.setCategoryId(categoryId);
        newScore.setFieldContent("");
        newScore.setZpScore(score);
        newScore.setOpinion(opinion);
        newScore.setState("0");
        newScore.setGiveBack(giveBack);
        newScore.setAuditTime(new Date());
        newScore.setAuditPerson(auditUserVo.getId());
        newScore.setAuditRole(auditUserVo.getRoleId());
        newScore.setCreateTime(new Date());
        newScore.setHistoryFlag("0");
        newScore.setDel("0");
        scoreMapper.insert(newScore);


        //统一退回界面 取消退回操作，归档其他节点打回记录
        if(StringUtils.equals(tongyiTuihuiPage,"1")
            &&StringUtils.equals(giveBack,"0")){
            UpdateWrapper<Score>  scoreUpdateWrapper=new UpdateWrapper<>();
            scoreUpdateWrapper.eq("BUSSINESS_KEY",bussinessKey);
            scoreUpdateWrapper.eq("CATEGORY_ID",categoryId);
            scoreUpdateWrapper.eq("GIVE_BACK","1");
            scoreUpdateWrapper.eq("HISTORY_FLAG","0");
            Score scoreHistory=new Score();
            scoreHistory.setHistoryFlag("1");
            scoreMapper.update(scoreHistory,scoreUpdateWrapper);
        }
        //打回处理----------------------------------------
        supplyIndexService.assignSupplyIndex(bussinessKey,auditUserVo.getRoleId(),counterSign);


        //删除汇总评分，必须点击大保存才生成汇总评分----------------------------------------
        deletePfhzByLoginUserAndPiId(auditUserVo,bussinessKey,piId);
        result.put("status", true);
        result.put("msg", "提交成功");
        return result;
    }
    @Override
    public JSONObject cleanCategoryIdScore(JSONObject jsonObject) {
        JSONObject result=new JSONObject();

        String piId=jsonObject.getString("id");//流程实例id
        String bussinessKey=jsonObject.getString("bussinessKey");
        String categoryId=jsonObject.getString("categoryId");

        IeduUserVo iEduUserVo=IeduUserController.load(null,null);
        AuditUserVo auditUserVo=iEduUserVo.getAuditUserVo();

        boolean isInCurrentTodoList= procInstService.checkIsInCurrentTodoListAndNode(piId);
        if(isInCurrentTodoList==false){
            result.put("status", false);
            result.put("msg", "该审批发生变化，请刷新待办列表");
            return result;
        }
        boolean counterSign=procInstService.queryCounterSignByPiId(piId);
        //备份指标历史打分
        UpdateWrapper<Score> updateWrapper=new UpdateWrapper();
        updateWrapper.eq("BUSSINESS_KEY",bussinessKey);
        updateWrapper.eq("CATEGORY_ID",categoryId);
        if(counterSign==true){
            updateWrapper.eq("AUDIT_PERSON",auditUserVo.getId());
        }else{
            updateWrapper.eq("AUDIT_ROLE",auditUserVo.getRoleId());
        }
        updateWrapper.eq("HISTORY_FLAG","0");
        updateWrapper.eq("DEL","0");
        Score oldScore=new Score();
        oldScore.setHistoryFlag("1");
        oldScore.setUpdateTime(new Date());
        scoreMapper.update(oldScore,updateWrapper);

        //打回处理---------------------------------------
        supplyIndexService.assignSupplyIndex(bussinessKey,auditUserVo.getRoleId(),counterSign);

        //删除汇总评分，必须点击大保存才生成汇总评分----------------------------------------
        deletePfhzByLoginUserAndPiId(auditUserVo,bussinessKey,piId);
        result.put("status", true);
        result.put("msg", "提交成功");
        return result;
    }
    public int countMyOrNodeGiveBack(boolean counterSign,AuditUserVo auditUserVo,String bussinessKey){
        int myOrNodeGiveBackCount=0;
        QueryWrapper<Score> queryWrapper=new QueryWrapper();
        queryWrapper.eq("BUSSINESS_KEY",bussinessKey);
        queryWrapper.eq("AUDIT_ROLE",auditUserVo.getRoleId());
        queryWrapper.eq("GIVE_BACK","1");
        if(counterSign){
            queryWrapper.eq("AUDIT_PERSON",auditUserVo.getId());
        }
        queryWrapper.eq("HISTORY_FLAG","0");
        queryWrapper.eq("DEL","0");
        myOrNodeGiveBackCount=scoreMapper.selectCount(queryWrapper);
        return myOrNodeGiveBackCount;
    }



    @Override
    public void deletePfhzByLoginUserAndPiId(AuditUserVo auditUserVo, String bussinessKey, String piId){
        UpdateWrapper<Pfhz> pfhzUpdateWrapper=new UpdateWrapper();
        pfhzUpdateWrapper.eq("BUSSINESS_KEY",bussinessKey);
        boolean counterSign=procInstService.queryCounterSignByPiId(piId);
        //会签删除自己的汇总
        if(counterSign){
            pfhzUpdateWrapper.eq("PF_PERSON_ID",auditUserVo.getId());
        }else{//普通删除节点或角色的汇总
            pfhzUpdateWrapper.eq("PF_ROLE",auditUserVo.getRoleId());
        }
        pfhzMapper.delete(pfhzUpdateWrapper);
    }
    @Override
    public FieldValueVo checkAllIndexCompelete(AuditUserVo auditUserVo,String piId,String bussinessKey){
        boolean allIndexCompelete=true;
        boolean counterSign=procInstService.queryCounterSignByPiId(piId);
        String userId=null;
        String roleId=null;
        //会签自己
        if(counterSign){
            userId=auditUserVo.getId();
        }else{//普通节点或角色
            roleId=auditUserVo.getRoleId();
        }
        FieldValueVo rr=null;
        List<FieldValueVo> fieldValueVoList=scoreMapper.getAllIndexPinjiaByUser(userId,roleId,bussinessKey);
        for (FieldValueVo fieldValueVo:fieldValueVoList){
            if(StringUtils.equals(fieldValueVo.getGiveBack(),"1")||StringUtils.isBlank(fieldValueVo.getOtherScore())){
                rr=fieldValueVo;
                break;
            }
        }
        return rr;
    }
    @Override
    public Map<String,Map<String,Object>> queryCurrentIndexScoreMap(String bussinessKey, String piId){
        IeduUserVo iEduUserVo= IeduUserController.load(null,null);
        AuditUserVo auditUserVo=iEduUserVo.getAuditUserVo();
        QueryWrapper<Score> scoreQueryWrapper=new QueryWrapper<>();
        scoreQueryWrapper.eq("BUSSINESS_KEY",bussinessKey);
        scoreQueryWrapper.eq("HISTORY_FLAG","0");
        scoreQueryWrapper.eq("DEL","0");
        boolean counterSign=procInstService.queryCounterSignByPiId(piId);
        //会签自己的汇总
        if(counterSign){
            scoreQueryWrapper.eq("AUDIT_PERSON",auditUserVo.getId());
        }else{//普通节点或角色的汇总
            scoreQueryWrapper.eq("AUDIT_ROLE",auditUserVo.getRoleId());
        }
        List<Score> scoreList=scoreMapper.selectList(scoreQueryWrapper);
        Map<String,Map<String,Object>> categoryIdScoreMap=new HashMap<>();
        for(Score score:scoreList){
            Map<String,Object> map=new HashMap<>();
            map.put("giveBack",score.getGiveBack());
            if(score.getZpScore()!=null){
                BigDecimal b=new BigDecimal(score.getZpScore());
                Double myAuditScore=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                map.put("myAuditScore",myAuditScore);
            }else{
                map.put("myAuditScore",0);
            }
            categoryIdScoreMap.put(score.getCategoryId(),map);
        }
        return categoryIdScoreMap;
    }

    @Override
    public Map<String,Double> querExpertIndexAvScoreMap(String bussinessKey){
        List<ScoreVo> list=scoreMapper.queryExpertIndexScoreList(bussinessKey);
        Map<String,Double> expertIndexAvScoreMap=new HashMap<>();
        for(ScoreVo scoreVo:list){
            log.info("指标平均分:"+JSONObject.toJSONString(scoreVo));
            BigDecimal b=new BigDecimal(scoreVo.getAvScore());
            Double avScore=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            expertIndexAvScoreMap.put(scoreVo.getCategoryId(), avScore);
        }
        return expertIndexAvScoreMap;
    }

    @Override
    public Map<String,String> queryFinalIndexScoreMap(String bussinessKey){
        if(StringUtils.isBlank(bussinessKey)){
            return new HashMap<>();
        }
        IeduUserVo iEduUserVo= IeduUserController.load(null,null);
        AuditUserVo auditUserVo=iEduUserVo.getAuditUserVo();
        QueryWrapper<ProcInst> queryWrapper=new QueryWrapper();
        queryWrapper.eq("BUSSINESS_KEY",bussinessKey);
        ProcInst procInst=procInstMapper.selectOne(queryWrapper);
        if(procInst==null){
            return new HashMap<>();
        }
        PdNode pdNode=pdNodeMapper.selectById(procInst.getCurNodeId());
        if(pdNode==null){
            return new HashMap<>();
        }
        List<ScoreVo> scoreVoList=supplyIndexMapper.queryFinalIndexScoreMap(bussinessKey,pdNode.getRoleId());
        /*//
        List<ScoreVo> scoreVoList=new ArrayList<>();
        if(StringUtils.equals(pdNode.getScoreButton(),"2")){
            scoreVoList=supplyIndexMapper.queryFinalIndexScoreMap(bussinessKey,pdNode.getRoleId());
        }else{
            scoreVoList=supplyIndexMapper.queryFinalIndexScoreMapForOnlyGiveBack(bussinessKey);
        }*/
        Map<String,String> categoryIdSupplyIndexMap=new HashMap<>();
        for(ScoreVo scoreVo:scoreVoList){
            categoryIdSupplyIndexMap.put(scoreVo.getCategoryId(),scoreVo.getGiveBack());
        }
        return categoryIdSupplyIndexMap;
    }


    @Override
    public JSONObject commitPfhz(JSONObject jsonObject) {
        JSONObject result=new JSONObject();
        String piId=jsonObject.getString("id");
        String bussinessKey=jsonObject.getString("bussinessKey");

        IeduUserVo iEduUserVo= IeduUserController.load(null,null);
        AuditUserVo auditUserVo=iEduUserVo.getAuditUserVo();
        boolean isInCurrentTodoList= procInstService.checkIsInCurrentTodoListAndNode(piId);
        if(isInCurrentTodoList==false){
            result.put("status", false);
            result.put("msg", "该审批发生变化，请刷新待办列表");
            return result;
        }
        //清除总评分
        deletePfhzByLoginUserAndPiId(auditUserVo,bussinessKey,piId);
        //检查指标评分是否都已经完成
        FieldValueVo allIndexCompelete=checkAllIndexCompelete(auditUserVo,piId,bussinessKey);
        if(allIndexCompelete!=null){
            if(StringUtils.equals(allIndexCompelete.getGiveBack(),"1")){
                result.put("status", true);
                result.put("msg", "请记得审批退回");
                return result;
            }
            if(StringUtils.isBlank(allIndexCompelete.getOtherScore())){
                result.put("status", false);
                result.put("msg", "还有指标未完成评分");
                return result;
            }
        }
        //生成总评分
        //查询所有指标的分数
        ProcInst procInst=procInstMapper.selectById(piId);
        QueryWrapper<Score> scoreQueryWrapper=new QueryWrapper<>();
        scoreQueryWrapper.eq("BUSSINESS_KEY",bussinessKey);
        scoreQueryWrapper.eq("GIVE_BACK","0");
        scoreQueryWrapper.eq("HISTORY_FLAG","0");
        scoreQueryWrapper.eq("DEL","0");
        scoreQueryWrapper.isNotNull("ZP_SCORE");
        boolean counterSign=procInstService.queryCounterSignByPiId(piId);
        //会签自己的汇总
        if(counterSign){
            scoreQueryWrapper.eq("AUDIT_PERSON",auditUserVo.getId());
        }else{//普通节点或角色的汇总
            scoreQueryWrapper.eq("AUDIT_ROLE",auditUserVo.getRoleId());
        }
        List<Score> scoreList=scoreMapper.selectList(scoreQueryWrapper);
        Map<String,Double> categoryIdScoreMap=new HashMap<>();
        for(Score score:scoreList){
            categoryIdScoreMap.put(score.getCategoryId(),score.getZpScore());
        }
        Double pfhzSum=0.0;
        for(Map.Entry<String,Double> e:categoryIdScoreMap.entrySet()){
            pfhzSum=pfhzSum+e.getValue();
        }
        BigDecimal b=new BigDecimal(pfhzSum);
        pfhzSum=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        //入库
        String pfhzUuid=com.sunsharing.component.utils.base.StringUtils.generateUUID();
        Pfhz pfhz=new Pfhz();
        pfhz.setHzId(pfhzUuid);
        pfhz.setBussinessKey(bussinessKey);
        pfhz.setNodeId(procInst.getCurNodeId());
        pfhz.setPId(piId);
        pfhz.setPfRole(auditUserVo.getRoleId());
        pfhz.setPfPersonId(auditUserVo.getId());
        pfhz.setPfPersonName(auditUserVo.getName());
        pfhz.setPfPersonIdnum(auditUserVo.getIdNum());
        pfhz.setPfSum(pfhzSum);
        pfhz.setCreateTime(new Date());
        pfhzMapper.insert(pfhz);

        result.put("status", true);
        result.put("msg", "提交成功");
        return result;
    }

}
