/*
 * @(#) util.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author xierx
 * <br> 2019-02-26 11:11:00
 */

export const isEmpty = function (value) {
    return value === '' || typeof value === 'undefined' || value === null;
};
export const isNotEmpty = function(value){
    return !isEmpty(value);
};
