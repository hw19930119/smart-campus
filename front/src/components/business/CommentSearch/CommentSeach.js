/*
 * @(#) CommentSeach.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author mana
 * <br> 2020-11-10 10:47:06
 */

import React from 'react';
class CommentSeach extends React.Component{
    constructor(props){
        super(props)
    }
    render(){
        return (
            <div className="comment-search">
                <Form pageType="queryPage">
                    <div className="form-option info-list-show ">
                        <div className="item w-percent-100">
                            <label>查询条件</label>
                            <div className="sub-item sub-text-item">
                                <div className="select-item">
                                    <em>当前环节：全部</em>
                                    <i className="fa fa-close"/>
                                </div>
                                <div className="select-item">
                                    <em>姓名：张三丰</em>
                                    <i className="fa fa-close"/>
                                </div>
                                <div className="select-item">
                                    <em>行政区划：厦门市</em>
                                    <i className="fa fa-close"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <FormItem>
                        <FormItem.Label>行政区划</FormItem.Label>
                        <FormItem.Content>
                            <div>
                                <Select options={[
                                    {
                                        label: '集美区',
                                        value: ''
                                    }
                                ]}/>
                                <Select options={[
                                    {
                                        label: '集美区',
                                        value: ''
                                    }
                                ]}/>
                                <Select options={[
                                    {
                                        label: '集美区',
                                        value: ''
                                    }
                                ]}/>
                            </div>
                        </FormItem.Content>
                    </FormItem>
                </Form>
            </div>
        )
    }
}
export default  CommentSeach;