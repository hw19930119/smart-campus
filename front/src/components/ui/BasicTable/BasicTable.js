/*
 * @(#) JCSSTable.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright：  Copyright (c) 2019
 * <br> Company：厦门畅享信息技术有限公司
 * <br> @author liuxia
 * <br> 2020/8/6 19：03
 */
import {renderRadios,renderCheckbox} from '../../../utils/util';

const BasicTable = (props)=>{
    let {data} = props || {};
    return (
        <table className="commit-table basic-table">
            <thead></thead>
            <tbody>
            <tr>
                <td width='15%' className="td-bg">学校名称（盖章）：</td>
                <td colSpan={3} width='85%' className="textLeft-td">{data.schoolName.columnValue || ''}</td>
            </tr>
            <tr>
                <td width='15%' className="td-bg">通讯地址：</td>
                <td width='35%' className="textLeft-td">{data.contactAddress.columnValue || ''}</td>
                <td width='15%' className="td-bg">邮编：</td>
                <td width='35%' className="textLeft-td">{data.postCode.columnValue || ''}</td>
            </tr>
            <tr>
                <td width='15%' className="td-bg">学校网址：</td>
                <td colSpan={3} width='85%' className="textLeft-td">
                    {data.schoolWebsite.columnValue || ''}
                    {/*<a href={data.schoolWebsite.columnValue || ''} target="_blank">{data.schoolWebsite.columnValue || ''}</a>*/}
                </td>
            </tr>
            <tr>
                <td width='15%' className="td-bg">校长：</td>
                <td width='35%' className="textLeft-td">{data.shcoolXz.columnValue || ''}</td>
                <td width='15%' className="td-bg">联系电话：</td>
                <td width='35%' className="textLeft-td">{data.shcoolPhone.columnValue || ''}</td>
            </tr>
            <tr className="other-tr">
                <td colSpan={4}>
                    <table className="sub-table">
                        <tr>
                            <th width='20%'>创建工作负责人</th>
                            <th width='20%'>姓名</th>
                            <th width='20%'>职务</th>
                            <th width='20%'>联系电话</th>
                            <th width='20%'>电子邮箱</th>
                        </tr>
                        <tr>
                            <td width='20%'>分管领导</td>
                            <td width='20%'>{data.ldName.columnValue || ''}</td>
                            <td width='20%'>{data.ldZw.columnValue || ''}</td>
                            <td width='20%'>{data.ldPhone.columnValue || ''}</td>
                            <td width='20%'>{data.ldDzyx.columnValue || ''}</td>
                        </tr>
                        <tr>
                            <td width='20%'>联系人</td>
                            <td width='20%'>{data.lxrName.columnValue || ''}</td>
                            <td width='20%'>{data.lxrZw.columnValue || ''}</td>
                            <td width='20%'>{data.lxrPhone.columnValue || ''}</td>
                            <td width='20%'>{data.lxrDzyx.columnValue || ''}</td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td width='15%' className="td-bg">学校类别：</td>
                <td colSpan={3} width='85%' className="textLeft-td">
                    {renderRadios(
                        [data.schoolType.columnValue || {}],
                        data.schoolType.dmList
                    )}
                    <span className="other-info"><b>{data.schoolType.columnValue[0].id == 'S06' ? data.schoolTypeName.columnValue :''}</b></span>
                </td>
            </tr>
            <tr>
                <td width='15%' className="td-bg">申报重点：</td>
                <td colSpan={3} width='85%' className="textLeft-td">
                    {renderCheckbox(
                        [data.sbzd.columnValue || {}],
                        data.sbzd.dmList
                    )}
                </td>
            </tr>
            <tr>
                <td width='15%' className="td-bg">学生总数：</td>
                <td width='35%' className="textLeft-td">{data.studentCount.columnValue || ''}</td>
                <td width='15%' className="td-bg">教学班数：</td>
                <td width='35%' className="textLeft-td">{data.classCount.columnValue || ''}</td>
            </tr>
            <tr>
                <td width='15%' className="td-bg">教职工数：</td>
                <td width='35%' className="textLeft-td">{data.facultyCount.columnValue || ''}</td>
                <td width='15%' className="td-bg">高级教师数：</td>
                <td width='35%' className="textLeft-td">{data.gjjsCount.columnValue || ''}</td>
            </tr>
            </tbody>
        </table>
    )
};

export default BasicTable