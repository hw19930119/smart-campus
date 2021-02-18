/*
 * @(#) field_num.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author wuxw
 * <br> 2020-05-18 11:42:35
 * ————————————————————————————————
 *    修改记录
 *    修改者：
 *    修改时间：
 *    修改原因：
 * ————————————————————————————————
 */

import React, {  Fragment } from 'react';

import BaseComponent from '@share/scurd/component/BaseComponent.js';

class fieldNum extends BaseComponent{
    constructor(props){
        super(props);
        this.state = {
            numData: '0' // 默认为0
        };
    }

    componentDidMount =() => {
        // 小数点位数
        const numLimit = this.props.stateChange('decimalPlaces');

        if (!numLimit) {
            const { itemData, stateChange } = this.props;

            stateChange(itemData.column, this.state.numData);
        } else {
            this.setState({
                numData: `${numLimit}`
            });
        }

    }

    setValue = val => {
        this.setState({
            numData: val
        });

        const { itemData, stateChange } = this.props;

        stateChange(itemData.column, val);
    }

    isValidate(val) {
        const { numData } = this.state;

        if (!numData) {
            return {
                status: false,
                error: ('请输入小数点位数')
            };
        }
        const { itemData } = this.props;

        return BaseComponent.validate(itemData, super.val(), super.isBlank(), this);
    }


    render(){
        const { numData } = this.state;

        return (
            <div>
                <input
                    {...super.defaultScurdProps()}
                    style={{ width: 50 }}
                    type="number"
                    value={numData}
                    onChange={
                        v => {
                            const val = v.target.value;
                            const pattern = /^[0-9][0-9]*$/;

                            if (!pattern.test(val)) {
                                alert('位数不能小于0');
                            }
                            let data =  val.replace(/\b(0+)/gi, '');

                            if (data <= 0) {
                                data = 0;
                            }
                            this.setValue(`${data}`);

                        }
                    }
                /> 位
            </div>
        );
    }
}

export default fieldNum;
