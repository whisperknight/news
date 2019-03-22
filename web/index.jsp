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
			<!-- 轮播 -->
			<div class="col-md-4" style="margin-top: 30px;">
				<div id="carousel-example-generic" class="carousel slide"
					data-ride="carousel" style="width: 500px; height: 282px;overflow: hidden;">
					<ol class="carousel-indicators">
						<c:forEach items="${imageNewsList }" var="news" varStatus="status">
							<li class='<c:if test="${status.index==0 }">active</c:if>'
								data-target="#carousel-example-generic"
								data-slide-to="${status.index }"></li>
						</c:forEach>
					</ol>
					<div class="carousel-inner" role="listbox">
						<c:forEach items="${imageNewsList }" var="news" varStatus="status">
							<div class="item <c:if test="${status.index==0 }">active</c:if>">
								<div class="carousel-img">
									<a href="news?action=show&typeId=${news.typeId }&newsId=${news.newsId }"><img
										src="${news.imageName }"></a>
								</div>
								<div class="carousel-caption">
									<h3>${fn:substring(news.title, 0, 24)}</h3>
								</div>
							</div>
						</c:forEach>
					</div>
					<a class="left carousel-control" href="#carousel-example-generic"
						role="button" data-slide="prev"> <span
						class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a> <a class="right carousel-control" href="#carousel-example-generic"
						role="button" data-slide="next"> <span
						class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>
			</div>

			<!-- 头条+最新  -->
			<div class="col-md-4">
				<div class="panel panel-default">
					<div class="panel-body">
						<!-- 头条 -->
						<div>
							<h3>
								<a href="news?action=show&typeId=${headNews.typeId }&newsId=${headNews.newsId }"
									title="${headNews.title }">${fn:substring(headNews.title, 0, 24)}</a>
							</h3>
							<p>
								${fn:substring(headNews.content, 0, 55)}...<a
									href="news?action=show&typeId=${headNews.typeId }&newsId=${headNews.newsId }">[查看全文]</a>
							</p>
						</div>
						<hr>
						<!-- 最新 -->
						<div>
							<h3>最近更新</h3>
							<table class="table-news-list">
								<c:forEach items="${rescentNewsList}" var="news" end="4">
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

			<!-- 热点 -->
			<div class="col-md-4">

				<div class="panel panel-default">
					<div class="panel-body">
						<h3>热点新闻</h3>
						<hr>
						<table class="table-news-list">


							<c:forEach items="${hotSpotNewsList}" var="news">
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

	<div class="container-fluid">

		<c:forEach items="${allTypeNewsList }" var="newsList"
			varStatus="status">

			<c:if test="${status.index%3==0 }">
				<div class="row">
			</c:if>

			<div class="col-md-4">
				<div class="panel panel-default">
					<div class="panel-body">
						<h3>
							${newsTypeList.get(status.index).typeName }&nbsp;&nbsp;<small><a
								href="news?action=list&typeId=${newsTypeList.get(status.index).newsTypeId }">[更多...]</a></small>
						</h3>
						<hr>
						<table class="table-news-list">
							<c:forEach items="${newsList }" var="news">
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

			<c:if test="${status.index%3==2 || status.last }">
	</div>
	</c:if>

	</c:forEach>

	</div>


	<jsp:include page="/foreground/common/link.jsp"></jsp:include>
	<jsp:include page="/foreground/common/foot.jsp"></jsp:include>
</body>
</html>