import { DetailMain } from '@share/scurd/block/detail_main/detail_main';
class DetailMainWrap extends DetailMain {

    ready() {
        // 字符长度、输入内容格式 图片大小，图片张数,选项,附近类型，模板
        const ColumnArrays = ['characterSize', 'inputFormat', 'size', 'num', 'choiceOption', 'fileType', 'fileTemplateAddress', 'DATA_READ', 'ADDRESS', 'TIMER', 'decimalPlaces', 'per_unit', 'money_unit', 'imageTypeOption'];
        const thisCopy = this;

        // 字段控件
        const FIELD_CONTROL = thisCopy.findComponent('FIELD_CONTROL');

        // console.log("FIELD_CONTROL====",FIELD_CONTROL,TAGS,thisCopy)
        const removeFun = function(){
            ColumnArrays.forEach((item, index) => {
                thisCopy.removeColumns(ColumnArrays[index]);
            });
        };
        removeFun();

        const recoveryFun = function(FIELD_CONTROL_VAL){
            let arrays = [];

            switch (FIELD_CONTROL_VAL) {
            case '1':// 单文本输入框
                arrays = ['characterSize', 'inputFormat'];
                break;
            case '2':// 多文本输入框
                arrays = ['characterSize'];
                break;
            case '3':// 图片上传
                arrays = ['size', 'num', 'imageTypeOption'];
                break;
            case '4':// 单选框
                arrays = ['choiceOption'];
                break;
            case '5':// 多选框
                arrays = ['choiceOption'];
                break;
            case '6':// 下拉单选
                arrays = ['choiceOption'];
                break;
            case '7':// 下拉多选
                arrays = ['choiceOption'];
                break;
            case '8':// 附件上传
                arrays = ['fileType', 'fileTemplateAddress'];
                break;
            case '9':// 数据读取
                arrays = ['DATA_READ'];
                break;
            case '10':// 地址库
                arrays = ['ADDRESS'];
                break;
            case '11':// 时间
                arrays = ['TIMER'];
                break;
            default:
                arrays = [];
            }
            arrays.forEach((index, item) => {
                thisCopy.recoveryColumns(arrays[item]);
            });

        };

        recoveryFun(FIELD_CONTROL.val());


        // 字段控件
        const inputFormatData = thisCopy.findComponent('inputFormat');

        // 选择【数字】【百分比】【金额】时，可设置小数点位数
        if (inputFormatData.val() === '3') {
            thisCopy.recoveryColumns('decimalPlaces');
            thisCopy.removeColumns('per_unit');
            thisCopy.removeColumns('money_unit');
        } else if (inputFormatData.val() === '4') {
            thisCopy.recoveryColumns('decimalPlaces');
            thisCopy.removeColumns('money_unit');
            thisCopy.recoveryColumns('per_unit');
            thisCopy.stateChange('per_unit', '2');
        } else if (inputFormatData.val() === '5') {
            thisCopy.recoveryColumns('decimalPlaces');
            thisCopy.removeColumns('per_unit');
            thisCopy.recoveryColumns('money_unit');
        } else {
            thisCopy.removeColumns('decimalPlaces');
            thisCopy.removeColumns('per_unit');
            thisCopy.removeColumns('money_unit');
        }
        // let TAG = thisCopy.findComponent('TAG');
        // let TAGS = TAG.val();
        // if(TAGS){
        //     TAGS = JSON.parse(TAGS);
        //     let labels = '<div>'
        //     TAGS.forEach(function(item,index){
        //         let lableIndex = lableStyle[index%(lableStyle.length)];
        //         labels += `<Label bsStyle=${lableIndex}>{item.name}</Label>`
        //     })
        //     let tagsContent = `${labels}</div>`;
        //     console.log("tagsContent====",tagsContent,$(".label_TAG"))
        //     $("#TAG").text('DSDSDS')
        // }

    }
}
export default DetailMainWrap;
