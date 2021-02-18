/*
 * @(#) query_list.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-11-18 17:00:52
 */
import React from 'react';
import {QueryList} from '@share/scurd/block/query_list/query_list';
import {Button,Textarea,Form} from '@share/shareui';
import {returnBaseInfoList} from "../../../../../services/requestScurdFunc";
import ModalBox from '../../../../../components/ui/ModalBox/ModalBox';
import "./index.scss";
class QueryListAudit extends QueryList {
    constructor(props) {
        super(props);
        this.state = {
            ...this.state,
            errorShow:false,
            ModalShow:false,
            opinion:''
        }
    }
    returnSubmit = ()=>{
        let {g_schema_key} = this.props
        let datas = $.fn.ulynlist.checkbox($(`#js-table${g_schema_key}`),['ID']);
        if(datas.length == 0){
            alert('请至少选择一条数据');
            return;
        }
        this.setState({
            modalShow:true
        })
    }
    handleCommit = ()=>{
        let {opinion} = this.state;
        if(!opinion){
            this.setState({errorShow:true});
            return;
        }
        let {g_schema_key} = this.props;
        let datas = $.fn.ulynlist.checkbox($(`#js-table${g_schema_key}`),['ID','DECLARE_ID']);
        let ids = datas.map(item=>{
            return item.DECLARE_ID;
        })
        let params = {
            declareIds:ids.join(','),
            opinion:opinion
        }
        returnBaseInfoList(params).then(res=>{
            this.handleClose();
            $.fn.ulynlist.refresh($(`#js-table${g_schema_key}`),true);
        })
    }
    handleClose = ()=>{
        this.setState({
            modalShow:false
        })
    }
    returnFunc = (id,item)=>{
        window.location.href = `/smart-campus/audit.html#/auditScore/${id}/${item.DECLARE_ID}/slzx`
    }
    render(){
        let {opinion,errorShow,modalShow} = this.state;
        return (
            <div className="return-list">
                {
                    super.render()
                }
                {
                    modalShow &&  <ModalBox handleCommit={this.handleCommit} title={'统一退回'} size={'sm'} handleModal={this.handleClose}>
                        <Form pageType="editPage">
                            <Form.Table>
                                <Form.Tr>
                                    <Form.Label required={true}>退回意见</Form.Label>
                                    <Form.Content>
                                <Textarea autoHeight
                                          onChange={e =>
                                          {
                                              let errorShow = e.target.value?false:true;
                                              this.setState({opinion: e.target.value,errorShow:errorShow});
                                          }}
                                          value={opinion}
                                          limit
                                          maxLength={150}
                                          rows="4"
                                />
                                        {
                                            errorShow && <span style={{color:"#f30"}}>请输入审批意见</span>
                                        }
                                    </Form.Content>
                                </Form.Tr>
                            </Form.Table>
                        </Form>
                    </ModalBox>
                }


            </div>
        )
    }
}
export default QueryListAudit;
