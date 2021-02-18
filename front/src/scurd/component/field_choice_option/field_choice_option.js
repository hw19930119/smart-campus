/*
 * @(#) field_choice_option.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author cxy
 * <br> 2020-01-15 16:52:06
 */
import React, { Component, Fragment } from 'react';
import { Button, Icon, Modal, FileUpload, ModalTool } from '@share/shareui';
import { createForm, getComponents } from '@share/shareui-form';
import styles from './fieldChoiceOption.css';
import { SchemaService } from '@share/scurd/block/SchemaService';
import { UrlUtils } from '@share/scurd/block/Utils';
import {loadSmartDm} from '@/services/requestFunc';

import * as utils from '../../../utils/util';

const { Form, Row, Input, Select, RadioGroup } = getComponents();

// const optionListPointer = utils.getUUID();
const contextPath = UrlUtils.getContextPath();
const warnModal = message => {
    // eslint-disable-next-line no-new
    new ModalTool({
        bsStyle: 'warning',
        content: message,
        cancelText: null
    });
};


const editValue = `{
    "service_name":"",
    "method_name":"",
    "params":[]
}
`;

@createForm({
    optionType: '1',
    datasourceData: '',
    tableName: '',
    keyField: '',
    valueField: '',
    itemList: [
        {
            // value: optionListPointer,
            value: '',
            label: ''
        }
    ]
})


class FieldChoiceOption extends Component {

    constructor(props, context) {
        super(props, context);

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.jumpAddDynamicData = this.jumpAddDynamicData.bind(this);
        this.findTableList = this.findTableList.bind(this);
        this.findFieldList = this.findFieldList.bind(this);

        this.state = {
            show: false,
            dictItemData: [],
            fileData: '',
            uploading: true,
            datasourceDataList: [],
            tableDataList: [],
            fieldDataList: [],
            component:null,
            editValue
        };

        // this.optionListPointer = optionListPointer;

    }
    componentWillMount() {
        let self = this;

        import("react-ace").then((AceEditor)=>{
            import("brace/mode/json").then(()=>{
                import("brace/theme/github").then(()=>{
                    self.setState({component:AceEditor.default})
                })
            })
        });
    }

    componentDidMount = () => {
        const { form } = this.props;
        const choiceOption = this.props.stateChange('choiceOption');

        if (choiceOption) {

            const jsonObj  = JSON.parse(choiceOption);

            console.info('choiceOption>>>>>>>>>>>>>>>>>', jsonObj);
            const { itemList, optionType } = jsonObj;
            const dictItemTempData = [];

            $.extend(true, dictItemTempData, itemList);

            if (optionType && optionType !== '3') {
                form.setFormData(jsonObj);
                this.setState({
                    dictItemData: dictItemTempData
                });
            } else {
                this.setState({
                    fileData: dictItemTempData,
                    uploading: false,
                    dictItemData: dictItemTempData
                });
                form.setFieldValue('optionType', optionType);
            }


        }

        this.initDs();
    }

    // ========================= 操作添加选项窗体方法start =========================

    /**
     * 打开添加选项窗体
     */
    handleShow() {
        this.setState({ show: true });

        const { form } = this.props;
        const { itemList } = form.formData;

        console.info('itemList>>>>', itemList);
        if (!itemList || itemList.length <= 0) {
            form.setFieldValue('itemList', [
                {
                    // value: optionListPointer,
                    value: '',
                    label: ''
                }
            ]);
        }

        this.initDs();
    }

    /**
     * 关闭添加选项窗体
     */
    handleClose = () => {
        this.setState({ show: false, uploading: true });
    }

    /**
     * 选项确定事件
     */
    handleConfirm = async () => {
        let {editValue} = this.state;
        const { form, stateChange } = this.props;
        const { optionType, itemList, datasourceData, tableName, keyField, valueField } = form.formData;

        console.info('formData-form>>', form.formData);

        const saveData = {};

        saveData.optionType = optionType;
        saveData.datasourceData = datasourceData;
        saveData.tableName = tableName;
        saveData.keyField = keyField;
        saveData.valueField = valueField;
        if (optionType === '1') {
            const validResultArray = await form.valid();
            const result = validResultArray.every(itemRs => itemRs === true);

            if (!result) {
                // 获取第一个错误并定位;
                const firstError = validResultArray.find(item => item !== true);

                firstError.element.scrollIntoView();
                console.info('未通过');
                return;
            }

            const tempData = [];

            itemList && itemList.map(item => {
                tempData.push(item.label);
            });

            if (this.isRepeataa(tempData)) {
                alert('选项名称不能重复,请修改!');
                return;
            } else {
                const dictItemTempData = [];

                // eslint-disable-next-line no-undef
                $.extend(true, dictItemTempData, itemList);
                console.info('dictItemTempData>>>', dictItemTempData);

                this.setState({
                    show: false,
                    dictItemData: dictItemTempData
                });

                form.setFieldValue('dynamicData', '');

                saveData.itemList = dictItemTempData;


                stateChange('choiceOption', saveData);
            }
        } else if (optionType === '2') {

            let params = editValue && JSON.parse(editValue);

            loadSmartDm(params).then(res=>{
                if(res && res.length > 0){
                    let saveData = [];
                    res.forEach(item => saveData.push({value:item.id,label:item.label}));

                    this.setState({
                        show: false,
                        dictItemData: saveData
                    });
                    form.setFieldValue('itemList', saveData);

                    stateChange('choiceOption', saveData);
                }
            })
            // eslint-disable-next-line no-undef
            // $.ajax({
            //     url: `${window.SHARE.CONTEXT_PATH}ds/dynamicData/findDynamicData/${datasourceData}/${tableName}/${keyField}/${valueField}`,
            //     type: 'get',
            //     contentType: 'application/json',
            //     success: rep => {
            //         console.info('rep>>>', rep);
            //         this.setState({
            //             show: false,
            //             dictItemData: rep
            //         });
            //         form.setFieldValue('itemList', rep);
            //
            //         saveData.itemList = rep;
            //         stateChange('choiceOption', saveData);
            //     }
            // });

        } else if (optionType === '3') {
            const { fileData } = this.state;

            if (fileData) {
                const objJson = JSON.parse(fileData);

                this.setState({
                    show: false,
                    dictItemData: objJson.itemList,
                    uploading: true
                });

                stateChange('choiceOption', objJson);
            }

        }

    }

    isRepeataa(arr){
        const hash = {};

        arr.forEach(element => {
            if (hash[element]) {
                return true;
            }
            hash[element] = true;
        });
        return false;
    }

    // =========================  操作添加选择窗体方法end =========================

    /**
     * 初始化读取数据下拉列表
     */
    initDs() {
        SchemaService.callService(
            'dataSourceCgController',
            'findList',
            [{ name: 'sourceType', data: 'DB' }]
        )
            .then(res => {
                this.setState({ datasourceDataList: JSON.parse(res).data });
            });
    }

    /**
     * 初始化读取数据下拉列表
     */
    findTableList(obj) {
        SchemaService.callService(
            'dataSourceCgController',
            'findTableList',
            [{ name: 'sourceCgId', data: obj.target.value }]
        )
            .then(res => {
                this.setState({ tableDataList: utils.transformProperty(JSON.parse(res).data, { code: 'value' }) });
            });
    }

    /**
     * 初始化读取数据下拉列表
     */
    findFieldList(obj) {
        const { form } = this.props;

        SchemaService.callService(
            'dynamicDataController',
            'findFieldList',
            [{ name: 'sourceCgId', data: form.formData.datasourceData }, { name: 'tableName', data: obj.target.value }]
        )
            .then(res => {
                this.setState({ fieldDataList: utils.transformProperty(JSON.parse(res).data, { code: 'value' }) });
            });
    }

    /**
     * 跳转到新增动态数据页面
     */
    jumpAddDynamicData() {
        console.info('jumpAddDynamicData');
    }

    // ========================= 操作添加选项方法start =========================

    /**
     * 新增选项行
     */
    handleAdd = () => {
        const { form } = this.props;
        const { itemList } = form.formData;

        // this.optionListPointer = utils.getUUID();
        // console.info('this.optionListPointer>>>', this.optionListPointer);
        form.setFieldValues({
            itemList: itemList.concat({
                // value: this.optionListPointer,
                value: '',
                label: ''
            })
        });
    };

    /**
     * 删除选项行
     * @param index
     */
    handleDelete = index => {
        const { form } = this.props;
        const { itemList } = form.formData;

        form.setFieldValues({
            itemList: itemList.filter((v, i) => i !== index)
        });
    };

    delItem = item => {
        const { form, stateChange } = this.props;
        const { itemList, optionType } = form.formData;
        const { dictItemData } = this.state;
        const index = dictItemData.indexOf(item);

        if (optionType !== '3') {
            form.setFieldValues({
                itemList: itemList.filter((v, i) => i !== index)
            });
        }


        if (index > -1) {
            dictItemData.splice(index, 1);
        }
        this.setState({ dictItemData });

        if (optionType === '3') {
            const saveData = {};

            saveData.optionType = optionType;
            saveData.dynamicData = '';
            saveData.itemList = dictItemData;
            stateChange('choiceOption', saveData);
        } else {
            stateChange('choiceOption', form.formData);
        }

    };

    // ========================= 操作添加选项方法end =========================

    render() {
        const { form } = this.props;
        const { datasourceDataList, tableDataList, fieldDataList, dictItemData, uploading,component,editValue } = this.state;
        const { itemList, optionType } = form.formData;
        let Component = component ? component:false;
        console.info('component->form>>>>>>>>>>>', form);
        return (
            <div>
                <div className={styles.span_left}>
                    {
                        dictItemData && dictItemData.map(item => (
                            <div className={styles.span_left_text}>
                                <span className="Select-value-icon " aria-hidden="true" onClick={this.delItem.bind(this, item)}>×</span> <span key={item.value}>{item.label}(key={item.value})、</span>
                            </div>
                        ))
                    }
                </div>
                <Button bsStyle="primary" bsSize="large" onClick={this.handleShow} className={styles.right_button}>
                    添加选项
                </Button>

                <Modal show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>添加选项</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form pageType="addPage" formState={form}>
                            <Row>
                                <RadioGroup
                                    field="optionType"
                                    label="选项类型"
                                    colSpan="6"
                                    options={[
                                        { value: '1', label: '手动添加' },
                                        { value: '2', label: '数据读取' },
                                        { value: '3', label: 'excel导入' }
                                    ]}
                                />
                            </Row>
                            {optionType === '1' && itemList && itemList.map((v, index, arr) => (
                                <Row key={v.key}>
                                    <Input
                                        colSpan="2"
                                        field={`itemList.${index}.label`}
                                        label="选项名称"
                                        rule="required"
                                    />

                                    <Input
                                        colSpan="2"
                                        field={`itemList.${index}.value`}
                                        label="选项值"
                                        rule="required"
                                    />
                                    <td>
                                        {arr.length > 1 && (
                                            <Button
                                                type="button" className="btn-xs btn-icon" border={false}
                                                onClick={() => this.handleDelete(index)}
                                                style={{position: 'relative',top: '13px'}}
                                            >
                                                <Icon className="si si-com_reduce" />
                                            </Button>
                                        )}
                                        {index === arr.length - 1 && (
                                            <Button type="button" className="btn-xs btn-icon" border={false}
                                                    onClick={this.handleAdd}
                                                    style={{position: 'relative',top: '13px'}}
                                            >
                                                <Icon className="si si-com_plus" />
                                            </Button>
                                        )}
                                    </td>
                                </Row>
                            ))}
                            {optionType === '2' &&
                            <Fragment>
                                <Row>
                                    <td colSpan="7">
                                        { Component ? <Component width="100%"
                                                                 height="200px"
                                                                 mode="json"
                                                                 theme="github"
                                                                 value={editValue}
                                                                 onChange={v => {this.setState({editValue:v})}}
                                                                 name={'datasourceData'}
                                                                 editorProps={{$blockScrolling: true}} /> : false}
                                    </td>

                                </Row>
                                {/*<Row>
                                    <Select
                                        field="datasourceData"
                                        label="数据源"
                                        colSpan="4"
                                        options={datasourceDataList}
                                        onChange={this.findTableList}
                                    />
                                </Row>
                                <Row>
                                    <Select
                                        field="tableName"
                                        label="表"
                                        colSpan="4"
                                        options={tableDataList}
                                        onChange={this.findFieldList}
                                    />
                                </Row>
                                <Row>
                                    <Select
                                        field="keyField"
                                        label="KEY字段"
                                        colSpan="4"
                                        options={fieldDataList}
                                    />
                                </Row>
                                <Row>
                                    <Select
                                        field="valueField"
                                        label="VALUE字段"
                                        colSpan="4"
                                        options={fieldDataList}
                                    />
                                </Row>*/}
                                {/* <Row>*/}
                                {/* <td className="noBorder"></td>*/}
                                {/* <td className="noBorder">*/}
                                {/* <Button type="button" className="btn-xs btn-icon" border={false}*/}
                                {/* onClick={() => this.jumpAddDynamicData()}>*/}
                                {/* 添加数据源*/}
                                {/* </Button>*/}
                                {/* </td>*/}
                                {/* </Row>*/}
                            </Fragment>
                            }
                        </Form>
                        {
                            optionType === '3' &&
                            <Fragment>
                                <Row>

                                    <FileUpload
                                        request={{ url: `${contextPath}/field/fieldConfig/upload` }}
                                        uploadProps={{ accept: '.xls,.xlsx,.XLS,.XLSX' }}
                                        onChange={() => this.setState({ uploading: false })}
                                        onComplete={({ response, status: code }) => {

                                            const { errorDetail, status } = response;

                                            if (status === '1200') {
                                                this.setState({ fileData: response.data, uploading: false });
                                            } else {
                                                warnModal(`上传失败，${errorDetail}，请重新上传`);
                                                this.setState({ uploading: true });
                                            }
                                        }}
                                    >
                                        <Button bsStyle="primary" className={styles.myUploadBtn} >点击上传文件</Button>
                                    </FileUpload>
                                    {
                                        !uploading &&
                                            <span className={styles.correct}>
                                                <i className="si si-com_correct-08" />
                                            </span>

                                    }
                                    <a
                                        title="下载" className={uploading === true ? styles.myTemplate : styles.myTemplate2}
                                        download href={`${contextPath}/field/fieldConfig/downLoad/template`}
                                    >下载模板</a>
                                </Row>
                            </Fragment>
                        }
                    </Modal.Body>
                    <Modal.Footer>
                        <Button bsStyle="primary" onClick={this.handleConfirm}>确定</Button>
                        <Button onClick={this.handleClose}>取消</Button>
                    </Modal.Footer>
                </Modal>

            </div>
        );

    }
}

export default FieldChoiceOption;
