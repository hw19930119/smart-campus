/*
 * @(#) FieldConfigController
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-18 10:13:01
 */

package com.sunsharing.smartcampus.web.common;

import com.sunsharing.share.webex.entity.ShareResponse;
import com.sunsharing.smartcampus.service.filed.FieldConfigUpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: cxy
 * @date: 2020-1-16 10:23:30
 * @description: 字段配置表管理控制层
 */
@RestController
@RequestMapping(value = "/field/fieldConfig")
public class FieldConfigController {

    @Autowired
    private FieldConfigUpService fieldConfigUpService;

    /**
     * 字段维护附件上传
     *
     * @param srcFile  文件信息
     * @return
     */
    @PostMapping("/upload")
    public ShareResponse batchProcessingUpload(HttpServletResponse response, @RequestParam("file") MultipartFile srcFile)
        throws IOException {
        return fieldConfigUpService.batchProcessingUpload(response, srcFile);
    }

    /**
     * 导出模板
     * @param response response
     */
    @GetMapping("/downLoad/template")
    public ShareResponse downLoadTemplate(HttpServletResponse response) throws IOException {
        return fieldConfigUpService.downLoadTemplate(response);
    }

}
