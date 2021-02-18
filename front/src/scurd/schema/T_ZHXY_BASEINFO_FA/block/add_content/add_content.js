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
import {ObjectUtils, ParamsUtils} from '@share/scurd/block/Utils';
import {ApiCall, isAbort} from '@share/scurd/block/ApiCall';
import {getDataFromStore, removeByName, setData} from "../../../../../utils/StoreUtil";

class AddContentWrap extends AddContent {

    constructor(props) {
        super(props);
        this.state = {
            ...this.state,
            _g_id: ''
        };

    }

    //上一步
    toPrev = async () => {
        let flag = await window.windowConfirm('返回上一步，信息不会被保存，确定返回？');
        if(!flag)return;
        window.location.href = `/smart-campus/apply.html#/apply/0`
    }
    //下一步
    toNext = () => {
        this.toAdd('save')
    }
    //返回列表
    toIndex = () => {
        window.location.href = `/smart-campus/apply.html#/`
    }
    //暂存草稿
    toSaveDraft = () => {
        this.toAdd('draft');
    }

    toAdd = (type) => {
        let self = this;
        this.firstError = true;
        let {_g_id} = this.state;
        const {schema, stateChange, state, g_id, pcState} = this.props;
        let new_id = _g_id ? _g_id : g_id; //保存草稿之後的继续操作
        let htStateArr = ['8', '13', '18','25','26','27'];
        if ((!state  || htStateArr.includes(state)) && pcState != '2') { // 不存在归档数据的时候
            stateChange('g_id', new_id); //如果有值，就是编辑状态，为草稿或者驳回状态为修改同一条数据
        }else if(_g_id != '' && _g_id != g_id){ //存在归档数据的时候
            stateChange('g_id', _g_id)
        } else {
            stateChange('g_id', '');
        }

        if (!super.validateForm() && type == 'save') { //为下一步的状态时才校验必填字段
            return;
        }

        // 保存一对多的添加样式
        this.multiSchema.one2moreAddViewSave(this);
        let stateArr = ['8', '12', '21', '24', '23'];
        if (!state || stateArr.includes(state) || pcState === '2') {
            stateChange('STATE', '8');
        } else {
            stateChange('STATE', state);
        }
        /*        // removeColumns后的需要过滤掉，不能再提交上去
                data = _.pickBy(data, (value, key) => {
                    return !stateChange().ctrlColumns.includes(key);
                });*/

        let data = stateChange('_form_');
        const ctrlColumns = stateChange().ctrlColumns;
        if (data && Array.isArray(ctrlColumns) && ctrlColumns.length > 0) {
            ctrlColumns.forEach(key => data[key] = '')
        }


        // if(!this.multiSchema.one2oneValidate(this)){
        //     return;
        // }
        data[ParamsUtils._childSchemas] = this.getOne2OneMoreForm()[ParamsUtils._childSchemas];
        const deleteSchema = this.getOne2OneMoreForm()[ParamsUtils._childDeleteSchemas];
        data[ParamsUtils._childDeleteSchemas] = deleteSchema;

        //创建申报的时候从表需重新赋值，第一次g_id赋值为空，再次提及赋值最新的g_id
        let datum = data['g_child_schemas'];
        datum[0].g_one2one_for_id = _g_id;
        datum[0].g_for_id = _g_id;
        datum[0].DECLARE_ID = _g_id;
        stateChange(data['g_child_schemas'], datum);

        let savePromise;

        if (ObjectUtils.toCompatBoolean(this.props[ParamsUtils.inline_one2more])) {
            this.multiSchema.one2moreSave(this, data, this.props);
            savePromise = Promise.resolve("");
        } else {
            const saveData = {
                ...ParamsUtils.getAllQueryParams(this.props),
                ...data
            };
            savePromise = ApiCall.save(saveData);
        }

        this.setState({addDisable: true}, () => {

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
                this.setState({addDisable: false, _g_id: data});
                stateChange('g_id', data)
                removeByName('_g_id');
                removeByName('_basicIds');
                //存缓存(g_id,state,pc_state)，方便下一个页面返回查看基本信息
                let params = {_g_id:data,_state:stateChange('STATE'),_pc_state:stateChange('PC_STATE')};
                setData('_g_id', data);
                setData('_basicIds', params); //存缓存，方便下一个页面返回查看基本信息
                if (self.getCallBack('add')) {
                    const callBack = self.getCallBack('add');

                    this[callBack](data);
                    return;
                }
                if (ObjectUtils.toCompatBoolean(this.props[ParamsUtils.inline_one2more]) != true) {

                    let stepSelected = getDataFromStore('stepSelected');
                    stepSelected = stepSelected ? stepSelected.concat(['2']) : ['2'];
                    setData('stepSelected', stepSelected);
                    if (type && type == 'save') {
                        stateChange('_form_', {});
                        window.location.href = `/smart-campus/apply.html#/apply/2`; //保存状态
                    } else {
                        window.success('保存草稿成功');
                        return;
                    }

                }
            }).catch(e => {
                this.setState({addDisable: false});
                if (!isAbort(e)) {
                    alert(`保存失败，错误信息为:${e.message}`);
                }
            });
        });
    }

}

export default AddContentWrap;
