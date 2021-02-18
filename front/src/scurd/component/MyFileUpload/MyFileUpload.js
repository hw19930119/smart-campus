import FileUpload from '@share/scurd/component/fileupload/fileupload';
import UploaderFix from "@share/scurd/component/UploaderFix";
import { Uploader, UploadField } from '@share/navjobs_upload';
class MyFileUpload extends FileUpload{
    constructor(props){
        super(props);
        this.state = {
            ...this.state
        }
    }
    render() {
        let { itemData, config, stateChange } = this.props;
        let { uploadUrl, resourceUrl, fileSystemVersionInited } = this.state;
        let self = this;
        let itemValues = stateChange(itemData.column).split(",");
        let items = [];
        itemValues.length > 1 && itemValues.map(function (item, index) {
            if(_.startsWith(item, "/")) {
                let fileNameArr = itemValues[ index + 1 ].split("$$");
                var fileContentType = fileNameArr[ 0 ].match(/^(.*)(\.)(.{1,8})$/)[ 3 ];
                if(fileContentType) {
                    fileContentType = fileContentType.toLowerCase();
                    var trans = { "xlsx": "xls", "jpeg": "jpg" };
                    if(trans[ fileContentType ]) {
                        fileContentType = trans[ fileContentType ];
                    }
                    if($.inArray(fileContentType, ["avi", "doc", "jpg", "pdf", "rar", "txt", "xls", "zip"]) == -1) {
                        fileContentType = "exts";
                    }
                } else {
                    fileContentType = "exts";
                }
                let fileSize = fileNameArr.length > 1 ? self.renderSize(fileNameArr[ 1 ]) : "未知大小";

                items.push({ "url": item, "title": fileNameArr[ 0 ], "ext": fileContentType, "fileSize": fileSize });
            }
        });

        let styleObj = this.stylePropFormat(itemData.style);
        let readOnly = this.state.readOnly || itemData.readOnly;

        let showAdd = true;
        if(itemData.ext.max && itemData.ext.max <= items.length) {
            showAdd = false;
        }
        let fileMaxSize = itemData.ext.size;
        if(!fileMaxSize) {
            fileMaxSize = DEAULT_MAX_SIZE;
        }

        return (
            <div className={"fileupload_" + itemData.column} {...super.defaultScurdProps()}
                 onChange={e => {
                 }}
                 onClick={e => {
                 }}
                 onFocus={e => {
                 }}
                 onBlur={e => {
                 }}
                 style={styleObj}
            >
                {items.map((item, index) => {
                    return (
                        <div key={"page" + index} className="attachment-item clearfix">
                            <a href={this.getResourceUrl(item.url, item.title)}
                               className="attachment-item-link" download={""}></a>
                            <img src={require("@share/scurd/component/fileupload/ico/ico_" + item.ext + ".png")} alt="图标"/>
                            <dl>
                                <dt title={item.title}>{item.title}</dt>
                                <dd>{item.fileSize}</dd>
                            </dl>
                            <span style={{ "display": readOnly ? "none" : "" }} className="close-btn" onClick={(e) => {
                                e.stopPropagation();
                                self.delete(item.url);
                            }}>
                                <i className="fa fa-times"></i>
                            </span>
                        </div>
                    );
                })}

                <UploaderFix
                    before={(file) => {

                        if(!fileSystemVersionInited) {
                            alert("尚未完成控件初始化，请稍后");
                            this.cleanInput();
                            return false;
                        }

                        if(!file || file.length <= 0) {
                            return true;
                        }
                        let filename = file[0].name;
                        if(filename && filename.includes(",") || filename.includes('$')){
                            alert("文件名存在特殊字符，如逗号或者$");
                            this.cleanInput();
                            return false;
                        }
                        if(!self.checkFileType(itemData, file[ 0 ].name)) {
                            alert("不支持的文件格式，只支持：" + itemData.ext.fileType);
                            this.cleanInput();
                            return false;
                        }
                        if(fileMaxSize !== -1) {
                            if(file[ 0 ].size > parseFloat(fileMaxSize) * 1024 * 1024) {
                                alert("超过文件大小限制，只允许：" + fileMaxSize);
                                this.cleanInput();
                                return false;
                            }
                        }
                        return true;
                    }}
                    request={{
                        fileName: 'file',
                        url: uploadUrl,
                        method: 'POST',
                        fields: {},
                        headers: {
                            //custom headers to send along
                        },
                        // use credentials for cross-site requests
                        withCredentials: false,
                    }}
                    ref={uploader => this.uploader = uploader}
                    onComplete={(e) => {
                        // 清空input的值
                        this.cleanInput();
                        this.onComplete(e);
                    }}
                    uploadOnSelection={true}
                >
                    {({ onFiles, progress, complete, uploadFieldPropsHandle }) => (
                        <UploadField
                            {
                                ...uploadFieldPropsHandle({
                                    onFiles: onFiles,
                                    uploadProps: {
                                        id: itemData.column,
                                        accept: itemData.ext.fileType || '*'
                                    },
                                    containerProps: {
                                        className: 'ui-fileupload',
                                    }
                                })
                            }
                        >
                            <div style={{ "display": readOnly || !showAdd ? "none" : "" }}
                                 className="attachment-add clearfix">
                                <input type="button" value="" className="attachment-btn"/>
                                <span className="fa-wrap">
                                        <i className="fa fa-plus"></i>
                                    </span>
                                {complete === true || !progress ? (<span className="text">选择附件(<label
                                        className="js-att-count">{items.length}{itemData.ext.max ? "/" + itemData.ext.max
                                    : ""}</label>)个{fileMaxSize === -1 ? "" : ("最大"
                                    + fileMaxSize)}</span>)
                                    : (<span className="text">上传进度{progress}%</span>)}

                            </div>
                        </UploadField>
                    )}
                </UploaderFix>
            </div>
        );
    }
}
export default MyFileUpload;