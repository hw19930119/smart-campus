/*
 * @(#) api.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author lihui
 * <br> 2019-12-04 15:54:02
 */
export const api = {
    getLoginUser: "/declaration/getLoginUser",
    loadDeclareInit: "/declaration/loadDeclareInit",
    revokeAttestation:'/declaration/revokeAttestation',
    findManagerFileList:"/allocation/findBasicFileList",
    changeCategory:"/allocation/changeCategory",
    deleteCategory:"/allocation/deleteCategory",
    loadSmartDm: "/dm/loadSmartDm",
    save: "/declare/save",
    submitDeclare: "/declaration/submitDeclare",
    revokeDeclaration: "/declaration/revokeDeclaration",
    submitAttestation: "/declaration/submitAttestation",
    getMenusByLoginRoleId: "/iEduUser/getMenusByLoginRoleId",
    savePCService:{"serviceName":"tzhxyPcServiceImpl","methodName":"addOrUpdate"},
    startPCService:{"serviceName":"tzhxyPcServiceImpl","methodName":"enablePc"},
    endPCService:{"serviceName":"tzhxyPcServiceImpl","methodName":"placeOnfile"},
    copyTemplate:{"serviceName":"tzhxyTemplateServiceImpl","methodName":"copyTemp"},
    copyPc:{"serviceName":"tzhxyPcServiceImpl","methodName":"copyPc"},
    getNodeAll:{"serviceName":"pdNodeServiceImpl","methodName":"getNodeAll"},
    getSchooleOption:{"serviceName":"smartDmService","methodName":"loadShoolDm"},
    queryApplyAllByBusinessKey:{"serviceName":"tzhxyBaseInfoServiceImpl","methodName":"queryApplyAllByBusinessKey"},
    getTreePreview:"/declaration/getTreePreview",
    getTreePreviewByTemplateId:"/declaration/getTreePreviewByTemplateId",
    addLable:'/allocation/addLable',
    //用户审批
    auditApplyUser:{"serviceName":"applyUserServiceImpl","methodName":"auditApplyUser"},
    queryUnitCodeExitsOtherUser:{"serviceName":"applyUserServiceImpl","methodName":"queryUnitCodeExitsOtherUser"},
    //审批
    publishProcDef:{"serviceName":"procDefServiceImpl","methodName":"publish"},
    clearAudit:{"serviceName":"procInstServiceImpl","methodName":"clearAudit"},
    startOrRestartProcess:{"serviceName":"procInstServiceImpl","methodName":"startOrRestartProcess"},
    enterProcess:{"serviceName":"procInstServiceImpl","methodName":"enterProcess"},
    cancelProcess:{"serviceName":"procInstServiceImpl","methodName":"cancelProcess"},
    getSelectButonForAudit:{"serviceName":"procInstServiceImpl","methodName":"getSelectButonForAudit"},
    auditSubmit:{"serviceName":"procInstServiceImpl","methodName":"auditSubmit"},
    cancelOneDoneOfAgree:{"serviceName":"personTodoDoneServiceImpl","methodName":"cancelOneDoneOfAgree"},
    //评分
    commitAuditScore:{"serviceName":"scoreServiceImpl","methodName":"commitCategoryIdScore"},
    queryAuditDetail:{"serviceName":"scoreServiceImpl","methodName":"queryCategoryIdScoreDetail"},
    queryCategoryIdScoreDetail:{"serviceName":"scoreServiceImpl","methodName":"queryCategoryIdScoreDetail"},
    commitCategoryIdScore:{"serviceName":"scoreServiceImpl","methodName":"commitCategoryIdScore"},
    commitPfhz:{"serviceName":"scoreServiceImpl","methodName":"commitPfhz"},
    queryPfhzList:{"serviceName":"pfhzServiceImpl","methodName":"queryPfhzList"},
    delBaseInfo:{"serviceName":"tzhxyBaseInfoServiceImpl","methodName":"delBaseInfo"},
    cleanCategoryIdScore:{"serviceName":"scoreServiceImpl","methodName":"cleanCategoryIdScore"},
    //首页
    getHomePageServiceImpl:{"serviceName":"homePageServiceImpl","methodName":["countAuditByUser", "countAlreadyAuditByNode", "countAuditAll", "countAuditPass",]},
    //首页批次号码表接口
    tzhxyPcServiceImpl :{"serviceName":"tzhxyPcServiceImpl","methodName":"listAllPc"},
    smartDmService:{"serviceName":"smartDmService","methodName":"loadSmartDm"},
    listStarLevel:{"serviceName":"starLevelServiceImpl","methodName":"listStarLevel"},
    getNodeLastNoPass:{"serviceName":"pdNodeServiceImpl","methodName":"getNodeLastNoPass"},
    queryConfigTree:"/comment/config/queryConfigTree",
    saveCommentConfig:"/comment/config/saveCommentConfig",
    queryExpertField:"/comment/config/queryExpertField",
    queryCommentClass:{"serviceName":"tzhxyCommentServiceImpl","methodName":"queryCommentClass"},
    saveDpOpinion:{"serviceName":"tzhxyDpOpinionServiceImpl","methodName":"saveDpOpinion"},
    getCommentByExpert:{"serviceName":"tzhxyCommentServiceImpl","methodName":"getCommentByExpert"},
    returnBaseInfoList:{"serviceName":"returnBaseServiceImpl","methodName":"returnBaseInfoList"}
};