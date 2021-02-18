import { ModalTool } from '@share/shareui';


export const BS_STYLE = {
    SUCCESS: 'success',
    WARNING: 'warning',
    NONE: ''
};

const defaultOpt = { bsStyle: BS_STYLE.NONE, backdrop: 'static', closeBtn: false };

/**
 * alert 实现
 * alert("提示").then(()=>{})
 * @param msg
 * @param opt
 * @returns {Promise}
 * @constructor
 */
export const alert = function (msg, opt) {
    const option = Object.assign({}, defaultOpt, opt);

    return new Promise(resolve => {
        return new ModalTool({
            ...option,
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
    const option = Object.assign({}, defaultOpt, opt);

    return new Promise((resolve, reject) => {
        return new ModalTool({
            ...option,
            loseBtn: false,
            onOk: () => {
                resolve(true);
            },
            content: msg,
            onCancel: () => {
                reject(false);
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
    return alert(msg, Object.assign({}, defaultOpt, { bsStyle: BS_STYLE.WARNING }));
};

export const alertSuccess = function (msg) {
    return alert(msg, Object.assign({}, defaultOpt, { bsStyle: BS_STYLE.SUCCESS }));
};

// 兼容之前的版本
export const systemConfirm = function (msg, opt = Object.assign({}, defaultOpt, { bsStyle: BS_STYLE.WARNING })) {
    const option = Object.assign({}, defaultOpt, opt);

    return new Promise((resolve, reject) => {
        return new ModalTool({
            ...option,
            loseBtn: false,
            onOk: () => {
                resolve(true);
            },
            content: msg,
            onCancel: () => {
                reject(false);
            }
        });
    });
};

export const eduConfirm = function (msg) {
    return confirm(msg, Object.assign({}, defaultOpt, { bsStyle: BS_STYLE.WARNING }));
};
