/*
 * @(#) DictClassService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 17:26:08
 */

package com.sunsharing.smartcampus.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.common.DictClass;

public interface DictClassService extends IService<DictClass> {
    /**
     * 根据字典id ,注销状态改变当前注销状态
     *
     * @param dictId 字典id
     * @param zxbs   注销标识
     */
    void updateStateById(String dictId, String zxbs);

}
