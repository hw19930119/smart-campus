/*
 * @(#) Ulynlist_Dom.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author lihui
 * <br> 2020-08-14 15:45:21
 */
import React from "react";
import "./Ulynlist_Dom.scss";
/*
* VW_SCF_TODO_FA
* VW_SCF_DONE*/
class Ulynlist_Dom extends React.Component{
    render(){
        const { tableColumn, list,id, startRowNum, currentObj, $outputContent, $outputLabel, $doFunc, extra } = this.props;
        let FN = null;
        switch (id){
            case "js-tableVW_SCF_TODO_FA" :
                FN = '1';
                break;
            case "js-tableVW_SCF_DONE" :
                FN = '2';
                break;
            case "js-tableVW_SCF_DONEALL_FA" :
                FN = '3';
                break;
            case "js-tableVW_AUDIT_TEMPLATE_ASSIGN_FA" :
                FN = "4";
                break;
            case "js-tableVW_AUDIT_TEMPLATE_COMMENT_FA" :
                FN = "5";
                break;
        }
        return(
            <div className="ulynlist-dom">
                {
                    list && list.length > 0 && list.map((item,index)=>{
                        return (
                           <div className="list-box" key={index}>
                               {
                                   FN=='5' ?
                                       <div className="list-box-content school-item">
                                           <div className="school-info">
                                               <p>
                                                   {item.SCHOOL_NAME}
                                               </p>
                                               <span>{item.COMMENT_STATUS}</span>
                                           </div>
                                           <div className="change-btn" dangerouslySetInnerHTML={{ __html: $outputContent(item, tableColumn.columns[tableColumn.columns.length - 1], currentObj, index) }} />
                                       </div>:
                                       <React.Fragment>
                                           <div className="list-box-title">
                                               <div>
                                                   <span>{index+1}、{item.SCHOOL_NAME}</span>
                                                   {
                                                       item.SCHOOL_TYPE && <span>{item.SCHOOL_TYPE}</span>
                                                   }
                                               </div>
                                           </div>
                                           <div className={`list-box-content ${FN == '1' && 'db-con'}`}>
                                               <div>
                                                   <div>
                                                       <div>
                                                           <label>申报重点:</label>
                                                           <span> {item.SBZD}</span>
                                                       </div>
                                                       <div>
                                                           <label>申报人:</label>
                                                           <span> {item.LXR_NAME}</span>
                                                       </div>
                                                       <div>
                                                           <label>行政区划:</label>
                                                           <span> {item.XZQH}</span>
                                                       </div>
                                                       {
                                                           FN != '1' && FN != '5' &&
                                                           <div style={{width:"120px"}}>
                                                               <label>数据状态:</label>
                                                               <span> {item.PC_STATE}</span>
                                                           </div>
                                                       }
                                                   </div>
                                                   <div>
                                                       <div>
                                                           <label>提交时间:</label>
                                                           <span> {item.COMMIT_TIME}</span>
                                                       </div>
                                                       <div>
                                                           <label>工作联系人电话:</label>
                                                           <span> {item.LXR_PHONE}</span>
                                                       </div>
                                                       {
                                                           (FN == '4' || FN == '5') ?
                                                               <div>
                                                                   <label>评审状态:</label>
                                                                   <span> {item.COMMENT_STATUS}</span>
                                                               </div> :
                                                               <div>
                                                                   <label>当前申报状态:</label>
                                                                   <span> {item.STATE}</span>
                                                               </div>
                                                       }
                                                   </div>
                                               </div>
                                               <div dangerouslySetInnerHTML={{ __html: $outputContent(item, tableColumn.columns[tableColumn.columns.length - 1], currentObj, index) }} />
                                           </div>
                                   </React.Fragment>
                               }

                           </div>
                       )
                    })
                }
            </div>
        )
    }
}
export default Ulynlist_Dom;
