/**
 * 时间戳处理
 * @param time
 * @returns {string}
 */
function dateFullFormat(time) {
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
};

/**
 * 时间戳处理
 * @param time
 * @returns {*}
 */
function dateFormat(time) {
    if (time != null && time != "") {
        var datetime = new Date();
        datetime.setTime(time);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        return year + "-" + month + "-" + date;
    } else {
        return "";
    }
};

/**
 * 时间戳处理
 * @param time
 * @returns {string}
 */
function dateFormat2(time) {
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year + "" + month + "" + date;
};

/**
 * 时间戳处理
 * @param time
 * @returns {string}
 */
function delTimeFormat(time) {
    var mse = 1000;//1秒
    var mi = mse * 60;//1分钟
    var hor = mi * 60;//1小时
    var day = hor * 24;//1天
//	console.info(mse+"=="+mi+"=="+hor+"=="+day);
    if (time < mse) {
        return time + "毫秒";
    } else if (time >= mse && time < mi) {
        return Math.floor(time / mse) + "秒";
    } else if (mi <= time && time < hor) {
        return Math.floor(time / mi) + "分" + Math.floor((time % mi) / mse) + "秒";
    } else if (hor <= time && time < day) {
        return Math.floor(time / hor) + "小时" + Math.floor((time % hor) / mi) + "分" + Math.floor(((time % hor) % mi) / mse) + "秒";
    }
};

/**
 * 日期加减法
 * @param date
 * @param days
 * @returns {string}
 */
function addDate(date, days) {
    var d = new Date(date);
    d.setDate(d.getDate() + days);
    var m = d.getMonth() + 1;
    var dd = d.getDate();
    return d.getFullYear() + '-' + (m + 1 > 10 ? m : "0" + m) + '-' + (dd + 1 > 10 ? dd : "0" + dd);
};

/**
 * 格式化货币
 * @param num
 * @param length 保留位数
 * @returns {*}
 * @constructor
 */
function RMBformat(num, length) {
    if (num != null && num != "") {
        var lengthNum = 1;
        if (length != null && length != "") {
            for (var i = 1; i <= length; i++) {
                lengthNum *= 10;
            }
        } else {
            length = 2;
            lengthNum *= 100;
        }
        num = num.toString().replace(/\$|\,/g, '');
        if (isNaN(num))
            num = "0";
        sign = (num == (num = Math.abs(num)));
        num = Math.floor(num * lengthNum + 0.50000000001);
        cents = ((Array(length).join('0') + num % lengthNum).slice(-length));
        num = Math.floor(num / lengthNum).toString();
        for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
            num = num.substring(0, num.length - (4 * i + 3)) + ',' +
                num.substring(num.length - (4 * i + 3));
        return (((sign) ? '' : '-') + num + '.' + cents);
    } else {
        return num;
    }
};

/**
 * 人民币小写转换大写
 * @param n
 * @returns {*}
 * @constructor
 */
function DX(n) {
    if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
        return "数据非法";
    var unit = "千百拾亿千百拾万千百拾元角分", str = "";
    n += "00";
    var p = n.indexOf('.');
    if (p >= 0)
        n = n.substring(0, p) + n.substr(p + 1, 2);
    unit = unit.substr(unit.length - n.length);
    for (var i = 0; i < n.length; i++)
        str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
    return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
}

/**
 * input输入控制，只允许输入数字和小数点
 * @param obj
 * @param length
 */
function clearNoNum(obj, length) {
    //先把非数字的都替换掉，除了数字和.
    obj.value = obj.value.replace(/[^\d.]/g, "");
    //必须保证第一个为数字而不是.
    obj.value = obj.value.replace(/^\./g, "");
    //保证只有出现一个.而没有多个.
    obj.value = obj.value.replace(/\.{2,}/g, ".");
    //保证.只出现一次，而不能出现两次以上
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    obj.value = RMBformat(obj.value, length);
};

/**
 * 只允许输入数字和小数点
 * @param obj
 * @param length
 */
function clearNum(obj, length) {
    //先把非数字的都替换掉，除了数字和.
    obj.value = obj.value.replace(/[^\d.]/g, "");
    //必须保证第一个为数字而不是.
    obj.value = obj.value.replace(/^\./g, "");
    //保证只有出现一个.而没有多个.
    obj.value = obj.value.replace(/\.{2,}/g, ".");
    //保证.只出现一次，而不能出现两次以上
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    // obj.value = RMBformat(obj.value,length);
};

/**
 * ataurl转换成Blob的方法
 * @param dataurl
 * @returns {*}
 */
function dataURLtoBlob(dataurl) {
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type: mime});
}