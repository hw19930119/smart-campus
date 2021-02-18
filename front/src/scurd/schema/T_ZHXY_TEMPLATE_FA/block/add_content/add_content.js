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
import {AddContent} from '@share/scurd/block/add_content/add_content';
import { getQueryString } from '@share/utils';
class AddContentWrap extends AddContent {

    constructor(props) {
        super(props);
        this.addCallBack = this.addCallBack.bind(this);

    }
    addCallBack(data) {
        let pcId = getQueryString(window.location,'PC_ID');
        if(!pcId){
            pcId = getQueryString(window.location,'pcId');
        }
        window.success('保存成功');
        setTimeout(()=>{
            window.location.href = `/smart-campus/manager.html?templateId=${data}&pcId=${pcId}`
        },1000)
    }
}

export default AddContentWrap;
