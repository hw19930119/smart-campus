
import React from 'react';
import {Panel} from '@share/shareui';
import './ApplyProgress.scss';

const ApplyProgress = (props) => {
    let {title,data} = props;

    return (
        <div className="progress-con">
            <Panel bsStyle="primary">
                <Panel.Head title={title}/>
                <Panel.Body>
                    <table className="progress-table">
                        <thead>
                            <tr className="progress-table-title">
                                <th>申报阶段</th>
                                <th>审核角色</th>
                                <th>审核时间</th>
                                <th>操作</th>
                                <th>审核结果</th>
                                <th>操作人</th>
                                <th>处理说明</th>
                            </tr>
                        </thead>
                        <tbody>
                        {
                            data && data.length > 0 ? data.map((item,index)=>{
                                return (
                                    <tr key={index}>
                                        <td>{item.NODE_NAME}</td>
                                        <td>{item.ROLE_NAME}</td>
                                        <td>{item.AUDIT_TIME}</td>
                                        <td>{item.RESULT}</td>
                                        <td>{item.BIZ_RESULT}</td>
                                        <td>{item.USER_NAME}</td>
                                        <td>{item.OPINON}</td>
                                    </tr>
                                )
                            }): <tr>
                                <td colSpan={7} style={{textAlign:'center'}}>暂无审核记录</td>
                            </tr>
                        }

                        </tbody>
                    </table>
                </Panel.Body>
            </Panel>
        </div>
    );
}
export default ApplyProgress;
