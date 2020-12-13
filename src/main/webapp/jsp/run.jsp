<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>运行状态</title>
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/run.js"></script>
</head>
<body>

<div>
	<span id='startTime'>启动时间：</span>
</div>
<div>
	<span id='startTimeLen'>启动时长：</span>
</div>
<div>
	<button id='switchButton' type='button' onclick='switchStopState()'></button>
</div>
<div>
	<span>Logic进行中任务数：${logicRunningCount}</span>
</div>

<script type="text/javascript">
    var startTime = ${startTime};
    var stop = ${stop};
</script>

</body>
</html>