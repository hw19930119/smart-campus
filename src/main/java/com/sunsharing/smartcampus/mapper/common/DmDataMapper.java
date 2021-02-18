/*
 * @(#) DmDataMapper
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:53:57
 */

package com.sunsharing.smartcampus.mapper.common;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.vo.common.DmData;
import com.sunsharing.smartcampus.model.vo.common.SchoolVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DmDataMapper extends BaseMapper<DmData> {

    List<Map<String, Object>> findByStartCodeAndEndCode(@Param("baseXqxh") String baseXqxh, @Param("startCode") String startCode, @Param("endCode") String endCode);

    List<DmData> queryStartDm(@Param("dmName") String dmName);

    List<Map<String, Object>> querySchoolTypeHeadFour();

    List<DmData> queryZhSelectDm(@Param("mark") String mark);

    List<SchoolVo> querySchoolDm(@Param("xzqh") String xzqh);

    List<Map<String, Object>> queryDpOpinion();
}
