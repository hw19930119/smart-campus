import React from 'react';
import {getComponents, FormState} from '@share/shareui-form';
import {Panel} from '@share/shareui';
const {Form,Row, Input, RadioGroup,Textarea} = getComponents();
import {dealNumber} from "../../../utils/util";
import ReturnRecord from '../../ui/ReturnRecord/ReturnRecord';
class EvaluationDeal extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            scoreRecord:[],
            isReturn:false,
            formStateSelf:new FormState(
                {
                    score:'',
                    message:'',
                    // ...data
                },
                (form1,callback) => this.setState({form1},callback)
            ),
            formStateAudit:new FormState(
                {
                    score:'',
                    giveBack:'0',
                    opinion:''
                },
                (form2,callback) => this.setState({form2},callback)
            )
        };
    }

    initForm = (props)=>{
        let {itemInfo,page,scoreRecord} = props;
        let data = {...itemInfo};
        let {formStateSelf,formStateAudit} = this.state;
        formStateSelf.setFieldValue('score',data.zpScore || '');
        formStateSelf.setFieldValue('message',data.message || '');

        if(page=='audit' || page=='slzx'){
            let {giveBack,auditScore} = data;
            console.log("data=====",data);
            let isReturn = giveBack == '1'?true:false;
            formStateAudit.setFieldValue('score',giveBack == '1'?'0':dealNumber(auditScore));
            formStateAudit.setFieldValue('giveBack',giveBack == '1'?giveBack:'0');
            formStateAudit.setFieldValue('opinion',data.opinion || '');
            this.setState({
                isReturn,
                scoreRecord:scoreRecord,
            })
        }
        else{
            this.setState({
                scoreRecord:scoreRecord,
            })
        }
    }
    componentDidMount(){
        this.initForm(this.props)
    }
    componentWillReceiveProps (nextProps){
        let {itemInfo} = nextProps;
        if(JSON.stringify(itemInfo)!= JSON.stringify(this.props.itemInfo)){
            this.initForm(nextProps)
        }
    }
    getValue = async() => {
        let {formStateSelf,formStateAudit} = this.state;
        let {page,submitScore} = this.props;
        let formState = page=='apply'?formStateSelf:formStateAudit;
        let data = {...formState.formData};
        let val = await formState.valid();
        let ele = val.every(item => item == true);
        if(ele){
            submitScore(data);
        }else{
            console.log("校验未通过");
            return false;
        }
    }
    render(){
        let {formStateSelf,formStateAudit,isReturn,scoreRecord} = this.state;
        let {readOnly,page,scoreTitleLabel,type} = this.props;
        return (
            <div className="evaluation-deal">
                <Form formState={formStateSelf}>
                    <Row>
                        <Input
                            field={'score'}
                            label={'自评得分'}
                            required={true}
                            rule={"required"}
                            type={'number'}
                            min={0}
                            disabled={readOnly}
                        />
                    </Row>
                    <Row>
                        <Textarea
                            field={'message'}
                            label={'备注'}
                            autoHeight
                            maxLength={100}
                            limit
                            placeholder="请输入备注"
                            disabled={readOnly}
                        />
                    </Row>
                </Form>

                {
                    scoreRecord && scoreRecord.length>0 && <ReturnRecord data={scoreRecord} page={page} title="评分记录"></ReturnRecord>
                }
                {
                    page && (page == 'audit' || page == 'slzx') && type!='detail' &&
                    <React.Fragment>
                        <div className="allocation-add">
                            <Panel bsStyle="primary">
                                <Panel.Head title="评分处理">
                                </Panel.Head>
                                <Panel.Body full>
                                    <Form formState={formStateAudit}>
                                        <Row>
                                            <RadioGroup
                                                required={true}
                                                rule={"required"}
                                                field={'giveBack'}
                                                label={page=='slzx'?'是否打回':'退回补充'}
                                                options={[
                                                    { value: '1', label: '是' },
                                                    { value: '0', label: '否' },
                                                ]}
                                                onChange={(e)=>{
                                                    let v = e.target.value;
                                                    let isReturn = v=='1'?true:false;
                                                    let score = isReturn?'0':''
                                                    formStateAudit.setFieldValue('score',score);
                                                    formStateAudit.setFieldValue('opinion','');
                                                    this.setState({
                                                        isReturn
                                                    })
                                                }}
                                            />
                                        </Row>
                                        {
                                            !isReturn && page=='audit' &&  <Row>
                                                <Input
                                                    required={true}
                                                    rule={"required"}
                                                    field={'score'}
                                                    label={scoreTitleLabel}
                                                    type={'number'}
                                                    min={0}
                                                />
                                            </Row>
                                        }
                                        <Row>
                                        <Textarea
                                            required={isReturn && page=='audit'}
                                            rule={isReturn?"required":''}
                                            field={'opinion'}
                                            label={page=='slzx'?'处理说明':'评分说明'}
                                            autoHeight
                                            maxLength={100}
                                        />
                                        </Row>
                                    </Form>
                                </Panel.Body>
                            </Panel>
                        </div>
                    </React.Fragment>
                }
            </div>

        )
    }
}
export default EvaluationDeal;