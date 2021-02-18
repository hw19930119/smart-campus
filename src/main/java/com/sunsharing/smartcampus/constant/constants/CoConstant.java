/*
 * @(#) CoConstant
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 17:07:10
 */

package com.sunsharing.smartcampus.constant.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CoConstant {

    /**
     * AOP 拦截路径
     */
    public static final String FILTER_PATH_PREFIX = "/scurd/remote.action";

    public static final String PRIMARY_KEY = "g_id";
    public static final String FOREIGN_KEY = "g_for_id";
    public static final String FILE_UPLOAD = "/smart-campus/upload";

    /**
     * JSON
     */
    public static final String JSON_MESSAGE_FORMAT = "1";
    /**
     * XML
     */
    public static final String XML_MESSAGE_FORMAT = "2";

    /**
     * 单层集合
     */
    public static final String ONLY_RETURN_TYPE = "1";
    /**
     * 多级集合
     */
    public static final String MORE_RETURN_TYPE = "2";


    @Getter
    @AllArgsConstructor
    public enum SAVE_TYPE {
        XZ("1", "正常新增"),
        YXXZ("2", "延续新增");
        private String value;
        private String label;
    }

    @Getter
    @AllArgsConstructor
    public enum SF {
        FALSE("0", "否"),
        TRUE("1", "是");
        private String value;
        private String label;
    }


    @Getter
    @AllArgsConstructor
    public enum VALID_STATE {
        DXZ("1", "兑现中"),
        JZDX("2", "截止兑现"),
        DDX("3", "待生效");
        private String value;
        private String label;
    }

    @Getter
    @AllArgsConstructor
    public enum DATA_TYPE {
        C2C("1", "企业-企业 C2C"),
        C2P("2", "企业-个人 C2P"),
        P2P("3", "个人-个人 P2P");
        private String value;
        private String label;
    }


    @Getter
    @AllArgsConstructor
    public enum APPLICANT_BODY {
        NONE("0", "无"),
        QY("1", "企业"),
        GR("2", "个人");
        private String value;
        private String label;
    }
}
