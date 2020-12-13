function imgShow(outerdiv, innerdiv, bigimg, src) {
    $(bigimg).attr("src", src);

    loadImg(src, function (img) {
        var windowW = $(window).width();
        var windowH = $(window).height();
        var realWidth = img.width;
        var realHeight = img.height;
        var imgWidth, imgHeight;
        var scale = 0.9;

        if (realHeight > windowH * scale) {
            imgHeight = windowH * scale;
            imgWidth = imgHeight / realHeight * realWidth;
            if (imgWidth > windowW * scale) {
                imgWidth = windowW * scale;
            }
        } else if (realWidth > windowW * scale) {
            imgWidth = windowW * scale;
            imgHeight = imgWidth / realWidth * realHeight;
        } else {
            imgWidth = realWidth;
            imgHeight = realHeight;
        }
        $(bigimg).css("width", imgWidth);

        var w = (windowW - imgWidth) / 2;
        var h = (windowH - imgHeight) / 2;
        $(innerdiv).css({
            "top": h,
            "left": w
        });

        $(outerdiv).fadeIn("fast");
    });

    $(outerdiv).click(function () {
        $(this).fadeOut("fast");
    });
}

function load9PatchImg(selection, src) {
    var offset = 1;
    var selectionWidth = pxValue(selection.css("width"));
    var selectionHeight = pxValue(selection.css("height"));
    loadImg(src + "_lt.png", function (img_lt) {
        var block = $("<img class='abs_pos'>");
        selection.append(block);
        block.css("left", "0px");
        block.css("top", "0px");
        block.css("width", check(img_lt.width) + "px");
        block.css("height", check(img_lt.height) + "px");
        block.attr("src", src + "_lt.png");
        loadImg(src + "_rt.png", function (img_rt) {
            var block = $("<img class='abs_pos'>");
            selection.append(block);
            block.css("right", "0px");
            block.css("top", "0px");
            block.css("width", check(img_rt.width) + "px");
            block.css("height", check(img_rt.height) + "px");
            block.attr("src", src + "_rt.png");
            loadImg(src + "_lb.png", function (img_lb) {
                var block = $("<img class='abs_pos'>");
                selection.append(block);
                block.css("left", "0px");
                block.css("bottom", "0px");
                block.css("width", check(img_lb.width) + "px");
                block.css("height", check(img_lb.height) + "px");
                block.attr("src", src + "_lb.png");
                loadImg(src + "_rb.png", function (img_rb) {
                    var block = $("<img class='abs_pos'>");
                    selection.append(block);
                    block.css("right", "0px");
                    block.css("bottom", "0px");
                    block.css("width", check(img_rb.width) + "px");
                    block.css("height", check(img_rb.height) + "px");
                    block.attr("src", src + "_rb.png");
                    loadImg(src + "_l.png", function (img_l) {
                        var block = $("<img class='abs_pos'>");
                        selection.append(block);
                        block.css("left", "0px");
                        block.css("top", (check(img_lt.height) - offset) + "px");
                        block.css("width", check(img_l.width) + "px");
                        block.css("height", (selectionHeight - check(img_lt.height) - check(img_lb.height) + 2 * offset) + "px");
                        block.attr("src", src + "_l.png");
                        loadImg(src + "_r.png", function (img_r) {
                            var block = $("<img class='abs_pos'>");
                            selection.append(block);
                            block.css("right", "0px");
                            block.css("top", (check(img_rt.height) - offset) + "px");
                            block.css("width", check(img_r.width) + "px");
                            block.css("height", (selectionHeight - check(img_rt.height) - check(img_rb.height) + 2 * offset) + "px");
                            block.attr("src", src + "_r.png");
                            loadImg(src + "_t.png", function (img_t) {
                                var block = $("<img class='abs_pos'>");
                                selection.append(block);
                                block.css("left", (check(img_lt.width) - offset) + "px");
                                block.css("top", "0px");
                                block.css("width", (selectionWidth - check(img_lt.width) - check(img_rt.width) + 2 * offset) + "px");
                                block.css("height", check(img_t.height) + "px");
                                block.attr("src", src + "_t.png");
                                loadImg(src + "_b.png", function (img_b) {
                                    var block = $("<img class='abs_pos'>");
                                    selection.append(block);
                                    block.css("left", (check(img_lb.width) - offset) + "px");
                                    block.css("bottom", "0px");
                                    block.css("width", (selectionWidth - check(img_lb.width) - check(img_rb.width) + 2 * offset) + "px");
                                    block.css("height", check(img_b.height) + "px");
                                    block.attr("src", src + "_b.png");
                                    loadImg(src + "_center.png", function (img_center) {
                                        var block = $("<img class='abs_pos'>");
                                        selection.append(block);
                                        block.css("left", (check(img_lt.width) - offset) + "px");
                                        block.css("top", (check(img_lt.height) - offset) + "px");
                                        block.css("width", (selectionWidth - check(img_lt.width) - check(img_rt.width) + 2 * offset) + "px");
                                        block.css("height", (selectionHeight - check(img_lt.height) - check(img_lb.height) + 2 * offset) + "px");
                                        block.attr("src", src + "_center.png");
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
    });
}

var Type_Photo = 0;
var Type_Video = 1;
var Type_Topic = 2;
var Type_SelectionGroup = 3;
var Type_BoardText = 4;

var VideoLocal = "local:";
var VideoNet = "net:";

var obj = null;
var contentWidth = null;
var contentHeight = null;

$(window).resize(function () {
    console.log('resize');
    if (obj != null)
        load();
});

$(document).ready(function () {
    console.log('ready');
    requestTask();
});

function checkUrl(url) {
    if (textIsEmpty(url)) {
        return url;
    } else {
        if (url.indexOf("http://") == 0 || url.indexOf("https://") == 0) {
            return url;
        } else {
            return urlRoot + url;
        }
    }
}

function showMessage(msg) {
    var message = $("#message");
    message.text(msg);
}

function requestTask() {
    console.log('requestTask');
    $.post({
        url: "../logic/api",
        contentType: "application/json",
        data: JSON.stringify({
            handleId: GetTaskForWeb,
            data: JSON.stringify({
                id: taskId,
                page: taskPage
            })
        }),
        success: function (data, status) {
            if (status == "success") {
                var reault = data;
                if (reault.code == 200) {
                    try {
                        obj = JSON.parse(reault.data);
                        load();
                    } catch (e) {
                        alert(e);
                    }
                } else {
                    showMessage(reault.msg);
                }
            } else {
                showMessage("加载任务失败！");
            }
        }
    });
}

function check(pos) {
    return Math.round(contentWidth * pos / 1920);
}

function calculateContentSize(pageWidth, pageHeight, boardHeight) {
    var contentSize = new Object();

    var aa = pageWidth / pageHeight;
    var bb = 1920 / boardHeight;
    if (aa > bb) {
        contentSize.height = pageHeight;
        contentSize.width = 1920 * contentSize.height / boardHeight;
    } else {
        contentSize.width = pageWidth;
        contentSize.height = contentSize.width * boardHeight / 1920;
    }

    return contentSize;
}

function load() {
    if (obj.pageDatas.length == 0) {
        showMessage("页面为空！");
        return;
    }

    if (obj.singlePage)
        document.title = obj.name + "[" + (obj.pageDatas[0].index + 1) + "/" + obj.count + "]";
    else
        document.title = obj.name + "[" + obj.count + "]";

    var page = $("#page");

    page.empty();

    var pageWidth = pxValue(page.css("width"));
    var pageHeight = pxValue(page.css("height"));

    var topPos = 0;

    for (var t = 0; t < obj.pageDatas.length; t++) {
        var pageData = obj.pageDatas[t];

        var content = $("<div class='abs_pos content'></div>");
        page.append(content);

        if (true)
        // if (isPC() || isLandscape())
        {
            var contentMinWidth = calculateContentSize(pageWidth, pageHeight, 1200).width;

            var contentSize = calculateContentSize(pageWidth, pageHeight, pageData.board.height);
            contentWidth = contentSize.width;
            contentHeight = contentSize.height;
            if (contentWidth < contentMinWidth) {
                contentWidth = contentMinWidth;
                contentHeight = contentWidth * contentSize.height / contentSize.width;
            }

            content.css("width", contentWidth + "px");
            content.css("height", contentHeight + "px");
            var contentLeft = (pageWidth - contentWidth) / 2;
            var contentTop = (pageHeight - contentHeight) / 2;
            if (contentTop < 0) {
                contentTop = 0;
            }
            content.css("left", contentLeft + "px");
            content.css("top", topPos + "px");
            // content.css("top", contentTop + "px");

            topPos += parseInt(contentHeight);
        } else {
            // content.addClass("rotate90");

            // var tmp = pageWidth;
            // pageWidth = pageHeight;
            // pageHeight = tmp;

            // var contentMinWidth = calculateContentSize(pageHeight, pageWidth, 1200).width;
            //
            // var contentSize = calculateContentSize(pageHeight, pageWidth, obj.board.height);
            // var contentWidth = contentSize.width;
            // var contentHeight = contentSize.height;
            // if (contentWidth < contentMinWidth)
            // {
            // contentWidth = contentMinWidth;
            // contentHeight = contentWidth * contentSize.height / contentSize.width;
            // }
            //
            // alert(pageWidth + "," + pageHeight + "\n" + contentWidth + "," + contentHeight);
            //
            // content.css("width", contentWidth + "px");
            // content.css("height", contentHeight + "px");
            // var contentLeft = (pageWidth - contentWidth) / 2;
            // var contentTop = (pageHeight - contentHeight) / 2;
            // if (contentTop < 0)
            // {
            // contentTop = 0;
            // }
            // content.css("left", contentLeft + "px");
            // content.css("top", contentTop + "px");
        }

        // if (obj.board.photos.length > 0)
        {
            var container = $("<div class='abs_pos'></div>");
            content.append(container);
            container.css("left", "0px");
            container.css("top", "0px");
            container.css("width", contentWidth + "px");
            container.css("height", contentHeight + "px");

            if (!textIsEmpty(pageData.bigVideo)) {
                var bigVideo = $("<video controls class='abs_pos'></video>");
                container.append(bigVideo);
                bigVideo.css("left", "0px");
                bigVideo.css("top", "0px");
                bigVideo.css("width", contentWidth + "px");
                bigVideo.css("height", contentHeight + "px");

                var bigVideoSource = $("<source type='video/mp4'>");
                bigVideo.append(bigVideoSource);

                if (pageData.bigVideo.indexOf(VideoNet) == 0) {
                    var videoUrl = pageData.bigVideo.substring(VideoNet.length);
                    bigVideoSource.attr("src", checkUrl(videoUrl));
                } else if (pageData.bigVideo.indexOf(VideoLocal) == 0) {
                    var videoUrl = obj.urlRoot + "/" + obj.userId + "/" + obj.id + "/" + pageData.page.pageDir + "/big_video.mp4";
                    bigVideoSource.attr("src", videoUrl);
                }

            }

            for (var i = 0; i < pageData.board.photos.length; i++) {
                var photo = pageData.board.photos[i];
                !function (pageData, photo) {
                    var photoWidth = photo.width - 28;
                    var photoHeight = photo.height - 28;

                    var con = $("<div class='abs_pos'></div>");
                    container.append(con);
                    con.css("left", check(photo.x + 14) + "px");
                    con.css("top", check(photo.y + 14) + "px");
                    con.css("width", check(photoWidth) + "px");
                    con.css("height", check(photoHeight) + "px");

                    var conWidth = pxValue(con.css("width"));
                    var conHeight = pxValue(con.css("height"));

                    if (photo.type == Type_Photo) {
                        var img = $("<img class='abs_pos'>");
                        con.append(img);
                        img.css("left", "0px");
                        img.css("top", "0px");
                        img.css("width", conWidth + "px");
                        img.css("height", conHeight + "px");
                        if (!textIsEmpty(photo.cover)) {
                            img.attr("src", photo.cover);
                        } else {
                            img.attr("src", obj.urlRoot + "/" + obj.userId + "/" + obj.id + "/" + pageData.page.pageDir + "/" + photo.name + ".png");
                        }
                    } else if (photo.type == Type_Video) {
                        con.css("border", "1px solid #707070");

                        var img = $("<img class='abs_pos'>");
                        con.append(img);
                        img.css("left", "0px");
                        img.css("top", "0px");
                        img.css("width", conWidth + "px");
                        img.css("height", conHeight + "px");
                        if (!textIsEmpty(photo.cover)) {
                            img.attr("src", photo.cover);
                        } else {
                            img.attr("src", obj.urlRoot + "/" + obj.userId + "/" + obj.id + "/" + pageData.page.pageDir + "/" + photo.name + ".png");
                        }

                        var videoFlag = $("<img class='abs_pos'>");
                        con.append(videoFlag);
                        videoFlag.css("left", check((photoWidth - 100) / 2) + "px");
                        videoFlag.css("top", check((photoHeight - 100) / 2) + "px");
                        videoFlag.css("width", check(100) + "px");
                        videoFlag.css("height", check(100) + "px");
                        videoFlag.attr("src", "../img/img_play.png");
                        con.click(function (event) {
                            if (photo.url.indexOf(VideoNet) == 0) {
                                var videoUrl = photo.url.substring(VideoNet.length);
                                $(window).attr('location', checkUrl(videoUrl));
                            } else if (photo.url.indexOf(VideoLocal) == 0) {
                                var videoUrl = obj.urlRoot + "/" + obj.userId + "/" + obj.id + "/" + pageData.page.pageDir + "/" + photo.name + ".mp4";
                                $(window).attr('location', videoUrl);
                            }
                            event.stopPropagation();
                        });

                        var durationText = $("<div class='abs_pos'></div>");
                        con.append(durationText);
                        durationText.text(formatTime(photo.duration));
                        durationText.css("color", "white");
                        durationText.css("background-color", "#b9b9b9");
                        durationText.css("padding-left", check(6) + "px");
                        durationText.css("padding-right", check(6) + "px");
                        durationText.css("left", check(12) + "px");
                        durationText.css("bottom", check(12) + "px");
                    } else if (photo.type == Type_Topic) {
                        var img = $("<img class='abs_pos'>");
                        con.append(img);
                        img.css("left", "0px");
                        img.css("top", "0px");
                        img.css("width", conWidth + "px");
                        img.css("height", conHeight + "px");
                        if (!textIsEmpty(photo.cover)) {
                            img.attr("src", photo.cover);
                        } else {
                            img.attr("src", obj.urlRoot + "/" + obj.userId + "/" + obj.id + "/" + pageData.page.pageDir + "/" + photo.name + ".png");
                        }

                        var parse = $("<img class='abs_pos'>");
                        con.append(parse);
                        parse.css("width", check(72) + "px");
                        parse.css("height", check(72) + "px");
                        parse.css("padding", check(10) + "px");
                        parse.css("right", check(0) + "px");
                        parse.css("bottom", check(0) + "px");
                        parse.attr("src", "../img/img_parse.png");
                        parse.click(function (event) {
                            var parseUrl;
                            if (!textIsEmpty(photo.url)) {
                                parseUrl = photo.url;
                            } else {
                                parseUrl = obj.urlRoot + "/" + obj.userId + "/" + obj.id + "/" + pageData.page.pageDir + "/" + photo.name + ".parse.png";
                            }
                            imgShow("#outerdiv", "#innerdiv", "#bigimg", parseUrl);
                            event.stopPropagation();
                        });
                    } else if (photo.type == Type_SelectionGroup) {
                        var selectionGroup = $("<div class='abs_pos'></div>");
                        con.append(selectionGroup);
                        selectionGroup.css("left", check(70 - 14) + "px");
                        selectionGroup.css("top", check(70 - 14) + "px");
                        selectionGroup.css("width", (conWidth - check(70 - 14) * 2) + "px");
                        selectionGroup.css("height", (conHeight - check(70 - 14) * 2) + "px");

                        var selectionGroupWidth = pxValue(selectionGroup.css("width"));

                        var selectionConWidth = parseInt(selectionGroupWidth / photo.selectionGroup.selections.length);

                        for (var k = 0; k < photo.selectionGroup.selections.length; k++) {
                            var text = photo.selectionGroup.selections[k];

                            var selectionCon = $("<div class='abs_pos'></div>");
                            selectionGroup.append(selectionCon);
                            selectionCon.css("left", (k * selectionConWidth + check(10)) + "px");
                            selectionCon.css("top", "0px");
                            selectionCon.css("width", (selectionConWidth - check(10) * 2) + "px");
                            selectionCon.css("height", "100%");

                            var selection = $("<div class='abs_pos'></div>");
                            selectionCon.append(selection);
                            selection.css("left", "0px");
                            selection.css("top", "0px");
                            selection.css("width", "100%");
                            selection.css("height", "100%");

                            if (includes(photo.selectionGroup.answer, text))
                                load9PatchImg(selection, "../img/img_selection_yes");
                            else
                                load9PatchImg(selection, "../img/img_selection_no");

                            var selectionText = $("<div class='abs_pos'></div>");
                            selectionCon.append(selectionText);
                            selectionText.text(text);
                            if (includes(photo.selectionGroup.answer, text))
                                selectionText.css("color", "white");
                            else
                                selectionText.css("color", "#797e82");
                            selectionText.css("width", "100%");
                            selectionText.css("text-align", "center");
                            selectionText.css("font-size", check(38) + "px");
                            selectionText.css("top", (pxValue(selectionCon.css("height")) - pxValue(selectionText.css("height"))) / 2 + "px");

                            if (includes(photo.selectionGroup.rightAnswer, text)) {
                                var rightSelectionFlag = $("<img class='abs_pos'>");
                                selectionCon.append(rightSelectionFlag);
                                rightSelectionFlag.css("right", "0px");
                                rightSelectionFlag.css("width", check(27) + "px");
                                rightSelectionFlag.css("height", check(27) + "px");
                                rightSelectionFlag.attr("src", "../img/img_right_selection.png");
                            }
                        }
                    } else if (photo.type == Type_BoardText) {
                        var text = $("<div></div>");
                        con.append(text);

                        var textHeight = conHeight - 2 * check(photo.boardText.strokeWidth);
                        if (photo.boardText.paddings.length == 4) {
                            textHeight -= check(photo.boardText.paddings[1]);
                            textHeight -= check(photo.boardText.paddings[3]);
                        }

                        text.css("left", "0px");
                        text.css("top", "0px");
                        text.css("width", conWidth + "px");
                        text.css("height", textHeight + "px");

                        text.text(photo.boardText.text);
                        text.css("color", intColor(photo.boardText.color));
                        text.css("font-size", check(photo.boardText.size) + "px");

                        text.css("display", "table-cell");

                        var gravityH = photo.boardText.gravity & 0x0f;
                        if (gravityH == 3)
                            text.css("text-align", "left");
                        else if (gravityH == 1)
                            text.css("text-align", "center");
                        else if (gravityH == 5)
                            text.css("text-align", "right");

                        var gravityV = photo.boardText.gravity & 0xf0;
                        if (gravityV == 48)
                            text.css("vertical-align", "top");
                        else if (gravityV == 16)
                            text.css("vertical-align", "middle");
                        else if (gravityV == 80)
                            text.css("vertical-align", "bottom");

                        if (photo.boardText.textStyle == 1)
                            text.css("font-weight", "bold");

                        text.css("background-color", intColor(photo.boardText.solidColor));
                        text.css("border-style", "solid");
                        text.css("border-color", intColor(photo.boardText.strokeColor));
                        text.css("border-width", check(photo.boardText.strokeWidth) + "px");

                        if (photo.boardText.corners.length == 8) {
                            text.css("border-top-left-radius", check(photo.boardText.corners[0]) + "px");
                            text.css("border-top-right-radius", check(photo.boardText.corners[2]) + "px");
                            text.css("border-bottom-right-radius", check(photo.boardText.corners[4]) + "px");
                            text.css("border-bottom-left-radius", check(photo.boardText.corners[6]) + "px");
                        }

                        if (photo.boardText.paddings.length == 4) {
                            text.css("padding-left", check(photo.boardText.paddings[0]) + "px");
                            text.css("padding-top", check(photo.boardText.paddings[1]) + "px");
                            text.css("padding-right", check(photo.boardText.paddings[2]) + "px");
                            text.css("padding-bottom", check(photo.boardText.paddings[3]) + "px");
                        }

                        text.css("letter-spacing", check(photo.boardText.letterSpacing * photo.boardText.size) + "px"); // use
                        // text.css("letter-spacing", (photo.boardText.letterSpacing * 100) + "%");

                        text.css("line-height", check(photo.boardText.lineSpacingMult * photo.boardText.size * 1.2) + "px"); // use
                        // text.css("line-height", (photo.boardText.lineSpacingMult * 100) + "%");

                        // text.css("transform", "scale(" + photo.boardText.textScaleX + ", 1)");
                    }
                }(pageData, photo);
            }
        }

        if (pageData.hasBoard) {
            var board = $("<img class='abs_pos'>");
            content.append(board);
            board.css("pointer-events", "none");
            board.css("left", "0px");
            board.css("top", "0px");
            board.css("width", contentWidth + "px");
            board.css("height", contentHeight + "px");
            board.attr("src", obj.urlRoot + "/" + obj.userId + "/" + obj.id + "/" + pageData.page.pageDir + "/board.png");
        }

    }

    if (obj.singlePage) {
        var pageIndex = obj.pageDatas[0].index;

        var nav_prev = $("#nav_prev");
        var nav_next = $("#nav_next");

        var navWidth = Math.max(pageWidth, pageHeight) * 0.05;
        var navHeight = navWidth * 100 / 56;

        nav_prev.css("left", navWidth / 2 + "px");
        nav_prev.css("top", (pageHeight - navHeight) / 2 + "px");
        nav_prev.css("width", navWidth + "px");
        nav_prev.css("height", navHeight + "px");

        nav_next.css("right", navWidth / 2 + "px");
        nav_next.css("top", (pageHeight - navHeight) / 2 + "px");
        nav_next.css("width", navWidth + "px");
        nav_next.css("height", navHeight + "px");

        page.unbind();
        page.click(function () {
            if (pageIndex > 0)
                nav_prev.fadeToggle();
            if (pageIndex < obj.count - 1)
                nav_next.fadeToggle();
        });

        if (pageIndex > 0)
            nav_prev.show();
        else
            nav_prev.hide();

        if (pageIndex < obj.count - 1)
            nav_next.show();
        else
            nav_next.hide();

        setTimeout(function () {
            page.trigger("click");
        }, 1000);

        nav_prev.click(function () {
            var pageUrl = obj.id + "_" + obj.prevPage;
            $(window).attr('location', pageUrl);
        });

        nav_next.click(function () {
            var pageUrl = obj.id + "_" + obj.nextPage;
            $(window).attr('location', pageUrl);
        });

        $(document).keydown(function (event) {
            if (event.keyCode == 37) {
                if (pageIndex > 0) {
                    var pageUrl = obj.id + "_" + obj.prevPage;
                    $(window).attr('location', pageUrl);
                }
            } else if (event.keyCode == 39) {
                if (pageIndex < obj.count - 1) {
                    var pageUrl = obj.id + "_" + obj.nextPage;
                    $(window).attr('location', pageUrl);
                }
            }
        });

    }

}
