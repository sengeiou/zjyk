function formatUserType(val, row, index) {
    var type = parseInt(val);
    if (type == 1)
        return '<span style="color:red;">超级管理员</span>';
    else if (type == 2)
        return '<span style="color:green;">管理员</span>';
    else if (type == 3)
        return '<span style="color:deeppink;">教师</span>';
    else if (type == 4)
        return '<span style="color:blue;">学生</span>';
    else
        return '<span style="color:grey;"></span>';
}


function formatSex(val, row, index) {
    var type = parseInt(val);
    if (type == 1)
        return '<span style="color:red;">女生</span>';
    else if (type == 2)
        return '<span style="color:blue;">男生</span>';
    else
        return '<span style="color:grey;"></span>';
}


function formatGrade(val, row, index) {
    var type = parseInt(val);
    if (type == 7)
        return '<span style="color:green;">初一</span>';
    else if (type == 8)
        return '<span style="color:green;">初二</span>';
    else if (type == 9)
        return '<span style="color:green;">初三</span>';
    else if (type == 10)
        return '<span style="color:blue;">高一</span>';
    else if (type == 11)
        return '<span style="color:blue;">高二</span>';
    else if (type == 12)
        return '<span style="color:blue;">高三</span>';
    else
        return '<span style="color:grey;"></span>';
}


function formatKeywords(val, row, index) {
    if (val) {
        for (var i = 0; i < keywordArray.length; i++) {
            var keyword = keywordArray[i];
            val = val.replace(new RegExp(keyword, "g"), '<span style="background-color:yellow;">' + keyword + '</span>');
        }
    }
    return val;
}

var keywordArray = new Array();


function parseKeywordArray(keywords) {
    keywordArray.length = 0;
    // console.log(keywords);
    if (keywords.length > 0) {
        var tmpArray = keywords.split(new RegExp("\\s+", "g"));
        // console.log(tmpArray);
        for (var i = 0; i < tmpArray.length; i++) {
            var keyword = tmpArray[i];
            if (keyword.length > 0) {
                // console.log(keyword);
                keywordArray[keywordArray.length] = keyword;
            }
        }
    }
    console.log(keywordArray);
}


function doSearch() {
    parseKeywordArray($('#keywords').val());
    var opts = $("#dg").datagrid("options");
    opts.url = "./search";
    $('#dg').datagrid('load', {
        userId: $('#userId').val(),
        action: $('#action').val(),
        target: $('#target').val(),
        beginTime: $('#beginTime').val(),
        endTime: $('#endTime').val(),
        keywords: $('#keywords').val(),
    });
}


if ($.fn.datagrid) {
    $.fn.datagrid.defaults.pageSize = 500;
    $.fn.datagrid.defaults.pageList = [100, 500, 1000];
}

if ($.fn.pagination) {
    $.fn.pagination.defaults.beforePageText = '第';
    $.fn.pagination.defaults.afterPageText = '页，共 {pages} 页';
    $.fn.pagination.defaults.displayMsg = '当前显示 {from} 到 {to} 条，共 {total} 条记录';
}

if ($.fn.datebox) {
    $.fn.datebox.defaults.formatter = function (date) {
        return dateFormat("yyyy年MM月dd日", date);
    }
    $.fn.datebox.defaults.parser = function (s) {
        var t = dateParse("yyyy年MM月dd日", s);
        if (!isNaN(t)) {
            return new Date(t);
        } else {
            return new Date();
        }
    }
}
