package com.sunsharing.smartcampus.service.impl.audit;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sunsharing.smartcampus.mapper.audit.PdNodeMapper;
import com.sunsharing.smartcampus.mapper.audit.ProcInstMapper;
import com.sunsharing.smartcampus.mapper.audit.ScoreMapper;
import com.sunsharing.smartcampus.model.pojo.audit.*;
import com.sunsharing.smartcampus.mapper.audit.SupplyIndexMapper;
import com.sunsharing.smartcampus.model.pojo.filed.FieldValue;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.audit.PfhzVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.service.audit.ProcInstService;
import com.sunsharing.smartcampus.service.audit.SupplyIndexService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

/**
 * <p>
 * 需要补充的指标 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
@Service
@Transactional
public class SupplyIndexServiceImpl extends ServiceImpl<SupplyIndexMapper, SupplyIndex> implements SupplyIndexService {
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private SupplyIndexMapper supplyIndexMapper;

    @Autowired
    private ProcInstMapper procInstMapper;
    @Autowired
    private PdNodeMapper pdNodeMapper;

    @Autowired
    private ProcInstService procInstService;

    @Override
    public void assignSupplyIndex(String bussinessKey,String scoreUserId,boolean counterSign) {
        QueryWrapper<ProcInst> procInstQueryWrapper=new QueryWrapper();
        procInstQueryWrapper.eq("BUSSINESS_KEY",bussinessKey);
        ProcInst procInst=procInstMapper.selectOne(procInstQueryWrapper);
        if(procInst==null){
            return;
        }
        PdNode pdNode=pdNodeMapper.selectById(procInst.getCurNodeId());
        if(pdNode==null){
            return;
        }
        QueryWrapper<Score> queryWrapper=new QueryWrapper();
        queryWrapper.eq("BUSSINESS_KEY",bussinessKey);
        queryWrapper.eq("GIVE_BACK","1");
        queryWrapper.eq("HISTORY_FLAG","0");
        queryWrapper.eq("DEL","0");
        //全局公用打回指标记录表
        /*if(counterSign==true){
            queryWrapper.eq("AUDIT_PERSON",scoreUserId);
        }else{
            queryWrapper.eq("AUDIT_ROLE",pdNode.getRoleId());
        }*/
        List<Score> scoreList=scoreMapper.selectList(queryWrapper);
        Map<String,Score> categoryMap=new HashMap<>();
        for(Score score:scoreList){
            categoryMap.put(score.getCategoryId(),score);
        }
        //先删除
       supplyIndexMapper.delIndexByBusinessKey(bussinessKey);
        //
        AuditUserVo auditUserVo = IeduUserController.load(null, null).getAuditUserVo();
        //
        for (String key : categoryMap.keySet()) {
            Score score=categoryMap.get(key);
            String supplyIndexUuid=com.sunsharing.component.utils.base.StringUtils.generateUUID();
            SupplyIndex supplyIndex=new SupplyIndex();
            supplyIndex.setId(supplyIndexUuid);
            supplyIndex.setBussinessKey(bussinessKey);
            supplyIndex.setMainIndexId(score.getCategoryId());
            supplyIndex.setOpinon(score.getOpinion());
            supplyIndex.setStatus("0");
            supplyIndex.setCreateTime(new Date());
            supplyIndex.setCreatePerson(auditUserVo.getId());
            supplyIndex.setDel("0");
            supplyIndexMapper.insert(supplyIndex);
        }
    }

    @Override
    public int updateIndexState(String businessKey, String mainIndexId, String status) {
        return supplyIndexMapper.updateIndexState( businessKey,  mainIndexId,  status);
    }

    @Override
    public boolean checkGivebackChangedFinish(String businessKey) {
        QueryWrapper<SupplyIndex> queryWrapper=new QueryWrapper();
        queryWrapper.eq("BUSSINESS_KEY",businessKey);
        queryWrapper.eq("STATUS","0");
        queryWrapper.eq("DEL","0");
        int supplyIndexNoChangeCount=supplyIndexMapper.selectCount(queryWrapper);
        if(supplyIndexNoChangeCount>0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public JSONObject findSupplyIndex(JSONObject jsonObject) {
        JSONObject result=new JSONObject();
        String bussinessKey=jsonObject.getString("bussinessKey");
        String categoryId=jsonObject.getString("categoryId");
        QueryWrapper<SupplyIndex> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("BUSSINESS_KEY",bussinessKey);
        queryWrapper.eq("MAIN_INDEX_ID",categoryId);
        SupplyIndex supplyIndex=supplyIndexMapper.selectOne(queryWrapper);
        result.put("supplyIndex",supplyIndex);
        return result;
    }
}
