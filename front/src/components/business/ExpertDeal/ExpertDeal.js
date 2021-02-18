/*
 * @(#) ExpertDeal.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-11-04 14:48:57
 */

import React from 'react';
import {getComponents, FormState} from '@share/shareui-form';
import {Panel,Button} from '@share/shareui';
const {Form,Row, RadioGroup,Textarea} = getComponents();
import {formaterOptions} from "../../../utils/util";
import {throttle} from "../../../utils/util";

class ExpertDeal extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            formStateExpert:new FormState(
                {
                    codeKey:'',
                    codeLabel:''
                },
                (form3,callback) => this.setState({form3},callback)
            ),
        };
        this.onSubmit = throttle(this.onSubmit)
    }

    initForm = (props)=>{
        let {itemInfo} = props;
        let data = {...itemInfo};
        let {formStateExpert} = this.state;
        formStateExpert.setFieldValue('codeKey',data.codeKey || '');
        formStateExpert.setFieldValue('codeLabel',data.codeLabel || '');
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
    onSubmit = async() => {
        let {formStateExpert} = this.state;
        let {submitExpert} = this.props;
        let formState = formStateExpert
        let data = {...formState.formData};
        let val = await formState.valid();
        let ele = val.every(item => item == true);
        if(ele){
            submitExpert && submitExpert(data);
        }else{
            console.log("校验未通过");
            return false;
        }
    }
    render(){
        let {dpOpinion,type} = this.props;
        let materialOption = formaterOptions(dpOpinion,'label')
        let {formStateExpert} = this.state;
        let materialStatus = formStateExpert.getFieldValue('codeKey');
        let disabled = type=='add'?false:true;
        return (
            <div className="evaluation-deal expert-deal">
                    <div className="allocation-add">
                        <Panel bsStyle="primary">
                            <Panel.Head title="材料复核">
                            </Panel.Head>
                            <Panel.Body full>
                                <Form formState={formStateExpert}>
                                    <Row>
                                        <RadioGroup
                                            required={true}
                                            rule={"required"}
                                            field={'codeKey'}
                                            label={'材料状态'}
                                            options={materialOption}
                                            disabled={disabled}
                                            onChange={(e)=>{
                                                let v = e.target.value;
                                                let isElse = v=='P04'?true:false;
                                                formStateExpert.setFieldValue('codeKey',v);
                                                if(isElse){
                                                    formStateExpert.setFieldValue('codeLabel','');
                                                }else{
                                                    formStateExpert.setFieldValue('codeLabel',e.option.label);
                                                }
                                            }}
                                        />
                                    </Row>
                                    {
                                        materialStatus=='P04' &&  <Row>
                                        <Textarea
                                            required={true}
                                            rule={"required"}
                                            field={'codeLabel'}
                                            label={'复核说明'}
                                            autoHeight
                                            disabled={disabled}
                                            maxLength={100}
                                        />
                                        </Row>
                                    }

                                </Form>
                                {
                                    type=='add' &&  <div className="btns-con">
                                        <Button bsStyle="primary" onClick={this.onSubmit}>保存点评</Button>
                                    </div>
                                }

                            </Panel.Body>
                        </Panel>
                    </div>
            </div>

        )
    }
}
export default ExpertDeal;