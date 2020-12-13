<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>考试管理</title>
	<link rel="stylesheet" type="text/css" href="./easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="./easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="./easyui/demo/demo.css">
	<link rel="stylesheet" type="text/css" href="./css/common.css">
	<link rel="stylesheet" type="text/css" href="./css/exam.css">
</head>
<body>


<div class="nav">

	<%--<div class="nav"  style="background-color: #b2dbfb; width: 100px; height: 20px">--%>
	<%--</div>--%>

	<a class="logo" href="./admin">
		<img src="./img/logo.png"/>
	</a>

	<a class="nav clear" href="./exampoint/page" target="iframe_content">考点列表</a>
	<a class="nav clear" href="./exampaper/page" target="iframe_content">试卷列表</a>
	<a class="nav clear" href="./examrecord/page" target="iframe_content">考生记录</a>

</div>


<div class="content">
	<iframe class="content" src="./examrecord/page" name="iframe_content"></iframe>
</div>


<div class="beian">
	Copyright © 2017-2020 湖北众教弈科教育科技有限公司 | <a href="http://www.beian.miit.gov.cn/" target="_blank">鄂ICP备20012895号-1</a>
</div>

</body>
</html>