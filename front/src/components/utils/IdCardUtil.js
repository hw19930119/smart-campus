/*
 * @(#) IdCardUtil.js
 * 版权声明 厦门畅享信息技术有限公司, 版权所有 违者必究
 *
 * <br> Copyright:  Copyright (c) 2018
 * <br> Company:厦门畅享信息技术有限公司
 * <br> @author yanfeng
 * <br> 2018-07-03 15:05:41
 */
const provinces = {
    11: '北京', 12: '天津', 13: '河北', 14: '山西', 15: '内蒙古',
    21: '辽宁', 22: '吉林', 23: '黑龙江', 31: '上海', 32: '江苏',
    33: '浙江', 34: '安徽', 35: '福建', 36: '江西', 37: '山东', 41: '河南',
    42: '湖北', 43: '湖南', 44: '广东', 45: '广西', 46: '海南', 50: '重庆',
    51: '四川', 52: '贵州', 53: '云南', 54: '西藏', 61: '陕西', 62: '甘肃',
    63: '青海', 64: '宁夏', 65: '新疆', 71: '台湾', 81: '香港', 82: '澳门', 91: '国外'
};
// 校验位的检测
const checkParityBit = function (card) {
    // 15位转18位
    card = changeFifteenToEighteen(card);
    // 最后一位的x转成X
    card = card.replace('x', 'X');
    if (card.length == 18) {
        const arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
        const arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
        let cardTemp = 0;

        for (let i = 0; i < 17; i++) {
            cardTemp += card.substr(i, 1) * arrInt[i];
        }
        const valNum = arrCh[cardTemp % 11];

        if (valNum == card.substr(17, 1)) {
            return true;
        }
    }
    return false;
};
const changeFifteenToEighteen = idcard => {
    let card = idcard;

    if (card.length == 15) {
        const arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
        const arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
        let cardTemp = 0;

        card = `${card.substr(0, 6)}19${card.substr(6, card.length - 6)}`;
        for (let i = 0; i < 17; i++) {
            cardTemp += card.substr(i, 1) * arrInt[i];
        }
        card += arrCh[cardTemp % 11];
        return card;
    }
    return card;
};

// 检查号码是否符合规范，包括长度，类型
// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
// todo：此正则表达式的问题是：1.省份可能会有问题 2.日期会有问题 3.需要对校验位进行校验
const isCardNo = card => {
    if (card.length !== 18 && card.length !== 15) {
        return false;
    }
    return (/(^\d{6}(19|20)?\d{2}(0[1-9]|1[0-2])([0][1-9]|[1-2][0-9]|3[0-1])\d{3}(\d|X|x)?$)/).test(card);
};
// 取身份证前两位,校验省份
const checkProvince = card => typeof provinces[card.substr(0, 2)] !== 'undefined';

export const checkCard = card => {
    if (typeof card === 'undefined' || null == card || card === '' || !isCardNo(card) || !checkProvince(card) || !checkParityBit(card)) {
        return false;
    }
    return true;
};

