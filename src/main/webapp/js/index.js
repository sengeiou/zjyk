//
// var FindTask = 30030;
//
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
//
//
// nnd_doPost(FindTask, {
//     group: 'xxxxxx',
//     name: 'yyyyyyyyyyyyy'
// });


// function requestTask() {
//     console.log('requestTask');
//     $.post({
//         url: "http://192.168.31.154/zjyk/logic/api",
//         contentType: "application/json",
//         data: JSON.stringify({
//             handleId: 30037,
//             data: JSON.stringify({
//                 id: 'xxxxxxxxxx'
//             })
//         }),
//         success: function (data, status) {
//             if (status == "success") {
//                 var reault = data;
//                 if (reault.code == 200) {
//                     try {
//                         obj = JSON.parse(reault.data);
//                         // alert();
//                     } catch (e) {
//                         alert(e);
//                     }
//                 } else {
//                     alert(reault.msg);
//                 }
//             } else {
//                 alert("加载任务失败！");
//             }
//         }
//     });
// }
//
// console.log(location.href);
//
// // 监听 message 消息
// window.addEventListener('message', function (e) {
//     console.log(location.href);
//     console.log(e.source); // e.source 发送消息的窗口
//     console.log(e.origin); // e.origin 消息发向的网址
//     console.log(e.data);   // e.data   发送的消息
// }, false);
//
// // 父窗口打开一个子窗口
// // var openWindow = window.open('http://192.168.31.154/zjyk/', 'title');
//
// if (location.href.startsWith('http://localhost/')) {
//     // openWindow.postMessage('Nice to meet you!', 'http://test2.com');
// }
// 父窗口向子窗口发消息(第一个参数代表发送的内容，第二个参数代表接收消息窗口的url)


// requestTask();