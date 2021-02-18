/*
 * @(#) index.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-10-30 10:54:44
 */

import React from 'react';
import {queryConfigTree,saveCommentConfig,queryExpertField} from '@/services/requestFunc';
import TreeList from '../../../../../components/business/TreeList';
import {generateKeys,RndNum} from "../../../../../utils/util";
import SelectExpert from '../../../../../components/business/SelectExpert/SelectExpert';
import SelectedExpert from '../../../../../components/ui/SelectedExpert/SelectedExpert';
import './style.scss';
class ConfigExpert extends React.Component{
    constructor(props){
        super(props)
        this.state = {
            templateId: this.props.templateId,
            pcNo:this.props.pcNo,
            treeList:{},
            expandedKeys:[],
            selectTreeCode:'',
            experts:[],
            chekedTreeNode:[],
            disabledChekedTreeNode:[],
            initCheckTreeNode:[],
            rnsNum:''
        }
    }
    async componentDidMount() {
        await this.getData();
    }

    getData = () => {
        let {templateId=''} = this.state;
        queryConfigTree(templateId).then(res => {
            if(res){
                let {alreadySelectIds,experts,treeDtos} = res;
                let itemData = treeDtos;
                let allocationData = itemData && itemData.length>0?itemData[0]:{};
                let expandedKeys = generateKeys(itemData);
                let selectTreeCode = allocationData.key;
                this.setState({
                    treeList:itemData,
                    selectTreeCode:selectTreeCode,
                    expandedKeys:expandedKeys,
                    experts,
                    initCheckTreeNode:alreadySelectIds,
                    chekedTreeNode:alreadySelectIds,
                    disabledChekedTreeNode:alreadySelectIds
                })
            }
        })
    }

    handleTree = (data, execute) => {
        if(execute == 'goto'){
            this.setState({selectTreeCode:data.key})
        }
    };
    // 柱状图展开折叠
    treeOnExpand=(keys)=>{
        // store.set('expandedKeysExpert',keys);
    }
    close() {
        this.props.onClose();
    }
    //分配专家
    handleSelect = (value)=>{
        let {pcNo,templateId,chekedTreeNode,disabledChekedTreeNode} = this.state;
        let fieldIds = chekedTreeNode.filter(item=>!disabledChekedTreeNode.includes(item));
        let params = {
            pcNo,
            templateId,
            expertId:value.join(','),
            fieldIds:fieldIds.join(',')
        }
        saveCommentConfig(params).then(res=>{
            let num = RndNum(6);
            this.setState({
                rnsNum:num
            })
            this.afterSaveData(res)
        })
    }
    afterSaveData = (res)=>{
        let that = this;
        if(res.result){
            window.success(res.msg);
            that.getData()
        }else{
            window.error(res.msg);
        }
    }
    onTreeCheck = (values)=>{
        this.setState({
            chekedTreeNode:values
        })
    }
    changeExpertField = async(key)=>{
        let {templateId,initCheckTreeNode,chekedTreeNode} = this.state;
        let disabledChekedTreeNode = initCheckTreeNode;
        if(key!=''){
           let result = await queryExpertField(templateId,key);
           let ids = result.ids || [];
            disabledChekedTreeNode = initCheckTreeNode.filter(item=>!ids.includes(item))
        }
        let thisCheckedValues =Array.from(new Set(initCheckTreeNode.concat(chekedTreeNode)))
        this.setState({
            chekedTreeNode:thisCheckedValues,
            disabledChekedTreeNode
        })
    }
    render(){
        let {treeList,selectTreeCode,expandedKeys,experts,chekedTreeNode,disabledChekedTreeNode,rnsNum} = this.state;
        return (
            <div className="index-allocation config-expert">
                <div className="zb-con">
                    <div className="tree-con">
                        <TreeList data={treeList}
                                  treeOnExpand={this.treeOnExpand}
                                  handleClick={this.handleTree}
                                  activeCode={selectTreeCode}
                                  expandedKeys={expandedKeys}
                                  chekedTreeNode={chekedTreeNode}
                                  disabledChekedTreeNode={disabledChekedTreeNode}
                                  page={'expert'}
                                  canDrag={true}
                                  canEdit={false}
                                  checkable={true}
                                  onTreeCheck = {this.onTreeCheck}
                        />
                    </div>
                    <div className="zb-content">
                       <SelectExpert handleSelect={this.handleSelect} options={experts} rnsNum={rnsNum} changeExpertField={this.changeExpertField}></SelectExpert>
                        <SelectedExpert data={treeList}  experts={experts}></SelectedExpert>
                    </div>
                </div>
                {/*<div className="page-btn-bar">*/}
                    {/*<Button type="button" onClick={()=>this.goBack(true)}  bsSize="large">返回</Button>*/}
                {/*</div>*/}
            </div>
        )
    }
}
export default ConfigExpert;