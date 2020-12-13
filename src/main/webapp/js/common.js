var GetConfig = 30000;
var GetUserList = 30014;
var GetTaskList = 30032;
var GetTaskForWeb = 30037;
var GetServerLog = 50100;
var GetServerState = 50101;
var SetServerState = 50104;
var GetServerUploadingList = 50102;

var ExamPaperDetail = 31104;

var ZXDeviceList = 40020;
var ZXTickInfo = 40021;

function dateFormat(fmt, date) {
    var opt = {
        "y+": date.getFullYear().toString(),        // 年
        "M+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "m+": date.getMinutes().toString(),         // 分
        "s+": date.getSeconds().toString()          // 秒
    };
    for (var k in opt) {
        var ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        }
    }
    return fmt;
}

function dateParse(fmt, str) {
    var date = new Date();
    var ret = new RegExp("(y+)").exec(fmt);
    date.setFullYear(ret ? parseInt(str.substr(ret.index, ret[1].length)) : 0);
    var ret = new RegExp("(M+)").exec(fmt);
    date.setMonth(ret ? parseInt(str.substr(ret.index, ret[1].length)) - 1 : 0);
    var ret = new RegExp("(d+)").exec(fmt);
    date.setDate(ret ? parseInt(str.substr(ret.index, ret[1].length)) : 0);
    var ret = new RegExp("(H+)").exec(fmt);
    date.setHours(ret ? parseInt(str.substr(ret.index, ret[1].length)) : 0);
    var ret = new RegExp("(m+)").exec(fmt);
    date.setMinutes(ret ? parseInt(str.substr(ret.index, ret[1].length)) : 0);
    var ret = new RegExp("(s+)").exec(fmt);
    date.setSeconds(ret ? parseInt(str.substr(ret.index, ret[1].length)) : 0);
    return date;
}

function formatSize(val, row, index) {
    var size = parseInt(val);
    if (size < 1024) {
        return size + " 字节";
    } else if (size < 1024 * 1024) {
        return parseInt(size / 1024) + " KB";
    } else if (size < 1024 * 1024 * 1024) {
        return parseInt(size / (1024 * 1024)) + " MB";
    } else {
        return parseInt(size / (1024 * 1024 * 1024)) + " GB";
    }
}

function timeFormat(ms) {
    var second = parseInt(ms / 1000);
    var minute = parseInt(second / 60);
    second = second % 60;
    var hour = parseInt(minute / 60);
    minute = minute % 60;
    var day = parseInt(hour / 24);
    hour = hour % 24;
    if (day != 0)
        return day + "天 " + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
    else if (hour != 0)
        return hour + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
    else
        return minute + ":" + (second < 10 ? "0" + second : second);
}

function formatTime(val, row, index) {
    return timeFormat(val);
}

function formatDate(val, row, index) {
    if (val == 0)
        return "";
    return dateFormat("yyyy年MM月dd日 HH:mm:ss", new Date(val));
}

function formatUser(val, row, index) {
    if (val)
        return val.name + "(" + val.id + ")";
    else
        return val;
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

function isPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    return flag;
}

function isLandscape() {
    return (window.orientation === 90 || window.orientation === -90);
}

function pxValue(str) {
    return str.substring(0, str.length - 2);
}

function includes(array, element) {
    for (var k = 0; k < array.length; k++) {
        if (array[k] == element)
            return true;
    }
    return false;
}

function currentTimeMillis() {
    return new Date().getTime();
}

function textIsEmpty(str) {
    return str == null || str.length == 0;
}

function loadImg(src, callback) {
    var img = new Image();
    img.src = src;
    if (img.complete) {
        if (callback != null)
            callback(img);
    } else {
        img.onload = function () {
            if (callback != null)
                callback(img);
        }
    }
}

function intColor(value) {
    if (value == 0)
        return "transparent";
    var str = (value & 0xffffff).toString(16);
    while (str.length < 6)
        str = "0" + str;
    return "#" + str;
}

function getSuffix(filename) {
    var pos = filename.lastIndexOf('.');
    var suffix = "";
    if (pos >= 0)
        suffix = filename.substring(pos);
    return suffix.toLowerCase();
}

function HashMap() {
    var length = 0;
    var obj = new Object();

    this.isEmpty = function () {
        return length == 0;
    };

    this.containsKey = function (key) {
        return (key in obj);
    };

    this.containsValue = function (value) {
        for (var key in obj) {
            if (obj[key] == value) {
                return true;
            }
        }
        return false;
    };

    this.put = function (key, value) {
        if (!this.containsKey(key)) {
            length++;
        }
        obj[key] = value;
    };

    this.get = function (key) {
        return this.containsKey(key) ? obj[key] : null;
    };

    this.remove = function (key) {
        if (this.containsKey(key) && (delete obj[key])) {
            length--;
        }
    };

    this.values = function () {
        var _values = new Array();
        for (var key in obj) {
            _values.push(obj[key]);
        }
        return _values;
    };

    this.keySet = function () {
        var _keys = new Array();
        for (var key in obj) {
            _keys.push(key);
        }
        return _keys;
    };

    this.size = function () {
        return length;
    };

    this.clear = function () {
        length = 0;
        obj = new Object();
    };
}

if (typeof String.prototype.startsWith != 'function') {
    String.prototype.startsWith = function (prefix) {
        return this.slice(0, prefix.length) === prefix;
    };
}

if (typeof String.prototype.endsWith != 'function') {
    String.prototype.endsWith = function (suffix) {
        return this.indexOf(suffix, this.length - suffix.length) !== -1;
    };
}

if ($.parser) {
    $.parser.onComplete = function () {
        $('.async-load').css('display', 'block');
    };
}
