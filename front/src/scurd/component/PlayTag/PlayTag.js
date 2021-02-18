import React, { Fragment } from 'react';
import ModalBox from '../../../components/ui/ModalBox/ModalBox';
import { Transfer,Form,FormControl,Button,Label } from '@share/shareui';
import './PlayTag.scss';
import {lableStyle} from "../../../utils/code";
import {ApiCall} from '@share/scurd/block/ApiCall';
import {formaterLables} from "../../../utils/util";
import {addLable} from "../../../services/requestFunc";

class PlayTag extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            tagArr:[],
            addTagModal:false,
            newValue:'',
            dataSource:[],
            targetMetadata:[]
        }
    }
    componentDidMount = () =>{
        const { form ,itemData} = this.props;
        const tags = itemData.val;
        if(tags){
            let tagArr = JSON.parse(tags);
            this.setState({
                tagArr:tagArr
            })
        }
    }
    handleTag = (status)=>{
        this.setState({
            addTagModal:status
        },()=>{
            if(status){
                this.getLabelList();
            }
        })
    };
    getLabelList = ()=>{
        let {tagArr} = this.state;
        ApiCall.query({
            g_schema_key:'T_ZHXY_LABLE_FA',
            linesPerPage:999
        }).then(res=>{
            let dataSource = formaterLables(res.list);
            let targetKeys = tagArr.map(item=>{
                return item.id
            });
            this.setState({
                dataSource,
                targetKeys,
            })
        })
    };

    filterOption = (inputValue, option)=> {
        return option.name.includes(inputValue);
    }
    handleAddLabel = ()=>{
        let {newValue,dataSource} = this.state;
        if(!newValue) {
            alert('请输入标签名称');
            return
        }
        let result = dataSource.filter(item=>{
           return item.name === newValue
        })
        if(result && result.length>0){
            alert('已存在该标签');
            return
        }
        addLable(newValue).then(res=>{
            let {message,result} = res;
            if(result){
                window.success(message);
                this.getLabelList();
            }else{
                alert(message);
            }
        })
    }
    handleChange=(value, direction, moveKeys, metadata)=>{
        this.setState({
            targetKeys: value, // 这是选中的id
            targetMetadata: metadata, // 选中的原数据

        });
    };
    handleCommit = ()=>{
        // 保存
        let {targetMetadata} = this.state;
        const {stateChange} = this.props;
        let results = targetMetadata.map(item=>{
            return {
                id:item.id,
                name:item.name
            }
        })
        stateChange('TAG',results);
        this.setState({
            tagArr:results
        })
        this.handleTag(false);
    }

    render(){
        const {form,itemData} = this.props;
        let {readOnly} = itemData;
        let {tagArr,addTagModal,newValue,dataSource,targetKeys} = this.state;
        return (
            <div className="tag-item">
                <div className="tags">
                    {
                        tagArr.length>0 && tagArr.map((item,index)=>{
                            let lableIndex = index%(lableStyle.length);
                            return <Label key={index} bsStyle={lableStyle[lableIndex]}>{item.name}</Label>
                        })
                    }
                    {
                        tagArr.length==0 && readOnly && <span className="label no-label">无</span>
                    }
                </div>
                {
                    !readOnly &&
                    <span className="add-tag" onClick={()=>{this.handleTag(true)}}>
                         +
                    </span>
                }

                {
                    addTagModal &&
                    <ModalBox size={'large'} className="play-tag-modal" handleModal={()=>this.handleTag(false)} handleCommit={this.handleCommit} title={'打标签'}>
                        <div className="setLabel-con">
                            <Form pageType="addPage" formState={form}>
                                <Form.Table>
                                    <Form.Tr className="label-input">
                                        <Form.Label>标签名称</Form.Label>
                                        <Form.Content>
                                            <FormControl
                                                value={newValue}
                                                type="text"
                                                placeholder="请输入"
                                                onChange={(e) => {
                                                    this.setState({
                                                        newValue:e.target.value
                                                    })
                                                }}/>
                                        </Form.Content>
                                        <Button bsStyle={"primary"} onClick={this.handleAddLabel}>添加</Button>
                                    </Form.Tr>
                                    <Form.Tr>
                                        <Form.Label>标签</Form.Label>
                                        <Form.Content>
                                            <Transfer
                                                rowKey="value"
                                                showSearch
                                                dataSource={dataSource}
                                                titles={['选项', '已选标签']}
                                                filterOption={this.filterOption}
                                                renderRow={data => <p>{data.name}</p>}
                                                targetKeys={targetKeys}
                                                onChange={this.handleChange}
                                            />
                                        </Form.Content>
                                    </Form.Tr>
                                </Form.Table>
                            </Form>

                        </div>
                    </ModalBox>
                }
            </div>
        )
    }
}
export default PlayTag;