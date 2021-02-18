import React, { Fragment, useEffect, useState } from 'react';
import { Select } from '@share/shareui';
import styles from '../styles/xzqh.scss';
import getXzqh from './requestData/getXzqh';
import TextTip from '@share/form-render/lib/widgets/shareui/TextTip';

const getIdx = value => {
    return value.findIndex(item => item === null);
};

const getLevel = value => {
    if (value.endsWith('0000000000')) return '市';
    if (value.endsWith('00000000')) return '县';
    if (value.endsWith('000000')) return '街道';
    if (value.endsWith('000')) return '社区';
    return false;
};

export default function(p) {
    const { componentProps, textTip } = p.schema;
    const { level: lev, explain } = componentProps;
    const level = typeof lev === 'number' ? lev : Number(lev) - 1;
    const [values, setValues] = useState([
        ...p.value,
        ...new Array(Array.isArray(p.value) ? p.value.length - lev : lev).fill(null)
    ]);
    const [arrLv, setArrLv] = useState(new Array(level + 1).fill(null));
    const disabled = !(p.disabled || p.readonly);

    useEffect(() => {

        (async () => {
            const xzqhData = await getXzqh({ baseXzqh: '', level: '省' });
            const _arrLv = [...arrLv];

            _arrLv.splice(0, 1, xzqhData);
            setArrLv(_arrLv);

            if (getIdx(values) < 0) {
                const _arr = [..._arrLv];

                for (let i = 1; i < values.length; i++){
                    const { value } = values[i - 1];
                    const whichLevel = getLevel(value);

                    if (whichLevel) {
                        const dataLv = await getXzqh({ baseXzqh: value, level: getLevel(value) });

                        _arr.splice(i, 1, dataLv);
                    }
                }
                setArrLv(_arr);
            }
        })();

    }, []);

    return (
        <Fragment>
            <div className={styles.xzqh} >
                {
                    arrLv.map((item, index) => (
                        <Select
                            options={item ? item : []} value={values[index]} key={index}
                            onChange={async e => {
                                const _values = [...values];
                                const _arrLv = [...arrLv];

                                _values.splice(index, 1, e);

                                for (let i = index + 1; i <= level; i++) {
                                    _values[i] = null;
                                    _arrLv[i] = null;
                                }
                                setValues(_values);
                                setArrLv(_arrLv);

                                if (index + 1 < lev) {
                                    const whichLevel = getLevel(e.value);

                                    _arrLv.splice(
                                        index + 1, 1,
                                        e && whichLevel ?
                                            await getXzqh({ baseXzqh: e.value, level: getLevel(e.value) })
                                            :
                                            []
                                    );
                                    setArrLv(_arrLv);
                                }

                                p.onChange(p.name, getIdx(_values) < 0 ? _values : '');
                            }}
                        />
                    ))
                }
            </div>

            { (explain && disabled && !textTip) && <TextTip textTip={explain} color="#ec705c" /> }
            { textTip && <TextTip textTip={textTip} color="#d43" bsStyle="warning" className={styles.xzqhTip} /> }
        </Fragment>

    );
}
