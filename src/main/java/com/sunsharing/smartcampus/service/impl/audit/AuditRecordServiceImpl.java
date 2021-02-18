package com.sunsharing.smartcampus.service.impl.audit;

import com.sunsharing.smartcampus.model.pojo.audit.AuditRecord;
import com.sunsharing.smartcampus.mapper.audit.AuditRecordMapper;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.service.audit.AuditRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 流程实例节点 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
@Service
@Transactional
public class AuditRecordServiceImpl extends ServiceImpl<AuditRecordMapper, AuditRecord> implements AuditRecordService {
    @Autowired
    AuditRecordMapper auditRecordMapper;
    @Override
    public void saveAuditRecord(String piId, String curNodeId, String result, String bizResult, String opinion, AuditUserVo auditUserVo) {
        AuditRecord auditRecord = new AuditRecord();
        auditRecord.setAuditTime(new Date());
        auditRecord.setAuditUserId(auditUserVo.getId());
        auditRecord.setNodeId(curNodeId);
        auditRecord.setOpinon(opinion);
        auditRecord.setPiId(piId);
        auditRecord.setResult(result);
        auditRecord.setBizResult(bizResult);
        auditRecord.setRoleId(auditUserVo.getRoleId());
        //保存审批记录
        auditRecordMapper.insert(auditRecord);
    }
}
