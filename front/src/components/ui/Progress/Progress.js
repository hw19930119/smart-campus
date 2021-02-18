/*
 * @(#) Progress.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author liuxia
 * <br> 2019/9/2 18:47
 */
//样式引入
//react基础依赖
import React, { Component } from 'react';
import {
    Panel,
    ProgressStep,
} from '@share/shareui';
import './Progress.scss';
class Progress extends Component {
    /* 构造函数 */
    constructor(props) {
        super(props);
        //初始化状态 */
        this.state = {};
    }
    render() {
        let {progress} = this.props;
        return (
            <Panel className="progressBox">
                <Panel.Body>
                    <ProgressStep
                        data={[
                            {
                                label: '申报须知'
                            },
                            {
                                label: '基本信息'
                            },
                            {
                                label: '自评项目'
                            },
                            {
                                label: '提交审核'
                            }
                        ]}
                        activeIndex={progress}
                    />
                </Panel.Body>
            </Panel>
        )
    }
};
export default  Progress;
