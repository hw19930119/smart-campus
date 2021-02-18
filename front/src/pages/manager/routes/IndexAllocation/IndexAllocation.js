import React from 'react';
import {Button,Tabs, Tab} from '@share/shareui';
import PageRouter from "@share/scurd/block/pageRouter/pageRouter";
import BaseComponent from '../../../../components/BaseComponent';
import TreeList from "../../../../components/business/TreeList/index";
import './IndexAllocation.scss';
import store from 'store';
import AddIndexAllocation from '../../../../components/business/AddIndexAllocation/AddIndexAllocation';
import AllcationWrap from '../../../../components/business/AllocationWrap/AllocationWrap';
import {findManagerFileList,changeCategory,deleteCategory} from '@/services/requestFunc';
import { getQueryString } from '@share/utils';
import {getParentCode,generateKeys} from "../../../../utils/util";
import ModalBox from '../../../../components/ui/ModalBox/ModalBox';
class IndexAllocation extends BaseComponent{
    constructor(props){
        super(props)
        this.state = {
            activeTab:1,
            templateId: getQueryString(window.location, 'templateId'),
            treeList:[],
            expandedKeys:[],
            allocationData:{},
            selectTreeCode:'',
            modalShow:false,
            type:'',
            itemInfo:{}
        }
    }
    async componentDidMount() {
        await this.getData();
    }
    componentWillUnmount(){
      this.goBack()
    }
    getData = () => {
        let {templateId=''} = this.state;
        let activeCode = store.get('activeCode');
        let storeExpandedKeys = store.get('expandedKeys');
        findManagerFileList(templateId).then(res => {
            if(res){
                let itemData = res.fieldConfig;
                let allocationData = itemData && itemData.length>0?itemData[0]:{};
                let expandedKeys = storeExpandedKeys?storeExpandedKeys:generateKeys(itemData);
                let selectTreeCode = allocationData.key;
                let thisData = {...allocationData};
                if(activeCode){
                    let newData  = getParentCode(activeCode,itemData);
                    if(newData){
                        thisData = newData;
                        selectTreeCode = activeCode;
                    }
                }
                this.setState({
                    templateName:res.templateName,
                    treeList:itemData,
                    allocationData:thisData,
                    selectTreeCode:selectTreeCode,
                    expandedKeys:expandedKeys
                })
            }
        })
    }

    handleTree = (data, execute,initIndex) => {
        store.set('activeCode',data.key);
        let {categoryType,key,parentId} = data;
        let {templateName} = this.state;
        let itemInfo = execute=='add'?{
                parentId:parentId,
                key:key,
                score:'',
                categoryType:initIndex<4?'0':'1',
                title:data.isTop?templateName:'',
                isDisabled: true


        }:{...data};
        let allocationData = {...data};
        this.setState({allocationData:allocationData,itemInfo:itemInfo,type:execute});
        if(execute=='del'){
            this.handleDelete(data)
        } else {
            if(categoryType=='0' && execute!='goto'){
                this.setState({
                    modalShow:true,
                })
            }
        }
    };
    // 柱状图展开折叠
    treeOnExpand=(keys)=>{
        store.set('expandedKeys',keys);
    }
    // 保存
    handleIndexAllocation = ()=>{
        this.refs.addDom.getValue();
    }

    saveIndexAllocation = (data)=>{
        let {templateId} = this.state;
        let params = {
            ...data,
            templateId:templateId,
        }
        changeCategory(params).then(res =>{
            this.afterSaveData(res,data.isAdd)
        })
    }
    handleClose = ()=>{
        this.setState({
            modalShow:false,
        })
    }
    handleDelete = async (data)=>{
        let {key='',categoryType} = data;
        let pass = await window.confirms('确定删除该项吗？');
        if(pass){
            let params = {
                key,
                categoryType
            }
            deleteCategory(params).then(res=>{
                store.remove('activeCode');
                this.afterSaveData(res)
            })
        }
    }
    afterSaveData = (res,isAdd)=>{
        let that = this;
        that.handleClose();
        if(res.result){
            window.success(res.message);
            if(isAdd=='0'){
                this.setState({
                    type:'edit'
                })
                store.set('activeCode',res.result.key);
            }
            that.getData()
        }else{
            window.error(res.message);
        }
    }
    changeTab = (index)=>{
        store.set('activeTab',index)
    }
    goBack = (status)=>{
        store.remove('activeTab');
        store.remove('activeCode');
        store.remove('expandedKeys');
        if(status){
            history.back();
        }
    }
    render(){
        let {treeList,selectTreeCode,modalShow,expandedKeys,type,allocationData,templateId,templateName,itemInfo} = this.state;
        let activeTab =  store.get('activeTab');
        activeTab = activeTab?activeTab:1;
        return (
            <div className="index-allocation">
                <Tabs bsStyle="tabs" defaultActiveKey={activeTab} id="demo-tabs" onSelect={(index) => {this.changeTab(index)}}>
                    <Tab eventKey={1} title='模板配置'>
                        <PageRouter
                            page={'detail'}
                            g_schema_key={'T_ZHXY_TEMPLATE_FA'}
                            g_id={templateId || ''}
                        />
                    </Tab>
                    <Tab eventKey={2} title='考评指标配置'>
                        <div className="zb-con">
                            <div className="tree-con">
                                <TreeList data={treeList}
                                          treeOnExpand={this.treeOnExpand}
                                          handleClick={this.handleTree}
                                          activeCode={selectTreeCode}
                                          expandedKeys={expandedKeys}
                                          page={'manager'}
                                          canDrag={true}
                                          canEdit={true}
                                />
                            </div>
                            <div className="zb-content">
                                {
                                    treeList.length>0?<AllcationWrap  data={allocationData} type={type=='edit'?'add':'detail'} templateName={templateName}></AllcationWrap>:
                                        <div className="no-data">
                                            暂无考评内容
                                        </div>
                                }
                            </div>
                        </div>
                        <div className="page-btn-bar">
                            <Button type="button" onClick={()=>this.goBack(true)}  bsSize="large">返回</Button>
                        </div>
                    </Tab>
                </Tabs>
                {
                    modalShow &&
                    <ModalBox size='sm' handleModal={this.handleClose} handleCommit={this.handleIndexAllocation} title={ type=='add'?'新增':'编辑'}>
                        <AddIndexAllocation type={type} itemInfo={itemInfo} ref="addDom"  saveData={this.saveIndexAllocation} treeList={treeList}/>
                    </ModalBox>
                }
            </div>
        )
    }
}
export default IndexAllocation;