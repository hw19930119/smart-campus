import { ModalTool } from '@share/shareui';
import queryString from 'query-string';

export const sleep = time => new Promise(resolve => setTimeout(resolve, time));

export const getContextPath = () => {
    const basePath = window.SHARE.CONTEXT_PATH;

    return basePath.replace(location.origin, '');
};

// 文件地址获取
export const getPath = (src, name) => `${getContextPath()}resource${src}${name ? `?name=${name}` : ''}`;

// 数组去重
export function removerRepeat(arr) {
    const obj = {};

    return arr.reduce((item, next) => {
        if (!obj[next.key]) {
            obj[next.key] = true;
            item.push(next);
        }
        return item;
    }, []);
}

// 获取guid
export function guid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
        const r = Math.random() * 16 | 0;
        const v = c === 'x' ? r : (r & 0x3 | 0x8);

        return v.toString(16);
    });
}

export const modalTip = ({ bsStyle = 'warning', content = '接口异常，请刷新重试', ...rest }) => {
    // eslint-disable-next-line no-new
    new ModalTool({
        bsStyle,
        cancelText: null,
        content,
        ...rest
    });
};

// 过滤树形结构中的指定项
export function filterTree(data, key, name) {
    const newData = data.filter(x => x[name] !== key);

    newData.forEach(x => x.children && (x.children = filterTree(x.children, key, name)));
    return newData;
}

// 是否为JSON
export const isJsonString = str => {
    try {
        if (typeof JSON.parse(str) === 'object') {
            return true;
        }
    } catch (e) {
        //
    }
    return false;
};

export const isObject = o => {
    return Object.prototype.toString.call(o) === '[object Object]';
};

// 判断图片是否存在
export const checkImgExists = fileUrl => {
    const lowerCase = ['jpg', 'jpeg', 'png', 'bmp', 'webp', 'svg', 'tiff', 'gif'];
    const upperCase = lowerCase.map(item => item.toLocaleUpperCase());
    const imgFormat = lowerCase.concat(upperCase);
    const str = imgFormat.join('|');
    const reg = new RegExp(`\\.(${str})$`);

    return reg.test(fileUrl);
};

// 进行url解码,解析url的query部分，解析成一个对象
// 遍历对象,如果对象的值是一个json字符串，则进行解析
export const parseEncodeQuery = encodeQuery => {
    try {
        // 类型错误处理
        if (typeof encodeQuery !== 'string') {
            throw new TypeError('Data Type Error:please input a string');
        }
        const decode = window.decodeURI(encodeQuery); // 进行解码
        const parse = queryString.parse(decode); // 进行解析

        // 对对象进行遍历，判断是否有JSON字符串(一般是查询条件)，如果有，解析
        isObject(parse) && Object.keys(parse).forEach(key => {
            const current = parse[key];

            if (isJsonString(current)) {
                parse[key] = JSON.parse(parse[key]); // 解析JSON
            }
        });
        return parse;
    } catch (e) {
        const {
            message = '调用parseEncodeQuery函数错误'
        } = e;

        console.error(message);
    }
};
