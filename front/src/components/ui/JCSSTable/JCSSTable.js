/*
 * @(#) JCSSTable.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author liuxia
 * <br> 2020/8/6 19:03
 */
import {renderRadios,renderCheckbox,renderRadioText} from '../../../utils/util';

const JCSSTable = (props)=>{
    let {data} = props || {};
    return (
        <table className="commit-table jcss-table">
            <thead/>
            <tbody>
            <tr className="other-tr">
                <td colSpan={2} rowSpan={2} width='15%' className="td-bg">网络基础：</td>
                <td colSpan={2} width='15%' className="td-bg">接入方式：</td>
                <td colSpan={2} width='25%' className="textLeft-td">
                    {renderRadios(
                        [data.wlJrfs && data.wlJrfs.columnValue || {}],
                        data.wlJrfs.dmList
                    )}
                </td>
                <td colSpan={2} width='15%' className="td-bg">网络带宽（主干M）：</td>
                <td colSpan={2} width='25%' className="textLeft-td">
                    {data.wlKdm ? `${data.wlKdm.columnValue}` : ''}
                </td>
            </tr>
            <tr className="other-tr">
                <td colSpan={2} width='15%' className="td-bg">覆盖范围：</td>
                <td colSpan={2} width='25%' className="textLeft-td">
                    {renderRadios(
                        [data.wlFgfw && data.wlFgfw.columnValue || {}],
                        data.wlFgfw.dmList
                    )}
                </td>
                <td colSpan={2} width='15%' className="td-bg">网络中心：</td>
                <td colSpan={2} width='25%' className="textLeft-td">
                    {renderRadios(
                        [data.wlZx && data.wlZx.columnValue || {}],
                        data.wlZx.dmList
                    )}
                </td>
            </tr>
            <tr>
                <td colSpan={2} rowSpan={3} width='15%' className="td-bg">终端与功能室：</td>
                <td colSpan={2} rowSpan={2} width='15%' className="td-bg">终端数量：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    <div className="nums-con">
                        <span>台式个人电脑<b>{data.zdTsgrdn ? data.zdTsgrdn.columnValue : ''}</b>台</span>
                        <span>笔记本电脑<b>{data.zdBjb ? data.zdBjb.columnValue : ''}</b>台</span>
                        <span>平板电脑<b>{data.zdPbdn ? data.zdPbdn.columnValue : ''}</b>台</span>
                        <span>电子书包<b>{data.zdDzsb ? data.zdDzsb.columnValue : ''}</b>台</span>
                        <span>其它<b>{data.zdQt ? data.zdQt.columnValue : ''}</b>台</span>
                    </div>
                </td>
            </tr>
            <tr>
                <td colSpan={1} width='10%' className="td-bg">教师用：</td>
                <td colSpan={2} width='25%' className="textLeft-td">
                    <div className="nums-con">
                        <span><b>{data.zdJsy ? data.zdJsy.columnValue : ''}</b>台</span>
                    </div>
                </td>
                <td colSpan={1} width='10%' className="td-bg">学生用：</td>
                <td colSpan={2} width='25%' className="textLeft-td">
                    <div className="nums-con">
                        <span><b>{data.zdXsy ? data.zdXsy.columnValue : ''}</b>台</span>
                    </div>
                </td>
            </tr>
            <tr>
                <td colSpan={2} width='15%' className="td-bg">功能室数量：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    <div className="nums-con">
                        <span>多媒体教室<b>{data.gnDmtjs ? data.gnDmtjs.columnValue : ''}</b>个</span>
                        <span>计算机教室<b>{data.gnJsjjs ? data.gnJsjjs.columnValue : ''}</b>个</span>
                        <span>数字化实验室<b>{data.gnSzhsys ? data.gnSzhsys.columnValue : ''}</b>个</span>
                        <span>数字化体验室<b>{data.gnSzhtys ? data.gnSzhtys.columnValue : ''}</b>个</span>
                        <span>多功能室<b>{data.gnDgns ? data.gnDgns.columnValue : ''}</b>个</span>
                        <span>电子预览室<b>{data.gnDzyns ? data.gnDzyns.columnValue : ''}</b>个</span>
                    </div>
                </td>
            </tr>
            <tr>
                <td colSpan={2} rowSpan={3} width='15%' className="td-bg">系统软件及公共服务平台：</td>
                <td colSpan={2} width='15%' className="td-bg">门户管理：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    {renderCheckbox(
                        [data.xtMhgl && data.xtMhgl.columnValue || {}],
                        data.xtMhgl.dmList
                    )}
                </td>
            </tr>
            <tr>
                <td colSpan={2} width='15%' className="td-bg">数据管理：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    {renderCheckbox(
                        [data.xtSjgl && data.xtSjgl.columnValue || {}],
                        data.xtSjgl.dmList
                    )}
                </td>
            </tr>
            <tr>
                <td colSpan={2} width='15%' className="td-bg">开发与接入：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    {renderRadios(
                        [data.xtKfyjr && data.xtKfyjr.columnValue || {}],
                        data.xtKfyjr.dmList
                    )}
                </td>
            </tr>
            <tr>
                <td colSpan={2} rowSpan={6} width='15%' className="td-bg">应用软件：</td>
                <td colSpan={2} width='15%' className="td-bg">教育管理系统：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    {renderCheckbox(
                        [data.rjJyglxt && data.rjJyglxt.columnValue || {}],
                        data.rjJyglxt.dmList
                    )}
                </td>
            </tr>
            <tr>
                <td colSpan={2} width='15%' className="td-bg">教学应用与管理系统：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    {renderCheckbox(
                        [data.rjJyyyjglxt && data.rjJyyyjglxt.columnValue || {}],
                        data.rjJyyyjglxt.dmList
                    )}
                </td>
            </tr>
            <tr>
                <td colSpan={2} width='15%' className="td-bg">学习管理系统：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    {renderCheckbox(
                        [data.rjXxglxt && data.rjXxglxt.columnValue || {}],
                        data.rjXxglxt.dmList
                    )}
                </td>
            </tr>
            <tr>
                <td colSpan={2} width='15%' className="td-bg">师生成长管理系统：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    {renderCheckbox(
                        [data.rjSsczglxt && data.rjSsczglxt.columnValue || {}],
                        data.rjSsczglxt.dmList
                    )}
                </td>
            </tr>
            <tr>
                <td colSpan={2} width='15%' className="td-bg">教研平台：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    {renderRadios(
                        [data.rjJypt && data.rjJypt.columnValue || {}],
                        data.rjJypt.dmList
                    )}
                </td>
            </tr>
            <tr>
                <td colSpan={2} width='15%' className="td-bg">家校互通的学习社区：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    {renderCheckbox(
                        [data.rjJxhtpt && data.rjJxhtpt.columnValue || {}],
                        data.rjJxhtpt.dmList
                    )}
                </td>
            </tr>
            <tr>
                <td colSpan={2} rowSpan={4} width='15%' className="td-bg">数字教学资源：</td>
                <td colSpan={2} width='15%' className="td-bg">数字图书资源：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    {renderRadios(
                        [data.zySzts && data.zySzts.columnValue || {}],
                        data.zySzts.dmList
                    )}
                </td>
            </tr>
            <tr>
                <td colSpan={2} width='15%' className="td-bg">选修课程：</td>
                <td colSpan={6} width='70%' className="textLeft-td xxkc-td">
                    {
                        renderRadioText(
                            [data.zyXxkc && data.zyXxkc.columnValue || {}],
                            data.zyXxkc.dmList,
                            '132',
                            data.zyXxkcNum ? data.zyXxkcNum.columnValue : ''
                        )
                    }
                </td>
            </tr>
            <tr>
                <td colSpan={2} width='15%' className="td-bg">校本课程：</td>
                <td colSpan={6} width='70%' className="textLeft-td xmkc-td">
                    {
                        renderRadioText(
                            [data.zyXmkc && data.zyXmkc.columnValue || {}],
                            data.zyXmkc.dmList,
                            '134',
                            data.zyXmkcNum ? data.zyXmkcNum.columnValue : ''
                        )
                    }
                </td>
            </tr>
            <tr>
                <td colSpan={2} width='15%' className="td-bg">个性化学习资源：</td>
                <td colSpan={6} width='70%' className="textLeft-td">
                    {renderRadios(
                        [data.zyGxhxxzy && data.zyGxhxxzy.columnValue || {}],
                        data.zyGxhxxzy.dmList
                    )}

                </td>
            </tr>
            </tbody>
        </table>
    )
};

export default JCSSTable