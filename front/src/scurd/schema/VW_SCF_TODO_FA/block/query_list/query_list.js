/*
 * @(#) query_list.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author chenchen
 * <br> 2019-09-19:02:25
 */
import React from 'react';
import {QueryList} from '@share/scurd/block/query_list/query_list';
import {removeByName, setData} from "../../../../../utils/StoreUtil";

class QueryListWapp extends QueryList {

    enterProcess(id,item) {
        removeByName('schoolInfo');
        setData('schoolInfo',item);
        window.location.href = `/smart-campus/audit.html#/audit/add/${id}/${item.DECLARE_ID}`
    }
}
export default QueryListWapp;
