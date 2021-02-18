/*
 * @(#) TzhxyJcss
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-10 15:16:31
 */

package com.sunsharing.smartcampus.model.vo.apply;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 学校基础实施信息
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-10
 */
@Data
public class JcssVo implements Serializable {


    /**
     * 网络接入方式DM_ZH_SELECT-WLJR
     */
    private String wlJrfs;

    /**
     * 网络宽度（M）
     */
    private String wlKdm;

    /**
     * 网络覆盖范围DM_ZH_SELECT-WLFW
     */
    private String wlFgfw;

    /**
     * 网络中心DM_ZH_SELECT-WLZX
     */
    private String wlZx;

    /**
     * 台式个人电脑（台）
     */
    private String zdTsgrdn;

    /**
     * 终端笔记本电脑（台）
     */
    private String zdBjb;

    /**
     * 平板电脑（台）
     */
    private String zdPbdn;

    /**
     * 电子书包（台）
     */
    private String zdDzsb;

    /**
     * 其它（台）
     */
    private String zdQt;

    /**
     * 教师用（台）
     */
    private String zdJsy;

    /**
     * 学生用（台）
     */
    private String zdXsy;

    /**
     * 多媒体教室（个）
     */
    private String gnDmtjs;

    /**
     * 计算机教室（个）
     */
    private String gnJsjjs;

    /**
     * 数字话实验室（个）
     */
    private String gnSzhsys;

    /**
     * 数字化体验室（个）
     */
    private String gnSzhtys;

    /**
     * 多功能室（个）
     */
    private String gnDgns;

    /**
     * 电子预览室（个）
     */
    private String gnDzyns;

    /**
     * 门户管理DM_ZH_SELECT-MHGL
     */
    private String xtMhgl;

    /**
     * 门户管理DM_ZH_SELECT-SJGL
     */
    private String xtSjgl;

    /**
     * 门户管理DM_ZH_SELECT-KFYJR
     */
    private String xtKfyjr;

    /**
     * 教育管理系统DM_ZH_SELECT-JYGLXT
     */
    private String rjJyglxt;

    /**
     * 教育应用及管理系统DM_ZH_SELECT-JYYYGLXT
     */
    private String rjJyyyjglxt;

    /**
     * 学习管理系统DM_ZH_SELECT-XXGLXT
     */
    private String rjXxglxt;

    /**
     * 师生成长管理系统DM_ZH_SELECT-SSCZGLXT
     */
    private String rjSsczglxt;

    /**
     * 教研平台DM_ZH_SELECT-JYPT
     */
    private String rjJypt;

    /**
     * 家校互通学习平台DM_ZH_SELECT-JYPT
     */
    private String rjJxhtpt;

    /**
     * 数字图上资源DM_ZH_SELECT-SZTS
     */
    private String zySzts;

    /**
     * 选修课程DM_ZH_SELECT-XXKC
     */
    private String zyXxkc;


    private String zyXxkcNum;
    /**
     * 校门课程DM_ZH_SELECT-XMKC
     */
    private String zyXmkc;

    private String zyXmkcNum;

    /**
     * 个性话学习资源DM_ZH_SELECT-GXHXXZY
     */
    private String zyGxhxxzy;


}
