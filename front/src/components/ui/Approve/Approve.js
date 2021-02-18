/*
 * @(#) Approve.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author lihui
 * <br> 2020-08-12 11:09:22
 */
import React from "react";
import { Form,RadioGroup,Textarea,Button} from '@share/shareui';
import {SchemaService} from "@share/scurd/block/SchemaService"
import {api} from "../../../services/serviceApi";
import "./index.scss";
import BaseComponent from '../../BaseComponent';
import {throttle} from "../../../utils/util";

class Approve extends BaseComponent{
    constructor(){
        super();
        this.state = {
            state: true,
            option:"",
            show:false,
            message:""
        }
        this.approve = throttle(this.approve)
    }
    componentDidMount(){
        let {serviceName,methodName} = api.queryUnitCodeExitsOtherUser;
        let {id} = this.props;
        let this_ = this;
        SchemaService.callService(
            serviceName,
            methodName,
            [{name:"data",data:{id}}]
        ).then(function (res) {
            res = JSON.parse(res);
            console.log(res);
            if(res && res.status){
                if(res.data.status){
                    this_.setState({message:res.data.msg});
                }
            }else{
                alert(res.msg);
            }
        })
    }
    cancel = () => {
        this.props.hideModal();
    };
    approve = async () => {
        let {state,option,message} = this.state;
        let {g_schema_key} = this.props;
        let {serviceName,methodName} = api.auditApplyUser;
        let {id} = this.props;
        let this_ = this;

        if(!state && option == ""){
            this.setState({show:true});
            return;
        }
        if(message != ''){
            let flag = await window.confirms(`${message}，是否确定覆盖？`);
            if(!flag) return;
        }
        SchemaService.callService(
            serviceName,
            methodName,
            [{name:"data",data:{state,option,id}}]
        ).then(function (res) {
            if(res && res.status == "1401"){ //登录失效提示
                alert(res.message);
                return;
            }
            res = JSON.parse(res);
            if(res && res.status){
                if(res.data.status){
                    //成功
                    success(res.data.msg);
                    $.fn.ulynlist.refresh($(`#js-table${g_schema_key}`));
                    this_.cancel();
                }else{
                    //失败
                    alert(res.data.msg);
                    return
                }
            }else{
                alert(res.msg || "")
            }
        })
    };
    render(){
        let {state,option,show,message} = this.state;
        return (
            <div className="approve">
                {
                    message && <div className="message-con">温馨提示：{message}，是否确定覆盖？</div>
                }
                <Form pageType="editPage">
                    <Form.Table>
                        <Form.Tr>
                            <Form.Label required>审批结果</Form.Label>
                            <Form.Content>
                                <RadioGroup
                                    value={state}
                                    options={[
                                        {
                                            label: '审核通过',
                                            value: true
                                        },
                                        {
                                            label: '审核不通过',
                                            value: false
                                        }
                                    ]}
                                    onChange={(value) => {
                                        this.setState({state:value});
                                    }}
                                    className="customClass"
                                />
                            </Form.Content>
                        </Form.Tr>
                        <Form.Tr>
                            <Form.Label required={!state}>审批意见</Form.Label>
                            <Form.Content>
                                <Textarea autoHeight
                                          onChange={e =>
                                          {
                                              this.setState({option: e.target.value,show:false});
                                          }}
                                          value={option}
                                          limit
                                          maxLength={150}
                                          rows="4"
                                />
                                {
                                    show && <span style={{color:"#f30"}}>请输入审批意见</span>
                                }
                            </Form.Content>
                        </Form.Tr>
                    </Form.Table>
                </Form>
                <div className="but-box">
                    <Button type="button" bsStyle="primary" onClick={this.approve}>确定</Button>
                    <Button type="button" onClick={this.cancel}>取消</Button>
                </div>
            </div>
        )
    }
}
export default Approve;
