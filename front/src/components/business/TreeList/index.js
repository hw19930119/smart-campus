/**
 * TreeList.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 * Copyright:  Copyright (c) 2019

 * @Company:厦门畅享信息技术有限公司
 * @author: liuxia
 * @date: 2020-7-23 19:51:40
 */
import PropTypes from 'prop-types';
import { Tree, Input } from 'antd';
const { TreeNode } = Tree;
const { Search } = Input;
import React from 'react';
import styles from './TreeList.scss';
import {dealNumber,sectionToChinese} from "../../../utils/util";

class TreeList extends React.Component {
    state = {
        expandedKeys: [], // 选定的要展开的树节点
        autoExpandParent: false, // 是否自动展开
        curNodeKey: null, // 当前节点的key
        highLightKey: [], // 对选中的树节点进行高亮
        multiple: false, // 默认不支持多选
        toggled: false, // 是否折叠，默认不折叠
        modalShow:false,
        checkedKeys:[],
        disabledChekedTreeNode:[]
    };

    formaterInit(props){
        const { activeCode = '',expandedKeys,page,chekedTreeNode,disabledChekedTreeNode } = props;
        this.selectTrigger([activeCode]);
        this.setState({
            expandedKeys: expandedKeys?expandedKeys:[activeCode],
            autoExpandParent:(page=='manager' || page=='expert')?false: true,
            activeCode:activeCode,
            checkedKeys:chekedTreeNode || [],
            disabledChekedTreeNode:disabledChekedTreeNode||[]
        });
    }
    // 页面初始化，根据activeCode去选中高亮
    componentDidMount() {
        this.formaterInit(this.props);
    }

    // 接收数据，判断是否更新
    componentWillReceiveProps(next) {
        let {page} = this.props;
        if (this.props.activeCode !== next.activeCode) {
            this.formaterInit(next)
        }
        if(page=='expert'){
            if(JSON.stringify(this.props.chekedTreeNode)!=JSON.stringify(next.chekedTreeNode)){
                this.setState({
                    checkedKeys:next.chekedTreeNode
                })
            }
            if(JSON.stringify(this.props.disabledChekedTreeNode)!=JSON.stringify(next.disabledChekedTreeNode)){
                this.setState({
                    disabledChekedTreeNode:next.disabledChekedTreeNode
                })
            }
        }

    }
    // 展开和折叠
    onExpand = (key) => {
        let {treeOnExpand} = this.props;
        this.setState({
            autoExpandParent: false,
            expandedKeys: key,
            multiple: false
        },()=>{
            treeOnExpand && treeOnExpand(key)
        });
    }
    // 点击触发事件,传递数据和对应的指令给父组件
    handleSelect = (item = {}, execute = '',e,initIndex)=> {
        e && e.stopPropagation();
        let activeCode = [item.key];
        this.selectTrigger([...activeCode]);
        const { handleClick }  = this.props;
        handleClick && handleClick(item, execute,initIndex);
    }

    // 点击触发效果,展示高亮
    selectTrigger = item => {
        const keys = [];
        const key = item[item.length - 1];
        keys.push(key);
        this.setState({
            highLightKey: keys,
            multiple: false
        });
    }

    // 折叠功能
    changeToggle = () => {
        const { toggled } = this.state;
        this.setState({
            toggled: !toggled
        });
    }

    // 模糊搜索功能
    handleSearch = (tree, word) => {
        if (!word) return;
        const collect = [];

        // 内部遍历，找出所有符合的结果，展开和高亮
        const findCode = (t, w) => {
            Array.isArray(t) && t.map(item => {
                const { title = '' } = item;

                if (title.indexOf(w) > -1) {
                    collect.push(item.key);
                }
                if (item.children) {
                    findCode(item.children, w);
                }
            });
        };

        findCode(tree, word);
        this.setState({
            expandedKeys: collect,
            highLightKey: collect,
            autoExpandParent: true,
            multiple: true,
        });
    }

    onCheck  = (checkedKeys, info)=>{
        let {onTreeCheck}  = this.props;
        let {checkedNodes} = info;
        this.setState({
            checkedKeys:checkedKeys
        },()=>{
            let newSelectedUnit = checkedNodes.filter((item)=>{
                return item.props.children == undefined
            }).map(i=>{
                return i.key
            });
            onTreeCheck && onTreeCheck(newSelectedUnit)
        })

    }
    render() {
        const {
            data = [],
            canToggle = false,
            canEdit = false,
            errorKeys,
            page ='',
            type='',
            checkable
        } = this.props;
        const {
            autoExpandParent,
            expandedKeys,
            highLightKey,
            multiple,
            toggled,
            checkedKeys,
            disabledChekedTreeNode
        } = this.state;

        const prefix = '(';
        const postfix = ')';

        const loop = (arr,initIndex) => arr.map((item,index) => {
            let newIndex = index+1;
            let sortIndex = (initIndex==2) ? `${sectionToChinese(newIndex)}、`:initIndex== 3?`${newIndex}、`:initIndex==4?`(${newIndex}) `:'';
            // 树的内容，包含文本以及操作icon
            let defaultValue = item.defaultValue && item.defaultValue.length>0 ? item.defaultValue[0] :{};
            let isComplete = page == 'apply' ? item.state == '0':page=='expertAudit'?defaultValue.codeKey:item.shState=='1';
            let ifBack = page == 'apply'? item.ifEdit == 'giveback':item.giveback=='1';
            let disabeledNode =disabledChekedTreeNode &&  disabledChekedTreeNode.includes(item.key);
            const label = (
                <div className="title-con">
                    {
                        (!canEdit &&  item.child && (type!='detail' || page=='expertAudit'))  &&
                        <span className="icon">
                             {
                                 ifBack ? <i className="backed">
                                     <svg t="1597829907137" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1160" width="64" height="64"><path d="M464 720a48 48 0 1 0 96 0 48 48 0 1 0-96 0zM480 416v184c0 4.4 3.6 8 8 8h48c4.4 0 8-3.6 8-8V416c0-4.4-3.6-8-8-8h-48c-4.4 0-8 3.6-8 8z" p-id="1161" fill="#d81e06"></path><path d="M955.7 856l-416-720c-6.2-10.7-16.9-16-27.7-16s-21.6 5.3-27.7 16l-416 720C56 877.4 71.4 904 96 904h832c24.6 0 40-26.6 27.7-48z m-783.5-27.9L512 239.9l339.8 588.2H172.2z" p-id="1162" fill="#d81e06"></path></svg>
                                  </i>: (page=='apply' && item.ifEdit=='givebackChanged')?<i className="si si-com_zq2 orange" />: isComplete ?  <i className="si si-com_zq2 success"></i>:false
                             }

                            {
                               errorKeys && errorKeys.includes(item.key) && <i className="si si-app_jgts warning"></i>
                            }
                            </span>

                    }

                    <p className={`tree-text text-level${initIndex}`}
                       onClick={(e) => this.handleSelect(item, 'goto',e)}>

                        <span
                            className="text"
                            // className={styles.label}
                            title={item.title}
                        >
                            {`${sortIndex}${item.title}`}
                        </span>
                        {
                            page!='expert' &&  <span className="score">
                            {
                                item.children ?
                                    `${prefix}${dealNumber(item.score)}分${postfix}`
                                    : ''
                            }
                        </span>
                        }

                        {
                            item.children && (page=='apply' || page=='audit')  && isComplete &&  <span className="self-score">{page=='apply'?dealNumber(item.zpScore):dealNumber(item.auditScore)}分</span>
                        }

                    </p>
                        {
                            canEdit &&
                            (
                                <span className={styles.iconGroup} style={{right:initIndex==2?'11px':initIndex=='3'?'12px':initIndex==4?'13px':initIndex==5?'14px':''}}>
                                    {
                                        item.children &&  <i
                                            // style={{ visibility: curNodeKey === item.key ? 'visible' : 'hidden' }}
                                            onClick={
                                                (e) => this.handleSelect(item, 'add',e,initIndex)
                                            }
                                            className="si si-com_plusthin"
                                        />
                                    }

                                    <i
                                        // style={{ visibility: curNodeKey === item.key ? 'visible' : 'hidden' }}
                                        onClick={
                                            (e) => this.handleSelect(item, 'edit',e,initIndex)
                                        }
                                        className="si si-com_edit"
                                    />

                                    <i
                                        // style={{ visibility: curNodeKey === item.key ? 'visible' : 'hidden' }}
                                        onClick={
                                            (e) => this.handleSelect(item, 'del',e,initIndex)
                                        }
                                        className="si si-com_delete"
                                    />
                                </span>
                            )
                        }
                </div>
            );

            if (item.children) {
                return (
                    <TreeNode key={item.key} title={label} icon={<span className="si si-com_correct-73"/>} disableCheckbox={disabeledNode} >
                        {loop(item.children,initIndex+1)}
                    </TreeNode>
                );
            }

            return <TreeNode key={item.key} title={label} icon={<span className="si si-com_correct-73"/>} disableCheckbox={disabeledNode}/>;
        });

        const treeWidth = toggled ? '0px' : '100%';
        const overflowX = toggled ? 'hidden' : 'auto';
        return (
            <div
                className={styles.treeContent}
                // style={{ width: `${treeWidth}` }}
            >
                <div className={styles.searchTree} >
                    {/* 搜索 */}
                    <div
                        className={styles.box}
                        style={{ width: `${treeWidth}`, overflowX: `${overflowX}` }}
                    >
                        <div className={styles.searchBox}>
                            <Search
                                className={styles.searchInput}
                                style={canEdit && data.length==0 ? { marginBottom: 8, width: '80%' } : { marginBottom: 8, width: '96%' }}
                                placeholder="请输入"
                                onSearch={e => this.handleSearch(data, e)}
                            />
                            {
                                canEdit && data.length==0 && <span
                                    className={styles.addNew}
                                    onClick={(e) => {
                                        this.handleSelect({categoryType:'0',isTop:true}, 'add',e,1);
                                    }}
                                >
                               创建考评
                            </span>
                            }
                        </div>
                        {
                            (page=='apply' || page=='audit') &&
                            <ul className="score-types">
                                <li><span></span>满分</li>
                                <li><span></span>当前评分</li>
                            </ul>
                        }
                        {
                            data && data.length>0 &&
                            <Tree
                                onExpand={e => this.onExpand(e)}
                                expandedKeys={expandedKeys}
                                autoExpandParent={autoExpandParent}
                                switcherIcon={<span className="si si-com_angledown" />}
                                selectedKeys={highLightKey}
                                multiple={multiple}
                                checkable={checkable}
                                onCheck={checkable?this.onCheck:undefined}
                                checkedKeys={checkedKeys}
                            >
                                {loop(data,1)}
                            </Tree >
                        }

                    </div>

                    {
                        canToggle && (
                            <div
                                className={styles.toggle}
                                onClick={this.changeToggle}
                            >
                                <span
                                    className={toggled ? 'si si-com_sxy' : 'si si-com_sxz'}
                                />
                            </div>
                        )
                    }
                </div >
            </div>
        );
    }
}

export default TreeList;

Search.PropTypes = {
    data: PropTypes.array, // 需要传入的数据，数据格式为对象数组
    handleClick: PropTypes.func, // 点击事件的回调
    activeCode: PropTypes.string, // 需要高亮和选中效果的节点
    canToggle: PropTypes.bool, // 是否使用折叠功能
    canEdit: PropTypes.bool, // 是否可编辑指标功能
    canDrag: PropTypes.bool, // 是否使用拖拽功能
    showDel: PropTypes.array, // 传入对象数组，包含键(key)值(value)两个字段，满足条件渲染删除按钮
    showAdd: PropTypes.array // 同理，满足条件渲染添加按钮
};
