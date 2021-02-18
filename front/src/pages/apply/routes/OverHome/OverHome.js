/*
 * @(#) OverHome.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author lihui
 * <br> 2020-10-29 14:54:08
 */
import React from "react";
import { Button } from '@share/shareui';
import door from "../../../../assets/images/door.png";
import "./OverHome.scss";
class OverHome extends React.Component{
    constructor(){
        super();
    }
    render(){
        return(
            <div className="setBackground">
                <div className="over-home">
                <div className="img-box">
                    <img src={door} />
                </div>
                <div className="info-box">
                    <h1>申请报名已结束</h1>
                    <p>本年度“智慧校园”学校申报网上报名已经截止，如有疑问，请联系管理员。</p>
                    <Button type="button" bsStyle="primary" onClick={()=>{
                        window.location.href = `https://www.xmedu.cn/door/index`;
                    }}>返回首页</Button>
                    </div>
                </div>
            </div>
        )
    }
}
export default OverHome;
