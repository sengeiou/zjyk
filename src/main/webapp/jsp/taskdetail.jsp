<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>task</title>
	<link rel="icon" type="image/x-icon" href="../img/img_water_b.png">
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<%--<script type="text/javascript" src='https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js'></script>--%>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/taskdetail.js"></script>
</head>
<body>

<div id='page' class='abs_pos' style='left: 0px; top: 0px; width: 100%; height: 100%;'>
	<div id='message' class='abs_pos' style='top: 30px; width: 100%; text-align: center;'>加载中。。。</div>
</div>
<img id='nav_prev' class="fixed_pos" src="../img/img_prev.png"
	 style="left: 0px; top: 0px; width: 56px; height: 100px; opacity: 0.4; display: none;">
<img id='nav_next' class="fixed_pos" src="../img/img_next.png"
	 style="right: 0px; top: 0px; width: 56px; height: 100px; opacity: 0.4; display: none;">
<div id="outerdiv"
	 style="position: fixed; top: 0; left: 0; background: rgba(0, 0, 0, 0.5); z-index: 2; width: 100%; height: 100%; display: none;">
	<div id="innerdiv" style="position: absolute;">
		<img id="bigimg" style="border: 0px solid #f00; background: #fff;" src=""/>
	</div>
</div>


<script type="text/javascript">
    var urlRoot = "${urlRoot}";
    var taskId = "${taskId}";
    var taskPage = "${taskPage}";
</script>


</body>
</html>