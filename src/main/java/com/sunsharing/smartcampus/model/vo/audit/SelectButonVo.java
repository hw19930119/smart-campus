package com.sunsharing.smartcampus.model.vo.audit;

import com.sunsharing.smartcampus.model.pojo.audit.AuditUser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SelectButonVo {
    private List<AuditOption> options=new ArrayList<>();
    private String scoreButton="0";//评分按钮 0没有 1详情 2评分
    private Boolean hasPerson=false;//配置
    private String opinion="";
    private Boolean disableNextNodeAuditUserSelect=false;
    private List<AuditUserVo> nextNodeAuditUserList=new ArrayList<>();

}
