<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>主页</title>
	<link rel="stylesheet" type="text/css" href="./easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="./easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="./easyui/demo/demo.css">
	<script type="text/javascript" src="./easyui/jquery.min.js"></script>
	<script type="text/javascript" src="./easyui/jquery.easyui.min.js"></script>
</head>
<body>
<div class="easyui-layout" style="width: 100%; height: 100%;">
	<a href="./actuator" class="easyui-linkbutton">actuator</a>
	<a href="./actuator/health" class="easyui-linkbutton">actuator/health</a>
	<a href="./info" class="easyui-linkbutton">info</a>
	<a href="./config" class="easyui-linkbutton">config</a>
	<a href="./file/page" class="easyui-linkbutton">上传文件</a>
	<a href="./run/page" class="easyui-linkbutton">运行状态</a>
	<a href="./cloud" class="easyui-linkbutton">云空间</a>
	<a href="./admin" class="easyui-linkbutton">admin</a>
</div>
</body>
</html>