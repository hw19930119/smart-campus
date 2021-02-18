package com.sunsharing.smartcampus.service.impl.apply;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.mapper.apply.TzhxyJcssMapper;
import com.sunsharing.smartcampus.model.pojo.apply.TzhxyJcss;
import com.sunsharing.smartcampus.service.apply.TzhxyJcssService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学校基础实施信息 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-08-04
 */
@Service
public class TzhxyJcssServiceImpl extends ServiceImpl<TzhxyJcssMapper, TzhxyJcss> implements TzhxyJcssService {


    @Override
    public String getJcssIdForBaseInfoId(String baseInfoId) {
        LambdaQueryWrapper<TzhxyJcss> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.eq(TzhxyJcss::getDeclareId, baseInfoId);
        List<TzhxyJcss> list = this.list(lambdaQuery);
        if (list.size() > 1) {
            throw new RuntimeException("存在多条基础实施草稿数据");
        }
        return list.size() == 0 ? "" : list.get(0).getJcssId();
    }
}
