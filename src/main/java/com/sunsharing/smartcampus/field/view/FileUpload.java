/*
 * @(#) FileUpload
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:59:33
 */

package com.sunsharing.smartcampus.field.view;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: cxy
 * @date: 2020/3/3
 * @description: 附件上传
 */
@Data
@Accessors(chain = true)
public class FileUpload {

    /**
     * 附件类型
     */
    private String fileType;

    /**
     * 模板
     */
    private String fileTemplateAddress;

    /**
     * 上传地址
     */
    private String url = "/co-policy/upload";
}
