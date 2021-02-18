/*
 * @(#) TzhxyJcss
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-10 15:16:31
 */

package com.sunsharing.smartcampus.model.pojo.apply;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学校基础实施信息
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-10
 */
@Data
@Accessors(chain = true)
@TableName("T_ZHXY_JCSS")
public class TzhxyJcss implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("JCSS_ID")
    private String jcssId;

    /**
     * 申报ID
     */
    @TableField("DECLARE_ID")
    private String declareId;

    /**
     * 网络接入方式DM_ZH_SELECT-WLJR
     */
    @TableField("WL_JRFS")
    private String wlJrfs;

    /**
     * 网络宽度（M）
     */
    @TableField("WL_KDM")
    private String wlKdm;

    /**
     * 网络覆盖范围DM_ZH_SELECT-WLFW
     */
    @TableField("WL_FGFW")
    private String wlFgfw;

    /**
     * 网络中心DM_ZH_SELECT-WLZX
     */
    @TableField("WL_ZX")
    private String wlZx;

    /**
     * 台式个人电脑（台）
     */
    @TableField("ZD_TSGRDN")
    private String zdTsgrdn = null;

    /**
     * 终端笔记本电脑（台）
     */
    @TableField("ZD_BJB")
    private String zdBjb = null;

    /**
     * 平板电脑（台）
     */
    @TableField("ZD_PBDN")
    private String zdPbdn = null;

    /**
     * 电子书包（台）
     */
    @TableField("ZD_DZSB")
    private String zdDzsb = null;

    /**
     * 其它（台）
     */
    @TableField("ZD_QT")
    private String zdQt = null;

    /**
     * 教师用（台）
     */
    @TableField("ZD_JSY")
    private String zdJsy = null;

    /**
     * 学生用（台）
     */
    @TableField("ZD_XSY")
    private String zdXsy = null;

    /**
     * 多媒体教室（个）
     */
    @TableField("GN_DMTJS")
    private String gnDmtjs = null;

    /**
     * 计算机教室（个）
     */
    @TableField("GN_JSJJS")
    private String gnJsjjs = null;

    /**
     * 数字话实验室（个）
     */
    @TableField("GN_SZHSYS")
    private String gnSzhsys = null;

    /**
     * 数字化体验室（个）
     */
    @TableField("GN_SZHTYS")
    private String gnSzhtys = null;

    /**
     * 多功能室（个）
     */
    @TableField("GN_DGNS")
    private String gnDgns = null;

    /**
     * 电子预览室（个）
     */
    @TableField("GN_DZYNS")
    private String gnDzyns = null;

    /**
     * 门户管理DM_ZH_SELECT-MHGL
     */
    @TableField("XT_MHGL")
    private String xtMhgl;

    /**
     * 门户管理DM_ZH_SELECT-SJGL
     */
    @TableField("XT_SJGL")
    private String xtSjgl;

    /**
     * 门户管理DM_ZH_SELECT-KFYJR
     */
    @TableField("XT_KFYJR")
    private String xtKfyjr;

    /**
     * 教育管理系统DM_ZH_SELECT-JYGLXT
     */
    @TableField("RJ_JYGLXT")
    private String rjJyglxt;

    /**
     * 教育应用及管理系统DM_ZH_SELECT-JYYYGLXT
     */
    @TableField("RJ_JYYYJGLXT")
    private String rjJyyyjglxt;

    /**
     * 学习管理系统DM_ZH_SELECT-XXGLXT
     */
    @TableField("RJ_XXGLXT")
    private String rjXxglxt;

    /**
     * 师生成长管理系统DM_ZH_SELECT-SSCZGLXT
     */
    @TableField("RJ_SSCZGLXT")
    private String rjSsczglxt;

    /**
     * 教研平台DM_ZH_SELECT-JYPT
     */
    @TableField("RJ_JYPT")
    private String rjJypt;

    /**
     * 家校互通学习平台DM_ZH_SELECT-JYPT
     */
    @TableField("RJ_JXHTPT")
    private String rjJxhtpt;

    /**
     * 数字图上资源DM_ZH_SELECT-SZTS
     */
    @TableField("ZY_SZTS")
    private String zySzts;

    /**
     * 选修课程DM_ZH_SELECT-XXKC
     */
    @TableField("ZY_XXKC")
    private String zyXxkc;


    @TableField("ZY_XXKC_NUM")
    private String zyXxkcNum = null;
    /**
     * 校门课程DM_ZH_SELECT-XMKC
     */
    @TableField("ZY_XMKC")
    private String zyXmkc;

    @TableField("ZY_XMKC_NUM")
    private String zyXmkcNum = null;

    /**
     * 个性话学习资源DM_ZH_SELECT-GXHXXZY
     */
    @TableField("ZY_GXHXXZY")
    private String zyGxhxxzy;

    /**
     * 提交时间
     */
    @TableField("COMMIT_TIME")
    private String commitTime;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("CREATE_PERSON")
    private String createPerson;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField("UPDATE_PERSON")
    private String updatePerson;

    /**
     * 状态 关联DM_BIZ_RESULT
     */
    @TableField("STATE")
    private String state;

    /**
     * 上一次状态
     */
    @TableField("LAST_STATE")
    private String lastState;

    /**
     * 状态 1 正在使用 2 归档
     */
    @TableField("PC_STATE")
    private String pcState;

    /**
     * 是否有效 0 有效 1 无效
     */
    @TableField("DEL")
    private String del;


}
