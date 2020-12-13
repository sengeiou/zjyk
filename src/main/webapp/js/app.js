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