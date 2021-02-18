/*
 * @(#) Audit.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author liuxia
 * <br> 2020/8/3 14:36
 */
import React from 'react';
import BaseComponent from '../../../../components/BaseComponent';
import {api} from "@/services/serviceApi";
import {SchemaService} from "@share/scurd/block/SchemaService";
import {findBasicFileList,submitDeclare,getTreePreview} from '@/services/requestFunc';
import SelfEvaluation from '../../../../components/ui/SelfEvaluation/SelfEvaluation';
import './AuditScore.scss';
import {deleteLastTree,throttle} from "../../../../utils/util";
import {ButtonToolBar,Button} from '@share/shareui'

class AuditScore extends BaseComponent {

    constructor(props) {
        super(props);
        let {businessKey,declareId,type} = props.match.params || ''; //是审批还是详情style-add/detail，审批流程对应的id--businessKey，学校id--declareId
        //初始化状态 */
        this.state = {
            businessKey,
            declareId,
            type,
            treeData:{},
        };
        this.handleCommit = throttle(this.handleCommit)
    }

    componentDidMount(){
       this.getData()
    }


    getData = () => {
        let {businessKey,declareId} = this.state;
        getTreePreview(declareId,businessKey,false).then(res => {
            if(res){
                let {fieldConfig} = res;
                let data = deleteLastTree(fieldConfig);
                res.fieldConfig = data;
                this.setState({
                    treeData:res,
                })
            }
        })
    }
    handleCommit = ()=>{
        // 提交
        let status = this.refs.selfEvaluation.saveAllData();
        if(!status) return;
        let {serviceName,methodName} = api.commitPfhz;
        let {businessKey,declareId} = this.state;
        SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"jsonObject","data":{"id" :businessKey,"bussinessKey":declareId}}]
        ).then(function (res) {
            res = JSON.parse(res);
            let {data} = res;
            if(data.status){
                success(data.msg);
                history.back()
            }else{
                error(data.msg)
            }
        })
    }
    refreshData =()=>{
        this.getData()
    }
    render() {
        let {businessKey,declareId,treeData,type} = this.state;
        let {fieldConfig} = treeData;
        let page = type == 'slzx' ? 'slzx':'audit';
        return (
            <div className="audit-score">
                <SelfEvaluation treeData={treeData} readOnly={true} type={type} page={page} ref="selfEvaluation" businessInfo={{businessKey:businessKey,pcId:declareId}} refreshData={this.refreshData}></SelfEvaluation>
                <ButtonToolBar>
                    {
                        fieldConfig && fieldConfig.length>0 && type!='detail' && page=='audit' &&
                        <Button type="button" bsSize="small" onClick={this.handleCommit} bsStyle="primary">提交</Button>
                    }
                    <Button type="button" bsSize="small" onClick={() =>  history.back()}>返回</Button>
                </ButtonToolBar>

            </div>
        )
    }
};
export default  AuditScore;
