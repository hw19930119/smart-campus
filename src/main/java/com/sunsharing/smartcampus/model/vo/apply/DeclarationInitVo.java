/*
 * @(#) DeclarationInitVo
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-05 15:46:13
 */

package com.sunsharing.smartcampus.model.vo.apply;

import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeclarationInitVo {

    private String pcNo;

    private Map<String, Object> treeData;

    private String g_id;

    private String state;

    private boolean yxsb;

    private ApplyLoginUser applyUserVo;

    private String pcState;

}
