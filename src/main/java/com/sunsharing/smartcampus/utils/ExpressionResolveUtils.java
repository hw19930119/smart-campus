/*
 * @(#) ExpressionResolveUtils
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2019-09-10 17:24:59
 */

package com.sunsharing.smartcampus.utils;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ExpressionResolveUtils {

    public static final String PREFIX = "#{";

    public static final String SUFFIX = "}";

    public static boolean analyzeExpression(String expression, Map<String, Object> map) {
        boolean returnBoolean = false;
        try {
            if (StringUtils.isEmpty(expression)) {
                return returnBoolean;
            }
            String expre = expression.replace(PREFIX, "").replace(SUFFIX, "");
            Expression aviatorExpre = AviatorEvaluator.compile(expre);
            returnBoolean = (boolean) aviatorExpre.execute(map);
            log.debug("指标表达式：({}),参数：{},执行结果：{}", expre, map, returnBoolean);
            return returnBoolean;
        } catch (Exception e) {
            log.error(expression + "表达式解析失败,不跳过该节点", e);
            returnBoolean = false;
        }
        return returnBoolean;
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("cityBelong", "1");
    }
}
