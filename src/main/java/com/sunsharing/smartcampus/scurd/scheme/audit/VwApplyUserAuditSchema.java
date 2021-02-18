/*
 * @(#) VwApplyUserAuditSchema
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-10-23 11:58:14
 */

package com.sunsharing.smartcampus.scurd.scheme.audit;

import com.sunsharing.smartcampus.constant.enums.RoleCodeEnum;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;

import org.springframework.stereotype.Component;

import java.util.Map;

import lombok.extern.log4j.Log4j2;

@Component
@Scurd(schemaKey = "^VW_APPLYUSER_(AUDIT|AUDITED)_FA$")
@Log4j2
public class VwApplyUserAuditSchema extends ScurdAopDeclare {
    @Override
    public void beforeSearch(Map reqData) {
        IeduUserVo ieduUserVo = IeduUserController.load(null, null);
        AuditUserVo auditUserVo = ieduUserVo.getAuditUserVo();
        String roleCode = auditUserVo.getRoleCode();
        //除管理员外都跟上行政区划
        if (!RoleCodeEnum.管理员.eq(roleCode)) {
            reqData.put("XZQH", auditUserVo.getXzqh());
        }
    }

}
