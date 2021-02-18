/*
 * @(#) scurdUtil.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author wuxw
 * <br> 2019-12-17 16:37:46
 * ————————————————————————————————
 *    修改记录
 *    修改者：
 *    修改时间：
 *    修改原因：
 * ————————————————————————————————
 */

import PortalMessenger from '@share/portal-messenger';
import { ParamsUtils, UrlUtils } from '@share/scurd/block/Utils';
const portalMessenger = PortalMessenger('appiframe');

// 判断是否是网格化3.0门户
export const isZeusAppPortal = () => {
    let ngApp = '';

    try {
        ngApp = top.document.getElementsByTagName('html')[0].getAttribute('ng-app');
    } catch (e) {
        console.error('获取门户类型异常', e);
    }
    return ngApp === 'zeusAppPortal';
};

// 网格化3.0新开tab页
export const zeusPortalOpen = (url, key, label) => {
    //kye不能为空，为空时使用[label]_rand作为key
    const id = key || label+"_rand";
    portalMessenger.openTab({
        app_id: id,
        key: id,
        label,
        url
    });
};

// 跳转链接 跳转在线表单路由
export const openScurdUrl = (gId, forwardSchemaKey, backSchemaKey, toPageFunc, pageType, extraParam) => {
    const scurdUrl = ParamsUtils.getUrl(
        `#${pageType}`,
        { g_id: gId, g_schema_key: forwardSchemaKey, g_back_schema_key: backSchemaKey, ...extraParam },
        null,
        null,
        this.props
    );

    toPageFunc(pageType, UrlUtils.getUrlParamComponentPass(scurdUrl));
};

/**
 * 跳转链接 自动识别网格化3.0门户和网格化2.0门户
 * @param tabUrl
 * @param tabLabel
 * @param gId
 * @param forwardSchemaKey
 * @param backSchemaKey
 * @param toPageFunc
 * @param pageType
 * @param extraParam
 */
export const openTabOrScurd = (tabUrl, tabLabel, gId, forwardSchemaKey, backSchemaKey, toPageFunc, pageType, extraParam) => {
    if (isZeusAppPortal()) {
        zeusPortalOpen(tabUrl, gId, tabLabel);
    } else {
        openScurdUrl(gId, forwardSchemaKey, backSchemaKey, toPageFunc, pageType, extraParam);
    }
};

// 网格化3.0tab打开或者默认function方法打开
export const openTabOrFunc = (tabUrl, tabLabel, gId, defaultOpenFunc) => {
    if (isZeusAppPortal()) {
        zeusPortalOpen(tabUrl, gId, tabLabel);
    } else {
        defaultOpenFunc && defaultOpenFunc();
    }
};

// 新开tab页
export const openTab = (tabUrl, tabLabel, gId) => {
    zeusPortalOpen(tabUrl, gId, tabLabel);
};

export const openHrefUrl = url => {
    return () => {
        window.location.href = url;
    };
};

// 获取列表当前行数据
export const getCurrentLineData = (id, gSchemaKey) => {
    const data = $.fn.ulynlist.getData($(`#js-table${gSchemaKey}`)) || [];

    return data.filter(item => `${item.ID}` === `${id}`)[0];
};

// 刷新列表
export const refreshList = gSchemaKey => {
    return () => {
        $.fn.ulynlist.refresh($(`#js-table${gSchemaKey}`));
    };
};


