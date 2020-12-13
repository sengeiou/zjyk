$(document).ready(function () {
    console.log('ready');
    // request();
});

var uploadFiles = new Array();

$(function () {
    // 阻止浏览器默认行为。
    $(document).on({
        dragleave: function (e) { // 拖离
            e.preventDefault();
        },
        drop: function (e) { // 拖后放
            e.preventDefault();
        },
        dragenter: function (e) { // 拖进
            e.preventDefault();
        },
        dragover: function (e) { // 拖来拖去
            e.preventDefault();
        }
    });

    var box = document.getElementById('dropbox'); // 拖拽区域

    box.addEventListener("drop", function (e) {
        e.preventDefault(); // 取消默认浏览器拖拽效果
        var fileList = e.dataTransfer.files; // 获取文件对象
        // 检测是否是拖拽文件到页面的操作
        if (fileList.length == 0) {
            return false;
        }
        // uploadFiles = new Array();
        if (uploadFiles.length > 0) {
            alert("请等待上传完成");
            return false;
        }
        AddFiles(fileList);
        Upload();
    }, false);
});

function onc() {
    var files = document.getElementById("file").files;
    if (files.length == 0) {
        return;
    }
    // uploadFiles = new Array();
    if (uploadFiles.length > 0) {
        alert("请等待上传完成");
        return;
    }
    AddFiles(files);
    Upload();
}

function AddFiles(files) {
    var errstr = "";
    for (var i = 0; i < files.length; i++) {
        var filename = files[i].name;
        var isfind = false;
        for (var j = 0; j < uploadFiles.length; j++) {
            if (uploadFiles[j].name == filename) {
                isfind = true;
                break;
            }
        }

        var myarray = new Array('.jpg', '.jpeg', '.png');

        if ($.inArray(getSuffix(filename), myarray) == -1) {
            errstr += filename + "/";
            continue;
        }
        if (isfind == false) {
            uploadFiles.push(files[i]);
        }
    }

    if (errstr != "") {
        alert("文件格式错误:" + errstr);
    }

    var fileliststring = "";

    for (var j = 0; j < uploadFiles.length; j++) {
        fileliststring += (uploadFiles[j].name + " 大小：" + formatSize(uploadFiles[j].size) + "<\/br>");
    }

    document.getElementById("fileliststring").innerHTML = fileliststring;
}

function Upload() {
    AddFiles(new Array());
    if (uploadFiles.length <= 0) {
        return;
    }

    uploadcount = uploadFiles.length;

    // var url = window.location.protocol + "//" + window.location.hostname + "/pair/cloud";
    var url = "./file/cloud";
    // FormData 对象

    get_filemd5sum(uploadFiles[0], function (md5) {
        // var path = "/files/" + md5.substring(md5.length - 2) + "/" + md5 + getSuffix(uploadFiles[0].name);

        var form = new FormData();
        form.append("file", uploadFiles[0]);
        form.append("md5", md5);
        form.append("suffix", getSuffix(uploadFiles[0].name));
        // XMLHttpRequest 对象
        var xhr = new XMLHttpRequest();
        xhr.open("post", url, true);
        xhr.ontimeout = function (event) {
            alert("请求超时！");
        };
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                if (xhr.status == 200) {
                    uploadFiles.splice(0, 1);
                    Upload();
                } else {
                    alert("请求错误：" + xhr.status);
                }
            }
        };
        xhr.send(form);
    });

}

function get_filemd5sum(ofile, callback) {
    var file = ofile;
    var tmp_md5;
    var blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice;
    // file = this.files[0],
    var chunkSize = 8097152; // Read in chunks of 2MB
    var chunks = Math.ceil(file.size / chunkSize);
    var currentChunk = 0;
    var spark = new SparkMD5.ArrayBuffer();
    var fileReader = new FileReader();

    fileReader.onload = function (e) {
        // console.log('read chunk nr', currentChunk + 1, 'of', chunks);
        spark.append(e.target.result); // Append array buffer
        currentChunk++;
        var md5_progress = Math.floor((currentChunk / chunks) * 100);

        console.log(file.name + "  正在处理，请稍等," + "已完成" + md5_progress + "%");
        if (currentChunk < chunks) {
            loadNext();
        } else {
            tmp_md5 = spark.end();
            console.log(tmp_md5);
            if (callback != null)
                callback(tmp_md5);
        }
    };

    fileReader.onerror = function () {
        console.warn('oops, something went wrong.');
    };

    function loadNext() {
        var start = currentChunk * chunkSize, end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize;
        fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
    }

    loadNext();
}
