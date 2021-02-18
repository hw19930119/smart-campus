/**
 * 数据呈现
 * */
import React from 'react';

export default function dataDisplay(p) {
    const _value = p.value || '';
    const { componentProps = {} } = p.schema || {};
    const { isDisplay = true } = componentProps;

    return (
        <div style={{ display: isDisplay ? 'block' : 'none' }}>{_value}</div>
    );
}
