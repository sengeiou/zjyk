<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>考情分析</title>
	<link rel="icon" type="image/x-icon" href="../img/img_water_b.png">
	<link rel="stylesheet" type="text/css" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="../css/common.css">
	<link rel="stylesheet" type="text/css" href="../css/examkq.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
	<script type="text/javascript" src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="http://code.highcharts.com/highcharts.js"></script>
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="../js/examkq.js"></script>
</head>
<body>


<div class="container-fluid">
	<div class="row">
		<div class="col" style="padding: 0px">

			<div class="head-container">
				<div style="margin-top: 30px; margin-bottom: 10px; text-align: center;">
					<span id="studentName" style="font-size: 60px;"></span>
				</div>
				<div style="margin-top: 10px; margin-bottom: 30px; text-align: center;">
					<span id="paperName" style="font-size: 30px;"></span>
				</div>
			</div>

			<div class="part-container">
				<p style="font-size: 33px; color: #87c700;">总评语：</p>
				<pre id="noteAll" style="font-size: 33px; color: #666; margin-left: 30px; margin-right: 30px; white-space: pre-wrap; word-wrap: break-word;"></pre>
			</div>


			<div class="part-container">
				<div id="containerCodeLossScore" style="width: 100%; height: auto; margin: 0 auto"></div>
				<hr/>
				<div id="containerTypeLossScore" style="width: 100%; height: auto; margin: 0 auto"></div>
				<hr/>
				<div style="margin-top: 0px;  ">
					<p style="font-size: 33px; color: #fb795f;">老师点评：</p>
					<pre id="noteTypeLossScore" style="font-size: 33px; color: #666; margin-left: 30px; margin-right: 30px; white-space: pre-wrap; word-wrap: break-word;"></pre>
				</div>
			</div>


			<div class="part-container">
				<div id="containerPointLossScore" style="width: 100%; height: auto; margin: 0 auto"></div>
				<hr/>
				<div style="margin-top: 0px;  ">
					<p style="font-size: 33px; color: #998bfd;">老师点评：</p>
					<pre id="notePointLossScore" style="font-size: 33px; color: #666; margin-left: 30px; margin-right: 30px; white-space: pre-wrap; word-wrap: break-word;"></pre>
				</div>
			</div>


			<div class="part-container">
				<div id="containerDifficultyLossScore" style="width: 100%; height: auto; margin: 0 auto"></div>
				<hr/>
				<div style="margin-top: 0px;  ">
					<p style="font-size: 33px; color: #fb795f;">老师点评：</p>
					<pre id="noteDifficultyLossScore" style="font-size: 33px; color: #666; margin-left: 30px; margin-right: 30px; white-space: pre-wrap; word-wrap: break-word;"></pre>
				</div>
			</div>


			<div class="part-container">
				<div id="containerErrorTypeLossScore" style="width: 100%; height: auto; margin: 0 auto"></div>
				<hr/>
				<div style="margin-top: 0px; ">
					<p style="font-size: 33px; color: #6ec703;">老师点评：</p>
					<pre id="noteErrorTypeLossScore" style="font-size: 33px; color: #666; margin-left: 30px; margin-right: 30px; white-space: pre-wrap; word-wrap: break-word;"></pre>
				</div>
			</div>


			<div class="part-container">
				<p style="font-size: 33px; color: #6194f9;">提分方案：</p>
				<pre id="noteOffer" style="font-size: 33px; color: #666; margin-left: 30px; margin-right: 30px; white-space: pre-wrap; word-wrap: break-word;"></pre>
			</div>

		</div>
	</div>
</div>


<script type="text/javascript">
    var record = ${record};
    var codeLossScoreList = ${codeLossScoreList};
    var typeLossScoreList = ${typeLossScoreList};
    var pointLossScoreList = ${pointLossScoreList};
    var difficultyLossScoreList = ${difficultyLossScoreList};
    var errorTypeLossScoreList = ${errorTypeLossScoreList};
</script>


</body>
</html>