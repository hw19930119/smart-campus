
import React from 'react';
import './ZPScore.scss';
import {formaterZpScore,dealNumber,sectionToChinese} from '../../../utils/util'
const ZPScore = (props) => {
    let {data} = props;
    let result = formaterZpScore(data);
    return (
        <table className="progress-table score-table">
            <colgroup>
                <col width={'40%'}/>
                <col width={'30%'}/>
                <col width={'30%'}/>
            </colgroup>
            <thead>
            <tr className="progress-table-title">
                <th>评价项目</th>
                <th>分值</th>
                <th>自评分</th>
            </tr>
            </thead>
            <tbody>
            {
                result && result.map((item,index)=>{
                    return (
                        <tr key={index}>
                            <td>{`${index+1<result.length?sectionToChinese(index+1)+'、':''}${item.title}`}</td>
                            <td>{dealNumber(item.score) || ''}</td>
                            <td>{dealNumber(item.zpScore) || ''}</td>
                        </tr>
                    )
                })
            }

            </tbody>
        </table>
    );
}
export default ZPScore;
