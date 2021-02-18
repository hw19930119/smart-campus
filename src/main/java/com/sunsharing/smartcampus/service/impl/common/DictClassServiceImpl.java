/*
 * @(#) DictClassServiceImpl
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 17:25:07
 */

package com.sunsharing.smartcampus.service.impl.common;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunsharing.smartcampus.mapper.common.DbPolicyDictClassMapper;
import com.sunsharing.smartcampus.model.pojo.common.DictClass;
import com.sunsharing.smartcampus.service.common.DictClassService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Service(value = "dictClassService")
@Log4j
public class DictClassServiceImpl extends ServiceImpl<DbPolicyDictClassMapper, DictClass> implements DictClassService {

    @Autowired
    private DbPolicyDictClassMapper dbPolicyDictClassMapper;

    @Override
    public void updateStateById(String dictId, String zxbs) {
        if (StringUtils.isNotBlank(dictId) && StringUtils.isNotBlank(zxbs)) {
            dbPolicyDictClassMapper.updateStateById(dictId, Integer.parseInt(zxbs));
        }
    }
}
