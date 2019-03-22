<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- 路径导航 -->
<ol class="breadcrumb">
	<li><a href="${pageContext.request.contextPath}/background/mainTemplate.jsp">主页</a></li>
	<li class="active">新闻管理</li>
	<li class="active">新闻维护</li>
</ol>

<form class="form-horizontal" method="post"
	action="news?action=newsList_back">

	<div class="form-group">
		<label class="col-sm-2 control-label">起始时间：</label>
		<div class='input-group date col-sm-5' id='datetimepicker1'>
			<input type='text' class="form-control" name="startTime"
				value="${startTime }" /> <span class="input-group-addon"> <span
				class="glyphicon glyphicon-calendar"></span>
			</span>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2">截止时间：</label>
		<div class='input-group date col-sm-5' id='datetimepicker2'>
			<input type='text' class="form-control" name="endTime"
				value="${endTime }" /> <span class="input-group-addon"> <span
				class="glyphicon glyphicon-calendar"></span>
			</span>
		</div>
	</div>
	<div class="form-group text-left">
		<label class="col-sm-2 control-label">新闻标题：</label>
		<div class="col-sm-8 input-group" style="padding-left: 0px">
			<input class="form-control" type="text" name="title" id="title"
				value="${title }"> <span class="input-group-btn">
				<input class="btn btn-primary" type="submit" value="查询">
			</span>
		</div>
	</div>
</form>
<script type="text/javascript">
//日期控件
$(function () {
	$('#datetimepicker1').datetimepicker({
    	format: 'yyyy-mm-dd hh:ii',
    	pickerPosition:'bottom-left',
    	language: 'zh-CN',
    	autoclose: true
    });
	
	$('#datetimepicker2').datetimepicker({
    	format: 'yyyy-mm-dd hh:ii',
    	pickerPosition:'bottom-left',
    	language: 'zh-CN',
    	autoclose: true
    });
});
</script>


<table class="table table-hover">
	<tr>
		<th>序号</th>
		<th>新闻标题</th>
		<th>新闻类别</th>
		<th>发布日期</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${newsList_back }" var="news" varStatus="status">
		<tr>
			<td>${status.index + 1 }</td>
			<td><a title="${news.title }" target="_blank"
				href="news?action=show&typeId=${news.typeId}&newsId=${news.newsId }">${fn:substring(news.title,0,18)}${news.title.length()>=18?"...":"" }</a></td>
			<td><a title="${news.typeName}" target="_blank"
				href="news?action=list&typeId=${news.typeId}">${fn:substring(news.typeName,0,10)}${news.typeName.length()>=10?"...":"" }</a></td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
					value="${news.publishTime}" /></td>
			<td>
				<button class="btn btn-primary btn-sm" role="button"
					onclick="javascript:window.location.href='news?action=preEdit&newsId=${news.newsId}'">修改</button>
				<button class="btn btn-danger btn-sm" role="button"
					onclick="deleteConfirm(${news.newsId})">删除</button>
			</td>
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

<script type="text/javascript">
	/**
	 * 分页
	 */
	var currentPage = ${currentPage};
	var totalPage = ${totalPage};
	
	$($("#pagination li a")[0]).attr("href",
		"news?action=newsList_back&page=1");
	$($("#pagination li a")[1]).attr("href",
		"news?action=newsList_back&page=" + (currentPage - 1));
	$($("#pagination li a")[2]).attr("href",
		"news?action=newsList_back&page=" + (currentPage + 1));
	$($("#pagination li a")[3]).attr("href",
		"news?action=newsList_back&page=" + totalPage);

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
	
/**
 * 单个删除确认
 */
function deleteConfirm(newsId){
	$('#myModal-confirm-yes').attr("href", "javascript:deleteNews("+newsId+")");
	$('#myModal-confirm').modal('show');
}

/**
 * 单个删除回调
 */
function deleteNews(newsId){
	$('#myModal-confirm').modal('hide');
	$.post("news?action=delete_back", {
		"newsId":newsId
	}, function(data) {
		data = eval("("+data+")");
		if(data.info == "success")
			window.location.href = "news?action=newsList_back";
		else if(data.info == "error"){
			$("#myModal-error").modal('show');
		}
	})
}
</script>


<!-- 确认信息Modal:myModal-confirm、myModal-confirm-yes -->
<div class="modal fade" id="myModal-confirm">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">删除新闻</h4>
			</div>
			<div class="modal-body" id="myModal-confirm-content">
				确定要删除该条新闻吗？</div>
			<div class="modal-footer">
				<a class="btn btn-default btn-primary" href="#" role="button"
					id="myModal-confirm-yes">确认</a> <a class="btn btn-default"
					role="button" data-dismiss="modal">取消</a>
			</div>
		</div>
	</div>
</div>

<!-- 错误信息Modal:myModal-error -->
<div class="modal fade" id="myModal-error" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">删除失败</h4>
			</div>
			<div class="modal-body text-danger" id="myModal-error-content">删除失败！</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal">确定</button>
			</div>
		</div>
	</div>
</div>


