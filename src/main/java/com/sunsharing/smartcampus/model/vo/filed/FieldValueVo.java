package com.sunsharing.smartcampus.model.vo.filed;

import com.sunsharing.smartcampus.model.pojo.filed.FieldValue;
import lombok.Data;

@Data
public class FieldValueVo extends FieldValue {
    private String otherScore;
    private String giveBack;
}
