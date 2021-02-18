/*
 * @(#) WordDownLoad.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2019
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author liuxia
 * <br> 2020/11/9 16:56
 */
import React from 'react';
import PDF from 'react-pdf-js';
import {Panel} from '@share/shareui';
import './style.scss'
const pdfUrl = './files/智慧校园标准提供材料要求明细.pdf';
class MyPdfViewer extends React.Component {
    state = {
        page: 1,
        isHidden:true
    };

    onDocumentComplete = (pages) => {
        this.setState({ page: 1, pages });
    }

    handlePrevious = () => {
        this.setState({ page: this.state.page - 1 });
    }

    handleNext = () => {
        this.setState({ page: this.state.page + 1 });
    }

    renderPagination = (page, pages) => {
        let previousButton = <li className="previous" onClick={this.handlePrevious}><a href="javascript:void(0)"><i className="fa fa-arrow-left"></i> 上一页</a></li>;
        if (page === 1) {
            previousButton = <li className="previous disabled"><a href="javascript:void(0)"><i className="fa fa-arrow-left"></i> 上一页</a></li>;
        }
        let nextButton = <li className="next" onClick={this.handleNext}><a href="javascript:void(0)">下一页 <i className="fa fa-arrow-right"></i></a></li>;
        if (page === pages) {
            nextButton = <li className="next disabled"><a href="javascript:void(0)">下一页 <i className="fa fa-arrow-right"></i></a></li>;
        }
        return (
            <nav>
                <ul className="pager">
                    {previousButton}
                    {nextButton}
                </ul>
            </nav>
        );
    }

    showPdf = ()=>{
        this.setState({
            isHidden:false
        })
    }

    downLoadPdf = ()=>{
        window.open(pdfUrl)
    }

    render() {
        let {isHidden} = this.state;
        let pagination = null;
        if (this.state.pages) {
            pagination = this.renderPagination(this.state.page, this.state.pages);
        }
        return (
            <div className="pdf-detail-con">
                <Panel className="pdf-title">
                    <Panel.Head title="文档下载"/>
                    <Panel.Body>
                        <ul className="pdf-info">
                            <li><span>文档名称：</span>智慧校园标准提供材料要求明细</li>
                            <li><span>文档格式：</span>PDF</li>
                            <li><span>上传时间：</span>2020-11-10</li>
                            <li>
                                <a href="javascript:void(0)" onClick={()=>this.showPdf()}>预览</a>
                                <a href="javascript:void(0)" onClick={()=>this.downLoadPdf()}>下载</a>
                            </li>
                        </ul>

                    </Panel.Body>
                </Panel>
                <div className={`pdf-show ${isHidden && 'pdf-show-hidden'}`}>
                    <PDF
                        file={pdfUrl}
                        onDocumentComplete={this.onDocumentComplete}
                        page={this.state.page}
                    />
                    {pagination}
                </div>
            </div>
        )
    }
}

export default MyPdfViewer;