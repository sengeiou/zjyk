<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>事件管理</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<link rel="stylesheet" type="text/css" href="../css/event.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/event.js"></script>
</head>
<body>


<div class="content">


	<table id="dg" title="事件列表" class="easyui-datagrid" style="width:100%;height:100%;" pagination="true"
		   toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true" nowrap="false">
		<thead>
		<tr>
			<th field="user" width="50" formatter="formatUser">用户</th>
			<th field="action" width="50">动作</th>
			<th field="target" width="120">目标</th>
			<th field="des" width="300" formatter="formatKeywords">描述</th>
			<th field="time" width="80" formatter="formatDate">时间</th>
		</tr>
		</thead>
	</table>
	<div id="toolbar" style="padding: 10px">

		<%--<label style="margin-left: 20px">用户类型：</label>--%>
		<%--<select id="userType" class="easyui-combobox" data-options="panelHeight:'auto'" style="width: 150px;">--%>
		<%--<option value="0">全部</option>--%>
		<%--<option value="1">超级管理员</option>--%>
		<%--<option value="2">管理员</option>--%>
		<%--<option value="3">教师</option>--%>
		<%--<option value="4">学生</option>--%>
		<%--</select>--%>

		<%--<label style="margin-left: 20px">有电话：</label>--%>
		<%--<input type="checkbox" id="hasPhone">--%>

		<label style="margin-left: 10px">用户：</label>
		<input id="userId" class="easyui-textbox" style="width: 100px">

		<label style="margin-left: 10px">动作：</label>
		<input id="action" class="easyui-textbox" style="width: 100px">

		<label style="margin-left: 10px">目标：</label>
		<input id="target" class="easyui-textbox" style="width: 300px">

		<span style="margin-left: 20px">
			<input id="beginTime" class="easyui-datebox" style="width:180px">
			<label> ~ </label>
			<input id="endTime" class="easyui-datebox" style="width:180px">
		</span>

		<span style="margin-left: 20px">
			<input id="keywords" class="easyui-textbox" style="width: 500px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</span>


	</div>


</div>


</body>
</html>