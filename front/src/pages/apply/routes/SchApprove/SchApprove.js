/*
 * @(#) SchApprove.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author lihui
 * <br> 2020-08-10 09:57:22
 */
import React from "react";
import {Select, Button, FileUpload ,Spin,Panel,FormControl} from '@share/shareui';
import {getContextPath} from '../../../../utils/formatter';
import {getDataFromStore} from "../../../../utils/StoreUtil";
import {SchemaService} from "@share/scurd/block/SchemaService"
import FileUpdate from "../../../../components/ui/FileUpdate/FileUpdate";
import {submitAttestation} from "../../../../services/requestFunc";
import {api} from "../../../../services/serviceApi";
import "./SchApprove.scss";
import BaseComponent from "../../../../components/BaseComponent";
import {throttle} from "../../../../utils/util";

const fileType = ["jpg","pdf","doc","docx"];
let context = getContextPath();
function xzqhOption(option) {
    return option && option.map((item)=>{
        return {value:item.id,label:item.label}
    })
}
class SchApprove extends BaseComponent{
    constructor(props){
        super(props);
        let roleInfo = getDataFromStore("roleInfo") || {};
        let {status = ""} = props.match.params;
        let {xzqh,schoolName,unitCode,schoolType,material,schoolTypeName} = roleInfo.loginUser || '';
        this.state = {
            roleInfo,
            loginUser:roleInfo.loginUser,
            xzqh: xzqh || '',
            schoolName: schoolName || '',
            unitCode: unitCode || "",
            schoolType: schoolType || '',
            schoolTypeName: schoolTypeName || "",
            shcOptions:[],
            files: material ? [{
                                    url:material.split(",")[0] || "",
                                    original: material.split(",")[1].split("$$")[0] || "",
                                    fileSize: material.split("$$")[1] || "",
                                }] : [],
            status,
        }
        this.submit = throttle(this.submit)
    };
    componentDidMount(){
        let {xzqh} = this.state;
        if(!xzqh){
            return;
        }
        let this_ = this;
        this.requstSeverce(xzqh).then(function (res) {
            res = JSON.parse(res);
            if(res){
                this_.setState({shcOptions:res.data})
            }
        });
    }
    requstSeverce(value){
        return new Promise((resolve,reject)=>{
            let {serviceName,methodName} = api.getSchooleOption;
            SchemaService.callService(
                serviceName,
                methodName,
                [{name:"xzqh",data:value}]
            ).then(function (res) {
                if(res && res.status == "1401"){
                    alert(res.message);
                    return;
                }
                resolve(res);
            });
        })
    }
    removeFile = () => {
        this.setState({files:[]});
    };
    submit = () => {
        let {loginUser,xzqh,schoolName,unitCode,schoolType,schoolTypeName,files} = this.state;
        if(xzqh == "")$(".select1-hide").addClass("show");
        if(schoolName == "")$(".select2-hide").addClass("show");
        if(schoolType == "" || (schoolType == "S06" && schoolTypeName == "")) $(".select3-hide").addClass("show");
        if(files.length == 0)$(".files-hide").addClass("show");
        //获取信息点击提交
        if(xzqh == "" || schoolName == "" || schoolType == "" || files.length == 0 || schoolTypeName == ""){
            return;
        }else{
            let material = files[0].url + "," + files[0].original + "$$" + files[0].fileSize;
            let params = {...loginUser,xzqh,schoolName,unitCode,schoolType,schoolTypeName,material};
            let this_ = this;
            submitAttestation(params).then(function (res) {
                if(res && res.status){
                    this_.setState({status:1},function () {
                        this_.props.history.push("/schApprove/1");
                    })
                }else{
                    window.error(res.msg);
                    return;
                }
            });
        }
    };


    goToBack = () => {
        this.props.history.push("/");
    };
    render(){
        let {roleInfo,status,xzqh,unitCode,schoolType,schoolTypeName,files,shcOptions} = this.state;
        return(
            <div className="sch-approve">
                {/*<div className="step">*/}
                    {/*<ProgressStep*/}
                        {/*data={step}*/}
                        {/*activeIndex={0}*/}
                    {/*/>*/}
                {/*</div>*/}
                <Panel bsStyle="primary">
                    <Panel.Head title={status == 0 ? "提交授权认证材料" : "等待审核"}/>
                    <Panel.Body>
                        {
                            status == 0 ? <div className="from-box">
                                <div>
                                    <span className="label"><em>*</em>所属行政区划 : </span>
                                    <div className="select-box">
                                        <Select
                                            name="form-field-name"
                                            value={xzqh}
                                            placeholder="请选择行政区划"
                                            options={xzqhOption(roleInfo.xzqhs)}
                                            onChange={newVal => {
                                                if(!newVal){
                                                    this.setState({xzqh:"",shcOptions:[]});
                                                    return;
                                                }
                                                let this_ = this;
                                                this.requstSeverce(newVal.value).then(function (res) {
                                                    res = JSON.parse(res);
                                                    if(res){
                                                        this_.setState({shcOptions:res.data})
                                                    }
                                                });
                                                this.setState({
                                                                  xzqh: newVal.value
                                                              });
                                                $(".select1-hide").removeClass("show");
                                            }}
                                        />
                                    </div>
                                    <div className="select1-hide">请选择所属行政区划</div>
                                </div>
                                <div>
                                    <span className="label"><em>*</em>申请授权学校 : </span>
                                    <div className="select-box">
                                        <Select
                                            name="form-field-name"
                                            value={unitCode}
                                            placeholder="请首先选择行政区划"
                                            options={xzqhOption(shcOptions)}
                                            disabled={shcOptions.length == 0 ? true : false}
                                            onChange={newVal => {
                                                if(!newVal){
                                                    this.setState({
                                                                      unitCode: "",
                                                                      schoolName: "",
                                                                  });
                                                    return;
                                                }
                                                this.setState({
                                                                  unitCode: newVal.value,
                                                                  schoolName: newVal.label,
                                                              });
                                                $(".select2-hide").removeClass("show");
                                            }}
                                        />
                                    </div>
                                    <div className="select2-hide">请选择申请授权学校</div>
                                </div>
                                <div>
                                    <span className="label"><em>*</em>学校类别 : </span>
                                    <div className="select-box">
                                        <Select
                                            name="form-field-name"
                                            value={schoolType}
                                            placeholder="请选择学校类别"
                                            options={xzqhOption(roleInfo.schoolEnum)}
                                            onChange={newVal => {
                                                if(newVal){
                                                    this.setState({
                                                                      schoolType: newVal.value,
                                                                      schoolTypeName:newVal.value == "S06" ? "" : newVal.label
                                                                  });
                                                    $(".select3-hide").removeClass("show");
                                                }else{
                                                    this.setState({
                                                                      schoolType: "",
                                                                      schoolTypeName: ""
                                                                  })
                                                }
                                            }}
                                        />
                                    </div>
                                    <div className="form-Control">
                                        {
                                            schoolType == "S06" &&
                                            <FormControl
                                                type="text"
                                                value={schoolTypeName}
                                                placeholder="请输入其他的学校类别"
                                                onChange={e => {
                                                    this.setState({schoolTypeName:e.target.value})
                                                }}
                                            />
                                        }
                                    </div>
                                    <div className="select3-hide">请{schoolType == "S06" ? "输入" : "选择"}学校类别</div>
                                </div>
                                <div>
                                    <span className="label"><em>*</em>上传认证材料 : </span>
                                    <div className="select-box" style={{height:"30px"}}>
                                        <FileUpdate files={files} removeFile={this.removeFile}/>
                                        <FileUpload
                                             request={{
                                                 url: `${context}/upload`
                                             }}
                                            onComplete={({ response, status }) => {
                                                // console.log("res",response,status);
                                                if(response.status == "1401"){
                                                    error(response.message);
                                                    Spin.hide();
                                                    return;
                                                }
                                                let files = response.data[0];
                                                let size = files.fileSize;
                                                let original = files.original;
                                                let nameArr = files.type.split(".");
                                                let type = nameArr[nameArr.length - 1].toLowerCase();
                                                if(size > 20 * 1024 * 1024){
                                                    error("上传文件不能大于10M");
                                                    Spin.hide();
                                                    return;
                                                }
                                                if(!fileType.includes(type)){
                                                    error("不支持该文件类型");
                                                    Spin.hide();
                                                    return;
                                                }
                                                if(original.includes(",") || original.includes("$")){
                                                    error("文件名存在特殊符号，如逗号或者$");
                                                    Spin.hide();
                                                    return;
                                                }
                                                this.setState({
                                                    files: response.data || []
                                                });
                                                Spin.hide();
                                            }}
                                            uploadProps={{
                                                //接受文件类型，如果不传默认为接受图片：accept: '.gif,.jpg,.jpeg,.bmp,.png,.GIF,.JPG,.PNG,.BMP'
                                                accept: ".jpg,.pdf,.doc,.docx"
                                            }}
                                            onChange={e => {
                                                // 选择或更改文件后触发
                                                Spin.show('上传中...');
                                                $(".files-hide").removeClass("show");
                                            }}
                                        >
                                            <Button type="button" bsStyle="primary">点击上传文件</Button>
                                        </FileUpload>
                                    </div>
                                    <div className="files-hide">请上传认证材料</div>
                                </div>
                                <div style={{color: "#FF6600", paddingLeft: "50px",paddingTop:"70px", fontSize: "14px"}}>
                                    请上传1份清晰的盖章的学校授权账号材料（最大10M）仅支持.jpg .pdf .doc .docx的文件格式
                                </div>
                            </div> :
                            <div className="com-submit">
                                <svg t="1597126863996" className="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="10552" width="64" height="64"><path d="M512.01 1010.33c-274.852 0-498.488-223.56-498.488-498.325 0-274.77 223.642-498.335 498.488-498.335 274.842 0 498.468 223.565 498.468 498.335 0 274.765-223.626 498.325-498.468 498.325z m289.06-690.304c-13.931-13.82-45.947-21.376-59.766-7.358L442.22 611.666 282.706 452.204c-13.737-14.127-42.43-2.043-56.443 11.668-14.116 13.732-14.403 36.306-0.686 50.324l190.039 193.895c0.102 0.092 0.195 0.092 0.297 0.19 0.097 0.091 0.097 0.199 0.195 0.291 2.237 2.232 5.058 3.61 7.69 5.069 1.362 0.778 2.529 1.94 3.891 2.53a35.789 35.789 0 0 0 13.445 2.63c4.475 0 9.052-0.89 13.333-2.63 1.372-0.59 2.432-1.752 3.804-2.443 2.626-1.47 5.447-2.821 7.69-5.059 0.097-0.102 0.097-0.189 0.195-0.302 0.097-0.092 0.184-0.092 0.291-0.19l334.905-337.924c13.834-13.916 13.64-36.403-0.282-50.227z m0 0" fill="#0099dd" p-id="10553"></path></svg>
                                <div>已提交！</div>
                                <div>正在为您审核，请稍后......</div>
                            </div>
                        }
                            <div className="but-box">
                                {status == 0 && <Button onClick={this.submit} type="button" bsStyle="primary" bsSize="large">提交</Button>}
                                <Button onClick={()=>this.goToBack()} type="button" bsSize="large">{status == 0 ? "取消" : "返回列表"}</Button>
                            </div>
                    </Panel.Body>
                </Panel>

            </div>
        )
    }
}
export default SchApprove
