import React from 'react';
import {getComponents, FormState} from '@share/shareui-form';
import {validetaSuperScore,dealNumber,validateScore} from "../../../utils/util";

const {Form,Row, Input, RadioGroup,Textarea} = getComponents();
class AddIndexAllocation extends React.Component{
    constructor(props){
        super(props)
        let {type,itemInfo} = props;
        let data = {...itemInfo};
        if(type=='edit'){
            data.score = dealNumber(data.score)
        }
        let {isDisabled,categoryType} = itemInfo;
        this.state = {
            itemInfo,
            type,  //0新增,1编辑
            disabled : isDisabled,
            categoryType:categoryType,
            formState:new FormState(
                {
                    ...data
                },
                (form,callback) => this.setState({form},callback)
            )
        };
    }
    getValue = async() => {
        let {type,formState,itemInfo} = this.state;
        let {treeList,saveData} = this.props;
        let data = {...formState.formData};
        let val = await formState.valid();
        let ele = val.every(item => item == true);
        if(ele){
            let {categoryType} = itemInfo;
            let parentId = type=='add'?itemInfo.key:itemInfo.parentId;
            let key = type == 'add'?'':itemInfo.key;
            let {score} = data;
            let params = {
                title:data.title,
                score:score?dealNumber(score):'0.00',
                key:data.key||'0',
                categoryType:data.categoryType,
                isAdd:type=='add'?'0':'1',
            }
            if(categoryType=='0'){
                if(!validateScore(score,'manager')){
                    return;
                }
                if(!validetaSuperScore(parentId,key,treeList,data.score)){
                    window.error('下级分数之和不能超过上级分类总分！');
                    return;
                }
               saveData && saveData(params);
            }
            else{
                saveData && saveData(params);
            }
        }else{
            console.log("校验未通过");
        }
    }
    render(){
        let { formState,categoryType} = this.state;
        return (
            <div className="allocation-add">
                <Form formState={formState}>
                    <Row>
                        <Textarea
                            field={'title'}
                            label={'名称'}
                            required={true}
                            rule={"required"}
                            disabled={false}
                            maxLength={200}
                            limit
                            autoHeight
                        />
                    </Row>
                    <Row>
                        <RadioGroup
                            field={'categoryType'}
                            label={'分类'}
                            required={true}
                            rule={"required"}
                            options={[
                                { value: '0', label: '目录' },
                                { value: '1', label: '评分项目' },
                            ]}
                            disabled={true}
                        />
                    </Row>
                    {
                        categoryType == '0' && <Row>
                            <Input
                                field={'score'}
                                type={'number'}
                                min={0}
                                label={'分值'}
                                required={true}
                                rule={"required"}
                                maxLength={5}
                            />
                        </Row>
                    }


                </Form>
            </div>
        )
    }
}
export default AddIndexAllocation;