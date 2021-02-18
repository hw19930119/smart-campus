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

import com.sunsharing.smartcampus.constant.enums.RoleCodeEnum;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scurd(schemaKey = "VW_SCF_DONE")
@Log4j2
public class AlreadyAuditSchema extends ScurdAopDeclare {
    @Override
    public void beforeSearch(Map reqData) {
        IeduUserVo ieduUserVo=IeduUserController.load(null,null);
        AuditUserVo auditUserVo=ieduUserVo.getAuditUserVo();
        String roleCode = auditUserVo.getRoleCode();
        String roleId = auditUserVo.getRoleId();
        String id = auditUserVo.getId();
        String xzqh = auditUserVo.getXzqh();
        //区教育局跟上行政区划
        String sql = " AND EXISTS (SELECT 1 FROM T_SCF_TODO_DONE t2 WHERE t2.PI_ID = t1.PI_ID AND t2.TYPE = '1' \n" +
                " AND t2.ROLE_ID='%s')";
        reqData.put("_other_role_sql",String.format(sql,roleId));
        if(RoleCodeEnum.专家.eq(roleCode)) {
            String sql1 = " AND EXISTS (SELECT 1 FROM T_SCF_PERSON_TODO_DONE t2 WHERE t2.PI_ID = t1.PI_ID AND t2.TYPE = '1' \n" +
                    " AND t2.USER_ID='%s')";
            reqData.put("_other_role_sql",String.format(sql1,id));
        }else if(RoleCodeEnum.区教育局.eq(roleCode)) {
            reqData.put("XZQH",xzqh);
        }
    }
}
