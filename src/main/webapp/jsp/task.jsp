<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>任务管理</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<link rel="stylesheet" type="text/css" href="../css/task.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/task.js"></script>
</head>
<body>


<div class="content">


	<table id="dg" title="任务列表" class="easyui-datagrid" style="width:100%;height:100%;" pagination="true"
		   toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true" nowrap="false">
		<thead>
		<tr>
			<th field="id" width="220" formatter="formatKeywords">ID</th>
			<th field="user" width="120" formatter="formatUser">所有人</th>
			<th field="group" width="100" formatter="formatKeywords">组名</th>
			<th field="name" width="200" formatter="formatKeywords">任务名</th>
			<th field="type" width="50" formatter="formatType">类型</th>
			<th field="jobType" width="100" formatter="formatJobType">作业类型</th>
			<th field="createTime" width="200" formatter="formatDate">创建时间</th>
			<th field="timesForWeb" width="50" formatter="formatTimes">访问次数</th>
			<th field="sendUser" width="120" formatter="formatUser">发送者</th>
			<th field="opt" width="120" formatter="formatOpt">操作</th>
		</tr>
		</thead>
	</table>
	<div id="toolbar" style="padding: 10px">

		<label style="margin-left: 20px">类型：</label>
		<select id="type" class="easyui-combobox" data-options="panelHeight:'auto'" style="width: 70px;">
			<option value="-1">全部</option>
			<option value="0">未知</option>
			<option value="1">作业</option>
			<option value="2">课程</option>
		</select>

		<label style="margin-left: 20px">作业类型：</label>
		<select id="jobType" class="easyui-combobox" data-options="panelHeight:'auto'" style="width: 100px;">
			<option value="-1">全部</option>
			<option value="0">默认</option>
			<option value="1">仅选择题</option>
			<option value="2">混合题型</option>
		</select>

		<label style="margin-left: 20px">访问次数：</label>
		<input id="timesForWeb" class="easyui-textbox" style="width: 50px">

		<span style="margin-left: 20px">
			<input id="beginTime" class="easyui-datebox" style="width:140px">
			<label> ~ </label>
			<input id="endTime" class="easyui-datebox" style="width:140px">
		</span>

		<span style="margin-left: 20px">
			<input id="keywords" class="easyui-textbox" style="width: 500px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</span>


	</div>


</div>


<div class="async-load">


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

</div>


</body>
</html>