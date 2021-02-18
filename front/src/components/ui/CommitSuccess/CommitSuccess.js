import React, {Component} from 'react';
import {Panel,Button} from '@share/shareui';
import applyOk from '../../../assets/images/apply-ok.png';
import './CommitSuccess.scss';

class CommitSuccess extends Component {

    constructor(props){
        super(props);
    }
    toIndex =(path)=>{
        this.props.onPath(path);
    }
    render() {
        return (
            <Panel>
                <Panel.Body>
                    <div className="commitSuccess">
                        <img src={applyOk} alt=""/>
                        <h3 className="title text-success">提交成功</h3>
                        <p className="text-danger">温馨提醒</p>
                        <hr />
                        <p className="text-default">提交成功，请到审核列表查看申报进度！</p>
                        <div className="btnBox">
                            <Button type="button" onClick={() =>this.toIndex('/')}>返回</Button>
                        </div>
                    </div>
                </Panel.Body>
            </Panel>
        );
    }
}

export default CommitSuccess;
