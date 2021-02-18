/*
 * @(#) SelectedExpert.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-10-30 18:03:39
 */

import React from 'react';
import './style.scss'
import {sectionToChinese} from '../../../utils/util';
import { Panel,Icon,Label } from '@share/shareui';
import {columnsExpert} from "../../../utils/code";

class SelectedExpert extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            arrow:  true,  //申报详情或者审核详情默认展开菜单
        }
        this.componentRef = null
    }
    renderContent = (data, initIndex) => {
        let {expertDetail} = this.props;
        return data && data.map((item,index)=>{
            let newIndex = index+1;
            let sortIndex = initIndex==1 ? `${sectionToChinese(newIndex)}、`:initIndex== 2?`${newIndex}、`:initIndex==3?`(${newIndex})`:'';
            let itemTitle = initIndex==3 ?`${item.title}。`:initIndex==4?`${item.title}；`:`${item.title}`;
            let childrenData = item.children;
            let expertList = item.expertNames || [];
            let context = item.context || [];
            return (
                <li key={index}>
                    <span className={`title-${initIndex}`}>{`${sortIndex}${itemTitle}`}</span>
                    {
                        initIndex==2 && expertList && <ul className="table-box expert-box">
                            {
                                expertList.map((i,idx)=>{
                                    let height = 100/(expertList.length) + '%';
                                    return (
                                        <li key={`${idx}-${newIndex}`} style={{height:height}}>
                                            <span className={`experts ${expertDetail?'experts-detail':''}`}>{i}</span>
                                            {/*{*/}
                                                {/*expertDetail  &&  <span className="experts-context">{context[idx]}</span>*/}
                                            {/*}*/}

                                        </li>
                                    )
                                })
                            }
                        </ul>
                    }
                    {
                        initIndex <2 &&   childrenData && childrenData.length>0 ?
                            <ul className={`table-box table-box-${initIndex} ${initIndex==2 && childrenData && childrenData.length==1?'only-one-child':''}`}>
                                {
                                    this.renderContent(childrenData,initIndex+1)
                                }

                            </ul>:false
                    }
                </li>
            )
        })
    };

    renderTable = ()=>{
        let {data=[],expertDetail=false} = this.props;
        let firstData = data.length>0?data[0]:{};
        let pjxmDatas = firstData.children;
        let columns = columnsExpert;
        // if(expertDetail){
        //     columns = columns.concat([{
        //         title: '专家评语',
        //         dataIndex: 'zjDetail',
        //         key: 'zjDetail',
        //     }])
        // }
        return (
            <React.Fragment>
               <div className="content">
                    <ul className={`columns ${expertDetail?'has-zj':''}`}>
                        {
                            columns && columns.map((item,index)=>{
                                return  <li key={index}>{item.title}</li>
                            })
                        }
                    </ul>

                    <ul className="table-box table-content">
                        {
                            this.renderContent(pjxmDatas,1)
                        }
                    </ul>
                </div>
            </React.Fragment>
        )
    }
    //是否收起
    changeArrow = ()=>{
        this.setState({
            arrow: !this.state.arrow
        })

    }
    render(){
        let {title='已分配专家名单',expertDetail} = this.props;
        let {arrow,arrowTitle} = this.state;
        return (
            <div className={`evaluation-detail-box-expert`}>
                <Panel>
                    <Panel.Head title={title}>
                        <Panel.HeadRight>
                            {
                                expertDetail &&  <Label bsStyle="primary" onClick={this.props.handleDetail}>查看点评详情</Label>
                            }

                            <span className="change-label" onClick={this.changeArrow}>{arrow?'收起':'展开'}<Icon className={`si ${arrow?'si-com_angleup':'si-com_angledown'}`}/></span>
                        </Panel.HeadRight>
                    </Panel.Head>
                    {
                        arrow  &&  <Panel.Body>
                            <div className={`evaluation-detail-expert  ${expertDetail?'expert-detail':''}`}>
                                {
                                    this.renderTable()
                                }
                            </div>
                        </Panel.Body>
                    }

                </Panel>
            </div>
        )
    }
}

export default SelectedExpert;