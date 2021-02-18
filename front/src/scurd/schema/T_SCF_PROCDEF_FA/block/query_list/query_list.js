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
import PropTypes from 'prop-types';
import {SchemaService} from "@share/scurd/block/SchemaService";
import {api} from "../../../../../services/serviceApi";
class QueryListWapp extends QueryList {

    constructor(props) {
        super(props);
    }
    static contextTypes = {
        router: PropTypes.object.isRequired,
    }

    publish(id) {
        let {serviceName,methodName} = api["publishProcDef"];
        SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"jsonObject","data":{"id" :id}}]
        ).then(function (res) {
            res = JSON.parse(res);
            if(res && res.status &&res.data.status){
                success(res.data.msg);
            }else{
                alert(res.data.msg);
            }
        })
    }

}
export default QueryListWapp;
