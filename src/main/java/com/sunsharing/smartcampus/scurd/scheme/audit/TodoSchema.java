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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunsharing.smartcampus.mapper.audit.PdNodeMapper;
import com.sunsharing.smartcampus.mapper.audit.ProcDefMapper;
import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;
import com.sunsharing.smartcampus.web.common.IeduUserController;
import com.sunsharing.zeus.scurd.configure.service.ext.Scurd;
import com.sunsharing.zeus.scurd.configure.service.ext.enhance.ScurdAopDeclare;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Scurd(schemaKey = "VW_SCF_TODO_FA")
@Log4j2
public class TodoSchema extends ScurdAopDeclare {
    @Override
    public void beforeSearch(Map reqData) {
        IeduUserVo ieduUserVo=IeduUserController.load(null,null);
        AuditUserVo auditUserVo=ieduUserVo.getAuditUserVo();
        if(StringUtils.equals(auditUserVo.getFilterData(),"1")){
            reqData.put("XZQH",auditUserVo.getXzqh());
        }

    }
}
