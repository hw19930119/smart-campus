/*
 * @(#) query_list.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author liuxia
 * <br> 2020-08-20 16:02:25
 */
import React from 'react';
import {QueryList} from '@share/scurd/block/query_list/query_list';
import {removeByName, setData} from "../../../../../utils/StoreUtil";

class QueryListWapp extends QueryList {

    detail(id,item) {
        removeByName('schoolInfo');
        setData('schoolInfo',item);
        window.location.href = `/smart-campus/audit.html#/audit/detailAll/${id}/${item.DECLARE_ID}/VW_SCF_ZJTOREVIEW_FA`;
    }
}
export default QueryListWapp;
