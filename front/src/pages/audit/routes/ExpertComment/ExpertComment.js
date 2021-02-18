/*
 * @(#) ExpertComment.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-11-04 11:08:10
 */
import React from 'react';
import BaseComponent from '../../../../components/BaseComponent';
import {api} from "@/services/serviceApi";
import {SchemaService} from "@share/scurd/block/SchemaService";
import {findBasicFileList,submitDeclare,getTreePreview} from '@/services/requestFunc';
import {getCommentByExpert} from "../../../../services/requestScurdFunc";
import SelfEvaluation from '../../../../components/ui/SelfEvaluation/SelfEvaluation';
import './ExpertComment.scss';
import {deleteLastTree} from "../../../../utils/util";
import {ButtonToolBar,Button} from '@share/shareui'
class ExpertComment extends BaseComponent {

    constructor(props) {
        super(props);
        let {businessKey,declareId,type} = props.match.params || ''; //是审批还是详情style-add/detail，审批流程对应的id--businessKey，学校id--declareId
        //初始化状态 */
        this.state = {
            businessKey,
            declareId,
            type,
            treeData:{},
            dpConfigUpdateTime:''
        };
    }

    componentDidMount(){
        this.getData();
        this.getDpConfigUpdateTime()
    }

    getDpConfigUpdateTime(){
        let {declareId} = this.state;
        getCommentByExpert(declareId).then(res=>{
            let dpConfigUpdateTime = res.dpConfigUpdateTime || '';
            this.setState({dpConfigUpdateTime})
        })
    }

    getData = () => {
        let {businessKey,declareId,type} = this.state;
        let isExpert = type=='add'?'yes':'expert';
        getTreePreview(declareId,businessKey,false,isExpert).then(res => {
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
    refreshData =()=>{
        this.getData()
    }
    render() {
        let {businessKey,declareId,treeData,type,dpConfigUpdateTime} = this.state;
        return (
            <div className="expert-comment">
                <SelfEvaluation treeData={treeData} readOnly={true} type={type} page={'expertAudit'} ref="selfEvaluation" businessInfo={{businessKey:businessKey,pcId:declareId}} refreshData={this.refreshData} dpConfigUpdateTime={dpConfigUpdateTime}></SelfEvaluation>
                <ButtonToolBar>
                    <Button type="button" bsSize="small" onClick={() =>  history.back()}>返回</Button>
                </ButtonToolBar>

            </div>
        )
    }
};
export default  ExpertComment;
