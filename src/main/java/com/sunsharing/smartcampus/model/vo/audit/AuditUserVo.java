package com.sunsharing.smartcampus.model.vo.audit;

import com.sunsharing.smartcampus.model.pojo.audit.AuditUser;
import lombok.Data;

@Data
public class AuditUserVo extends AuditUser {
    String roleCode;
    String roleName;
    String filterData;
    Boolean selected;
}
