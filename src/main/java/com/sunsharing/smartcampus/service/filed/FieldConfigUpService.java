/*
 * @(#) FieldConfigUpService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-18 10:13:45
 */

package com.sunsharing.smartcampus.service.filed;

import com.sunsharing.share.webex.entity.ShareResponse;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by wuxw on 2020/5/25.
 */
public interface FieldConfigUpService extends ExcelCheckManager<Map> {

    ShareResponse downLoadTemplate(HttpServletResponse response) throws IOException;

    ShareResponse batchProcessingUpload(HttpServletResponse response, MultipartFile file) throws IOException;
}
