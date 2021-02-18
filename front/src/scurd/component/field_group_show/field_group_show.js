import React, { Fragment } from 'react';
import EditorComponent from '@share/scurd/component/ueditor/ueditor';
import { Row } from '@share/shareui';
import styles from './field_group_show.scss';
import { SchemaService } from '@share/scurd/block/SchemaService';

/**
 * 选择字段控件--关联控件：file_group
 * 该控件所有属性控制语句不可删除
 * window.eventBus.isFieldGroupShow ：控制编辑页面第一次元素渲染
 * window.eventBus.isFieldGroup ：控制编辑页面未点击下层部分元素时渲染
 */

export default class FieldGroup extends EditorComponent{

    constructor(props){
        super(props);
        this.state = {
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
            .then(function(data){

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

        if (this.props.g_id){
            const fieldGroupId = this.props.g_id;

            const { recoverData } = this.state;

            await SchemaService.callService('dateDeclareService',
                'getAllFieldOfFieldGroup',
                [{ name: 'fieldGroupId', data: fieldGroupId }]).then(function(data){

                const dataCopy = JSON.parse(data);

                dataCopy.data.forEach(item => {
                    recoverData.forEach(itemSub => {
                        if (item.DICT_NAME === itemSub.titel){
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


    render(){
        let { recoverData } = this.state;
        const { stateChange } = this.props;
        const copyDataArray = this.props.parent.props.parent.findComponent('YXZD').state.recoverData;

        if (window.eventBus.isFieldGroupShow && window.eventBus.isFieldGroup){
            if (copyDataArray && copyDataArray.__proto__ === Array.prototype && copyDataArray.length > 0){
                recoverData = copyDataArray;
            }
        }

        return (
            <div>
                <div>
                    <Row className={styles.selectField}>
                        <Fragment>
                            <Fragment>
                                {recoverData && recoverData.map(item => {
                                    return item.options.map(itemSub => {
                                        return (
                                            <div key={`button${itemSub.code}`} className={styles.selectData}>
                                                <span className={styles.lable}>{itemSub.label}</span>
                                                <span
                                                    className="si si-com_closethin"
                                                    onClick={() => {
                                                        window.eventBus.isFieldGroupShow = true;

                                                        const recoverDataCopy = [...recoverData];
                                                        const itemArray = [];

                                                        recoverDataCopy.forEach(itemRecIt => {
                                                            if (itemRecIt.code === item.code) {

                                                                itemRecIt.options.forEach(itemRecItSub => {
                                                                    if (itemRecItSub.code !== itemSub.code){
                                                                        itemArray.push(itemRecItSub);
                                                                    }
                                                                });
                                                                itemRecIt.options = itemArray;

                                                            }
                                                        });

                                                        stateChange('YXZD', recoverDataCopy);

                                                        this.props.parent.props.parent.findComponent('YXZD').setState({
                                                            recoverData: recoverDataCopy
                                                        });

                                                        this.setState({
                                                            recoverDataCopy
                                                        });

                                                    }}
                                                />
                                            </div>
                                        );
                                    });
                                })}
                            </Fragment>
                        </Fragment>
                    </Row>
                </div>
            </div>
        );
    }
}

