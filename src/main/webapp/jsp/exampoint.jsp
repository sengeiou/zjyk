<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>考点列表</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<link rel="stylesheet" type="text/css" href="../css/exampoint.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/exampoint.js"></script>
</head>
<body style="background-color: white">


<div class="content">


	<table id="dg" title="考点列表" class="easyui-datagrid" style="width:100%;height:100%;" pagination="true"
		   url="./search" toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true" nowrap="false">
		<thead>
		<tr>
			<th field="id" width="100">ID</th>
			<th field="chapter" width="100">章节</th>
			<th field="point" width="100">考点</th>
			<th field="difficulty" width="50">难度</th>
			<th field="importance" width="50">重要度</th>
			<th field="frequency" width="100">考频</th>
			<th field="note" width="100">备注</th>
		</tr>
		</thead>
	</table>
	<div id="toolbar" style="padding: 10px">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddItem()">添加</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditItem()">修改</a>
		<%--<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="doDeleteItem()">删除</a>--%>
		<span style="margin-left: 20px">
			<input id="keywords" class="easyui-textbox" style="width: 500px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
	</span>
	</div>

</div>


<div id="dlg" class="easyui-dialog" style="width:700px;height:500px;padding:10px 20px"
	 closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
		<div class="fitem" style="padding:5px 0px">
			<label>ID：</label>
			<input id="id" name="id" class="easyui-textbox" style="width:300px" editable="false" disabled="true">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>章节：</label>
			<input id="chapter" name="chapter" class="easyui-textbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>考点：</label>
			<input id="point" name="point" class="easyui-textbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>难度：</label>
			<input id="difficulty" name="difficulty" class="easyui-numberbox" min="1" max="5">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>重要度：</label>
			<input id="importance" name="importance" class="easyui-numberbox" min="1" max="5">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>考频：</label>
			<input id="frequency" name="frequency" class="easyui-textbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>备注：</label>
			<input id="note" name="note" class="easyui-textbox" style="width:300px">
		</div>
	</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doAddItem()">确定</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>

</body>
</html>