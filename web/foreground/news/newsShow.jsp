<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="panel panel-default">
	<div class="panel-body">
		<!-- 导航路径 -->
		<ol class="breadcrumb">
			<li><a href="goIndex">主页</a></li>
			<li><a href="news?action=list&typeId=${news.typeId }">${news.typeName}</a></li>
			<li class="active">${news.title}</li>
		</ol>
		<hr>
		<h1 class="text-center">${news.title }</h1>
		<p class="text-center">
			发布时间：
			<fmt:formatDate pattern="yyyy-MM-dd HH:mm"
				value="${news.publishTime}" />
			&nbsp;&nbsp;作者：${news.author }&nbsp;&nbsp;&nbsp;&nbsp;阅读次数：${news.click }
		</p>
		<hr>
		<div>${news.content }</div>
		<div style="clear: both"></div>
		<hr>
		<!-- 上下篇翻页 -->
		<nav>
			<ul class="pager" id="upAndDown">
				<li class="previous"><a href="#">上一篇：<span></span></a></li>
				<li class="next"><a href="#">下一篇：<span></span></a></li>
			</ul>
		</nav>
		<script type="text/javascript">
		$(function() {
			if(${upNews.newsId} == -1){
				$("#upAndDown li a span:eq(0)").html("没有了");
				$("#upAndDown li a:eq(0)").attr("href", "#");
				$("#upAndDown li:eq(0)").addClass("disabled");
			} else{
				$("#upAndDown li a span:eq(0)").html("${upNews.title}");
				$("#upAndDown li a:eq(0)").attr("href", "news?action=show&typeId="+${news.typeId }+"&newsId="+ ${upNews.newsId});
			}
			if(${downNews.newsId} == -1){
				$("#upAndDown li a span:eq(1)").html("没有了");
				$("#upAndDown li a:eq(1)").attr("href", "#");
				$("#upAndDown li:eq(1)").addClass("disabled");
			} else{
				$("#upAndDown li a span:eq(1)").html("${downNews.title}");
				$("#upAndDown li a:eq(1)").attr("href", "news?action=show&typeId="+${news.typeId }+"&newsId="+ ${downNews.newsId});
			}
		});
		</script>

		<!-- 用户评论 -->
		<b>用户评论</b>
		<hr>
		<textarea class="form-control" rows="3" name="content" id="content"
			onchange="checkForm()"></textarea>
		<span id="error" style="color: red"></span>
		<p class="text-right" style="margin-top: 5px">
			<button type="submit" class="btn btn-primary">提交评论</button>
		</p>
		<script type="text/javascript">
		<!-- 提交验证 -->
		function checkForm(){
			var content = $("#content").val();
			if(content == null || $.trim(content) == ""){
				$("#error").text("*评论为空");
				return false;
			}
			$("#error").text("");
			return true;
		}
			
		$(function() {
			<!-- Ajax提交评论 -->
			$(":submit").click(function() {
				if(checkForm()){
					$.post("${pageContext.request.contextPath}/comment?action=add", {
						newsId : ${news.newsId },
						content : $("#content").val()
					}, function(data, status) {
						data = eval("(" + data + ")");
						
						$("#noComment").remove();
						var newComment = $("<hr><p>" +data.comment.userIP+"：</p><div>"+data.comment.content+"</div><p>"+data.comment.commentTime+"</p>");

						if ($("#commentArea").children().length==0)
							$("#commentArea").html(newComment);
						else
							$("#commentArea").children().eq(0).before(newComment);
						$("#content").val("");
					});
				}
			});
		});
		</script>

		<div id="noComment">
			<c:if test="${commentList.size() ==0 }">
				<hr>
				<p>暂时没有评论，小编很伤心QAQ</p>
			</c:if>
		</div>

		<div id="commentArea">
			<c:forEach items="${commentList }" var="comment">
				<hr>
				<p>${comment.userIP }：</p>
				<div>${comment.content }</div>
				<p>
					<fmt:formatDate pattern="yyyy-MM-dd HH:mm" type="date"
						value="${comment.commentTime}" />
				</p>
			</c:forEach>
		</div>

	</div>
</div>
