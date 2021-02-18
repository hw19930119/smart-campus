package com.sunsharing.smartcampus.service.impl.audit;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunsharing.share.common.base.exception.ShareBusinessException;
import com.sunsharing.smartcampus.mapper.audit.PdNodeMapper;
import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.sunsharing.smartcampus.model.pojo.audit.ProcDef;
import com.sunsharing.smartcampus.mapper.audit.ProcDefMapper;
import com.sunsharing.smartcampus.service.audit.ProcDefService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 流程定义 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
@Service
@Transactional
public class ProcDefServiceImpl extends ServiceImpl<ProcDefMapper, ProcDef> implements ProcDefService {

    @Autowired
    ProcDefMapper procDefMapper;
    @Autowired
    PdNodeMapper pdNodeMapper;


    @Override
    public JSONObject publish(JSONObject jsonObject) {
        JSONObject result=new JSONObject();
        String id=jsonObject.getString("id");
        //发布定义
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("PD_ID",id);
        ProcDef procDef=procDefMapper.selectOne(queryWrapper);
        if(procDef==null){
            throw new ShareBusinessException(1500, "流程不存在");
            /*result.put("status",false);
            result.put("msg","流程不存在");
            return result;*/
        }
        String procDefNewUuid=com.sunsharing.component.utils.base.StringUtils.generateUUID();;
        ProcDef procDefNew=new ProcDef();
        BeanUtils.copyProperties(procDef,procDefNew);
        procDefNew.setPdId(procDefNewUuid);
        procDefNew.setPublish("1");
        procDefNew.setPublishTime(new Date());
        procDefMapper.insert(procDefNew);
        //发布定义节点
        QueryWrapper pdNodeQueryWrapper=new QueryWrapper();
        pdNodeQueryWrapper.eq("PD_ID",procDef.getPdId());
        pdNodeQueryWrapper.eq("DEL","0");
        pdNodeQueryWrapper.orderByAsc("SEQ_NUM");

        List<PdNode> pdNodeList=pdNodeMapper.selectList(pdNodeQueryWrapper);
        if(pdNodeList.isEmpty()){
            throw new ShareBusinessException(1500, "流程节点不存在");
            /*result.put("status",false);
            result.put("msg","流程节点不存在");
            return result;*/
        }
        List<PdNode> pdNodeNewList=new ArrayList<>();
        for(PdNode pdNode:pdNodeList){
            String pdNodeNewUuid=com.sunsharing.component.utils.base.StringUtils.generateUUID();;
            PdNode pdNodeNew=new PdNode();
            BeanUtils.copyProperties(pdNode,pdNodeNew);
            pdNodeNew.setNodeId(pdNodeNewUuid);
            pdNodeNew.setPdId(procDefNewUuid);
            pdNodeNew.setPreNodeId("start");
            pdNodeNew.setNextNodeId("end");
            pdNodeNewList.add(pdNodeNew);
            pdNodeMapper.insert(pdNodeNew);
        }
        for(int i=0;i<pdNodeNewList.size();i++){
            PdNode preNode=null;//pdNodeList.get(i-1);
            PdNode currNode=pdNodeNewList.get(i);
            PdNode nextNode=null;//pdNodeList.get(i+1);
            if(i>0){
                preNode=pdNodeNewList.get(i-1);
            }
            if(i<pdNodeNewList.size()-1){
                nextNode=pdNodeNewList.get(i+1);
            }
            PdNode updateNode=new PdNode();
            updateNode.setNodeId(currNode.getNodeId());
            updateNode.setPreNodeId("start");
            updateNode.setNextNodeId("end");
            if(preNode!=null){
                updateNode.setPreNodeId(preNode.getNodeId());
            }
            if(nextNode!=null){
                updateNode.setNextNodeId(nextNode.getNodeId());
            }
            pdNodeMapper.updateById(updateNode);
        }
        result.put("status",true);
        result.put("msg","发布成功");
        return result;
    }
}
