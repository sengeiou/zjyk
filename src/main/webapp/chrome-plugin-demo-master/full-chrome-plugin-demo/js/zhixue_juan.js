alert('xxxx');

function MyNnd_Main() {
    MyNnd_Log("MyNnd_Main", null);
}

function MyNnd_SetAccount(account, psw) {
    MyNnd_Log("MyNnd_SetAccount " + account + "," + psw, null);
    var txtUserName = MyNnd_Find_Id(document, "txtUserName");
    if (txtUserName != null) {
        MyNnd_Log("find txtUserName", null);
        txtUserName.value = account;
    }
    var txtPassword = MyNnd_Find_Id(document, "txtPassword");
    if (txtPassword != null) {
        MyNnd_Log("find txtPassword", null);
        txtPassword.value = psw;
    }
}

function MyNnd_ClickIgnore() {
    /* MyNnd_Log("MyNnd_ClickIgnore", null); */
    MyNnd_Delay(4000, function () {
        var aui_content = MyNnd_Find_Class(document, "aui_content");
        if (aui_content != null) {
            /* MyNnd_Log("find aui_content", null); */
            var ignore = MyNnd_Find_Class(aui_content, "ignore");
            if (ignore != null) {
                MyNnd_Log("click ignore", null);
                MyNnd_Click(ignore);
            }
        }
    });
}

function MyNnd_ClickXtzj() {
    /* MyNnd_Log("MyNnd_ClickXtzj", null); */
    MyNnd_Delay(4000, function () {
        var headMenuList = MyNnd_Find_Id(document, "headMenuList");
        if (headMenuList != null) {
            /* MyNnd_Log("find headMenuList", null); */
            var xtzj = MyNnd_FindByText(headMenuList, "A", "选题组卷");
            if (xtzj != null) {
                MyNnd_Log("click 选题组卷", null);
                MyNnd_Click(xtzj);
            }
        }
    });
}

function MyNnd_ClickTbzj() {
    /* MyNnd_Log("MyNnd_ClickTbzj", null); */
    MyNnd_Delay(4000, function () {
        var pub_nav = MyNnd_Find_Class(document, "pub-nav");
        if (pub_nav != null) {
            /* MyNnd_Log("find pub_nav", null); */
            var tbzj = MyNnd_FindByText(pub_nav, "A", "精品卷库");
            if (tbzj != null) {
                MyNnd_Log("click 精品卷库", null);
                MyNnd_Click(tbzj);
            }
        }
    });
}

function MyNnd_ModifyPage() {
    var disBlock = MyNnd_Find_Class(document, "pd-contr");
    var topics = MyNnd_Find_TagList(disBlock, "app-topic-show");
    MyNnd_Log("MyNnd_ModifyPage 题数：" + topics.length, null);
    for (var i = 0; i < topics.length; i++) {
        var topic = topics[i];

        var question_list = MyNnd_GetChild(topic, "DIV", 0);
        var question_content = MyNnd_GetChild(question_list, "DIV", 1);
        var ans_analy = MyNnd_GetChild(question_list, "DIV", 4);

        question_content.setAttribute("style", "font-size: 20px;");

        ans_analy.setAttribute("style", "display: block;");

        var answer = MyNnd_GetChild(ans_analy, "DIV", 0);
        var parse = MyNnd_GetChild(ans_analy, "DIV", 1);

        var answer_p = MyNnd_GetChild(answer, "P", 0);
        answer_p.setAttribute("style", "font-size: 20px;");

        var parse_div = MyNnd_GetChild(parse, "DIV", 0);
        parse_div.setAttribute("style", "font-size: 20px;");
    }
    return topics.length;
}

function MyNnd_ModifyPage2() {
    var disBlock = MyNnd_Find_Class(document, "paper-pane");

    var titles = MyNnd_Find_ClassList(disBlock, "topicset-title");
    MyNnd_Log("MyNnd_ModifyPage 大题数：" + titles.length, null);
    for (var i = 0; i < titles.length; i++) {
        var title = titles[i];
        title.setAttribute("style", "display: none;");
    }

    var topics = MyNnd_Find_TagList(disBlock, "app-topic-view");
    MyNnd_Log("MyNnd_ModifyPage 题数：" + topics.length, null);
    for (var i = 0; i < topics.length; i++) {
        var topic = topics[i];

        var question_content = MyNnd_GetChild(topic, "DIV", 0);
        var ans_analy = MyNnd_GetChild(topic, "DIV", 1);

        question_content.setAttribute("style", "padding: 10px; font-size: 18px; border: 1px solid #0dc2b3; margin: -1px;");

        ans_analy.setAttribute("style", "display: block;");

        var answer = MyNnd_GetChild(ans_analy, "DIV", 0);
        var parse = MyNnd_GetChild(ans_analy, "DIV", 1);
        var knowledge = MyNnd_GetChild(ans_analy, "DIV", 2);

        var answer_p = MyNnd_GetChild(answer, "P", 0);
        answer_p.setAttribute("style", "font-size: 18px;");

        var parse_P = MyNnd_GetChild(parse, "P", 0);
        parse_P.setAttribute("style", "font-size: 18px;");

        knowledge.setAttribute("style", "display: none;");
    }
    return topics.length;
}

function MyNnd_CatchProblems() {
    var problems = new Array();
    var kldList = MyNnd_Find_Class(document, "pd-contr");
    var topics = MyNnd_Find_TagList(kldList, "app-topic-show");
    MyNnd_Log("MyNnd_CatchProblems 题数：" + topics.length, null);
    for (var i = 0; i < topics.length; i++) {
        var topic = topics[i];

        var problem = new Object();

        var question_list = MyNnd_GetChild(topic, "DIV", 0);
        var question_content = MyNnd_GetChild(question_list, "DIV", 1);
        var operate_area = MyNnd_GetChild(question_list, "DIV", 3);
        var ans_analy = MyNnd_GetChild(question_list, "DIV", 4);

        problem.id = question_content.getAttribute("id").trim();

        problem.content = question_content.innerText.trim().replace(new RegExp("\r\n", "g"), "").replace(new RegExp("\n", "g"), "");

        var answer = MyNnd_GetChild(ans_analy, "DIV", 0);
        var parse = MyNnd_GetChild(ans_analy, "DIV", 1);
        var knowledge = MyNnd_GetChild(ans_analy, "DIV", 2);
        var style = MyNnd_GetChild(ans_analy, "DIV", 3);

        var answer_p = MyNnd_GetChild(answer, "P", 0);
        problem.answer = answer_p.innerText.trim().replace(new RegExp("\r\n", "g"), "").replace(new RegExp("\n", "g"), "");

        var parse_div = MyNnd_GetChild(parse, "DIV", 0);
        problem.parse = parse_div.innerText.trim().replace(new RegExp("\r\n", "g"), "").replace(new RegExp("\n", "g"), "");

        problem.knowledges = new Array();
        var knowledge_div = MyNnd_GetChild(knowledge, "DIV", 0);
        var spans = MyNnd_Find_TagList(knowledge_div, "SPAN");
        for (var j = 0; j < spans.length; j++) {
            var span = spans[j];
            problem.knowledges[problem.knowledges.length++] = span.innerHTML.trim();
        }

        var style_div = MyNnd_GetChild(style, "DIV", 0);
        var style_p = MyNnd_GetChild(style_div, "P", 0);
        problem.style = style_p.innerHTML.trim();

        /*
         * var zujuan = MyNnd_Find_Class(operate_area, "zujuan"); var zuoda = MyNnd_Find_Class(operate_area, "zuoda"); problem.zujuan = zujuan.innerText.trim(); problem.zuoda = zuoda.innerText.trim();
         */

        var defens = operate_area.getElementsByClassName("defen");
        if (defens != null && defens.length == 1) {
            problem.defen = defens[0].innerText.trim();
        }

        var nandus = operate_area.getElementsByClassName("nandu");
        if (nandus != null && nandus.length == 1) {
            problem.nandu = nandus[0].innerText.trim();
        }

        problems[problems.length++] = problem;
    }
    return problems;
}

function MyNnd_CatchProblems2() {
    var problems = new Array();
    var disBlock = MyNnd_Find_Class(document, "paper-pane");
    var topics = MyNnd_Find_TagList(disBlock, "app-topic-view");
    MyNnd_Log("MyNnd_CatchProblems 题数：" + topics.length, null);
    for (var i = 0; i < topics.length; i++) {
        var topic = topics[i];

        var problem = new Object();

        var question_content = MyNnd_GetChild(topic, "DIV", 0);
        var ans_analy = MyNnd_GetChild(topic, "DIV", 1);

        var answer = MyNnd_GetChild(ans_analy, "DIV", 0);
        var parse = MyNnd_GetChild(ans_analy, "DIV", 1);
        var answer_p = MyNnd_GetChild(answer, "P", 0);
        var parse_P = MyNnd_GetChild(parse, "P", 0);

        problem.answer = answer_p.innerText.trim().replace(new RegExp("\r\n", "g"), "").replace(new RegExp("\n", "g"), "");

        problems[problems.length++] = problem;
    }
    return problems;
}

function MyNnd_CheckTopicImpl(text) {
    if (text.indexOf("\\(") >= 0 || text.indexOf("(\\") >= 0 || text.indexOf("^{") >= 0) {
        return false;
    } else {
        return true;
    }
}

function MyNnd_CheckTopic(problem) {
    if (!MyNnd_CheckTopicImpl(problem.content)) {
        MyNnd_Log("bad content : " + problem.content, null);
        return false;
    }
    if (!MyNnd_CheckTopicImpl(problem.answer)) {
        MyNnd_Log("bad answer : " + problem.answer, null);
        return false;
    }
    if (!MyNnd_CheckTopicImpl(problem.parse)) {
        MyNnd_Log("bad parse : " + problem.parse, null);
        return false;
    }
    return true;
}

function MyNnd_HasRequestError() {
    var pt_msg_div_list = MyNnd_Find_ClassList(document, "pt_msg_div");
    if (pt_msg_div_list.length > 0) {
        for (var i = 0; i < pt_msg_div_list.length; i++) {
            var pt_msg_div = pt_msg_div_list[i];
            var msg_str = pt_msg_div.innerText.trim();
            if (msg_str == "请求出错") {
                return true;
            }
        }
    }
    return false;
}

function MyNnd_TryCatchProblems(name, phase) {
    var problems = MyNnd_CatchProblems();
    var checkSuccess = true;
    for (var i = 0; i < problems.length; i++) {
        var problem = problems[i];
        if (!MyNnd_CheckTopic(problem)) {
            MyNnd_Log(times + " check fail and topic number is : " + (i + 1), null);
            checkSuccess = false;
            break;
        }
    }
    if (checkSuccess) {
        for (var i = 0; i < problems.length; i++) {
            var problem = problems[i];
        }
        var info = new Object();
        info.phase = phase;
        info.subject = "数学";
        info.material = name;
        info.chapterPath = "1";
        info.diff = "1";
        info.area = "1";
        info.year = "1";
        var htmlStr = document.getElementsByTagName('html')[0].innerHTML;
        if (MyNnd_HasRequestError()) {
            MyNnd_Log("has 请求出错", null);
        } else {
            MyNnd_CallCmd4("resultProblems", JSON.stringify(info), 0, JSON.stringify(problems), htmlStr, null);
        }
    } else {
    }
}

function MyNnd_TryCatchProblems2(name, phase) {
    var problems = MyNnd_CatchProblems2();
    var info = new Object();
    info.phase = phase;
    info.subject = "数学";
    info.material = name;
    info.chapterPath = "1";
    info.diff = "1";
    info.area = "1";
    info.year = "1";
    var htmlStr = document.getElementsByTagName('html')[0].innerHTML;
    MyNnd_CallCmd4("resultProblems2", JSON.stringify(info), 0, JSON.stringify(problems), htmlStr, null);
}

function MyNnd_GetNamePhase() {
    var result = new Object();
    try {
        var totalPage_span = MyNnd_Find_Class(document, "pd-topl");
        if (totalPage_span != null) {
            var p0 = MyNnd_GetChild(totalPage_span, "P", 0);
            var p1 = MyNnd_GetChild(totalPage_span, "P", 1);
            var span = MyNnd_GetChild(p1, "SPAN", 1);
            result.name = p0.innerHTML.trim();
            result.phase = span.innerHTML.trim();
            result.subject = "数学";
            return result;
        }
    } catch (err) {
        alert(err.message);
    }
    return null;
}

function MyNnd_GetNamePhase2() {
    var result = new Object();
    try {
        var paper_head = MyNnd_Find_Class(document, "paper-head");
        if (paper_head != null) {
            result.name = paper_head.innerHTML.trim();
            result.phase = "试卷";
            result.subject = "数学";
            return result;
        }
    } catch (err) {
        alert(err.message);
    }
    return null;
}

function MyNnd_CatchOnePage() {
    var topicCount = MyNnd_ModifyPage();
    if (topicCount > 0) {
        MyNnd_Delay(10000, function () {
            var totalPage_span = MyNnd_Find_Class(document, "pd-topl");
            if (totalPage_span != null) {
                var p0 = MyNnd_GetChild(totalPage_span, "P", 0);
                var p1 = MyNnd_GetChild(totalPage_span, "P", 1);
                var span = MyNnd_GetChild(p1, "SPAN", 1);
                var name = p0.innerHTML.trim();
                var phase = span.innerHTML.trim();
                MyNnd_TryCatchProblems(name, phase);
            }
        });
    } else {
    }
}

function MyNnd_CatchOnePage2() {
    var topicCount = MyNnd_ModifyPage2();
    if (topicCount > 0) {
        MyNnd_Delay(10000, function () {
            var paper_head = MyNnd_Find_Class(document, "paper-head");
            if (paper_head != null) {
                var name = paper_head.innerHTML.trim();
                var phase = "试卷";
                MyNnd_TryCatchProblems2(name, phase);
            }
        });
    } else {
    }
}

function MyNnd_CatchNextChapter(phase, subject, material, diff, area, year, currChapterPath, currPage) {
    var chapterLeaf = MyNnd_FindNextLeafChapter(currChapterPath);
    if (chapterLeaf != null) {
        MyNnd_Log("click chapterLeaf : " + chapterLeaf.path, null);
        MyNnd_MouseDown_Delay_Callback(chapterLeaf.li_a, 4000, function () {
            currChapterPath = chapterLeaf.path;
            var totalPage_span = MyNnd_Find_Class(document, "totalPage");
            if (totalPage_span != null) {
                var totalPage = totalPage_span.innerHTML.trim();
                /* MyNnd_Log("find totalPage " + totalPage, null); */
                if (parseInt(currPage) <= parseInt(totalPage)) {
                    MyNnd_CatchOnePage(phase, subject, material, diff, area, year, currChapterPath, currPage);
                } else {
                    MyNnd_CatchNextChapter(phase, subject, material, diff, area, year, currChapterPath, 1);
                }
            }
        });
    } else {
        /* MyNnd_Log("not find next chapterLeaf", null); */
        var info = new Object();
        info.phase = phase;
        info.subject = subject;
        info.material = material;
        info.diff = diff;
        info.area = area;
        info.year = year;
        MyNnd_CallCmd1("catchOver", JSON.stringify(info), null);
    }
}

function MyNnd_CatchPage(diff, area, year, currChapterPath, currPage) {
    /* MyNnd_Log("MyNnd_CatchPage " + diff + "," + area + "," + year + "," + currChapterPath + "," + currPage, null); */
    MyNnd_Delay(4000, function () {
        /* MyNnd_Log("start catch", null); */
        var phase_subject = MyNnd_Find_Class(document, "phase_subject");
        if (phase_subject != null) {
            var phaseSubject = phase_subject.innerHTML.trim().split("·");
            var phase = phaseSubject[0].trim();
            var subject = phaseSubject[1].trim();
            /* MyNnd_Log("find phase_subject " + phase + "," + subject, null); */
            var leftTree = MyNnd_Find_Class(document, "leftTree");
            if (leftTree != null) {
                /* MyNnd_Log("find leftTree", null); */
                var leftTree_material = MyNnd_GetChild(leftTree, "DIV", 0);
                var curBook = MyNnd_Find_Class(leftTree_material, "curBook");
                var material = curBook.innerText.trim();
                /* MyNnd_Log("find material " + material, null); */

                var selectorPanel = MyNnd_Find_Class(document, "selectorPanel");
                if (selectorPanel != null) {
                    /* MyNnd_Log("find selectorPanel", null); */
                    var span = MyNnd_GetChild(selectorPanel, "SPAN", 0);
                    var dl_diff = MyNnd_GetChild(span, "DL", 1);
                    var a_diff = MyNnd_FindByText(dl_diff, "A", diff);
                    if (a_diff != null) {
                        MyNnd_Log("click " + diff, null);
                        MyNnd_Click_Delay_Callback(a_diff, 4000, function () {
                            var filter_item_content = MyNnd_Find_Class(span, "filter-item-content");
                            var filter_area = MyNnd_GetChild(filter_item_content, "app-condition-selector-more", 0);
                            var filter_year = MyNnd_GetChild(filter_item_content, "app-condition-selector-more", 2);

                            var filter_area_dropdown = MyNnd_Find_Class(filter_area, "dropdown");
                            var a_area = MyNnd_FindByText(filter_area_dropdown, "A", area);
                            if (a_area != null) {
                                MyNnd_Log("click " + area, null);
                                MyNnd_Click_Delay_Callback(a_area, 4000, function () {
                                    var filter_year_dropdown = MyNnd_Find_Class(filter_year, "dropdown");
                                    var a_year = MyNnd_FindByText(filter_year_dropdown, "A", year);
                                    if (a_year != null) {
                                        MyNnd_Log("click " + year, null);
                                        MyNnd_Click_Delay_Callback(a_year, 4000, function () {
                                            MyNnd_ClickChapter(function () {
                                                /* start logic */
                                                if (currChapterPath == "") {
                                                    MyNnd_CatchNextChapter(phase, subject, material, diff, area, year, currChapterPath, 1);
                                                } else {
                                                    var chapterLeaf = MyNnd_FindLeafChapter(currChapterPath);
                                                    if (chapterLeaf != null) {
                                                        MyNnd_MouseDown_Delay_Callback(chapterLeaf.li_a, 4000, function () {
                                                            MyNnd_CatchOnePage(phase, subject, material, diff, area, year, currChapterPath, currPage);
                                                        });
                                                    } else {
                                                        alert("error @293 " + currChapterPath);
                                                    }
                                                }
                                                /* end logic */
                                            });
                                        });
                                    }
                                });
                            }
                        });
                    }
                }
            }
        }
    });
}

function MyNnd_GetPhaseSubject() {
    /* MyNnd_Log("MyNnd_GetPhaseSubject", null); */
    var result = new Object();
    try {
        var phase_subject = MyNnd_Find_Class(document, "phase_subject");
        if (phase_subject != null) {
            var phaseSubject = phase_subject.innerHTML.trim().split("·");
            result.phase = phaseSubject[0].trim();
            result.subject = phaseSubject[1].trim();
            /* MyNnd_Log("find phase_subject " + result.phase + "," + result.subject, null); */
            return result;
        }
    } catch (err) {
        alert(err.message);
    }
    return null;
}

function MyNnd_GetMaterial() {
    /* MyNnd_Log("MyNnd_GetMaterial", null); */
    var result = new Object();
    var leftTree = MyNnd_Find_Class(document, "leftTree");
    if (leftTree != null) {
        /* MyNnd_Log("find leftTree", null); */
        var leftTree_material = MyNnd_GetChild(leftTree, "DIV", 0);
        var curBook = MyNnd_Find_Class(leftTree_material, "curBook");
        result.material = curBook.innerText.trim();
        return result;
    }
}

function MyNnd_GetGroup() {
    /* MyNnd_Log("MyNnd_GetGroup", null); */
    var group = new Object();
    try {
        var selectorPanel = MyNnd_Find_Class(document, "selectorPanel");
        if (selectorPanel != null) {
            /* MyNnd_Log("find selectorPanel", null); */
            var span = MyNnd_GetChild(selectorPanel, "SPAN", 0);
            var dl_style = MyNnd_GetChild(span, "DL", 0);
            var dl_diff = MyNnd_GetChild(span, "DL", 1);

            group.diffs = new Array();
            group.areas = new Array();
            group.years = new Array();

            var as_diff = MyNnd_Find_TagList(dl_diff, "A");
            for (var i = 0; i < as_diff.length; i++) {
                var a_diff = as_diff[i];
                if (a_diff.innerHTML.trim() != "全部") {
                    group.diffs[group.diffs.length++] = a_diff.innerHTML.trim();
                }
            }

            var filter_item_content = MyNnd_Find_Class(span, "filter-item-content");
            var filter_area = MyNnd_GetChild(filter_item_content, "app-condition-selector-more", 0);
            var filter_year = MyNnd_GetChild(filter_item_content, "app-condition-selector-more", 2);

            var filter_area_dropdown = MyNnd_Find_Class(filter_area, "dropdown");
            var as_area = MyNnd_Find_TagList(filter_area_dropdown, "A");
            for (var i = 0; i < as_area.length; i++) {
                var a_area = as_area[i];
                if (a_area.innerHTML.trim() != "全国" && a_area.innerHTML.trim() != "本省" && a_area.innerHTML.trim() != "香港" && a_area.innerHTML.trim() != "澳门" && a_area.innerHTML.trim() != "台湾") {
                    group.areas[group.areas.length++] = a_area.innerHTML.trim();
                }
            }

            var filter_year_dropdown = MyNnd_Find_Class(filter_year, "dropdown");
            var as_year = MyNnd_Find_TagList(filter_year_dropdown, "A");
            for (var i = 0; i < as_year.length; i++) {
                var a_year = as_year[i];
                if (a_year.innerHTML.trim() != "全部") {
                    group.years[group.years.length++] = a_year.innerHTML.trim();
                }
            }

            return group;
        }
    } catch (err) {
        alert(err.message);
    }
    return null;
}

function MyNnd_ClickKnowledge() {
    /* MyNnd_Log("MyNnd_ClickKnowledge", null); */
    var leftTree = MyNnd_Find_Class(document, "leftTree");
    if (leftTree != null) {
        /* MyNnd_Log("find leftTree", null); */
        var leftTree_div = MyNnd_GetChild(leftTree, "DIV", 2);
        var leftTree_app_tree = MyNnd_GetChild(leftTree_div, "app-tree", 0);
        var ztree = MyNnd_GetChild(leftTree_app_tree, "UL", 0);
        var roots_close_list = MyNnd_Find_ClassList(ztree, "roots_close");
        if (roots_close_list.length > 0) {
            MyNnd_Click_Delay_Callback(roots_close_list[0], 500, function () {
                MyNnd_ClickKnowledge();
            });
        } else {
            var bottom_close_list = MyNnd_Find_ClassList(ztree, "bottom_close");
            if (bottom_close_list.length > 0) {
                MyNnd_Click_Delay_Callback(bottom_close_list[0], 500, function () {
                    MyNnd_ClickKnowledge();
                });
            } else {
                var center_close_list = MyNnd_Find_ClassList(ztree, "center_close");
                if (center_close_list.length > 0) {
                    MyNnd_Click_Delay_Callback(center_close_list[0], 500, function () {
                        MyNnd_ClickKnowledge();
                    });
                } else {
                    MyNnd_Log("展开 knowledges over", null);
                    var phaseSubject = MyNnd_GetPhaseSubject();
                    var knowledges = MyNnd_GetKnowledge();
                    knowledges.phase = phaseSubject.phase;
                    knowledges.subject = phaseSubject.subject;
                    MyNnd_CallCmd1("resultKnowledges", JSON.stringify(knowledges), null);
                }
            }
        }
    }
}

function MyNnd_GetKnowledgeNode(li) {
    var knowledge = new Object();

    var li_span = MyNnd_GetChild(li, "SPAN", 0);
    var li_a = MyNnd_GetChild(li, "A", 0);
    var li_ul = MyNnd_GetChild(li, "UL", 0);
    var span = MyNnd_GetChild(li_a, "SPAN", 1);

    knowledge.index = li.getAttribute("id").trim().substring(37);
    knowledge.name = span.innerHTML.trim();

    if (li_ul != null) {
        knowledge.knowledges = new Array();
        for (var i = 0; i < li_ul.childNodes.length; i++) {
            var child = li_ul.childNodes[i];
            if (child.nodeName == "LI") {
                knowledge.knowledges[knowledge.knowledges.length++] = MyNnd_GetKnowledgeNode(child);
            }
        }
    }

    return knowledge;
}

function MyNnd_GetKnowledge() {
    /* MyNnd_Log("MyNnd_GetKnowledge", null); */
    var root = new Object();
    try {
        var leftTree = MyNnd_Find_Class(document, "leftTree");
        if (leftTree != null) {
            /* MyNnd_Log("find leftTree", null); */

            var leftTree_div = MyNnd_GetChild(leftTree, "DIV", 2);
            var leftTree_app_tree = MyNnd_GetChild(leftTree_div, "app-tree", 0);
            var ztree = MyNnd_GetChild(leftTree_app_tree, "UL", 0);

            root.knowledges = new Array();

            for (var i = 0; i < ztree.childNodes.length; i++) {
                var li = ztree.childNodes[i];
                if (li.nodeName == "LI") {
                    root.knowledges[root.knowledges.length++] = MyNnd_GetKnowledgeNode(li);
                }
            }

            return root;
        }
    } catch (err) {
        alert(err.message);
    }
    return null;
}

function MyNnd_ClickChapter(callback) {
    /* MyNnd_Log("MyNnd_ClickChapter", null); */
    var leftTree = MyNnd_Find_Class(document, "leftTree");
    if (leftTree != null) {
        /* MyNnd_Log("find leftTree", null); */
        var leftTree_div = MyNnd_GetChild(leftTree, "DIV", 1);
        var leftTree_app_tree = MyNnd_GetChild(leftTree_div, "app-tree", 0);
        var ztree = MyNnd_GetChild(leftTree_app_tree, "UL", 0);
        var roots_close_list = MyNnd_Find_ClassList(ztree, "roots_close");
        if (roots_close_list.length > 0) {
            MyNnd_Click_Delay_Callback(roots_close_list[0], 500, function () {
                MyNnd_ClickChapter(callback);
            });
        } else {
            var bottom_close_list = MyNnd_Find_ClassList(ztree, "bottom_close");
            if (bottom_close_list.length > 0) {
                MyNnd_Click_Delay_Callback(bottom_close_list[0], 500, function () {
                    MyNnd_ClickChapter(callback);
                });
            } else {
                var center_close_list = MyNnd_Find_ClassList(ztree, "center_close");
                if (center_close_list.length > 0) {
                    MyNnd_Click_Delay_Callback(center_close_list[0], 500, function () {
                        MyNnd_ClickChapter(callback);
                    });
                } else {
                    MyNnd_Log("展开 chapters over", null);
                    if (callback != null)
                        callback();
                }
            }
        }
    }
}

function MyNnd_CatchChapter() {
    MyNnd_ClickChapter(function () {
        var phaseSubject = MyNnd_GetPhaseSubject();
        var chapters = MyNnd_GetChapter();
        chapters.phase = phaseSubject.phase;
        chapters.subject = phaseSubject.subject;
        chapters.material = MyNnd_GetMaterial().material;
        MyNnd_CallCmd1("resultChapters", JSON.stringify(chapters), null);
    });
}

function MyNnd_GetChapterNode(li) {
    var chapter = new Object();

    var li_span = MyNnd_GetChild(li, "SPAN", 0);
    var li_a = MyNnd_GetChild(li, "A", 0);
    var li_ul = MyNnd_GetChild(li, "UL", 0);
    var span = MyNnd_GetChild(li_a, "SPAN", 1);

    chapter.index = li.getAttribute("id").trim().substring(37);
    chapter.name = span.innerHTML.trim();

    if (li_ul != null) {
        chapter.chapters = new Array();
        for (var i = 0; i < li_ul.childNodes.length; i++) {
            var child = li_ul.childNodes[i];
            if (child.nodeName == "LI") {
                chapter.chapters[chapter.chapters.length++] = MyNnd_GetChapterNode(child);
            }
        }
    }

    return chapter;
}

function MyNnd_GetChapter() {
    /* MyNnd_Log("MyNnd_GetChapter", null); */
    var root = new Object();
    try {
        var leftTree = MyNnd_Find_Class(document, "leftTree");
        if (leftTree != null) {
            /* MyNnd_Log("find leftTree", null); */

            var leftTree_div = MyNnd_GetChild(leftTree, "DIV", 1);
            var leftTree_app_tree = MyNnd_GetChild(leftTree_div, "app-tree", 0);
            var ztree = MyNnd_GetChild(leftTree_app_tree, "UL", 0);

            root.chapters = new Array();

            for (var i = 0; i < ztree.childNodes.length; i++) {
                var li = ztree.childNodes[i];
                if (li.nodeName == "LI") {
                    root.chapters[root.chapters.length++] = MyNnd_GetChapterNode(li);
                }
            }

            return root;
        }
    } catch (err) {
        alert(err.message);
    }
    return null;
}

function MyNnd_GetLeafChapterNode(li, chapterPath, chapterLeafs) {
    var li_span = MyNnd_GetChild(li, "SPAN", 0);
    var li_a = MyNnd_GetChild(li, "A", 0);
    var li_ul = MyNnd_GetChild(li, "UL", 0);
    var span = MyNnd_GetChild(li_a, "SPAN", 1);

    var name = span.innerHTML.trim();

    chapterPath = chapterPath + "---##---" + name;

    if (li_ul != null && li_ul.childNodes.length > 0) {
        for (var i = 0; i < li_ul.childNodes.length; i++) {
            var child = li_ul.childNodes[i];
            if (child.nodeName == "LI") {
                MyNnd_GetLeafChapterNode(child, chapterPath, chapterLeafs);
            }
        }
    } else {
        var chapterLeafPath = chapterPath;
        /* MyNnd_Log("chapterLeafPath : " + chapterLeafPath, null); */
        var chapterLeaf = new Object();
        chapterLeaf.path = chapterLeafPath;
        chapterLeaf.li_a = li_a;
        chapterLeafs[chapterLeafs.length++] = chapterLeaf;
    }
}

function MyNnd_GetLeafChapter() {
    /* MyNnd_Log("MyNnd_GetLeafChapter", null); */
    try {
        var leftTree = MyNnd_Find_Class(document, "leftTree");
        if (leftTree != null) {
            /* MyNnd_Log("find leftTree", null); */

            var leftTree_div = MyNnd_GetChild(leftTree, "DIV", 1);
            var leftTree_app_tree = MyNnd_GetChild(leftTree_div, "app-tree", 0);
            var ztree = MyNnd_GetChild(leftTree_app_tree, "UL", 0);

            var chapterLeafs = new Array();

            var chapterPath = "";

            for (var i = 0; i < ztree.childNodes.length; i++) {
                var li = ztree.childNodes[i];
                if (li.nodeName == "LI") {
                    MyNnd_GetLeafChapterNode(li, chapterPath, chapterLeafs);
                }
            }

            return chapterLeafs;
        }
    } catch (err) {
        alert(err.message);
    }
}

function MyNnd_FindNextLeafChapter(currChapterPath) {
    var chapterLeafs = MyNnd_GetLeafChapter();
    for (var i = 0; i < chapterLeafs.length; i++) {
        var chapterLeaf = chapterLeafs[i];
        if (currChapterPath == "") {
            return chapterLeaf;
        } else {
            if (currChapterPath == chapterLeaf.path) {
                currChapterPath = "";
            }
        }
    }
    return null;
}

function MyNnd_FindLeafChapter(currChapterPath) {
    var chapterLeafs = MyNnd_GetLeafChapter();
    for (var i = 0; i < chapterLeafs.length; i++) {
        var chapterLeaf = chapterLeafs[i];
        if (currChapterPath == chapterLeaf.path) {
            return chapterLeaf;
        }
    }
    return null;
}

function MyNnd_Test(currChapterPath) {
    MyNnd_ClickChapter(function () {
        var chapterLeaf = MyNnd_FindNextLeafChapter(currChapterPath);
        if (chapterLeaf != null) {
            MyNnd_Log("next chapterLeaf : " + chapterLeaf.path, null);
            MyNnd_MouseDown_Delay_Callback(chapterLeaf.li_a, 4000, function () {
                MyNnd_Log("222", null);
            });
        } else {
            MyNnd_Log("not find next chapterLeaf", null);
        }
    });
}

function MyNnd_ZhiHu_CatchOne(questionId, title, List_items, index) {
    MyNnd_Log("catch ----------------------- " + index + " / " + List_items.length, null);
    if (index < List_items.length) {
        var catchVotersNum = 0;
        for (var i = 0; i < List_items.length; i++) {
            var List_item = List_items[i];

            if (index == i) {
                List_item.setAttribute("style", "display: block;");

                /*
                 * var styleValue = List_item.getAttribute("style"); MyNnd_Log("styleValue " + styleValue, null);
                 *
                 * if (styleValue == "display: none;") { MyNnd_Log("not display", null); } else
                 */
                {
                    var ContentItem_meta = MyNnd_Find_Class(List_item, "ContentItem-meta");
                    if (ContentItem_meta != null) {
                        MyNnd_Log("find ContentItem_meta", null);

                        var Voters = MyNnd_Find_Class(ContentItem_meta, "Voters");
                        if (Voters != null) {
                            MyNnd_Log("find Voters", null);
                            MyNnd_Log("" + Voters.innerText.trim(), null);

                            var Voters_str = Voters.innerText.trim();
                            var VotersNum = Voters_str.substring(0, Voters_str.indexOf("人赞同了该回答")).trim().replace(new RegExp(",", "g"), "");
                            MyNnd_Log("VotersNum " + VotersNum, null);
                            catchVotersNum = parseInt(VotersNum);
                            /*
                             * if (parseInt(VotersNum) < 1000) { MyNnd_Log("will to delete ...", null); List_item.setAttribute("style", "display: none;"); }
                             */
                        }
                    }
                }
            } else {
                List_item.setAttribute("style", "display: none;");
            }

        }

        if (catchVotersNum < 1000) {
            MyNnd_ZhiHu_CatchPage(questionId, title, index + 1);
        } else {
            MyNnd_Delay(1000, function () {
                var info = new Object();
                info.questionId = questionId;
                info.title = title;
                info.index = index;
                info.catchVotersNum = catchVotersNum;
                MyNnd_CallCmd1("catchZhiXueOne", JSON.stringify(info), null);

                MyNnd_Delay(1000, function () {
                    MyNnd_ZhiHu_CatchPage(questionId, title, index + 1);
                });
            });
        }

    } else {
        /* MyNnd_Log("catch over !!!!!!!!!", null); */
        MyNnd_Log("index " + index + " is out", null);

        for (var i = 0; i < List_items.length; i++) {
            var List_item = List_items[i];
            List_item.setAttribute("style", "display: block;");
        }

        /*
         *
         *
         *
         *
         * MyNnd_Delay(3000, function() { MyNnd_CallCmd1("scrollToBottom", "", null); MyNnd_Delay(6000, function() { MyNnd_ZhiHu_CatchPage(questionId, title, index); }); });
         */

    }
}

function MyNnd_ZhiHu_CatchPage(questionId, title, index) {
    var QuestionAnswers_answers = MyNnd_Find_Id(document, "QuestionAnswers-answers");
    if (QuestionAnswers_answers != null) {
        MyNnd_Log("find QuestionAnswers_answers", null);

        var List = MyNnd_Find_Class(QuestionAnswers_answers, "List");
        if (List != null) {
            MyNnd_Log("find List", null);

            var List_header = MyNnd_GetChild(List, "DIV", 0);
            var List_Content = MyNnd_GetChild(List, "DIV", 1);

            var List_headerText = MyNnd_Find_Class(List_header, "List-headerText");
            if (List_headerText != null) {
                MyNnd_Log("find List_headerText", null);
                MyNnd_Log("" + List_headerText.innerText.trim(), null);
            }

            var List_items = MyNnd_Find_ClassList(List_Content, "List-item");
            if (List_items != null) {
                MyNnd_Log("List_items count ：" + List_items.length, null);
                MyNnd_ZhiHu_CatchOne(questionId, title, List_items, index);
            }
        }
    }
}

function MyNnd_ZhiHu_CatchStart() {
    MyNnd_Log("MyNnd_ZhiHu_CatchStart", null);

    var href = window.location.href;

    var questionId = href.substring(href.lastIndexOf("/") + 1);

    var QuestionHeader_title = MyNnd_Find_ClassExt(document, "QuestionHeader-title", 2, 2, 0);
    if (QuestionHeader_title != null) {
        var title = QuestionHeader_title.innerText.trim();
        MyNnd_Log("find QuestionHeader_title : " + title, null);

        MyNnd_ZhiHu_CatchPage(questionId, title, 0);
    }
}

function MyNnd_ZhiHu_Scroll() {
    var QuestionAnswers_answers = MyNnd_Find_Id(document, "QuestionAnswers-answers");
    if (QuestionAnswers_answers != null) {
        MyNnd_Log("find QuestionAnswers_answers", null);

        var List = MyNnd_Find_Class(QuestionAnswers_answers, "List");
        if (List != null) {
            MyNnd_Log("find List", null);

            var List_header = MyNnd_GetChild(List, "DIV", 0);
            var List_Content = MyNnd_GetChild(List, "DIV", 1);

            var List_headerText = MyNnd_Find_Class(List_header, "List-headerText");
            if (List_headerText != null) {
                MyNnd_Log("find List_headerText", null);
                MyNnd_Log("" + List_headerText.innerText.trim(), null);

                var Answers_str = List_headerText.innerText.trim();
                var AnswersNum = Answers_str.substring(0, Answers_str.indexOf("个回答")).trim().replace(new RegExp(",", "g"), "");
                MyNnd_Log("AnswersNum " + AnswersNum, null);

                var List_items = MyNnd_Find_ClassList(List_Content, "List-item");
                if (List_items != null) {
                    MyNnd_Log("List_items count ：" + List_items.length, null);
                    if (parseInt(AnswersNum) == List_items.length) {
                        MyNnd_Log("scroll over", null);
                        MyNnd_ZhiHu_CatchStart();
                    } else {
                        MyNnd_CallCmd1("scrollToBottom", "", null);
                        MyNnd_Delay(1000, function () {
                            MyNnd_ZhiHu_Scroll();
                        });
                    }
                }
            }

        }
    }
}

function MyNnd_ZhiHu() {
    MyNnd_Log("tttttttttttt", null);
    try {
        /* MyNnd_ZhiHu_Scroll(); */
        MyNnd_ZhiHu_CatchStart();
    } catch (err) {
        alert(err.message);
    }
}

// MyNnd_Main();
