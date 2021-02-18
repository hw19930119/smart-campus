/*
 * @(#) DbPolicyDictInfoMapper
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 17:30:18
 */

package com.sunsharing.smartcampus.mapper.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunsharing.smartcampus.model.pojo.common.DictInfo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DbPolicyDictInfoMapper extends BaseMapper<DictInfo> {

    List<Map<String, Object>> findMapList(DictInfo dictInfo);

    /**
     * 根据字典信息id更新状态
     *
     * @param dictId 字典id
     * @param zxbs   注销标识状态
     */
    void updateStateById(@Param("dictId") String dictId, @Param("zxbs") int zxbs);

    List<DictInfo> findDataByClassEnNameAndDictCode(DictInfo dictInfo);

}
