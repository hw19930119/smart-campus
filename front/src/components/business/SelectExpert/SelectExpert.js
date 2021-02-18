/*
 * @(#) SelectExpert.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author mana
 * <br> 2020-10-30 17:16:05
 */
import React from 'react';
import {Checkbox} from 'antd';
import {Button, Panel} from '@share/shareui';
import './style.scss'
import BaseComponent from '../../BaseComponent';
import {throttle} from "../../../utils/util";

class SelectExpert extends BaseComponent {
    constructor(props) {
        super(props);
        this.state = {
            defaultValue: [],
            value: [],
            disabledArr: [],
            checkedValue: []
        }
        this.submit = throttle(this.submit)
    }

    /**
     * 切换选择专家
     * @param value
     */
    onChange = (value) => {
        let {options = [], changeExpertField} = this.props;
        value = options.filter((item) => {
                let falg = parseInt(item.falg);
                return falg < 1 && value.includes(item.userId)
            }
        ).map(item => item.userId);
        let newValue = [], disabledArr = [];
        options.map((item => {
            let falg = parseInt(item.falg);
            let {userId} = item;
            if (falg < 1 && value.includes(item.userId)) {
                newValue.push(userId)
            }
            if (falg > 0 || (value.length > 0 && !value.includes(item.userId))) {
                disabledArr.push(userId)
            }
        }))
        this.setState({
            checkedValue: value,
            value: newValue,
            disabledArr: disabledArr
        }, () => {
            changeExpertField && changeExpertField(newValue.join(','))
        })
    }
    submit = () => {
        let {value} = this.state;
        let {handleSelect} = this.props;
        if (value.length != 1) {
            window.error('请选择一位专家！');
            return;
        }
        handleSelect && handleSelect(value);
    }

    componentWillReceiveProps(next) {
        if (JSON.stringify(this.props.rnsNum) != JSON.stringify(next.rnsNum)) {
            this.setState({
                checkedValue: [],
                disabledArr: [],
                value: []
            })
        }
    }
    render() {
        let {disabledArr, checkedValue} = this.state;
        let {options = []} = this.props;
        return (
            <div className="list-groups">
                <Panel>
                    <Panel.Head title="选择专家">
                    </Panel.Head>
                    <Panel.Body>
                        <div className="expert-list">
                            <Checkbox.Group onChange={this.onChange} value={checkedValue}>
                                {
                                    options && options.map((item, index) => {
                                        let falg = parseInt(item.falg);
                                        let checked = falg > 0;
                                        let disabled = disabledArr.includes(item.userId) || checked;
                                        return (
                                            <Checkbox value={item.userId} key={item.userId}
                                                      disabled={disabled}>{item.userName}</Checkbox>
                                        )
                                    })
                                }
                            </Checkbox.Group>
                            <Button bsStyle="primary" type="button" bsSize="large" onClick={this.submit}>分配</Button>
                        </div>

                    </Panel.Body>
                </Panel>

            </div>
        )
    }
}

export default SelectExpert;