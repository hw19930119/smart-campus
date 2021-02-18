/**
 * 认证中心配置的路径名称(key)，状态对应(value)
 * 0 待审核，1 审核通过，2审核不通过，3 补交材料
 *
 * */
import {dealNumber} from "./util";

//星级展示
export const desc = ['一星', '二星', '三星', '四星', '五星'];

export const columnsScore = [
    {
        title: '评分项目',
        dataIndex: 'title',
        key: 'title',
        width: '30%',
    },
    {
        title: '分值',
        dataIndex: 'score',
        key: 'score',
        render: (data) => {
            return dealNumber(data)
        }
    },
    {
        title: '自评分',
        dataIndex: 'zpScore',
        key: 'zpScore',
        render: (data) => {
            return dealNumber(data)
        }
    },
];
export const columnsExpert = [
    {
        title: '评价项目',
        dataIndex: 'title',
        key: 'title',
        width: '30%',
    },
    {
        title: '评价指标',
        dataIndex: 'score',
        key: 'score',
    },
    {
        title: '点评专家',
        dataIndex: 'zpScore',
        key: 'zpScore',
    },
]
export const columnsEvalution = [
    {
        title: '评价项目',
        dataIndex: 'title',
        key: 'title',
        width: '30%',
    },
    {
        title: '评价指标',
        dataIndex: 'score',
        key: 'score',
    },
    {
        title: '评价标准',
        dataIndex: 'zpScore',
        key: 'zpScore',
    },
    {
        title: '分值',
        dataIndex: 'zpScore',
        key: 'zpScore',
    },
    {
        title: '评分细则',
        dataIndex: 'zpScore',
        key: 'zpScore',
    },
    {
        title: '自评分',
        dataIndex: 'zpScore',
        key: 'zpScore',
    },
    {
        title: '专家评分',
        dataIndex: 'zjScore',
        key: 'zjScore',
    }

]
export const lableStyle = ['primary','success','warning','info'];