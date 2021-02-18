import React from 'react';
import './EvaluationDetail.scss';
import {dealNumber,sectionToChinese,substrDate} from '../../../utils/util';
import ReactToPrint from 'react-to-print';
class EvaluationDetail extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            isDownload:false
        }
        this.componentRef = null
    }
    componentWillMount(){
        let {page} = this.props;
        if(page && page=='audit'){
            window.parent.document.getElementsByTagName("title")[0].innerText ='评分详情';
        }
    }
    componentWillUnmount(){
        let {page} = this.props;
        if(page && page=='audit') {
            window.parent.document.getElementsByTagName("title")[0].innerText ='智慧校园创建申报管理';
        }
    }
     renderContent = (data, initIndex) => {
        let {page} = this.props;
        return data && data.map((item,index)=>{
            let newIndex = index+1;
            let itemScore = dealNumber(item.score);
            let sortIndex = initIndex==1 ? `${sectionToChinese(newIndex)}、`:initIndex== 2?`${newIndex}、`:initIndex==3?`(${newIndex})`:'';
            let itemTitle = initIndex==3 ?`${item.title}。`:initIndex==4?`${item.title}；`:`${item.title}（${itemScore}分）`;
            let childrenData = item.children;
            return (
                <li>
                    <span className={`title-${initIndex}`}>{`${sortIndex}${itemTitle}`}</span>
                    {
                        initIndex==3 && <span className="score">{dealNumber(item.score)}</span> //分值
                    }
                    {
                        // initIndex==3 &&  <span className="zp-score">{getParentCode(item.parentId,pjxmDatas).zpScore}</span>  //自评分
                        initIndex==3 &&  <span className="zp-score">{dealNumber(item.zpScore)}</span>  //自评分
                    }
                    {
                        // initIndex==3 &&  <span className="zp-score">{getParentCode(item.parentId,pjxmDatas).zpScore}</span>  //自评分
                        page!='apply' &&  initIndex==3 &&  <span className="zj-score">{ dealNumber(item.avgScore)}</span>  //专家
                    }
                    {
                        childrenData && childrenData.length>0 ?
                            <ul className={`table-box table-box-${initIndex} ${initIndex==2 && childrenData && childrenData.length==1?'only-one-child':''}`}>
                                {
                                    this.renderContent(childrenData,initIndex+1)
                                }

                            </ul>:initIndex<4?<span className="no-content"></span>:false
                    }
                </li>
            )
        })
    };

    renderTable = ()=>{
        let {data={},columns,page} = this.props;
        let {fieldConfig,schoolName,updateTime} = data;
        let firstData = fieldConfig.length>0?fieldConfig[0]:{};
        let pjxmDatas = firstData.children;
        let noZjScore = page=='apply';
        if(noZjScore){
             columns.splice(6,1)
        }
        return (
            <React.Fragment>
                <h3 className="title">厦门市中小学智慧校园建设评价指标（试行）</h3>
                <p className="info"><span>学校名称：{schoolName}</span><span>填表时间：{substrDate(updateTime,10)}</span></p>
                <div className={`content`}>
                    <ul className={`columns ${noZjScore?'no-zj':''}`}>
                        {
                            columns && columns.map((item,index)=>{
                                // let width = index==0?'80':index==1?'100':index==2?'180':index==3?'50':index==4?'50':index=='5'?'320':'100';
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
    render(){
        let {page} = this.props;
        return (
            <div className={`evaluation-detail-box`}>
                <React.Fragment>
                    <ReactToPrint
                        trigger={() => {
                            // NOTE: could just as easily return <SomeComponent />. Do NOT pass an `onClick` prop
                            // to the root node of the returned component as it will be overwritten.
                            return  <i className="si si-com_xz download"></i>;
                        }}
                        documentTitle={'评分详情'}
                        content={() => this.componentRef}
                        removeAfterPrint={true}
                    />
                    <div style={{display:'none'}}>
                        <div ref={el => (this.componentRef = el)} className={`evaluation-detail down-load`}>
                            {
                                this.renderTable()
                            }
                        </div>
                    </div>
                </React.Fragment>

                <div className={`evaluation-detail ${page=='apply'?'apply-evaluation-detail':''}`} >
                    {
                        this.renderTable()
                    }
                </div>
            </div>
        )
    }
}

export default EvaluationDetail;