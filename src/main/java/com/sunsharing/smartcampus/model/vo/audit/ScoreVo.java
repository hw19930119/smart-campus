package com.sunsharing.smartcampus.model.vo.audit;

import com.sunsharing.smartcampus.model.pojo.audit.Score;
import lombok.Data;

@Data
public class ScoreVo extends Score {
    private String giveBackLabel;
    private String roleName;
    private String auditPersonName;

    private String indexName;
    private Double avScore;
    private Double sumScore;
    private Integer countScore;
}
