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
import {api} from "../../../../../services/serviceApi";
import {SchemaService} from "@share/scurd/block/SchemaService";

class QueryListWapp extends QueryList {

    detail(id,item) {
        removeByName('schoolInfo');
        setData('schoolInfo',item);
        window.location.href = `/smart-campus/audit.html#/audit/detail/${id}/${item.DECLARE_ID}`

    }
    cancelOneDoneOfAgree(id) {
        let {g_schema_key} = this.props;
        let {serviceName,methodName} = api["cancelOneDoneOfAgree"];
        SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"jsonObject","data":{"id" :id}}]
        ).then(function (res) {
            res = JSON.parse(res);
            let {data,status} = res;
            if(status && data.status){
                success(res.data.msg);
                $.fn.ulynlist.refresh($(`#js-table${g_schema_key}`));
            }else{
                alert(res.data.msg);
            }
        })
    }

}
export default QueryListWapp;
