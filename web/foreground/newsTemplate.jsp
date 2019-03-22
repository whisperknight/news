<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/favicon.ico" rel="shortcut icon">
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
<title>新闻网</title>
</head>
<body>
	<jsp:include page="/foreground/common/head.jsp"></jsp:include>


	<div class="container-fluid">
		<div class="row">
			<div class="col-md-8">
				<jsp:include page="${mainPage }"></jsp:include>
			</div>
			<div class="col-md-4">

				<div class="panel panel-default">
					<div class="panel-body">
						<h3>最近更新</h3>
						<hr>
						<table class="table-news-list">
							<c:forEach items="${rescentNewsList}" var="news">
								<tr>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
											value="${news.publishTime}" /></td>
									<td><a href="news?action=show&typeId=${news.typeId }&newsId=${news.newsId}"
										title="${news.title}">${fn:substring(news.title, 0, 24)}</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-body">
						<h3>热门新闻</h3>
						<hr>
						<table class="table-news-list">
							<c:forEach items="${hotNewsList}" var="news">
								<tr>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
											value="${news.publishTime}" /></td>
									<td><a href="news?action=show&typeId=${news.typeId }&newsId=${news.newsId}"
										title="${news.title}">${fn:substring(news.title, 0, 24)}</a></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/foreground/common/link.jsp"></jsp:include>
	<jsp:include page="/foreground/common/foot.jsp"></jsp:include>
</body>
</html>