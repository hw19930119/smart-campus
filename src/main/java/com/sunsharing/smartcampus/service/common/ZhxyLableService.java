/*
 * @(#) ZhxyLableService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-08-17 10:46:48
 */

package com.sunsharing.smartcampus.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.common.TLable;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyPc;

import java.util.List;

public interface ZhxyLableService extends IService<TLable> {

    List<TLable> getLableByName(String name);

    int addLable(String id,String name);
}
