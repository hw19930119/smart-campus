/*
 * @(#) ExpertVo
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-10-30 15:06:17
 */

package com.sunsharing.smartcampus.model.vo.comment;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExpertVo {

    private String userId;

    private String userName;
    /**是否可以被分配 0 可以 大于 0 不可以**/
    private String falg;
}
