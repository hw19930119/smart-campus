package com.sunsharing.smartcampus.model.vo.apply;

import lombok.Data;

@Data
public class BaseInfoVo {


    /**
     * 学校名称
     */
    private String schoolName;


    /**
     * 学校类型名称
     */
    private String schoolTypeName;


    /**
     * 学校类型名称
     */
    private String schoolType;


    /**
     * 通信地址
     */
    private String contactAddress;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 学校网址
     */
    private String schoolWebsite;

    /**
     * 学校校长
     */
    private String shcoolXz;

    /**
     * 学校联系电话
     */
    private String shcoolPhone;

    /**
     * 分管领导姓名
     */
    private String ldName;

    /**
     * 分管领导职务
     */
    private String ldZw;

    /**
     * 分管领导电话
     */
    private String ldPhone;

    /**
     * 分管领导邮箱
     */
    private String ldDzyx;

    /**
     * 工作联系人姓名
     */
    private String lxrName;

    /**
     * 工作联系人职务
     */
    private String lxrZw;

    /**
     * 联系人电话
     */
    private String lxrPhone;

    /**
     * 电子邮箱
     */
    private String lxrDzyx;

    /**
     * 申报重点DM_SCHOOL_TYPE
     */
    private String sbzd;

    /**
     * 学生总数
     */
    private String studentCount;

    /**
     * 教职工总数
     */
    private String facultyCount;

    /**
     * 教学班数
     */
    private String classCount;

    /**
     * 高级教师总数
     */
    private String gjjsCount;


}
