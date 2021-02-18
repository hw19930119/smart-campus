/*
 * @(#) FiledTypeEnum
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:41:51
 */

package com.sunsharing.smartcampus.constant.enums;

import com.sunsharing.share.common.base.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by wuxw on 2020/03/18
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum StarLevelWayEnum implements IEnum {
    不要("0"),
    手动("1"),
    自动("2");
    private String value;


}
