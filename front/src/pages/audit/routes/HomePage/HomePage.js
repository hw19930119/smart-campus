/*
 * @(#) HomePage.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author lihui
 * <br> 2020-08-17 10:25:15
 */
import React from "react";
import { Button } from '@share/shareui';
import {numFormat} from "../../../../utils/util";
import {SchemaService} from "@share/scurd/block/SchemaService"
// 引入 ECharts 主模块
import echarts from "echarts";
import { Timeline,Select } from 'antd';
const { Option } = Select;
import {api} from "../../../../services/serviceApi";
import BaseComponent from "../../../../components/BaseComponent";
import {zeusPortalOpen} from "../../../../services/user";
import {stateOption,schoolOption,stateTotalBar,schoolTotalBar} from "./options";
import HomePage01 from "../../../../assets/images/HomePage01.png"
import HomePage02 from "../../../../assets/images/HomePage02.png"
import HomePage03 from "../../../../assets/images/HomePage03.png"
import HomePage04 from "../../../../assets/images/HomePage04.png"
import HomePage05 from "../../../../assets/images/HomePage05.png"
import HomePage06 from "../../../../assets/images/HomePage06.png"
import HomePage07 from "../../../../assets/images/HomePage07.png"
import HomePage08 from "../../../../assets/images/HomePage08.png"
import HomePage09 from "../../../../assets/images/HomePage09.png"
const NOWYEAR = 2020;
let myChartState = null;
let myChartSchool = null;
let myChartStateBar = null;
let myChartSchoolBar = null;
let generator = null;
import "./HomePage.scss";
function getYearDate() {
    let date = new Date();
    let nowYear = date.getFullYear();
    let arr = [];
    while (nowYear >= NOWYEAR){
        arr.push({value:nowYear+"",label:nowYear + "年"});
        nowYear = nowYear - 1;
    }
    return arr;
}
class HomePage extends BaseComponent{
    constructor(props){
        super(props);
        let now = getYearDate()[0].value;
        this.state = {
            applyYearValue:now,
            applyBatchValue:"",
            approveYearValue:now,
            approveBatchValue:"",
            resultLeftOne:{
                alreadyAudit: 0,
                awaitAudit: 0
            },
            resultLeftTwo:{
                acceptCenter: 0,
                cityEducation: 0,
                districtEducation: 0,
                expert: 0
            },
            resultRightOne:{
                auditAll:{
                    allAudit: 0,
                    awaitAudit: 0,
                    noPassAudit: 0,
                    passAudit: 0,
                },
                auditStar: {
                    threeStar: 0,
                    oneStar: 0,
                    fourStar: 0,
                    twoStar: 0,
                    fiveStar: 0,
                }
            },
            batchNum:[], //所有pic
            pcNum1:[],    //按年份查找批次
            pcNum2:[],    //按年份查找批次
        }
    }
    //获取默认数据
    getEchartsData = () => {
        this.getEchartsApplyData();
        this.getEchartsApproveData();
    };
    *generatorFun(){
        yield this.getPch();
        yield this.getEchartsData();
    };
    //获取四个模块的数据
    getData = (index , year = "",pcNo = "") => {
        let {serviceName,methodName} = api.getHomePageServiceImpl;
        return new Promise((resolve)=>{
            SchemaService.callService(
                serviceName,
                methodName[index],
                index > 1 ? [{name:"pcNo",data:pcNo},{name:"year",data:year}] : []
            ).then(function (res) {
                if(res && res.status == "1401"){
                    error(res.message);
                    return;
                }
                res = JSON.parse(res);
                if(res.status && res.data.message == "查询成功"){
                    resolve(res.data.result);
                }else{
                    error(res.msg || res.data.message);
                }
            });
        });
    };
    //通过年份获取批次号
    getYearPc = (year) => {
        let {batchNum} = this.state;
        if(batchNum.length > 0 && year) {
            return batchNum.map((item)=>{
                return item.years == `${year}` && item;
            });
        }else {
            return batchNum;
        }
    };
    //获取批次号表码
    getPch(){
        let this_ = this;
        let {applyYearValue} = this.state;
        let {serviceName,methodName} = api.tzhxyPcServiceImpl;
        SchemaService.callService(
            serviceName,
            methodName,
            []
        ).then(function (res) {
            if(res && res.status == "1401"){
                error(res.message);
                return;
            }
            res = JSON.parse(res);
            if(res.status && res.data.message == "查询成功"){
                this_.setState({
                   batchNum:res.data.result,
                },function () {
                    let arr = this.getYearPc(applyYearValue);
                    // let {batchNum} = this.state;
                    this_.setState({
                        pcNum1:arr,
                        pcNum2:arr,
                        applyBatchValue:arr.length > 0 ? arr[0].id : "",
                        approveBatchValue:arr.length > 0 ? arr[0].id : "",
                    },function () {
                        generator.next();
                    });
                });
            }else{
                error(res.msg || res.data.message);
            }
        })
    }
    componentDidMount(){
        //饼图
        //状态统计// 学校分布统计
        myChartState = echarts.init(document.getElementById('stateTotal'));
        myChartSchool = echarts.init(document.getElementById('schoolTotal'));
        myChartState.setOption(stateOption);
        myChartSchool.setOption(schoolOption);

        //柱状图
        //状态统计// 学校分布统计
        myChartStateBar = echarts.init(document.getElementById('stateTotalBar'));
        myChartSchoolBar = echarts.init(document.getElementById('schoolTotalBar'));
        myChartStateBar.setOption(stateTotalBar);
        myChartSchoolBar.setOption(schoolTotalBar);

        //左1
        let this_ = this;
        this.getData(0).then(function (res) {
            if(res){
                this_.setState({resultLeftOne:res})
            }
        });
        //左2
        this.getData(1).then(function (res) {
            if(res){
                this_.setState({resultLeftTwo:res})
            }
        });

        generator = this.generatorFun();
        generator.next();
    }
    getEchartsApplyData = () => {
        let {applyYearValue, applyBatchValue} = this.state;
        let this_ = this;
        this.getData(2,applyYearValue,applyBatchValue).then(function (res) {
            if(res){
                this_.setState({resultRightOne:res},function () {
                    //设置echarts的optiosn
                    let {resultRightOne} = this_.state;
                    let {auditAll,auditStar} = resultRightOne;
                    let {passAudit,awaitAudit,noPassAudit} = auditAll;
                    let {oneStar,twoStar,threeStar,fourStar,fiveStar} = auditStar;
                    let StatisticsParams = {
                        series:[
                            {
                                data: [
                                    {value: passAudit, name: '审批通过'},
                                    {value: awaitAudit, name: '审批中'},
                                    {value: noPassAudit, name: '审批不通过'},
                                ]
                            }
                        ]
                    };
                    let StarParams = {
                        series:[
                            {
                                data: [
                                    {value: oneStar, name: '一星'},
                                    {value: twoStar, name: '二星'},
                                    {value: threeStar, name: '三星'},
                                    {value: fourStar, name: '四星'},
                                    {value: fiveStar, name: '五星'}
                                ],
                            }
                        ]
                    };
                    myChartState.setOption(StatisticsParams);
                    myChartSchool.setOption(StarParams);
                });
            }
        });
    };
    getEchartsApproveData = () => {
        let {approveYearValue, approveBatchValue} = this.state;
        this.getData(3,approveYearValue,approveBatchValue).then(function (res) {
            if(res){
                //设置echarts的optiosn
                let {xzqhResult,schoolTypeResult} = res;
                let {S01,S02,S03,S04,S05,S06} = schoolTypeResult;
                let idArr = ["350203","350206","350211","350205","350212","350213","350200"];
                let numArr = ["one","two","three","four","five"];
                let series = [];
                for(let i=0;i<numArr.length;i++){
                    let arr = [];
                    for(let j=0;j<idArr.length;j++){
                        arr.push(xzqhResult[idArr[j]][numArr[i]]);
                    }
                    series.push({data:arr});
                }
                let xzqhParams = {
                    series: series
                };
                let schoolParams = {
                    series: [
                        {
                            data: [S01,S02,S03,S04,S05,S06],
                        }
                    ]
                };
                myChartStateBar.setOption(xzqhParams);
                myChartSchoolBar.setOption(schoolParams);
            }
        });
    };
    hrefLink = (link,label) => {
        if(link == "ds"){
            zeusPortalOpen(`/smart-campus/query.html?g_schema_key=	VW_SCF_TODO_FA#/`,link,label);
        }else if(link == "ys"){
            zeusPortalOpen(`/smart-campus/query.html?g_schema_key=VW_SCF_DONE&PC_STATE=1#/`,link,label);
        } else{
            zeusPortalOpen(`/smart-campus/query.html?g_schema_key=VW_SCF_DONEALL_FA&nodeType=${link}#/`,link,label);
        }
    };
    render(){
        let {applyYearValue,applyBatchValue,approveYearValue,
            approveBatchValue,resultLeftOne,resultLeftTwo,pcNum1,pcNum2,
            resultRightOne,
        } = this.state;
        let {alreadyAudit,awaitAudit} = resultLeftOne;
        let {acceptCenter,cityEducation,districtEducation,expert} = resultLeftTwo;
        let {allAudit, noPassAudit, passAudit} = resultRightOne.auditAll;
        return(
            <div className="home-page">
                <div style={{width:"24%",marginRight:"15px",marginBottom:"15px"}}>
                    <div className="title">当前批次申报审核概览</div>
                    <div className="first-box">
                        <div>
                            <img src={HomePage01}/>
                            <p>待审核</p>
                            <span onClick={this.hrefLink.bind(this,"ds","待审列表")}>{numFormat(awaitAudit) || 0}</span>
                        </div>
                        <div>
                            <img src={HomePage02}/>
                            <p>已审核</p>
                            <span onClick={this.hrefLink.bind(this,"ys","已审列表")}>{numFormat(alreadyAudit) || 0}</span>
                        </div>
                    </div>
                </div>
                <div style={{width:"74%",marginBottom:"15px"}}>
                    <div className="title">
                        <div>厦门市智慧校园创建申报情况统计</div>
                        <div className="select-box">
                            <span>年份</span>
                            <div>
                                <Select
                                    value={applyYearValue}
                                    style={{ width: 110 }}
                                    allowClear
                                    onChange={(value)=>{
                                        this.setState({
                                                          applyYearValue:value ? value : "",
                                                          pcNum1:this.getYearPc(value),
                                                          applyBatchValue:""
                                        })
                                    }}
                                >
                                    {
                                        getYearDate() && getYearDate().map((item,index)=>{
                                            return(
                                                <Option  className="home-page-li" value={item.value} key={index}>{item.label}</Option>
                                            )
                                        })
                                    }
                                </Select>
                            </div>
                            <span>批次</span>
                            <div>
                                <Select
                                    value={applyBatchValue}
                                    style={{ width: 180 }}
                                    allowClear
                                    notFoundContent={"暂无批次"}
                                    onChange={(value)=>{
                                        this.setState({applyBatchValue:value ? value : ""})
                                    }}
                                >
                                    {
                                        pcNum1.length > 0 && pcNum1.map((item,index)=>{
                                            return(
                                                <Option className="home-page-li" value={item.id || ""} key={index}>{item.id || ""}</Option>
                                            )
                                        })
                                    }
                                </Select>
                            </div>
                            <Button type="button" bsStyle="primary" onClick={this.getEchartsApplyData}>查询</Button>
                        </div>
                    </div>
                    <div className="second-box">
                        <div style={{width:"20%"}}>
                            <div>
                                <img src={HomePage03}/>
                                <span>申报总计</span>
                                <p>{numFormat(allAudit) || 0}</p>
                            </div>
                            <div>
                                <img src={HomePage04}/>
                                <span>审批通过</span>
                                <p>{numFormat(passAudit) || 0}</p>
                            </div>
                            <div>
                                <img src={HomePage05}/>
                                <span>审批不通过</span>
                                <p>{numFormat(noPassAudit) || 0}</p>
                            </div>
                        </div>
                        <div style={{width:"40%"}}>
                            <div id="stateTotal" style={{height:"100%"}} />
                        </div>
                        <div style={{width:"40%"}}>
                            <div id="schoolTotal" style={{height:"100%"}} />
                        </div>
                    </div>
                </div>
                <div style={{width:"24%",marginRight:"15px",marginBottom:"15px"}}>
                    <div className="title">当前批次已审核申报进度统计</div>
                    <div className="thirdly-box">
                        <Timeline>
                            <Timeline.Item>
                                <strong>1</strong>
                                <div>
                                    <img src={HomePage06}/>
                                    <span>区教育局已初审</span>
                                </div>
                                <p onClick={this.hrefLink.bind(this,"qjyj","区教育局已初审列表")}>{numFormat(districtEducation ) || 0}</p>
                            </Timeline.Item>
                            <Timeline.Item>
                                <strong>2</strong>
                                <div>
                                    <img src={HomePage07}/>
                                    <span>专家已评审</span>
                                </div>
                                <p onClick={this.hrefLink.bind(this,"zj","专家已评审列表")}>{numFormat(expert) || 0}</p>
                            </Timeline.Item>
                            <Timeline.Item>
                                <strong>3</strong>
                                <div>
                                    <img src={HomePage08}/>
                                    <span>受理中心已复审</span>
                                </div>
                                <p onClick={this.hrefLink.bind(this,"slzx","受理中心已复审列表")}>{numFormat(acceptCenter) || 0}</p>
                            </Timeline.Item>
                            <Timeline.Item>
                                <strong>4</strong>
                                <div>
                                    <img src={HomePage09}/>
                                    <span>市教育局已终审</span>
                                </div>
                                <p onClick={this.hrefLink.bind(this,"sjyj","市教育局已终审列表")}>{numFormat(cityEducation ) || 0}</p>
                            </Timeline.Item>
                        </Timeline>
                    </div>
                </div>
                <div style={{width:"74%",marginBottom:"15px"}}>
                    <div className="title">
                        <div>审批通过学校统计概览</div>
                        <div className="select-box">
                            <span>年份</span>
                            <div>
                                <Select
                                    value={approveYearValue}
                                    style={{ width: 110 }}
                                    allowClear
                                    onChange={(value)=>{
                                        this.setState({
                                                          approveYearValue:value ? value : "",
                                                          pcNum2:this.getYearPc(value),
                                                          approveBatchValue:""
                                        })
                                    }}
                                >
                                    {
                                        getYearDate() && getYearDate().map((item,index)=>{
                                            return(
                                                <Option className="home-page-li" value={item.value} key={index}>{item.label}</Option>
                                            )
                                        })
                                    }
                                </Select>
                            </div>
                            <span>批次</span>
                            <div>
                                <Select
                                    value={approveBatchValue}
                                    style={{ width: 180 }}
                                    allowClear
                                    notFoundContent={"暂无批次"}
                                    onChange={(value)=>{
                                        this.setState({approveBatchValue:value ? value : ""})
                                    }}
                                >
                                    {
                                        pcNum2.length > 0 && pcNum2.map((item,index)=>{
                                            return(
                                                <Option className="home-page-li" value={item.id || ""} key={index}>{item.id || ""}</Option>
                                            )
                                        })
                                    }
                                </Select>
                            </div>
                            <Button type="button" bsStyle="primary" onClick={this.getEchartsApproveData}>查询</Button>
                        </div>
                    </div>
                    <div className="fourthly-box">
                        <div>
                            <div id="stateTotalBar" style={{height:"100%"}} />
                        </div>
                        <div>
                            <div id="schoolTotalBar" style={{height:"100%"}} />
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
export default HomePage;