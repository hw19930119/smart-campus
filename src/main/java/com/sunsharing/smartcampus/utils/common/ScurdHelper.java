/*
 * @(#) ScurdHelper
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:59:43
 */

package com.sunsharing.smartcampus.utils.common;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import com.sunsharing.share.common.base.DateUtil;
import com.sunsharing.share.common.base.TypeConvUtil;
import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.smartcampus.model.vo.common.RangeMonthVo;
import com.sunsharing.smartcampus.model.vo.common.RangeTimeVo;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

/**
 * 在线表单扩展工具类
 */
@Log4j2
@Component
public class ScurdHelper {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String EXCEL_IDENTIFY = "batchNo";
    private static final String EXCEL_EXPORT_IDENTIFY = "export_status_token";

    public static boolean noneAnyParam(Map<String, Object> reqData) {///\\\\
        Pattern pattern = Pattern.compile("^[0-9A-Z_~$%^&*]*$");
        AtomicBoolean hasParam = new AtomicBoolean(true);
        reqData.forEach((key, val) -> {
            if (pattern.matcher(key).matches() && !Strings.isNullOrEmpty(TypeConvUtil.toString(val))) {
                hasParam.set(false);
            }
        });
        return hasParam.get();
    }

    public static List getList4AfterSearch(Map result) {
        Map data = MapUtils.getObject(result, "data", Collections.EMPTY_MAP);
        List list = MapUtils.getObject(data, "list", Collections.EMPTY_LIST);
        return list;
    }

    public static Map putList4AfterSearch(Map result, List newList) {
        Map data = MapUtils.getObject(result, "data", Collections.EMPTY_MAP);
        data.put("list", newList);
        return data;
    }

    public static boolean isExcelSearch(Map reqData) {
        return !Strings.isNullOrEmpty(getExcelIdentify(reqData));
    }

    public static boolean isExcelExport(Map reqData) {
        return !Strings.isNullOrEmpty(getExcelExportIdentify(reqData));
    }

    public static String getExcelIdentify(Map reqData) {
        return MapUtils.getString(reqData, EXCEL_IDENTIFY, "");
    }

    public static String getExcelExportIdentify(Map reqData) {
        return MapUtils.getString(reqData, EXCEL_EXPORT_IDENTIFY, "");
    }

    public static String getLabelValue(Map result, String columnKey) {
        Map map = getColumnMap(result, columnKey);
        return MapUtils.getString(map, "label");
    }

    public static String getDetailLabelValue(Map result, String columnKey) {
        Map map = getColumnMap(result, columnKey);
        return MapUtils.getString(map, "detail_label");
    }

    public static void updateLabelValue(Map result, String columnKey, String value) {
        Map map = getColumnMap(result, columnKey);
        map.put("label", value);
    }

    public static void updateDetailLabelValue(Map result, String columnKey, String value) {
        Map map = getColumnMap(result, columnKey);
        map.put("detail_label", value);
    }

    public static void updateDetailValue(Map result, String columnKey, String value) {
        Map map = getColumnMap(result, columnKey);
        map.put("val", value);
    }

    /**
     * 解析在线表单的时间范围参数并格式化
     *
     * @param date 直接访问字符串（是已逗号隔开的）
     * @return 开始结束时间的对象
     */
    public static RangeTimeVo getRangeTime(String date) {
        if (Strings.isNullOrEmpty(date)) {
            return new RangeTimeVo();
        }
        List<String> times = Splitter.on(",").splitToList(date);
        String startTime = times.get(0);
        String endTime = times.get(1);
        return new RangeTimeVo()
            .setStartTime(Strings.isNullOrEmpty(startTime) ? null : DateUtil.toLocalDateTime(startTime))
            .setEndTime(Strings.isNullOrEmpty(endTime) ? null : DateUtil.toLocalDateTime(endTime));
    }

    public static RangeMonthVo getRangeMonth(String date) {
        if (Strings.isNullOrEmpty(date)) {
            return new RangeMonthVo();
        }
        LocalDateTime now = DateUtil.toLocalDateTime(date);
        LocalDateTime endTime = now.minusMonths(1);
        LocalDateTime startTime = endTime.minusMonths(1);
        return new RangeMonthVo()
            .setStartMonth(startTime.format(DateTimeFormatter.ofPattern("yyyyMM")))
            .setEndMonth(endTime.format(DateTimeFormatter.ofPattern("yyyyMM")))
            .setEndDay(endTime.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    public static Map getColumnMap(Map result, String columnKey) {
        List<Map<String, Object>> columns = MapUtils.getObject(result, "columns", Collections.EMPTY_LIST);
        List<Map<String, Object>> column = columns.stream()
            .filter(map -> Objects.equals(map.get("column"), columnKey))
            .collect(Collectors.toList());
        if (column.isEmpty()) {
            return Maps.newHashMap();
        }
        return column.get(0);
    }

    /**
     * 20171212  或者  20181223,20191212  进行格式化
     *
     * @param date      需要格式化的字符串
     * @param separator 返回回去的分隔符，默认,
     * @param format    格式化格式 默认 yyy-MM-dd
     * @return 转换后的字符串
     */
    public static String tranMultiDateFormat(String date, String separator, String format) {
        if (Strings.isNullOrEmpty(date)) {
            return "";
        }
        List<String> dateList = Splitter.on(",").splitToList(date);
        List<String> result = new ArrayList<>();
        String finalSeparator = Strings.isNullOrEmpty(separator) ? "," : separator;
        String finalFormat = Strings.isNullOrEmpty(format) ? DateUtil.DATE_FORMAT_STR : format;
        dateList.forEach(s -> {
            try {
                String value = DateUtil.toString(DateUtil.toDate(s), finalFormat);
                result.add(value);
            } catch (Exception e) {
                log.error("转换时间格式失败:{}", e);
                result.add(s);
            }
        });

        return Joiner.on(finalSeparator).join(result);
    }

    public static String getScurdImportId(Map reqData) {
        return MapUtils.getString(reqData, "importId");
    }

    /**
     * 获取导入方案名
     *
     * @param importId 导入方案ID
     */
    public String getImportSchemaName(String importId) {
        String sql = "SELECT SCHEMA_NAME FROM T_SCURD_IMPORT WHERE ID=?";
        return jdbcTemplate.queryForObject(sql, String.class, importId);
    }

    /**
     * 设置今年与去年同期月份日期
     * @param date date
     * @return
     */
    public static RangeMonthVo getTheSomeTermMonth(String date) {
        if (Strings.isNullOrEmpty(date)) {
            return new RangeMonthVo();
        }
        LocalDateTime now = DateUtil.toLocalDateTime(date);
        LocalDateTime endTime = now.minusMonths(1);
        LocalDateTime startTime = endTime.minusMonths(12);
        return new RangeMonthVo()
            .setStartMonth(startTime.format(DateTimeFormatter.ofPattern("yyyyMM")))
            .setEndMonth(endTime.format(DateTimeFormatter.ofPattern("yyyyMM")))
            .setEndDay(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    /**
     * 表码 code 转 id
     * @param list list
     * @return
     * [{
     *     "id": "",
     *     "label": ""
     * }]
     */
    public static List transCodeToId(List<Map<String, Object>> list) {
        return list.stream()
            .map(map -> MapUtil.ofHashMap(
                "id", org.apache.commons.collections.MapUtils.getString(map, "code"),
                "label", org.apache.commons.collections.MapUtils.getString(map, "label"))
            ).collect(Collectors.toList());
    }

    /**
     * 获取属性值
     * @param result result
     * @param columnKey columnKey
     * @return
     */
    public static String getColumnVal(Map result, String columnKey) {
        Map<String, Object> columnMap = getColumnMap(result, columnKey);
        String val = MapUtils.getString(columnMap, "val");
        return val;
    }

}
