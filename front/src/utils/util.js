/*
 * @(#) util.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author yanfeng
 * <br> 2019-03-27 19:26:55
 */
import React,{Fragment} from 'react';
import { SchemaService } from '@share/scurd/block/SchemaService';
import { ModalTool,CheckboxGroup,Radio,RadioGroup } from '@share/shareui';

export const isNull = param => {
    return JSON.stringify(param) === '{}' || !param;
};

export const isEmpty = function (value) {
    return value === '' || typeof value === 'undefined' || value === null;
};

/**
 * util.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 * Copyright:  Copyright (c) 2020

 * @Company:厦门畅享信息技术有限公司
 * @author: wangzs
 * @date: 2020-01-09 16:26:56
 * 深度比较两个对象是否相等，用于componentWillReceiveProps生命周期函数中
 */

export const compare = (origin, target) => {
    if (typeof target === 'object' && (target !== null || target)) {
        if (typeof origin !== 'object') {
            return false;
        }
        for (const key of Object.keys(target)) {
            if (!compare(origin[key], target[key])) {
                return false;
            }
        }
        return true;
    } else {
        return origin === target;
    }
};

export function numFormat(num) {
    if(!num){
        return;
    }
    let c = (num.toString().indexOf ('.') !== -1) ?
            num.toLocaleString() :
            num.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
    return c;
}
/**
 * util.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 * Copyright:  Copyright (c) 2020

 * @Company:厦门畅享信息技术有限公司
 * @author: wangzs
 * @date: 2020-03-02 10:20:01
 *
 * 输出小写字符串 判断数据类型
 */

export const getType = data => {
    const { toString } = Object.prototype;
    const dataType = toString
        .call(data)
        .replace(/\[object\s(.+)\]/, '$1')
        .toLowerCase();

    return dataType;
};

export const getDmData = async codeName => {
    let dataBack = [];

    await SchemaService.callService('dictService', 'getSelectAllData',
        [{ name: 'classEnName', data: codeName }]).then(data => {
        const dataJson = JSON.parse(data);

        dataBack = dataJson.data;
    });

    return dataBack;
};

/**
 * util.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 * Copyright:  Copyright (c) 2020

 * @Company:厦门畅享信息技术有限公司
 * @author: wangzs
 * @date: 2020-04-01 15:04:07
 *
 * 格式化货币格式
 */

export const currencyFmt = value => {
    // 如果没有值，放空
    if (!value || isNaN(value)) {
        return '';
    }
    const val = (value / 1).toFixed(2).replace(',', '.');

    return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
};

export const goBackModal = history => {
    // eslint-disable-next-line no-new
    new ModalTool({
        bsStyle: 'success',
        content: '请求成功，点击确认返回',
        cancelText: null,
        backdrop: false,
        closeBtn: false,
        onOk: () => {
            history && history.goBack();
        }
    });
};

export const errorModal = e => {
    // eslint-disable-next-line no-new
    new ModalTool({
        bsStyle: 'warning',
        content: e.message || '访问异常，请联系管理员！',
        cancelText: null
    });
};

// 是否为JSON
export const isJsonString = str => {
    try {
        if (typeof JSON.parse(str) === 'object') {
            return true;
        }
    } catch (e) {
        //
    }
    return false;
};

/**
 * 右边填充指定字符
 * @param str
 * @param stuff
 */
export const rightPad = (str, stuff, length) => {
    const lackSize = length - str.length;

    if (lackSize < 0) {
        return str;
    }
    let result = str;

    for (let i = 0; i < lackSize; i++) {
        result += stuff;
    }
    return result;
};

/**
 * 转换数组中的对象属性，如：let a = [{id: 1, text: 2}]，调用trnasformPropery(a, {id: 'value', text: 'label'}) ====> //a = [{value: 1, label: 2}];
 * @param data
 * @param fieldNameMap
 * @returns {Array}
 */
export const transformProperty = (data, fieldNameMap) => {
    if (!data) {
        return [];
    }
    const tmpData = JSON.parse(JSON.stringify(data));

    for (let i = 0; i < tmpData.length; i++) {
        for (const prop in fieldNameMap) {
            if (tmpData[i][prop]) {
                tmpData[i][fieldNameMap[prop]] = tmpData[i][prop];
                delete tmpData[i][prop];
            }
        }
    }

    return tmpData;
};

export const getUUID = () => {
    return new Date().getTime();
};

/**
 * 获取url查询参数
 * @param key 查询参数
 * @returns {*}
 */
export const getQueryItem = function (props, key) {
    const queryString = props.location.search.replace(/\?/g, '')
        + window.location.search.replace(/\?/g, '&');
    // queryString = queryString.substring(1, queryString.length);
    const queryItems = queryString.split('&').filter(e => {
        return e != '';
    });
    const queryMap = {};

    queryItems && queryItems.forEach(item => {
        const kv = item.split('=');

        queryMap[kv[0]] = kv[1];
    });

    if (key){
        return queryMap[key];
    }
    return queryMap;
};


// 获取properties层
const getPropertiesJSON = (list, properties) => {
    list.forEach(v => {
        const { key, title, type = 'object', maxItems, targetField } = v;
        const isObjectType = type === 'object';
        const isArrayType = type === 'array';
        properties[key] = { type, title, properties: {}, required: [] };
        if (isArrayType) {
            properties[key].maxItems = typeof maxItems === 'number' ? maxItems : Number(maxItems);
            properties[key].minItems = 1;
            properties[key].message = {
                maxItems: `当前个数不能大于${maxItems}`,
                minItems: `当前个数不能小于${properties[key].minItems}`,
            };
            properties[key].targetField = targetField;
            properties[key]['ui:options'] = {
                foldable: true,
                hideIndex: true
            };
            properties[key].items = {
                type: 'object',
                required: [],
                properties: properties[key].properties
            };
            delete properties[key].properties;
            delete properties[key].required;

        } else if (!isArrayType && !isObjectType) {
            properties[key] = {
                ...v,
            };
        }
        // 必填项放入required字段中
        if (v.child && Array.isArray(v.child)) {
            v.child.forEach(val => {
                let  { componentProps = {} } = val;
                componentProps = componentProps||{};
                const { required = false } = componentProps;

                if (isArrayType && required) {
                    properties[key].items.required.push(val.key);
                } else if (properties[key].required && required) {
                    properties[key].required.push(val.key);
                }
            });
        }
        if (v.child && Array.isArray(v.child) && (isArrayType || isObjectType)) {
            getPropertiesJSON(v.child, isArrayType ? properties[key].items.properties : properties[key].properties,list);
        }
    });
};

// 获取formData层
const getFormDataJson = (list, formData, oldKey,oldData) => {
    list.forEach(v => {
        const { key, type = 'object' } = v;
        const isObjectType = type === 'object';
        const isArrayType = type === 'array';
        const obj = {}; // formData的对应数据
        if (isObjectType) formData[key] = obj;
        if (!isObjectType && !isObjectType) {   // 正常组件直接赋值
            let value = ''
            if(oldData && oldData.length>0){
                let {defaultValue} = oldData[0];
                if(defaultValue && defaultValue.length>0){
                    let {fieldContent} = defaultValue[0];
                    fieldContent = JSON.parse(fieldContent);
                    if(fieldContent){
                        value = fieldContent[v.key]
                    }
                }
            }
            formData[key] = value;
        };
        if (isArrayType && Array.isArray(v.child)) {
            // 非array/object直接置空，array类型需要使用数组，为递归做准备
            v.child.forEach(item => {
                obj[item.key] = item.type !== 'array' && item.type !== 'object' ? '' : [];

            });

            if (Object.prototype.toString.call(formData) === '[object Object]') formData[key] = [obj];
        }

        if (Array.isArray(v.child) && (isArrayType || isObjectType)) {
            if (Array.isArray(formData[key])) {
                formData[key].forEach(item => {
                    if (Array.isArray(item)) item.push(obj); // 在当前key所对应的值为数组时，将后续属性值放入

                    getFormDataJson(v.child, item, key,list);
                });
            }

            if (typeof oldKey === 'undefined') getFormDataJson(v.child, formData[key], key,list);
        }
    });
};

// 获取JSONSchema
export function getJSONSchema(data) {
    const properties = {};
    const formData = {};

    // 拆成两个较为好调试
    getPropertiesJSON(data, properties);
    getFormDataJson(data, formData);
    return { properties, formData };
}
export function validateZbData(data) {
    if(getDmData(data,'object')){
        let value = Object.values(data)[0];
        for(let i in value){

        }
    }
    return false
}
export function getDataType(data,type){
    return Object.prototype.toString.call(data) ===  `[object ${type}]`;
}

// 判断是不是树的根节点
const isRoot = (key, tree) => {
    const res = [];
    if (Array.isArray(tree)) {
        tree
            .map(item => {
                item.key === key && res.push(item);
            });
    }
    if (res.length > 0) return res;
    return false;
}
// 获取一颗树的父节点
export const getParentCode = (key, tree) => {
    // 根节点没有父节点，需要排除
    let res = [];
    let rootData = isRoot(key, tree);
    if (rootData) {
        let [data] = rootData
        return data;
    }
    const findParent = (c, t) => {
        Array.isArray(t) && t
            .map(item => {
                if(item.key === c){
                    res.push(item);
                    return;
                }
                if(item.children &&  Array.isArray(item.children)){
                    findParent(c,item.children)
                }
            });
    };

    findParent(key, tree);

    const [r] = res;

    return r || {};
}
export const validetaSuperScore = function (key,thisKey, tree,itemScore){
    let parentData = getParentCode(key, tree);
    if(parentData){
        let scores = 0;
        let {children,score} = parentData;
        score = parseFloat(score);
        Array.isArray(children) && children.filter(item=>{
            return item.key!=thisKey
        }).map(item=>{
            scores += parseFloat(item.score);
        })
        let distance = parseFloat(score-scores).toFixed(2);
        if(parseFloat(itemScore)- distance>0){
            return false
        }
        return true;

    }
    return true;
}

export const validetaSuperScore2 = function (key,thisKey, tree,itemScore){
    let parentData = getParentCode(key, tree);
    if(parentData){
        let scores = 0;
        let {children,score} = parentData;
        score = parseFloat(score);
        Array.isArray(children) && children.filter(item=>{
            return item.key!=thisKey
        }).map(item=>{
            scores += parseFloat(item.score);
        })
        let distance = parseFloat(score-scores).toFixed(2);
        if(parseFloat(itemScore)- distance>0){
            return false;
        }
        return true;

    }
    return true;
}

export function getObjectKey(data){
    let key = Object.keys(data)[0];
    return key;
}

export function dealNumber(x) {
    if(x===''){
       return ''
    }else{
        let f = Math.round(x * 100) / 100;
        let s = f.toString();
        return s;
    }
}
export const generateKeys = (data,results=[]) => {
     data && data.map(item=>{
         let {key,children,child} = item;
         results.push(key);
         if(children && children.length>0 && !child){
             generateKeys(children,results)
         }
    })
    return results;
};
/*
    * value 选中的选项
    * options 提供的默认选项
    * 加载选择框
    * */
export function renderRadios(value,options){
    let checkedItems = [],resultOptions = [];
    if(value.length > 0 && Object.keys(value[0]).length > 0){
        let values = value[0];
        values.map(item=> checkedItems.push({label: item.label, value: item.id}))
    }
    if(options.length > 0){
        options.map(item=> resultOptions.push({label: item.label, value: item.id}))
    }
    return (
        <RadioGroup
            value={checkedItems.length > 0 ? checkedItems[0].value : ''}
            options={resultOptions}
            className="customClass"
        />
    )
}

/*
    * value 选中的选项
    * options 提供的默认选项
    * 加载选择框
    * */
export function renderCheckbox(value,options){
    let checkedItems = [],resultOptions = [];
    if(value.length > 0 && Object.keys(value[0]).length > 0){
        let values = value[0];
        values.map(item=> checkedItems.push({label: item.label, code: item.id}))
    }
    if(options.length > 0){
        options.map(item=> resultOptions.push({label: item.label, code: item.id}))
    }
    return (
        <CheckboxGroup
            value={checkedItems}
            options={resultOptions}
            valueKey="code"
            className="customClass"
        />
    )
}

/*
    * value 选中的选项
    * options 提供的默认选项
    * 加载特殊选择框，包含文本
    * */
export function renderRadioText(value,options,id,textValue){
    return (
        <div className="other-num-con">
            {
                options && options.map((item,index)=>{
                    return (
                        <Fragment key={index}>
                            <Radio name={id} checked={value.length > 0 && Object.values(value[0]).length > 0 && item.id == value[0][0].id}>
                                {item.label}
                            </Radio>
                            { item.id == id && <span className="num-span">（数量：<b>{textValue}</b>门）</span>}
                        </Fragment>
                    )
                })
            }
        </div>
    )
}


export const getChildScores = (data=[],page) =>{
    let scores = 0;
    data && data.map(item=>{
        let itemScore = page=='apply'?item.zpScore:item.auditScore;
        scores += Number(itemScore || 0)
    })
    scores = dealNumber(scores);
    return scores;
}
export let chnNumChar = ["零","一","二","三","四","五","六","七","八","九"];
let chnUnitSection = ["","万","亿","万亿","亿亿"];
let chnUnitChar = ["","十","百","千"];
export  function sectionToChinese(section){
    let newSection = section;
    let strIns = '', chnStr = '';
    let unitPos = 0;
    let zero = true;
    while(section > 0){
        let v = section % 10;
        if(v === 0){
            if(!zero){
                zero = true;
                chnStr = chnNumChar[v] + chnStr;
            }
        }else{
            zero = false;
            strIns = chnNumChar[v];
            strIns += chnUnitChar[unitPos];
            chnStr = strIns + chnStr;
        }
        unitPos++;
        section = Math.floor(section / 10);
    }
    let isTenToTwo = (newSection>9 && newSection<20)?true:false;
    if(isTenToTwo){
        chnStr = chnStr.slice(1)
    }
    return chnStr;
}
export function formaterZpScore(data=[]){
    let results = [];
    let firstLevelData = data.length>0?data[0]:{};
    let {children=[]} = firstLevelData;
    const obj = function(info,level){
        return {
            title:level?'总分':info.title,
            score:info.score,
            zpScore:info.zpScore
        }
    }
    children.map((item)=>{
        results.push(obj(item))
    })
    results.push(obj(firstLevelData,1))
    return results;
}
export function substrDate(data,length){
    if(data){
        data = data.substr(0,length)
    }
    return data;
}
export function validateScore(score,type='apply'){
    if(type=='slzx'){
        return true;
    }
    var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
    if(!reg.test(score)){
        window.error('分值的输入格式不正确');
        return false;
    }
    if(type=='manager' && dealNumber(score)==0){
        window.error('分值应大于0');
        return false;
    }
    return true;
}
export function validateFhScore(score){
    var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
    if(!reg.test(score)){
        window.error('分值的输入格式不正确');
        return false;
    }
    if(dealNumber(score)>200 || dealNumber(score)<0){
        window.error('分值应在0-200之间');
        return false;
    }
    return true;
}
export function validateScore2(score,type='apply'){
    var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
    if(!reg.test(score)){
        return false
    }
    if(type=='manager' && dealNumber(score)==0){
        return false
    }
    return true;
}
export function deleteLastTree(data){
    data && data.map(item=>{
        let {children,child} = item;
        if(child){
            item.children = [];
        }else {
            deleteLastTree(children)
        }
    })
    return data;
}
export function formaterLables(data){
    let results = [];
    if(data && data.length>0){
        results = data.map(item=>{
            return {
                id:item.ID,
                name:item.NAME,
                value:item.ID
            }
        })
    }
    return results;
}

export function formaterOptions(items,label) {
    if(!items || items.length == 0) return;
    let result = items && items.map(item=>{
        if(label){
            return {
                value:item.id,
                label:item[label]
            }
        }else{
            return {
                code:item.id,
                label:item.name
            }
        }

    })
    return result;
}
export function getThreeLevelData(data,initIndex,results=[]){
    data && data.map(item=>{
        let {children} = item;
        if(initIndex==3){
            results.push(item);
        }

        if(children && children.length>0){
            getThreeLevelData(children,initIndex+1,results);
        }

    })
    return results;
}
export function getRowCount(data,level){
    let {children} = data;
    let length = 0;
   if(level==1){
       length = children.length
   }else if(level==2){
       children.map(item=>{
           let itemData = item.children;
           itemData.map(item2=>{
               length++
           })
       })
   }
   return length;
}
export function throttle(handler, wait=2000) {
    var lastTime = 0;

    return function () {
        var nowTime = new Date().getTime();

        if (nowTime - lastTime > wait) {
            handler.apply(this, arguments);
            lastTime = nowTime;
        }else{
            console.log("请不要快速点击");
        }

    }
}
export function RndNum(n){
    var rnd="";
    for(var i=0;i<n;i++)
        rnd+=Math.floor(Math.random()*10);
    return rnd;
}
export function getDisabledTreeData(data,disabledArr) {
    let newData = [...data];
    newData && newData.map(item=>{
        let {key,children} = item;
        let length = children?children.length:0;
        if(children){
            let count = 0;
           children.map(child=>{
                if(disabledArr.includes(child.key) || child.disabled){
                    count+=1
                }
            })
            if(length==count){
                newData.disabled = true;
                // getDisabledTreeData(data,disabledArr)
            }
            getDisabledTreeData(children,disabledArr)
        }
    })
    return newData;
}
export function dealSpecialStr(str){
   if(str){
      str =  str.replace(/[&\%]/g,'')
   }
   return str;
}