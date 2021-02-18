package com.sunsharing.smartcampus.mapper.audit;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.pojo.audit.AuditUser;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.comment.ExpertVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 审核用户 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-02
 */
public interface AuditUserMapper extends BaseMapper<AuditUser> {
    AuditUserVo getAuditUserVoByIdNum(@Param("idNum") String idNum, @Param("userId") String userId);

    List<AuditUser> queryForAuditUserZj(@Param("role") String role);

    List<ExpertVo> queryCommentExperts(@Param("role") String role,
                                       @Param("templateId") String templateId);
}
