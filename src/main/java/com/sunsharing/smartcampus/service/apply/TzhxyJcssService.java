package com.sunsharing.smartcampus.service.apply;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyJcss;

/**
 * <p>
 * 学校基础实施信息 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-04
 */
public interface TzhxyJcssService extends IService<TzhxyJcss> {

    /**
     * 根据主表信息获取基础实施的关联信息
     * @param g_id
     * @return
     */
    String getJcssIdForBaseInfoId(String g_id);
}
