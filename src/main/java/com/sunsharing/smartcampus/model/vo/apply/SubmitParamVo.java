/*
 * @(#) SubmitParamVo
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-05 17:14:28
 */

package com.sunsharing.smartcampus.model.vo.apply;

import lombok.Data;

@Data
public class SubmitParamVo {

    private String declareId;

    /**是否是回退提交，默认不是*/
    private boolean sfshttj = false;
}
