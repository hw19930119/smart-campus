
/**
 * function 按行切割数组
 * data 需要分割的数组
 * chunk 一行展示几个数据字段
 * index 第几组数据进行循环
 * result 需要展示的多少行数据
 */
export const changeArrs = (data,chunk)=>{
    var result = [];
    for (var i = 0, j = data.length; i < j; i += chunk) {
        result.push(data.slice(i, i + chunk));
    }
    return result
}

export const getContextPath=()=>{
    if(window.SHARE && window.SHARE.CONTEXT_PATH){
        return window.SHARE.CONTEXT_PATH.substring(0,window.SHARE.CONTEXT_PATH.length - 1);
    }
    let pathname = location.pathname;
    let re=eval("/\\//ig")
    let len = pathname.match(re).length;
    if(len == 1){
        return "";
    }else if(pathname.indexOf("/admin/") === 0){
        return "";
    }else{
        let begin = pathname.indexOf("/");
        let end = pathname.indexOf("/",begin+1);
        return pathname.substring(begin,end);
    }
}
export const formaterDmData = (arr=[])=>{
    let newArr = arr.map((item)=>{
        return {
            label:item.MC0000,
            value:item.XH0000
        }
    })
    return newArr
}
export const formaterDate = (date)=>{
    let newDate = date;
    let year = 2010,month=0,day = 0;
    if(newDate){
        year = parseInt(newDate.substring(0,4));
        let month_1 =  parseInt(newDate.substring(4,5));
        let month_2 =  parseInt(newDate.substring(5,6));
        month = month_1==0?month_2 - 1:parseInt(newDate.substring(4,6))-1;
        let day_1 = parseInt(newDate.substring(6,7));
        let day_2 = parseInt(newDate.substring(7,8));
        day = day_1==0?day_2:parseInt(newDate.substring(6,8));
        // month = parseInt(date.substring(4,5))<10?parseInt(date.substring(4,5)):parseInt(date.substring(4,4));
        // date = `${date.substring(0,4)}-${date.substring(4,6)}-${date.substring(6,8)}`
    }
    var x=new Date();
    console.log(year,month,day)
    let setFullYear = x.setFullYear(year,month,day);
    return setFullYear;
}

export const substrDate = function(date,length){
    let newDate = date || '';
    if(date){
        newDate = newDate.substr(0,length);
    }
    return newDate;
}
