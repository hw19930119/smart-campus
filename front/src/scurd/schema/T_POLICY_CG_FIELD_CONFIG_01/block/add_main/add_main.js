import { AddMain } from '@share/scurd/block/add_main/add_main';


class AddMainWrap extends AddMain {

    ready() {
        // 字符长度、输入内容格式 图片大小，图片张数,选项,附近类型，模板
        const ColumnArrays = ['characterSize', 'inputFormat', 'size', 'num', 'choiceOption', 'fileType', 'fileTemplateAddress', 'DATA_READ', 'ADDRESS', 'TIMER', 'decimalPlaces', 'per_unit', 'money_unit', 'imageTypeOption'];
        const thisCopy = this;
        const isEist = (this.props.g_id !== undefined);
        const FIELD_CATEGORY = thisCopy.findComponent('FIELD_CATEGORY');
        FIELD_CATEGORY.readOnly(true);
        // 字段控件
        const FIELD_CONTROL = thisCopy.findComponent('FIELD_CONTROL');

        const removeFun = function () {
            ColumnArrays.forEach((item, index) => {
                thisCopy.removeColumns(ColumnArrays[index]);
            });
        };

        const recoveryFun = function (FIELD_CONTROL_VAL) {
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

        removeFun();

        // 编辑回显
        if (isEist) {
            console.info('编辑回显');
            recoveryFun(FIELD_CONTROL.val());
        } else {
            // 新增 是否入库 默认选中否
            this.stateChange('IN_STOCK_FLAG', '0');
        }

        // 字段名称
        const cnName = this.findComponent('FIELD_CN_NAME');

        this.on('FIELD_CN_NAME', 'change', () => {
            const vals = FIELD_CONTROL.val();

            if (vals === '1' || vals === '2') {
                this.stateChange('PROMPT_MSG', `请输入${cnName.val()}`);
            } else if (vals === '4' || vals === '5') {
                // 选择框
                this.stateChange('PROMPT_MSG', `请选择${cnName.val()}`);
            } else {
                this.stateChange('PROMPT_MSG', '');
            }

        });

        FIELD_CONTROL.on('change', () => {
            const vals = FIELD_CONTROL.val();

            removeFun();
            recoveryFun(vals);
            // 输入框
            if (vals === '1' || vals === '2') {
                this.stateChange('PROMPT_MSG', `请输入${cnName.val()}`);
            } else if (vals === '4' || vals === '5') {
                // 选择框
                this.stateChange('PROMPT_MSG', `请选择${cnName.val()}`);
            } else {
                this.stateChange('PROMPT_MSG', '');
            }
        });

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

        this.on('inputFormat', 'change', () => {
            const vals = inputFormatData.val();

            // 选择【数字】【百分比】【金额】时，可设置小数点位数
            if (vals === '3') {
                thisCopy.recoveryColumns('decimalPlaces');
                thisCopy.removeColumns('per_unit');
                thisCopy.removeColumns('money_unit');
            } else if (vals === '4') {
                thisCopy.recoveryColumns('decimalPlaces');
                thisCopy.removeColumns('money_unit');
                thisCopy.recoveryColumns('per_unit');
                thisCopy.stateChange('per_unit', '2');
            } else if (vals === '5') {
                thisCopy.recoveryColumns('decimalPlaces');
                thisCopy.removeColumns('per_unit');
                thisCopy.recoveryColumns('money_unit');
            } else {
                thisCopy.removeColumns('decimalPlaces');
                thisCopy.removeColumns('per_unit');
                thisCopy.removeColumns('money_unit');
            }

        });


    }

}

export default AddMainWrap;
