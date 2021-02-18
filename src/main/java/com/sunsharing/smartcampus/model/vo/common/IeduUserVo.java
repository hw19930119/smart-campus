package com.sunsharing.smartcampus.model.vo.common;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;
import com.sunsharing.smartcampus.model.vo.audit.AuditUserVo;
import lombok.Data;

@Data
public class IeduUserVo {
    ApplyUserVo applyUserVo;
    AuditUserVo auditUserVo;
    String type;//
    JSONObject iEduJson;//i厦门返回的所有信息
}
