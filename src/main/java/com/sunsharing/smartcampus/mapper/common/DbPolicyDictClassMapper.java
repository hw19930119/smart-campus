/*
 * @(#) DbPolicyDictClassMapper
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 17:30:18
 */

package com.sunsharing.smartcampus.mapper.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.pojo.common.DictClass;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DbPolicyDictClassMapper extends BaseMapper<DictClass> {

    List<DictClass> findAllList(DictClass dictClass);

    /**
     * 根据字典类别id更新状态
     *
     * @param dictId 字典类别id
     * @param zxbs   注销标识状态
     */
    void updateStateById(@Param("dictId") String dictId, @Param("zxbs") int zxbs);
}
