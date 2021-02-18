package com.sunsharing.smartcampus.model.vo.audit;
import com.sunsharing.smartcampus.model.pojo.audit.Pfhz;
import lombok.Data;

@Data
public class PfhzVo extends Pfhz {
    private String roleName;
    private String pfSumStr;
}
