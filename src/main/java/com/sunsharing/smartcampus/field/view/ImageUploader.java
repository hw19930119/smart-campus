/*
 * @(#) ImageUploader
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
 * @description: 图片上传
 */
@Data
@Accessors(chain = true)
public class ImageUploader {

    /**
     * 图片大小
     */
    private String size;
    /**
     * 图片张数
     */
    private String num;
    /**
     * 图片上传类型
     */
    private String imageTypeOptionAll;
}
