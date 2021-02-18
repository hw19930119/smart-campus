/*
 * @(#) Portal.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 * 
 * Copyright:  Copyright (c) 2017
 * Company:厦门畅享信息技术有限公司
 * @author Enboga
 * 2017-10-17 20:09
 */

/* 样式依赖 */

/* react基础依赖 */
import React, {Component} from 'react';

import ShareuiPortal from '@share/shareui-portal';

import {SiteHeader,NavItem} from '@share/shareui';
import {getMenusByLoginRoleId} from '../services/requestFunc';

/* 门户入口 */
class Portal extends Component {

    /* 构造函数 */
    constructor(props) {
        super(props);

        //初始化状态
        this.state = {
            menus:[],
            apps:[]
        };

        this.handelAppClick = this.handelAppClick.bind(this);
        this.handelMenuClick = this.handelMenuClick.bind(this);
    }

    async componentDidMount(){
        await this.getData();
    }

    //获取门户菜单
    getData = async()=>{
        await getMenusByLoginRoleId().then(res=>{
            if(res && res.menuJson){
                this.setState({apps: res.menuJson.apps || []})
            }
        })
    }

    //点击APP应用
    handelAppClick(item) {
        let {apps} = this.state;
        this.setState({menus:apps.getMenus(item.key)});
    }

    //点击菜单
    handelMenuClick(item) {
        console.info('onMenuClick：你点击了菜单'+ item.label, item);
    }


    render() {
        let {apps} = this.state;
        return (
            <ShareuiPortal
                logo={require('@share/shareui-portal/example/static/image/logo.png')}
                title="智慧校园创建申报管理"
                subTitle=""
                headerStyle={{background:'url(require("@share/shareui-portal/example/static/image/ico_header_bg.png")) #09d left top no-repeat'}}//自定义背景
                sidebarModel="menu"
                menus={apps}
                // menus={apps.apps}
                onAppClick={(item)=>this.handelAppClick(item)}
                onMenuClick={(item)=>this.handelMenuClick(item)}
                selectedAppId="message"
                selectedMenuId=""
                useSelectedStyle={false}
                listenMessenger={(msg)=>console.info('门户接收到iframe的消息：',msg)}
            >
                <SiteHeader.Nav>
                    <NavItem icon="fa fa-windows" onClick={() => {}} />
                </SiteHeader.Nav>
            </ShareuiPortal>
        )
    }
}

export default Portal;
