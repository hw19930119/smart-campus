const maxSize = 10;
export const getFileType = function(data){
    let results = []
    if(data){
        let dataArr = data.split(',').filter(item=>{
            return item!=''
        }).map(item=>{
            let itemData = item.toUpperCase().replace(".","").replace(",","");
            return itemData;
        })
        results = [...new Set(dataArr)]
    }
    return results;
}
export const  formaterFileTyle = function(files,fileType,thisSize){
    let size = files.fileSize;
    let original = files.original;
    let nameArr = files.type.split(".");
    let type = nameArr[nameArr.length - 1].toUpperCase();
    if(original.includes(",") || original.includes("$")){
        error("文件名存在特殊符号，如逗号或者$");
        return false;
    }
    if(!fileType.includes(type)){
        error("不支持该文件类型");
        return false;
    }
    let initSize = maxSize;
    if(thisSize){
        initSize = parseFloat(thisSize).toFixed(0);
    }
    if(size > (initSize * 1024 * 1024)){
        error(`上传文件不能大于${thisSize?thisSize:maxSize}M`);
        return false;
    }
    return true;
}