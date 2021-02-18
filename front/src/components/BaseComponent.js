/*
 * @(#) BaseComponent.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author liuxia
 * <br> 2019-09-02 17:45:20
 */

import React, {Component} from "react";
import ModalTool from "@share/shareui/es/components/ModalTool";

class BaseComponent extends Component {

    /* 构造函数 */
    constructor(props) {
        super(props);
        window.success = function (content) {
            new ModalTool({
                title: '',
                bsStyle: 'success',
                closeBtn: false,
                backdrop: 'static',
                content: content,
                cancelText: null
            });
        }
        let openconfirm = async function(content,bsStyle){
            return new Promise((resolve,reject) =>{
                new ModalTool({
                        title: '提示',
                        bsStyle: bsStyle?bsStyle:'warning',
                        closeBtn: false,
                        backdrop: 'static',
                        content: content,
                        onOk: () => {
                            //alert("ok")
                            resolve(true);
                        },
                        onCancel: () => {
                            //alert("cancel")
                            resolve(false);
                        }
                    }
                );
            });
        }
        //提示
        window.confirms = async function(content,bsStyle){
            return await openconfirm(content,bsStyle);
        }
        //普通信息
        window.info = function (content) {
            new ModalTool({
                title: '提示信息',
                okAutoClose: true,
                cancelAutoClose: true,
                bsStyle: null,
                bsSize: 'sm',
                content: content,
                closeBtn: true,
                backdrop: true,
                cancelText: '我知道了',
                okText: null
                // showModalFooter: false
            });
        }
        //错误信息
        window.error = function (content) {
            new ModalTool({
                title: '',
                bsStyle: 'warning',
                closeBtn: false,
                backdrop: 'static',
                content: content,
                cancelText: null
            });
        }

    }

    //组件将被卸载
    componentWillUnmount() {
        //重写组件的setState方法，直接返回空
        this.setState = (state, callback) => {

        }
    }
}
export default BaseComponent;
