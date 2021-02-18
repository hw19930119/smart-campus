
import React, {Fragment} from 'react';
import {Form,Panel,Icon,Checkbox,Label} from '@share/shareui';
import { FrownOutlined} from '@ant-design/icons';
import './Commit.scss';
import BaseComponent from '../../BaseComponent';
import BasicTable from '../../../components/ui/BasicTable/BasicTable';
import JCSSTable from '../../../components/ui/JCSSTable/JCSSTable';
import ZPScore from '../../../components/ui/ZPScore/ZPScore';
import {getTreePreview} from '@/services/requestFunc';
import {api} from "../../../services/serviceApi";
import {SchemaService} from "@share/scurd/block/SchemaService";
import store from 'store';
import {chnNumChar} from '../../../utils/util';
class Commit extends BaseComponent {
    constructor(props) {
        super(props);
        this.state = {
            status: props.status ? true : false, // 存在有值表示申报端(true)，没值表示审核端(false);
            isChecked: false,
            evaluationModal: false,
            treeData: {},
            arrow: (props.type == 'audit' && props.style == 'detail') || props.isDetail ? 'si-com_angleup' : 'si-com_angledown',  //申报详情或者审核详情默认展开菜单
            arrowTitle: (props.type == 'audit' && props.style == 'detail') || props.isDetail ? '收起' : '展开',
            baseInfo: {}, //基础信息
            jcss: {}, //基础设施
            g_id: props.g_id || '',
            result: {}, //审核结果展示，有退回的情况
            reviewResult: null
        }
    }
    async componentDidMount(){
        let {g_id,type,status,isDetail,declareId,businessKey=''} = this.props;
        let _g_id = type == 'audit'? declareId:store.get('_g_id') || g_id;
        let isShenBao = status ? true : false;
        await this.getCommitInfo(_g_id);
        this.getData(_g_id,businessKey,isShenBao);
        isDetail && this.getNodeLastNoPass(_g_id);


    }

    //获取基本信息展示
    getCommitInfo = async (id)=>{
        let _this = this;
        let {serviceName,methodName} = api["queryApplyAllByBusinessKey"];
        await SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"businessKey","data":id}]
        ).then(function (res) {
            res = JSON.parse(res);
            if(res && res.status){
                let {baseInfo,jcss,reviewResult} = res.data.result;
                _this.setState({
                    baseInfo,
                    jcss,
                    reviewResult
                })

            }
        })
    }

    getData = (_g_id,businessKey,isShenBao) => {
        let {getTreeData} = this.props;
        getTreePreview(_g_id,businessKey,isShenBao).then(res => {
            if(res){
                this.setState({
                    treeData:res,
                },()=>{
                    getTreeData && getTreeData(res)
                })
            }
        })
    }
    //详情状态获取最后一条审核结果信息
    getNodeLastNoPass=(id)=>{
        console.log("getNodeLastNoPass==")
        let _this = this;
        let {serviceName,methodName} = api["getNodeLastNoPass"];
        SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"businessKey","data":id}]
        ).then(function (res) {
                res = JSON.parse(res);
                if (res && res.status && res.data.result) {
                    _this.setState({result:res.data.result})
                }
            }
        )
    }


    // 导出pdf
    toDownload = (status)=>{
        let {g_id,type,declareId} = this.props;
        let _g_id = type == 'audit'? declareId:store.get('_g_id') || g_id;
        let service = status == '1' ? 'exportJbqkPdf' : 'exportJbxxPdf';
        window.location.href = `/smart-campus/allocation/${service}?businessKey=${_g_id}`

    };

    isChecked = ()=>{
        this.setState({
            isChecked:!this.state.isChecked
        },function () {
            this.props.isChecked(this.state.isChecked)
        });

    }
    //查看自评
    handleEvaluationModal = (status)=>{
        this.setState({
            evaluationModal:status
        })
    }

    //是否收起
    changeArrow = (arrow)=>{
        let _arrow = arrow == 'si-com_angleup' ? 'si-com_angledown' : 'si-com_angleup';
        let _arrowTitle = arrow == 'si-com_angleup' ? '展开' : '收起';
        this.setState({
            arrow: _arrow,
            arrowTitle: _arrowTitle
        })

    }

    render() {
        let {baseInfo,jcss,status,isChecked,evaluationModal,arrow,arrowTitle,treeData,result,reviewResult} = this.state;
        let {isDetail,type,style,handleEvaluationModal,scoreStatus,page} = this.props;
        let schoolInfo = store.get('schoolInfo') || {}; //从缓存获取学校基本信息
        let rate = reviewResult && reviewResult.starLevel ? reviewResult.starLevel:0,rateArr = [];
        let showResult =  page == "apply" && isDetail && reviewResult && (schoolInfo.__STATE==23 || schoolInfo.__STATE==24);
        // let showResult =  page == "apply" && isDetail && reviewResult;
        for(let i=0;i<rate;i++){
            rateArr.push(i)
        }
        return (
            <Fragment>
                {/* 申报时间 */}

                <div className={`commit ${status && 'apply-commit'}`}>
                    {(isDetail || type == 'audit') &&
                    <div className="apply-time-con">
                        <Form pageType="detailPage">
                            <Form.Table>
                                <Form.Tr>
                                    <Form.Label>批次号</Form.Label>
                                    <Form.Content>
                                        <span className="textShow">{schoolInfo.PC_NO || ''}</span>
                                    </Form.Content>
                                    <Form.Label>学校</Form.Label>
                                    <Form.Content>
                                        <span className="textShow">{schoolInfo.SCHOOL_NAME || ''}</span>
                                    </Form.Content>
                                    <Form.Label>提交时间</Form.Label>
                                    <Form.Content>
                                        <span className="textShow">{schoolInfo.COMMIT_TIME || ''}</span>
                                    </Form.Content>
                                </Form.Tr>
                            </Form.Table>
                        </Form>
                    </div>}
                    {/* 驳回原因展示 */}
                    {status && Object.keys(result).length > 0 && <div className="back-reason-con">
                        <p className="reason-title"><Icon className="si si-xy_xx"/><b>{result.NODE_NAME}{result.RESULT}</b></p>
                        <div>
                            <span>{result.RESULT}理由：</span>
                            <ul>
                                <li>{result.OPINON}</li>
                            </ul>
                        </div>
                    </div>}
                    {
                        showResult &&   <Panel bsStyle="primary">
                            <Panel.Head title="评审结果">
                            </Panel.Head>
                            <Panel.Body>
                                <div className="rate-result-con">
                                    {
                                        schoolInfo.__STATE==23 ?
                                            <React.Fragment>
                                                <div className="rate-con">
                                                    {
                                                        rateArr.map((item,index)=>{
                                                            return (
                                                                <svg t="1607482719222" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3203" width="40" height="40"><path d="M575.852903 115.426402L661.092435 288.054362c10.130509 20.465674 29.675227 34.689317 52.289797 37.963825l190.433097 27.62866c56.996902 8.288598 79.7138 78.281203 38.475467 118.496253l-137.836314 134.35715c-16.372539 15.963226-23.84251 38.987109-19.954032 61.49935l32.540421 189.716799c9.721195 56.792245-49.833916 100.077146-100.793444 73.267113L545.870691 841.446188a69.491196 69.491196 0 0 0-64.67153 0l-170.376737 89.537324c-50.959528 26.810033-110.51464-16.474868-100.793444-73.267113L242.569401 667.9996c3.888478-22.512241-3.581493-45.536125-19.954032-61.49935L84.779055 472.245428c-41.238333-40.215049-18.521435-110.207655 38.475467-118.496252l190.433097-27.62866c22.61457-3.274508 42.159288-17.498151 52.289797-37.963826L451.319277 115.426402c25.479764-51.675827 99.053862-51.675827 124.533626 0z" p-id="3204" fill="#fadb14"></path></svg>
                                                            )
                                                        })
                                                    }
                                                </div>
                                                <p>恭喜贵校以{reviewResult.avScore}分的成绩，获得"{chnNumChar[rate]}星级智慧校园"称号</p>
                                            </React.Fragment>: schoolInfo.__STATE==24?
                                       <React.Fragment>
                                           <svg t="1607484292370" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4416" width="48" height="48"><path d="M512.4 959.6c247.3 0 447.8-200.5 447.8-447.8S759.7 63.9 512.4 63.9 64.6 264.4 64.6 511.8s200.5 447.8 447.8 447.8z m0-66.4c-210.7 0-381.5-170.8-381.5-381.5s170.8-381.5 381.5-381.5S893.9 301 893.9 511.7 723.1 893.2 512.4 893.2z" p-id="4417"></path><path d="M733.4 702c-123.5-123.5-321.8-126.1-442.7-5.1-12.9 13-12.9 33.9 0 46.9 13 12.9 33.9 12.9 46.9 0 94.8-94.8 251-92.7 348.9 5.1 12.6 13.3 33.6 13.8 46.9 1.2 13.3-12.6 13.8-33.6 1.2-46.9l-1.2-1.2zM304.8 432.9c0 28.6 23.2 51.8 51.8 51.8 28.6 0 51.8-23.2 51.8-51.8s-23.2-51.8-51.8-51.8c-28.6 0-51.8 23.2-51.8 51.8zM615.6 432.9c0 28.6 23.2 51.8 51.8 51.8 28.6 0 51.8-23.2 51.8-51.8v-0.1c0-28.6-23.2-51.8-51.8-51.8-28.6 0.1-51.8 23.3-51.8 51.9z" p-id="4418"></path></svg>                                            {/*<FrownOutlined />*/}
                                            <p>很遗憾，贵校未能获得"智慧校园"称号，请继续努力！</p>
                                        </React.Fragment>:null
                                    }

                                </div>
                            </Panel.Body>
                        </Panel>
                    }
                    <Panel bsStyle="primary">
                        <Panel.Head title="学校基本情况">
                            <Panel.HeadRight>
                                <Label bsStyle="primary" onClick={()=>handleEvaluationModal(true)}>查看评分详情</Label>
                                <Label bsStyle="primary" onClick={()=>this.toDownload('1')}>学校基本情况PDF下载</Label>
                            </Panel.HeadRight>
                        </Panel.Head>
                        <Panel.Body>
                            {Object.keys(baseInfo).length > 0 && <BasicTable data={baseInfo} />}
                        </Panel.Body>
                    </Panel>
                    <Panel bsStyle="primary">
                        <Panel.Head title="学校信息化建设基础设施基本信息">
                            <Panel.HeadRight>
                                <Label bsStyle="primary" onClick={()=>handleEvaluationModal(true)}>查看评分详情</Label>
                                <Label bsStyle="primary" onClick={()=>this.toDownload('2')}>学校信息化建设基础设施基本信息PDF下载</Label>
                                <span className="change-label" onClick={()=>this.changeArrow(arrow)}>{arrowTitle}<Icon className={`si ${arrow}`}/></span>
                            </Panel.HeadRight>
                        </Panel.Head>
                        <Panel.Body>
                            { arrow == 'si-com_angleup' && Object.keys(jcss).length > 0 && <JCSSTable data={jcss} />}
                        </Panel.Body>
                    </Panel>
                    <div className="progress-con zp-score-con">
                        <Panel bsStyle="primary">
                            <Panel.Head title="自评得分">
                                <Panel.HeadRight>
                                    {
                                        (scoreStatus!='2' && type == 'audit') && <Label bsStyle="primary" onClick={()=>{this.props.toScore('detail')}}>查看佐证材料</Label>
                                    }
                                    <Label bsStyle="primary" onClick={()=>handleEvaluationModal(true)}>查看评分详情</Label>
                                </Panel.HeadRight>
                            </Panel.Head>
                            <Panel.Body>
                                <ZPScore data={treeData.fieldConfig}/>
                            </Panel.Body>
                        </Panel>
                    </div>




                    {/* 申报端展示 */}
                    {
                        status && !isDetail && <div className="commit-save-info">
                            <Checkbox name="checkbox" checked={isChecked} onChange={() => this.isChecked()}>申报人承诺</Checkbox>
                            <p>1、兹保证本人提供的所有电子信息和纸质材料的内容均真实有效如果提供虚假公文、证明文件、证件等，愿意承担相应的法律责任。<br/>
                                2、兹保证以上申报情况属实，如有造假虚报，本人愿担负相应法律责任。</p>
                        </div>
                    }
                    {/*//查看评分详情模态框*/}
                    {/*{*/}
                        {/*evaluationModal &&*/}
                        {/*<ModalBox title={'评分详情'} handleModal={this.handleEvaluationModal}>*/}
                            {/*<EvaluationDetail data={treeData} columns={columnsEvalution}></EvaluationDetail>*/}
                        {/*</ModalBox>*/}
                    {/*}*/}
                </div>
            </Fragment>
        );
    }
}
export default Commit;
