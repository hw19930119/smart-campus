/*
 * @(#) DictInfoService
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 17:26:08
 */

package com.sunsharing.smartcampus.service.common;

import java.util.List;
import java.util.Map;

public interface DictInfoService {

    List<Map<String, Object>> getAllData(String classEnName);

    String getNameByDictCode(String classEnName, String dictCode);

    void updateStateById(String dictId, String zxbs);

    Map getAllDataWithId(String classEnName);

    List<Map<String, Object>> getSelectAllData(String classEnName);

    void deleteDict(String classEnName);

    String getCodeByDictName(String classEnName, String labelName);
}
