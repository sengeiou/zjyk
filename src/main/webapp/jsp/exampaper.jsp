<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>试卷列表</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<link rel="stylesheet" type="text/css" href="../css/exampaper.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/exampaper.js"></script>
</head>
<body style="background-color: white">


<div class="content">


	<table id="dg" title="试卷列表" class="easyui-datagrid" style="width:100%;height:100%;" pagination="true"
		   url="./search" toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true" nowrap="false">
		<thead>
		<tr>
			<th field="id" width="120" formatter="formatId">ID</th>
			<th field="name" width="100">名字</th>
			<th field="time" width="80" formatter="formatDate">日期</th>
			<th field="files" width="100">文件</th>
			<th field="note" width="100">备注</th>
			<th field="topics" width="100" formatter="formatTopic">试题</th>
		</tr>
		</thead>
	</table>
	<div id="toolbar" style="padding: 10px">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddItem()">添加</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditItem()">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="doDeleteItem()">删除</a>
		<span style="margin-left: 20px">
			<input id="keywords" class="easyui-textbox" style="width: 500px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</span>
	</div>


</div>


<div id="dlg" class="easyui-dialog" style="width:80%;height:600px;padding:10px 20px"
	 closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
		<div class="fitem" style="padding:5px 0px">
			<label>ID：</label>
			<input id="id" name="id" class="easyui-textbox" style="width:300px" editable="false" disabled="true">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>名字：</label>
			<input id="name" name="name" class="easyui-textbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>日期：</label>
			<input id="time" name="time" class="easyui-datetimebox" data-options="required:true,showSeconds:false" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>备注：</label>
			<input id="note" name="note" class="easyui-textbox" style="width:300px">
		</div>


		<div class="fitem" style="padding:5px 0px">
			<a href="#" class="easyui-linkbutton" onclick="perPaparClear()">清空</a>
			<a href="#" class="easyui-linkbutton" onclick="perPaparA()">结构A</a>
			<a href="#" class="easyui-linkbutton" onclick="perPaparB()">结构B</a>
		</div>

		<table id="topics" style="width:1000px;">
			<tr>
				<th style="width:100px">题号</th>
				<th style="width:100px">题型</th>
				<th style="width:100px">分值</th>
				<th style="width:100px">难度</th>
				<th style="width:600px">知识点</th>
				<%--<th style="width:200px">操作</th>--%>
			</tr>


			<%
				for (int i = 0; i < 50; i++)
				{
			%>
			<tr class="topic topic<%= i %>">
				<td><input class="code easyui-textbox" style="width:100px"></td>
				<td>
					<select class="type easyui-combobox" data-options="panelHeight:'auto'" style="width: 100px;">
						<option value="0">单选题</option>
						<option value="1">多选题</option>
						<option value="2">填空题</option>
						<option value="3">解答题</option>
					</select>
				</td>
				<td><input class="allScore easyui-numberbox" min="1" style="width:100px"></td>
				<td><input class="difficulty easyui-numberbox" min="1" max="3" style="width:100px"></td>
				<td>
					<input class="pointName easyui-textbox" style="width:200px" editable="false">
					<a href="#" class="easyui-linkbutton" onclick="selectPoint(<%= i %>)">选择</a>
					<span class="pointId"></span>
					<%--<span class="pointId" style="display:none;"></span>--%>
				</td>
				<%--<td>--%>
				<%--<a href="#" class="easyui-linkbutton" onclick="insertTopic()">插入</a>--%>
				<%--<a href="#" class="easyui-linkbutton" onclick="deleteTopic()">删除</a>--%>
				<%--</td>--%>
			</tr>
			<%}%>

		</table>


	</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doAddItem()">确定</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>


<div id="dlgs" class="easyui-dialog" style="width:80%;height:600px;padding:10px 20px"
	 closed="true" buttons="#dlgs-buttons">


	<table id="dgs" class="easyui-datagrid" style="width:100%;height:100%;" pagination="true"
		   url="../exampoint/search" toolbar="#toolbars" rownumbers="true" fitColumns="true" singleSelect="true" nowrap="false">
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
	<div id="toolbars" style="padding: 10px">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddItems()">添加</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditItems()">修改</a>
		<%--<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="doDeleteItems()">删除</a>--%>
		<span style="margin-left: 20px">
			<input id="keywordss" class="easyui-textbox" style="width: 500px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearchs()">查询</a>
		</span>
	</div>

</div>
<div id="dlgs-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doSelectPoint()">确定</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgs').dialog('close')">取消</a>
</div>




<div id="dlg2s" class="easyui-dialog" style="width:700px;height:500px;padding:10px 20px"
	 closed="true" buttons="#dlg2s-buttons">
	<form id="fm2s" method="post">
		<div class="fitem" style="padding:5px 0px">
			<label>ID：</label>
			<input id="id2s" name="id" class="easyui-textbox" style="width:300px" editable="false" disabled="true">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>章节：</label>
			<input id="chapter2s" name="chapter" class="easyui-textbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>考点：</label>
			<input id="point2s" name="point" class="easyui-textbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>难度：</label>
			<input id="difficulty2s" name="difficulty" class="easyui-numberbox" min="1" max="5">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>重要度：</label>
			<input id="importance2s" name="importance" class="easyui-numberbox" min="1" max="5">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>考频：</label>
			<input id="frequency2s" name="frequency" class="easyui-textbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>备注：</label>
			<input id="note2s" name="note" class="easyui-textbox" style="width:300px">
		</div>
	</form>
</div>
<div id="dlg2s-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doAddItems()">确定</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg2s').dialog('close')">取消</a>
</div>





</body>
</html>