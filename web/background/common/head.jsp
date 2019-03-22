<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="head-banner" style="margin-top: 0px">
	<h1>
		<a href="goIndex">新闻网</a>
	</h1>
	<div></div>
	<p>瞬息百态，纵览天下</p>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-8">
			<h4>
				欢迎管理员：${currentUser.userName }&nbsp;&nbsp;<small><a
					href="${pageContext.request.contextPath}/user?action=logout">[退出系统]</a></small>
			</h4>
		</div>
		<div class="col-md-4">
			<h4 class="text-right">
				<font id="today"></font>
			</h4>
		</div>
	</div>
</div>
<hr>
<script type="text/javascript">
	function setDateTime() {
		var date = new Date();
		var day = date.getDay();
		var week;
		switch (day) {
		case 0:
			week = "星期日";
			break;
		case 1:
			week = "星期一";
			break;
		case 2:
			week = "星期二";
			break;
		case 3:
			week = "星期三";
			break;
		case 4:
			week = "星期四";
			break;
		case 5:
			week = "星期五";
			break;
		case 6:
			week = "星期六";
			break;
		}
		var today = date.getFullYear() + "年" + (date.getMonth() + 1) + "月"
				+ date.getDate() + "日  " + week + " " + date.getHours() + ":"
				+ date.getMinutes() + ":" + date.getSeconds();
		document.getElementById("today").innerHTML = today;
	}

	window.setInterval("setDateTime()", 1000);

	$(function() {
		setDateTime();
	});
</script>