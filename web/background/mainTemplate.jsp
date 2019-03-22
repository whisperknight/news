<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>新闻网后台管理</title>
<link href="${pageContext.request.contextPath}/favicon.ico"
	rel="shortcut icon">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/news.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-theme.min.css">
<script
	src="${pageContext.request.contextPath}/bootstrap/js/jquery-3.3.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>

<link
	href="${pageContext.request.contextPath}/bootstrap/datetimepicker/bootstrap-datetimepicker.min.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/bootstrap/datetimepicker/bootstrap-datetimepicker.min.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/bootstrap/datetimepicker/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
<script src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
<style type="text/css">
button {
	border-radius: 200px !important;
}
/*
	解决模态框加自动padding的问题
*/
body {
	padding-right: 0px !important;
}
</style>
<%
	String mainPage = (String) request.getAttribute("mainPage");
	if (mainPage == null || mainPage.equals("")) {
		mainPage = "/background/default.jsp";
	}
%>
</head>
<body>
	<jsp:include page="/background/common/head.jsp" />

	<div class="container">
		<div class="row">
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading" role="tab">
						<h4 class="panel-title text-center">
							<a class="" href="${pageContext.request.contextPath}/background/mainTemplate.jsp">主页</a>
						</h4>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading" role="tab"
						id="collapseListGroupHeading1">
						<h4 class="panel-title text-center">
							<a class="" data-toggle="collapse" href="#collapseListGroup1"
								aria-expanded="true" aria-controls="collapseListGroup1">
								新闻管理 </a>
						</h4>
					</div>
					<div id="collapseListGroup1" class="panel-collapse collapse in"
						role="tabpanel" aria-labelledby="collapseListGroupHeading1"
						aria-expanded="true">
						<ul class="list-group">
							<li class="list-group-item"><a
								href="${pageContext.request.contextPath}/news?action=preEdit" style="margin-left: 20px">新闻添加</a></li>
							<li class="list-group-item"><a
								href="${pageContext.request.contextPath}/news?action=newsList_back" style="margin-left: 20px">新闻维护</a></li>
						</ul>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading" role="tab"
						id="collapseListGroupHeading2">
						<h4 class="panel-title text-center">
							<a class="" data-toggle="collapse" href="#collapseListGroup2"
								aria-expanded="true" aria-controls="collapseListGroup2">
								新闻评论管理 </a>
						</h4>
					</div>
					<div id="collapseListGroup2" class="panel-collapse collapse in"
						role="tabpanel" aria-labelledby="collapseListGroupHeading2"
						aria-expanded="true">
						<ul class="list-group">
							<li class="list-group-item"><a
								href="${pageContext.request.contextPath}/comment?action=commentList_back"
								style="margin-left: 20px">新闻评论维护</a></li>
						</ul>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading" role="tab"
						id="collapseListGroupHeading3">
						<h4 class="panel-title text-center">
							<a class="" data-toggle="collapse" href="#collapseListGroup3"
								aria-expanded="true" aria-controls="collapseListGroup3">
								新闻类别管理 </a>
						</h4>
					</div>
					<div id="collapseListGroup3" class="panel-collapse collapse in"
						role="tabpanel" aria-labelledby="collapseListGroupHeading3"
						aria-expanded="true">
						<ul class="list-group">
							<li class="list-group-item"><a
								href="${pageContext.request.contextPath}/newsType?action=preEdit" style="margin-left: 20px">新闻类别添加</a></li>
							<li class="list-group-item"><a
								href="${pageContext.request.contextPath}/newsType?action=newsTypeList"
								style="margin-left: 20px">新闻类别维护</a></li>
						</ul>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading" role="tab"
						id="collapseListGroupHeading4">
						<h4 class="panel-title text-center">
							<a class="" data-toggle="collapse" href="#collapseListGroup4"
								aria-expanded="true" aria-controls="collapseListGroup4">
								友情链接管理 </a>
						</h4>
					</div>
					<div id="collapseListGroup4" class="panel-collapse collapse in"
						role="tabpanel" aria-labelledby="collapseListGroupHeading4"
						aria-expanded="true">
						<ul class="list-group">
							<li class="list-group-item"><a
								href="${pageContext.request.contextPath}/link?action=preEdit" style="margin-left: 20px">友情链接添加</a></li>
							<li class="list-group-item"><a
								href="${pageContext.request.contextPath}/link?action=linkList" style="margin-left: 20px">友情链接维护</a></li>
						</ul>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading" role="tab"
						id="collapseListGroupHeading5">
						<h4 class="panel-title text-center">
							<a class="" data-toggle="collapse" href="#collapseListGroup5"
								aria-expanded="true" aria-controls="collapseListGroup5">
								系统管理</a>
						</h4>
					</div>
					<div id="collapseListGroup5" class="panel-collapse collapse in"
						role="tabpanel" aria-labelledby="collapseListGroupHeading5"
						aria-expanded="true">
						<ul class="list-group">
							<li class="list-group-item"><a
								href="javascript:refreshSystem()" style="margin-left: 20px">刷新服务器缓存</a></li>
						</ul>
					</div>
					<script type="text/javascript">
						function refreshSystem() {
							$.post("${pageContext.request.contextPath}/init", function(data) {
								data = eval("(" + data + ")");
								if (data.info == "success")
									$("#myModal_refresh_success").modal('show');
								else
									alert("刷新失败");
							})
						}
					</script>
					<!-- 刷新成功Modal -->
					<div class="modal fade" id="myModal_refresh_success" tabindex="-1"
						role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title">刷新成功！</h4>
								</div>
								<div class="modal-body">刷新服务器缓存成功！</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary"
										data-dismiss="modal">确定</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-body">
						<jsp:include page="<%=mainPage%>"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/background/common/foot.jsp" />
</body>
</html>