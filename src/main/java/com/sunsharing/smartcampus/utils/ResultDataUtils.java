/*
 * @(#) ResultDataUtils
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-12 10:30:22
 */

package com.sunsharing.smartcampus.utils;

import com.sunsharing.share.common.base.exception.ShareBusinessException;
import com.sunsharing.share.common.collection.MapUtil;

import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultDataUtils {

    /**
     * 包装返回参数
     * @param result 主要返回值
     * @param message 返回提示信息
     * @param otherParams 可变参数
     * @return 包装结果
     */
    public static <T> Map packParams(T result, String message, Object... otherParams) {
        if (otherParams.length > 0 && otherParams.length % 2 != 0) {
            throw new ShareBusinessException(1500, "额外参数不正确");
        }
        Map<String, Object> basicMap = MapUtil.ofHashMap("result", result, "message", message);
        for (int i = 0; i < otherParams.length - 1; i += 2) {
            basicMap.put((String) otherParams[i], otherParams[i + 1]);
        }
        return basicMap;
    }

    /**
     * 获取某字符串地n次出现的位置
     * @param str 原字符串
     * @param target 位置字符串
     * @param times 第几次出现
     * @return 包装结果
     */
    public static int getCharacterPosition(String str, String target, int times) {
        //这里是获取target符号的位置
        Matcher slashMatcher = Pattern.compile(target).matcher(str);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            //当符号第三次出现的位置
            if (mIdx == times) {
                break;
            }
        }
        return slashMatcher.start();
    }

    public static String randomStr(int n) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //获取长度为6的字符串
        for (int i = 0; i < n; i++) {
            //获取范围在3之内的索引值
            int number = random.nextInt(3);
            int result = 0;
            switch (number) {
                case 0:
                    //Math.random()*25+65成成65-90的int型的整型,强转小数只取整数部分
                    result = (int) (Math.random() * 25 + 65);  //对应A-Z 参考ASCII编码表
                    //将整型强转为char类型
                    sb.append((char) result);
                    break;
                case 1:
                    result = (int) (Math.random() * 25 + 97);   //对应a-z
                    sb.append((char) result);
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }
}
