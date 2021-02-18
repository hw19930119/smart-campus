/*
 * @(#) EasyExcelUtils
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-18 10:56:06
 */

package com.sunsharing.smartcampus.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by wuxw on 2020/4/4.
 */
@Slf4j
public class EasyExcelUtils {

    // private static  String resourceFile = Resources.getResource("PERSON_NS.xlsx").getPath();
    //获取临时文件路径
    // private static String resourceFile = Resources.getResource("PERSON_NS.xlsx").getPath();

    private EasyExcelUtils() {
    }


    @SuppressWarnings("rawtypes")
    public static void webWriteExcel(HttpServletResponse response, List objects, Class clazz, String fileName) throws IOException {
        String sheetName = fileName;
        webWriteExcel(response, objects, clazz, fileName, sheetName);
    }

    @SuppressWarnings("rawtypes")
    public static String localWriteExcel(HttpServletResponse response, List objects, Class clazz, String fileName) throws IOException {
        String sheetName = fileName;
        return localWriteExcel(response, objects, clazz, fileName, sheetName);
    }

    public static void webDynamicWriteExcel(HttpServletResponse response, List<List<Object>> dynamicData,
                                            List<List<String>> dynamicHead, String fileName) throws IOException {
        String sheetName = fileName;
        webDynamicWriteExcel(response, dynamicData, dynamicHead, fileName, sheetName);
    }


    @SuppressWarnings("rawtypes")
    public static void webWriteExcel(HttpServletResponse response, List objects, Class clazz, String fileName, String sheetName)
        throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setBold(true);
        //居中
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //加粗
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 背景设置为白
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
            new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            EasyExcelFactory.write(outputStream, clazz).registerWriteHandler(horizontalCellStyleStrategy).sheet(sheetName).doWrite(objects);
        } catch (Exception e) {
            // log.info("EXCEL输出异常", e.getMessage());
            log.info("EXCEL输出异常", e);
        } finally {
            outputStream.close();
        }

    }

    @SuppressWarnings("rawtypes")
    public static String localWriteExcel(HttpServletResponse response, List objects, Class clazz, String fileName, String sheetName)
        throws IOException {
        String fileTemp = System.getProperty("user.dir") + "/static/temp/" + new Date().getTime() + "_" + fileName + ".xlsx";
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为白
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
            new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        // 文件输出位置
        OutputStream outputStream = new FileOutputStream(fileTemp);
        try {
            EasyExcelFactory.write(outputStream, clazz).registerWriteHandler(horizontalCellStyleStrategy).sheet(sheetName).doWrite(objects);
        } catch (Exception e) {
            log.info("EXCEL输出异常:", e.getMessage());
        } finally {
            outputStream.close();
        }
        return fileTemp;
    }


    @SuppressWarnings("rawtypes")
    public static void webDynamicWriteExcel(HttpServletResponse response, List<List<Object>> dynamicData, List<List<String>> dynamicHead,
                                            String fileName, String sheetName) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setBold(true);
        headWriteFont.setFontHeightInPoints((short) 12);
        //居中
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //加粗
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 背景设置为白
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();

        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
            new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        ServletOutputStream outputStream = response.getOutputStream();
        SimpleColumnWidthStyleStrategy columnWidthStyleStrategy = new SimpleColumnWidthStyleStrategy(35);
        try {
            EasyExcelFactory.write(outputStream)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(columnWidthStyleStrategy)
                .sheet(sheetName)
                .head(dynamicHead)
                .doWrite(dynamicData);
        } catch (Exception e) {
            log.info("EXCEL动态输出异常", e);
        } finally {
            outputStream.close();
        }
    }


    @SuppressWarnings("rawtypes")
    public static void downTemplate(HttpServletResponse response, String fileName) throws IOException {
        String files = "static/template/" + fileName + ".xlsx";
        String fileTemp = ClassLoader.getSystemClassLoader().getResource(files).getPath();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        OutputStream out = new FileOutputStream(new File(fileTemp));
        try {
            ExcelWriter writer = EasyExcelFactory.getWriter(out, ExcelTypeEnum.XLSX, true);
            writer.write1(null, new Sheet(1, 0));
            writer.finish();
        } catch (Exception e) {
            log.info("EXCEL动态输出异常", e);
        } finally {
            outputStream.close();
        }
    }


    @SuppressWarnings("rawtypes")
    public static void delectTempExel(String fileLocal) {
        File file = new File(fileLocal);// 读取
        file.delete();// 删除
    }


}
