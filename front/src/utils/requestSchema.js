/*
 * @(#) myService.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-11-05 15:50:48
 */

import {Spin} from '@share/shareui';
import {SchemaService} from "@share/scurd/block/SchemaService";
export function requestSchemaService(serviceName,methodName,params,successTip,errorTip,loading){
    return new Promise((resolve,reject)=>{
        loading && Spin.show(loading)
        SchemaService.callService(
            serviceName,
            methodName,
            [{'name':'jsonObject','data':params}]).then(res=>{
            loading && Spin.hide();
            let result = JSON.parse(res);
            if(result.status){
                let {msg,status} = result.data;
                if(status){
                    resolve(result.data);
                    successTip && window.success(result.data.msg || successTip)
                }else{
                    reject(msg || '请求失败');
                    errorTip && window.error(result.data.msg || errorTip);
                }
            }else{
                errorTip && window.error(result.msg || errorTip);
                reject(result.msg || errorTip);
            }
        }).catch(e=>{
            loading && Spin.hide();
            errorTip && window.error('系统内部异常');
        })
    })
}