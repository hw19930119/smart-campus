package com.sunsharing.smartcampus.service.apply;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.share.common.base.exception.ResponseCode;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyBaseInfo;
import com.sunsharing.smartcampus.model.vo.apply.SubmitParamVo;

import java.util.Map;

/**
 * <p>
 * 学校基本信息 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-04
 */
public interface TzhxyBaseInfoService extends IService<TzhxyBaseInfo> {

    /**
     *  根据学校唯一标识查找最新一条学校基本信息
     * @return
     */
    TzhxyBaseInfo queryFirstForUnitCode(String unitCode);

    /**
     * 提交页面信息查询
     * @param businessKey 业务主键
     * @return 查询结果
     */

    Map queryApplyAllByBusinessKey(String businessKey);

    /**
     * 提交启动流程
     * @param submitParamVo
     * @return
     */
    ResponseCode submitDeclare(SubmitParamVo submitParamVo);

    /**
     * 提交页面学校基本信息查询
     * @param businessKey 业务主键
     * @return 查询结果
     */
    Map<String, Object> queryBaseInfoByBusinessKey(String businessKey);

    /**
     * 提交页面基础设施信息查询
     * @param businessKey 业务主键
     * @return 查询结果
     */
    Map<String, Object> queryJcxxByBusinessKey(String businessKey);

    /**
     * 根据批次号进行基础实施的归档
     * @param pcNo
     * @return
     */
    Integer updateBatchJcssPlaceOnfile(String pcNo);

    /**
     *  删除申报信息
     * @param declareId
     * @return
     */
    ResponseCode delBaseInfo(String declareId);

    /**
     *  判断该批次号是否有已提交、审核中的申报数据
     * @param pcNo
     * @return
     */
    boolean checkCommitBaseInfoForPcNo(String pcNo, String unitCode);

    /**
     * 撤销
     * @param paramVo
     * @return
     */
    ResponseCode revokeDeclaration(SubmitParamVo paramVo);

    /**
     * 查询系统设置的回退提交时间
     * @return
     */
    Map<String, Object> querySystemConfigBackEndTime();

    /**
     * 更T_zhxy_SYS表的mack标识
     */
    void updateSysConfigMack();
}
