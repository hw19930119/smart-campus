import { AddMain } from '@share/scurd/block/add_main/add_main';


class AddMainWrap extends AddMain {

    ready() {
        let _this = this;
        // 默认值为0的数量全部赋值为null入库
        /*let nums = this.findComponents(['ZD_TSGRDN','ZD_BJB','ZD_PBDN','ZD_DZSB','ZD_QT','ZD_JSY','ZD_XSY',
            'GN_DMTJS','GN_JSJJS','GN_SZHSYS','GN_SZHTYS','GN_DGNS','GN_DZYNS']);
        nums.forEach(item=>{
            if(item.val() == 0){
                item.val(null)
            }
        })*/

        let [ZY_XXKC,ZY_XXKC_NUM,ZY_XMKC,ZY_XMKC_NUM] = this.findComponents(['ZY_XXKC','ZY_XXKC_NUM','ZY_XMKC','ZY_XMKC_NUM']);
        let xx_val = ZY_XXKC.val(),xm_val = ZY_XMKC.val();
        $('.label_ZY_XXKC_NUM,.label_ZY_XMKC_NUM').parent().hide();
        let disabled = true;
        let xx_input = `<span style="margin-right: 15px;margin-left: -12px;">（数量<input name="ZY_XXKC_NUM"
                             maxLength="6"
                             placeholder="请输入"
                             disabled=${disabled}
                             type="number"
                             id="ZY_XXKC_NUM"
                             onkeyup="value=value.replace(/^(0+)|[^\\d]+/g,'')"
                             class="form-control form-control-inline"
                             style="width: 80px; margin: 0 5px">门）</span>`;
        let xm_input = `<span style="margin-right: 15px;margin-left: -12px;">（数量<input name="ZY_XMKC_NUM"
                             maxLength="6"
                             placeholder="请输入"
                             disabled=${disabled}
                             type="number"
                             id="ZY_XMKC_NUM"
                             onkeyup="value=value.replace(/^(0+)|[^\\d]+/g,'')"
                             class="form-control form-control-inline"
                             style="width: 80px; margin: 0 5px">门）</span>`;
        $('.label_ZY_XXKC').next().find('.tipWrap .js-checkbox-ZY_XXKC').eq(0).after(xx_input);
        $('.label_ZY_XMKC').next().find('.tipWrap .js-checkbox-ZY_XMKC').eq(0).after(xm_input);

        xx_val != '' && xx_val != 0 && setInput(xx_val,'132','ZY_XXKC',ZY_XXKC_NUM);
        xm_val != '' && xm_val != 0 && setInput(xm_val,'134','ZY_XMKC',ZY_XMKC_NUM);

        ZY_XXKC.on('change', function () {
            let val = ZY_XXKC.val();
            setInput(val,'132','ZY_XXKC',ZY_XXKC_NUM)
        })
        ZY_XMKC.on('change', function () {
            let val = ZY_XMKC.val();
            setInput(val,'134','ZY_XMKC',ZY_XMKC_NUM)
        })

        function setInput(val,checkedVal,name,obj){
            if(val == checkedVal){
                $(`#${name}_NUM`).attr({disabled:false}).val(obj.val())//可编辑
                _this.stateChange(`${name}_NUM`,obj.val())
            }else{
                $(`#${name}_NUM`).attr({disabled:true}).val('')//不可编辑
                _this.stateChange(`${name}_NUM`,'')
            }
        }

        $('#ZY_XXKC_NUM').on('input propertychange',function () {
            let text = this.value;
            if(text.length > 6){
                text = text.substring(0, 6);
                $('#ZY_XXKC_NUM').val(text)
            }
            _this.stateChange('ZY_XXKC_NUM',text)
        })
        $('#ZY_XMKC_NUM').on('input propertychange',function () {
            let text = this.value;
            if(text.length > 6){
                text = text.substring(0, 6);
                $('#ZY_XMKC_NUM').val(text)
            }
            _this.stateChange('ZY_XMKC_NUM',$('#ZY_XMKC_NUM').val())
        })



    }

}

export default AddMainWrap;
