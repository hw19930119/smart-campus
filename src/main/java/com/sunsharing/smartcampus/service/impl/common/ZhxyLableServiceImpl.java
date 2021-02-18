/*
 * @(#) ZhxyLableServiceImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-08-17 11:04:14
 */

package com.sunsharing.smartcampus.service.impl.common;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.mapper.common.TzhxyLableMapper;
import com.sunsharing.smartcampus.model.pojo.common.TLable;
import com.sunsharing.smartcampus.service.common.ZhxyLableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class ZhxyLableServiceImpl implements ZhxyLableService{
    @Autowired
    TzhxyLableMapper tzhxyLableMapper;
    @Override
    public boolean saveBatch(Collection<TLable> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<TLable> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<TLable> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(TLable entity) {
        return false;
    }

    @Override
    public TLable getOne(Wrapper<TLable> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<TLable> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<TLable> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<TLable> getBaseMapper() {
        return null;
    }

    @Override
    public List<TLable> getLableByName(String name) {
        List<TLable> list=tzhxyLableMapper.getLableByName(name);
        return list;
    }

    @Override
    public int addLable(String id, String name) {

        return tzhxyLableMapper.addLable(id,name);
    }
}
