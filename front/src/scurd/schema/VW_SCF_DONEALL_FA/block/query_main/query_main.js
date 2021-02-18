/*
 * @(#) query_main.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author hanjb
 * <br> 2020-10-23 15:04:09
 */

import {QueryMain} from "@share/scurd/block/query_main/query_main";

class QueryMainAudit extends QueryMain {

    constructor(props) {
        super(props);
    }

    ready() {
        let userInfo = this.props.schema.user_info;
        if (userInfo.roleCode == 'qjyj') {
            this.removeColumns('XZQH');
            // $(".query_item_XZQH").hide();
        }
    }
}

export default QueryMainAudit;
