/*
 * @(#) InfoItem.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author wuxw
 * <br> 2020-03-19 15:51:04
 * ————————————————————————————————
 *    修改记录
 *    修改者：
 *    修改时间：
 *    修改原因：
 * ————————————————————————————————
 */
import styles from './FieldInfoItem.scss';
import React from 'react';

const FieldInfoItem = props => {
    const { data = {}, translate = {}, onExecute = {}, column = 3, labelOption = [] } = props;
    const { status = '', title = '' } = data;

    const itemWidth = column === 0 ? '33%' : `${Math.floor(100 / Math.abs(column))}% `;

    // 生成对象数组
    const resArr = [];
    const labelKey = Object.keys(translate);


    labelKey && labelKey.map(item => {
        resArr.push(
            { label: translate[item], data: data[item] }
        );
        return null;
    });

    // 用于输出每个字段的数据（包含中文标签名和数据）
    const Field = () => {
        return (
            resArr && resArr.map(item => (
                <div className={styles.item} title={item.data} style={{ width: itemWidth }}>
                    <span className={styles.label}>{item.label}</span>
                    <span className={styles.data}>{item.data}</span>
                </div>
            ))
        );
    };


    // 用于输出右侧的按钮
    const Labels = () => {
        // 这边需要加reuturn

        return labelOption && labelOption.map(item => {
            const { condition = [] } = item;

            // 满足此条件时，不展示
            const res = condition.map(c => {
                return data[c.field] === c.value;
            });
            const checkRender = res.every(bool => bool);

            // 不满足条件或条件为空时，展示
            if (!checkRender || condition.length < 1) {
                return (
                    <p
                        className={styles.operate}
                        onClick={() => onExecute(item.execute, data)}
                        title={item.name}
                    >
                        {item.name}
                    </p>
                );
            }
        });
    };

    return (
        <div className={styles.InfoItem}>
            <div className={styles.titleBox}>
                {
                    title && <span
                        className={styles.title}
                        title={title}
                        onClick={() => onExecute('detail', data)}
                    >
                        {title}
                    </span>
                }

            </div>
            <div className={styles.fieldBox}>
                <Field />
            </div>
            <div className={styles.operations}>
                <div className={styles.operateBox}>
                    <div className={styles.tablecell}>
                        {
                            <Labels />
                        }
                    </div>
                </div>
            </div>
        </div>
    );
};

export default FieldInfoItem;


