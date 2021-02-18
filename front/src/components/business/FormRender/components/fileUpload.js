/**
 * 图片上传
 *
 * 此文件为示例文件，建议直接copy到项目内做自定义组件
 * */
import React, { useState } from 'react';
import styles from '../styles/fileUpload.scss';
import { FileUpload, Button, Spin } from '@share/shareui';
import TextTip from '@share/form-render/lib/widgets/shareui/TextTip';
import { getPath } from '@/utils';
import {getFileType,formaterFileTyle} from "./util";

export default function fileUpload(p) {
    const [loading, setLoading] = useState(false);
    let _value = p.value || [];
    const { componentProps = {}, url, uploadProps = {}, textTip } = p.schema || {};
    const { fileSum, fileName = 'original', src = 'url', fileTemplate = '', explain = '' } = componentProps;
    const disabled = !(p.disabled || p.readonly);
    const fileType = getFileType(uploadProps.accept);
    return (
        <div className={styles.fileUpload}>
            {
                _value.length > 0 &&
                <div className={styles.fileNames}>
                    {
                        _value.map((item, index) => (
                            <div key={index} className={styles.fileItem}>
                                <a
                                    className={styles.name} title="下载" download
                                    href={getPath(item[src], item[fileName])}
                                >{item[fileName]}</a>
                                {
                                    disabled &&
                                    <i
                                        className={styles.deleteBtn} title="删除"
                                        onClick={() => {
                                            _value.splice(index, 1);
                                            p.onChange(p.name, _value.length === 0 ? '' : _value);
                                        }}
                                    >
                                        <svg t="1597113398549" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="9486" width="16" height="16"><path d="M512.1 127.8c49.9 0 98.5 9.5 144.5 28.1 47.6 19.3 90.3 47.7 127 84.4s65.1 79.4 84.4 127c18.7 46 28.1 94.6 28.1 144.5s-9.5 98.5-28.1 144.5c-19.3 47.6-47.7 90.3-84.4 127s-79.4 65.1-127 84.4c-46 18.7-94.6 28.1-144.5 28.1-49.9 0-98.5-9.5-144.5-28.1-47.6-19.3-90.3-47.7-127-84.4s-65.1-79.4-84.4-127c-18.7-46-28.1-94.6-28.1-144.5s9.5-98.5 28.1-144.5c19.3-47.6 47.7-90.3 84.4-127s79.4-65.1 127-84.4c46-18.6 94.6-28.1 144.5-28.1m0-64c-114.7 0-229.3 43.7-316.8 131.2-175 175-175 458.6 0 633.6 87.5 87.5 202.1 131.2 316.8 131.2s229.3-43.7 316.8-131.2c175-175 175-458.6 0-633.6-87.5-87.4-202.1-131.2-316.8-131.2z" p-id="9487" fill="#d81e06"></path><path d="M715.8 670.3L557.4 511.8l158.3-158.3-45.3-45.2-158.3 158.3-158.4-158.4-45.3 45.2 158.4 158.4-158.3 158.4 45.2 45.3 158.4-158.4 158.4 158.4z" p-id="9488" fill="#d81e06"></path></svg>
                                    </i>
                                }
                            </div>
                        ))
                    }
                </div>
            }

            {
                (!(_value.length === Number(fileSum)) && disabled) &&
                <React.Fragment>
                    <FileUpload
                        request={{ url }}
                        uploadProps={{ ...uploadProps }}
                        onComplete={({ response, status }) => {
                            if (status === 200) {
                                let files = response.data[0];
                                if(!formaterFileTyle(files,fileType,20)){
                                    setLoading(false);
                                    Spin.hide();
                                    return;
                                }
                                if (Array.isArray(response.data)) {
                                    _value = _value.concat(response.data);
                                } else {
                                    _value.push(response);
                                }
                                p.onChange(p.name, _value);
                            }
                            setLoading(false);
                            Spin.hide();
                        }}
                        onChange={(e) => {
                            Spin.show('上传中...');
                            setLoading(true);
                        }}
                        {...componentProps}
                    >
                        <div className={loading ? styles.fileUploadBtn : ''}>
                            <Button type="button" loading={loading} disabled={loading}>点击上传文件</Button>
                        </div>

                    </FileUpload>
                    <p className={styles.fileType}>{`（支持${fileType.join(',')}）`}</p>
                </React.Fragment>


            }
            {
                !disabled &&
                <Button type="button" disabled={true} className={styles.disabledBtn}>文件上传</Button>
            }
            {
                fileTemplate &&
                <a
                    className={`${styles.name} ${styles.fileTemplate}`} title="下载"
                    download href={getPath(`/${fileTemplate}`)}
                >点击下载模板</a>
            }

            { (explain && disabled && !textTip) && <TextTip textTip={explain} color="#ec705c" /> }
            { textTip && <TextTip textTip={textTip} color="#d43" bsStyle="warning" /> }

        </div>
    );
}
