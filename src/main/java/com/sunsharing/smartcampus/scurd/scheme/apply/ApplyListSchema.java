/*
 * @(#) DeclarationSchema
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-05 10:41:39
 */

package com.sunsharing.smartcampus.scurd.scheme.apply;

import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scurd(schemaKey = "VW_APPLY_LIST_FA")
@Log4j2
public class ApplyListSchema extends ScurdAopDeclare {
    @Override
    public void beforeSearch(Map reqData) {
        IeduUserVo ieduUserVo= IeduUserController.load(null,null);
        ApplyUserVo applyUserVo=ieduUserVo.getApplyUserVo();
        String unitCode=applyUserVo.getUnitCode();
        if(unitCode==null){
            unitCode="";
        }
        reqData.put("UNIT_CODE",unitCode);
    }
}
