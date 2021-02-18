package com.sunsharing.smartcampus.service.common;

import com.sunsharing.smartcampus.model.pojo.common.TzhxyTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 模板信息表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-03
 */
public interface TzhxyTemplateService extends IService<TzhxyTemplate> {
    /**
     *  复制模板
     * @param oldPcId -- 旧批次号
     * @param templateIds -- 要复制的模板列表
     * @return 复制结果
     */
    Map copyTemp(String oldPcId,String newPcId,String templateIds);

    /**
     *  根据批次号查询模板
     * @param pcId -- 批次号
     * @return 查询结果
     */
    List<TzhxyTemplate> selectTemplateByPcId(String pcId);
}
