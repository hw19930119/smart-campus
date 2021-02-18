/*
 * @(#) SmartDmService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-27 10:37:32
 */

package com.sunsharing.smartcampus.service.dm;


import com.sunsharing.smartcampus.model.vo.common.SchoolVo;

import java.util.List;
import java.util.Map;

/**
 * 业务表码获取自定义service
 * 因为scurdSeviceImpl.getDmData()方法被占用，所以自定义了Service汇总类。
 * 业务相关获取dm的自定义方法都可以往这个service中添加，方便维护
 */
public interface SmartDmService {

    /**
     * 获取必填表码值
     * @return
     */
    List<Map<String, Object>> loadRequiredDm();

    /**
     * 获取批次状态码表值
     * @return
     */
    List<Map<String, Object>> loadPcStateDm();

    /**
     * 获取学校类型码表值
     * @return
     */
    List<Map<String, Object>> loadSchoolTypeDm();

    /**
     * 加载数据库码表
     * @return
     */
    List<Map<String, Object>> loadSmartDm(String dmName);

    /**
     * 加载数据库码表 - DM_ZH_SELECT
     * @return
     */
    List<Map<String, Object>> loadJcssZhSelectDm(String mark);

    /**
     * 加载申报端认证用户认证状态 AttestStateEnum
     * @return
     */
    List<Map<String, Object>> loadAttestStateEnum();

    List<SchoolVo> loadLoginUserShoolDm();

    /**
     * 查询申报学校
     * @param xzqh
     * @return
     */
    List<SchoolVo> loadShoolDm(String xzqh);


    /**
     * 加载点评已经表码
     * @return
     */
    List<Map<String, Object>> loadDpOpinion();
}
