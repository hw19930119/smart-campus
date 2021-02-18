/*
 * @(#) PersonalIndex
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * Copyright:  Copyright (c) 2018
 * Company:厦门畅享信息技术有限公司
 * @author: huangqz
 * 2018/8/9 16:19
 */
import React from 'react';
import PageRouter from "@share/scurd/block/pageRouter/pageRouter";
import SchApproveInfo from "../../../components/ui/SchApproveInfo";
import {getLoginUser, revokeAttestation} from '../../../services/requestFunc';
import {clearAll, removeByName, setData} from '../../../utils/StoreUtil';
import BaseComponent from '../../../components/BaseComponent';
import './Home.scss';

class Home extends BaseComponent {
    constructor(props) {
        super(props);

        this.state = {
            loginUser: {}, //用户登录信息
            attestStateEnum: [], //状态标码
            sbSwitch:''
        }
    }

    async componentDidMount() {
        clearAll(); //首先清除所有缓存
        await this.getUserInfo();
    }

    getUserInfo = async () => {
        let _this = this;
        await getLoginUser().then(res => {
            if (res) {
                let {attestStateEnum, loginUser, schoolEnum, xzqhs, sbSwitch} = res || {};

                if (sbSwitch == '1' && !loginUser.state) {
                    _this.props.history.push('/overHome');
                }
                if (loginUser.sftz == true) {
                    window.location.href = ` /smart-campus/realLogin.html?userId=${loginUser.id}`;
                } else {
                    let data = {loginUser, schoolEnum, xzqhs}; //学校认证提交对应的参数，码表
                    _this.setState({
                                        sbSwitch,
                                       attestStateEnum,
                                       loginUser,
                                       state: loginUser.state || ''
                                   })
                    removeByName('roleInfo');
                    setData('roleInfo', data);

                }

            }
        })
    }

    reBack = async () => {
        let {loginUser} = this.state;
        let flag = await window.confirms('是否确定撤销认证？');
        if (!flag) {
            return;
        }
        revokeAttestation(loginUser).then(res => {
            if (res && res.status) {
                window.success(res.msg);
                window.location.reload(true)
            } else {
                window.error(res.msg);
                return
            }
        })
    }

    toPath = (path) => {
        this.props.history.push(path)
    };

    render() {
        let {state, loginUser,sbSwitch} = this.state;
        return (
            <div className="apply-con">
                {
                    (sbSwitch == '0' || loginUser.state )&& <React.Fragment>
                        <SchApproveInfo
                            loginUser={loginUser}
                            toPath={this.toPath}
                            reBack={this.reBack}/>
                        {state == 'A03' && //新增yxsb的校验，防止刷新两遍
                        <PageRouter
                            page='query'
                            g_schema_key="VW_APPLY_LIST_FA"
                            g_inline="false"/>
                        }
                    </React.Fragment>
                }

            </div>
        );
    }
}

export default Home;
