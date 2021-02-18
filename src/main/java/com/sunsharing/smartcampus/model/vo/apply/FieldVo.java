package com.sunsharing.smartcampus.model.vo.apply;

import lombok.Data;

import java.util.List;

@Data
public class FieldVo<T> {
    //字段名
    private String columnName;
    //字段值
    private T columnValue;
    //发否存在表码0不存在,1存在
    private int isDm;
    //表码列表
    private List dmList;
    public FieldVo(){
        this.isDm = 0;
    }
}
