/*
 * @(#) detail_main.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author lihui
 * <br> 2020-08-21 17:09:44
 */

import { DetailMain } from '@share/scurd/block/detail_main/detail_main';
class DetailMainWrap extends DetailMain {
    ready() {
        // 字段控件
        const SCHOOL_TYPE = this.findComponent('SCHOOL_TYPE');
        const SCHOOL_TYPE_NAME = this.findComponent('SCHOOL_TYPE_NAME');
        if(SCHOOL_TYPE.val() !== "S06" || !SCHOOL_TYPE.val()){
            $(".label_SCHOOL_TYPE_NAME").hide();
            $(".label_SCHOOL_TYPE_NAME").next().hide();
        }
    }
}
export default DetailMainWrap;
