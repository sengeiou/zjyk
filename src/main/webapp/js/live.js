function formatType(val, row, index) {
    var type = parseInt(val);
    if (type == 0)
        return '<span style="color:orangered;">私密</span>';
    else if (type == 1)
        return '<span style="color:green;">公开</span>';
    else if (type == 2)
        return '<span style="color:orangered;">私密可回放</span>';
    else if (type == 3)
        return '<span style="color:green;">公开可回放</span>';
    else
        return '<span style="color:grey;"></span>';
}

function formatUserIds(val, row, index) {
    var ret = "";
    for (var i = 0; i < val.length; i++) {
        if (i != val.length - 1)
            ret += val[i] + "<br/>";
        else
            ret += val[i];
    }
    return ret;
}

function onSearch(selectRowIndex) {
    var opts = $("#dg").datagrid("options");
    opts.url = "./getList";
    opts.onLoadSuccess = function (data) {
        console.log("selectRowIndex : " + selectRowIndex)
        // if (selectRowIndex) {
        //     $('#dg').datagrid('selectRow', selectRowIndex);
        // }
    };
    $("#dg").datagrid('load', {
        // type: $('input:radio[name="searchType"]:checked').val(),
        // invalid: $('input:radio[name="searchInvalid"]:checked').val(),
    });
}

function openAddItem() {
    $('#dlg').dialog('open').dialog('setTitle', '添加');
    $('#fm').form('clear');
    // $('#zhanPercent').numberbox('setValue', 100);
    // $('#lookCount').numberbox('setValue', 1000 + Math.round(Math.random() * 9000));
    $('input:radio[name="type"]').get(0).checked = true;
    // $('input:radio[name="editInvalid"]').get(0).checked = true;
}

function doAddItem() {

    var formData = new FormData();
    formData.append("id", $.trim($("#id").val()));
    formData.append("actorId", $.trim($("#actorId").val()));
    formData.append("name", $.trim($("#name").val()));
    formData.append("des", $.trim($("#des").val()));
    formData.append("coverFile", $('#coverFile').next().find('input[type=file]')[0].files[0]);
    formData.append("videoFile", $('#videoFile').next().find('input[type=file]')[0].files[0]);
    formData.append("taskId", $.trim($("#taskId").val()));
    formData.append("type", $('input:radio[name="type"]:checked').val());
    formData.append("userIds", $.trim($("#userIds").val()));
    formData.append("startTime", $.trim($("#startTime").val()));
    // formData.append("cover", $.trim($("#cover").val()));
    // formData.append("video", $.trim($("#video").val()));
    // formData.append("zhanPercent", $.trim($("#zhanPercent").val()));
    // formData.append("lookCount", $.trim($("#lookCount").val()));
    // formData.append("sort", $.trim($("#sort").val()));
    // formData.append("invalid", $('input:radio[name="editInvalid"]:checked').val());

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
            onSearch();
        }
    });
}

function openEditItem() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        $('#dlg').dialog('open').dialog('setTitle', '修改');
        $('#fm').form('clear');
        $('#fm').form('load', row);
        $('input:radio[name="type"]').get(parseInt(row.type)).checked = true;
        // $('input:radio[name="editInvalid"]').get(parseInt(row.invalid)).checked = true;
        $('#startTime').datetimebox('setValue', dateFormat("MM/dd/yyyy HH:mm", new Date(row.startTime)));
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
                $('#dlg').dialog('close');
                onSearch();
            });
    } else {
        alert("请先选中一行");
    }
}