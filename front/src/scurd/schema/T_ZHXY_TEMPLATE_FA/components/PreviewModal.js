import React from 'react';
import {findManagerFileList,changeCategory,deleteCategory,getTreePreviewByTemplateId} from '@/services/requestFunc';
import SelfEvaluation from '../../../../components/ui/SelfEvaluation/SelfEvaluation';
class IndexAllocation extends React.Component{
    constructor(props){
        super(props)
        this.state = {
            templateId: this.props.templateId,
            treeList:{},
            expandedKeys:[],
            allocationData:{},
            selectTreeCode:'',
            type:'',
        }
    }
    async componentDidMount() {
        await this.getData();
    }

    getData = async () => {
        let {templateId=''} = this.state;
        getTreePreviewByTemplateId(templateId).then(res => {
            if(res){
                this.setState({
                    treeList:res,
                })
            }
        })
    }

    handleTree = (data, execute) => {
        if(execute == 'goto'){
            this.setState({allocationData:data,type:execute})
        }
    };
    cancel() {
        this.props.onClose();
    }
    render(){
        let {treeList} = this.state;
        return (
            <div className="preview-template">
                <SelfEvaluation treeData={treeList} readOnly={true} page="preview"></SelfEvaluation>
            </div>
        )
    }
}
export default IndexAllocation;