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
import PageRouter from '@share/scurd/block/pageRouter/pageRouter';
import PreviewModal from '../../components/PreviewModal';
import ConfigExpert from '../../components/ConfigExpert';
import '../../../../../pages/manager/routes/IndexAllocation/IndexAllocation.scss';
import { getQueryString } from '@share/utils';
import {ParamsUtils} from "@share/scurd/block/Utils";
// import ModalBox from '../../../../../components/ui/ModalBox/ModalBox';
import {getTreePreviewByTemplateId} from '@/services/requestFunc';
import './style.scss';
class QueryListWapp extends QueryList {
    constructor(props) {
        super(props);
        this.state = {
            ...this.state,
            modalShow:false,
            templateId:''
        };
        this.copyTemplate = this.copyTemplate.bind(this);
    }

    copyTemplate() {
        this.parent().showModal(<PageRouter
            g_schema_key="T_ZHXY_TEMPLATE_FA01"
            f_cancel={this.parent().hideModal}
            page="query"
            g_inline
        />, '复制模板');
    }

    //编辑
    toManager(id){
        let pcId = getQueryString(window.location, 'PC_ID');
        window.location.href = `/smart-campus/manager.html?templateId=${id}&pcId=${pcId}#/`
    }
     configExpert = async (id)=>{
        let pcNo = getQueryString(window.location, 'PC_ID');
        this.parent().showModal(
            <ConfigExpert templateId={id} onClose={this.parent().hideModal} pcNo={pcNo}></ConfigExpert>,
            "配置专家", 1000,550,
            [{
                label: "关闭",
                className: "btn-default",
                func: 'close'
            }])
    }
    // 预览
    preview = async(id,item)=>{
        return new Promise((resolve, reject) => {
            this.parent().showModal(
                <PreviewModal templateId={id} onClose={this.parent().hideModal}></PreviewModal>,
                "模板预览", "800","550",
                [{
                    label: "关闭",
                    className: "btn-default",
                    func: 'cancel'
                }])
            resolve()
        }).then(() => {
            $('.modal-content').css({
                'width': '100%',
                'height':'100%'
            });
        });
    }

    //返回批次列表
    backList = () => {
        window.location.href = `/smart-campus/query.html?g_schema_key=T_ZHXY_PC_FA#/`
    }


    afterRender(){
        let gSchemaKey = ParamsUtils.getQueryString("g_schema_key", this.props);
        let datas = $.fn.ulynlist.getData($("#js-table" + gSchemaKey));
        $.each(datas, function (i, item) {
            if (item.PC_STATE == '启用') {
                $('.query-T_ZHXY_TEMPLATE_FA .right-box ul.right-item li').eq(0).hide();  //有启用的批次，就不允许在新增模板
                $('.query-T_ZHXY_TEMPLATE_FA .right-box ul.right-item li').eq(1).hide();  //有启用的批次，就不允许在复制模板
                return false;
            }
            else if(item.PC_STATE == '归档'){
                $('.query-T_ZHXY_TEMPLATE_FA .right-box ul.right-item li').eq(0).hide();  //有归档的批次，就不允许在新增模板
            }
        });
    }
    handleEvaluationModal = ()=>{
        this.setState({
            modalShow:false,
            templateId:'',
        })
    }
}
export default QueryListWapp;
