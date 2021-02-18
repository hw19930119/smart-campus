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
import ApplyProgress from '../../../../components/ui/ApplyProgress'
import Commit from '../../../../components/ui/Commit';
import ScoreRecord from "../../../../components/ui/ScoreRecord";
import ApplyToAudit from "../../../../components/ui/ApplyToAudit";
import {api} from "../../../../services/serviceApi";
import {SchemaService} from "@share/scurd/block/SchemaService";
import './Audit.scss';
import ModalBox from '../../../../components/ui/ModalBox/ModalBox';
import {findBasicFileList,submitDeclare,queryConfigTree} from '@/services/requestFunc';
import EvaluationDetail from '../../../../components/ui/EvaluationDetail/EvaluationDetail';
import {columnsEvalution} from "../../../../utils/code";
// let starData = {
//     pjf:'108.50',
//     stars:[
//         {title:'专家推荐学校星级',star:'3'},
//         {title:'受理中心授予学校星级',star:'2'},
//     ]
// }

class Audit extends BaseComponent {

    constructor(props) {
        super(props);
        let {style,businessKey,declareId,status,schema} = props.match.params || ''; //是审批还是详情style-add/detail，审批流程对应的id--businessKey，学校id--declareId

        //初始化状态 */
        this.state = {
            style,
            businessKey,
            declareId,
            status,
            schema,
            selectButtonVo:{}, //审批节点信息
            processData:[], //申报进度
            treeData:{},
            scoreModal:false,
            scoreData:[], //评分记录
            scoreStatus:'',
            starData:{}, //平均分记录
            evaluationModal:false,
            auditUserVo:{}
        };
    }

    componentDidMount(){
        let {businessKey,declareId,style} = this.state;
        style == 'add' && this.getChangeStatus(businessKey);
        style == 'add' && this.getAuditOptions(businessKey);
        this.getNodeAll(declareId);
        this.queryScoreList(declareId);
        this.getListStarLevel(declareId);
    }

    //修改展示审批中的状态
    getChangeStatus = (id)=>{
        let {serviceName,methodName} = api["enterProcess"];
        SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"jsonObject","data":{"id" :id,"varJson":null}}]
        ).then(function (res) {
            res = JSON.parse(res);
            if(res && res.status &&res.data.status){
                console.info(res.data.msg);
            }else{
                alert(res.msg);
                return
            }
        })
    };

    //获取审批结果选择项options
    getAuditOptions = ( id )=>{
        let _this = this;
        let {serviceName,methodName} = api["getSelectButonForAudit"];
        SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"jsonObject","data":{"id" :id}}]
        ).then(function (res) {
            res = res ? JSON.parse(res) : {};
            if(res && res.status && res.data){
                let {selectButonVo,auditUserVo} = res.data;
                let {scoreButton=''} = selectButonVo || {}
                _this.setState({
                    selectButtonVo:selectButonVo,
                    scoreStatus: scoreButton,
                    auditUserVo:auditUserVo
                })
            }
        })
    }

    //获取审批记录
    getNodeAll = ( id )=>{
        let _this = this;
        let {serviceName,methodName} = api["getNodeAll"];
        SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"businessKey","data":id}]
        ).then(function (res) {
            res = JSON.parse(res);
            if(res && res.status && res.data){
                let {result} = res.data || [];
                _this.setState({
                    processData:result
                })
            }
        })
    }
    //获取平均分值
    getListStarLevel = ( id )=>{
        let _this = this;
        let {serviceName,methodName} = api["listStarLevel"];
        SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"businessKey","data":id}]
        ).then(function (res) {
            res = JSON.parse(res);
            if(res && res.status && res.data){
                let {result} = res.data || {};
                _this.setState({
                    starData:result
                })
            }
        })
    }


    //获取评分项目（树）
    getTreeData = (res) => {
        this.setState({
            treeData:res
        })
    }

    // 我要评分
    toScore = (type)=>{
        let {businessKey,declareId} = this.state;
        this.props.history.push(`/auditScore/${businessKey}/${declareId}/${type}`)
        // this.handleScoreModal(true)
    }
    queryScoreList = ()=>{
        let _this = this;
        let {serviceName,methodName} = api.queryPfhzList;
        let {declareId} = this.state;
        SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"jsonObject","data":{"bussinessKey":declareId}}]
        ).then(function (res) {
            res = JSON.parse(res);
            let {data,status} = res;
            if(status && data){
                _this.setState({
                    scoreData:data.list
                })
            }

        })
    }
    //查看自评
    handleEvaluationModal = (status)=>{
        this.setState({
            evaluationModal:status
        })
    }
    toPath = (path) =>{
        this.props.history.push(path)
    }
    refreshData =()=>{
        let { declareId ,businessKey}= this.state;
        this.refs.commit.getData(declareId,businessKey,false)
    }
    render() {
        let {style,businessKey,declareId,selectButtonVo,processData,auditUserVo,evaluationModal,scoreData,scoreStatus,starData,treeData,schema} = this.state;
        return (
            <div className="audit-con">
                {/* 申报相关信息 */}
                <Commit type="audit" style={style} scoreStatus={scoreStatus} declareId={declareId} businessKey={businessKey} getTreeData={this.getTreeData} ref="commit" handleEvaluationModal={this.handleEvaluationModal} toScore={this.toScore}/>

                {/*<SelectedExpert scoreStatus={scoreStatus} data={[]}  experts={[]} title={'专家点评'} expertDetail={true}></SelectedExpert>*/}

                {/* 评分记录 */}
                <ScoreRecord title={"评分记录"}
                             style={style}
                             data={scoreData}
                             starData={starData}
                             scoreStatus={scoreStatus}
                             toScore={this.toScore}  //审核评分
                             handleEvaluationModal={this.handleEvaluationModal}
                />

                {/* 审核记录 */}
                <ApplyProgress data={processData}
                               title={"审核记录"}/>

                {/* 申报审批 */}
                <ApplyToAudit title={'申报审批'}
                              style={style}
                              id={businessKey}
                              auditUserVo={auditUserVo}
                              schema={schema}
                              starData={starData}
                              selectButtonVo={selectButtonVo}/>

                {
                    evaluationModal &&
                    <ModalBox title={'评分详情'} handleModal={this.handleEvaluationModal}>
                        <EvaluationDetail data={treeData} columns={columnsEvalution} page={'audit'}></EvaluationDetail>
                    </ModalBox>
                }

            </div>
        )
    }
};
export default  Audit;
