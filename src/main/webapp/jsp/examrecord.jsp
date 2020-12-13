<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>考生记录</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<link rel="stylesheet" type="text/css" href="../css/examrecord.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/examrecord.js"></script>
</head>
<body style="background-color: white">


<div class="content">


	<table id="dg" title="考生记录" class="easyui-datagrid" style="width:100%;height:100%;" pagination="true"
		   url="./search" toolbar="#toolbar" rownumbers="true" fitColumns="true" singleSelect="true" nowrap="false">
		<thead>
		<tr>
			<%--<th field="id" width="100">ID</th>--%>
			<th field="studentName" width="100" formatter="formatStudentName">学生</th>
			<th field="paper" width="100" formatter="formatPaper">试卷</th>
			<th field="selectTime" width="100" formatter="formatTimeMin">选择题时长</th>
			<th field="gapTime" width="100" formatter="formatTimeMin">填空题时长</th>
			<th field="answerTime" width="100" formatter="formatTimeMin">解答题时长</th>
			<th field="mindset" width="100" formatter="formatMindset">心态</th>
			<th field="note" width="100">备注</th>
			<th field="topics" width="100" formatter="formatTopic">失分题</th>
			<%--<th field="notePoint" width="100">考点点评</th>--%>
			<%--<th field="noteDifficulty" width="100">难度点评</th>--%>
			<%--<th field="noteErrorType" width="100">错误类型点评</th>--%>
			<%--<th field="noteAll" width="100">总点评</th>--%>
			<th field="opt" width="120" formatter="formatOpt">操作</th>
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
			<label>学生：</label>
			<input id="studentName" name="studentName" class="easyui-textbox" style="width:300px">
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>试卷：</label>
			<input id="paperName" name="paperName" class="easyui-textbox" style="width:300px" editable="false">
			<a href="#" class="easyui-linkbutton" onclick="selectPaper()">选择</a>
			<span id="paperId" name="paperId"></span>
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>选择题时长：</label>
			<input id="selectTime" name="selectTime" class="easyui-numberbox" min="0" style="width:200px">
			<span>分钟</span>
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>填空题时长：</label>
			<input id="gapTime" name="gapTime" class="easyui-numberbox" min="0" style="width:200px">
			<span>分钟</span>
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>解答题时长：</label>
			<input id="answerTime" name="answerTime" class="easyui-numberbox" min="0" style="width:200px">
			<span>分钟</span>
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>心态：</label>
			<select id="mindset" name="mindset" class="easyui-combobox" data-options="panelHeight:'auto'" style="width: 250px;">
				<option value="0">一般般</option>
				<option value="1">紧张</option>
				<option value="2">放松</option>
			</select>
		</div>
		<div class="fitem" style="padding:5px 0px">
			<label>备注：</label>
			<input id="note" name="note" class="easyui-textbox" style="width:300px">
		</div>


		<table id="topics" style="width:700px;">
			<tr>
				<th style="width:100px">题号</th>
				<th style="width:100px">分值</th>
				<th style="width:100px">得分</th>
				<th style="width:100px">错误类型</th>
				<th style="width:100px">难度</th>
				<th style="width:200px">知识点</th>
			</tr>


			<%
				for (int i = 0; i < 50; i++)
				{
			%>
			<tr class="topic topic<%= i %>">
				<td><input class="code easyui-textbox" style="width:100px" editable="false"></td>
				<td><input class="allScore easyui-numberbox" min="1" style="width:100px" editable="false"></td>
				<td><input class="gotScore easyui-numberbox" min="0" style="width:100px"></td>
				<td>
					<select class="errorType easyui-combobox" data-options="panelHeight:'auto'" style="width: 100px;">
						<option value="0">A</option>
						<option value="1">B</option>
						<option value="2">C</option>
						<option value="3">D</option>
					</select>
				</td>
				<td><input class="difficulty easyui-numberbox" min="1" max="3" style="width:100px" editable="false"></td>
				<td><input class="pointName easyui-textbox" style="width:200px" editable="false"></td>
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
		   url="../exampaper/search" toolbar="#toolbars" rownumbers="true" fitColumns="true" singleSelect="true" nowrap="false">
		<thead>
		<tr>
			<th field="id" width="120">ID</th>
			<th field="name" width="100">名字</th>
			<th field="time" width="80" formatter="formatDate">日期</th>
			<th field="files" width="100">文件</th>
			<th field="note" width="100">备注</th>
			<th field="topics" width="100" formatter="formatTopic">试题</th>
		</tr>
		</thead>
	</table>
	<div id="toolbars" style="padding: 10px">
		<span style="margin-left: 20px">
			<input id="keywordss" class="easyui-textbox" style="width: 500px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearchs()">查询</a>
		</span>
	</div>

</div>
<div id="dlgs-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="doSelectPaper()">确定</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgs').dialog('close')">取消</a>
</div>


</body>
</html>