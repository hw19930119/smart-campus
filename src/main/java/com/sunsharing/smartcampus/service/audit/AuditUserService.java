package com.sunsharing.smartcampus.service.audit;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.audit.AuditUser;
import com.sunsharing.smartcampus.model.vo.comment.ExpertVo;

import java.util.List;

/**
 * <p>
 * 审核用户 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface AuditUserService extends IService<AuditUser> {

    /**
     * 查询系统内的所有专家
     * @return
     */
    List<AuditUser> queryForAuditUserZj();

    /**
     * 查询点评专家包含不可分配标识 针对模板
     * @param templateId -- 模板ID
     * @return
     */
    List<ExpertVo> queryCommentExperts(String templateId);
}
