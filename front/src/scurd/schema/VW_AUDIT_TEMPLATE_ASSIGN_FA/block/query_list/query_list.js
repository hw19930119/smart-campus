/*
 * @(#) query_list.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-11-02 11:07:10
 */
import React from 'react';
import {QueryList} from '@share/scurd/block/query_list/query_list';
import {removeByName, setData} from "../../../../../utils/StoreUtil";

class QueryListWapp extends QueryList {

    detail(id,item) {
        removeByName('schoolInfo');
        setData('schoolInfo',item);
        window.location.href = `/smart-campus/audit.html#/auditExpert/detail/${id}/${item.DECLARE_ID}`
    }

}
export default QueryListWapp;
