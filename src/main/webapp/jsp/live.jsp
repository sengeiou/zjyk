<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>直播管理</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/live.js"></script>
</head>
<body style="background-color: white">

<table id="dg" title="直播列表" class="easyui-datagrid" style="width:100%;height:700px"
	   url="./getList" rownumbers="true" fitColumns="true" singleSelect="true" nowrap="false">
	<thead>
	<tr>
		<%--<th data-options="field:'id'" width="100">ID</th>--%>
		<th data-options="field:'name'" width="200">标题</th>
		<th data-options="field:'des'" width="150">描述</th>
		<th data-options="field:'actorId'" width="70">讲师</th>
		<%--<th data-options="field:'cover'" width="100">封面</th>--%>
		<%--<th data-options="field:'video'" width="100">视频</th>--%>
		<th data-options="field:'type'" width="100" formatter="formatType">类型</th>
		<th data-options="field:'duration'" width="100" formatter="formatTime">时长</th>
		<%--<th data-options="field:'taskId'" width="100">任务</th>--%>
		<th data-options="field:'startTime'" width="200" formatter="formatDate">开始时间</th>
		<th data-options="field:'userIds'" width="500">目标</th>
	</tr>
	</thead>
	<%--<tbody>--%>
	<%--<c:forEach items="${liveList}" var="live">--%>
	<%--<tr>--%>
	<%--<td>${live.id}</td>--%>
	<%--<td>${live.actorId}</td>--%>
	<%--<td>${live.name}</td>--%>
	<%--<td>${live.des}</td>--%>
	<%--<td>${live.cover}</td>--%>
	<%--<td>${live.video}</td>--%>
	<%--<td>${live.taskId}</td>--%>
	<%--<td>${live.type}</td>--%>
	<%--<td>${live.userIds}</td>--%>
	<%--<td>${live.startTime}</td>--%>
	<%--</tr>--%>
	<%--</c:forEach>--%>
	<%--</tbody>--%>
</table>
<%--<div id="toolbar">--%>
<%--<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddItem()">添加</a>--%>
<%--<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditItem()">修改</a>--%>
<%--<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="doDeleteItem()">删除</a>--%>
<%--</div>--%>

<div id="dlg" class="easyui-dialog" style="width:700px;height:500px;padding:10px 20px"
	 closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
		<div class="fitem" style="padding:5px 0px">
			<label>ID：</label>
			<input id="id" name="id" class="easyui-textbox" style="width:300px" editable="false" disabled="true">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>讲师：</label>
			<input id="actorId" name="actorId" class="easyui-textbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>标题：</label>
			<input id="name" name="name" class="easyui-textbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>描述：</label>
			<input id="des" name="des" class="easyui-textbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>封面：</label>
			<input class="easyui-filebox" style="width:300px" id="coverFile" name="coverFile" accept=".png, .jpg, .jpeg">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>视频：</label>
			<input class="easyui-filebox" style="width:300px" id="videoFile" name="videoFile" accept=".mp4">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>任务：</label>
			<input id="taskId" name="taskId" class="easyui-textbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>类型：</label>
			<input type="radio" data-toggle="topjui-radiobutton" name="type" value="0">私有
			<input type="radio" data-toggle="topjui-radiobutton" name="type" value="1">公开
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>目标：</label>
			<input id="userIds" name="userIds" class="easyui-tagbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>开始时间：</label>
			<input id="startTime" name="startTime" class="easyui-datetimebox" data-options="required:true,showSeconds:false" style="width:300px">
		</div>
	</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doAddItem()">确定</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>

</body>
</html>