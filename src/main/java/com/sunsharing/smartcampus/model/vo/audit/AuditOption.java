package com.sunsharing.smartcampus.model.vo.audit;

import lombok.Data;

@Data
public class AuditOption {
    private String value;//配置
    private String label;//配置
    private Boolean hasStarLevel=false;//配置
    private Boolean selected=false;//有些有配置
    private Boolean disabled=false;
    private String disabledMsg="";
    private Boolean hasScore=false;
}
