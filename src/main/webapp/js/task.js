function formatType(val, row, index) {
    var type = parseInt(val);
    if (type == 1)
        return '<span style="color:orangered;">作业</span>';
    else if (type == 2)
        return '<span style="color:blue;">课程</span>';
    else
        return '<span style="color:grey;">未知</span>';
}


function formatJobType(val, row, index) {
    if (row.type == 1) {
        var jobType = parseInt(val);
        if (jobType == 1)
            return '<span style="color:orangered;">仅选择题</span>';
        else if (jobType == 2)
            return '<span style="color:blue;">混合题型</span>';
        else
            return '<span style="color:grey;">默认</span>';
    } else {
        return '<span style="color:grey;"></span>';
    }
}

function formatTimes(val, row, index) {
    var times = parseInt(val);
    if (times <= 0)
        return '<span style="color:grey;">' + '' + '</span>';
    else if (times < 10)
        return '<span style="color:black;">' + times + '</span>';
    else if (times < 100)
        return '<span style="color:blue;">' + times + '</span>';
    else
        return '<span style="color:red;">' + times + '</span>';
}


function formatOpt(val, row, index) {
    return '<a href="../td/' + row.id + '" class="easyui-linkbutton" target="_blank">页面详情</a>'
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
        type: $('#type').val(),
        jobType: $('#jobType').val(),
        timesForWeb: $('#timesForWeb').val(),
        beginTime: $('#beginTime').val(),
        endTime: $('#endTime').val(),
        keywords: $('#keywords').val(),
    });
}


function openAddItem() {
    $('#dlg').dialog('open').dialog('setTitle', '添加');
    $('#fm').form('clear');
}

function doAddItem() {
    // var data = {
    //     pkgName: $("#pkgName").val(),
    //     channel: $("#channel").val(),
    //     versionCode: $("#versionCode").val(),
    //     versionName: $("#versionName").val(),
    //     size: 100,
    //     apkUrl: 'kkkkkkk',
    // }
    // $.post({
    //     url: "./set",
    //     contentType: "application/json",
    //     data: JSON.stringify(data),
    //     success: function (result) {
    //         $('#dlg').dialog('close');
    //         $('#dg').datagrid('reload');
    //     }
    // });


    var formData = new FormData();
    // formData.append("pkgName", $.trim($("#pkgName").val()));
    // formData.append("channel", $.trim($("#channel").val()));
    // formData.append("versionCode", $.trim($("#versionCode").val()));
    // formData.append("versionName", $.trim($("#versionName").val()));
    formData.append("apkFile", $('#apkFile').next().find('input[type=file]')[0].files[0]);

    $.ajax({
        url: './submit',
        dataType: 'json',
        type: 'POST',
        async: true,
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            $('#dlg').dialog('close');
            $('#dg').datagrid('reload');
        }
    });
}

function openEditItem() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $('#dlg').dialog('open').dialog('setTitle', '修改');
        $('#fm').form('load', row);
    } else {
        alert("请先选中一行");
    }
}

function doDeleteItem() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $.messager.confirm('提示', '确定要删除吗?', function (r) {
            if (r) {
                $.post("./delete",
                    {
                        pkgName: row.pkgName,
                        channel: row.channel,
                    },
                    function (data, status) {
                        $('#dg').datagrid('reload');
                    });
            }
        });
    } else {
        alert("请先选中一行");
    }
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
