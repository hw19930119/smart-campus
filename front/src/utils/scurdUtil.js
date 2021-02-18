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
    portalMessenger.openTab({
        app_id: key,
        key,
        label,
        url
    });
};

// 跳转链接 跳转在线表单路由
export const openScurdUrl = (gId, forwardSchemaKey, backSchemaKey, toPageFunc, pageType, extraParam, props) => {
    const scurdUrl = ParamsUtils.getUrl(
        `#${pageType}`,
        { g_id: gId, g_schema_key: forwardSchemaKey, g_back_schema_key: backSchemaKey, ...extraParam },
        null,
        null,
        props
    );

    toPageFunc(pageType, UrlUtils.getUrlParamComponentPass(scurdUrl));
};

// 跳转链接 自动识别网格化3.0门户和网格化2.0门户
export const openTabOrScurd = (tabUrl, tabLabel, gId, forwardSchemaKey, backSchemaKey, toPageFunc, pageType, extraParam, props) => {
    if (isZeusAppPortal()) {
        zeusPortalOpen(tabUrl, gId, tabLabel);
    } else {
        openScurdUrl(gId, forwardSchemaKey, backSchemaKey, toPageFunc, pageType, extraParam, props);
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

export const openTab = (tabUrl, tabLabel, gId) => {
    zeusPortalOpen(tabUrl, gId, tabLabel);
};
