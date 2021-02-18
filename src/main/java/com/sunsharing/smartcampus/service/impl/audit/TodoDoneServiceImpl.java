package com.sunsharing.smartcampus.service.impl.audit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sunsharing.smartcampus.constant.enums.HandleStateEnum;
import com.sunsharing.smartcampus.mapper.audit.NodePersonMapper;
import com.sunsharing.smartcampus.mapper.audit.PersonTodoDoneMapper;
import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.sunsharing.smartcampus.model.pojo.audit.TodoDone;
import com.sunsharing.smartcampus.mapper.audit.TodoDoneMapper;
import com.sunsharing.smartcampus.service.audit.PersonTodoDoneService;
import com.sunsharing.smartcampus.service.audit.TodoDoneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 待办已办表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
@Service
@Transactional
public class TodoDoneServiceImpl extends ServiceImpl<TodoDoneMapper, TodoDone> implements TodoDoneService {

    @Autowired
    TodoDoneMapper todoDoneMapper;
    @Autowired
    PersonTodoDoneMapper personTodoDoneMapper;
    @Autowired
    NodePersonMapper nodePersonMapper;
    //-------------------------------------------------
    @Autowired
    PersonTodoDoneService personTodoDoneService;

    //当前节点是否都已办
    @Override
    public boolean currentNodeIsAllDone(String piId, String roleId, PdNode pdNode, String userId){
        boolean currentNodeAllDone=true;
        //如果是会签------------------------------------------------
        if(StringUtils.equals(pdNode.getCounterSign(),"1")){
            currentNodeAllDone=personTodoDoneService.currentNodeIsAllAggree(piId,roleId,pdNode,userId);
        }else {//普通的节点---------------------------------------
            QueryWrapper<TodoDone> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("PI_ID",piId);
            queryWrapper.eq("ROLE_ID",roleId);
            queryWrapper.eq("TYPE",HandleStateEnum.待办.getValue());
            List<TodoDone> list=todoDoneMapper.selectList(queryWrapper);
            if(!list.isEmpty()){
                currentNodeAllDone=false;
            }
        }
        return currentNodeAllDone;
    }


    @Override
    public void createDoneList(String piId, String roleId, PdNode pdNode, String userId,String result) {
        //如果是会签------------------------------------------------
        if(StringUtils.equals(pdNode.getCounterSign(),"1")){
            /*if(AuditEnum.驳回补充.eq(result)){*/
                personTodoDoneService.createOneDone(piId,userId,userId,result);
            /*}else{
                boolean currentNodeAllDone=personTodoDoneService.currentNodeIsAllAggree(piId,roleId,pdNode,userId);
                if(currentNodeAllDone==true){
                    personTodoDoneService.createDoneList(piId,roleId,pdNode,userId);
                }else{
                    personTodoDoneService.createOneDone(piId,userId,userId,result);
                }
            }*/
        }else {//普通的节点---------------------------------------
            //先删除所有待办已办##########
            deleteAllTodoDoneList(piId,null,HandleStateEnum.待办);
            deleteAllTodoDoneList(piId,pdNode.getRoleId(),HandleStateEnum.已办);
            //添加已办############
            insertByType(piId,pdNode.getRoleId(),userId,userId,HandleStateEnum.已办);
        }
    }
    @Override
    public void createTodoList(String piId, PdNode pdNode,String createUserId) {
        //如果是会签------------------------------------------------
        if(StringUtils.equals(pdNode.getCounterSign(),"1")){
            personTodoDoneService.createTodoList(piId,pdNode,createUserId);
        }else{//普通的节点---------------------------------------
            //先删除所有待办##########
            deleteAllTodoDoneList(piId,null,HandleStateEnum.待办);
            deleteAllTodoDoneList(piId,pdNode.getRoleId(),HandleStateEnum.已办);
            //添加待办############
            insertByType(piId,pdNode.getRoleId(),null,createUserId,HandleStateEnum.待办);
        }
    }


    private void insertByType(String piId,String roleId,String doneUserId,String createUserId,HandleStateEnum type){
        TodoDone todoDone = new TodoDone();
        String uuid = com.sunsharing.component.utils.base.StringUtils.generateUUID();
        todoDone.setId(uuid);
        todoDone.setPiId(piId);
        todoDone.setRoleId(roleId);
        todoDone.setUserId(doneUserId);
        todoDone.setType(type.getValue());
        todoDone.setCreateTime(new Date());
        todoDone.setCreatePerson(createUserId);
        todoDoneMapper.insert(todoDone);
    }
    @Override
    public void deleteAllTodoDoneList(String piId, String roleId, HandleStateEnum type){
        UpdateWrapper<TodoDone> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("PI_ID",piId);
        if(StringUtils.equals(type.getValue(),HandleStateEnum.待办.getValue())){//不分节点
        }else{
            updateWrapper.eq("ROLE_ID",roleId);
        }
        updateWrapper.eq("TYPE",type.getValue());
        todoDoneMapper.delete(updateWrapper);
    }
    private void deleteOneTodoDone(String piId,String roleId,String userId,HandleStateEnum type){
        UpdateWrapper<TodoDone> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("PI_ID",piId);
        updateWrapper.eq("ROLE_ID",roleId);
        updateWrapper.eq("USER_ID",userId);
        updateWrapper.eq("TYPE",type.getValue());
        todoDoneMapper.delete(updateWrapper);
    }
}
