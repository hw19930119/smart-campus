/*
 * @(#) requestFunc.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author lihui
 * <br> 2019-12-04 16:16:05
 */
import request from "../utils/request";
import {api} from "./serviceApi";
// import {schemaCallService} from "./requestService";

//获取用户权限
export function getMenusByLoginRoleId(){
    return request(`${api.getMenusByLoginRoleId}`,{method:"GET"},true);
}
//获取登录用户信息
export function getLoginUser(){
    return request(`${api.getLoginUser}`,{body:{},method:"POST"},true);
}
//获取基础文件列表
export function loadDeclareInit(declareId){
    return request(`${api.loadDeclareInit}?declareId=${declareId}`,{method:"GET"},true);
}
export function findManagerFileList(templateId){
    return request(`${api.findManagerFileList}?templateId=${templateId}`,{method:"GET"},true);
}
export function changeCategory(params){
    return request(api.changeCategory,{body:params,method:"POST"},true);
}
export function deleteCategory(params){
    return request(api.deleteCategory,{body:params,method:"POST"},true);
}
//从controller获取业务DM
export function loadSmartDm(params){
    return request(api.loadSmartDm,{body:params,method:"POST"},true);
}
//保存指标
export function save(params){
    return request(api.save,{body:params,method:"POST"},true);
}
//申报流程提交审核
export function submitDeclare(declareId){
    return request(api.submitDeclare,{body:{declareId:declareId},method:"POST"},true);
}
//申报流程提交审核
export function revokeDeclaration(declareId){
    return request(api.revokeDeclaration,{body:{declareId:declareId},method:"POST"},true);
}
//认证提交信息
export function submitAttestation(ApplyLoginUser){ //参数对象
    return request(api.submitAttestation,{body:ApplyLoginUser,method:"POST"},true);
}
//撤销认证
export function revokeAttestation(ApplyLoginUser){ //参数对象
    return request(api.revokeAttestation,{body:ApplyLoginUser,method:"POST"},true);
}

//申请段预览那个树形结构的接口
export function getTreePreview(schoolId,businessKey,isShenBao,isExpertCommentPage){
    return request(`${api.getTreePreview}?declareId=${schoolId}&piId=${businessKey}&isShenBao=${isShenBao}&isExpertCommentPage=${isExpertCommentPage || ""}`,{method:"GET"},true);
}
//配置段预览那个树形结构的接口
export function getTreePreviewByTemplateId(templateId){
    return request(`${api.getTreePreviewByTemplateId}?templateId=${templateId}`,{method:"GET"},true);
}
//配置段新增标签
export function addLable(name){
    return request(`${api.addLable}?name=${name}`,{method:"GET"},true);
}
export function queryConfigTree(templateId){
    return request(`${api.queryConfigTree}?templateId=${templateId}`,{method:"GET"},true);
}
//申报流程提交审核
export function saveCommentConfig (params){
    return request(api.saveCommentConfig,{body:{...params},method:"POST"},true);
}
//申报流程提交审核
export function queryExpertField (templateId,expertId){
    return request(`${api.queryExpertField}?templateId=${templateId}&expertId=${expertId}`,true);
}
// export function saveDpOpinion(params) {
//     let {serviceName,methodName} = api.saveDpOpinion;
//     return schemaCallService(serviceName,methodName,params,false,true)
// }