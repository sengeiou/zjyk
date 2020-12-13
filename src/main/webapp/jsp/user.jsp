<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>用户管理</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<link rel="stylesheet" type="text/css" href="../css/user.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/user.js"></script>
</head>
<body>


<div class="content">


	<table id="dg" title="用户列表" class="easyui-datagrid" style="width:100%;height:100%;" pagination="true"
		   toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true" nowrap="false">
		<thead>
		<tr>
			<th field="id" width="100" formatter="formatKeywords">ID</th>
			<th field="userType" width="100" formatter="formatUserType">用户类型</th>
			<th field="useRong" width="100" formatter="formatUseRong">使用融云</th>
			<th field="psw" width="100">密码</th>
			<th field="name" width="100" formatter="formatKeywords">用户名</th>
			<th field="token" width="100">token</th>
			<th field="createTime" width="200" formatter="formatDate">创建时间</th>
			<th field="sex" width="100" formatter="formatSex">性别</th>
			<th field="grade" width="100" formatter="formatGrade">年级</th>
			<th field="phone" width="100" formatter="formatKeywords">电话</th>
			<th field="ts" width="200">TS</th>
		</tr>
		</thead>
	</table>
	<div id="toolbar" style="padding: 10px">

		<label style="margin-left: 20px">用户类型：</label>
		<select id="userType" class="easyui-combobox" data-options="panelHeight:'auto'" style="width: 150px;">
			<option value="0">全部</option>
			<option value="1">超级管理员</option>
			<option value="2">管理员</option>
			<option value="3">教师</option>
			<option value="4">学生</option>
		</select>

		<label style="margin-left: 20px">有电话：</label>
		<input type="checkbox" id="hasPhone">

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