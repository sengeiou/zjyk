function doSearch() {
    var opts = $("#dg").datagrid("options");
    opts.url = "./search";
    $('#dg').datagrid('load', {
        keywords: $('#keywords').val(),
    });
}

function doAddItem() {

    var formData = new FormData();
    formData.append("id", $.trim($("#id").val()));
    formData.append("chapter", $.trim($("#chapter").val()));
    formData.append("point", $.trim($("#point").val()));
    formData.append("difficulty", $.trim($("#difficulty").val()));
    formData.append("importance", $.trim($("#importance").val()));
    formData.append("frequency", $.trim($("#frequency").val()));
    formData.append("note", $.trim($("#note").val()));

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


function openAddItem() {
    $('#dlg').dialog('open').dialog('setTitle', '添加');
    $('#fm').form('clear');
}


function openEditItem() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $('#dlg').dialog('open').dialog('setTitle', '修改');
        $('#fm').form('clear');
        $('#fm').form('load', row);
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


if ($.fn.datagrid) {
    $.fn.datagrid.defaults.pageSize = 500;
    $.fn.datagrid.defaults.pageList = [100, 500, 1000];
}

if ($.fn.pagination) {
    $.fn.pagination.defaults.beforePageText = '第';
    $.fn.pagination.defaults.afterPageText = '页，共 {pages} 页';
    $.fn.pagination.defaults.displayMsg = '当前显示 {from} 到 {to} 条，共 {total} 条记录';
}
