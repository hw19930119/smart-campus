/*
 * @(#) FieldItemDto
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-07-21 11:56:08
 */

package com.sunsharing.smartcampus.model.vo.filed;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by wuxw on 2020/3/18.
 */
@Data
@Accessors(chain = true)
public class FieldItemDto implements Serializable {
    private String optionType; //选项类型
    private String datasourceData; //读取数据id
    private String tableName; //读取数据id
    private String keyField; //读取数据id
    private String valueField; //读取数据id
    private List<FildItemSelectDto> itemList; //选项数据json

}
