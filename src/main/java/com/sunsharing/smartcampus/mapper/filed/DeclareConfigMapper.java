/*
 * @(#) DeclareConfigMapper
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-22 19:20:24
 */

package com.sunsharing.smartcampus.mapper.filed;

import com.sunsharing.smartcampus.model.vo.filed.FieldConfigDto;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeclareConfigMapper {

    List<FieldConfigDto> findBasicFieldList();

}
