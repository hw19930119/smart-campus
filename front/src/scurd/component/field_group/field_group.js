import React, { Fragment } from 'react';
import EditorComponent from '@share/scurd/component/ueditor/ueditor';
import { Button, CheckboxGroup, Row } from '@share/shareui';
import { SchemaService } from '@share/scurd/block/SchemaService';
import styles from './field_group.scss';

/**
 * 选择字段控件--关联控件：file_group_show
 * 该控件所有属性控制语句不可删除
 * window.eventBus.isFieldGroupShow ：控制编辑页面第一次元素渲染
 * window.eventBus.isFieldGroup ：控制编辑页面未点击下层部分元素时渲染
 */
export default class FieldGroup extends EditorComponent {

    constructor(props) {
        super(props);
        this.state = {
            isSearch: false,
            // 用于页面显示
            returnDataArray: [],
            // 用于页面结果
            recoverData: []
        };
    }

    async componentDidMount() {
        window.eventBus.isFieldGroupShow = false;
        window.eventBus.isFieldGroup = false;
        const thisCopy = this;

        await SchemaService.callService('dateDeclareService', 'getAllManualData', [])
            .then(function (data) {

                const dataCopy = JSON.parse(data);
                const returnDataArray = [];
                const recoverData = [];

                dataCopy.data.data.forEach((item, index) => {
                    const itemOptions = [];

                    item.options.forEach(itemSub => {
                        itemOptions.push({
                            code: itemSub.value,
                            label: itemSub.label,
                            isSearch: false
                        });
                    });
                    returnDataArray.push({
                        code: index,
                        titel: item.label,
                        isPress: false,
                        isSearch: false,
                        options: itemOptions
                    });
                    recoverData.push({
                        code: index,
                        titel: item.label,
                        options: []
                    });

                });

                thisCopy.setState({
                    returnDataArray,
                    recoverData,
                });

            });

        if (this.props.g_id) {
            const fieldGroupId = this.props.g_id;

            const { recoverData } = this.state;

            await SchemaService.callService('dateDeclareService',
                'getAllFieldOfFieldGroup',
                [{ name: 'fieldGroupId', data: fieldGroupId }]).then(function (data) {

                const dataCopy = JSON.parse(data);

                dataCopy.data.forEach(item => {
                    recoverData.forEach(itemSub => {
                        if (item.DICT_NAME === itemSub.titel) {
                            item.isSearch = false;
                            itemSub.options.push(item);
                        }
                    });

                });
                thisCopy.setState({
                    recoverData,
                });
            });
        }

    }

    isValidate = val => {
        const { itemData, stateChange } = this.props;
        const { recoverData } = this.state;

        const returnData = [];

        recoverData.filter(item => {
            return item.options.length > 0;
        }).forEach(item => {
            item.options.forEach(itemSub => {
                returnData.push(itemSub.code);
            });
        });
        if (returnData.length === 0) {
            return {
                status: false,
                error: ('请选择字段！')
            };
        }
        stateChange('YXZD', returnData.join(','));
        return EditorComponent.validate(itemData, super.val(), super.isBlank(), this);

    }


    render() {
        const { returnDataArray, recoverData, isSearch } = this.state;
        const { itemData, stateChange } = this.props;

        let isAllShow = true;

        // console.info('============================recoverData', recoverData);
        // console.info('============================returnDataArray', recoverData);

        returnDataArray.forEach(item => {
            if (item.isPress) {
                isAllShow = false;
            }
        });

        return (
            <div className={styles.fieldGroup}>
                <div className={styles.buttonFiled}>
                    <div className={styles.subTitle}>所属分类</div>
                    <Row className={styles.selectField}>
                        {
                            returnDataArray && returnDataArray.map((item, index) => {

                                return (
                                    <Button
                                        key={`titel${index}`} className={styles.btn} type="button" bsStyle={item.isPress ? 'primary' : 'default'}
                                        onClick={() => {
                                            window.eventBus.isFieldGroupShow = true;
                                            window.eventBus.isFieldGroup = true;
                                            const recoverDataCopy = [...returnDataArray];

                                            recoverDataCopy.forEach(itemRecIt => {
                                                if (itemRecIt.code === item.code) {
                                                    itemRecIt.isPress = !itemRecIt.isPress;
                                                }
                                            });

                                            this.setState({
                                                isFieldGroupShow: true,
                                                returnDataArray: recoverDataCopy
                                            });

                                        }}
                                    >{item.titel}</Button>
                                );
                            })
                        }
                    </Row>
                </div>

                <div className={styles.searchArea}>
                    <div>
                        <span className={styles.searchLabel}>字段名称:</span>
                        <input id={'inputIdofSearch'} className={styles.fieldName} />
                        <Button
                            id={'asdhjaisdjasdasdas'}
                            type="button"
                            style={{ marginLeft: '20px', height: '28px', lineHeight: '22px' }}
                            bsStyle="primary"
                            onClick={() => {

                                window.eventBus.isFieldGroupShow = true;
                                window.eventBus.isFieldGroup = true;
                                const inputValue = document.getElementById('inputIdofSearch').value;
                                const returDataArrayCopy = [...returnDataArray];

                                returDataArrayCopy.forEach(item => {
                                    // 判断第一级是否显示
                                    let itemIsSearch = false;

                                    // 模糊搜索
                                    item.options.forEach(itemRecIt => {

                                        if (itemRecIt.label.indexOf(inputValue) !== -1) {
                                            itemRecIt.isSearch = true;
                                            itemIsSearch = true;
                                        } else {
                                            itemRecIt.isSearch = false;
                                        }

                                    });

                                    item.isSearch = itemIsSearch;

                                });

                                stateChange(itemData.column, recoverData);
                                this.setState({
                                    isFieldGroupShow: true,
                                    isSearch: true,
                                    returnDataArray: returDataArrayCopy
                                });
                            }}
                        >查询</Button>
                    </div>

                    <div className={styles.checkboxGroup}>
                        {
                            returnDataArray && returnDataArray
                                .filter(itemFilter => {
                                    if (isAllShow) {
                                        return true;
                                    } else {
                                        return itemFilter.isPress;
                                    }
                                })
                                .filter(itemFilter => {
                                    if (isSearch) {
                                        return itemFilter.isSearch;
                                    } else {
                                        return true;
                                    }
                                })
                                .map((item, index) => {
                                    let valueData = [];

                                    recoverData.forEach(itemRec => {
                                        if (itemRec.code === item.code) {
                                            valueData = itemRec.options;
                                        }
                                    });

                                    const searchDataOptions = [];

                                    item.options.forEach(itemSearchDataOptions => {
                                        if (itemSearchDataOptions.isSearch) {
                                            searchDataOptions.push(itemSearchDataOptions);
                                        }
                                    });

                                    return (
                                        <Row className={styles.selectField} key={`field${index}`}>
                                            {`${item.titel}：`}
                                            <CheckboxGroup
                                                value={valueData}
                                                options={isSearch ? searchDataOptions : item.options}
                                                onChange={(value, option) => {
                                                    window.eventBus.isFieldGroupShow = true;
                                                    window.eventBus.isFieldGroup = true;
                                                    const recoverItem = [...recoverData];

                                                    recoverItem.forEach(itemRecIt => {
                                                        if (itemRecIt.code === item.code) {
                                                            itemRecIt.options = value;
                                                        }
                                                    });
                                                    stateChange(itemData.column, recoverData);
                                                    this.setState({
                                                        isFieldGroupShow: true,
                                                        recoverData: recoverItem
                                                    });
                                                }}
                                                valueKey="code"
                                                className="customClass"
                                            />
                                        </Row>
                                    );
                                })
                        }
                    </div>
                </div>
            </div >
        );
    }
}

