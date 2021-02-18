/*
 * @(#) InfoList.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author wuxw
 * <br> 2020-03-19 15:50:19
 * ————————————————————————————————
 *    修改记录
 *    修改者：
 *    修改时间：
 *    修改原因：
 * ————————————————————————————————
 */
import React from 'react';
import FieldInfoItem from '../FieldInfoItem';

const FiledInfoList = props => {

    const {data = [], translate = {}, onExecute = {}, column = 3, labelOption = [], commonOption = []} = props;

    //commonOption 为权限控制按钮
    return (
        <div>
            {
                data && data.map(item => {
                    // if (item.QX_FLAG && item.QX_FLAG === '1') {
                    //     return (
                    //         <FieldInfoItem
                    //             data={item}
                    //             translate={translate}
                    //             labelOption={labelOption}
                    //             onExecute={onExecute}
                    //             column={column}
                    //         />
                    //     );
                    // }
                    return (
                        <FieldInfoItem
                            data={item}
                            translate={translate}
                            labelOption={labelOption}
                            onExecute={onExecute}
                            column={column}
                        />
                    );
                })
            }
        </div>
    );
};

export default FiledInfoList;



