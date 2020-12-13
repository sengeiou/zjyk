function formatStudentName(val, row, index) {
    return val + ' <button type="button" onclick="doExportXls(' + "'" + val + "'" + ')">导出</button>';
}

function doExportXls(name) {
    var formData = new FormData();
    formData.append("name", name);
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

function formatPaper(val, row, index) {
    var paper = val;
    return '<span>' + paper.name + '</span>';
}

function formatTimeMin(val, row, index) {
    return '<span>' + val + ' 分钟</span>';
}

function formatMindset(val, row, index) {
    var type = parseInt(val);
    if (type == 0)
        return '<span style="color:blue;">一般般</span>';
    else if (type == 1)
        return '<span style="color:orangered;">紧张</span>';
    else if (type == 2)
        return '<span style="color:green;">放松</span>';
    else
        return '<span style="color:grey;"></span>';
}

function formatTopic(val, row, index) {
    var topics = val;
    return '<span>' + topics.length + ' 个题</span>';
}


function formatOpt(val, row, index) {
    return '<a href="../examdp/' + row.id + '" class="easyui-linkbutton" target="_blank">点评</a>'
        + '<span> | </span>'
        + '<a href="../examkq/' + row.id + '" class="easyui-linkbutton" target="_blank">考情分析</a>'
}


function doSearch() {
    var opts = $("#dg").datagrid("options");
    opts.url = "./search";
    $('#dg').datagrid('load', {
        keywords: $('#keywords').val(),
    });
}

function doAddItem() {
    var paperId = $.trim($("#paperId").text());
    if (!paperId || paperId.length == 0) {
        alert("请选择考卷！");
        return;
    }
    var topics = saveTopics();
    if (topics != null) {
        var formData = new FormData();
        formData.append("id", $.trim($("#id").val()));
        formData.append("studentName", $.trim($("#studentName").val()));
        formData.append("paperId", $.trim($("#paperId").text()));
        formData.append("selectTime", $.trim($("#selectTime").val()));
        formData.append("gapTime", $.trim($("#gapTime").val()));
        formData.append("answerTime", $.trim($("#answerTime").val()));
        formData.append("mindset", $.trim($("#mindset").val()));
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
    $('#paperId').text('');
    loadTopics([], []);
}


function openEditItem() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $('#dlg').dialog('open').dialog('setTitle', '修改');
        $('#fm').form('clear');
        $('#fm').form('load', row);
        $('#paperName').textbox('setValue', row.paper.name);
        $('#paperId').text(row.paperId);
        loadTopics(row.paper.topics, row.topics);
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


function loadTopics(topics, errorTopics) {
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
                            loadTopicsImpl(obj.topics, errorTopics);
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
        loadTopicsImpl(topics, errorTopics);
    }
}

function loadTopicsImpl(topics, errorTopics) {

    var errorTopicsMap = new Map();
    for (var i = 0; i < errorTopics.length; i++) {
        var errorTopic = errorTopics[i];
        errorTopicsMap.set(errorTopic.code, errorTopic);
    }

    var nodes = document.getElementsByClassName("topic");
    console.log(nodes.length);
    console.log(topics.length);
    console.log(errorTopics.length);
    for (var i = 0; i < nodes.length; i++) {
        var node = nodes[i];
        if (i < topics.length) {
            var topic = topics[i];
            $(node.getElementsByClassName("code")[0]).textbox('setValue', topic.code);
            $(node.getElementsByClassName("allScore")[0]).textbox('setValue', topic.allScore);
            var errorTopic = errorTopicsMap.get(topic.code);
            if (errorTopic) {
                $(node.getElementsByClassName("gotScore")[0]).textbox('setValue', errorTopic.gotScore);
                $(node.getElementsByClassName("errorType")[0]).combobox('select', 1); // 解决为0时的BUG，非正常方式
                $(node.getElementsByClassName("errorType")[0]).combobox('select', errorTopic.errorType);
            } else {
                $(node.getElementsByClassName("gotScore")[0]).textbox('setValue', '');
                $(node.getElementsByClassName("errorType")[0]).textbox('setValue', '');
            }
            $(node.getElementsByClassName("difficulty")[0]).textbox('setValue', topic.difficulty);
            if (topic.pointId && topic.pointId.length > 0)
                $(node.getElementsByClassName("pointName")[0]).textbox('setValue', topic.point.point);
            else
                $(node.getElementsByClassName("pointName")[0]).textbox('setValue', '');
        } else {
            $(node.getElementsByClassName("code")[0]).textbox('setValue', '');
            $(node.getElementsByClassName("allScore")[0]).textbox('setValue', '');
            $(node.getElementsByClassName("gotScore")[0]).textbox('setValue', '');
            $(node.getElementsByClassName("errorType")[0]).textbox('setValue', '');
            $(node.getElementsByClassName("difficulty")[0]).textbox('setValue', '');
            $(node.getElementsByClassName("pointName")[0]).textbox('setValue', '');
        }
    }
}


function saveTopics() {
    var topics = [];
    var nodes = document.getElementsByClassName("topic");
    for (var i = 0; i < nodes.length; i++) {
        var node = nodes[i];
        var topic = {};
        topic.code = $.trim($(node.getElementsByClassName("code")[0]).val());
        if (topic.code && topic.code.length > 0) {
            topic.gotScore = $.trim($(node.getElementsByClassName("gotScore")[0]).val());
            if (topic.gotScore && topic.gotScore.length > 0) {
                topic.errorType = $.trim($(node.getElementsByClassName("errorType")[0]).val());
                topics[topics.length] = topic;
                var allScore = $.trim($(node.getElementsByClassName("allScore")[0]).val());
                if (parseInt(topic.gotScore) >= parseInt(allScore)) {
                    alert(topic.code + " 得分填写错误（满分的情况不用填写）！");
                    topics = null;
                    break;
                }
                if (!topic.errorType || topic.errorType.length == 0) {
                    alert(topic.code + " 未选择错误类型！");
                    topics = null;
                    break;
                }
            }
        }
    }
    return topics;
}


function selectPaper() {
    $('#dlgs').dialog('open').dialog('setTitle', '选择');
}


function doSelectPaper() {
    var row = $('#dgs').datagrid('getSelected');
    if (row) {
        $('#paperName').textbox('setValue', row.name);
        $('#paperId').text(row.id);
        loadTopics(row.topics, []);
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


if ($.fn.datagrid) {
    $.fn.datagrid.defaults.pageSize = 500;
    $.fn.datagrid.defaults.pageList = [100, 500, 1000];
}

if ($.fn.pagination) {
    $.fn.pagination.defaults.beforePageText = '第';
    $.fn.pagination.defaults.afterPageText = '页，共 {pages} 页';
    $.fn.pagination.defaults.displayMsg = '当前显示 {from} 到 {to} 条，共 {total} 条记录';
}
