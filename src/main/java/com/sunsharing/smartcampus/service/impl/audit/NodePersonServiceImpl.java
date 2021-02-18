package com.sunsharing.smartcampus.service.impl.audit;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sunsharing.smartcampus.constant.enums.AuditEnum;
import com.sunsharing.smartcampus.model.pojo.audit.NodePerson;
import com.sunsharing.smartcampus.mapper.audit.NodePersonMapper;
import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.sunsharing.smartcampus.service.audit.NodePersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 节点候选人 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-13
 */
@Service
@Transactional
public class NodePersonServiceImpl extends ServiceImpl<NodePersonMapper, NodePerson> implements NodePersonService {
    @Autowired
    NodePersonMapper nodePersonMapper;

    @Override
    public void createNodePersonList(String piId,PdNode nextNode, JSONArray nextNodeAuditUserList,String userId) {
        if(nextNode==null){
            return;
        }
        //如果之前有数据，则不修改,受理中心第二次提交初审，不能修改专家，只需重置审批转态
        QueryWrapper<NodePerson> nodePersonQueryWrapper = new QueryWrapper<>();
        nodePersonQueryWrapper.eq("PI_ID",piId );
        nodePersonQueryWrapper.eq("NODE_ID",nextNode.getNodeId());
        List<NodePerson> exixtNodePersonList = nodePersonMapper.selectList(nodePersonQueryWrapper);
        if(!exixtNodePersonList.isEmpty()){
            NodePerson nodePersonNew=new NodePerson();
            nodePersonNew.setState("0");
            nodePersonMapper.update(nodePersonNew,nodePersonQueryWrapper);
            return;
        }

        //删除之前的数据
        UpdateWrapper<NodePerson> updateWrapper=new UpdateWrapper();
        updateWrapper.eq("PI_ID",piId);
        updateWrapper.eq("NODE_ID",nextNode.getNodeId());
        nodePersonMapper.delete(updateWrapper);
        //添加数据
        for(Object nextNodeAuditUser:nextNodeAuditUserList){
            String nextNodeAuditUserId=nextNodeAuditUser.toString();
            NodePerson nodePerson=new NodePerson();
            String uuid=com.sunsharing.component.utils.base.StringUtils.generateUUID();
            nodePerson.setId(uuid);
            nodePerson.setPiId(piId);
            nodePerson.setNodeId(nextNode.getNodeId());
            nodePerson.setUserId(nextNodeAuditUserId);
            nodePerson.setState(AuditEnum.待审核.getValue());
            nodePerson.setCreateTime(new Date());
            nodePerson.setCreatePerson(userId);
            nodePersonMapper.insert(nodePerson);
        }
    }

    @Override
    public void updateStateWhenCommitAudit(String piId,PdNode pdNode,String userId,String state){
        UpdateWrapper<NodePerson> updateWrapper=new UpdateWrapper();
        updateWrapper.eq("PI_ID",piId);
        updateWrapper.eq("NODE_ID",pdNode.getNodeId());
        updateWrapper.eq("USER_ID",userId);
        NodePerson nodePersonodePerson=new NodePerson();
        nodePersonodePerson.setState(state);
        nodePersonMapper.update(nodePersonodePerson,updateWrapper);
    }
}
