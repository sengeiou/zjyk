function formatId(val, row, index) {
    return val + ' <button type="button" onclick="doExportXls(' + "'" + val + "'" + ')">导出</button>';
}

function doExportXls(id) {
    var formData = new FormData();
    formData.append("id", id);
    $.post({
        url: './exportXls',
        contentType: "application/json",
        async: true,
        data: formData,
        processData: false,
        contentType: false,
        success: function (data, status) {
            if (status == "success") {
                window.open(data);
            } else {
                alert("导出失败！！！");
            }
        }
    });
}

function formatTopic(val, row, index) {
    var topics = val;
    return '<span>' + topics.length + ' 个题</span>';
}

function doSearch() {
    $('#dg').datagrid('load', {
        keywords: $('#keywords').val(),
    });
}

function doAddItem() {
    var topics = saveTopics();
    if (topics != null) {
        var formData = new FormData();
        formData.append("id", $.trim($("#id").val()));
        formData.append("name", $.trim($("#name").val()));
        formData.append("time", $.trim($("#time").val()));
        formData.append("note", $.trim($("#note").val()));
        formData.append("topics", JSON.stringify(topics));

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
                doSearch();
            }
        });
    }
}


function openAddItem() {
    $('#dlg').dialog('open').dialog('setTitle', '添加');
    $('#fm').form('clear');
    loadTopics([]);
}


function openEditItem() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $('#dlg').dialog('open').dialog('setTitle', '修改');
        $('#fm').form('clear');
        $('#fm').form('load', row);
        $('#time').datetimebox('setValue', dateFormat("MM/dd/yyyy HH:mm", new Date(row.time)));
        loadTopics(row.topics);
    } else {
        alert("请先选中一行");
    }
}

function doDeleteItem() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $.post("./delete",
            {
                id: row.id,
            },
            function (data, status) {
                doSearch();
            });
    } else {
        alert("请先选中一行");
    }
}


function perPaparClear() {
    loadTopics([]);
}

function perPaparA() {
    loadTopics([
        {code: '1', type: 0, allScore: 5, difficulty: ''},
        {code: '2', type: 0, allScore: 5, difficulty: ''},
        {code: '3', type: 0, allScore: 5, difficulty: ''},
        {code: '4', type: 0, allScore: 5, difficulty: ''},
        {code: '5', type: 0, allScore: 5, difficulty: ''},
        {code: '6', type: 0, allScore: 5, difficulty: ''},
        {code: '7', type: 0, allScore: 5, difficulty: ''},
        {code: '8', type: 0, allScore: 5, difficulty: ''},
        {code: '9', type: 0, allScore: 5, difficulty: ''},
        {code: '10', type: 0, allScore: 5, difficulty: ''},
        {code: '11', type: 0, allScore: 5, difficulty: ''},
        {code: '12', type: 0, allScore: 5, difficulty: ''},
        {code: '13', type: 2, allScore: 5, difficulty: ''},
        {code: '14', type: 2, allScore: 5, difficulty: ''},
        {code: '15', type: 2, allScore: 5, difficulty: ''},
        {code: '16', type: 2, allScore: 5, difficulty: ''},
        {code: '17-1', type: 3, allScore: 4, difficulty: ''},
        {code: '17-2', type: 3, allScore: 6, difficulty: ''},
        {code: '18-1', type: 3, allScore: 4, difficulty: ''},
        {code: '18-2', type: 3, allScore: 8, difficulty: ''},
        {code: '19-1', type: 3, allScore: 4, difficulty: ''},
        {code: '19-2', type: 3, allScore: 8, difficulty: ''},
        {code: '20-1', type: 3, allScore: 4, difficulty: ''},
        {code: '20-2', type: 3, allScore: 8, difficulty: ''},
        {code: '21-1', type: 3, allScore: 4, difficulty: ''},
        {code: '21-2', type: 3, allScore: 8, difficulty: ''},
        {code: '22-1', type: 3, allScore: 4, difficulty: ''},
        {code: '22-2', type: 3, allScore: 8, difficulty: ''}]);
}

function perPaparB() {
    loadTopics([
        {code: '1', type: 0, allScore: 5, difficulty: ''},
        {code: '2', type: 0, allScore: 5, difficulty: ''},
        {code: '3', type: 0, allScore: 5, difficulty: ''},
        {code: '4', type: 0, allScore: 5, difficulty: ''},
        {code: '5', type: 0, allScore: 5, difficulty: ''},
        {code: '6', type: 0, allScore: 5, difficulty: ''},
        {code: '7', type: 0, allScore: 5, difficulty: ''},
        {code: '8', type: 0, allScore: 5, difficulty: ''},
        {code: '9', type: 1, allScore: 5, difficulty: ''},
        {code: '10', type: 1, allScore: 5, difficulty: ''},
        {code: '11', type: 1, allScore: 5, difficulty: ''},
        {code: '12', type: 1, allScore: 5, difficulty: ''},
        {code: '13', type: 2, allScore: 5, difficulty: ''},
        {code: '14', type: 2, allScore: 5, difficulty: ''},
        {code: '15', type: 2, allScore: 5, difficulty: ''},
        {code: '16', type: 2, allScore: 5, difficulty: ''},
        {code: '17-1', type: 3, allScore: 4, difficulty: ''},
        {code: '17-2', type: 3, allScore: 6, difficulty: ''},
        {code: '18-1', type: 3, allScore: 4, difficulty: ''},
        {code: '18-2', type: 3, allScore: 8, difficulty: ''},
        {code: '19-1', type: 3, allScore: 4, difficulty: ''},
        {code: '19-2', type: 3, allScore: 8, difficulty: ''},
        {code: '20-1', type: 3, allScore: 4, difficulty: ''},
        {code: '20-2', type: 3, allScore: 8, difficulty: ''},
        {code: '21-1', type: 3, allScore: 4, difficulty: ''},
        {code: '21-2', type: 3, allScore: 8, difficulty: ''},
        {code: '22-1', type: 3, allScore: 4, difficulty: ''},
        {code: '22-2', type: 3, allScore: 8, difficulty: ''}]);
}

function loadTopics(topics) {
    if (topics.length > 0) {
        for (var i = 0; i < topics.length; i++) {
            delete topics[i].point;
        }
        $.post({
            url: "../logic/api",
            contentType: "application/json",
            data: JSON.stringify({
                handleId: ExamPaperDetail,
                data: JSON.stringify({
                    topics: topics
                })
            }),
            success: function (data, status) {
                if (status == "success") {
                    var reault = data;
                    if (reault.code == 200) {
                        try {
                            var obj = JSON.parse(reault.data);
                            loadTopicsImpl(obj.topics);
                        } catch (e) {
                            alert(e);
                        }
                    } else {
                        alert(reault.msg);
                    }
                } else {
                    alert("加载失败！");
                }
            }
        });
    } else {
        loadTopicsImpl(topics);
    }
}

function loadTopicsImpl(topics) {
    var nodes = document.getElementsByClassName("topic");
    console.log(nodes.length);
    console.log(topics.length);
    for (var i = 0; i < nodes.length; i++) {
        var node = nodes[i];
        if (i < topics.length) {
            var topic = topics[i];
            $(node.getElementsByClassName("code")[0]).textbox('setValue', topic.code);
            $(node.getElementsByClassName("type")[0]).combobox('select', 1); // 解决为0时的BUG，非正常方式
            $(node.getElementsByClassName("type")[0]).combobox('select', topic.type);
            $(node.getElementsByClassName("allScore")[0]).textbox('setValue', topic.allScore);
            $(node.getElementsByClassName("difficulty")[0]).textbox('setValue', topic.difficulty);
            if (topic.pointId && topic.pointId.length > 0)
                $(node.getElementsByClassName("pointName")[0]).textbox('setValue', topic.point.point);
            else
                $(node.getElementsByClassName("pointName")[0]).textbox('setValue', '');
            $(node.getElementsByClassName("pointId")[0]).text(topic.pointId);
        } else {
            $(node.getElementsByClassName("code")[0]).textbox('setValue', '');
            $(node.getElementsByClassName("type")[0]).textbox('setValue', '');
            $(node.getElementsByClassName("allScore")[0]).textbox('setValue', '');
            $(node.getElementsByClassName("difficulty")[0]).textbox('setValue', '');
            $(node.getElementsByClassName("pointName")[0]).textbox('setValue', '');
            $(node.getElementsByClassName("pointId")[0]).text('');
        }
    }
}


function saveTopics() {
    var map = new Map();
    var topics = [];
    var nodes = document.getElementsByClassName("topic");
    for (var i = 0; i < nodes.length; i++) {
        var node = nodes[i];
        var topic = {};
        topic.code = $.trim($(node.getElementsByClassName("code")[0]).val());
        if (topic.code && topic.code.length > 0) {
            topic.type = $.trim($(node.getElementsByClassName("type")[0]).val());
            topic.allScore = $.trim($(node.getElementsByClassName("allScore")[0]).val());
            topic.difficulty = $.trim($(node.getElementsByClassName("difficulty")[0]).val());
            topic.pointId = $.trim($(node.getElementsByClassName("pointId")[0]).text());
            if (map.get(topic.code)) {
                alert(topic.code + " 题号重复！");
                topics = null;
                break;
            }
            map.set(topic.code, topic);
            topics[topics.length] = topic;
            if (!topic.type || topic.type.length == 0) {
                alert(topic.code + " 未选择题型！");
                topics = null;
                break;
            }
            if (!topic.allScore || topic.allScore.length == 0) {
                alert(topic.code + " 未设置分值！");
                topics = null;
                break;
            }
            if (!topic.difficulty || topic.difficulty.length == 0) {
                alert(topic.code + " 未设置难度！");
                topics = null;
                break;
            }
            if (!topic.pointId || topic.pointId.length == 0) {
                alert(topic.code + " 未选择考点！");
                topics = null;
                break;
            }
        }
    }
    return topics;
}


var currTopicIndex = 0;

function selectPoint(index) {
    currTopicIndex = index;
    $('#dlgs').dialog('open').dialog('setTitle', '选择');
}


function doSelectPoint() {
    var row = $('#dgs').datagrid('getSelected');
    if (row) {
        var node = document.getElementsByClassName("topic" + currTopicIndex)[0];
        $(node.getElementsByClassName("pointName")[0]).textbox('setValue', row.point);
        $(node.getElementsByClassName("pointId")[0]).text(row.id);
        $('#dlgs').dialog('close');
    } else {
        alert("请先选中一行");
    }
}


function doSearchs() {
    $('#dgs').datagrid('load', {
        keywords: $('#keywordss').val(),
    });
}


function doAddItems() {

    var formData = new FormData();
    formData.append("id", $.trim($("#id2s").val()));
    formData.append("chapter", $.trim($("#chapter2s").val()));
    formData.append("point", $.trim($("#point2s").val()));
    formData.append("difficulty", $.trim($("#difficulty2s").val()));
    formData.append("importance", $.trim($("#importance2s").val()));
    formData.append("frequency", $.trim($("#frequency2s").val()));
    formData.append("note", $.trim($("#note2s").val()));

    $.ajax({
        url: '../exampoint/submit',
        dataType: 'json',
        type: 'POST',
        async: true,
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            $('#dlg2s').dialog('close');
            doSearchs();
        }
    });
}


function openAddItems() {
    $('#dlg2s').dialog('open').dialog('setTitle', '添加');
    $('#fm2s').form('clear');
}


function openEditItems() {
    var row = $('#dgs').datagrid('getSelected');
    if (row) {
        $('#dlg2s').dialog('open').dialog('setTitle', '修改');
        $('#fm2s').form('clear');
        $('#fm2s').form('load', row);
    } else {
        alert("请先选中一行");
    }
}

function doDeleteItems() {
    var row = $('#dgs').datagrid('getSelected');
    if (row) {
        $.post("../exampoint/delete",
            {
                id: row.id,
            },
            function (data, status) {
                doSearchs();
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
