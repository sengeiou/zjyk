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

			<img src="../img/exam_head_bg.png" style="position:absolute; width: 100%; left:0px; top:0px; z-index:-1;"/>

			<div class="head-container">
				<div style="margin-top: 30px; margin-bottom: 10px; text-align: center;">
					<span id="studentName" style="font-size: 60px; color: white;"></span>
				</div>
				<div style="margin-top: 10px; margin-bottom: 30px; text-align: center;">
					<span id="paperName" style="font-size: 30px; color: white;"></span>
				</div>
			</div>

			<div class="part-container">
				<div style="margin-top: 0px; margin-bottom: 0px; text-align: center;">
					<img src="../img/flagall.png" style="margin-bottom: 20px; width: 35px; height: 42px;"/>
					<span style="font-size: 36px; color: #ea8f47; margin-left: 5px;">总评语</span>
				</div>
				<div style="margin-top: 0px; margin-bottom: 20px; text-align: center;">
					<img style="background-color: #ea8f47; padding: 2px 40px;"/>
				</div>
				<pre id="noteAll" style="font-size: 33px; color: #666; margin-left: 30px; margin-right: 30px; white-space: pre-wrap; word-wrap: break-word;"></pre>
			</div>


			<div class="part-container">
				<div style="margin-top: 0px; margin-bottom: 0px; text-align: center;">
					<img src="../img/flag1.png" style="margin-bottom: 20px; width: 35px; height: 42px;"/>
					<span style="font-size: 36px; color: #ffc955; margin-left: 5px;">题型得分情况</span>
				</div>
				<div style="margin-top: 0px; margin-bottom: 0px; text-align: center;">
					<img style="background-color: #ffc955; padding: 2px 40px;"/>
				</div>
				<div id="containerCodeLossScore" style="width: 100%; height: auto; margin: 0 auto"></div>
				<hr/>
				<div id="containerTypeLossScore" style="width: 100%; height: auto; margin: 0 auto"></div>
				<hr/>
				<div style="margin-top: 0px;  ">
					<p style="font-size: 33px; color: #ffc955;">老师点评：</p>
					<pre id="noteTypeLossScore" style="font-size: 33px; color: #666; margin-left: 30px; margin-right: 30px; white-space: pre-wrap; word-wrap: break-word;"></pre>
				</div>
				<img src="../img/part1.png" style="position:absolute; right:0px; top:0px;"/>
			</div>


			<div class="part-container">
				<div style="margin-top: 0px; margin-bottom: 0px; text-align: center;">
					<img src="../img/flag2.png" style="margin-bottom: 20px; width: 35px; height: 42px;"/>
					<span style="font-size: 36px; color: #998bfd; margin-left: 5px;">考点失分</span>
				</div>
				<div style="margin-top: 0px; margin-bottom: 0px; text-align: center;">
					<img style="background-color: #998bfd; padding: 2px 40px;"/>
				</div>
				<div id="containerPointLossScore" style="width: 100%; height: auto; margin: 0 auto"></div>
				<hr/>
				<div style="margin-top: 0px;  ">
					<p style="font-size: 33px; color: #998bfd;">老师点评：</p>
					<pre id="notePointLossScore" style="font-size: 33px; color: #666; margin-left: 30px; margin-right: 30px; white-space: pre-wrap; word-wrap: break-word;"></pre>
				</div>
				<img src="../img/part2.png" style="position:absolute; right:0px; top:0px;"/>
			</div>


			<div class="part-container">
				<div style="margin-top: 0px; margin-bottom: 0px; text-align: center;">
					<img src="../img/flag3.png" style="margin-bottom: 20px; width: 35px; height: 42px;"/>
					<span style="font-size: 36px; color: #6ec801; margin-left: 5px;">难度得分</span>
				</div>
				<div style="margin-top: 0px; margin-bottom: 0px; text-align: center;">
					<img style="background-color: #6ec801; padding: 2px 40px;"/>
				</div>
				<div id="containerDifficultyLossScore" style="width: 100%; height: auto; margin: 0 auto"></div>
				<hr/>
				<div style="margin-top: 0px;  ">
					<p style="font-size: 33px; color: #6ec801;">老师点评：</p>
					<pre id="noteDifficultyLossScore" style="font-size: 33px; color: #666; margin-left: 30px; margin-right: 30px; white-space: pre-wrap; word-wrap: break-word;"></pre>
				</div>
				<img src="../img/part3.png" style="position:absolute; right:0px; top:0px;"/>
			</div>


			<div class="part-container">
				<div style="margin-top: 0px; margin-bottom: 0px; text-align: center;">
					<img src="../img/flag4.png" style="margin-bottom: 20px; width: 35px; height: 42px;"/>
					<span style="font-size: 36px; color: #6194f9; margin-left: 5px;">错误类型</span>
				</div>
				<div style="margin-top: 0px; margin-bottom: 0px; text-align: center;">
					<img style="background-color: #6194f9; padding: 2px 40px;"/>
				</div>
				<div id="containerErrorTypeLossScore" style="width: 100%; height: auto; margin: 0 auto"></div>
				<hr/>
				<div style="margin-top: 0px; ">
					<p style="font-size: 33px; color: #6194f9;">老师点评：</p>
					<pre id="noteErrorTypeLossScore" style="font-size: 33px; color: #666; margin-left: 30px; margin-right: 30px; white-space: pre-wrap; word-wrap: break-word;"></pre>
				</div>
				<img src="../img/part4.png" style="position:absolute; right:0px; top:0px;"/>
			</div>


			<div class="part-container">
				<div style="margin-top: 0px; margin-bottom: 0px; text-align: center;">
					<img src="../img/flagend.png" style="margin-bottom: 20px; width: 35px; height: 42px;"/>
					<span style="font-size: 36px; color: #ff755d; margin-left: 5px;">提分方案</span>
				</div>
				<div style="margin-top: 0px; margin-bottom: 20px; text-align: center;">
					<img style="background-color: #ff755d; padding: 2px 40px;"/>
				</div>
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