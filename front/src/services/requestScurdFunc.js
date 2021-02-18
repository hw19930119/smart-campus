/*
 * @(#) requestScurdFunc.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-11-05 15:50:21
 */

import {requestSchemaService} from "../utils/requestSchema";
import {api} from "./serviceApi";

export function getCommentByExpert(bussinessKey){
    let {serviceName,methodName} = api.getCommentByExpert;
    return requestSchemaService(serviceName,methodName,{bussinessKey},false,false,false)
}
export function saveDpOpinionFunc(params){
    let {serviceName,methodName} = api.saveDpOpinion;
    return requestSchemaService(serviceName,methodName,params,false,true,false)
}
export function returnBaseInfoList(params){
    let {serviceName,methodName} = api.returnBaseInfoList;
    return requestSchemaService(serviceName,methodName,params,true,true,true)
}