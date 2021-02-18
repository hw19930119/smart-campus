/*
 * @(#) add_content.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author wuxw
 * <br> 2020-03-25 11:31:28
 * ————————————————————————————————
 *    修改记录
 *    修改者：
 *    修改时间：
 *    修改原因：
 * ————————————————————————————————
 */
import {AddContent} from '@share/scurd/block/add_content/add_content';
import { ObjectUtils, ParamsUtils, UrlUtils } from '@share/scurd/block/Utils';
import { SchemaService } from '@share/scurd/block/SchemaService';
import { ApiCall, isAbort } from '@share/scurd/block/ApiCall';
import store from 'store';
class AddContentWrap extends AddContent {

    constructor(props) {
        super(props);
        this.state = {
            ...this.state,

        };
        this.addCallBack = this.addCallBack.bind(this);

    }

    add() {
        const {stateChange, g_id} = this.props;
        const {choiceOption, FIELD_CONTROL} = stateChange('_form_');
        if (FIELD_CONTROL && (FIELD_CONTROL === '4' || FIELD_CONTROL === '5' || FIELD_CONTROL === '6' || FIELD_CONTROL === '7')) {
            if (!choiceOption) {
                alert('选项为空，请选择添加选项');
                return;
            } else {
                let jsonObj = choiceOption;

                if (typeof (choiceOption) === 'string') {
                    jsonObj = JSON.parse(choiceOption);
                }
                const {itemList} = jsonObj;

                console.info('add-itemList>>>>', itemList);
                if (!itemList || itemList.length <= 0) {
                    alert('选项为空，请选择添加选项');
                    return;
                }
            }
        }
        this.toAdd();
    }

    toAdd(){
        this.firstError = true;
        const { parent, schema, stateChange } = this.props;
        const self = this;

        if (!this.validateForm()){
            return;
        }

        // 保存一对多的添加样式
        this.multiSchema.one2moreAddViewSave(this);

        let data = this.props.stateChange('_form_');

        /*        // removeColumns后的需要过滤掉，不能再提交上去
                data = _.pickBy(data, (value, key) => {
                    return !stateChange().ctrlColumns.includes(key);
                });*/

        const ctrlColumns = stateChange().ctrlColumns;
        if(data && Array.isArray(ctrlColumns) && ctrlColumns.length > 0){
            ctrlColumns.forEach(key => data[key] = '')
        }

        const addGroups = schema.addGroups;

        // if(!this.multiSchema.one2oneValidate(this)){
        //     return;
        // }
        data[ParamsUtils._childSchemas] = this.getOne2OneMoreForm()[ParamsUtils._childSchemas];
        const deleteSchema = this.getOne2OneMoreForm()[ParamsUtils._childDeleteSchemas];
        data[ParamsUtils._childDeleteSchemas] = deleteSchema;


        let savePromise;

        if (ObjectUtils.toCompatBoolean(this.props[ParamsUtils.inline_one2more])){
            this.multiSchema.one2moreSave(this, data, this.props);
            savePromise = Promise.resolve("");
        } else {
            const saveData = {
                ...ParamsUtils.getAllQueryParams(this.props),
                ...data
            };

            savePromise = ApiCall.save(saveData);
        }


        this.setState({ addDisable: true }, () => {

            ApiCall.tryGetScurdVersion().then(versionData => {
                //旧版本的后端没有version，因为着后端没有删除功能，前端进行循环删除
                if (!versionData) {
                    if (deleteSchema && deleteSchema.length > 0) {
                        deleteSchema.forEach(del => {
                            ApiCall.delete(del['id'], del['schemaKey'])
                        });
                    }
                }
            });

            savePromise.then(data => {
                this.setState({ addDisable: false });
                if (self.getCallBack('add')) {
                    const callBack = self.getCallBack('add');

                    this[callBack](data);
                    return;
                }
                stateChange('_form_', {});
                if (ObjectUtils.toCompatBoolean(this.props[ParamsUtils.inline_one2more]) != true){
                    window.success('保存成功');
                }

                if (self.ifModuleHide()) {
                    return;
                }

                if (addGroups[0].isTab === '1') {
                    const gId = data;
                    const url = ParamsUtils.getUrl('#/add', { g_id: gId }, null, true, self.props);

                    // location.href = url;
                    // location.reload();
                    this.props.toPage('add', UrlUtils.getUrlParamComponentPass(url));
                } else {
                    if (!ParamsUtils.getQueryString('g_id', self.props)) {
                        store.remove(ParamsUtils.getQueryString('g_schema_key', self.props));
                    }
                    self.cancel();
                }
            }).catch(e => {
                this.setState({ addDisable: false });
                if (!isAbort(e)) {
                    alert(`保存失败，错误信息为:${e.message}`);
                }
            });
        });
    }

    validateForm(skipValidate){
        const self = this;
        const { parent, schema, stateChange } = this.props;
        const inline_one2more = this.props[ParamsUtils.inline_one2more];

        if (!skipValidate && inline_one2more != 'true' && !SchemaService.validateForTable(schema, this.props)) {
            return;
        }
        const allColumns = [];

        schema.columns.map(item => {
            allColumns.push(item.column);
        });
        const conmponents = this.findComponents(allColumns);
        let validate = true;

        const firstError = true;

        conmponents.forEach(function(com){
            const rst = com.isValidate();

            com.parent().validateInfo(rst.status, rst.error);
            if (!rst.status) {
                validate = false;
                if (self.firstError){
                    // 滚动到该错误
                    // ReactDOM.findDOMNode(com).scrollIntoView();
                }
                self.firstError = false;
            }
        });

        const childValidate = this.multiSchema.one2oneValidate(this);
        const one2moreAddViewValidate = this.multiSchema.one2moreAddViewValidate(this);

        if (!validate || !childValidate || !one2moreAddViewValidate) {
            // 校验不通过
            return false;
        }
        return true;
    }

    addCallBack() {
        window.success('保存成功');
        setTimeout(()=>{
            window.location.reload();
        },1000)
    }
}

export default AddContentWrap;
