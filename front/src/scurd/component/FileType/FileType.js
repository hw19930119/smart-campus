/*
 * @(#) file_type.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author cxy
 * <br> 2020-03-11 16:28:13
 */
import React, { Fragment } from 'react';
import { CheckboxGroup, FormControl } from '@share/shareui';
import BaseComponent from '@share/scurd/component/BaseComponent.js';
import styles from './fileType.css';
import TextTip from '@share/shareui/es/components/TextTip';

const fileTypeOptions = [
    {
        label: 'word',
        value: '.doc,.docx,.DOC,.DOCX'
    },
    {
        label: 'excel',
        value: '.xls,.xlsx,.XLS,.XLSX'
    },
    {
        label: 'PDF',
        value: '.pdf,.PDF'
    },
    {
        label: 'PPT',
        value: '.ppt,.pptx,.PPT,.PPTX'
    },
    {
        label: '图片',
        value: '.jpg,.png,.jpeg,.JPG,.PNG,.JPEG'
    },
    {
        label: '压缩包',
        value: '.zip,.rar,.ZIP,.RAR'
    }
];

class FileType extends BaseComponent {

    constructor(props, context) {
        super(props, context);

        this.state = {
            checkboxGroup: [],
            otherCheck: '',
        };
    }

    componentDidMount = () => {
        const qtsclx = this.props.stateChange('qtsclx');
        const fileTypeStr = this.props.stateChange('fileTypeOption');
        let checkboxGroup = [];

        if (fileTypeStr) {
            checkboxGroup = JSON.parse(fileTypeStr)
                .filter(item => fileTypeOptions.some(val => val.value === item.value));
        }
        this.setState({
            checkboxGroup,
            otherCheck: qtsclx
        });
    }


    isValidate(val) {
        const { otherCheck } = this.state;

        if (otherCheck) {
            const pattern = /^\.[^\\/:*?"<>\.|,\s]+(\s*,\s*\.[^\\/:*?"<>\.|,]+)*$/;

            if (!pattern.test(otherCheck)) {
                return {
                    status: false,
                    error: ('格式不正确，请重新填写"."开头，多个以","隔开的附件类型')
                };
            }
        }
        const { itemData, stateChange } = this.props;
        const sourceVal = stateChange(itemData.column);

        if (sourceVal === '') {
            return {
                status: false,
                error: ('请选择或填写附件类型')
            };
        }


        return BaseComponent.validate(itemData, super.val(), super.isBlank(), this);
    }

    componentDidUpdate(prevProps, prevState) {
        const { checkboxGroup, otherCheck } = this.state;
        const { stateChange } = this.props;

        console.info('componentDidUpdate-checkboxGroup>>>,', checkboxGroup);
        let fileType = '';

        checkboxGroup.map(function (val) {
            fileType += `${val.value},`;
        });
        fileType += otherCheck;
        if ((this.state.checkboxGroup !== prevState.checkboxGroup)
            || this.state.otherCheck !== prevState.otherCheck) {

            stateChange('fileTypeOption', JSON.stringify(checkboxGroup));
            stateChange('fileType', fileType);
            stateChange('qtsclx', otherCheck);
            this.isValidate();
        }
    }

    render() {
        const { checkboxGroup, otherCheck } = this.state;

        console.info('render-checkboxGroup>>', checkboxGroup);
        console.info('render-otherCheck>>', otherCheck);
        return (

            <Fragment>
                <CheckboxGroup
                    {...super.defaultScurdProps()}
                    value={checkboxGroup}
                    options={fileTypeOptions}
                    onChange={(value, option) => {
                        this.setState({
                            checkboxGroup: value
                        });
                    }}
                />
                <div className={styles.form_group}>
                    其他：
                    <FormControl
                        {...super.defaultScurdProps()}
                        maxLength={100}
                        className={styles.other_class}
                        value={otherCheck}
                        onChange={value => {
                            this.setState({
                                otherCheck: value.target.value
                            });
                        }}
                    />
                    <TextTip bsStyle="info" className={styles.tip_class}>请输入文件的后缀，例如：.doc,.jpg</TextTip>
                    {/* <p className="text text-tip pull-left">请输入文件的后缀，例如：.doc,.jpg</p>*/}
                </div>


            </Fragment>
        );

    }
}

export default FileType;
