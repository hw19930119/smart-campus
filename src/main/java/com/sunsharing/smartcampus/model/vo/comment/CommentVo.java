/*
 * @(#) CommentVo
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-11-02 10:49:05
 */

package com.sunsharing.smartcampus.model.vo.comment;

import lombok.Data;

@Data
public class CommentVo {
    /**专家名称*/
    private String userName;
    /**专家ID*/
    private String userId;
    /**点评内容*/
    private String context;
    /**属性ID*/
    private String fieldIds;

}
