import {AddMain} from '@share/scurd/block/add_main/add_main';
import {getDataFromStore} from '../../../../../utils/StoreUtil';

class AddMainWrap extends AddMain {

    ready() {
        let {pcNo, state, pcState} = this.props || '';
        let roleInfo = getDataFromStore('roleInfo') || {};
        let {schoolName, schoolType, schoolTypeName} = roleInfo.loginUser || '';
        this.stateChange('PC_NO', pcNo);
        this.stateChange('SCHOOL_NAME', schoolName);
        this.stateChange('SCHOOL_TYPE', schoolType);
        this.stateChange('SCHOOL_TYPE_NAME', schoolTypeName);
        $('.label_SCHOOL_TYPE_NAME').parent().hide();
        let stateArr = ['13', '18', '25', '26', '27'];
        if (stateArr.includes(state) && pcState != '2') { //非草稿状态，不能再提交草稿
            $('.save-cg').hide();
        }
        let title = `<tr class="item"><td colSpan=${4} style="padding: 10px 20px;background: #f7f8f9;"><b>创建工作分管领导</b></td></tr>`;
        let lxrTitle = `<tr class="item"><td colSpan=${4} style="padding: 10px 20px;background: #f7f8f9;"><b>创建工作联系人</b></td></tr>`

        $('.label_LD_NAME').parent().before(title);
        $('.label_LXR_NAME').parent().before(lxrTitle);

        let SCHOOL_TYPE_NAME = this.findComponent('SCHOOL_TYPE_NAME');
        let val = SCHOOL_TYPE_NAME.val();
        let _input = `<input name="SCHOOL_TYPE_NAME2" 
                             maxlength="100" 
                             placeholder="请输入" 
                             disabled
                             type="text" 
                             id="SCHOOL_TYPE_NAME2" 
                             class="form-control form-control-inline"
                             style="width: 300px;">`;
        $('.label_SCHOOL_TYPE').next().find('.tipWrap .form-group').append(_input);
        $('#SCHOOL_TYPE_NAME2').val(schoolType == 'S06' ? val : '');
        let [SCHOOL_NAME, SCHOOL_TYPE] = this.findComponents(['SCHOOL_NAME', 'SCHOOL_TYPE']);
        SCHOOL_NAME.readOnly(true);
        SCHOOL_TYPE.readOnly(true);

        //默认值为0的数量全部赋值为null入库
        /*let nums = this.findComponents(['STUDENT_COUNT','CLASS_COUNT','FACULTY_COUNT','GJJS_COUNT']);
        nums.forEach(item=>{
            if(item.val() == 0){
                item.val(null)
            }
        })*/

    }

}

export default AddMainWrap;
