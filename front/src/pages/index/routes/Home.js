import React from 'react';
import TreeList from "../../../components/business/TreeList/index";
import FormList from "../../../components/business/FormList/index";
import {findBasicFileList} from '@/services/requestFunc';
import './Home.scss'


class Home extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            treeList:[],
            nowFormList:[],
            selectTreeCode:''
        }
    }

    async componentDidMount() {
        await this.getData();
    }

    getData = () => {
        findBasicFileList().then(res => {
            if(res){
                this.setState({
                    treeList:res.fieldConfig,
                    nowFormList:[res.lastList[0]],
                    selectTreeCode:res.lastList[0].key
                })
            }
        })
    }

    handleTree = (data, execute) => {
        if(execute == 'goto'){
            this.setState({nowFormList:[data]})
        }

    };



    render() {
        let {treeList,nowFormList,selectTreeCode} = this.state;
        return (
            <div className="zb-con">
                <div className="tree-con">
                    <TreeList data={treeList}
                              handleClick={this.handleTree}
                              activeCode={selectTreeCode}
                              canDrag={false}
                              canEdit={false}
                    />
                </div>
                <div className="zb-content">
                    <FormList data={nowFormList}/>
                </div>
            </div>

        );
    }


}

export default Home;