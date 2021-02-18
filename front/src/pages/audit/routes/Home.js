import React from 'react';
import { Panel } from '@share/shareui';
import './Home.scss'


export default class App extends React.Component {
    state = { };


    render() {

        return (
            <div className="pageLoad-con">
                <Panel bsStyle="primary">
                    <Panel.Head title="我的申报审核情况"/>
                    <Panel.Body>
                        <div>我的申报审核情况图表页</div>
                    </Panel.Body>
                </Panel>
            </div>
        );
    }
}
