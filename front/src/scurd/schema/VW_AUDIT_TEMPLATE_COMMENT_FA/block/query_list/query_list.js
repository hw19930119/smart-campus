/*
 * @(#) query_list.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author lihui
 * <br> 2020-11-01 13:18:09
 */
import React from 'react';
import {QueryList} from '@share/scurd/block/query_list/query_list';
import PropTypes from 'prop-types';
import {getTreePreview} from "../../../../../services/requestFunc";
import {getCommentByExpert} from "../../../../../services/requestScurdFunc";
import {deleteLastTree} from "../../../../../utils/util";
import SelfEvaluation from '../../../../../components/ui/SelfEvaluation';
import './style.scss';
import {Spin} from '@share/shareui';
class QueryListWapp extends QueryList {

    constructor(props) {
        super(props);
        this.state = {
            ...this.state,
            businessKey:'',
            declareId:'',
            treeData:[],
            dpConfigUpdateTime:''
        }
    }
    static contextTypes = {
        router: PropTypes.object.isRequired,
    };
    getDpConfigUpdateTime(id){
        getCommentByExpert(id).then(res=>{
            let dpConfigUpdateTime = res.dpConfigUpdateTime || '';
            this.setState({dpConfigUpdateTime})
        })
    }
    reviewFun(data,item){
        this.getData(item)
        this.setRowStyle(item.ID)
        // window.location.href = `/smart-campus/audit.html#/expertComment/${item.ID}/${item.DECLARE_ID}/add`
        // this.parent().showModal(
        //     <div>
        //         <AuditScore businessKey={item.ID} declareId={item.DECLARE_ID} type={"detail"} />
        //         <ExpertReview cancel={this.parent().hideModal} DECLARE_ID={item.DECLARE_ID} />
        //     </div>,
        //     "厦门市中小学智慧校园建设评价评分",
        //     1200,
        //     800,
        //     []
        // );
    }
    setRowStyle = (key)=>{
        let {g_schema_key} = this.props;
        var datas = $.fn.ulynlist.getData($("#js-table" + g_schema_key));
        let zIndex = 0;
        datas.map((item,index)=>{
            if(key==item.ID){
                zIndex = index
            }
        })
        let dom = $('#js-tableVW_AUDIT_TEMPLATE_COMMENT_FA .school-item').eq(zIndex);
        if(dom){
            $('#js-tableVW_AUDIT_TEMPLATE_COMMENT_FA .school-item').removeClass('active');
            dom.addClass('active');
        }

    }
    afterRender = ()=>{
        let {g_schema_key} = this.props;
        var datas = $.fn.ulynlist.getData($("#js-table" + g_schema_key));
        if(datas && datas.length>0){
            let thisData = datas[0];
            this.getData(thisData);
            this.getDpConfigUpdateTime(thisData.DECLARE_ID);
            this.setRowStyle(thisData.ID)
        }else{
            this.setState({
                treeData:[]
            })
        }


    }
    getData = (itemInfo) => {
        let businessKey =itemInfo.ID;
        let declareId = itemInfo.DECLARE_ID;
        Spin.show("数据加载中")
        getTreePreview(declareId,businessKey,false,'yes').then(res => {
            Spin.hide();
            if(res){
                let {fieldConfig} = res;
                let data = deleteLastTree(fieldConfig);
                res.fieldConfig = data;
                this.setState({
                    businessKey,
                    declareId,
                    treeData:res,
                })
            }
        })
    }
    refreshData = ()=>{
       let {businessKey,declareId} = this.state;
       let params = {
           ID:businessKey,
           DECLARE_ID:declareId
       }
        this.getData(params)
    }
    renderHeadLeft() {
        return (<h3 className="title">学校列表</h3>);
    }
    render(){
        let {businessKey,declareId,dpConfigUpdateTime,treeData } = this.state;
        return (
            <div className="expert-comment-page">
                <div className="school-list">
                    {
                        super.render()
                    }
                </div>
                <div className="comment-tree">
                    <SelfEvaluation treeData={treeData} readOnly={true} type={'add'} page={'expertAudit'} ref="selfEvaluation" businessInfo={{businessKey:businessKey,pcId:declareId}} refreshData={this.refreshData} dpConfigUpdateTime={dpConfigUpdateTime}></SelfEvaluation>
                </div>
            </div>
        )
    }
}
export default QueryListWapp;
