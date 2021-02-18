package com.sunsharing.smartcampus.service.impl.audit;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.common.utils.StringUtils;
import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.smartcampus.constant.enums.NodeIdentifierEnum;
import com.sunsharing.smartcampus.mapper.apply.TzhxyBaseInfoMapper;
import com.sunsharing.smartcampus.mapper.audit.NodePersonMapper;
import com.sunsharing.smartcampus.mapper.audit.PdNodeMapper;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyBaseInfo;
import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.sunsharing.smartcampus.model.vo.audit.AuditOption;
import com.sunsharing.smartcampus.model.vo.audit.SelectButonVo;
import com.sunsharing.smartcampus.service.audit.PdNodeService;
import com.sunsharing.smartcampus.utils.ExpressionResolveUtils;
import com.sunsharing.smartcampus.utils.ResultDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 流程定义节点 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
@Service
@Transactional
public class PdNodeServiceImpl extends ServiceImpl<PdNodeMapper, PdNode> implements PdNodeService {
    @Autowired
    PdNodeMapper pdNodeMapper;

    @Autowired
    TzhxyBaseInfoMapper tzhxyBaseInfoMapper;
    @Autowired
    NodePersonMapper nodePersonMapper;

    public Map getNodeAll(String businessKey) {
        List<Map> nodeAll = pdNodeMapper.getNodeAll(businessKey);
        for(Map map:nodeAll){
            String resultId=(String)map.get("RESULT_ID");
            String nodeId=(String)map.get("NODE_ID");
            PdNode pdNode=pdNodeMapper.selectById(nodeId);
            if(pdNode==null
                    ||org.apache.commons.lang3.StringUtils.isBlank(pdNode.getOtption())){
                continue;
            }
            String otptionStr = pdNode.getOtption();
            SelectButonVo selectButonVo = JSONObject.parseObject(otptionStr, SelectButonVo.class);
            List<AuditOption> auditOptionList=selectButonVo.getOptions();
            for(AuditOption auditOption:auditOptionList){
                if(org.apache.commons.lang3.StringUtils.equals(auditOption.getValue(),resultId)){
                    map.put("RESULT",auditOption.getLabel());
                    break;
                }
            }
        }


        return ResultDataUtils.packParams(nodeAll,"查询成功");
    }

    public Map getNodeLastNoPass(String businessKey) {
        Map nodeLastNoPass = pdNodeMapper.getNodeLastNoPass(businessKey);
        return ResultDataUtils.packParams(nodeLastNoPass,"查询成功");
    }

    public String skipNode(String nodeId,String xzqh) {
        while(true) {
            PdNode pdNode = pdNodeMapper.selectById(nodeId);
            if(pdNode == null) {
                return NodeIdentifierEnum.结束节点.getValue();
            }
            String skipExpression = pdNode.getSkipExpression();
            boolean isSkip = ExpressionResolveUtils.analyzeExpression(skipExpression, MapUtil.ofHashMap("xzqh",xzqh));
            if(!isSkip) {
                return pdNode.getNodeId();
            }
        }
    }
    @Transactional
    public void setBusinessState(String result,String businessKey,String nodeId) {
        //查询业务基础信息
        TzhxyBaseInfo tzhxyBaseInfo = tzhxyBaseInfoMapper.selectById(businessKey);
        //查询指标信息
        PdNode pdNode = pdNodeMapper.selectById(nodeId);
        String bizOtption = pdNode.getBizOtption();
        if("{}".equals(bizOtption) || StringUtils.isBlank(bizOtption)) {
            return;
        }
        //记录上一次的状态
        String finalState = tzhxyBaseInfo.getState();
        tzhxyBaseInfo.setLastState(finalState);

        //解析json
        JSONObject jsonObject = JSONObject.parseObject(bizOtption);
        String businessState = jsonObject.getString(result);
        //更新业务表中state状态
        tzhxyBaseInfo.setState(businessState);

        tzhxyBaseInfoMapper.updateById(tzhxyBaseInfo);
    }
}
