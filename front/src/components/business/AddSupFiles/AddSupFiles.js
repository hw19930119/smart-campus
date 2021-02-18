/*
 * @(#) AddSupFiles.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2020
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author Administrator
 * <br> 2020-11-18 14:50:32
 */

import React from 'react';
import {Spin,FileUpload,Button,Panel} from '@share/shareui';
import {getContextPath} from '../../../utils/formatter';
import './AddSupFiles.scss';
const fileType = ["jpg","pdf","png"];
let context = getContextPath();
class AddSupFiles extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            supFiles:this.props.itemInfo.supFiles || [],
            canAddFile:this.props.itemInfo.canAddFile,
        }
    }
    removeFile = (index) => {
        let {supFiles} = this.state;
        supFiles.splice(index,1)
        this.setState({supFiles});
    };
    getFile = ()=>{
        let {supFiles} = this.state;
        return supFiles;
    }
    componentWillReceiveProps(next){
        if(JSON.stringify(next.itemInfo)!=JSON.stringify(this.props.itemInfo)){
            this.setState({
                supFiles:next.itemInfo.supFiles || [],
                canAddFile:next.itemInfo.canAddFile
            })
        }
    }
    render(){
        let {supFiles,canAddFile} = this.state;
        let {page,maxSize} = this.props;
        console.log("supFiles",supFiles,page,canAddFile)
        return (
            <div className="allocation-add add-supFile">
                <Panel bsStyle="primary">
                    {
                        page != 'apply' &&  supFiles.length ==0 ?false:
                            <React.Fragment>
                                {
                                    (page == 'apply' && canAddFile || supFiles.length>0 ) &&
                                    <Panel.Head>
                                        <em className="ico-require">*</em>补充材料
                                    </Panel.Head>
                                }

                                <Panel.Body>
                                    {supFiles.length>0 &&
                                        <ul className="file-lists">
                                            {
                                                supFiles.map((item,index)=>{
                                                    return (
                                                        <li className="file-item">
                                                            <a title="下载" href={`${context}/resource${item.url}`} download={`${item.original}`}>
                                                                {item.original}
                                                            </a>
                                                            {
                                                                page == 'apply' ?
                                                                    <i
                                                                        className='deleteBtn' title="删除"
                                                                        onClick={() => {
                                                                            this.removeFile(index)
                                                                        }}
                                                                    >
                                                                        <svg t="1597113398549" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="9486" width="16" height="16"><path d="M512.1 127.8c49.9 0 98.5 9.5 144.5 28.1 47.6 19.3 90.3 47.7 127 84.4s65.1 79.4 84.4 127c18.7 46 28.1 94.6 28.1 144.5s-9.5 98.5-28.1 144.5c-19.3 47.6-47.7 90.3-84.4 127s-79.4 65.1-127 84.4c-46 18.7-94.6 28.1-144.5 28.1-49.9 0-98.5-9.5-144.5-28.1-47.6-19.3-90.3-47.7-127-84.4s-65.1-79.4-84.4-127c-18.7-46-28.1-94.6-28.1-144.5s9.5-98.5 28.1-144.5c19.3-47.6 47.7-90.3 84.4-127s79.4-65.1 127-84.4c46-18.6 94.6-28.1 144.5-28.1m0-64c-114.7 0-229.3 43.7-316.8 131.2-175 175-175 458.6 0 633.6 87.5 87.5 202.1 131.2 316.8 131.2s229.3-43.7 316.8-131.2c175-175 175-458.6 0-633.6-87.5-87.4-202.1-131.2-316.8-131.2z" p-id="9487" fill="#d81e06"></path><path d="M715.8 670.3L557.4 511.8l158.3-158.3-45.3-45.2-158.3 158.3-158.4-158.4-45.3 45.2 158.4 158.4-158.3 158.4 45.2 45.3 158.4-158.4 158.4 158.4z" p-id="9488" fill="#d81e06"></path></svg>
                                                                    </i>:
                                                                    false

                                                            }

                                                        </li>

                                                    )
                                                })
                                            }
                                        </ul>
                                    }
                                    {
                                        page == 'apply' && canAddFile &&
                                        <FileUpload
                                            request={{
                                                url: `${context}/upload`
                                            }}
                                            onComplete={({ response, status }) => {
                                                Spin.hide();
                                                let maxLength = maxSize? maxSize :3;
                                                if(supFiles.length == maxLength){
                                                    error(`最多上传${maxLength}个文件`);
                                                    return;
                                                }
                                                // console.log("res",response,status);
                                                if(response.status == "1401"){
                                                    error(response.message);
                                                    return;
                                                }
                                                let files = response.data[0];
                                                let size = files.fileSize;
                                                let original = files.original;
                                                let nameArr = files.type.split(".");
                                                let type = nameArr[nameArr.length - 1].toLowerCase();
                                                if(size > 50 * 1024 * 1024){
                                                    error("上传文件不能大于10M");
                                                    return;
                                                }
                                                if(!fileType.includes(type)){
                                                    error("不支持该文件类型");
                                                    return;
                                                }
                                                if(original.includes(",") || original.includes("$")){
                                                    error("文件名存在特殊符号，如逗号或者$");
                                                    return;
                                                }
                                                supFiles.push(files);
                                                this.setState({
                                                    files: supFiles
                                                });
                                            }}
                                            uploadProps={{
                                                //接受文件类型，如果不传默认为接受图片：accept: '.gif,.jpg,.jpeg,.bmp,.png,.GIF,.JPG,.PNG,.BMP'
                                                accept: ".jpg,.pdf,.png"
                                            }}
                                            onChange={e => {
                                                // 选择或更改文件后触发
                                                let maxLength = maxSize? maxSize :3;
                                                if(supFiles.length == maxLength){
                                                    return;
                                                }
                                                Spin.show('上传中...');
                                                $(".files-hide").removeClass("show");
                                            }}
                                        >
                                            <Button type="button" bsStyle="default">点击上传文件</Button>
                                        </FileUpload>
                                    }

                                    {/*<FileUpdate files={supFiles} removeFile={this.removeFile}/>*/}

                                </Panel.Body>
                            </React.Fragment>

                    }

                </Panel>
            </div>
        )
    }
}
export default AddSupFiles;