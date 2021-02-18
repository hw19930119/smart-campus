import { handleStyle } from '@share/ulynlist4react';

import '@share/datagrid/dist/css/datagrid.css';
import React, { Fragment } from 'react';
import {  ModalTool } from '@share/shareui';
import { Column, Ulynlist } from '@share/datagrid';
import FiledInfoList from '../../../components/business/FiledInfoList/FiledInfoList';

class Renderer extends React.Component {
    constructor(props) {
        super(props);
        this.xmTransExtFunc = this.xmTransExtFunc.bind(this);
        this.detail = this.detail.bind(this);
        this.edit = this.edit.bind(this);
        this.execute = this.execute.bind(this);


    }

    xmTransExtFunc(val, line) {
        return `<a class='detail-bt btn-link' data-id='${line.ID}' href="javascript:void(0)">${val}</a>`;
    }

    detail(data) {
        const g_id  = data.ID;
        const { toPage } = this.props.extra.queryListProps;
        toPage('detail', {
            g_schema_key: 'T_POLICY_CG_FIELD_CONFIG_01',
            g_back_schema_key: 'T_POLICY_CG_FIELD_CONFIG_01', g_id
        });
    }

    edit(data) {
        const g_id  = data.ID;
        const { toPage } = this.props.extra.queryListProps;
        toPage('add', {
            g_schema_key: 'T_POLICY_CG_FIELD_CONFIG_01',
            g_back_schema_key: 'T_POLICY_CG_FIELD_CONFIG_01', g_id
        });
    }


    componentDidMount() {

    }


    // 点击删除按钮，触发模态框
    modalTrigger = props => {
        return new ModalTool({
                 title: '提示',
                 bsStyle: 'warning',
                 content: '确定删除?',
                 onOk: () => {   // 点击确定后，需要执行的回调函数
                   this.props.extra.queryList.del(props.ID);
                 }
           });
    }


    // 根据操作判断如何处理回调的数据
    execute(command, data) {
        switch (command) {
            case 'edit':
                this.edit(data);
                break;
            case 'modalTrigger':
                this.modalTrigger(data);
                break;
            case 'detail':
                this.detail(data);
                break;
            default:
                break;
        }
    }

    render() {
        const { list } = this.props;
        const { g_schema_key } = this.props.extra.queryListProps;


        const itemOption = {
            isEditable: false, // 是否使用
            isMainTitle: true, // 是否为页面主标题
            hasIcon: false // 是否采用图标
        };

        $('#js-tablePageBar--T_POLICY_SD_DOC_INFO_HISTORY_TAB').remove(
        );


        // 列表项的中英文对照
        const itemTranslate = {
            enName: '英文名：',
            fieldCategory: '所属分类：',
            fieldControl: '字段控件：',
            createTime: '创建时间：',
            createDept: '创建部门：',
            promptMsg: '提示语：',
        };

        const transList = [];

        list.map(item => {
            const obj = {
                ID: item.FIELD_ID,
                title: item.FIELD_CN_NAME,
                enName: item.FIELD_EN_NAME,
                fieldCategory: item.FIELD_CATEGORY,
                fieldControl: item.FIELD_CONTROL,
                createTime: item.CREATE_TIME,
                createDept: item.CREATE_DEPT_ID,
                promptMsg: item.PROMPT_MSG,
            };
            transList.push(obj);
            return item;
        });

        // 列表项右侧操作label的配置
        let operations = [
            {
                name: '详情',
                execute: 'detail'
            },
            {
                name: '编辑',
                execute: 'edit'
            },
            {
                name: '删除',
                execute: 'modalTrigger'
            }
        ];

        // 列表项右侧操作label的配置
        let operations2 = [
            {
                name: '详情',
                execute: 'detail'
            }
        ];

        return (
            <Fragment>
                {/* 列表页分成左右两部分，，左边是数据，右边是操作 */}
                <div className="fileManageList">
                    {/* 不使用分页 */}

                    <FiledInfoList
                        data={transList}
                        translate={itemTranslate}
                        labelOption={operations}
                        commonOption={operations2}
                        onExecute={this.execute}
                        column={3}
                    />
                </div>
            </Fragment>

        );
    }

}

export default Renderer;
