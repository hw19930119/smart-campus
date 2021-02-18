import React, { Fragment } from 'react';
import { ImageUploader, Spin } from '@share/shareui';
import TextTip from '@share/form-render/lib/widgets/shareui/TextTip';
import { getPath } from '@/utils';
import styles from '../styles/imageUpload.scss';
import {getFileType,formaterFileTyle} from "./util";

export default function imageUploader(p) {
    let _value = p.value || [];
    const { componentProps = {}, url = '', textTip,uploadProps } = p.schema || {};
    const { imgSum, w = 0, h = 0, explain = '',imageSize } = componentProps;
    const result = ({ url: src, ...item }) => ({ url: src, src: getPath(src), thumbnail: getPath(src), w, h, ...item });
    const items = _value.map(item => result(item));
    const disabled = !(p.disabled || p.readonly);
    const view = disabled ? 'edit' : 'detail';
    const fileType = getFileType(uploadProps.accept);
    return (
        <Fragment>
            <ImageUploader
                view={view}
                fileUploadProps={{
                    request: { url },
                    onComplete: ({ response }) => {
                        if(response.status == "1401"){
                            error(response.message);
                            Spin.hide();
                            return;
                        }
                        let files = response.data[0];
                        if(!formaterFileTyle(files,fileType,imageSize)){
                            Spin.hide();
                            return;
                        }
                        // let files = response.data[0];
                        // let size = files.fileSize;
                        // let nameArr = files.type.split(".");
                        // let type = nameArr[nameArr.length - 1].toUpperCase();
                        // console.log("e",size,type);
                        // if(size > 10 * 1024 * 1024){
                        //     error("上传文件不能大于10M");
                        //     Spin.hide();
                        //     return;
                        // }
                        // if(!fileType.includes(type)){
                        //     error("不支持该文件类型");
                        //     Spin.hide();
                        //     return;
                        // }

                        if (Array.isArray(response.data)) {
                            const res = response.data.map(item => result(item));

                            _value = _value.concat(res);
                        } else {
                            _value.push(response);
                        }
                        p.onChange(p.name, _value);
                        Spin.hide();
                    },
                    onChange: (e) => {

                        Spin.show('上传中...');
                    }
                }}
                onRemove={(item, index) => {
                    _value.splice(index, 1);
                    p.onChange(p.name, _value.length === 0 ? '' : _value);
                }}
                items={items}
                showAddBtn={disabled && !(_value.length === Number(imgSum))}
                {...componentProps}
            />
            <p className={styles.fileType}>{`（支持${fileType}）`}</p>
            {
                !disabled &&
                    <div className={styles.disableImg}>
                        <i className="si si-com_plus"></i><span>添加照片</span>
                    </div>
            }

            { (explain && disabled && !textTip) && <TextTip textTip={explain} color="#ec705c" /> }
            { textTip && <TextTip textTip={textTip} color="#d43" bsStyle="warning" /> }

        </Fragment>
    );
}
