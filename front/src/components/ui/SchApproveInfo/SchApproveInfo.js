
import React from 'react';
import {Button,Icon} from '@share/shareui';
import './SchApproveInfo.scss';

const SchApproveInfo = (props) => {
    let {loginUser,toPath,reBack} = props;
    let {state,schoolName,opinion} = loginUser || '';
    //A01未认证，A02认证中，A03认证通过，A04认证不通过
    let path = !state || state == 'A01' || state == 'A03' || state == 'A04'  ? '/schApprove/0' :
                state == 'A02' ? '/schApprove/1' : '/';

    return (
        <div className="check-con">
            <ul>
                <li>
                    <Icon className="fa si si-app_rz"/>
                    <p>
                        <b>学校授权认证</b>
                        <span>通过学校授权后，可进行智慧校园创建申报</span>
                    </p>
                </li>
                <li>
                    {state == 'A03' ? <b className="isChecked-b">认证通过</b>  :
                        state == 'A04' ? <b className="isChecked-fail-b">认证不通过</b> :
                            state == 'A02' ? <b className="isChecked-ing-b">认证中</b> : <b>未认证</b>}

                    {state == 'A03' ? <p>{schoolName}</p> :
                        state == 'A02' || state == 'A04' ? <p className="reason-p">{opinion || ''}</p> : ''}
                </li>
                <li>
                    <Button bsStyle="info" onClick={()=>toPath(path)}>
                        {state == 'A03' || state == 'A04' ? '修改认证' :
                            state == 'A02' ? '查看认证': '申请认证'}
                        </Button>
                    {state == 'A02' && <Button onClick={()=>reBack()} type="button" bsStyle="primary">撤销认证</Button>}
                </li>
            </ul>
        </div>
    );
}
export default SchApproveInfo;
