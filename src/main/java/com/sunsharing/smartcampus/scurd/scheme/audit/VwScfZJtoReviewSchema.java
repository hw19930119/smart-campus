/*
 * @(#) VwScfZJtoReviewSchema
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-12-02 17:17:58
 */

package com.sunsharing.smartcampus.scurd.scheme.audit;

import com.sunsharing.common.utils.StringUtils;
import com.sunsharing.smartcampus.constant.enums.RoleCodeEnum;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

import lombok.extern.log4j.Log4j2;

@Component
@Scurd(schemaKey = "VW_SCF_ZJTOREVIEW_FA")
@Log4j2
public class VwScfZJtoReviewSchema extends ScurdAopDeclare {

    @Override
    public void beforeSearch(Map reqData) {
        IeduUserVo ieduUserVo = IeduUserController.load(null, null);
        AuditUserVo auditUserVo = ieduUserVo.getAuditUserVo();
        String roleCode = auditUserVo.getRoleCode();
        //区教育局跟上行政区划
        if (RoleCodeEnum.区教育局.eq(roleCode)) {
            reqData.put("XZQH", auditUserVo.getXzqh());
        }
        //首页跳转扩展
        String nodeType = MapUtils.getString(reqData, "nodeType", "");
        if (StringUtils.isBlank(nodeType)) {
            return;
        }
        String sql = "AND EXISTS(SELECT 1 FROM T_SCF_PROCINST b INNER JOIN T_SCF_AUDIT_RECORD c ON c.PI_ID = b.PI_ID " +
            "INNER JOIN T_SC_ROLE d ON d.ROLE_ID = c.ROLE_ID AND d.`CODE`='%s' WHERE  b.BUSSINESS_KEY = t4.DECLARE_ID) AND t4.PC_STATE = '1'";
        if (RoleCodeEnum.区教育局.eq(nodeType)) {
            reqData.put("_other_qjyj_sql", String.format(sql, nodeType));
        } else if (RoleCodeEnum.专家.eq(nodeType)) {
            reqData.put("_other_zj_sql", String.format(sql, nodeType));
        } else if (RoleCodeEnum.受理中心.eq(nodeType)) {
            sql = "AND EXISTS(SELECT 1 FROM T_SCF_PROCINST b INNER JOIN T_SCF_AUDIT_RECORD c ON c.PI_ID = b.PI_ID " +
                "INNER JOIN T_SC_ROLE d ON d.ROLE_ID = c.ROLE_ID AND d.`CODE`='slzx' INNER JOIN T_SCF_PDNODE e ON e.NODE_ID=c.NODE_ID AND e.NODE_IDENTIFIER = '4' WHERE  b.BUSSINESS_KEY = t4.DECLARE_ID) AND t4.PC_STATE = '1'";
            reqData.put("_other_slzx_sql", sql);
        } else if (RoleCodeEnum.市教育局.eq(nodeType)) {
            reqData.put("_other_sjyj_sql", String.format(sql, nodeType));
        }
    }
}
