<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-default">
	<div class="panel-body">
		<!-- 路径导航 -->
		<ol class="breadcrumb">
			<li><a href="goIndex">主页</a></li>
			<li class="active">${newsList_type_limit.get(0).typeName}</li>
		</ol>
		<hr>
		<!-- 新闻列表 -->
		<table class="table-news-list">
			<c:forEach items="${newsList_type_limit}" var="news">
				<tr>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
							value="${news.publishTime}" /></td>
					<td><a href="news?action=show&typeId=${news.typeId}&newsId=${news.newsId}"
						title="${news.title}">${fn:substring(news.title, 0, 50)}</a></td>
				</tr>
			</c:forEach>
		</table>

		<!-- 分页 -->
		<div class="text-center">
			<nav aria-label="Page navigation">
				<ul class="pagination" id="pagination">
					<li><a href="#">首页</a></li>
					<li><a href="#">上一页</a></li>
					<li><a href="#">下一页</a></li>
					<li><a href="#">尾页</a></li>
				</ul>
			</nav>
		</div>
	</div>
</div>

<script type="text/javascript">
	/**
	 * 分页
	 */
	var currentPage = ${currentPage};
	var totalPage = ${totalPage};
	var typeId = ${newsList_type_limit.get(0).typeId};

	$($("#pagination li a")[0]).attr("href",
			"news?action=list&typeId=" + typeId + "&page=1");
	$($("#pagination li a")[1]).attr("href",
			"news?action=list&typeId=" + typeId + "&page=" + (currentPage - 1));
	$($("#pagination li a")[2]).attr("href",
			"news?action=list&typeId=" + typeId + "&page=" + (currentPage + 1));
	$($("#pagination li a")[3]).attr("href",
			"news?action=list&typeId=" + typeId + "&page=" + totalPage);

	if (currentPage == 1) {
		$($("#pagination li a")[0]).attr("href", "#");
		$($("#pagination li a")[1]).attr("href", "#");
		$($("#pagination li")[0]).addClass("disabled");
		$($("#pagination li")[1]).addClass("disabled");
	}

	if (currentPage == totalPage) {
		$($("#pagination li a")[2]).attr("href", "#");
		$($("#pagination li a")[3]).attr("href", "#");
		$($("#pagination li")[2]).addClass("disabled");
		$($("#pagination li")[3]).addClass("disabled");
	}
</script>




