package com.sunsharing.smartcampus.service.impl.audit;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.constant.enums.RoleCodeEnum;
import com.sunsharing.smartcampus.mapper.audit.AuditUserMapper;
import com.sunsharing.smartcampus.model.pojo.audit.AuditUser;
import com.sunsharing.smartcampus.model.vo.comment.ExpertVo;
import com.sunsharing.smartcampus.service.audit.AuditUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 审核用户 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
@Service
@Transactional
public class AuditUserServiceImpl extends ServiceImpl<AuditUserMapper, AuditUser> implements AuditUserService {

    @Autowired
    AuditUserMapper auditUserMapper;

    @Override
    public List<AuditUser> queryForAuditUserZj() {
        return auditUserMapper.queryForAuditUserZj(RoleCodeEnum.专家.getValue());
    }

    @Override
    public List<ExpertVo> queryCommentExperts(String templateId) {
        return auditUserMapper.queryCommentExperts(RoleCodeEnum.专家.getValue(), templateId);
    }
}
