
import React from 'react';
import {Panel} from '@share/shareui';
import './ReturnRecord.scss';

const ScoreRecord = (props) => {
    let {title,data,page} = props;
    return (
        <div className="return-record">
            <Panel bsStyle="primary">
                <Panel.Head title={title}>
                </Panel.Head>
                <Panel.Body>
                    <table>
                        {
                            page=='apply'?
                                <colgroup>
                                <col width={'40%'}/>
                                <col width={'20%'}/>
                                <col width={'30%'}/>
                                <col width={'10%'}/>
                            </colgroup>:
                                <colgroup>
                                    <col width={'10%'}/>
                                    <col width={'10%'}/>
                                    <col width={'10%'}/>
                                    <col width={'10%'}/>
                                    <col width={'10%'}/>
                                    <col width={'20%'}/>
                                    <col width={'30%'}/>
                                </colgroup>
                        }
                        <thead>
                        {
                            page=='apply' ?
                                <tr className="progress-table-title">
                                <th>退回理由</th>
                                <th>退回部门</th>
                                <th>退回时间</th>
                                <th>退回状态</th>
                            </tr>:
                                <tr className="progress-table-title">
                                    <th>评分角色</th>
                                    <th>评分人</th>
                                    <th>评分结果</th>
                                    <th>是否退回</th>
                                    <th>状态</th>
                                    <th>处理时间</th>
                                    <th>评分说明</th>
                                </tr>
                        }

                        </thead>
                        <tbody>
                        {
                            data && data.map((item,index)=>{
                                if(page=='apply'){
                                    return (
                                        <tr key={index}>
                                            <td>{item.opinion}</td>
                                            <td>{item.roleName}</td>
                                            <td>{item.auditTime}</td>
                                            <td>{item.historyFlag=='1'?'历史':'当前'}</td>
                                        </tr>
                                    )
                                }
                                return (
                                    <tr key={index}>
                                        <td>{item.roleName}</td>
                                        <td>{item.auditPersonName}</td>
                                        <td>{item.zpScore}</td>
                                        <td>{item.giveBack=='1'?'是':'否'}</td>
                                        <td>{item.historyFlag=='1'?'历史':'当前'}</td>
                                        <td>{item.auditTime}</td>
                                        <td>{item.opinion}</td>
                                    </tr>
                                )
                            })
                        }

                        </tbody>
                    </table>
                </Panel.Body>
            </Panel>
        </div>
    );
}
export default ScoreRecord;
