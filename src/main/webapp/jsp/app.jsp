<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>应用管理</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<link rel="stylesheet" type="text/css" href="../css/app.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/app.js"></script>
</head>
<body>


<div class="content">


	<table id="dg" title="应用列表" class="easyui-datagrid" style="width:100%;height:100%"
		   url="./getList" toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true">
		<thead>
		<tr>
			<th field="pkgName" width="200">包名</th>
			<th field="channel" width="100">渠道</th>
			<th field="versionCode" width="100">版本号</th>
			<th field="versionName" width="100">版本名</th>
			<th field="probability" width="100">更新几率</th>
			<th field="name" width="100">应用名</th>
			<th field="size" width="100" formatter="formatSize">大小</th>
			<th field="apkUrl" width="300">链接</th>
			<th field="icoUrl" width="100">图标</th>
			<th field="des" width="100">描述</th>
		</tr>
		</thead>
		<%--<tbody>--%>
		<%--<c:forEach items="${appList}" var="app">--%>
		<%--<tr>--%>
		<%--<td>${app.pkgName}</td>--%>
		<%--<td>${app.channel}</td>--%>
		<%--<td>${app.versionCode}</td>--%>
		<%--<td>${app.versionName}</td>--%>
		<%--<td>${app.size}</td>--%>
		<%--<td>${app.apkUrl}</td>--%>
		<%--</tr>--%>
		<%--</c:forEach>--%>
		<%--</tbody>--%>
	</table>
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddItem()">添加</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditItem()">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="doDeleteItem()">删除</a>
	</div>


</div>


<div class="page">
	Copyright © 2017-2020 湖北众教弈科教育科技有限公司 | <a href="http://www.beian.miit.gov.cn/" target="_blank">鄂ICP备20012895号-1</a>
</div>


<div id="dlg" class="easyui-dialog" style="width:500px;height:300px;padding:10px 20px"
	 closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
		<%--<div class="fitem">--%>
		<%--<label>包名：</label>--%>
		<%--<input id="pkgName" name="pkgName">--%>
		<%--</div>--%>
		<%--<div class="fitem">--%>
		<%--<label>渠道：</label>--%>
		<%--<input id="channel" name="channel">--%>
		<%--</div>--%>
		<%--<div class="fitem">--%>
		<%--<label>版本号：</label>--%>
		<%--<input id="versionCode" name="versionCode">--%>
		<%--</div>--%>
		<%--<div class="fitem">--%>
		<%--<label>版本名：</label>--%>
		<%--<input id="versionName" name="versionName">--%>
		<%--</div>--%>
		<div class="fitem">
			<label>APK：</label>
			<input class="easyui-filebox" style="width:300px" id="apkFile" name="apkFile" accept=".apk">
		</div>
	</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doAddItem()">确定</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>

</body>
</html>