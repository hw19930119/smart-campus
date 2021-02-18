/*
 * @(#) add_content.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author mana
 * <br> 2020-03-25 11:31:28
 * ————————————————————————————————
 *    修改记录
 *    修改者：
 *    修改时间：
 *    修改原因：
 * ————————————————————————————————
 */
import {DetailContent} from '@share/scurd/block/detail_content/detail_content';
import { getQueryString } from '@share/utils';
import './style.scss';
class DetailContentWrap extends DetailContent {

    //返回模板管理
    cancel = ()=>{
        let pcId = getQueryString(window.location,'pcId');
        window.location.href = `/smart-campus/query.html?g_schema_key=T_ZHXY_TEMPLATE_FA&PC_ID=${pcId}`
    }

}

export default DetailContentWrap;
