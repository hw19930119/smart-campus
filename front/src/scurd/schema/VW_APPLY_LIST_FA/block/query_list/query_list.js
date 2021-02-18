/*
 * @(#) query_list.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author chenchen
 * <br> 2019-09-19:02:25
 */
import React from 'react';
import {QueryList} from '@share/scurd/block/query_list/query_list';
import {api} from "../../../../../services/serviceApi";
import {SchemaService} from "@share/scurd/block/SchemaService";
import {removeByName,setData} from "../../../../../utils/StoreUtil";
import {revokeDeclaration,loadDeclareInit} from '../../../../../services/requestFunc';
import './style.scss'

class QueryListWapp extends QueryList {

    constructor(props) {
        super(props);
        this.state = {
            ...this.state,
            hasMb:true,
            message:'',
        };
    }

    componentDidMount(){
        this.getData();
    }

    getData = () => {
        loadDeclareInit('').then(res => {
            if(res){
                this.setState({
                    hasMb:true,
                    message:''
                })
            }
        }).catch(error => {
            this.setState({
                hasMb: false,
                message:error.message
            });
        })
    }

    //返回是否刷新是否允许申报字段
    refreshSb = async ()=>{
        let data = true;
        await loadDeclareInit('').then(res => {
            if(res){
                data = res.yxsb;
            }
        })
        return data;
    }


    //创建申报
    add(){
        let {hasMb,message} = this.state;
        if(!hasMb){
            error(message);
            return;
        }
        window.location.href = `/smart-campus/apply.html#/apply/0`
    }

    //查看详情
    detail(id,item){
        removeByName('_g_id');
        removeByName('schoolInfo');
        setData('_g_id',id);
        setData('schoolInfo',item);
        window.location.href = `/smart-campus/apply.html#/apply/3-detail`
    }

    //编辑
    edit(id){
        let {hasMb,message} = this.state;
        if(!hasMb){
            error(message);
            return;
        }
        removeByName('_g_id');
        setData('_g_id',id);
        window.location.href = `/smart-campus/apply.html#/apply/1`
    }

    del(id){
        let _this = this;
        let {g_schema_key} = this.props;
        let {serviceName,methodName} = api["delBaseInfo"];
        SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"declareId","data":id}]
        ).then(function (res) {
            res = JSON.parse(res);
            if(res && res.status){
                success(res.data.message);
                _this.getData()
                $.fn.ulynlist.refresh($(`#js-table${g_schema_key}`));

            }
        })
    }
    //撤销功能
    revote(id){
        let _this = this;
        let {g_schema_key} = this.props;
        revokeDeclaration(id).then(res=>{
            if(res && res.code == 100){
                success(res.message);
                _this.getData();
                $.fn.ulynlist.refresh($(`#js-table${g_schema_key}`));
            }else{
                error(res.message)
            }
        })
    }

    //扩展状态字段
    stateFunc = (value,item)=>{
        let _class = item.__STATE == '8' ? 'warning' : //草稿
            item.__STATE == '13' || item.__STATE == '18' || item.__STATE=='25' || item.__STATE=='26' || item.__STATE=='27' || item.__STATE=='28'?'info' : //驳回
                item.__STATE == '9' || item.__STATE == '10' || item.__STATE == '14' || item.__STATE == '16' || item.__STATE == '19'|| item.__STATE == '22' ? 'success' : //已提交
                    item.__STATE == '11' || item.__STATE == '15' || item.__STATE == '17' || item.__STATE == '20' || item.__STATE == '23' ? 'primary' : //审核通过
                        item.__STATE == '12' || item.__STATE == '21' || item.__STATE == '24' ? 'danger' :  'default';//审核不通过
        return `<span class="status-span label-border label label-${_class}">${value}</span>`
    }
    clearAudit(id,item) {
        let _this = this;
        let {g_schema_key} = this.props;
        let {serviceName,methodName} = api["clearAudit"];
        SchemaService.callService(
            serviceName,
            methodName,
            [{"name":"jsonObject","data":{"bussinessKey" :id}}]
        ).then(function (res) {
            res = JSON.parse(res);
            if(res && res.status &&res.data.status){
                success(res.data.message);
                _this.getData();
                $.fn.ulynlist.refresh($(`#js-table${g_schema_key}`));
            }else{
                alert(res.data.msg);
            }
        })
    }

    async afterRender(){
        let flag = await this.refreshSb();
        if(!flag){
            $('.query-VW_APPLY_LIST_FA .panel-head .ui-list-horizontal').hide()
        }else{
            $('.query-VW_APPLY_LIST_FA .panel-head .ui-list-horizontal').show()
        }
    }
}
export default QueryListWapp;
