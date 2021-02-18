package com.sunsharing.smartcampus.utils;


import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.word.WordExportUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ReportUtils {

    public static void exportWordToPdf(HttpServletResponse response, Map<String, Object> data, String templateUrl, String exportFileName) throws Exception {
        XWPFDocument doc = null;
        try {
            doc = WordExportUtil.exportWord07(
                templateUrl, data);
            exportWordStream(response, doc, exportFileName);
        } catch (Exception e) {
            log.error("pdf下载失败", e);
        } finally {
            if (!Objects.isNull(doc)) {
                doc.close();
            }
        }
    }

    //     word 转pdf 通过浏览器下载的形式
    private static void exportWordStream(HttpServletResponse response, XWPFDocument workbook, String fileName) throws Exception {
        response.setHeader("Content-Disposition",
            "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "iso8859-1"));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        Document document = new Document();
        document.loadFromStream(parse(out), FileFormat.Docx);
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(response.getOutputStream());
        document.saveToStream(bufferedOutPut, FileFormat.PDF);
        bufferedOutPut.flush();
        bufferedOutPut.close();

    }

    private static ByteArrayInputStream parse(final OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        final ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        out.flush();
        out.close();
        return swapStream;
    }
}
