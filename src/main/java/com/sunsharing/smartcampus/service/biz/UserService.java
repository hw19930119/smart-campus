/*
 * @(#) FieldConfigService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:46:58
 */

package com.sunsharing.smartcampus.service.biz;

import com.alibaba.fastjson.JSONObject;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;
import com.sunsharing.smartcampus.model.vo.common.IeduUserVo;

public interface UserService{
    IeduUserVo getIeduUserVoByIdNum(String idNum,String userId);

    ApplyUserVo getApplyUserVoByUserId(String userId);

    JSONObject getMenusByLoginRoleId(String roleId);
}
