package com.sunsharing.smartcampus.model.vo.audit;

import com.sunsharing.smartcampus.model.pojo.audit.PdNode;
import lombok.Data;

@Data
public class PdNodeVo extends PdNode {
    private String piId;
    private String pdName;
    private String bussinessKey;
    private String have;
}
