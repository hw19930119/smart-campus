
import React,{Fragment} from 'react';
import {Panel,Button,Form} from '@share/shareui';
import { Rate } from 'antd';
import {desc} from '../../../utils/code';
import './ScoreRecord.scss';
import ModalBox from '../ModalBox/ModalBox';
import {getContextPath} from '../../../utils/formatter';
let context = getContextPath();
class ScoreRecord  extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            fileModal:false,
            files:[]
        }
    }
    handleEvaluationModal = (files,status)=>{
        this.setState({
            files,
            fileModal:status
        })
    }
    render(){
        let {title,data,toScore,starData={},scoreStatus,handleEvaluationModal} = this.props;
        let {expertPointMap ={},starLevelList=[]} = starData;
        let {fileModal,files} = this.state;
        return (
            <div className="progress-con score-con">
                <Panel bsStyle="primary">
                    <Panel.Head title={title}>
                        <Panel.HeadRight>
                            {/*{style && style == 'add' && <Button type="button" bsStyle="primary" onClick={()=>toScore()}>我要评分</Button>}*/}
                            {scoreStatus && scoreStatus == '1' ? <Button type="button" bsStyle="primary" onClick={()=>handleEvaluationModal(true)}>查看评分详情</Button> :
                                scoreStatus && scoreStatus == '2' ? <Button type="button" bsStyle="primary" onClick={()=>toScore()}>我要评分</Button> : ''
                            }
                        </Panel.HeadRight>
                    </Panel.Head>
                    <Panel.Body>
                        <table className="progress-table score-table">
                            <colgroup>
                                <col width={'20%'}/>
                                <col width={'20%'}/>
                                <col width={'20%'}/>
                                <col width={'40%'}/>
                            </colgroup>
                            <thead>
                            <tr className="progress-table-title">
                                <th>评分角色</th>
                                <th>评分人</th>
                                <th>评分</th>
                                <th>处理时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                data && data.map((item,index)=>{
                                    return (
                                        <tr key={index}>
                                            <td>{item.roleName}</td>
                                            <td>{item.pfPersonName}</td>
                                            <td>{item.pfSum}</td>
                                            <td>{item.createTime}</td>
                                        </tr>
                                    )
                                })
                            }

                            </tbody>
                        </table>

                        {/* 平均分展示 */}
                        {
                            starData  &&
                            <Form pageType="addPage" className="pj-score-con">
                                <Form.Table>
                                    <Form.Tr>
                                        {starLevelList && starLevelList.length > 0 && starLevelList.map((item,index)=>{
                                            let REVIEW_MATERIALS = item.REVIEW_MATERIALS ? JSON.parse(item.REVIEW_MATERIALS):[];
                                            console.log("REVIEW_MATERIALS=====",REVIEW_MATERIALS);
                                            let file = REVIEW_MATERIALS[0];
                                            let colSpan = starLevelList.length > 0 ? starLevelList.length*2 -1 : 1;
                                            colSpan = colSpan>4?4:colSpan;
                                            return (
                                                <Fragment key={index}>
                                                    {
                                                        item.AV_SCORE_LABEL && <React.Fragment>
                                                            <Form.Label  colSpan={2}>{item.AV_SCORE_LABEL}</Form.Label>
                                                            <Form.Content colSpan={colSpan}>
                                                                <span>{item.AV_SCORE || '0'}分</span>
                                                                {
                                                                    REVIEW_MATERIALS.length>0 &&  <a title="下载" className="download-btn" href={`${context}/resource${file.url}`} download={`${file.original}`}>
                                                                        下载复核材料
                                                                    </a>
                                                                }
                                                            </Form.Content>
                                                        </React.Fragment>
                                                    }


                                                </Fragment>
                                            )
                                        })}
                                        {/*<Form.Label>{expertPointMap.AV_SCORE_LABEL}</Form.Label>*/}
                                    </Form.Tr>
                                    <Form.Tr>
                                        {starLevelList && starLevelList.length > 0 && starLevelList.map((item,index)=>{
                                            let colSpan = starLevelList.length > 0 ? starLevelList.length*2 -1 : 1;
                                            colSpan = colSpan>4?4:colSpan;
                                            return (
                                                <Fragment key={index}>
                                                    {
                                                        item.STAR_LEVEL_LABEL && <React.Fragment>
                                                            <Form.Label colSpan={2}>{item.STAR_LEVEL_LABEL}</Form.Label>
                                                            <Form.Content colSpan={colSpan}>
                                                                <Rate disabled tooltips={desc} defaultValue={item.STAR_LEVEL} />
                                                            </Form.Content>
                                                        </React.Fragment>
                                                    }

                                                </Fragment>
                                            )
                                        })}
                                    </Form.Tr>
                                </Form.Table>
                            </Form>

                        }

                    </Panel.Body>
                </Panel>
                {/*{*/}
                    {/*fileModal &&*/}
                    {/*<ModalBox title={'评分详情'} handleModal={()=>this.handleEvaluationModal([],false)} size={'sm'}>*/}
                        {/*{*/}
                            {/*files && <ul className="file-lists">*/}
                                {/*{*/}
                                    {/*files.map((item2,index2)=>{*/}
                                        {/*return (*/}
                                            {/*<li className="file-item" key={index2}>*/}
                                                {/*<a title="下载" href={`${context}/resource${item2.url}`} download={`${item2.original}`}>*/}
                                                    {/*{item2.original}*/}
                                                {/*</a>*/}
                                            {/*</li>*/}
                                        {/*)*/}
                                    {/*})*/}
                                {/*}*/}
                            {/*</ul>*/}
                        {/*}*/}
                    {/*</ModalBox>*/}
                {/*}*/}
            </div>
        );
    }
}

export default ScoreRecord;
