<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 路径导航 -->
<ol class="breadcrumb">
	<li><a href="${pageContext.request.contextPath}/background/mainTemplate.jsp">主页</a></li>
	<li class="active">新闻类别管理</li>
	<li class="active">新闻类别维护</li>
</ol>

<table class="table table-hover">
	<tr>
		<th>序号</th>
		<th>新闻类别</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${newsTypeList }" var="newsType" varStatus="status">
		<tr>
			<td>${status.index + 1 }</td>
			<td>${newsType.typeName }</td>
			<td>
				<button class="btn btn-primary btn-sm" role="button"
					onclick="javascript:window.location.href='newsType?action=preEdit&newsTypeId=${newsType.newsTypeId}'">修改</button>&nbsp;&nbsp;

				<button class="btn btn-danger btn-sm" role="button"
					onclick="deleteConfirm(${newsType.newsTypeId})">删除</button>
			</td>
		</tr>
	</c:forEach>
</table>


<script type="text/javascript">
//删除确认
function deleteConfirm(newsTypeId){
	$('#myModal-confirm-yes').attr("href", "javascript:deleteNewsType("+newsTypeId+")");
	$('#myModal-confirm').modal('show');
}

//删除回调
function deleteNewsType(newsTypeId){
	$.post("newsType?action=deleteNewsType", {
		newsTypeId:newsTypeId
	}, function(data) {
			data = eval("("+data+")");
			if(data.info == "success")
				window.location.href = "newsType?action=newsTypeList";
			else if(data.info == "exist"){
				$('#myModal-confirm').modal('hide');
				$("#myModal-error-content").text("该类别下有新闻，无法删除！");
				$("#myModal-error").modal('show');
			} else if(data.info == "error"){
				$('#myModal-confirm').modal('hide');
				$("#myModal-error-content").text("删除失败！");
				$("#myModal-error").modal('show');
			}
		});
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
				<h4 class="modal-title">删除类别</h4>
			</div>
			<div class="modal-body">
				<p>确定要删除该类别吗？</p>
			</div>
			<div class="modal-footer">
				<a class="btn btn-default btn-primary" href="#" role="button"
					id="myModal-confirm-yes">确认</a> <a class="btn btn-default"
					role="button" data-dismiss="modal">取消</a>
			</div>
		</div>
	</div>
</div>

<!-- 错误信息Modal:myModal-error、myModal-error-content -->
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
			<div class="modal-body text-danger" id="myModal-error-content"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
			</div>
		</div>
	</div>
</div>


