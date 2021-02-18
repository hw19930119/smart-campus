
import React,{Fragment} from 'react';
import {Panel,Form,RadioGroup,CheckboxGroup,Textarea, Button,FormControl} from '@share/shareui';
import { Rate } from 'antd';
import './ApplyToAudit.scss';
import BaseComponent from '../../BaseComponent';
import {desc} from '../../../utils/code';
import {api} from "../../../services/serviceApi";
import {SchemaService} from "@share/scurd/block/SchemaService";
import {removeByName} from "../../../utils/StoreUtil";
import {formaterOptions,throttle,validateFhScore} from "../../../utils/util";
import AddSupFiles from '../../../components/business/AddSupFiles/AddSupFiles';
class ApplyToAudit extends BaseComponent {
    constructor(props) {
        super(props);
        this.state = {
            result:'',
            rate:'3',
            opinion:'', //审批意见,
            hasStarLevel: false, //是否显示打星行
            userValue:[],
            hasScore:false,
            avScore:''
        };
        this.toSave = throttle(this.toSave)
    }

    componentDidMount(){
        let {selectButtonVo} = this.props;
        this.setValue(selectButtonVo.options || [])
    }

    componentWillReceiveProps(nextProps,nextState){
        if(this.props.selectButtonVo !== nextProps.selectButtonVo){
            let {selectButtonVo} = nextProps;
            this.setValue(selectButtonVo.options);
            this.setExpertList(selectButtonVo.nextNodeAuditUserList)
        }
    }

    setValue = (options)=>{
        if(!options || options.length == 0) return;
        let result = options.filter(item=> item.selected == true);
        this.setState({
            result: result.length > 0 ? result[0].value : ''
        })
    }
    setExpertList = (options)=>{
        if(!options || options.length == 0) return;
        let results = [];
       options.map(item=>{
           if(item.selected == true){
               results.push({code:item.id,label:item.name})
           }
       });
        this.setState({
            userValue: results
        })
    }

    // 保存
    toSave = (schemaKey)=>{
        let {id,selectButtonVo,auditUserVo} = this.props;
        let {hasPerson,options=[]} = selectButtonVo || false;
        let {result,rate,opinion,hasStarLevel,userValue,avScore,hasScore} = this.state;
        let newRate = hasStarLevel ? rate : '';  //没有评星，则传入空
        let userList = hasPerson ? userValue : []; //没有专家，则传入空数组
        let newUserList = [],files=[];
        userList.length > 0 && userList.forEach(item=>newUserList.push(item.code));
        let {roleCode} = auditUserVo || {};
        if(options.length>0 && !result){
            error("请选择审批结果");
            return;
        }
        if(hasScore){
            files = this.refs.addSupFiles.getFile();
            if(avScore && !validateFhScore(avScore)){
                return;
            }
        }

        if(!hasStarLevel && !opinion){
            error("请输入审批意见");
            return;
        }
        if(roleCode=='zj' && opinion && opinion.length<50){
            error("审批意见不少于50字");
            return;
        }

        let {serviceName,methodName} = api["auditSubmit"];
        let params = {id,result,opinion:opinion,star:newRate,nextNodeAuditUserList:newUserList};
        if(roleCode=='slzx'){
            params = {...params,reviewMaterials:files,avScore}
        }
        SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"jsonObject","data":params}]
        ).then(function (res) {
            res = JSON.parse(res);
            if(res && res.status){
                if(res.data.result){
                    window.success(res.data.message);
                    setTimeout(function () {
                        removeByName('schoolInfo');
                        window.location.href = `/smart-campus/query.html?g_schema_key=${schemaKey}`;
                    },2000)
                }else{
                    window.error(res.data.message);
                    return
                }

            }else{
                window.alert(res.msg);
                return
            }
        })
    }

    handleChange = rate => {
        this.setState({ rate });
    };

    changeRadio = (value,option)=>{
        let {auditUserVo,starData} = this.props;
        let {roleCode} = auditUserVo || {};
        let {rate}= this.state;
        let starInfo = starData.starLevelList;
        if(roleCode=='slzx'){
            rate = starInfo[0].STAR_LEVEL;
        }
        if(roleCode=='sjyj'){
            rate = starInfo[1].STAR_LEVEL;
        }
        let flag = false;
        if(option.hasStarLevel){
            flag = true;
        }
        this.setState({
            result:value,
            option,
            hasStarLevel:flag,
            hasScore:option.hasScore,
            rate:rate,
        });
    }

    changeCheckbox = (userValue,option)=>{
        console.log('changeCheckbox-----',userValue,option);
        this.setState({
            userValue,
        });
    }

    toBack =(schemaKey)=>{
        removeByName('schoolInfo');
        window.location.href = `/smart-campus/query.html?g_schema_key=${schemaKey}`;
    }

    render() {
        let {result,rate,opinion,hasStarLevel,userValue,hasScore,avScore} = this.state;
        let {title,style,selectButtonVo,page,auditUserVo,schema} = this.props;
        //style: add审批状态，detail查看详情状态
        let status = style && style == 'add' ? true : false;
        let {hasPerson,nextNodeAuditUserList=[],options=[],disableNextNodeAuditUserSelect} = selectButtonVo || '';
        let UserList = formaterOptions(nextNodeAuditUserList) || [];
        let schemaKey = schema ? schema:page=='expert'?'VW_AUDIT_TEMPLATE_ASSIGN_FA':style && style == 'add' ? 'VW_SCF_TODO_FA' :
                            style && style == 'detail' ? 'VW_SCF_DONE' : 'VW_SCF_DONEALL_FA'

        let {roleCode} = auditUserVo || {};
        console.log("result====",result)
        return (
            <div className="audit-deal-con">
                <Panel bsStyle="primary">
                    { status && <Panel.Head title={title}/> }
                    <Panel.Body>
                        {
                            status &&
                            <Form pageType="addPage">
                                <Form.Table>
                                    <Form.Tr>
                                        <Form.Label required>审批结果</Form.Label>
                                        <Form.Content>
                                                <RadioGroup
                                                    value={result}
                                                    options={options}
                                                    onChange={(value, option) => this.changeRadio(value,option)}
                                                    disabled={item => item.disabled === true} //不可点击设置
                                                    className="customClass"
                                                />

                                        </Form.Content>
                                    </Form.Tr>
                                    {
                                        hasStarLevel &&
                                        <Form.Tr>
                                            <Form.Label required>授予星级</Form.Label>
                                            <Form.Content>
                                                <Rate tooltips={desc} onChange={this.handleChange} value={rate} />
                                                {rate ? <span className="ant-rate-text">{desc[rate - 1]}</span> : ''}
                                            </Form.Content>
                                        </Form.Tr>
                                    }
                                    {
                                        hasScore &&
                                        <React.Fragment>
                                            <Form.Tr className="slzx-score">
                                                <Form.Label>复核分数</Form.Label>
                                                <Form.Content>
                                                    <FormControl
                                                        ref='slzxScore'
                                                        value={avScore}
                                                        placeholder="请输入分数"
                                                        onChange={e => {
                                                            this.setState({
                                                                avScore:e.target.value
                                                            })
                                                        }}
                                                        type={'number'}
                                                        min={0}
                                                        max={200}
                                                        maxLength={5}
                                                    />
                                                    <span>分数区间：0-200分</span>
                                                </Form.Content>
                                            </Form.Tr>
                                            <Form.Tr className="slzx-files">
                                                <Form.Label>复核材料上传</Form.Label>
                                                <Form.Content>
                                                    <AddSupFiles itemInfo={{supFiles:[],canAddFile:true}} ref="addSupFiles" page={'apply'} maxSize={1}></AddSupFiles>
                                                </Form.Content>
                                            </Form.Tr>
                                        </React.Fragment>

                                    }
                                    {
                                        hasPerson && result == '11' &&
                                        <Form.Tr>
                                            <Form.Label required>选择专家</Form.Label>
                                            <Form.Content>
                                                <CheckboxGroup
                                                    value={userValue}
                                                    options={UserList}
                                                    onChange={(value, option) => this.changeCheckbox(value,option)}
                                                    disabled={disableNextNodeAuditUserSelect?true:item => item.disabled === true} //不可点击设置
                                                    valueKey="code"
                                                    className="customClass"
                                                />
                                            </Form.Content>
                                        </Form.Tr>
                                    }

                                    <Form.Tr>
                                        <Form.Label required={!hasStarLevel}>审批意见</Form.Label>
                                        <Form.Content>
                                        <Textarea autoHeight
                                                  placeholder={roleCode=='zj'?'请专家提交一份对于学校的评价（含特点、亮点等），不少于50字':''}
                                                  onChange={e => this.setState({opinion: e.target.value})}
                                                  value={opinion}
                                                  limit
                                                  maxLength={200}
                                                  rows="3"/>
                                        </Form.Content>
                                    </Form.Tr>
                                </Form.Table>
                            </Form>
                        }
                        <div className="audit-btn-con">
                            {
                                status ?
                                    <Fragment>
                                        <Button type="submit" bsSize="large" onClick={()=>this.toSave(schemaKey)} bsStyle="primary">保存</Button>
                                        <Button type="button" bsSize="large" onClick={() => this.toBack(schemaKey)}>取消</Button>
                                    </Fragment> :
                                    <Button type="submit" bsSize="large" onClick={() => this.toBack(schemaKey)} bsStyle="primary">返回列表</Button>
                            }
                        </div>
                    </Panel.Body>
                </Panel>

            </div>
        );
    }
}
export default ApplyToAudit;
