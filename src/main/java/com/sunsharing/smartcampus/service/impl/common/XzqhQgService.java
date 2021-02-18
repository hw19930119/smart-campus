/*
 * @(#) XzqhQgService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 16:51:56
 */

/**
 * 基础 - 行政区划获取服务
 * xzqhQgService - e8d2b90e760a49c2997fe14b8372c1b2
 */

package com.sunsharing.smartcampus.service.impl.common;

import com.sunsharing.smartcampus.mapper.common.DmDataMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j;

@Log4j
@Service(value = "xzqhQgService")
public class
XzqhQgService {
    enum LEVEL {
        省, 市, 县, 街道, 社区
    }

    @Autowired
    private DmDataMapper dmDataMapper;

    public List<Map<String, Object>> getXzqh(String baseXzqh, String level) {
        LEVEL le = LEVEL.valueOf(level);

        if (StringUtils.isBlank(baseXzqh)) {
            baseXzqh = "000000000000";
        }
        String start = "";
        String end = "";
        switch (le) {
            case 省:
                start = "";
                end = "0000000000";
                break;
            case 市:
                start = baseXzqh.substring(0, 2);
                end = "00000000";
                break;
            case 县:
                start = baseXzqh.substring(0, 4);
                end = "000000";
                break;
            case 街道:
                start = baseXzqh.substring(0, 6);
                end = "000";
                break;
            case 社区:
                start = baseXzqh.substring(0, 9);
                end = "";
                break;
        }
        List<Map<String, Object>> data = dmDataMapper.findByStartCodeAndEndCode(baseXzqh, start, end);

        return data;
    }


}
