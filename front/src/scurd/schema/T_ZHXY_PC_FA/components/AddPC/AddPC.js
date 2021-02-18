import React from 'react';
import {Form,FormControl} from '@share/shareui';
import {SchemaService} from "@share/scurd/block/SchemaService";
import {api} from "../../../../../services/serviceApi";
import BaseComponent from '../../../../../components/BaseComponent'

class AddPC extends BaseComponent {
    constructor(props) {
        super(props);
        let {data} = props || {};
        let {PC_NO,PC_NAME} = data || '';
        this.state = {
            pcNo: PC_NO,
            pcName: PC_NAME,
            msg:''
        };
    }

    onChange = (e,key)=> {
        let val = e.target.value;
        let r = /^[^\u4e00-\u9fa5]+$/; //不能输入中文
        if(!r.test(val) && key == 'pcNo'){
            this.setState({
                pcNo:'',
                msg:'批次号不能包含中文字符'
            });
            return
        }else{
            this.setState({msg:''})
        }
        this.setState({[key]:val});

    }

    saveData=()=> {
        let self = this;
        let {g_schema_key,data} = this.props;
        let mark = Object.keys(data).length > 0 ? 'edit' : 'add'; //有数据表示编辑，没数据表示新增
        let {pcNo,pcName} = this.state;
        if (pcNo == '') {
            this.setState({msg:'批次号不能为空！'});
            return;
        }
        if (pcName == '') {
            this.setState({msg:'批次名称不能为空！'});
            return;
        }
        let {serviceName,methodName} = api["savePCService"];
        SchemaService.callService(
            serviceName,
            methodName,
            [{'name':'pcNo','data':pcNo},{'name':'pcName','data':pcName},{'name':'mark','data':mark}]
        ).then(function (res) {
            res = JSON.parse(res);
            if(res && res.status == true){
                if(res.data.code == 1200){
                    window.success(res.data.message);
                    $.fn.ulynlist.refresh($(`#js-table${g_schema_key}`));
                    self.props.onClose();
                }else{
                    window.error(res.data.message);
                    return
                }
            }else{
                window.error(res.msg);
                return
            }
        })
    }

    cancel() {
        this.props.onClose();
    }

    render() {
        let {pcNo,pcName, msg} = this.state;
        let {data} = this.props;
        let disabled = Object.keys(data).length > 0 ? true : false; //有数据不能编辑批次号
        return (
            <div style={{width: '100%'}}>
                <Form pageType="editPage">
                    <Form.Table>
                        <Form.Tr>
                            <Form.Label required>批次号</Form.Label>
                            <Form.Content>
                                <FormControl disabled={disabled} value={pcNo} maxLength={20} onChange={(e)=>this.onChange(e,'pcNo')}/>
                            </Form.Content>
                        </Form.Tr>
                        <Form.Tr>
                            <Form.Label required>批次名称</Form.Label>
                            <Form.Content>
                                <FormControl value={pcName} maxLength={64} onChange={(e)=>this.onChange(e,'pcName')}/>
                            </Form.Content>
                        </Form.Tr>
                    </Form.Table>
                </Form>
                {
                    msg != '' && <p className="text-danger text text-tip pull-left" style={{marginTop: '5px', paddingLeft: '150px'}}>
                        <i className="fa fa-times-circle"></i>{msg}
                    </p>
                }
            </div>

        )
    }

}

export default AddPC;

