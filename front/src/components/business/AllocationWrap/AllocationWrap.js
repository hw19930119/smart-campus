import React from 'react';
import BaseComponent from '../../BaseComponent';
import {Form,Panel} from '@share/shareui';
import PageRouter from "@share/scurd/block/pageRouter/pageRouter";
import './AllocationWrap.scss';
import {dealNumber} from "../../../utils/util";
class AllocationWrap extends BaseComponent{
    constructor(props){
        super(props);
        let {data={},type} = this.props;
        this.state = {
            data:data,
            type:type
        }
    }
    componentDidMount(){
        let {data,type} = this.props;
        this.setState({
            data:data,
            type:type
        })
    }
    componentWillReceiveProps(nextProps){
        let {data,type} = nextProps;
        if(this.state.type!==type){
            this.setState({
                type:type,
            })
        }
        if(JSON.stringify(this.state.data)!==JSON.stringify(data)){
            this.setState({
                data:data,
            })
        }
    }
    render(){
        let {data,type} = this.state;
        let {parentId='',title,score,child} = data || {};
        let {templateName} = this.props;
        let isZb = data && data.categoryType == '1'?true:false;
        return (
            <div className={`allocation-wrap ${type=='detail'?'detail':'add'}`}>
                <Panel bsStyle="primary">
                    <Panel.Head title={`${title}${isZb?'':`（满分${dealNumber(score)}分）`}`}>
                    </Panel.Head>
                    <Panel.Body>
                        {
                            isZb?
                                <PageRouter
                                    page={type}
                                    g_schema_key={'T_POLICY_CG_FIELD_CONFIG_01'}
                                    g_id={data.key || ''}
                                />:
                                <div className="fl-con">
                                    <Form pageType="editPage">
                                        <Form.Table>
                                            <Form.Tr>
                                                <Form.Label required>分类</Form.Label>
                                                <Form.Content>
                                                    <span className="textShow">{parentId=='0'?templateName:child?'自评项目':'目录'}</span>
                                                </Form.Content>
                                            </Form.Tr>
                                            <Form.Tr>
                                                <Form.Label required>名称</Form.Label>
                                                <Form.Content>
                                                    <span className="textShow">{title}</span>
                                                </Form.Content>
                                            </Form.Tr>
                                            <Form.Tr>
                                                <Form.Label required>分值</Form.Label>
                                                <Form.Content>
                                                    <span className="textShow">{dealNumber(score)}</span>
                                                </Form.Content>
                                            </Form.Tr>
                                        </Form.Table>
                                    </Form>
                                </div>
                        }
                    </Panel.Body>
                </Panel>

            </div>
        )
    }
}
export default AllocationWrap;