package com.sunsharing.smartcampus.service.impl.audit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sunsharing.smartcampus.constant.enums.AuditEnum;
import com.sunsharing.smartcampus.mapper.audit.NodePersonMapper;
import com.sunsharing.smartcampus.mapper.audit.ProcInstMapper;
import com.sunsharing.smartcampus.mapper.audit.ScoreMapper;
import com.sunsharing.smartcampus.model.pojo.audit.*;
import com.sunsharing.smartcampus.mapper.audit.PfhzMapper;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.audit.PfhzVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.model.vo.filed.FieldValueVo;
import com.sunsharing.smartcampus.service.audit.NodePersonService;
import com.sunsharing.smartcampus.service.audit.PfhzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.service.audit.ProcInstService;
import com.sunsharing.smartcampus.service.audit.ScoreService;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评分汇总表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
@Service
@Transactional
public class PfhzServiceImpl extends ServiceImpl<PfhzMapper, Pfhz> implements PfhzService {

    @Autowired
    PfhzMapper pfhzMapper;
    @Autowired
    ScoreMapper scoreMapper;
    @Autowired
    ProcInstMapper procInstMapper;
    @Autowired
    NodePersonMapper nodePersonMapper;
    //------------------------------------
    @Autowired
    ProcInstService procInstService;
    @Autowired
    ScoreService scoreService;

    @Override
    public JSONObject queryPfhzList(JSONObject jsonObject) {
        JSONObject result=new JSONObject();
        String bussinessKey=jsonObject.getString("bussinessKey");
        List<PfhzVo> list=pfhzMapper.queryPfhzList(bussinessKey);
        for(PfhzVo pfhzVo:list){
            DecimalFormat df=new DecimalFormat("#0.00");
            pfhzVo.setPfSumStr(df.format(pfhzVo.getPfSum()));
        }
        result.put("list",list);
        return result;
    }

    @Override
    public Map<String,Object> computeStarLevelByPfhz(String piId, PdNode pdNode){
        Map<String,Object> map=new HashMap<>();
        Integer starLevel=0;
        QueryWrapper<Pfhz> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("P_ID",piId);
        queryWrapper.eq("NODE_ID",pdNode.getNodeId());
        List<Pfhz> list=pfhzMapper.selectList(queryWrapper);
        Double sumScore=0.0;
        Double avScore=0.0;
        for(Pfhz pfhz:list){
            sumScore=sumScore+pfhz.getPfSum();
        }

        avScore=sumScore/list.size();
        BigDecimal b=new BigDecimal(avScore);
        avScore=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        //100到120一星   ，  120到140  二星 ，140到160 三星，160到180四星，180以上五星
        if(avScore>=100&&avScore<120){
            starLevel=1;
        }else if(avScore>=120&&avScore<140){
            starLevel=2;
        }else if(avScore>=140&&avScore<160){
            starLevel=3;
        }else if(avScore>=160&&avScore<180){
            starLevel=4;
        }else if(avScore>=180){
            starLevel=5;
        }else{
            starLevel=0;
        }
        map.put("avScore",avScore);
        map.put("starLevel",starLevel);
        return map;
    }
    @Override
    //{"score": "must","mustOn": ["11"]}
    //{"score":"can"}
    public boolean checkMyPfhzCompleteByConfig(String piId, PdNode pdNode, String result,String userId){
        boolean myPfhzComplete=true;
        String mustScoreOnOption=pdNode.getMustScoreOnOption();
        if(StringUtils.isBlank(mustScoreOnOption)){
            return myPfhzComplete;
        }
        JSONObject mustScoreOnOptionJson=JSONObject.parseObject(mustScoreOnOption);
        if(StringUtils.equals(mustScoreOnOptionJson.getString("score"),"can")){
            return myPfhzComplete;
        }
        JSONArray mustOnJSONArray=mustScoreOnOptionJson.getJSONArray("mustOn");
        boolean mustScore=false;
        for(Object mustOn:mustOnJSONArray){
            String mustOnState=mustOn.toString();
            if(StringUtils.equals(mustOnState,result)){
                mustScore=true;
            }
        }
        if(mustScore==false){
            return myPfhzComplete;
        }
        boolean counterSign=StringUtils.equals(pdNode.getCounterSign(),"1")?true:false;
        //汇总评分的数量
        QueryWrapper<Pfhz> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("P_ID",piId);
        queryWrapper.eq("NODE_ID",pdNode.getNodeId());
        if(counterSign==true){
            queryWrapper.eq("PF_PERSON_ID",userId);
        }else{
            queryWrapper.eq("PF_ROLE",pdNode.getRoleId());
        }
        int indeedCount=pfhzMapper.selectCount(queryWrapper);
        if(indeedCount==0){
            myPfhzComplete=false;
        }
        return myPfhzComplete;
    }

    @Override
    public boolean checkMyScoreCompleteAllOnAgreeByConfig(String piId, PdNode pdNode, String result, String userId) {
        boolean myScoreCompleteAllOnAgree=true;
        //受理中心初审不做检验
        if(pdNode.getSeqNum().intValue()==2||pdNode.getSeqNum().intValue()==4){
            return myScoreCompleteAllOnAgree;
        }

        //检查指标评分是否都已经完成
        if(AuditEnum.通过.eq(result)){
            boolean haveOneScore=false;
            boolean haveAllScore=true;

            boolean counterSign=StringUtils.equals(pdNode.getCounterSign(),"1")?true:false;
            AuditUserVo auditUserVo = IeduUserController.load(null, null).getAuditUserVo();
            ProcInst procInst = procInstMapper.selectById(piId);
            String userIdP=null;
            String roleIdP=null;
            //会签自己
            if(counterSign){
                userIdP=auditUserVo.getId();
            }else{//普通节点或角色
                roleIdP=auditUserVo.getRoleId();
            }
            List<FieldValueVo> fieldValueVoList=scoreMapper.getAllIndexPinjiaByUser(userIdP,roleIdP,procInst.getBussinessKey());
            for (FieldValueVo fieldValueVo:fieldValueVoList){
                if(StringUtils.isNotBlank(fieldValueVo.getOtherScore())){
                    haveOneScore=true;
                }
                if(StringUtils.equals(fieldValueVo.getGiveBack(),"1")||StringUtils.isBlank(fieldValueVo.getOtherScore())){
                    haveAllScore=false;
                }
            }
            if(haveOneScore==true){
                //汇总评分的数量
                QueryWrapper<Pfhz> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("P_ID",piId);
                queryWrapper.eq("NODE_ID",pdNode.getNodeId());
                if(counterSign==true){
                    queryWrapper.eq("PF_PERSON_ID",userId);
                }else{
                    queryWrapper.eq("PF_ROLE",pdNode.getRoleId());
                }
                int indeedCount=pfhzMapper.selectCount(queryWrapper);
                if(indeedCount==0){
                    myScoreCompleteAllOnAgree=false;
                }
            }
            if(haveOneScore==true&&haveAllScore==false){
                myScoreCompleteAllOnAgree=false;
            }
        }
        return myScoreCompleteAllOnAgree;
    }
    @Override
    public boolean checkMyScoreCompleteAllOnAgreeOrNoAgreeByConfig(String piId, PdNode pdNode, String result, String userId) {
        boolean done=true;
        boolean mustScore=false;//必须（评价）
        boolean haveOneScore=false;
        boolean haveAllScore = true;
        //受理中心初审不做检验
        if(pdNode.getSeqNum().intValue()==2||pdNode.getSeqNum().intValue()==4){
            return haveAllScore;
        }
        String mustScoreOnOption=pdNode.getMustScoreOnOption();
        if(StringUtils.isNotBlank(mustScoreOnOption)){
            JSONObject mustScoreOnOptionJson=JSONObject.parseObject(mustScoreOnOption);
            if(StringUtils.equals(mustScoreOnOptionJson.getString("score"),"must")){
                mustScore=true;
            }
        }
        //检查指标评分是否都已经完成
        boolean counterSign = StringUtils.equals(pdNode.getCounterSign(), "1") ? true : false;
        AuditUserVo auditUserVo = IeduUserController.load(null, null).getAuditUserVo();
        ProcInst procInst = procInstMapper.selectById(piId);
        String userIdP=null;
        String roleIdP=null;
        //会签自己
        if(counterSign){
            userIdP=auditUserVo.getId();
        }else{//普通节点或角色
            roleIdP=auditUserVo.getRoleId();
        }
        List<FieldValueVo> fieldValueVoList=scoreMapper.getAllIndexPinjiaByUser(userIdP,roleIdP,procInst.getBussinessKey());
        for (FieldValueVo fieldValueVo:fieldValueVoList){
            if(StringUtils.isNotBlank(fieldValueVo.getOtherScore())){
                haveOneScore=true;
            }
            if(StringUtils.isBlank(fieldValueVo.getOtherScore())){
                haveAllScore=false;
            }
        }
        if((mustScore||haveOneScore)
            &&haveAllScore==false){
            done=false;
        }

        return done;
    }



//    public static void main(String[] args) {
//        boolean pfhzComplete=true;
//        String result="12";
//        String mustScoreOnOption="{\"score\": \"must\",\"mustOn\": [\"11\"]}";
//        if(StringUtils.isBlank(mustScoreOnOption)){
//            System.out.println(pfhzComplete);
//        }
//        JSONObject mustScoreOnOptionJson=JSONObject.parseObject(mustScoreOnOption);
//        if(StringUtils.equals(mustScoreOnOptionJson.getString("score"),"can")){
//            System.out.println(pfhzComplete);
//        }
//        JSONArray mustOnJSONArray=mustScoreOnOptionJson.getJSONArray("mustOn");
//        boolean mustScore=false;
//        for(Object mustOn:mustOnJSONArray){
//            String mustOnState=mustOn.toString();
//            if(StringUtils.equals(mustOnState,result)){
//                mustScore=true;
//            }
//        }
//        if(mustScore==false){
//            System.out.println(pfhzComplete);
//        }
//        System.out.println(pfhzComplete);
//    }


}
