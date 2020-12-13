<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>管理后台</title>
	<link rel="stylesheet" type="text/css" href="./easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="./easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="./easyui/demo/demo.css">
	<link rel="stylesheet" type="text/css" href="./css/common.css">
	<link rel="stylesheet" type="text/css" href="./css/admin.css">
</head>
<body>


<div class="nav">

	<%--<div class="nav"  style="background-color: #b2dbfb; width: 100px; height: 20px">--%>
	<%--</div>--%>

	<a class="logo" href="./admin">
		<img src="./img/logo.png"/>
	</a>

	<a class="nav clear" href="./app/page" target="iframe_content">应用管理</a>
	<a class="nav clear" href="./live/page" target="iframe_content">直播管理</a>
	<a class="nav clear" href="./task/page" target="iframe_content">任务管理</a>
	<a class="nav clear" href="./user/page" target="iframe_content">用户管理</a>
	<a class="nav clear" href="./event/page" target="iframe_content">事件管理</a>

</div>


<div class="content">
	<iframe class="content" src="./event/page" name="iframe_content"></iframe>
</div>


<div class="beian">
	Copyright © 2017-2020 湖北众教弈科教育科技有限公司 | <a href="http://www.beian.miit.gov.cn/" target="_blank">鄂ICP备20012895号-1</a>
</div>

</body>
</html>