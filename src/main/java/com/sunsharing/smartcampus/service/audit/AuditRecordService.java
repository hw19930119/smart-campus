package com.sunsharing.smartcampus.service.audit;

import com.sunsharing.smartcampus.model.pojo.audit.AuditRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;

/**
 * <p>
 * 流程实例节点 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface AuditRecordService extends IService<AuditRecord> {
    void saveAuditRecord(String piId, String curNodeId, String result, String bizResult, String opinion, AuditUserVo auditUserVo);
}
