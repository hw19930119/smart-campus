/*
 * @(#) ScurdUtils.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author xierx
 * <br> 2019-02-28 09:14:20
 */
import { SchemaService } from '@share/scurd/block/SchemaService';
import { ParamsUtils } from '@share/scurd/block/Utils';
import { isEmpty, isNotEmpty } from './util';
import { checkCard } from './IdCardUtil';
import BaseComponent from '@share/scurd/component/BaseComponent';
import { getContextPath } from './GetContextPath';
import $ from 'jquery';

/**
 * 覆写表单的重置方法
 * @param obj 所要覆写的对象
 * @param refresh 所要重置的select
 */
export const resetFun = (obj, refresh) => {
    const { schema, stateChange } = obj.props;

    SchemaService.getColumns(schema, 'search_columns').forEach(item => {
        if (obj.findComponent(item.column).state.readOnly) {
            return;
        }
        const defaultValue = ParamsUtils.getQueryString(item.column, obj.props);
        let v = defaultValue;

        if (defaultValue && defaultValue !== '') {
            stateChange(item.column, defaultValue);
        } else {
            const de = item.ext.defaultValue;

            if (de && de !== '') {
                stateChange(item.column, de);
                v = de;
            } else {
                stateChange(item.column, '');
                v = '';
            }
        }
        // 针对Date特殊处理一下
        if (item.ext.needReset
            || item.type === 'xzqhWithAddress'
            || (item.type === 'date'
                && item.search_type === 'single')) {
            const d = obj.findComponent(item.column);

            d && d.reset(v);
        } else if (item.type === 'date' && item.search_type === 'range') {
            const d = obj.findComponent(`curd_begin_${item.column}`);
            const d2 = obj.findComponent(`curd_end_${item.column}`);

            d && d.reset(v);
            d2 && d2.reset(v);
        }
    });
    if (isEmpty(refresh)) {
        return;
    }
    refresh.forEach(item => {
        item.options(item.itemData().ext.data);
    });
};


/**
 * 覆写表单的重置方法
 * @param obj 所要覆写的对象
 * @param refresh 所要重置的select
 */
export const resetReadOnly = (obj, refresh) => {
    // 需要清空readOnly的情况
    const { schema, stateChange } = obj.props;

    SchemaService.getColumns(schema, 'search_columns').forEach(item => {

        const defaultValue = ParamsUtils.getQueryString(item.column, obj.props);
        let v = defaultValue;

        if (defaultValue && defaultValue !== '') {
            stateChange(item.column, defaultValue);
        } else {
            const de = item.ext.defaultValue;

            if (de && de !== '') {
                stateChange(item.column, de);
                v = de;
            } else {
                stateChange(item.column, '');
                v = '';
            }
        }
        // 针对Date特殊处理一下
        if (item.ext.needReset
            || item.type === 'xzqhWithAddress'
            || (item.type === 'date'
                && item.search_type === 'single')) {
            const d = obj.findComponent(item.column);

            d && d.reset(v);
        } else if (item.type === 'date' && item.search_type === 'range') {
            const d = obj.findComponent(`curd_begin_${item.column}`);
            const d2 = obj.findComponent(`curd_end_${item.column}`);

            d && d.reset(v);
            d2 && d2.reset(v);
        }
    });
    if (isEmpty(refresh)) {
        return;
    }
    refresh.forEach(item => {
        item.options(item.itemData().ext.data);
    });
};



/**
 * 身份信息联动
 * @param zjlxObj 证件类型
 * @param zjhmObj 证件号码
 * @param csrqObj 出生日期
 * @param xbObj 性别
 */
export const sfxxLink = (zjlxObj, zjhmObj, csrqObj, xbObj) => {
    // 证件类型为身份证触发校验、连带出出生日期跟性别
    zjlxObj.on('change', function () {
        if (this.val() === '1') {
            // 类型为身份证触发身份证校验
            zjhmObj.isValidate = () => {
                const zjhm = zjhmObj.val();
                const status = checkCard(zjhm);

                if (status) {
                    // 出生日期跟性别联动
                    if (isNotEmpty(csrqObj)) {
                        csrqObj.readOnly();
                        csrqObj.val(zjhm.substring(6, 14));
                        csrqObj.blurValidate();
                    }
                    if (isNotEmpty(xbObj)) {
                        xbObj.readOnly();
                        xbObj.val(zjhm.substring(16, 17) % 2 === 0 ? '2' : '1');
                    }
                }
                return { status, error: '请输入正确的身份证件号码' };
            };
            return;
        }
        if (isNotEmpty(xbObj)) {
            xbObj.readOnly(false);
        }
        if (isNotEmpty(csrqObj)) {
            csrqObj.readOnly(false);
        }
        zjhmObj.isValidate = () => {
            const { itemData } = zjhmObj.props;

            return BaseComponent.validate(itemData, zjhmObj.val(), zjhmObj.isBlank(), zjhmObj);
        };
    });
};

export const getTextColor = result => {
    switch (result) {
    case '2':
        return { color: 'red' };
    case '1':
        return { color: 'green' };
    default:
        return {};
    }
};



/**
 * 校验obj组件的val有效性（val的值是否在data中）
 */
export const checkVal = (obj, data) => {
    if (data.filter(item => item.id === obj.val()).length === 0) {
        obj.val('');
    }
};

/**
 * 根据id获取一行的数据
 * @param id 所要获取的一行的id
 * @param obj 所要的对象
 */
export const getDataById = (id, obj) => {

    const gSchemaKey = ParamsUtils.getQueryString('g_schema_key', obj.props);
    const data = $.fn.ulynlist.getData($(`#js-table${gSchemaKey}`));

    for (const line of data) {
        if (line.ID === id.toString()) {
            return line;
        }
    }
};

/**
 * 清空列
 * @param columns 所要清空的列的列名
 */
export const clearColumns = (self, columns) => {
    columns.forEach(item => {
        self.findComponent(item).val('');
    });
};

/**
 * 根据性质区划代码转换行政区划
 */
export const transXzqh = code => {
    switch (code) {
    case '350201000000':
        return '市辖区';
    case '350203000000':
        return '思明区';
    case '350205000000':
        return '海沧区';
    case '350206000000':
        return '湖里区';
    case '350211000000':
        return '集美区';
    case '350212000000':
        return '同安区';
    case '350213000000':
        return '翔安区';
    default: return '';
    }
};
export const download = downloadUrl => {
    if (document.getElementById('downloadExcelA')) {
        document.body.removeChild(document.getElementById('downloadExcelA'));
    }
    const downloadExcel = document.createElement('a');

    if (isEmpty(window.CONTEXT_PATH)) {
        getContextPath();
    }
    downloadExcel.setAttribute('href', `${window.CONTEXT_PATH + downloadUrl}`);
    downloadExcel.setAttribute('target', '_blank');
    downloadExcel.setAttribute('id', 'downloadExcelA');
    document.body.appendChild(downloadExcel);
    downloadExcel.click();
};


