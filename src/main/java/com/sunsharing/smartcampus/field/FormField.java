/*
 * @(#) FormField
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:33
 */

package com.sunsharing.smartcampus.field;

import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: cxy
 * @date: 2020/3/3
 * @description:
 */
@Data
@Accessors(chain = true)
public class FormField {

    private String score;
    private String title;
    private String type;
    private String format;
    private String name;
    private String key;
    private String parentKey;
    private String pattern;
    private Map<String, Object> message;
    /**
     * 区分是‘必选’还是‘扩展’: 1必选；2拓展
     */
    private String tabKey;
    /**
     * 异常提示信息！
     */
    private String textTip;
    private String url;
    private String[] enums;
    private String[] enumNames;
    private Map<String, Object> componentProps;
    private Map<String, String> uploadProps;
}
