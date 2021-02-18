import React, { Component } from 'react';
import FormRender from '@/components/business/FormRender';
import {Button} from '@share/shareui';
import {getJSONSchema,dealNumber,validateScore} from '../../../utils/util';
import {save} from '@/services/requestFunc';
import {SchemaService} from "@share/scurd/block/SchemaService";
import './FormList.scss'
import store from 'store';
import EvaluationDeal from '../EvaluationDeal/EvaluationDeal';
import {api} from "../../../services/serviceApi";
import ExpertDeal from '../ExpertDeal/ExpertDeal';
import {throttle} from "../../../utils/util";
import AddSupFiles from '@/components/business/AddSupFiles/AddSupFiles';
class FormList extends Component {

    constructor(props) {
        super(props);
        //初始化状态 */
        this.state = {
            formData: {},
            propsSchema:{},
            validateArr: [],
            showValidate: false,
            data: this.props.data || {},
            itemInfo:{},
            itemReadOnly:false
        };
        this.onSubmit = throttle(this.onSubmit)

    }


    componentDidMount(){
        this.getFormRenderJSONSchema();
    }

    componentWillReceiveProps(nextProps){
        let {page,refreshData,data} = this.props;
        let nextData = nextProps.data;
        if(data.key !== nextData.key){
            this.setState({data:nextProps.data},function () {
                this.getFormRenderJSONSchema();
                page=='audit' && refreshData();
            })
        }
        if(page=='expertAudit' && (JSON.stringify(nextProps.data)!=JSON.stringify(this.props.data))){
            this.setState({data:nextProps.data},function () {
                this.getFormRenderJSONSchema();
            })
        }
    }
    // 获取JSONSchema
    getFormRenderJSONSchema = async() => {
        let {data} = this.state;
        const { properties, formData } = getJSONSchema([data]);
        let results =  await this.queryAuditDetail(data);
        this.setState({
            itemReadOnly:(data.ifEdit=='edit' || data.ifEdit==null)? false:true,
            propsSchema:{ type: 'object', properties },
            formData,
            itemInfo:results.itemInfo,
            scoreRecord:results.scoreRecord,
            scoreTitleLabel:results.scoreTitleLabel
        })

    };

    queryAuditDetail = async (dataItem)=>{
        let {page,businessInfo} =  this.props;
        let {defaultValue=[]} = dataItem;
        let defaultVal = defaultValue && defaultValue.length>0?defaultValue[0]:{};
        let {zpScore='',message='',supFiles,codeKey,codeLabel} = defaultVal;
        let itemInfo = {
            key:dataItem.key,
            zpScore:dataItem.state=='0'?dealNumber(zpScore):'',
            message:message,
            codeKey,
            codeLabel,
            supFiles:supFiles?JSON.parse(supFiles):[],
            canAddFile:dataItem.ifEdit=='giveback' || dataItem.ifEdit=='givebackChanged'

        },scoreRecord=[],scoreTitleLabelStr='评分';
        if(businessInfo){
            let {serviceName,methodName} = api.queryAuditDetail;
            let params = {
                piId:businessInfo.businessKey,
                bussinessKey:businessInfo.pcId,
                categoryId:dataItem.key,
                onlyGiveBack:page=='apply'?true:false
            }
            await SchemaService.callService(
                serviceName,
                methodName,
                [{'name':'jsonObject','data':params}]
            ).then((res) => {
                let resDara  = JSON.parse(res);
                if(resDara.status){
                    let {myScore,list,scoreTitleLabel} = resDara.data;
                    if(page=='audit' || page=='slzx'){
                        myScore = myScore || {};
                        itemInfo = {
                            ...itemInfo,
                            auditScore:myScore.zpScore,
                            opinion:myScore.opinion || '',
                            giveBack:myScore.giveBack || '0'
                        };
                        scoreTitleLabelStr=scoreTitleLabel
                    }
                    scoreRecord = list;
                }
            });
        }
        return {
            itemInfo,
            scoreRecord,
            scoreTitleLabel:scoreTitleLabelStr
        }
    }


    onSubmit = (data)=>{
        let {page} = this.props;
        if(page=='apply'){
            this.submitSelfScore(data)
        }else{
            this.submitAuditScore(data)
        }
    }
    //申报段自评分保存
    submitSelfScore = () => {
        // validateArr 是校验判断的数组，validateArr 长度为 0 代表校验全部通过
        let {validateArr}= this.state;
        if (validateArr.length > 0) {
            this.setState({
                showValidate:true
            });
        } else {
            this.setState({
                showValidate:false
            },()=>{
                this.refs.evaluationDeal.getValue();
            });

        }
    };
    // 审核端 评分处理保存
    submitAuditScore = (data)=>{
        this.refs.evaluationDeal.getValue();
    }
    // 保存指标
    submitScore = (info)=>{
        let that = this;
        let {data={},formData}= this.state;
        let {page,businessInfo={}} = this.props;
        let {score,message} = info;
        console.log("data====",data)
        if(validateScore(score,page)){
            let initScore = Number(data.score);
            if(initScore < Number(score)){
                window.error("自评得分不能超过项目评分");
                return;
            }
            let newFormData = JSON.parse(JSON.stringify(formData))
            let key = data.key;
            if(page=='apply'){   //申报端
                let files = this.refs.addSupFiles.getFile();
                if(data.ifEdit=='giveback' && files.length == 0){
                    error('请上传补充材料');
                    return;
                }
                let _g_id = store.get('_g_id') || '';
                newFormData[key].score = score;
                newFormData[key].message = message;
                newFormData[key].supFiles = files;
                let params = {
                    ...newFormData,
                    schoolId:_g_id,
                }
                save(params).then(res=>{
                    if(res.result){
                        that.props.refreshData(key)
                    }else{
                        error(res.message||'保存失败')
                    }
                })
            } else {         //审核端
                let params = {
                    ...info,
                    id:businessInfo.businessKey,
                    bussinessKey:businessInfo.pcId,
                    categoryId:key,
                    tongyiTuihuiPage:page=='slzx'?'1':'0',
                    // opinion:opinion
                    score:page=='slzx'?'0':info.score
                }
                let {serviceName,methodName} = api.commitAuditScore;
                SchemaService.callService(
                    serviceName,
                    methodName,
                    [{'name':'jsonObject','data':params}]
                ).then((res) => {
                    let resDara  = JSON.parse(res);
                    if(resDara && resDara.status == true){
                        let {msg,status} = resDara.data;
                        if(status){
                            that.getFormRenderJSONSchema();
                            that.props.refreshData(key)
                        }else {
                            error(msg||'保存失败')
                        }
                    }else{
                        error(res.msg||'保存失败')
                    }
                });

            }

        }
    }
    deleteRecord = async()=>{
        let that = this;
        let {data}= this.state;
        let {businessInfo={}} = this.props;
        let params = {
            id:businessInfo.businessKey,
            bussinessKey:businessInfo.pcId,
            categoryId:data.key,
        }
        let {serviceName,methodName} = api.cleanCategoryIdScore;
        await SchemaService.callService(
            serviceName,
            methodName,
            [{'name':'jsonObject','data':params}]
        ).then((res) => {
            let resDara  = JSON.parse(res);
            if(resDara && resDara.status == true){
                let {msg,status} = resDara.data;
                if(status){
                    success('删除记录成功！');
                    that.getFormRenderJSONSchema();
                    setTimeout(()=>{
                        that.props.refreshData(data.key)
                    },30)
                }else {
                    error(msg||'删除失败')
                }
            }else{
                error(res.msg||'删除失败')
            }
        });
    }

    render() {
        let {propsSchema,formData,showValidate,itemInfo,scoreRecord,itemReadOnly,data,scoreTitleLabel} = this.state;
        let {readOnly=false,page,type='',dpOpinion} = this.props;
        let isZb = JSON.stringify(data) == '{}' ? false :true;
        let onlyRead = itemReadOnly || readOnly;
        console.log("itemInfo",formData,readOnly)
        return (
            <div className="form-list">
                <FormRender
                    readOnly={onlyRead}
                    propsSchema={propsSchema}
                    formData={formData}
                    onChange={e => this.setState({ formData: e })}
                    showValidate={showValidate}
                    displayType="row"
                    labelWidth={150}
                    showDescIcon
                    onValidate={e => this.setState({ validateArr: e })}
                />
                {
                    isZb  && <React.Fragment>
                        <EvaluationDeal itemInfo={itemInfo} scoreTitleLabel={scoreTitleLabel} type={type} scoreRecord={scoreRecord} page={page} readOnly={onlyRead} submitScore={this.submitScore} ref="evaluationDeal"></EvaluationDeal>

                        <AddSupFiles itemInfo={itemInfo} ref="addSupFiles" page={page}></AddSupFiles>

                        {
                            page == 'apply' && itemInfo.canAddFile &&
                            <React.Fragment>

                                <div className="btns-con">
                                    <Button bsStyle="primary" onClick={this.onSubmit}>保存</Button>
                                </div>
                            </React.Fragment>
                        }
                        {
                            ( !onlyRead || page=='audit' || page=='slzx') && type!='detail' &&
                            <div className="btns-con">
                                <Button bsStyle="primary" onClick={this.onSubmit}>{page=='slzx'?'保存':'保存评分'}</Button>
                                {
                                    page=='audit' && scoreRecord && scoreRecord.length >0 &&  <Button bsStyle="default" onClick={this.deleteRecord}>删除评价</Button>
                                }

                            </div>
                        }
                    </React.Fragment>
                }
                {
                    page=='expertAudit' && <ExpertDeal itemInfo={itemInfo} type={type} submitExpert={(info)=>this.props.submitExpert(info)} dpOpinion={dpOpinion}></ExpertDeal>
                }
            </div>
        )
    }
};
export default  FormList;