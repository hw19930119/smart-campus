/** 
* 基础 - 行政区划获取服务 
* xzqhQgService - e8d2b90e760a49c2997fe14b8372c1b2 
*/
"use strict";
(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(["eos"],factory);
    } else {
        factory(eos);
    }
}(function (eos) {

    var APP_ID = "auth",SERVICE_ID = "xzqhQgService";

    eos.registerService(APP_ID,SERVICE_ID)
        .registerMethod("getXzqh","1.0",["baseXzqh","level"])
        .registerMethod("getQgCityLevel","1.0",[]);

    return eos[APP_ID][SERVICE_ID];
}));