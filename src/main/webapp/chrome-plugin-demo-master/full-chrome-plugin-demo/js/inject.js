// alert('inject');

//------------------------------------------------------- nnd_base_lib_base64 ----------------------------------------------------------

function MyNnd_Base64_Class() {
    this.base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    this.base64DecodeChars = new Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45,
        46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);

    this.base64encode = function (str) {
        var out, i, len;
        var c1, c2, c3;
        len = str.length;
        i = 0;
        out = "";
        while (i < len) {
            c1 = str.charCodeAt(i++) & 0xff;
            if (i == len) {
                out += this.base64EncodeChars.charAt(c1 >> 2);
                out += this.base64EncodeChars.charAt((c1 & 0x3) << 4);
                out += "==";
                break;
            }
            c2 = str.charCodeAt(i++);
            if (i == len) {
                out += this.base64EncodeChars.charAt(c1 >> 2);
                out += this.base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
                out += this.base64EncodeChars.charAt((c2 & 0xF) << 2);
                out += "=";
                break;
            }
            c3 = str.charCodeAt(i++);
            out += this.base64EncodeChars.charAt(c1 >> 2);
            out += this.base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
            out += this.base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
            out += this.base64EncodeChars.charAt(c3 & 0x3F);
        }
        return out;
    };

    this.base64decode = function (str) {
        var c1, c2, c3, c4;
        var i, len, out;
        len = str.length;
        i = 0;
        out = "";
        while (i < len) {
            do {
                c1 = this.base64DecodeChars[str.charCodeAt(i++) & 0xff];
            } while (i < len && c1 == -1);
            if (c1 == -1)
                break;
            do {
                c2 = this.base64DecodeChars[str.charCodeAt(i++) & 0xff];
            } while (i < len && c2 == -1);
            if (c2 == -1)
                break;
            out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
            do {
                c3 = str.charCodeAt(i++) & 0xff;
                if (c3 == 61)
                    return out;
                c3 = this.base64DecodeChars[c3];
            } while (i < len && c3 == -1);
            if (c3 == -1)
                break;
            out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
            do {
                c4 = str.charCodeAt(i++) & 0xff;
                if (c4 == 61)
                    return out;
                c4 = this.base64DecodeChars[c4];
            } while (i < len && c4 == -1);
            if (c4 == -1)
                break;
            out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
        }
        return out;
    };

    this.utf16to8 = function (str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            }
        }
        return out;
    };

    this.utf8to16 = function (str) {
        var out, i, len, c;
        var char2, char3;
        out = "";
        len = str.length;
        i = 0;
        while (i < len) {
            c = str.charCodeAt(i++);
            switch (c >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    out += str.charAt(i - 1);
                    break;
                case 12:
                case 13:
                    char2 = str.charCodeAt(i++);
                    out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
                    break;
                case 14:
                    char2 = str.charCodeAt(i++);
                    char3 = str.charCodeAt(i++);
                    out += String.fromCharCode(((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
                    break;
            }
        }
        return out;
    };

    this.encode = function (str) {
        return this.base64encode(this.utf16to8(str));
    };

    this.decode = function (str) {
        return this.utf8to16(this.base64decode(str));
    };
}

var MyNnd_Base64 = new MyNnd_Base64_Class();


//------------------------------------------------------- nnd_base_lib_native ----------------------------------------------------------

function MyNnd_CanJs() {
    try {
        var str = nnd_native.getNative();
        if (str == "nnd_native") {
            return true;
        } else {
            return false;
        }
    } catch (err) {
        return false;
    }
}

function MyNnd_EncodeStr(str) {
    return MyNnd_Base64.encode(str).replace(/=/g, "#").replace(/\+/g, "!");
}

function MyNnd_DecodeStr(str) {
    return MyNnd_Base64.decode(str.replace(/#/g, "=").replace(/!/g, "+"));
}

function MyNnd_MsgQueue_Class() {
    this.queue = new Array();
    this.isRunning = false;
    this.currObj = null;

    this.unshift = function (obj) {
        this.queue.unshift(obj);
    };

    this.pop = function () {
        return this.queue.pop();
    };

    this.isEmpty = function () {
        return this.queue.length == 0;
    };

    this.run = function () {
        if (this.isRunning)
            return;

        this.isRunning = true;

        this.sendAMsg();
    };

    this.sendAMsg = function () {
        if (!this.isEmpty()) {
            this.currObj = this.pop();

            if (this.currObj.func == "log") {
                window.open("msg://com.nukt.codelib/?func=log&info=" + MyNnd_EncodeStr(this.currObj.info));
            } else if (this.currObj.func == "toast") {
                window.open("msg://com.nukt.codelib/?func=toast&info=" + MyNnd_EncodeStr(this.currObj.info));
            } else if (this.currObj.func == "init") {
                window.open("msg://com.nukt.codelib/?func=init");
            } else if (this.currObj.func == "over") {
                window.open("msg://com.nukt.codelib/?func=over");
            } else if (this.currObj.func == "call") {
                window.open("msg://com.nukt.codelib/?func=call");
            } else if (this.currObj.func == "callStr") {
                window.open("msg://com.nukt.codelib/?func=callStr&str=" + MyNnd_EncodeStr(this.currObj.str));
            } else if (this.currObj.func == "callCmd") {
                window.open("msg://com.nukt.codelib/?func=callCmd&cmd=" + MyNnd_EncodeStr(this.currObj.cmd));
            } else if (this.currObj.func == "callCmd1") {
                window.open("msg://com.nukt.codelib/?func=callCmd1&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1));
            } else if (this.currObj.func == "callCmd2") {
                window.open("msg://com.nukt.codelib/?func=callCmd2&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2));
            } else if (this.currObj.func == "callCmd3") {
                window.open("msg://com.nukt.codelib/?func=callCmd3&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3));
            } else if (this.currObj.func == "callCmd4") {
                window.open("msg://com.nukt.codelib/?func=callCmd4&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3) + "&p4=" + MyNnd_EncodeStr(this.currObj.p4));
            } else if (this.currObj.func == "callCmd5") {
                window.open("msg://com.nukt.codelib/?func=callCmd5&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3) + "&p4=" + MyNnd_EncodeStr(this.currObj.p4) + "&p5=" + MyNnd_EncodeStr(this.currObj.p5));
            } else if (this.currObj.func == "callCmd6") {
                window.open("msg://com.nukt.codelib/?func=callCmd6&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3) + "&p4=" + MyNnd_EncodeStr(this.currObj.p4) + "&p5=" + MyNnd_EncodeStr(this.currObj.p5) + "&p6=" + MyNnd_EncodeStr(this.currObj.p6));
            } else if (this.currObj.func == "callCmd7") {
                window.open("msg://com.nukt.codelib/?func=callCmd7&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3) + "&p4=" + MyNnd_EncodeStr(this.currObj.p4) + "&p5=" + MyNnd_EncodeStr(this.currObj.p5) + "&p6=" + MyNnd_EncodeStr(this.currObj.p6) + "&p7=" + MyNnd_EncodeStr(this.currObj.p7));
            } else if (this.currObj.func == "callCmd8") {
                window.open("msg://com.nukt.codelib/?func=callCmd8&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3) + "&p4=" + MyNnd_EncodeStr(this.currObj.p4) + "&p5=" + MyNnd_EncodeStr(this.currObj.p5) + "&p6=" + MyNnd_EncodeStr(this.currObj.p6) + "&p7=" + MyNnd_EncodeStr(this.currObj.p7) + "&p8=" + MyNnd_EncodeStr(this.currObj.p8));
            } else if (this.currObj.func == "callCmd9") {
                window.open("msg://com.nukt.codelib/?func=callCmd9&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3) + "&p4=" + MyNnd_EncodeStr(this.currObj.p4) + "&p5=" + MyNnd_EncodeStr(this.currObj.p5) + "&p6=" + MyNnd_EncodeStr(this.currObj.p6) + "&p7=" + MyNnd_EncodeStr(this.currObj.p7) + "&p8=" + MyNnd_EncodeStr(this.currObj.p8) + "&p9="
                    + MyNnd_EncodeStr(this.currObj.p9));
            }
        } else {
            this.isRunning = false;
            this.currObj = null;
        }
    };

    this.feedback = function (ret) {
        if (this.currObj.callback != null)
            this.currObj.callback(ret);
        this.sendAMsg();
    };
}

var MyNnd_MsgQueue = new MyNnd_MsgQueue_Class();

function MyNnd_Log(info, callback) {
    console.log(info);
    // if (MyNnd_CanJs())
    // {
    // 	var str = nnd_native.log(info);
    // 	setTimeout(function()
    // 	{
    // 		if (callback != null)
    // 			callback(str);
    // 	}, 1);
    // }
    // else
    // {
    // 	var obj = new Object();
    // 	obj.func = "log";
    // 	obj.info = info;
    // 	obj.callback = callback;
    // 	MyNnd_MsgQueue.unshift(obj);
    //
    // 	MyNnd_MsgQueue.run();
    // }
}

function MyNnd_Toast(info, callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.toast(info);
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "toast";
        obj.info = info;
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_Init(callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.init();
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "init";
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_Over(callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.over();
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "over";
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_Call(callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.call();
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "call";
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_CallStr(str, callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.callStr(str);
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "callStr";
        obj.str = str;
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_CallCmd(cmd, callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.callCmd(cmd);
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "callCmd";
        obj.cmd = cmd;
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_CallCmd1(cmd, p1, callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.callCmd1(cmd, p1);
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "callCmd1";
        obj.cmd = cmd;
        obj.p1 = p1;
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_CallCmd2(cmd, p1, p2, callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.callCmd2(cmd, p1, p2);
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "callCmd2";
        obj.cmd = cmd;
        obj.p1 = p1;
        obj.p2 = p2;
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_CallCmd3(cmd, p1, p2, p3, callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.callCmd3(cmd, p1, p2, p3);
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "callCmd3";
        obj.cmd = cmd;
        obj.p1 = p1;
        obj.p2 = p2;
        obj.p3 = p3;
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_CallCmd4(cmd, p1, p2, p3, p4, callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.callCmd4(cmd, p1, p2, p3, p4);
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "callCmd4";
        obj.cmd = cmd;
        obj.p1 = p1;
        obj.p2 = p2;
        obj.p3 = p3;
        obj.p4 = p4;
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_CallCmd5(cmd, p1, p2, p3, p4, p5, callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.callCmd5(cmd, p1, p2, p3, p4, p5);
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "callCmd5";
        obj.cmd = cmd;
        obj.p1 = p1;
        obj.p2 = p2;
        obj.p3 = p3;
        obj.p4 = p4;
        obj.p5 = p5;
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_CallCmd6(cmd, p1, p2, p3, p4, p5, p6, callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.callCmd6(cmd, p1, p2, p3, p4, p5, p6);
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "callCmd6";
        obj.cmd = cmd;
        obj.p1 = p1;
        obj.p2 = p2;
        obj.p3 = p3;
        obj.p4 = p4;
        obj.p5 = p5;
        obj.p6 = p6;
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_CallCmd7(cmd, p1, p2, p3, p4, p5, p6, p7, callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.callCmd7(cmd, p1, p2, p3, p4, p5, p6, p7);
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "callCmd7";
        obj.cmd = cmd;
        obj.p1 = p1;
        obj.p2 = p2;
        obj.p3 = p3;
        obj.p4 = p4;
        obj.p5 = p5;
        obj.p6 = p6;
        obj.p7 = p7;
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_CallCmd8(cmd, p1, p2, p3, p4, p5, p6, p7, p8, callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.callCmd8(cmd, p1, p2, p3, p4, p5, p6, p7, p8);
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "callCmd8";
        obj.cmd = cmd;
        obj.p1 = p1;
        obj.p2 = p2;
        obj.p3 = p3;
        obj.p4 = p4;
        obj.p5 = p5;
        obj.p6 = p6;
        obj.p7 = p7;
        obj.p8 = p8;
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}

function MyNnd_CallCmd9(cmd, p1, p2, p3, p4, p5, p6, p7, p8, p9, callback) {
    if (MyNnd_CanJs()) {
        var str = nnd_native.callCmd9(cmd, p1, p2, p3, p4, p5, p6, p7, p8, p9);
        setTimeout(function () {
            if (callback != null)
                callback(str);
        }, 1);
    } else {
        var obj = new Object();
        obj.func = "callCmd9";
        obj.cmd = cmd;
        obj.p1 = p1;
        obj.p2 = p2;
        obj.p3 = p3;
        obj.p4 = p4;
        obj.p5 = p5;
        obj.p6 = p6;
        obj.p7 = p7;
        obj.p8 = p8;
        obj.p9 = p9;
        obj.callback = callback;
        MyNnd_MsgQueue.unshift(obj);

        MyNnd_MsgQueue.run();
    }
}


//------------------------------------------------------- nnd_base_lib_utils ----------------------------------------------------------

/**
 * open activity -------------------------------
 */

function MyNnd_OpenActivity(callback) {
    window.open("nnd://com.nukt.codelib/?num=456&info=abc&is=true");
    setTimeout(function () {
        if (callback != null)
            callback();
    }, 10);
}

/*
 * click -------------------------------
 */

function MyNnd_MouseDown_Delay_Callback(node, delayTime, callback) {
    if (node != null) {
        var e = document.createEvent("MouseEvents");
        e.initEvent("mousedown", true, true);
        node.dispatchEvent(e);

        setTimeout(function () {
            if (callback != null)
                callback();
        }, delayTime);
    }
}

function MyNnd_Click(node) {
    if (node != null) {
        var e = document.createEvent("MouseEvents");
        e.initEvent("click", true, true);
        node.dispatchEvent(e);
    }
}

function MyNnd_Click_Callback(node, callback) {
    if (node != null) {
        var e = document.createEvent("MouseEvents");
        e.initEvent("click", true, true);
        node.dispatchEvent(e);

        setTimeout(function () {
            if (callback != null)
                callback();
        }, 10);
    }
}

function MyNnd_Click_Delay_Callback(node, delayTime, callback) {
    if (node != null) {
        var e = document.createEvent("MouseEvents");
        e.initEvent("click", true, true);
        node.dispatchEvent(e);

        setTimeout(function () {
            if (callback != null)
                callback();
        }, delayTime);
    }
}

function MyNnd_Delay_Click_Callback(delayTime, node, callback) {
    MyNnd_Delay(delayTime, function () {
        MyNnd_Click_Callback(node, callback);
    });
}

/*
 * delay -------------------------------
 */

function MyNnd_Delay(delayTime, callback) {
    setTimeout(function () {
        if (callback != null)
            callback();
    }, delayTime);
}

function MyNnd_Delay_Param1(delayTime, callback, param1) {
    setTimeout(function () {
        if (callback != null)
            callback(param1);
    }, delayTime);
}

function MyNnd_Delay_Param2(delayTime, callback, param1, param2) {
    setTimeout(function () {
        if (callback != null)
            callback(param1, param2);
    }, delayTime);
}

/*
 * tools -------------------------------
 */

function MyNnd_Str_StartsWith(str, prefix) {
    if (str.indexOf(prefix) == 0) {
        return true;
    } else {
        return false;
    }
}

function MyNnd_GetIndentStr(indent) {
    var str = "";
    for (var i = 0; i < indent; i++) {
        str += "\t";
    }
    return str;
}

/*
 * break -------------------------------
 */

function MyNnd_Break(info) {
    MyNnd_Log(info, function (ret) {
        MyNnd_Over(null);
    });
}

/*
 * random -------------------------------
 */

/*
 * [from, to)
 */
function MyNnd_RandomImpl(from, to) {
    return Math.floor(Math.random() * (to - from)) + from;
}

function MyNnd_Random(length) {
    return MyNnd_RandomImpl(0, length);
}

function MyNnd_Random_Node(nodeArray) {
    return nodeArray[MyNnd_Random(nodeArray.length)];
}

function MyNnd_Random_Do(probability, callback, required) {
    if (MyNnd_Random(100) < probability) {
        if (callback != null)
            callback(true);
    } else {
        if (required) {
            MyNnd_Break("概率性终止:" + probability);
        } else {
            if (callback != null)
                callback(false);
        }
    }
}


//------------------------------------------------------- nnd_base_lib_dom ----------------------------------------------------------

function MyNnd_GetChild(parent, tag, index) {
    var counter = 0;
    if (parent.childNodes) {
        for (var i = 0; i < parent.childNodes.length; i++) {
            var node = parent.childNodes[i];
            if (node.nodeName == tag.toUpperCase()) {
                if (counter == index) {
                    return node;
                } else {
                    counter++;
                }
            }
        }
    }
    return null;
}

function MyNnd_FindByText(rootNode, tag, text) {
    var nodes = MyNnd_Find_TagList(rootNode, tag);
    if (nodes != null) {
        for (var i = 0; i < nodes.length; i++) {
            var node = nodes[i];
            if (node.innerHTML.trim() == text) {
                return node;
            }
        }
    }
    MyNnd_Log("MyNnd_FindByText not find : " + tag + "," + text, null);
    return null;
}

function MyNnd_Find_Id(rootNode, id) {
    return rootNode.getElementById(id);
}

function MyNnd_Find_TagList(rootNode, tag) {
    return rootNode.getElementsByTagName(tag);
}

function MyNnd_Find_TagExt(rootNode, tag, lowLimit, highLimit, getIndex) {
    var nodes = MyNnd_Find_TagList(rootNode, tag);

    if (nodes == null) {
        return null;
    }

    if (lowLimit >= 0 && nodes.length < lowLimit) {
        MyNnd_Log("tag数量不正确:" + tag + ",nodes.length is " + nodes.length + ",lowLimit is " + lowLimit + ",highLimit is " + highLimit, null);
        return null;
    }

    if (highLimit >= 0 && nodes.length > highLimit) {
        MyNnd_Log("tag数量不正确:" + tag + ",nodes.length is " + nodes.length + ",lowLimit is " + lowLimit + ",highLimit is " + highLimit, null);
        return null;
    }

    return nodes[getIndex];
}

function MyNnd_Find_Tag(rootNode, tag) {
    return MyNnd_Find_TagExt(rootNode, tag, 1, 1, 0);
}

function MyNnd_Find_ClassList(rootNode, className) {
    return rootNode.getElementsByClassName(className);
}

function MyNnd_Find_ClassExt(rootNode, className, lowLimit, highLimit, getIndex) {
    var nodes = MyNnd_Find_ClassList(rootNode, className);

    if (nodes == null) {
        return null;
    }

    if (lowLimit >= 0 && nodes.length < lowLimit) {
        MyNnd_Log("class数量不正确:" + className + ",nodes.length is " + nodes.length + ",lowLimit is " + lowLimit + ",highLimit is " + highLimit, null);
        return null;
    }

    if (highLimit >= 0 && nodes.length > highLimit) {
        MyNnd_Log("class数量不正确:" + className + ",nodes.length is " + nodes.length + ",lowLimit is " + lowLimit + ",highLimit is " + highLimit, null);
        return null;
    }

    return nodes[getIndex];
}

function MyNnd_Find_Class(rootNode, className) {
    return MyNnd_Find_ClassExt(rootNode, className, 1, 1, 0);
}

function MyNnd_GetStyle(node, name) {
    var style = node.getAttribute("style");
    if (style != undefined) {
        var obj = new Object();

        var attrs = style.split(";");
        for (var i = 0; i < attrs.length; i++) {
            var attr = attrs[i].replace(/(^\s*)|(\s*$)/g, "");
            if (attr.length != 0) {
                var attrArray = attr.split(":");
                var attrName = attrArray[0].replace(/(^\s*)|(\s*$)/g, "");
                var attrValue = attrArray[1].replace(/(^\s*)|(\s*$)/g, "");
                obj[attrName] = attrValue;
            }
        }

        if (obj[name] != undefined)
            return obj[name];
    }
    return undefined;
}

function MyNnd_Filter_Attr(nodes, attrName, attrValue, callback, required) {
    var nodeArray = new Array();
    for (var i = 0; i < nodes.length; i++) {
        var node = nodes[i];
        if (node.getAttribute(attrName) == attrValue) {
            nodeArray[nodeArray.length] = node;
        }
    }
    if (nodeArray.length <= 0) {
        if (required) {
            MyNnd_Break("过滤失败:" + attrName + " is " + attrValue);
        } else {
            if (callback != null)
                callback(null);
        }
    } else {
        if (callback != null)
            callback(nodeArray);
    }
}

function MyNnd_GetNode(nodes, index, callback) {
    if (index < nodes.length) {
        if (callback != null)
            callback(nodes[index], index);
    } else {
        if (callback != null)
            callback(null, index);
    }
}

function MyNnd_GetHtmlBody() {
    return document.body.innerHTML;
}

/*
 * DOM json -------------------------------
 */

function MyNnd_GetDOMTree_Iterator_Json(parent) {
    var root = new Object();

    root.nodeName = parent.nodeName;

    for (var i = 0; i < parent.attributes.length; i++) {
        var attr = parent.attributes[i];
        root[attr.name] = attr.value;
    }

    root.childNodes = new Array();

    for (var i = 0; i < parent.childNodes.length; i++) {
        var node = parent.childNodes[i];

        root.childNodes[root.childNodes.length++] = MyNnd_GetDOMTree_Iterator(node);
    }

    return root;
}

function MyNnd_GetDOMTree_Json() {
    var root = MyNnd_GetDOMTree_Iterator_Json(document.body);
    return JSON.stringify(root);
}

/*
 * DOM xml -------------------------------
 */

function MyNnd_GetDOMTree_Iterator_Xml(parent, indent) {
    var text = "";

    if (parent.nodeName == "#comment")
        return "";

    text += (MyNnd_GetIndentStr(indent) + "<" + parent.nodeName);

    if (parent.attributes) {
        for (var i = 0; i < parent.attributes.length; i++) {
            var attr = parent.attributes[i];

            if (attr.name == "'") {
                /* alert("error attr name : " + attr.name); */
            } else {
                text += (" " + attr.name + "=" + "\"" + attr.value.replace(/&/g, "&amp;").replace(/'/g, "&qpos;").replace(/"/g, "&quot;") + "\"");
            }
        }
    }
    text += ">\r\n";

    if (parent.nodeName == "IFRAME") {
        text += MyNnd_GetDOMTree_Iterator_Xml(parent.contentWindow.document.body, indent + 1);
    } else {
        if (parent.childNodes) {
            for (var i = 0; i < parent.childNodes.length; i++) {
                var node = parent.childNodes[i];

                if (node.nodeName == "SCRIPT" || node.nodeName == "STYLE")
                    continue;

                if (node.nodeName == "#text") {
                    text += (MyNnd_GetIndentStr(indent + 1) + node.nodeValue.replace(/&/g, "&amp;") + "\r\n");
                } else {
                    text += MyNnd_GetDOMTree_Iterator_Xml(node, indent + 1);
                }
            }
        }
    }

    text += (MyNnd_GetIndentStr(indent) + "</" + parent.nodeName + ">" + "\r\n");

    return text;
}

function MyNnd_GetDOMTree_Xml() {
    var text = "";
    try {
        text = MyNnd_GetDOMTree_Iterator_Xml(document.body, 0);
    } catch (err) {
        alert(err.message);
    }
    return text;
}

function MyNnd_DOM() {
    MyNnd_CallCmd1("dom", MyNnd_GetDOMTree_Xml(), null);
}


//------------------------------------------------------- zhixue_juan ----------------------------------------------------------

function MyNnd_Main() {
    MyNnd_Log("MyNnd_Main", null);
}

function MyNnd_SetAccount(account, psw) {
    MyNnd_Log("MyNnd_SetAccount " + account + "," + psw, null);
    var txtUserName = MyNnd_Find_Id(document, "txtUserName");
    if (txtUserName != null) {
        MyNnd_Log("find txtUserName", null);
        txtUserName.value = account;
    }
    var txtPassword = MyNnd_Find_Id(document, "txtPassword");
    if (txtPassword != null) {
        MyNnd_Log("find txtPassword", null);
        txtPassword.value = psw;
    }
}

function MyNnd_ClickIgnore() {
    /* MyNnd_Log("MyNnd_ClickIgnore", null); */
    MyNnd_Delay(4000, function () {
        var aui_content = MyNnd_Find_Class(document, "aui_content");
        if (aui_content != null) {
            /* MyNnd_Log("find aui_content", null); */
            var ignore = MyNnd_Find_Class(aui_content, "ignore");
            if (ignore != null) {
                MyNnd_Log("click ignore", null);
                MyNnd_Click(ignore);
            }
        }
    });
}

function MyNnd_ClickXtzj() {
    /* MyNnd_Log("MyNnd_ClickXtzj", null); */
    MyNnd_Delay(4000, function () {
        var headMenuList = MyNnd_Find_Id(document, "headMenuList");
        if (headMenuList != null) {
            /* MyNnd_Log("find headMenuList", null); */
            var xtzj = MyNnd_FindByText(headMenuList, "A", "选题组卷");
            if (xtzj != null) {
                MyNnd_Log("click 选题组卷", null);
                MyNnd_Click(xtzj);
            }
        }
    });
}

function MyNnd_ClickTbzj() {
    /* MyNnd_Log("MyNnd_ClickTbzj", null); */
    MyNnd_Delay(4000, function () {
        var pub_nav = MyNnd_Find_Class(document, "pub-nav");
        if (pub_nav != null) {
            /* MyNnd_Log("find pub_nav", null); */
            var tbzj = MyNnd_FindByText(pub_nav, "A", "精品卷库");
            if (tbzj != null) {
                MyNnd_Log("click 精品卷库", null);
                MyNnd_Click(tbzj);
            }
        }
    });
}

function MyNnd_ModifyPage() {
    var disBlock = MyNnd_Find_Class(document, "pd-contr");
    var topics = MyNnd_Find_TagList(disBlock, "app-topic-show");
    MyNnd_Log("MyNnd_ModifyPage 题数：" + topics.length, null);
    for (var i = 0; i < topics.length; i++) {
        var topic = topics[i];

        var question_list = MyNnd_GetChild(topic, "DIV", 0);
        var question_content = MyNnd_GetChild(question_list, "DIV", 1);
        var ans_analy = MyNnd_GetChild(question_list, "DIV", 4);

        question_content.setAttribute("style", "font-size: 20px;");

        ans_analy.setAttribute("style", "display: block;");

        var answer = MyNnd_GetChild(ans_analy, "DIV", 0);
        var parse = MyNnd_GetChild(ans_analy, "DIV", 1);

        var answer_p = MyNnd_GetChild(answer, "P", 0);
        answer_p.setAttribute("style", "font-size: 20px;");

        var parse_div = MyNnd_GetChild(parse, "DIV", 0);
        parse_div.setAttribute("style", "font-size: 20px;");
    }
    return topics.length;
}

function MyNnd_ModifyPage2() {
    var disBlock = MyNnd_Find_Class(document, "paper-pane");

    var titles = MyNnd_Find_ClassList(disBlock, "topicset-title");
    MyNnd_Log("MyNnd_ModifyPage 大题数：" + titles.length, null);
    for (var i = 0; i < titles.length; i++) {
        var title = titles[i];
        title.setAttribute("style", "display: none;");
    }

    var topics = MyNnd_Find_TagList(disBlock, "app-topic-view");
    MyNnd_Log("MyNnd_ModifyPage 题数：" + topics.length, null);
    for (var i = 0; i < topics.length; i++) {
        var topic = topics[i];

        var question_content = MyNnd_GetChild(topic, "DIV", 0);
        var ans_analy = MyNnd_GetChild(topic, "DIV", 1);

        question_content.setAttribute("style", "padding: 10px; font-size: 18px; border: 1px solid #0dc2b3; margin: -1px;");

        ans_analy.setAttribute("style", "display: block;");

        var answer = MyNnd_GetChild(ans_analy, "DIV", 0);
        var parse = MyNnd_GetChild(ans_analy, "DIV", 1);
        var knowledge = MyNnd_GetChild(ans_analy, "DIV", 2);

        var answer_p = MyNnd_GetChild(answer, "P", 0);
        answer_p.setAttribute("style", "font-size: 18px;");

        var parse_P = MyNnd_GetChild(parse, "P", 0);
        parse_P.setAttribute("style", "font-size: 18px;");

        knowledge.setAttribute("style", "display: none;");
    }
    return topics.length;
}

function MyNnd_CatchProblems() {
    var problems = new Array();
    var kldList = MyNnd_Find_Class(document, "pd-contr");
    var topics = MyNnd_Find_TagList(kldList, "app-topic-show");
    MyNnd_Log("MyNnd_CatchProblems 题数：" + topics.length, null);
    for (var i = 0; i < topics.length; i++) {
        var topic = topics[i];

        var problem = new Object();

        var question_list = MyNnd_GetChild(topic, "DIV", 0);
        var question_content = MyNnd_GetChild(question_list, "DIV", 1);
        var operate_area = MyNnd_GetChild(question_list, "DIV", 3);
        var ans_analy = MyNnd_GetChild(question_list, "DIV", 4);

        problem.id = question_content.getAttribute("id").trim();

        problem.content = question_content.innerText.trim().replace(new RegExp("\r\n", "g"), "").replace(new RegExp("\n", "g"), "");

        var answer = MyNnd_GetChild(ans_analy, "DIV", 0);
        var parse = MyNnd_GetChild(ans_analy, "DIV", 1);
        var knowledge = MyNnd_GetChild(ans_analy, "DIV", 2);
        var style = MyNnd_GetChild(ans_analy, "DIV", 3);

        var answer_p = MyNnd_GetChild(answer, "P", 0);
        problem.answer = answer_p.innerText.trim().replace(new RegExp("\r\n", "g"), "").replace(new RegExp("\n", "g"), "");

        var parse_div = MyNnd_GetChild(parse, "DIV", 0);
        problem.parse = parse_div.innerText.trim().replace(new RegExp("\r\n", "g"), "").replace(new RegExp("\n", "g"), "");

        problem.knowledges = new Array();
        var knowledge_div = MyNnd_GetChild(knowledge, "DIV", 0);
        var spans = MyNnd_Find_TagList(knowledge_div, "SPAN");
        for (var j = 0; j < spans.length; j++) {
            var span = spans[j];
            problem.knowledges[problem.knowledges.length++] = span.innerHTML.trim();
        }

        var style_div = MyNnd_GetChild(style, "DIV", 0);
        var style_p = MyNnd_GetChild(style_div, "P", 0);
        problem.style = style_p.innerHTML.trim();

        /*
         * var zujuan = MyNnd_Find_Class(operate_area, "zujuan"); var zuoda = MyNnd_Find_Class(operate_area, "zuoda"); problem.zujuan = zujuan.innerText.trim(); problem.zuoda = zuoda.innerText.trim();
         */

        var defens = operate_area.getElementsByClassName("defen");
        if (defens != null && defens.length == 1) {
            problem.defen = defens[0].innerText.trim();
        }

        var nandus = operate_area.getElementsByClassName("nandu");
        if (nandus != null && nandus.length == 1) {
            problem.nandu = nandus[0].innerText.trim();
        }

        problems[problems.length++] = problem;
    }
    return problems;
}

function MyNnd_CatchProblems2() {
    var problems = new Array();
    var disBlock = MyNnd_Find_Class(document, "paper-pane");
    var topics = MyNnd_Find_TagList(disBlock, "app-topic-view");
    MyNnd_Log("MyNnd_CatchProblems 题数：" + topics.length, null);
    for (var i = 0; i < topics.length; i++) {
        var topic = topics[i];

        var problem = new Object();

        var question_content = MyNnd_GetChild(topic, "DIV", 0);
        var ans_analy = MyNnd_GetChild(topic, "DIV", 1);

        var answer = MyNnd_GetChild(ans_analy, "DIV", 0);
        var parse = MyNnd_GetChild(ans_analy, "DIV", 1);
        var answer_p = MyNnd_GetChild(answer, "P", 0);
        var parse_P = MyNnd_GetChild(parse, "P", 0);

        problem.answer = answer_p.innerText.trim().replace(new RegExp("\r\n", "g"), "").replace(new RegExp("\n", "g"), "");

        problems[problems.length++] = problem;
    }
    return problems;
}

function MyNnd_CheckTopicImpl(text) {
    if (text.indexOf("\\(") >= 0 || text.indexOf("(\\") >= 0 || text.indexOf("^{") >= 0) {
        return false;
    } else {
        return true;
    }
}

function MyNnd_CheckTopic(problem) {
    if (!MyNnd_CheckTopicImpl(problem.content)) {
        MyNnd_Log("bad content : " + problem.content, null);
        return false;
    }
    if (!MyNnd_CheckTopicImpl(problem.answer)) {
        MyNnd_Log("bad answer : " + problem.answer, null);
        return false;
    }
    if (!MyNnd_CheckTopicImpl(problem.parse)) {
        MyNnd_Log("bad parse : " + problem.parse, null);
        return false;
    }
    return true;
}

function MyNnd_HasRequestError() {
    var pt_msg_div_list = MyNnd_Find_ClassList(document, "pt_msg_div");
    if (pt_msg_div_list.length > 0) {
        for (var i = 0; i < pt_msg_div_list.length; i++) {
            var pt_msg_div = pt_msg_div_list[i];
            var msg_str = pt_msg_div.innerText.trim();
            if (msg_str == "请求出错") {
                return true;
            }
        }
    }
    return false;
}

function MyNnd_TryCatchProblems(name, phase) {
    var problems = MyNnd_CatchProblems();
    var checkSuccess = true;
    for (var i = 0; i < problems.length; i++) {
        var problem = problems[i];
        if (!MyNnd_CheckTopic(problem)) {
            MyNnd_Log(times + " check fail and topic number is : " + (i + 1), null);
            checkSuccess = false;
            break;
        }
    }
    if (checkSuccess) {
        for (var i = 0; i < problems.length; i++) {
            var problem = problems[i];
        }
        var info = new Object();
        info.phase = phase;
        info.subject = "数学";
        info.material = name;
        info.chapterPath = "1";
        info.diff = "1";
        info.area = "1";
        info.year = "1";
        var htmlStr = document.getElementsByTagName('html')[0].innerHTML;
        if (MyNnd_HasRequestError()) {
            MyNnd_Log("has 请求出错", null);
        } else {
            MyNnd_CallCmd4("resultProblems", JSON.stringify(info), 0, JSON.stringify(problems), htmlStr, null);
        }
    } else {
    }
}

function MyNnd_TryCatchProblems2(name, phase) {
    var problems = MyNnd_CatchProblems2();
    var info = new Object();
    info.phase = phase;
    info.subject = "数学";
    info.material = name;
    info.chapterPath = "1";
    info.diff = "1";
    info.area = "1";
    info.year = "1";
    // var htmlStr = document.getElementsByTagName('html')[0].innerHTML;
    // MyNnd_CallCmd4("resultProblems2", JSON.stringify(info), 0, JSON.stringify(problems), htmlStr, null);
    resultProblems2(info, 0, problems, '');
}

function MyNnd_GetNamePhase() {
    var result = new Object();
    try {
        var totalPage_span = MyNnd_Find_Class(document, "pd-topl");
        if (totalPage_span != null) {
            var p0 = MyNnd_GetChild(totalPage_span, "P", 0);
            var p1 = MyNnd_GetChild(totalPage_span, "P", 1);
            var span = MyNnd_GetChild(p1, "SPAN", 1);
            result.name = p0.innerHTML.trim();
            result.phase = span.innerHTML.trim();
            result.subject = "数学";
            return result;
        }
    } catch (err) {
        alert(err.message);
    }
    return null;
}

function MyNnd_GetNamePhase2() {
    var result = new Object();
    try {
        var paper_head = MyNnd_Find_Class(document, "paper-head");
        if (paper_head != null) {
            result.name = paper_head.innerHTML.trim();
            result.phase = "试卷";
            result.subject = "数学";
            return result;
        }
    } catch (err) {
        alert(err.message);
    }
    return null;
}

function MyNnd_CatchOnePage() {
    var topicCount = MyNnd_ModifyPage();
    if (topicCount > 0) {
        MyNnd_Delay(10000, function () {
            var totalPage_span = MyNnd_Find_Class(document, "pd-topl");
            if (totalPage_span != null) {
                var p0 = MyNnd_GetChild(totalPage_span, "P", 0);
                var p1 = MyNnd_GetChild(totalPage_span, "P", 1);
                var span = MyNnd_GetChild(p1, "SPAN", 1);
                var name = p0.innerHTML.trim();
                var phase = span.innerHTML.trim();
                MyNnd_TryCatchProblems(name, phase);
            }
        });
    } else {
    }
}

function MyNnd_CatchOnePage2() {
    var topicCount = MyNnd_ModifyPage2();
    if (topicCount > 0) {
        MyNnd_Delay(1000, function () {
            var paper_head = MyNnd_Find_Class(document, "paper-head");
            if (paper_head != null) {
                var name = paper_head.innerHTML.trim();
                var phase = "试卷";
                MyNnd_TryCatchProblems2(name, phase);
            }
        });
    } else {
    }
}

function MyNnd_CatchNextChapter(phase, subject, material, diff, area, year, currChapterPath, currPage) {
    var chapterLeaf = MyNnd_FindNextLeafChapter(currChapterPath);
    if (chapterLeaf != null) {
        MyNnd_Log("click chapterLeaf : " + chapterLeaf.path, null);
        MyNnd_MouseDown_Delay_Callback(chapterLeaf.li_a, 4000, function () {
            currChapterPath = chapterLeaf.path;
            var totalPage_span = MyNnd_Find_Class(document, "totalPage");
            if (totalPage_span != null) {
                var totalPage = totalPage_span.innerHTML.trim();
                /* MyNnd_Log("find totalPage " + totalPage, null); */
                if (parseInt(currPage) <= parseInt(totalPage)) {
                    MyNnd_CatchOnePage(phase, subject, material, diff, area, year, currChapterPath, currPage);
                } else {
                    MyNnd_CatchNextChapter(phase, subject, material, diff, area, year, currChapterPath, 1);
                }
            }
        });
    } else {
        /* MyNnd_Log("not find next chapterLeaf", null); */
        var info = new Object();
        info.phase = phase;
        info.subject = subject;
        info.material = material;
        info.diff = diff;
        info.area = area;
        info.year = year;
        MyNnd_CallCmd1("catchOver", JSON.stringify(info), null);
    }
}

function MyNnd_CatchPage(diff, area, year, currChapterPath, currPage) {
    /* MyNnd_Log("MyNnd_CatchPage " + diff + "," + area + "," + year + "," + currChapterPath + "," + currPage, null); */
    MyNnd_Delay(4000, function () {
        /* MyNnd_Log("start catch", null); */
        var phase_subject = MyNnd_Find_Class(document, "phase_subject");
        if (phase_subject != null) {
            var phaseSubject = phase_subject.innerHTML.trim().split("·");
            var phase = phaseSubject[0].trim();
            var subject = phaseSubject[1].trim();
            /* MyNnd_Log("find phase_subject " + phase + "," + subject, null); */
            var leftTree = MyNnd_Find_Class(document, "leftTree");
            if (leftTree != null) {
                /* MyNnd_Log("find leftTree", null); */
                var leftTree_material = MyNnd_GetChild(leftTree, "DIV", 0);
                var curBook = MyNnd_Find_Class(leftTree_material, "curBook");
                var material = curBook.innerText.trim();
                /* MyNnd_Log("find material " + material, null); */

                var selectorPanel = MyNnd_Find_Class(document, "selectorPanel");
                if (selectorPanel != null) {
                    /* MyNnd_Log("find selectorPanel", null); */
                    var span = MyNnd_GetChild(selectorPanel, "SPAN", 0);
                    var dl_diff = MyNnd_GetChild(span, "DL", 1);
                    var a_diff = MyNnd_FindByText(dl_diff, "A", diff);
                    if (a_diff != null) {
                        MyNnd_Log("click " + diff, null);
                        MyNnd_Click_Delay_Callback(a_diff, 4000, function () {
                            var filter_item_content = MyNnd_Find_Class(span, "filter-item-content");
                            var filter_area = MyNnd_GetChild(filter_item_content, "app-condition-selector-more", 0);
                            var filter_year = MyNnd_GetChild(filter_item_content, "app-condition-selector-more", 2);

                            var filter_area_dropdown = MyNnd_Find_Class(filter_area, "dropdown");
                            var a_area = MyNnd_FindByText(filter_area_dropdown, "A", area);
                            if (a_area != null) {
                                MyNnd_Log("click " + area, null);
                                MyNnd_Click_Delay_Callback(a_area, 4000, function () {
                                    var filter_year_dropdown = MyNnd_Find_Class(filter_year, "dropdown");
                                    var a_year = MyNnd_FindByText(filter_year_dropdown, "A", year);
                                    if (a_year != null) {
                                        MyNnd_Log("click " + year, null);
                                        MyNnd_Click_Delay_Callback(a_year, 4000, function () {
                                            MyNnd_ClickChapter(function () {
                                                /* start logic */
                                                if (currChapterPath == "") {
                                                    MyNnd_CatchNextChapter(phase, subject, material, diff, area, year, currChapterPath, 1);
                                                } else {
                                                    var chapterLeaf = MyNnd_FindLeafChapter(currChapterPath);
                                                    if (chapterLeaf != null) {
                                                        MyNnd_MouseDown_Delay_Callback(chapterLeaf.li_a, 4000, function () {
                                                            MyNnd_CatchOnePage(phase, subject, material, diff, area, year, currChapterPath, currPage);
                                                        });
                                                    } else {
                                                        alert("error @293 " + currChapterPath);
                                                    }
                                                }
                                                /* end logic */
                                            });
                                        });
                                    }
                                });
                            }
                        });
                    }
                }
            }
        }
    });
}

function MyNnd_GetPhaseSubject() {
    /* MyNnd_Log("MyNnd_GetPhaseSubject", null); */
    var result = new Object();
    try {
        var phase_subject = MyNnd_Find_Class(document, "phase_subject");
        if (phase_subject != null) {
            var phaseSubject = phase_subject.innerHTML.trim().split("·");
            result.phase = phaseSubject[0].trim();
            result.subject = phaseSubject[1].trim();
            /* MyNnd_Log("find phase_subject " + result.phase + "," + result.subject, null); */
            return result;
        }
    } catch (err) {
        alert(err.message);
    }
    return null;
}

function MyNnd_GetMaterial() {
    /* MyNnd_Log("MyNnd_GetMaterial", null); */
    var result = new Object();
    var leftTree = MyNnd_Find_Class(document, "leftTree");
    if (leftTree != null) {
        /* MyNnd_Log("find leftTree", null); */
        var leftTree_material = MyNnd_GetChild(leftTree, "DIV", 0);
        var curBook = MyNnd_Find_Class(leftTree_material, "curBook");
        result.material = curBook.innerText.trim();
        return result;
    }
}

function MyNnd_GetGroup() {
    /* MyNnd_Log("MyNnd_GetGroup", null); */
    var group = new Object();
    try {
        var selectorPanel = MyNnd_Find_Class(document, "selectorPanel");
        if (selectorPanel != null) {
            /* MyNnd_Log("find selectorPanel", null); */
            var span = MyNnd_GetChild(selectorPanel, "SPAN", 0);
            var dl_style = MyNnd_GetChild(span, "DL", 0);
            var dl_diff = MyNnd_GetChild(span, "DL", 1);

            group.diffs = new Array();
            group.areas = new Array();
            group.years = new Array();

            var as_diff = MyNnd_Find_TagList(dl_diff, "A");
            for (var i = 0; i < as_diff.length; i++) {
                var a_diff = as_diff[i];
                if (a_diff.innerHTML.trim() != "全部") {
                    group.diffs[group.diffs.length++] = a_diff.innerHTML.trim();
                }
            }

            var filter_item_content = MyNnd_Find_Class(span, "filter-item-content");
            var filter_area = MyNnd_GetChild(filter_item_content, "app-condition-selector-more", 0);
            var filter_year = MyNnd_GetChild(filter_item_content, "app-condition-selector-more", 2);

            var filter_area_dropdown = MyNnd_Find_Class(filter_area, "dropdown");
            var as_area = MyNnd_Find_TagList(filter_area_dropdown, "A");
            for (var i = 0; i < as_area.length; i++) {
                var a_area = as_area[i];
                if (a_area.innerHTML.trim() != "全国" && a_area.innerHTML.trim() != "本省" && a_area.innerHTML.trim() != "香港" && a_area.innerHTML.trim() != "澳门" && a_area.innerHTML.trim() != "台湾") {
                    group.areas[group.areas.length++] = a_area.innerHTML.trim();
                }
            }

            var filter_year_dropdown = MyNnd_Find_Class(filter_year, "dropdown");
            var as_year = MyNnd_Find_TagList(filter_year_dropdown, "A");
            for (var i = 0; i < as_year.length; i++) {
                var a_year = as_year[i];
                if (a_year.innerHTML.trim() != "全部") {
                    group.years[group.years.length++] = a_year.innerHTML.trim();
                }
            }

            return group;
        }
    } catch (err) {
        alert(err.message);
    }
    return null;
}

function MyNnd_ClickKnowledge() {
    /* MyNnd_Log("MyNnd_ClickKnowledge", null); */
    var leftTree = MyNnd_Find_Class(document, "leftTree");
    if (leftTree != null) {
        /* MyNnd_Log("find leftTree", null); */
        var leftTree_div = MyNnd_GetChild(leftTree, "DIV", 2);
        var leftTree_app_tree = MyNnd_GetChild(leftTree_div, "app-tree", 0);
        var ztree = MyNnd_GetChild(leftTree_app_tree, "UL", 0);
        var roots_close_list = MyNnd_Find_ClassList(ztree, "roots_close");
        if (roots_close_list.length > 0) {
            MyNnd_Click_Delay_Callback(roots_close_list[0], 500, function () {
                MyNnd_ClickKnowledge();
            });
        } else {
            var bottom_close_list = MyNnd_Find_ClassList(ztree, "bottom_close");
            if (bottom_close_list.length > 0) {
                MyNnd_Click_Delay_Callback(bottom_close_list[0], 500, function () {
                    MyNnd_ClickKnowledge();
                });
            } else {
                var center_close_list = MyNnd_Find_ClassList(ztree, "center_close");
                if (center_close_list.length > 0) {
                    MyNnd_Click_Delay_Callback(center_close_list[0], 500, function () {
                        MyNnd_ClickKnowledge();
                    });
                } else {
                    MyNnd_Log("展开 knowledges over", null);
                    var phaseSubject = MyNnd_GetPhaseSubject();
                    var knowledges = MyNnd_GetKnowledge();
                    knowledges.phase = phaseSubject.phase;
                    knowledges.subject = phaseSubject.subject;
                    MyNnd_CallCmd1("resultKnowledges", JSON.stringify(knowledges), null);
                }
            }
        }
    }
}

function MyNnd_GetKnowledgeNode(li) {
    var knowledge = new Object();

    var li_span = MyNnd_GetChild(li, "SPAN", 0);
    var li_a = MyNnd_GetChild(li, "A", 0);
    var li_ul = MyNnd_GetChild(li, "UL", 0);
    var span = MyNnd_GetChild(li_a, "SPAN", 1);

    knowledge.index = li.getAttribute("id").trim().substring(37);
    knowledge.name = span.innerHTML.trim();

    if (li_ul != null) {
        knowledge.knowledges = new Array();
        for (var i = 0; i < li_ul.childNodes.length; i++) {
            var child = li_ul.childNodes[i];
            if (child.nodeName == "LI") {
                knowledge.knowledges[knowledge.knowledges.length++] = MyNnd_GetKnowledgeNode(child);
            }
        }
    }

    return knowledge;
}

function MyNnd_GetKnowledge() {
    /* MyNnd_Log("MyNnd_GetKnowledge", null); */
    var root = new Object();
    try {
        var leftTree = MyNnd_Find_Class(document, "leftTree");
        if (leftTree != null) {
            /* MyNnd_Log("find leftTree", null); */

            var leftTree_div = MyNnd_GetChild(leftTree, "DIV", 2);
            var leftTree_app_tree = MyNnd_GetChild(leftTree_div, "app-tree", 0);
            var ztree = MyNnd_GetChild(leftTree_app_tree, "UL", 0);

            root.knowledges = new Array();

            for (var i = 0; i < ztree.childNodes.length; i++) {
                var li = ztree.childNodes[i];
                if (li.nodeName == "LI") {
                    root.knowledges[root.knowledges.length++] = MyNnd_GetKnowledgeNode(li);
                }
            }

            return root;
        }
    } catch (err) {
        alert(err.message);
    }
    return null;
}

function MyNnd_ClickChapter(callback) {
    /* MyNnd_Log("MyNnd_ClickChapter", null); */
    var leftTree = MyNnd_Find_Class(document, "leftTree");
    if (leftTree != null) {
        /* MyNnd_Log("find leftTree", null); */
        var leftTree_div = MyNnd_GetChild(leftTree, "DIV", 1);
        var leftTree_app_tree = MyNnd_GetChild(leftTree_div, "app-tree", 0);
        var ztree = MyNnd_GetChild(leftTree_app_tree, "UL", 0);
        var roots_close_list = MyNnd_Find_ClassList(ztree, "roots_close");
        if (roots_close_list.length > 0) {
            MyNnd_Click_Delay_Callback(roots_close_list[0], 500, function () {
                MyNnd_ClickChapter(callback);
            });
        } else {
            var bottom_close_list = MyNnd_Find_ClassList(ztree, "bottom_close");
            if (bottom_close_list.length > 0) {
                MyNnd_Click_Delay_Callback(bottom_close_list[0], 500, function () {
                    MyNnd_ClickChapter(callback);
                });
            } else {
                var center_close_list = MyNnd_Find_ClassList(ztree, "center_close");
                if (center_close_list.length > 0) {
                    MyNnd_Click_Delay_Callback(center_close_list[0], 500, function () {
                        MyNnd_ClickChapter(callback);
                    });
                } else {
                    MyNnd_Log("展开 chapters over", null);
                    if (callback != null)
                        callback();
                }
            }
        }
    }
}

function MyNnd_CatchChapter() {
    MyNnd_ClickChapter(function () {
        var phaseSubject = MyNnd_GetPhaseSubject();
        var chapters = MyNnd_GetChapter();
        chapters.phase = phaseSubject.phase;
        chapters.subject = phaseSubject.subject;
        chapters.material = MyNnd_GetMaterial().material;
        MyNnd_CallCmd1("resultChapters", JSON.stringify(chapters), null);
    });
}

function MyNnd_GetChapterNode(li) {
    var chapter = new Object();

    var li_span = MyNnd_GetChild(li, "SPAN", 0);
    var li_a = MyNnd_GetChild(li, "A", 0);
    var li_ul = MyNnd_GetChild(li, "UL", 0);
    var span = MyNnd_GetChild(li_a, "SPAN", 1);

    chapter.index = li.getAttribute("id").trim().substring(37);
    chapter.name = span.innerHTML.trim();

    if (li_ul != null) {
        chapter.chapters = new Array();
        for (var i = 0; i < li_ul.childNodes.length; i++) {
            var child = li_ul.childNodes[i];
            if (child.nodeName == "LI") {
                chapter.chapters[chapter.chapters.length++] = MyNnd_GetChapterNode(child);
            }
        }
    }

    return chapter;
}

function MyNnd_GetChapter() {
    /* MyNnd_Log("MyNnd_GetChapter", null); */
    var root = new Object();
    try {
        var leftTree = MyNnd_Find_Class(document, "leftTree");
        if (leftTree != null) {
            /* MyNnd_Log("find leftTree", null); */

            var leftTree_div = MyNnd_GetChild(leftTree, "DIV", 1);
            var leftTree_app_tree = MyNnd_GetChild(leftTree_div, "app-tree", 0);
            var ztree = MyNnd_GetChild(leftTree_app_tree, "UL", 0);

            root.chapters = new Array();

            for (var i = 0; i < ztree.childNodes.length; i++) {
                var li = ztree.childNodes[i];
                if (li.nodeName == "LI") {
                    root.chapters[root.chapters.length++] = MyNnd_GetChapterNode(li);
                }
            }

            return root;
        }
    } catch (err) {
        alert(err.message);
    }
    return null;
}

function MyNnd_GetLeafChapterNode(li, chapterPath, chapterLeafs) {
    var li_span = MyNnd_GetChild(li, "SPAN", 0);
    var li_a = MyNnd_GetChild(li, "A", 0);
    var li_ul = MyNnd_GetChild(li, "UL", 0);
    var span = MyNnd_GetChild(li_a, "SPAN", 1);

    var name = span.innerHTML.trim();

    chapterPath = chapterPath + "---##---" + name;

    if (li_ul != null && li_ul.childNodes.length > 0) {
        for (var i = 0; i < li_ul.childNodes.length; i++) {
            var child = li_ul.childNodes[i];
            if (child.nodeName == "LI") {
                MyNnd_GetLeafChapterNode(child, chapterPath, chapterLeafs);
            }
        }
    } else {
        var chapterLeafPath = chapterPath;
        /* MyNnd_Log("chapterLeafPath : " + chapterLeafPath, null); */
        var chapterLeaf = new Object();
        chapterLeaf.path = chapterLeafPath;
        chapterLeaf.li_a = li_a;
        chapterLeafs[chapterLeafs.length++] = chapterLeaf;
    }
}

function MyNnd_GetLeafChapter() {
    /* MyNnd_Log("MyNnd_GetLeafChapter", null); */
    try {
        var leftTree = MyNnd_Find_Class(document, "leftTree");
        if (leftTree != null) {
            /* MyNnd_Log("find leftTree", null); */

            var leftTree_div = MyNnd_GetChild(leftTree, "DIV", 1);
            var leftTree_app_tree = MyNnd_GetChild(leftTree_div, "app-tree", 0);
            var ztree = MyNnd_GetChild(leftTree_app_tree, "UL", 0);

            var chapterLeafs = new Array();

            var chapterPath = "";

            for (var i = 0; i < ztree.childNodes.length; i++) {
                var li = ztree.childNodes[i];
                if (li.nodeName == "LI") {
                    MyNnd_GetLeafChapterNode(li, chapterPath, chapterLeafs);
                }
            }

            return chapterLeafs;
        }
    } catch (err) {
        alert(err.message);
    }
}

function MyNnd_FindNextLeafChapter(currChapterPath) {
    var chapterLeafs = MyNnd_GetLeafChapter();
    for (var i = 0; i < chapterLeafs.length; i++) {
        var chapterLeaf = chapterLeafs[i];
        if (currChapterPath == "") {
            return chapterLeaf;
        } else {
            if (currChapterPath == chapterLeaf.path) {
                currChapterPath = "";
            }
        }
    }
    return null;
}

function MyNnd_FindLeafChapter(currChapterPath) {
    var chapterLeafs = MyNnd_GetLeafChapter();
    for (var i = 0; i < chapterLeafs.length; i++) {
        var chapterLeaf = chapterLeafs[i];
        if (currChapterPath == chapterLeaf.path) {
            return chapterLeaf;
        }
    }
    return null;
}

function MyNnd_Test(currChapterPath) {
    MyNnd_ClickChapter(function () {
        var chapterLeaf = MyNnd_FindNextLeafChapter(currChapterPath);
        if (chapterLeaf != null) {
            MyNnd_Log("next chapterLeaf : " + chapterLeaf.path, null);
            MyNnd_MouseDown_Delay_Callback(chapterLeaf.li_a, 4000, function () {
                MyNnd_Log("222", null);
            });
        } else {
            MyNnd_Log("not find next chapterLeaf", null);
        }
    });
}

function MyNnd_ZhiHu_CatchOne(questionId, title, List_items, index) {
    MyNnd_Log("catch ----------------------- " + index + " / " + List_items.length, null);
    if (index < List_items.length) {
        var catchVotersNum = 0;
        for (var i = 0; i < List_items.length; i++) {
            var List_item = List_items[i];

            if (index == i) {
                List_item.setAttribute("style", "display: block;");

                /*
                 * var styleValue = List_item.getAttribute("style"); MyNnd_Log("styleValue " + styleValue, null);
                 *
                 * if (styleValue == "display: none;") { MyNnd_Log("not display", null); } else
                 */
                {
                    var ContentItem_meta = MyNnd_Find_Class(List_item, "ContentItem-meta");
                    if (ContentItem_meta != null) {
                        MyNnd_Log("find ContentItem_meta", null);

                        var Voters = MyNnd_Find_Class(ContentItem_meta, "Voters");
                        if (Voters != null) {
                            MyNnd_Log("find Voters", null);
                            MyNnd_Log("" + Voters.innerText.trim(), null);

                            var Voters_str = Voters.innerText.trim();
                            var VotersNum = Voters_str.substring(0, Voters_str.indexOf("人赞同了该回答")).trim().replace(new RegExp(",", "g"), "");
                            MyNnd_Log("VotersNum " + VotersNum, null);
                            catchVotersNum = parseInt(VotersNum);
                            /*
                             * if (parseInt(VotersNum) < 1000) { MyNnd_Log("will to delete ...", null); List_item.setAttribute("style", "display: none;"); }
                             */
                        }
                    }
                }
            } else {
                List_item.setAttribute("style", "display: none;");
            }

        }

        if (catchVotersNum < 1000) {
            MyNnd_ZhiHu_CatchPage(questionId, title, index + 1);
        } else {
            MyNnd_Delay(1000, function () {
                var info = new Object();
                info.questionId = questionId;
                info.title = title;
                info.index = index;
                info.catchVotersNum = catchVotersNum;
                MyNnd_CallCmd1("catchZhiXueOne", JSON.stringify(info), null);

                MyNnd_Delay(1000, function () {
                    MyNnd_ZhiHu_CatchPage(questionId, title, index + 1);
                });
            });
        }

    } else {
        /* MyNnd_Log("catch over !!!!!!!!!", null); */
        MyNnd_Log("index " + index + " is out", null);

        for (var i = 0; i < List_items.length; i++) {
            var List_item = List_items[i];
            List_item.setAttribute("style", "display: block;");
        }

        /*
         *
         *
         *
         *
         * MyNnd_Delay(3000, function() { MyNnd_CallCmd1("scrollToBottom", "", null); MyNnd_Delay(6000, function() { MyNnd_ZhiHu_CatchPage(questionId, title, index); }); });
         */

    }
}

function MyNnd_ZhiHu_CatchPage(questionId, title, index) {
    var QuestionAnswers_answers = MyNnd_Find_Id(document, "QuestionAnswers-answers");
    if (QuestionAnswers_answers != null) {
        MyNnd_Log("find QuestionAnswers_answers", null);

        var List = MyNnd_Find_Class(QuestionAnswers_answers, "List");
        if (List != null) {
            MyNnd_Log("find List", null);

            var List_header = MyNnd_GetChild(List, "DIV", 0);
            var List_Content = MyNnd_GetChild(List, "DIV", 1);

            var List_headerText = MyNnd_Find_Class(List_header, "List-headerText");
            if (List_headerText != null) {
                MyNnd_Log("find List_headerText", null);
                MyNnd_Log("" + List_headerText.innerText.trim(), null);
            }

            var List_items = MyNnd_Find_ClassList(List_Content, "List-item");
            if (List_items != null) {
                MyNnd_Log("List_items count ：" + List_items.length, null);
                MyNnd_ZhiHu_CatchOne(questionId, title, List_items, index);
            }
        }
    }
}

function MyNnd_ZhiHu_CatchStart() {
    MyNnd_Log("MyNnd_ZhiHu_CatchStart", null);

    var href = window.location.href;

    var questionId = href.substring(href.lastIndexOf("/") + 1);

    var QuestionHeader_title = MyNnd_Find_ClassExt(document, "QuestionHeader-title", 2, 2, 0);
    if (QuestionHeader_title != null) {
        var title = QuestionHeader_title.innerText.trim();
        MyNnd_Log("find QuestionHeader_title : " + title, null);

        MyNnd_ZhiHu_CatchPage(questionId, title, 0);
    }
}

function MyNnd_ZhiHu_Scroll() {
    var QuestionAnswers_answers = MyNnd_Find_Id(document, "QuestionAnswers-answers");
    if (QuestionAnswers_answers != null) {
        MyNnd_Log("find QuestionAnswers_answers", null);

        var List = MyNnd_Find_Class(QuestionAnswers_answers, "List");
        if (List != null) {
            MyNnd_Log("find List", null);

            var List_header = MyNnd_GetChild(List, "DIV", 0);
            var List_Content = MyNnd_GetChild(List, "DIV", 1);

            var List_headerText = MyNnd_Find_Class(List_header, "List-headerText");
            if (List_headerText != null) {
                MyNnd_Log("find List_headerText", null);
                MyNnd_Log("" + List_headerText.innerText.trim(), null);

                var Answers_str = List_headerText.innerText.trim();
                var AnswersNum = Answers_str.substring(0, Answers_str.indexOf("个回答")).trim().replace(new RegExp(",", "g"), "");
                MyNnd_Log("AnswersNum " + AnswersNum, null);

                var List_items = MyNnd_Find_ClassList(List_Content, "List-item");
                if (List_items != null) {
                    MyNnd_Log("List_items count ：" + List_items.length, null);
                    if (parseInt(AnswersNum) == List_items.length) {
                        MyNnd_Log("scroll over", null);
                        MyNnd_ZhiHu_CatchStart();
                    } else {
                        MyNnd_CallCmd1("scrollToBottom", "", null);
                        MyNnd_Delay(1000, function () {
                            MyNnd_ZhiHu_Scroll();
                        });
                    }
                }
            }

        }
    }
}

function MyNnd_ZhiHu() {
    MyNnd_Log("tttttttttttt", null);
    try {
        /* MyNnd_ZhiHu_Scroll(); */
        MyNnd_ZhiHu_CatchStart();
    } catch (err) {
        alert(err.message);
    }
}

// MyNnd_Main();

//------------------------------ send msg -------------------------------------

function sendMessageToContentScriptByEvent(data) {
    var hiddenDiv = document.getElementById('myCustomEventDiv');
    hiddenDiv.innerText = data
    hiddenDiv.dispatchEvent(customEvent);
}

var customEvent = document.createEvent('Event');
customEvent.initEvent('myCustomEvent', true, true);

//------------------------------ rcv msg -------------------------------------

function initXxxxEventListen() {
    var hiddenDiv = document.getElementById('myXxxxEventDiv');
    if (!hiddenDiv) {
        hiddenDiv = document.createElement('div');
        hiddenDiv.style.display = 'none';
        hiddenDiv.id = 'myXxxxEventDiv';
        document.body.appendChild(hiddenDiv);
    }
    hiddenDiv.addEventListener('myXxxxEvent', function () {
        var eventData = document.getElementById('myXxxxEventDiv').innerText;
        var data = JSON.parse(eventData);
        if (data.cmd == 'apiback') {
            eval(data.cb + "(data.data);");
        } else if (data.cmd == 'uploadback') {
            eval(data.cb + "(data.data, data.cbobj);");
        }
    });
}

initXxxxEventListen();


// // 通过postMessage调用content-script
// function invokeContentScript(code)
// {
// 	window.postMessage({cmd: 'invoke', code: code}, '*');
// }
// // 发送普通消息到content-script
// function sendMessageToContentScriptByPostMessage(data)
// {
// 	window.postMessage({cmd: 'message', data: data}, '*');
// }
//
// // 通过DOM事件发送消息给content-script
// (function() {
// 	var customEvent = document.createEvent('Event');
// 	customEvent.initEvent('myCustomEvent', true, true);
// 	// 通过事件发送消息给content-script
// 	function sendMessageToContentScriptByEvent(data) {
// 		data = data || '你好，我是injected-script!';
// 		var hiddenDiv = document.getElementById('myCustomEventDiv');
// 		hiddenDiv.innerText = data
// 		hiddenDiv.dispatchEvent(customEvent);
// 	}
// 	window.sendMessageToContentScriptByEvent = sendMessageToContentScriptByEvent;
// })();

var Nnd_FindTask = 30030;

function nnd_doPost(handleId, data, cbname) {
    sendMessageToContentScriptByEvent(JSON.stringify({
        cmd: "api",
        cb: cbname,
        data: JSON.stringify({
            handleId: handleId,
            data: JSON.stringify(data)
        })
    }));
}


function nnd_upload(filestr, path, type, cbname, cbobj) {
    sendMessageToContentScriptByEvent(JSON.stringify({
        cmd: "upload",
        cb: cbname,
        cbobj: cbobj,
        data: {
            filestr: filestr,
            path: path,
            type: type
        }
    }));
}

// function nnd_doPost(handleId, data) {
//     console.log('nnd_doPost ' + handleId);
//     console.log(data);
//     $.post({
//         url: "http://192.168.31.154/zjyk/logic/api",
//         contentType: "application/json",
//         data: JSON.stringify({
//             handleId: handleId,
//             data: JSON.stringify(data)
//         }),
//         success: function (data, status) {
//             if (status == "success") {
//                 var reault = data;
//                 if (reault.code == 200) {
//                     try {
//                         var obj = JSON.parse(reault.data);
//                         alert(obj);
//                         // load();
//                     } catch (e) {
//                         alert(e);
//                     }
//                 } else {
//                     alert(reault.msg);
//                 }
//             } else {
//                 alert(doPost + "失败！");
//             }
//         }
//     });
// }


function nnd_catch() {
    // alert(document.body.innerHTML);
    console.log(location.href);
    if (location.href.startsWith('https://www.zhixue.com/paperfresh/dist/#/mylib/paperEdit/')) {
        var result = MyNnd_GetNamePhase2();
        if (result) {
            // alert(ret.name);
            nnd_doPost(Nnd_FindTask, {
                group: result.phase + result.subject,
                name: result.name
            }, 'nnd_checkRepeat2');
        }
    }
}

function nnd_checkRepeat2(data) {
    var reault = JSON.parse(data);
    if (reault.code == 200) {
        var response = JSON.parse(reault.data);
        if (response.task) {
            alert('该卷已经抓取过（可能未抓取成功），将重新抓取？');
            console.log("开始抓取。。。");
            MyNnd_CatchOnePage2();
        } else {
            console.log("开始抓取。。。");
            MyNnd_CatchOnePage2();
        }
    }
}

function resultProblems2(info, currPage, problems, htmlStr) {
    var phase = info.phase;
    var subject = info.subject;
    var material = info.material;
    var currChapterPath = info.chapterPath;
    var diff = info.diff;
    var area = info.area;
    var year = info.year;

    var materialpath = material.replaceAll("/", "").replaceAll("\\", "").replaceAll("*", "").replaceAll(":", "");
    var jsonRawPath = "/juan/" + phase + subject + "/" + materialpath + "/main.json.raw";
    console.log(jsonRawPath);
    nnd_upload(JSON.stringify(problems), jsonRawPath, '', 'nnd_uploadOver2', '');
}

function nnd_uploadOver2(reault, cbobj) {
    if (reault.code == 200) {
        alert('xxxxxx')
    }
}

function nnd_addbtn() {
    var panel = document.createElement('div');
    panel.style.position = 'fixed';
    panel.style.top = '100px';
    panel.style.right = '100px';
    panel.style.zIndex = 99;
    panel.innerHTML = '<button onclick="nnd_catch()">抓取</button>';
    document.body.appendChild(panel);
}

console.log('goin inject');


nnd_addbtn();

