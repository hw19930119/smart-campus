/*
 * @(#) TzhxyLableMapper
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-08-17 11:12:44
 */

package com.sunsharing.smartcampus.mapper.common;

import com.sunsharing.smartcampus.model.pojo.common.TLable;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TzhxyLableMapper {

    List<TLable> getLableByName(String name);

    int addLable(@Param("id") String id,@Param("name") String name);
}
