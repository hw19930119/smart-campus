/*
 * @(#) StoreUtil.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author weixl
 * <br> 2018-07-04 10:46:11
 */

import store from "store";

/**
 * @author:weixl
 * @description: 保存数据
 * @date: 2018/7/4 10:38
 * @params :
 * @return :
 */
export const setData = (name, data) => {
    store.set(name, data);
};

/**
 * @author:weixl
 * @description: 获取数据
 * @date: 2018/7/4 10:39
 * @params :
 * @return :
 */
export const getDataFromStore = (name) => {
    let data = store.get(name);
    return data;
};
/**
 * @author:weixl
 * @description: 移除指定数据
 * @date: 2018/7/4 10:41
 * @params :
 * @return :
 */
export const removeByName = (name) => {
    store.remove(name);
};
/**
 * @author:weixl
 * @description: 清空所有数据
 * @date: 2018/7/4 10:40
 * @params :
 * @return :
 */
export const clearAll = () => {
    store.clearAll();
};
