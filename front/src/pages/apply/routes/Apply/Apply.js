import React, {Fragment} from 'react';
import {Button} from '@share/shareui';
import BaseComponent from '../../../../components/BaseComponent';
import Progress from "../../../../components/ui/Progress/Progress";
import Notice from "../../../../components/ui/Notice/index";
import BasicInfo from "../../../../components/ui/BasicInfo/index";
import SelfEvaluation from "../../../../components/ui/SelfEvaluation/index";
import Commit from "../../../../components/ui/Commit/index";
import CommitSuccess from "../../../../components/ui/CommitSuccess/index";
import '../Home.scss';
import {loadDeclareInit, submitDeclare} from '@/services/requestFunc';
import {getDataFromStore, removeByName, setData} from "../../../../utils/StoreUtil";
import {api} from ".././../../../services/serviceApi";
import {SchemaService} from "@share/scurd/block/SchemaService";
import {columnsEvalution} from "../../../../utils/code";
import ModalBox from '../../../../components/ui/ModalBox/ModalBox';
import EvaluationDetail from '../../../../components/ui/EvaluationDetail/EvaluationDetail';

class Apply extends BaseComponent {
    constructor(props) {
        super(props);
        let {step = ''} = this.props.match.params;
        let stepArr = step.split('-');
        let isDetail = stepArr.length > 1 ? true : false; //长度大于1为真，表示查看申报详情
        this.state = {
            progress: parseInt(step) || 0, /* 目前进度条*/
            isDetail, //是否为查看详情
            treeData: {},
            g_id: '',
            pcNo: '',
            message: '',
            isChecked: false,
            result: {},
            evaluationModal: false,
            pcState: '',
            evaluationDetailData:{}
        }

    }

    async componentDidMount() {
        let {isDetail,progress} = this.state;
        !isDetail && this.getData();
        progress == 1 && this.getNodeLastNoPass();
    }

    getData = () => {
        let g_id = getDataFromStore('_g_id') || '';
        loadDeclareInit(g_id).then(res => {
            if (res) {
                this.setState({
                                  treeData: res.treeData,
                                  g_id: res.g_id || '',
                                  pcNo: res.pcNo || '',
                                  state: res.state || '',
                                  pcState: res.pcState || ''
                              })
            }
        }).catch(error => {
            this.setState({
                              message: error.message
                          });
            window.info(error.message);
        })
    }
    //详情状态获取最后一条审核结果信息
    getNodeLastNoPass = () => {
        let _this = this;
        let g_id = getDataFromStore('_g_id') || '';
        if (g_id) {
            let {serviceName, methodName} = api["getNodeLastNoPass"];
            SchemaService.callService(
                serviceName,
                methodName,
                [{"name": "businessKey", "data": g_id}]
            ).then(function (res) {
                       res = JSON.parse(res);
                       if (res && res.status && res.data.result) {
                           _this.setState({result: res.data.result})
                       }
                   }
            )
        }

    }

    //重新刷新页面
    componentWillReceiveProps(nextProps) {
        let {step = ''} = nextProps.match.params;
        let {progress} = this.state;
        if (parseInt(step) !== progress) {
            this.setState({progress: parseInt(step)})
        }
    }

    // 判断是否点击申报人承诺
    isChecked = (value) => {
        this.setState({
                          isChecked: value
                      })
    }
    //跳转页面
    onPath = (path) => {
        this.props.history.push(path)
    };

    toNext = () => {
        let {progress, g_id, isChecked} = this.state;
        let _this = this;
        if (progress == 2) {
            let status = this.refs.selfEvaluation.saveAllData();
            if (!status) {
                return;
            }
            let stepSelected = getDataFromStore('stepSelected');
            stepSelected = stepSelected ? stepSelected.concat(['3']) : ['3'];
            setData('stepSelected', stepSelected)
            _this.onPath(`/apply/${progress + 1}`)
        } else if (progress == 3) {
            if (!isChecked) {
                window.info("请先勾选“申报人承诺”再提交");
                return
            }
            let stepSelected = getDataFromStore('stepSelected'); //必须满足前面三步的操作已完成，才能提交
            // if(!stepSelected || stepSelected.indexOf('1') == -1){ //不校验第一步骤
            //     window.error("没有阅读申报须知，请退回阅读再提交");
            //     setTimeout(function () {_this.onPath(`/apply/0`)},2000);
            //     return
            // }
            if (!stepSelected || stepSelected.indexOf('2') == -1) {
                window.error("基础信息不完整，请退回核查再提交");
                setTimeout(function () {
                    _this.onPath(`/apply/1`)
                }, 2000);
                return
            }
            if (stepSelected.indexOf('3') == -1) {
                window.error("自评项目不完整，请退回核查再提交");
                setTimeout(function () {
                    _this.onPath(`/apply/02`)
                }, 2000);
                return
            }
            let _g_id = getDataFromStore('_g_id');
            let declareId = g_id && g_id != '' ? g_id : _g_id;
            submitDeclare(declareId).then(res => {
                if (res && res.code == 100) {
                    // window.success(res.message);
                    removeByName('_g_id'); //流程结束，删除g_id的缓存信息
                    _this.onPath(`/apply/${progress + 1}`);
                } else {
                    window.error(res.message);
                    return
                }
            })
        }
    }
    handleEvaluationModal = (status) => {
        this.setState({
                          evaluationModal: status
                      })
    }
    getTreeData = (res) => {
        this.setState({
                        evaluationDetailData: res
                      })
    }

    render() {
        let storeGId = getDataFromStore('_g_id') || '';
        const {isDetail, progress, treeData, g_id, pcNo, state, pcState, message, result, evaluationModal,evaluationDetailData} = this.state;
        const progressPage = {
            0: () => <Notice onPath={this.onPath} message={message}/>, /* 申报须知*/
            1: () => <BasicInfo g_id={g_id} pcNo={pcNo} state={state} pcState={pcState} result={result}/>, /*基本信息*/
            2: () => <SelfEvaluation treeData={treeData} ref="selfEvaluation" refreshData={this.getData} page={'apply'}
                                     businessInfo={{pcId: storeGId, businessKey: ''}}/>, /* 自评项目 */
            3: () => <Commit g_id={g_id} isDetail={isDetail} status={progress} isChecked={this.isChecked} page={'apply'}
                             handleEvaluationModal={this.handleEvaluationModal} getTreeData={this.getTreeData}/>, /* 提交审核*/
            4: () => <CommitSuccess onPath={this.onPath}/> /* 提交成功*/
        };

        return (
            <div className="apply-con">
                {progress !== 4 && !isDetail && <Progress progress={progress}/>}
                <div key={progress}>
                    {progressPage[progress]()}
                </div>
                {
                    isDetail ? <div className="btnBox" style={{marginTop: '-15px'}}>
                        <Button type="button" bsStyle="primary" onClick={() => this.onPath('/')}>返回列表</Button>
                    </div> : <Fragment>
                        {
                            progress == 0 || progress == 1 || progress == 4 ? null : <div className="btnBox">
                                <Button type="button" onClick={() => this.onPath(`/apply/${progress - 1}`)}>上一步</Button>
                                <Button type="button" bsStyle="primary" onClick={() => this.toNext()}>{progress == 3 ? '确认提交'
                                    : '下一步'}</Button>
                                <Button type="button" onClick={() => this.onPath('/')}>返回列表</Button>
                                {/*<Button type="button" onClick={() => this.toNext('draft')}>暂存草稿</Button>*/}
                            </div>
                        }
                    </Fragment>
                }
                {
                    evaluationModal &&
                    <ModalBox title={'评分详情'} handleModal={this.handleEvaluationModal}>
                        <EvaluationDetail data={evaluationDetailData} columns={columnsEvalution} page={'apply'}></EvaluationDetail>
                    </ModalBox>
                }

            </div>
        );
    }
}

export default Apply;
