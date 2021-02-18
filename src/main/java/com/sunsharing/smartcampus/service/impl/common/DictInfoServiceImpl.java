/*
 * @(#) DictInfoServiceImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 17:24:52
 */

package com.sunsharing.smartcampus.service.impl.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.alibaba.fastjson.JSONArray;
import com.sunsharing.share.common.collection.MapUtil;
import com.sunsharing.smartcampus.mapper.common.DbPolicyDictClassMapper;
import com.sunsharing.smartcampus.mapper.common.DbPolicyDictInfoMapper;
import com.sunsharing.smartcampus.model.pojo.common.DictClass;
import com.sunsharing.smartcampus.model.pojo.common.DictInfo;
import com.sunsharing.smartcampus.service.common.DictInfoService;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import lombok.extern.log4j.Log4j;

@Service(value = "dictService")
@Log4j
public class DictInfoServiceImpl implements DictInfoService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    DbPolicyDictClassMapper dbPolicyDictClassMapper;
    @Autowired
    DbPolicyDictInfoMapper dbPolicyDictInfoMapper;

    private static final String dictClassNameKey = "policy:dict:%s";

    public void loadDict(String classEnName) {
        if (StringUtils.isBlank(classEnName)) {
            throw new RuntimeException("字典名称不能为空");
        }
        //防止注入简单处理
        if (classEnName.trim().indexOf(" ") != -1) {
            throw new RuntimeException("字典名称有误：" + classEnName);
        }
        String key = String.format(dictClassNameKey, classEnName);

        ValueOperations<String, String> hop = redisTemplate.opsForValue();

        if (hop.size(key) > 0) {
            return;
        }

        log.info("初始化加载字典类别【" + classEnName + "】");

        DictClass dictClass = new DictClass().setClassEnName(classEnName);
        List<DictClass> dictClassList = dbPolicyDictClassMapper.findAllList(dictClass);

        log.info("成功加载字典类别【" + classEnName + "】，类别总数:" + dictClassList.size() + "条，默认取第一条");

        if (dictClassList.isEmpty()) {
            return;
        }

        log.info("初始化加载字典【" + classEnName + "】");

        DictInfo dictInfo = new DictInfo().setClassEnName(classEnName);
        List<Map<String, Object>> dictInfoList = dbPolicyDictInfoMapper.findMapList(dictInfo);
        log.info("成功加载字典字典【" + classEnName + "】，字典总数:" + dictInfoList.size() + "条");
        // 默认设置1天的过期时间
        hop.set(key, JSONArray.toJSONString(dictInfoList), 1, TimeUnit.DAYS);

    }

    @Override
    public void deleteDict(String classEnName) {
        if (StringUtils.isEmpty(classEnName)) {
            return;
        }
        String key = String.format(dictClassNameKey, classEnName);
        redisTemplate.delete(key);
    }

    @Override
    public String getCodeByDictName(String classEnName, String labelName) {
        List<Map<String, Object>> allData = getAllData(classEnName);
        Map<String, Object> result = allData.stream()
            .filter(maps -> Objects.equals(MapUtils.getString(maps, "label", ""), labelName))
            .findFirst().orElse(Maps.newHashMap());
        return MapUtils.getString(result, "id", "");
    }

    @Override
    public List<Map<String, Object>> getAllData(String classEnName) {
        loadDict(classEnName);
        String key = String.format(dictClassNameKey, classEnName);
        ValueOperations<String, String> hop = redisTemplate.opsForValue();
        if (hop.size(key) > 0) {
            return (List<Map<String, Object>>) JSONArray.parse(hop.get(key));
        } else {
            return new ArrayList();
        }
    }

    @Override
    public String getNameByDictCode(String classEnName, String dictCode) {
        List<Map<String, Object>> allData = getAllData(classEnName);
        Map<String, Object> result = allData.stream()
            .filter(maps -> Objects.equals(MapUtils.getString(maps, "id", ""), dictCode))
            .findFirst().orElse(Maps.newHashMap());
        return MapUtils.getString(result, "label", "");
    }

    /**
     * 根据传入状态改变当前注销情况
     *
     * @param dictId 字典id
     */
    @Override
    public void updateStateById(String dictId, String zxbs) {
        if (StringUtils.isNotBlank(dictId) && StringUtils.isNotBlank(zxbs)) {
            dbPolicyDictInfoMapper.updateStateById(dictId, Integer.parseInt(zxbs));
        }
    }


    @Override
    public Map getAllDataWithId(String classEnName) {
        List<Map<String, Object>> list = this.getAllData(classEnName);
        Map rst = new LinkedHashMap();
        for (Map m : list) {
            String code = (String) m.get("id");
            String name = (String) m.get("label");
            rst.put(code, name);
        }
        return rst;
    }

    @Override
    public List<Map<String, Object>> getSelectAllData(String classEnName) {
        List<Map<String, Object>> list = this.getAllData(classEnName);
        List<Map<String, Object>> resultList = Lists.newArrayList();
        list.stream().forEach(maps -> {
            String code = (String) maps.get("id");
            String name = (String) maps.get("label");
            Map temp = MapUtil.ofHashMap("value", code, "label", name);
            resultList.add(temp);
        });
        return resultList;
    }
}
