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
import AddPC from "../../components/AddPC/AddPC";
import {SchemaService} from "@share/scurd/block/SchemaService";
import {api} from "../../../../../services/serviceApi";
import ModalTool from "@share/shareui/es/components/ModalTool";

class QueryListWapp extends QueryList {

    constructor(props) {
        super(props);
        this.state = {
            ...this.state
        }
        let openconfirm = async function(content,bsStyle){
            return new Promise((resolve,reject) =>{
                new ModalTool({
                        title: '提示',
                        bsStyle: bsStyle?bsStyle:'warning',
                        closeBtn: false,
                        backdrop: 'static',
                        content: content,
                        onOk: () => {
                            //alert("ok")
                            resolve(true);
                        },
                        onCancel: () => {
                            //alert("cancel")
                            resolve(false);
                        }
                    }
                );
            });
        }
        //提示
        window.confirms = async function(content,bsStyle){
            return await openconfirm(content,bsStyle);
        }

    }


    showAddModal = (data = {}) =>{
        let {g_schema_key} = this.props;
        return new Promise((resolve, reject) => {
            this.parent().showModal(
                <AddPC onClose={this.parent().hideModal} data={data} g_schema_key={g_schema_key}/>,
                "新增", "600","250",
                [{
                    label: "确定",
                    className: "btn-primary",
                    func: "saveData"
                }, {
                    label: "取消",
                    className: "btn-default",
                    func: 'cancel'
                }])
            resolve()
        }).then(() => {
            $('.modal-content').css({
                'maxWidth': '600px',
                'transform': 'translate(-50%,20%)',
                'top': '20%',
                'left': '50%'
            });
        });
    }

    //创建
    addPC = ()=>{
        this.showAddModal()

    }
    //复制
    copyPC = async ()=>{
        let {g_schema_key} = this.props;
        let ID_ARR = $.fn.ulynlist.checkbox($(`#js-table${g_schema_key}`),["ID"]);
        if(ID_ARR && ID_ARR.length==0){
            alert("请选择批次");
            return;
        }
        if(ID_ARR && ID_ARR.length>1){
            alert("一次只能复制一个批次");
            return;
        }
        let flag = await window.confirms(`确定复制批次吗？`);
        if(!flag){
            return;
        }
        let pcIds = ID_ARR.map(item=>{
            return item.ID;
        });
        pcIds = pcIds.join(',');
        let {serviceName,methodName} = api.copyPc;
        SchemaService.callService(
            serviceName,
            methodName,
            [{'name':'pcIds','data':pcIds}]
        ).then((res) => {
            let resDara  = JSON.parse(res);
            if(resDara && resDara.status == true){
                success(resDara.msg ||'批次复制成功');
                $.fn.ulynlist.refresh($(`#js-table${g_schema_key}`),true);
            }else{
                alert(resDara.msg || '批次复制失败');
            }
        });
    }
    //编辑
    edit(id,item){
        this.showAddModal(item)
        // super.edit(id)
    }
    //删除
    del(id){
        super.del(id)
    }
    //归档
    async toEnd(id){
        await this.dealPc(id,api["endPCService"],'归档');
    }
    //启用
    async toStart(id){
        await this.dealPc(id,api["startPCService"],'启用');

    }

    dealPc = async (id,api,name)=>{
        let flag = await window.confirms(`是否确定${name}？`,'');
        if(!flag){
            return;
        }
        let {g_schema_key} = this.props;
        let {serviceName,methodName} = api;
        SchemaService.callService(
            serviceName,
            methodName,
            [{'name':'pcNo','data':id}]
        ).then(function (res) {
            res = JSON.parse(res);
            if(res && res.status){
                if(res.data && res.data.code == 1200){ //成功
                    let message = res.data && res.data.message ? res.data.message : `${name}成功`;
                    success(message);
                    $.fn.ulynlist.refresh($(`#js-table${g_schema_key}`));
                }else{ //异常操作
                    let message = res.data && res.data.message ? res.data.message : `${name}失败`;
                    alert(message);
                    return
                }
            }else{ //接口失败
                let message = res.msg ? res.msg : `${name}失败`;
                alert(message);
                return
            }
        })
    }
    //申报模板管理
    toManager(id){
        window.location.href = `/smart-campus/query.html?g_schema_key=T_ZHXY_TEMPLATE_FA&PC_ID=${id}#/`
    }




}
export default QueryListWapp;
