//gotoPage key和对应uri(自己加斜杠)
var gotoPageMap={
    // signin:"/signPageOne.html",
    // hello:"/2hello.html",
    // signPageOne:'/signPageOne.html',
    // receipt:"/index.html#/ReceiveDetail",
};
var defeadGoToPageMap={
    // signPageOne:"/signPageTwo.html"
};

//通用方法-将所有的参数缓存到cookie里
function getParamsAndSetJsonIntoCookie(){
    //JSON.stringify({"key":"val"}) ; //序列化成字符串
    //JSON.parse(string);//json字符串反序列化成对象
    var variableJSONStr=JSON.stringify(getQueryVariableJSON()) ; //序列化成字符串
    setCookie("variableJSONStr", variableJSONStr, 30);//将数据以json字符串存入cookie
}
//通用方法-获取url问号所有参数形成JSON，例如{"key":"val"}
function getQueryVariableJSON(){
    var variableJSON={};
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0]==null
            ||pair[0]==''){
            continue;
        }
        var key=pair[0];
        var value=null;
        if(pair.length==2){
            value=pair[1];
        }
        value=decodeURIComponent(value);//这是针对浏览器地址栏的解码
        variableJSON[key]=value;
    }
    return variableJSON;
}
//通用方法-获取url问号后的一个参数
function getQueryVariable(variable)
{
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

function formatGetQueryVariable(name){
    var val=getQueryVariable(name);
    if(val==null||val==false){
        val="";
    }
    return decodeURIComponent(val)
}
//通用方法-得到cookie里的参数json对象，例如{"key":"val"}
function getParamsJsonInCookie(){
    var variableJSONStr=getCookie("variableJSONStr");
    if(variableJSONStr==null){
        return {};
    }
    var variableJSON=JSON.parse(variableJSONStr);
    return variableJSON;
}
//通用方法-得到cookie里的参数 &拼接的url参数，例如a=a&b=b
function getParamsStringInCookie(){
    var paramItemArray=[];
    var variableJSON=getParamsJsonInCookie();
    for (var key in variableJSON){
        var value=variableJSON[key];
        if(value==null){
            value="";
        }
        paramItemArray.push(key+"="+value);
    }
    return paramItemArray.join("&");
}
//通用方法-设置cookie
function setCookie(name, value, exdays) {
    clearSingleCookie(name);
    if(value==null){
        return;
    }
    var d = new Date();
    if(exdays==null){
        exdays=30;
    }
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires="+d.toUTCString();
    value=encodeURIComponent(value);
    document.cookie = name + "=" + value + ";" + expires +"; path="+getMyContextPath();
}
//通用方法-获取cookie
function getCookie(name) {
    var nameD = name + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(nameD)  == 0) {
            var value= c.substring(nameD.length, c.length);
            value=decodeURIComponent(value);
            return value;
        }
    }
    return "";
}
//通用方法-检查cookie是否存在
function checkCookie() {
    var user = getCookie("username");
    if (user != "") {
        alert("Welcome again " + user);
    } else {
        user = prompt("Please enter your name:", "");
        if (user != "" && user != null) {
            setCookie("username", user, 365);
        }
    }
}
//通用方法-清除所有cookie函数
function clearAllCookie() {
    var keys=document.cookie.match(/[^ =;]+(?=\=)/g);
    //console.log("需要删除的cookie名字："+keys);
    if (keys) {
        for (var i =  keys.length; i--;){
            clearSingleCookie(keys[i]);
        }
    }
}
//通用方法-清除单个cookie函数
function clearSingleCookie(name) {
    //1清除自身 不带path
    //2清除根 path=/
    //3清除 path=/contextPath
    var date=new Date();
    date.setTime(date.getTime()-10000);
    document.cookie=name+"=0; expires="+date.toGMTString()+";";
    document.cookie=name+"=0; expires="+date.toGMTString()+"; path=/";
    document.cookie=name+"=0; expires="+date.toGMTString()+"; path="+getMyContextPath();
}
//通用方法-获取contextPath
function getMyContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}
