import React from 'react';
import {Icon} from '@share/shareui';
import BaseComponent from '../../BaseComponent';
import PageRouter from "@share/scurd/block/pageRouter/pageRouter";
import {getDataFromStore} from "../../../utils/StoreUtil";
import './BasicInfo.scss';

class BasicInfo extends BaseComponent {
    constructor(props) {
        super(props);
        let {_g_id,_state,_pc_state} = getDataFromStore('_basicIds') || '';
        this.state = {
            _g_id, //从缓存中获取最新的g_id
            _state, //从缓存中获取最新的state
            _pc_state, //从缓存中获取最新的pc_state
        }

    }

    render() {
        let {g_id, pcNo, state, result, pcState} = this.props;
        let {_g_id,_state,_pc_state} = this.state;
        // g_id有值，则回填数据
        // let id = g_id && g_id != '' ? g_id : this.state.g_id;
        let id = _g_id && _g_id != '' ? _g_id : g_id; // 优先取缓存中的值
        let new_state = _g_id && _g_id != '' ? _state : state; // 优先取缓存中的值
        let new_pc_state = _g_id && _g_id != '' ? _pc_state : pcState; // 优先取缓存中的值
        return (
            <div className="BasicInfo">
                {/* 驳回原因展示 */}
                {Object.keys(result).length > 0 && <div className="back-reason-con">
                    <p className="reason-title"><Icon className="si si-xy_xx"/><b>{result.NODE_NAME}{result.RESULT}</b></p>
                    <div>
                        <span>{result.RESULT}理由：</span>
                        <ul>
                            <li>{result.OPINON}</li>
                        </ul>
                    </div>
                </div>}
                <PageRouter
                    page='add'
                    g_id={id || ''}
                    pcNo={pcNo || ''}
                    state={new_state || ''}
                    pcState={new_pc_state || ''}
                    g_schema_key="T_ZHXY_BASEINFO_FA"
                    g_inline="false"/>

            </div>
        );
    }
}

export default BasicInfo;
