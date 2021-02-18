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
import {QueryCon} from '@share/scurd/block/query_con/query_con';

class QueryConWapp extends QueryCon {

    searchFun(e){
        let {g_schema_key} = this.props;
        super.searchFun(e);
        $.fn.ulynlist.refresh($(`#js-table${g_schema_key}`));
    }
}
export default QueryConWapp;
