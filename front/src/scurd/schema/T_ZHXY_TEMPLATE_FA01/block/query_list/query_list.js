/*
 * @(#) query_list.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author mana
 * <br> 2019-09-19:02:25
 */
import React from 'react';
import {QueryList} from '@share/scurd/block/query_list/query_list';
import {SchemaService} from "@share/scurd/block/SchemaService";
import {api} from "../../../../../services/serviceApi";
import ModalTool from "@share/shareui/es/components/ModalTool";
import {UrlUtils} from '@share/scurd/block/Utils'
import {ButtonToolBar,Button} from '@share/shareui';
import './style.scss';
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
    copyTemplate = async ()=>{
        let {g_schema_key} = this.props;
        let ID_ARR = $.fn.ulynlist.checkbox($(`#js-table${g_schema_key}`),["ID"]);
        if(ID_ARR && ID_ARR.length==0){
            alert("请选择模板");
            return;
        }
        if(ID_ARR && ID_ARR.length>1){
            alert("一次只能复制一个模板");
            return;
        }
        let flag = await window.confirms(`确定复制模板吗？`);
        if(!flag){
            return;
        }
        let templateIds = ID_ARR.map(item=>{
            return item.ID
        })
        templateIds = templateIds.join(',');
        let {serviceName,methodName} = api.copyTemplate;
        let pcId = UrlUtils.GetQueryString('PC_ID');
        SchemaService.callService(
            serviceName,
            methodName,
            [{'name':'oldPcId','data':pcId},{'name':'newPcId','data':pcId},{'name':'templateIds','data':templateIds}]
        ).then(function (res) {
            let resDara  = JSON.parse(res);
            if(resDara && resDara.status == true){
                success(resDara.msg || '模板复制成功');
                $.fn.ulynlist.refresh($(`#js-tableT_ZHXY_TEMPLATE_FA`),true);
                $.fn.ulynlist.refresh($(`#js-table${g_schema_key}`),true);
            }else{
                alert(resDara.msg || '模板复制失败');
            }
        });
    }
    //申报模板管理
    toManager(id){
        window.location.href = `/smart-campus/query.html?g_schema_key=T_ZHXY_TEMPLATE_FA&PC_ID=${id}}#/`
    }
    render(){
        return (
            <div className="template-list">
                {
                    super.render()
                }
                <ButtonToolBar>
                    <Button type="submit" bsStyle="primary" onClick={this.copyTemplate}>确定</Button>
                    {/*<Button type="button" onClick={() => history.replace('/')}>取消</Button>*/}
                </ButtonToolBar>
            </div>
            )
    }
}
export default QueryListWapp;
