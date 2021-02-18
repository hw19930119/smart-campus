package com.sunsharing.smartcampus.service.impl.audit;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sunsharing.smartcampus.constant.enums.AuditEnum;
import com.sunsharing.smartcampus.constant.enums.BizResultEnum;
import com.sunsharing.smartcampus.constant.enums.FinalResultEnum;
import com.sunsharing.smartcampus.mapper.apply.TzhxyBaseInfoMapper;
import com.sunsharing.smartcampus.mapper.audit.ProcInstMapper;
import com.sunsharing.smartcampus.model.pojo.audit.*;
import com.sunsharing.smartcampus.mapper.audit.ReturnBaseMapper;
import com.sunsharing.smartcampus.model.pojo.filed.FieldValue;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.service.audit.AuditRecordService;
import com.sunsharing.smartcampus.service.audit.ProcInstService;
import com.sunsharing.smartcampus.service.audit.ReturnBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.service.audit.SupplyIndexService;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 专家退回的申报 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-11-17
 */
@Service
public class ReturnBaseServiceImpl extends ServiceImpl<ReturnBaseMapper, ReturnBase> implements ReturnBaseService {
    @Autowired
    ReturnBaseMapper returnBaseMapper;
    @Autowired
    TzhxyBaseInfoMapper tzhxyBaseInfoMapper;
    @Autowired
    ProcInstMapper procInstMapper;


    @Autowired
    SupplyIndexService supplyIndexService;

    @Autowired
    ProcInstService procInstService;
    @Autowired
    AuditRecordService auditRecordService;

    @Override
    public JSONObject returnBaseInfoList(JSONObject jsonObject) {
        JSONObject result=ApplyUserServiceImpl.getDefaultReturnJSON();
        String[] declareIds=jsonObject.getString("declareIds").split(",");
        String opinion=jsonObject.getString("opinion");
        IeduUserVo iEduUserVo = IeduUserController.load(null, null);
        AuditUserVo auditUserVo = iEduUserVo.getAuditUserVo();

        List<String> declareIdList= Arrays.asList(declareIds);

        QueryWrapper<ReturnBase> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("DECLARE_ID",declareIdList);
        List<ReturnBase> list=returnBaseMapper.selectList(queryWrapper);

        if(declareIdList.size()!=list.size()){
            result.put("status", false);
            result.put("msg", "所选数据发生变化，请刷新列表");
            return result;
        }
        for(String declareId:declareIdList){
            supplyIndexService.assignSupplyIndex(declareId, null, false);

            UpdateWrapper<ReturnBase> returnBaseUpdateWrapper=new UpdateWrapper();
            returnBaseUpdateWrapper.eq("DECLARE_ID",declareId);
            returnBaseMapper.delete(returnBaseUpdateWrapper);

            procInstService.changeBizState(declareId, BizResultEnum.受理中心退回补充.getValue());


            QueryWrapper<ProcInst> queryWrapper1=new QueryWrapper<>();
            queryWrapper1.eq("BUSSINESS_KEY",declareId);
            ProcInst procInst=procInstMapper.selectOne(queryWrapper1);

            ProcInst procInstNew=new ProcInst();
            procInstNew.setPiId(procInst.getPiId());
            procInstNew.setCurResult(AuditEnum.驳回补充.getValue());
            procInstNew.setFinalResult(FinalResultEnum.驳回补充.getValue());
            procInstMapper.updateById(procInstNew);

            //创建审批记录
            auditRecordService.saveAuditRecord(procInst.getPiId(),procInst.getCurNodeId(),
                                AuditEnum.驳回补充.getValue(),BizResultEnum.受理中心退回补充.getValue(),opinion,auditUserVo);
        }
        return result;
    }
}
