import React from 'react';
import {Form,Panel} from '@share/shareui';
import BaseComponent from '../../BaseComponent';
import TreeList from "../../../components/business/TreeList/index";
import FormList from "../../../components/business/FormList/index";
import {findBasicFileList} from '@/services/requestFunc';
import {dealNumber,getParentCode,generateKeys} from "../../../utils/util";
import './SelfEvaluation.scss';
import {saveDpOpinionFunc} from "../../../services/requestScurdFunc";

class SelfEvaluation extends BaseComponent {
    constructor(props) {
        super(props);
        this.state = {
            treeList:[],
            nowFormList:{},
            lastList:[],
            selectTreeCode:'',
            expandedKeys:[],
            dpOpinion:[],
            dpConfigUpdateTime:''
        }
    }

    async componentDidMount() {
        let {treeData} = this.props;
        this.setInitState(treeData);
    }
    componentWillReceiveProps(next){
        let {page} = this.props;
        let {treeData} = next;
        if(JSON.stringify(next.treeData)!=JSON.stringify(this.props.treeData)){
            if(page=='expertAudit' && (next.businessInfo.businessKey!=this.props.businessInfo.businessKey)){
                this.setState({
                    selectTreeCode:''
                },()=>{
                    this.setInitState(treeData)
                })
            }else{
                this.setInitState(treeData)
            }
        }
        if(next.dpConfigUpdateTime !=this.props.dpConfigUpdateTime){
            this.setState({
                dpConfigUpdateTime:next.dpConfigUpdateTime
            })
        }
    }
    setInitState = (treeData)=>{
        if(treeData){
            let activeCode = this.state.selectTreeCode;
            let {fieldConfig=[],lastList=[],dpOpinion} = treeData;
            let itemData = lastList && lastList.length>0?lastList[0]:{};
            let selectTreeCode = activeCode?activeCode:itemData.key;
            let expandedKeys = generateKeys(lastList);
            let nowFormList = activeCode? getParentCode(activeCode,lastList):itemData;
            this.setState({
                treeList:fieldConfig,
                lastList:lastList,
                nowFormList:nowFormList,
                selectTreeCode:selectTreeCode,
                expandedKeys:expandedKeys,
                dpOpinion:dpOpinion||[]
            })
        }
    }

    handleTree = async(data, execute) => {
        if(execute == 'goto'){
            this.setState({nowFormList:data,selectTreeCode:data.key})
        }

    };
    saveAllData = ()=>{
        let {lastList,treeList} = this.state;
        let {page} = this.props;
        let topItem = treeList.length>0?treeList[0]:{};
        let initScore = Number(topItem.score);
        let errorKeys = [],scores=0;
        lastList && lastList.map(item=>{
            let isComplete = page == 'apply' ? item.state == '0':item.shState=='1';
            if(!isComplete && item.child && item.child.length>0){
                errorKeys.push(item.key)
            }
            scores += Number(item.zpScore);
        })
        if(errorKeys.length>0){
            let firstKey = errorKeys[0];
            let nowFormList = getParentCode(firstKey,lastList);
            this.setState({
                errorKeys:errorKeys,
                selectTreeCode:errorKeys[0],
                nowFormList:nowFormList
            })
            window.error(`请${page=='apply'?'自评完':'完成'}评价标准再提交！`)
            return false;
        }
        if(initScore<scores){
            window.error(`项目${page=='apply'?'自':''}评分不能超过顶级分数！`)
            return false;
        }
        return true;
    }
    // 保存指标后过滤错误项 并且刷新数据
    refreshData = (key)=>{
        let {refreshData} = this.props;
        let {errorKeys=[]} = this.state;
        let newErrorKeys = errorKeys.filter(item=>{
            return item!=key
        })
        this.setState({
            errorKeys: newErrorKeys
        })
        refreshData && refreshData()
    }
    submitExpert =(info)=>{
        let {businessInfo,refreshData} = this.props;
        let {selectTreeCode,dpConfigUpdateTime} = this.state;
        let params = {
            ...info,
            bussinessKey:businessInfo.pcId,
            fieldId:selectTreeCode,
            dpConfigUpdateTime:dpConfigUpdateTime
        }
        saveDpOpinionFunc(params).then(res=>{
            refreshData && refreshData()
        })
    }

    render() {
        let {treeList,nowFormList,selectTreeCode,errorKeys,expandedKeys,dpOpinion} = this.state;
        let {readOnly,page,businessInfo,type} = this.props;
        let itemData = {...nowFormList};
        return (
            <div className={`self-evaluation ${page}-evaluation`}>
                <Panel bsStyle="primary" className="evaluation-title">
                    <Panel.Head title="厦门市中小学智慧校园建设评价评分"/>
                    <Panel.Body>
                        {
                            JSON.stringify(nowFormList)=='{}'?
                                <div className="no-data">
                                暂无数据
                            </div>:
                                <div className="zb-con">
                                    <div className="tree-con">
                                        <TreeList data={treeList}
                                                  handleClick={this.handleTree}
                                                  activeCode={selectTreeCode}
                                                  expandedKeys={expandedKeys}
                                                  errorKeys={errorKeys}
                                                  canDrag={true}
                                                  canEdit={false}
                                                  page={page}
                                                  type={type}
                                        />
                                    </div>
                                    <div className="zb-content">
                                        <p className="catergory-title">{itemData.title}：（满分{dealNumber(itemData.score) ||'0'}分）</p>
                                        {
                                            itemData.child ?
                                                <div className="fl-con">
                                                    <FormList data={nowFormList} type={type} businessInfo={businessInfo} saveAllData={this.saveAllData} refreshData={this.refreshData}  ref="formList" readOnly={readOnly} page={page} dpOpinion={dpOpinion} submitExpert={this.submitExpert}/>
                                                </div>
                                                :
                                                <div className="fl-con">
                                                    <Form pageType="editPage">
                                                        <Form.Table>
                                                            <Form.Tr>
                                                                <Form.Label required>{'分类'}</Form.Label>
                                                                <Form.Content>
                                                                    <span className="textShow">目录</span>
                                                                </Form.Content>
                                                            </Form.Tr>
                                                            <Form.Tr>
                                                                <Form.Label required>名称</Form.Label>
                                                                <Form.Content>
                                                                    <span className="textShow">{itemData.title}</span>
                                                                </Form.Content>
                                                            </Form.Tr>
                                                            <Form.Tr>
                                                                <Form.Label required>当前汇总得分</Form.Label>
                                                                <Form.Content>
                                                                    <span className="textShow">{page=='apply'?dealNumber(itemData.zpScore):dealNumber(itemData.auditScore)}</span>
                                                                </Form.Content>
                                                            </Form.Tr>
                                                        </Form.Table>
                                                    </Form>
                                                </div>
                                        }

                                    </div>
                                </div>
                        }

                    </Panel.Body>
                </Panel>
             </div>
        );
    }
}
export default SelfEvaluation
