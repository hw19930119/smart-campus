/*
 * @(#) TzhxyPcService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-08-03 16:50:51
 */

package com.sunsharing.smartcampus.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.share.common.base.exception.ResponseCode;
import com.sunsharing.smartcampus.model.pojo.common.TzhxyPc;
import com.sunsharing.smartcampus.model.vo.apply.DeclarationInitVo;
import com.sunsharing.smartcampus.model.vo.audit.ApplyUserVo;

import java.util.Map;

/**
 * <p>
 * 批次信息表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-03
 */
public interface TzhxyPcService extends IService<TzhxyPc> {

    /**
     *  新增 or 修改批次信息
     * @param pcNo -- 批次号
     * @param pcName -- 批次名称
     * @return
     */
    ResponseCode addOrUpdate(String pcNo, String pcName, String mark);

    /**
     *  启用批次
     * @param pcNo
     * @return
     */
   ResponseCode enablePc(String pcNo);

    /**
     *  批次归档
     * @param pcNo
     * @return
     */
    ResponseCode placeOnfile(String pcNo);

    /**
     * 创建申报端是，初始化页面数据加载
     * @param userVo
     * @return
     */
    DeclarationInitVo laodDeclarationInit(ApplyUserVo userVo, String declareId);
    /**
     * 复制批次
     * @param pcIds
     * @return 复制结果
     */
    Map copyPc(String pcIds);
    /**
     * 查询出所有批次
     * @return 查询结果
     */
    Map listAllPc();
}
