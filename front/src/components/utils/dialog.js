/*
 * @(#) dialog.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author xierx
 * <br> 2019-03-05 20:17:59
 */

import { ModalTool } from '@share/shareui';


export const BS_STYLE = {
    SUCCESS: 'success',
    WARNING: 'warning',
    NONE: ''
};

/**
 * Alert 实现
 * Alert("提示").then(()=>{})
 * @param msg
 * @param opt
 * @returns {Promise}
 * @constructor
 */

export const alert = function (msg, opt) {
    const defaultOpt = { bsStyle: BS_STYLE.SUCCESS };
    const option = Object.assign({}, defaultOpt, opt);

    return new Promise(resolve => {
        new ModalTool({
            bsStyle: option.bsStyle,
            onOk: () => {
                resolve();
            },
            content: msg,
            cancelText: null
        });
    });
};

/**
 * Confirm 确认框
 * Confirm("提示").then().fail()
 * @param msg
 * @param opt
 * @returns {Promise}
 * @constructor
 */
export const confirm = function (msg, opt) {
    const defaultOpt = { bsStyle: BS_STYLE.NONE };
    const option = Object.assign({}, defaultOpt, opt);

    return new Promise((resolve, reject) => {
        new ModalTool({
            bsStyle: option.bsStyle,
            closeBtn: false,
            onOk: () => {
                resolve();
            },
            content: msg,
            onCancel: () => {
                reject();
            }
        });
    });
};

/**
 * 快速的错误提示框
 * @param msg
 * @constructor
 */
export const alertError = function (msg) {
    return alert(msg, { bsStyle: BS_STYLE.WARNING });
};
