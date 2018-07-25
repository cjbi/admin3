/**
 * 公共js, 1.0.0
 * cjbi, 2017-12-04
 */
(function () {
    'use strict';
    //定义一些默认参数
    var _options = {
        _dateFormat: {
            ZH: {
                dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
                shortDayNames: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
                monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                shortMonthNames: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
            },
            US: {
                dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
                shortDayNames: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
                monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
                shortMonthNames: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
            }
        }

    };


    //定义一些api
    var _date_format_api = {

        /**
         * 格式化Unix时间戳
         * @param t
         * @param fmt
         * @returns {*}
         */
        formatTimestamp: function (time, fmt) {
            var date = new Date();
            date.setTime(time);
            return this.format(date, fmt);
        },
        /**
         * 格式化时间
         * @param date
         * @param fmt
         * @returns {*}
         */
        format: function (date, fmt) {
            var o = {
                'M+': date.getMonth() + 1, //月份
                'd+': date.getDate(), //日
                'h+': date.getHours(), //小时
                'm+': date.getMinutes(), //分
                's+': date.getSeconds(), //秒
                'q+': Math.floor((date.getMonth() + 3) / 3), //季度
                'S': date.getMilliseconds() //毫秒
            };
            if (!this.isNotEmpty(fmt)) {
                fmt = 'yyyy-MM-dd hh:mm:ss';
            }
            if (/(y+)/.test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
            }
            for (var k in o) {
                if (new RegExp('(' + k + ')').test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)));
                }
            }
            return fmt;
        },

        formatToDate: function (dateStr) {
            if (this.isNotEmpty(dateStr)) {
                return new Date(Date.parse(dateStr.replace(/-/g, "/")));
            }
            return '';
        },

        /**
         * 得到一天的开始 date类型
         * @param date
         */
        getDateStart: function (date) {
            var fmt = 'yyyy-MM-dd';
            var dateStartStr = this.getDateStartStr(date, fmt);
            var startTime = new Date(Date.parse(dateStartStr));
            return startTime;
        },

        /**
         * 得到一天的开始 str 类型
         * @param date
         */
        getDateStartStr: function (date, fmt) {
            if (typeof fmt == 'undefined') {
                fmt = 'yyyy-MM-dd';
            }
            var dateStr = this.format(date, fmt);
            dateStr += ' 00:00:00';
            return dateStr;
        },

        /**
         * 得到一天的结束 date类型
         * @param date
         */
        getDateEnd: function (date) {
            var fmt = 'yyyy-MM-dd';
            var dateEndStr = this.getDateEndStr(date, fmt);
            var endTime = new Date(Date.parse(dateEndStr));
            return endTime;
        },

        /**
         * 得到一天的结束 str 类型
         * @param date
         */
        getDateEndStr: function (date, fmt) {
            if (typeof fmt == 'undefined') {
                fmt = 'yyyy-MM-dd';
            }
            var endStr = this.format(date, fmt);
            endStr += ' 23:59:59';
            return endStr;
        },

        /**
         * 两个时间比较大小
         * @param d1
         * @param d2
         */
        compareDate: function (d1, d2) {
            if (d1 && d2) {
                if (d1.getTime() > d2.getTime()) {
                    return 1;
                } else if (d1.getTime() == d2.getTime()) {
                    return 0;
                } else if (d1.getTime() < d2.getTime()) {
                    return -1;
                }
            }
        },

        /**
         * 得到星期几, 支持中英
         * @param date
         * @param type
         * @returns {string}
         */
        getWeek: function (date, type) {
            if (date) {
                if (!this.isNotEmpty(type)) {
                    type = 0;
                }
                var index = date.getDay();
                var dateStr = '';
                switch (type) {
                    case this.WEEKTYPE.ZH_DAYNAME:
                        dateStr = _options._dateFormat.ZH.dayNames[index];
                        break;
                    case this.WEEKTYPE.ZH_SDAYNAME:
                        dateStr = _options._dateFormat.ZH.shortDayNames[index];
                        break;
                    case this.WEEKTYPE.US_DAYNAME:
                        dateStr = _options._dateFormat.US.dayNames[index];
                        break;
                    case this.WEEKTYPE.US_SDAYNAME:
                        dateStr = _options._dateFormat.US.shortDayNames[index];
                        break;
                }
                return dateStr;
            }
        },

        /**
         * 是否为闰年
         * @param date
         * @returns {boolean}
         */
        isLeapYear: function (date) {
            if (date instanceof Date) {
                return (0 == date.getYear() % 4 && ((date.getYear() % 100 != 0) || (date.getYear() % 400 == 0)));
            }
            console.warn('argument format is wrong');
            return false;
        },

        /**
         * 字符串是否为正确的时间格式，支持 - /
         * @param dateStr
         * @returns {boolean}
         */
        isValidDate: function (dateStr) {
            if (this.isNotEmpty(dateStr)) {
                var r = dateStr.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
                if (r == null) {
                    return false;
                }
                var d = new Date(r[1], r[3] - 1, r[4]);
                var num = (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]);
                return (num != 0);
            }
        },

        /**
         * 增加天数
         * @param date
         * @param dayNum
         */
        addDay: function (date, dayNum) {
            if (this.isNotEmpty(date) && this.isNotEmpty(dayNum) && date instanceof Date && typeof dayNum == 'number') {
                date.setDate(date.getDate() + dayNum);
            } else {
                console.warn('date or dayNum format wrong');
            }
            return date;
        },

        addDayStr: function (dateStr, dayNum) {
            var date = '';
            if (this.isNotEmpty(dateStr) && this.isNotEmpty(dayNum) && typeof dayNum == 'number') {
                date = this.formatToDate(dateStr);
                date.setDate(date.getDate() + dayNum);
            } else {
                console.warn('dateStr or dayNum format wrong');
            }
            return date;
        },

        /**
         * 增加月份
         * @param date
         * @param dayNum
         */
        addMonth: function (date, monthNum) {
            if (this.isNotEmpty(date) && this.isNotEmpty(monthNum) && date instanceof Date && typeof monthNum == 'number') {
                date.setMonth(date.getMonth() + monthNum);
            } else {
                console.warn('date or monthNum format wrong');
            }
            return date;
        },

        addMonthStr: function (dateStr, monthNum) {
            var date = '';
            if (this.isNotEmpty(dateStr) && this.isNotEmpty(monthNum) && typeof monthNum == 'number') {
                date = this.formatToDate(dateStr);
                date.setMonth(date.getMonth() + monthNum);
            } else {
                console.warn('date or monthNum format wrong');
            }
            return date;
        },

        /**
         * 增加年份
         * @param date
         * @param dayNum
         */
        addYear: function (date, yearNum) {
            if (this.isNotEmpty(date) && this.isNotEmpty(yearNum) && date instanceof Date && typeof yearNum == 'number') {
                date.setYear(date.getFullYear() + yearNum);
            } else {
                console.warn('date or yearNum format wrong');
            }
            return date;
        },

        addYearStr: function (dateStr, yearNum) {
            var date = '';
            if (this.isNotEmpty(dateStr) && this.isNotEmpty(yearNum) && typeof yearNum == 'number') {
                date = this.formatToDate(dateStr);
                date.setYear(date.getFullYear() + yearNum);
            } else {
                console.warn('date or yearNum format wrong');
            }
            return date;
        },


        /**
         * 是否为空
         * @param str
         * @returns {boolean}
         */
        isNotEmpty: function (str) {
            if (str != '' && str != null && typeof str != 'undefined') {
                return true;
            }
            console.warn('argument format is wrong');
            return false;
        },

        //定义内部常量
        WEEKTYPE: {
            ZH_DAYNAME: 0,
            ZH_SDAYNAME: 1,
            US_DAYNAME: 2,
            US_SDAYNAME: 3,
        }


    };

    var _file_api = {
        /**
         * dataurl转换成Blob的方法
         * @param dataurl
         * @returns {*}
         */
        dataURLtoBlob: function (dataurl) {
            var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
                bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
            while (n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new Blob([u8arr], {type: mime});
        }
    };

    var _cookie_api = {
        /**
         *
         * @desc 根据name读取cookie
         * @param  {String} name
         * @return {String}
         */
        getCookie: function (name) {
            var arr = document.cookie.replace(/\s/g, "").split(';');
            for (var i = 0; i < arr.length; i++) {
                var tempArr = arr[i].split('=');
                if (tempArr[0] == name) {
                    return decodeURIComponent(tempArr[1]);
                }
            }
            return '';
        },
        /**
         *
         * @desc  设置Cookie
         * @param {String} name
         * @param {String} value
         * @param {Number} days
         */
        setCookie: function (name, value, days) {
            var date = new Date();
            date.setDate(date.getDate() + days);
            document.cookie = name + '=' + value + ';expires=' + date;
        },
        /**
         *
         * @desc 根据name删除cookie
         * @param  {String} name
         */
        removeCookie: function (name) {
            // 设置已过期，系统会立刻删除cookie
            setCookie(name, '1', -1);
        }
    };

    var _device_api = {
        /**
         *
         * @desc 获取浏览器类型和版本
         * @return {String}
         */
        getExplore: function () {
            var sys = {},
                ua = navigator.userAgent.toLowerCase(),
                s;
            (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? sys.ie = s[1] :
                (s = ua.match(/msie ([\d\.]+)/)) ? sys.ie = s[1] :
                    (s = ua.match(/edge\/([\d\.]+)/)) ? sys.edge = s[1] :
                        (s = ua.match(/firefox\/([\d\.]+)/)) ? sys.firefox = s[1] :
                            (s = ua.match(/(?:opera|opr).([\d\.]+)/)) ? sys.opera = s[1] :
                                (s = ua.match(/chrome\/([\d\.]+)/)) ? sys.chrome = s[1] :
                                    (s = ua.match(/version\/([\d\.]+).*safari/)) ? sys.safari = s[1] : 0;
            // 根据关系进行判断
            if (sys.ie) return ('IE: ' + sys.ie)
            if (sys.edge) return ('EDGE: ' + sys.edge)
            if (sys.firefox) return ('Firefox: ' + sys.firefox)
            if (sys.chrome) return ('Chrome: ' + sys.chrome)
            if (sys.opera) return ('Opera: ' + sys.opera)
            if (sys.safari) return ('Safari: ' + sys.safari)
            return 'Unkonwn'
        },
        /**
         *
         * @desc 获取操作系统类型
         * @return {String}
         */
        getOS: function () {
            var userAgent = 'navigator' in window && 'userAgent' in navigator && navigator.userAgent.toLowerCase() || '';
            var vendor = 'navigator' in window && 'vendor' in navigator && navigator.vendor.toLowerCase() || '';
            var appVersion = 'navigator' in window && 'appVersion' in navigator && navigator.appVersion.toLowerCase() || '';

            if (/mac/i.test(appVersion)) return 'MacOSX'
            if (/win/i.test(appVersion)) return 'windows'
            if (/linux/i.test(appVersion)) return 'linux'
            if (/iphone/i.test(userAgent) || /ipad/i.test(userAgent) || /ipod/i.test(userAgent)) 'ios'
            if (/android/i.test(userAgent)) return 'android'
            if (/win/i.test(appVersion) && /phone/i.test(userAgent)) return 'windowsPhone'
        }
    };

    var _dom_api = {
        /**
         *
         * @desc 获取滚动条距顶部的距离
         */
        getScrollTop: function () {
            return (document.documentElement && document.documentElement.scrollTop) || document.body.scrollTop;
        },
        /**
         *
         * @desc 设置滚动条距顶部的距离
         */
        setScrollTop: function (value) {
            window.scrollTo(0, value);
            return value;
        },
        /**
         *
         * @desc  获取一个元素的距离文档(document)的位置，类似jQ中的offset()
         * @param {HTMLElement} ele
         * @returns { {left: number, top: number} }
         */
        offset: function (ele) {
            var pos = {
                left: 0,
                top: 0
            };
            while (ele) {
                pos.left += ele.offsetLeft;
                pos.top += ele.offsetTop;
                ele = ele.offsetParent;
            }
            ;
            return pos;
        },
        /**
         *
         * @desc  在${duration}时间内，滚动条平滑滚动到${to}指定位置
         * @param {Number} to
         * @param {Number} duration
         */
        scrollTo: function (to, duration) {
            if (duration < 0) {
                setScrollTop(to);
                return
            }
            var diff = to - getScrollTop();
            if (diff === 0) return
            var step = diff / duration * 10;
            requestAnimationFrame(
                function () {
                    if (Math.abs(step) > Math.abs(diff)) {
                        setScrollTop(getScrollTop() + diff);
                        return;
                    }
                    setScrollTop(getScrollTop() + step);
                    if (diff > 0 && getScrollTop() >= to || diff < 0 && getScrollTop() <= to) {
                        return;
                    }
                    scrollTo(to, duration - 16);
                });
        }

    };

    var _array_api = {
        /**
         *
         * @desc 判断两个数组是否相等
         * @param {Array} arr1
         * @param {Array} arr2
         * @return {Boolean}
         */
        arrayEqual: function (arr1, arr2) {
            if (arr1 === arr2) return true;
            if (arr1.length != arr2.length) return false;
            for (var i = 0; i < arr1.length; ++i) {
                if (arr1[i] !== arr2[i]) return false;
            }
            return true;
        }
    };

    var _class_api = {
        /**
         *
         * @desc   为元素添加class
         * @param  {HTMLElement} ele
         * @param  {String} cls
         */
        addClass: function (ele, cls) {
            if (!hasClass(ele, cls)) {
                ele.className += ' ' + cls;
            }
        },
        /**
         *
         * @desc 判断元素是否有某个class
         * @param {HTMLElement} ele
         * @param {String} cls
         * @return {Boolean}
         */
        hasClass: function (ele, cls) {
            return (new RegExp('(\\s|^)' + cls + '(\\s|$)')).test(ele.className);
        },
        /**
         *
         * @desc 为元素移除class
         * @param {HTMLElement} ele
         * @param {String} cls
         */
        removeClass: function (ele, cls) {
            if (hasClass(ele, cls)) {
                var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
                ele.className = ele.className.replace(reg, ' ');
            }
        }
    };

    var _object_api = {
        /**
         * @desc 深拷贝，支持常见类型
         * @param {Any} values
         */
        deepClone: function (values) {
            var copy;

            // Handle the 3 simple types, and null or undefined
            if (null == values || "object" != typeof values) return values;

            // Handle Date
            if (values instanceof Date) {
                copy = new Date();
                copy.setTime(values.getTime());
                return copy;
            }

            // Handle Array
            if (values instanceof Array) {
                copy = [];
                for (var i = 0, len = values.length; i < len; i++) {
                    copy[i] = deepClone(values[i]);
                }
                return copy;
            }

            // Handle Object
            if (values instanceof Object) {
                copy = {};
                for (var attr in values) {
                    if (values.hasOwnProperty(attr)) copy[attr] = deepClone(values[attr]);
                }
                return copy;
            }

            throw new Error("Unable to copy values! Its type isn't supported.");
        },
        /**
         *
         * @desc   判断`obj`是否为空
         * @param  {Object} obj
         * @return {Boolean}
         */
        isEmptyObject: function (obj) {
            if (!obj || typeof obj !== 'object' || Array.isArray(obj))
                return false
            return !Object.keys(obj).length
        }
    }

    var _random_api = {
        /**
         *
         * @desc 随机生成颜色
         * @return {String}
         */
        randomColor: function () {
            return '#' + ('00000' + (Math.random() * 0x1000000 << 0).toString(16)).slice(-6);
        },
        /**
         *
         * @desc 生成指定范围随机数
         * @param  {Number} min
         * @param  {Number} max
         * @return {Number}
         */
        randomNum: function (min, max) {
            return Math.floor(min + Math.random() * (max - min));
        }
    };

    var _regexp_api = {
        /**
         *
         * @desc   判断是否为邮箱地址
         * @param  {String}  str
         * @return {Boolean}
         */
        isEmail: function (str) {
            return /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test(str);
        },
        /**
         *
         * @desc  判断是否为身份证号
         * @param  {String|Number} str
         * @return {Boolean}
         */
        isIdCard: function (str) {
            return /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/.test(str)
        },
        /**
         *
         * @desc   判断是否为手机号
         * @param  {String|Number} str
         * @return {Boolean}
         */
        isPhoneNum: function (str) {
            return /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test(str)
        },
        /**
         *
         * @desc   判断是否为URL地址
         * @param  {String} str
         * @return {Boolean}
         */
        isUrl: function (str) {
            return /[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/i.test(str);
        }
    };

    var _string_api = {

        /**
         * 驼峰转换为连字符\连字符转换为驼峰
         * <pre>
         *     fooStyleCss -->  foo-style-css
         *     formatHump('fooStyleCss','-');
         * </pre>
         * @param str
         * @param split
         */
        formatHump: function (str, split) {
            if (str.indexOf(split) >= 0) {
                var re = new RegExp(split + "(\\w)", "g");
                return str.replace(re, function ($0, $1) {
                    return $1.toUpperCase();
                });

            }
            return str.replace(/([A-Z])/g, '' + split + '$1').toLowerCase();
        },
        /**
         *
         * @desc   现金额转大写
         * @param  {Number} n
         * @return {String}
         */
        digitUppercase: function (n) {
            var fraction = ['角', '分'];
            var digit = [
                '零', '壹', '贰', '叁', '肆',
                '伍', '陆', '柒', '捌', '玖'
            ];
            var unit = [
                ['元', '万', '亿'],
                ['', '拾', '佰', '仟']
            ];
            var head = n < 0 ? '欠' : '';
            n = Math.abs(n);
            var s = '';
            for (var i = 0; i < fraction.length; i++) {
                s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
            }
            s = s || '整';
            n = Math.floor(n);
            for (var i = 0; i < unit[0].length && n > 0; i++) {
                var p = '';
                for (var j = 0; j < unit[1].length && n > 0; j++) {
                    p = digit[n % 10] + unit[1][j] + p;
                    n = Math.floor(n / 10);
                }
                s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
            }
            return head + s.replace(/(零.)*零元/, '元')
                    .replace(/(零.)+/g, '零')
                    .replace(/^整$/, '零元整');
        },
        /**
         * 格式化货币
         * @param num
         * @param length 保留位数
         * @returns {*}
         * @constructor
         */
        rmbformat: function (num, length) {
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
                var num = num.toString().replace(/\$|\,/g, '');
                if (isNaN(num))
                    num = "0";
                var sign = (num == (num = Math.abs(num)));
                num = Math.floor(num * lengthNum + 0.50000000001);
                var cents = ((Array(length).join('0') + num % lengthNum).slice(-length));
                num = Math.floor(num / lengthNum).toString();
                for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
                    num = num.substring(0, num.length - (4 * i + 3)) + ',' +
                        num.substring(num.length - (4 * i + 3));
                return (((sign) ? '' : '-') + num + '.' + cents);
            } else {
                return num;
            }
        }
    };

    var _support_api = {
        /**
         *
         * @desc 判断浏览器是否支持webP格式图片
         * @return {Boolean}
         */
        isSupportWebP: function () {
            return !![].map && document.createElement('canvas').toDataURL('image/webp').indexOf('data:image/webp') == 0;
        }
    };

    var _time_api = {
        /**
         *
         * @desc   格式化现在距${endTime}的剩余时间
         * @param  {Date} endTime
         * @return {String}
         */
        formatRemainTime: function (endTime) {
            var startDate = new Date(); //开始时间
            var endDate = new Date(endTime); //结束时间
            var t = endDate.getTime() - startDate.getTime(); //时间差
            var d = 0,
                h = 0,
                m = 0,
                s = 0;
            if (t >= 0) {
                d = Math.floor(t / 1000 / 3600 / 24);
                h = Math.floor(t / 1000 / 60 / 60 % 24);
                m = Math.floor(t / 1000 / 60 % 60);
                s = Math.floor(t / 1000 % 60);
            }
            return d + "天 " + h + "小时 " + m + "分钟 " + s + "秒";
        },
        /**
         * @desc   格式化${startTime}距现在的已过时间
         * @param  {Date} startTime
         * @return {String}
         */
        formatPassTime: function (startTime) {
            var currentTime = Date.parse(new Date()),
                time = currentTime - startTime,
                day = parseInt(time / (1000 * 60 * 60 * 24)),
                hour = parseInt(time / (1000 * 60 * 60)),
                min = parseInt(time / (1000 * 60)),
                month = parseInt(day / 30),
                year = parseInt(month / 12);
            if (year) return year + "年前"
            if (month) return month + "个月前"
            if (day) return day + "天前"
            if (hour) return hour + "小时前"
            if (min) return min + "分钟前"
            else return '刚刚'
        }
    };

    var _url_api = {
        /**
         *
         * @desc   url参数转对象
         * @param  {String} url  default: window.location.href
         * @return {Object}
         */
        parseQueryString: function (url) {
            url = url == null ? window.location.href : url
            var search = url.substring(url.lastIndexOf('?') + 1)
            if (!search) {
                return {}
            }
            return JSON.parse('{"' + decodeURIComponent(search).replace(/"/g, '\\"').replace(/&/g, '","').replace(/=/g, '":"') + '"}')
        },
        /**
         *
         * @desc   对象序列化
         * @param  {Object} obj
         * @return {String}
         */
        stringfyQueryString: function (obj) {
            if (!obj) return '';
            var pairs = [];

            for (var key in obj) {
                var value = obj[key];

                if (value instanceof Array) {
                    for (var i = 0; i < value.length; ++i) {
                        pairs.push(encodeURIComponent(key + '[' + i + ']') + '=' + encodeURIComponent(value[i]));
                    }
                    continue;
                }

                pairs.push(encodeURIComponent(key) + '=' + encodeURIComponent(obj[key]));
            }
            return pairs.join('&');
        }
    };

    var _function_api = {
        /**
         * @desc   函数节流。
         * 适用于限制`resize`和`scroll`等函数的调用频率
         *
         * @param  {Number}    delay          0 或者更大的毫秒数。 对于事件回调，大约100或250毫秒（或更高）的延迟是最有用的。
         * @param  {Boolean}   noTrailing     可选，默认为false。
         *                                    如果noTrailing为true，当节流函数被调用，每过`delay`毫秒`callback`也将执行一次。
         *                                    如果noTrailing为false或者未传入，`callback`将在最后一次调用节流函数后再执行一次.
         *                                    （延迟`delay`毫秒之后，节流函数没有被调用,内部计数器会复位）
         * @param  {Function}  callback       延迟毫秒后执行的函数。`this`上下文和所有参数都是按原样传递的，
         *                                    执行去节流功能时，调用`callback`。
         * @param  {Boolean}   debounceMode   如果`debounceMode`为true，`clear`在`delay`ms后执行。
         *                                    如果debounceMode是false，`callback`在`delay` ms之后执行。
         *
         * @return {Function}  新的节流函数
         */
        throttle: function (delay, noTrailing, callback, debounceMode) {

            // After wrapper has stopped being called, this timeout ensures that
            // `callback` is executed at the proper times in `throttle` and `end`
            // debounce modes.
            var timeoutID;

            // Keep track of the last time `callback` was executed.
            var lastExec = 0;

            // `noTrailing` defaults to falsy.
            if (typeof noTrailing !== 'boolean') {
                debounceMode = callback;
                callback = noTrailing;
                noTrailing = undefined;
            }

            // The `wrapper` function encapsulates all of the throttling / debouncing
            // functionality and when executed will limit the rate at which `callback`
            // is executed.
            function wrapper() {

                var self = this;
                var elapsed = Number(new Date()) - lastExec;
                var args = arguments;

                // Execute `callback` and update the `lastExec` timestamp.
                function exec() {
                    lastExec = Number(new Date());
                    callback.apply(self, args);
                }

                // If `debounceMode` is true (at begin) this is used to clear the flag
                // to allow future `callback` executions.
                function clear() {
                    timeoutID = undefined;
                }

                if (debounceMode && !timeoutID) {
                    // Since `wrapper` is being called for the first time and
                    // `debounceMode` is true (at begin), execute `callback`.
                    exec();
                }

                // Clear any existing timeout.
                if (timeoutID) {
                    clearTimeout(timeoutID);
                }

                if (debounceMode === undefined && elapsed > delay) {
                    // In throttle mode, if `delay` time has been exceeded, execute
                    // `callback`.
                    exec();

                } else if (noTrailing !== true) {
                    // In trailing throttle mode, since `delay` time has not been
                    // exceeded, schedule `callback` to execute `delay` ms after most
                    // recent execution.
                    //
                    // If `debounceMode` is true (at begin), schedule `clear` to execute
                    // after `delay` ms.
                    //
                    // If `debounceMode` is false (at end), schedule `callback` to
                    // execute after `delay` ms.
                    timeoutID = setTimeout(debounceMode ? clear : exec, debounceMode === undefined ? delay - elapsed : delay);
                }

            }

            // Return the wrapper function.
            return wrapper;
        },
        /**
         * @desc 函数防抖
         * 与throttle不同的是，debounce保证一个函数在多少毫秒内不再被触发，只会执行一次，
         * 要么在第一次调用return的防抖函数时执行，要么在延迟指定毫秒后调用。
         * @example 适用场景：如在线编辑的自动存储防抖。
         * @param  {Number}   delay         0或者更大的毫秒数。 对于事件回调，大约100或250毫秒（或更高）的延迟是最有用的。
         * @param  {Boolean}  atBegin       可选，默认为false。
         *                                  如果`atBegin`为false或未传入，回调函数则在第一次调用return的防抖函数后延迟指定毫秒调用。
         如果`atBegin`为true，回调函数则在第一次调用return的防抖函数时直接执行
         * @param  {Function} callback      延迟毫秒后执行的函数。`this`上下文和所有参数都是按原样传递的，
         *                                  执行去抖动功能时，，调用`callback`。
         *
         * @return {Function} 新的防抖函数。
         */
        debounce: function (delay, atBegin, callback) {
            return callback === undefined ? throttle(delay, atBegin, false) : throttle(delay, callback, atBegin !== false);
        }
    };

    var _ajax_api =  {
        get: function (url, fn) {
            XR.open("GET", url, true);
            XR.onreadystatechange = function () {
                if (XR.readyState == 4) {
                    try {
                        fn(eval("(" + XR.responseText + ")"));
                    } catch (e) {
                        fn(null);
                    }
                }
            };
            XR.send(null)
        },
        post: function (url, fn) {
            XR.open("POST", url, true);
            XR.onreadystatechange = function () {
                if (XR.readyState == 4) {
                    try {
                        fn(eval("(" + XR.responseText + ")"));
                    } catch (e) {
                        fn(null);
                    }
                }
            };
            XR.send(null)
        },
        put: function (url, fn) {
            XR.open("PUT", url, true);
            XR.onreadystatechange = function () {
                if (XR.readyState == 4) {
                    try {
                        fn(eval("(" + XR.responseText + ")"));
                    } catch (e) {
                        fn(null);
                    }
                }
            };
            XR.send(null)
        },
        delete: function (url, fn) {
            XR.open("DELETE", url, true);
            XR.onreadystatechange = function () {
                if (XR.readyState == 4) {
                    try {
                        fn(eval("(" + XR.responseText + ")"));
                    } catch (e) {
                        fn(null);
                    }
                }
            };
            XR.send(null)
        }
    };
    var XR = false;
    try {
        XR = new XMLHttpRequest()
    } catch (trymicrosoft) {
        try {
            XR = new ActiveXObject("Msxml2.XMLHTTP")
        } catch (othermicrosoft) {
            try {
                XR = new ActiveXObject("Microsoft.XMLHTTP")
            } catch (failed) {
                XR = false
            }
        }
    }
    if (!XR) {
        alert("Error Initializing XMLHttpRequest!")
    }

    //这里确定了插件的名称
    window.Utils = {};

    // 暴露方法供外部调用  
    //日期格式化
    Utils.dateFormat = _date_format_api;
    //文件操作
    Utils.file = _file_api;
    //cookie
    Utils.cookie = _cookie_api;
    //device
    Utils.device = _device_api;
    //dom
    Utils.dom = _dom_api;
    //dom节点的class操作
    Utils.class = _class_api;
    //数组操作
    Utils.array = _array_api;
    //对象操作
    Utils.class = _object_api;
    //正则表达式
    Utils.regexp = _regexp_api;
    //字符串处理
    Utils.string = _string_api;
    //浏览器兼容性
    Utils.support = _support_api;
    //时间处理
    Utils.time = _time_api;
    //url处理
    Utils.url = _url_api;
    //函数处理
    Utils.function = _function_api;
    //异步请求
    Utils.ajax = _ajax_api;

})();