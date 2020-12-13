<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>云空间</title>
	<link rel="icon" type="image/x-icon" href="./img/img_water_a.png">
	<script type="text/javascript" src="./easyui/jquery.min.js"></script>
	<script type="text/javascript" src='./js/spark-md5.min.js'></script>
	<script type="text/javascript" src="./js/common.js"></script>
	<script type="text/javascript" src="./js/cloud.js"></script>
</head>
<body>

<div name="single" style="text-align: center;">
	<A class=btn_addPic href="javascript:void(0);"> <SPAN>选择文件</SPAN> <input id="file" class="filePrew" type="file"
																			 name="file" multiple="multiple" onchange="javascript:onc();"/>
	</A>
</div>
<div name="dropbox" id="dropbox"
	 style="font-size: 30px; color: #333333; background-color: #888888; min-width: 300px; min-height: 300px; border: 3px dashed silver;">
	<p style="line-height: 300px; text-align: center;">拖拽文件上传</p>
</div>
<!-- <div style="text-align: center;">
	<button class="button" type="submit" onclick="javascript:Upload();">上传</button>
</div> -->
<div id="fileliststring" style="background-color: #cccccc; color: #333333;"></div>

</body>
</html>