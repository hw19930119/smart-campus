/*
 * @(#) TzhxyPcMapper
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-03 20:17:01
 */

package com.sunsharing.smartcampus.mapper.common;

import com.sunsharing.smartcampus.model.pojo.common.TzhxyPc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 批次信息表 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-03
 */
@Repository
public interface TzhxyPcMapper extends BaseMapper<TzhxyPc> {

    TzhxyPc queryNowAvailablePC(@Param("state")String state);

    TzhxyPc selectDiyById(@Param("pcId")String pcId);

    List<Map> selectAll();

}
