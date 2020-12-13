$(document).ready(function () {
    console.log('ready');
    $("#startTime").text("启动时间：" + dateFormat("yyyy年MM月dd日 HH:mm:ss", new Date(startTime)));
    $("#startTimeLen").text("启动时长：" + timeFormat(currentTimeMillis() - startTime));
    $("#switchButton").text(stop ? "服务器已暂停" : "服务器正在运行");
});

function switchStopState() {
    console.log('switchStopState');
    $.post({
        url: "./swapStop",
        contentType: "application/json",
        success: function (data, status) {
            if (status == "success") {
                stop = data;
                $("#switchButton").text(stop ? "服务器已暂停" : "服务器正在运行");
            } else {
                showMessage("失败！");
            }
        }
    });
}