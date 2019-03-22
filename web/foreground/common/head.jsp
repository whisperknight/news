<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="goIndex">主页</a>
		</div>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav" id="nav-top">
				<c:forEach items="${newsTypeList }" var="newsType">
					<li><a href="news?action=list&typeId=${newsType.newsTypeId }">${newsType.typeName }</a></li>
				</c:forEach>
			</ul>
		</div>
	</div>
</nav>

<div class="head-banner">
	<h1>
		<a href="goIndex">新闻网</a>
	</h1>
	<div></div>
	<p>瞬息百态，纵览天下</p>
</div>

<script type="text/javascript">
	var arr = window.location.href.split("&");
	var typeIdStr;
	for (var i = 0; i < arr.length; i++)
		if (arr[i].indexOf("typeId=") != -1) {
			typeIdStr = arr[i];
			break;
		}
	$('#nav-top li a').each(function() {
		if (this.href.indexOf(typeIdStr) != -1)
			$(this).parent().addClass('active');
		else
			$(this).parent().removeClass('active');
	});
</script>