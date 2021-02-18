/*
 * @(#) DeclarationSchema
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-05 10:41:39
 */

package com.sunsharing.smartcampus.scurd.scheme.audit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunsharing.smartcampus.mapper.audit.PdNodeMapper;
import com.sunsharing.smartcampus.mapper.audit.ProcDefMapper;
import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.sunsharing.smartcampus.model.pojo.audit.ProcDef;
import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Scurd(schemaKey = "T_SCF_PDNODE_FA")
@Log4j2
public class PdNodeSchema extends ScurdAopDeclare {
    @Autowired
    ProcDefMapper procDefMapper;
    @Autowired
    PdNodeMapper pdNodeMapper;


    @Override
    public void afterSave(Map reqData, String id) {
        String pdId=(String)reqData.get("PD_ID");
        //定义
        //QueryWrapper queryWrapper=new QueryWrapper();
        //queryWrapper.eq("PD_ID",id);
        //ProcDef procDef=procDefMapper.selectOne(queryWrapper);
        //发布定义节点
        QueryWrapper pdNodeQueryWrapper=new QueryWrapper();
        pdNodeQueryWrapper.eq("PD_ID",pdId);
        pdNodeQueryWrapper.eq("DEL","0");
        pdNodeQueryWrapper.orderByAsc("SEQ_NUM");

        List<PdNode> pdNodeList=pdNodeMapper.selectList(pdNodeQueryWrapper);
        for(int i=0;i<pdNodeList.size();i++){
            PdNode preNode=null;//pdNodeList.get(i-1);
            PdNode currNode=pdNodeList.get(i);
            PdNode nextNode=null;//pdNodeList.get(i+1);
            if(i>0){
                preNode=pdNodeList.get(i-1);
            }
            if(i<pdNodeList.size()-1){
                nextNode=pdNodeList.get(i+1);
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
    }
}
