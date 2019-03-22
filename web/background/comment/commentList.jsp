<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- 路径导航 -->
<ol class="breadcrumb">
	<li><a href="${pageContext.request.contextPath}/background/mainTemplate.jsp">主页</a></li>
	<li class="active">新闻评论管理</li>
	<li class="active">新闻评论维护</li>
</ol>

<div style="float: left">
	<button class="btn btn-danger" type="button"
		onclick="deleteAllcheckedConfirm()">批量删除</button>
</div>
<div style="float: right">
	<form class="form-inline" method="post"
		action="comment?action=commentList_back">
		<div class="form-group">
			<label>从</label>
			<div class='input-group date' id='datetimepicker1'>
				<input type='text' class="form-control" name="startTime"
					value="${startTime }" /> <span class="input-group-addon"> <span
					class="glyphicon glyphicon-calendar"></span>
				</span>
			</div>
		</div>
		<div class="form-group">
			<label>到</label>
			<div class='input-group date' id='datetimepicker2'>
				<input type='text' class="form-control" name="endTime"
					value="${endTime }" /> <span class="input-group-addon"> <span
					class="glyphicon glyphicon-calendar"></span>
				</span>
			</div>
		</div>
		<button class="btn btn-default" type="submit">查询</button>
	</form>
</div>
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
		<th><input type="checkbox" id="checkedAll"></th>
		<th>序号</th>
		<th>评论内容</th>
		<th>新闻标题</th>
		<th>时间</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${commentList_back }" var="comment"
		varStatus="status">
		<tr>
			<td><input type="checkbox" name="commentIds"
				value="${comment.commentId}"></td>
			<td>${status.index + 1 }</td>
			<td><a title="${comment.content }">${fn:substring(comment.content,0,18)}${comment.content.length()>=18?"...":"" }</a></td>
			<td><a title="${comment.newsTitle }" target="_blank"
				href="news?action=show&typeId=${comment.typeId}&newsId=${comment.newsId }">${fn:substring(comment.newsTitle,0,10)}${comment.newsTitle.length()>=10?"...":"" }</a></td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
					value="${comment.commentTime}" /></td>
			<td>
				<button class="btn btn-danger btn-sm" role="button"
					onclick="deleteConfirm(${comment.commentId})">删除</button>
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
		"comment?action=commentList_back&page=1");
	$($("#pagination li a")[1]).attr("href",
		"comment?action=commentList_back&page=" + (currentPage - 1));
	$($("#pagination li a")[2]).attr("href",
		"comment?action=commentList_back&page=" + (currentPage + 1));
	$($("#pagination li a")[3]).attr("href",
		"comment?action=commentList_back&page=" + totalPage);

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
function deleteConfirm(commentId){
	$('#myModal-confirm-yes').attr("href", "javascript:deleteComment("+commentId+")");
	$('#myModal-confirm-content').text("确定要删除这条评论吗？");
	$('#myModal-confirm').modal('show');
}

/**
 * 单个删除回调
 */
function deleteComment(commentId){
	$('#myModal-confirm').modal('hide');
	$.post("comment?action=delete_back", {
		commentIds:commentId
	}, function(data) {
		data = eval("("+data+")");
		if(data.info == "success")
			window.location.href = "comment?action=commentList_back";
		else if(data.info == "error"){
			$('#myModal-error-content').text("删除失败！");
			$("#myModal-error").modal('show');
		}
	})
}

//全选按钮设置
$(function(){
	$("#checkedAll").click(function(){
		if($(this).prop("checked") == true){
			$("[name='commentIds']").prop("checked", true);
		}else{
			$("[name='commentIds']").prop("checked", false);
		}
	});
});

//批量删除确认
function deleteAllcheckedConfirm(){
	$('#myModal-confirm-yes').attr("href", "javascript:deleteComments()");
	$('#myModal-confirm-content').text("确定要删除这些评论吗？");
	$('#myModal-confirm').modal('show');
}

//批量删除
function deleteComments(){
	$('#myModal-confirm').modal('hide');
	var arr_commentIds=[];
	$('[name="commentIds"]:checked').each(function(){
		arr_commentIds.push($(this).val());
	});
	if(arr_commentIds.length==0){
		$('#myModal-error-content').text("请选择要删除的数据！");
		$("#myModal-error").modal('show');
		return;
	}
	var commentIds=arr_commentIds.join(",");
	$.post("comment?action=delete_back",{commentIds:commentIds},
			function(data){
				var data=eval('('+data+')');
				if(data.info == "success"){
					window.location.href="${pageContext.request.contextPath}/comment?action=commentList_back";
				}else if (data.info == "error"){
					$('#myModal-error-content').text("删除失败！");
					$("#myModal-error").modal('show');
				}
			}
		);
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
				<h4 class="modal-title">删除评论</h4>
			</div>
			<div class="modal-body" id="myModal-confirm-content">
				确定要删除该条评论吗？</div>
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


