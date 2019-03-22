<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 路径导航 -->
<ol class="breadcrumb">
	<li><a href="${pageContext.request.contextPath}/background/mainTemplate.jsp">主页</a></li>
	<li class="active">友情链接管理</li>
	<li class="active">友情链接维护</li>
</ol>

<table class="table table-hover">
	<tr>
		<th>序号</th>
		<th>链接名称</th>
		<th>链接地址</th>
		<th>联系人邮箱</th>
		<th>优先级</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${linkList }" var="link" varStatus="status">
		<tr>
			<td>${status.index + 1 }</td>
			<td>${link.linkName }</td>
			<td>${link.linkUrl }</td>
			<td>${link.linkEmail }</td>
			<td>${link.orderNum }</td>
			<td>
				<button class="btn btn-primary btn-sm" role="button"
					onclick="javascript:window.location.href='link?action=preEdit&linkId=${link.linkId}'">修改</button>&nbsp;&nbsp;

				<button class="btn btn-danger btn-sm" role="button" onclick="deleteConfirm(${link.linkId})">删除</button>
			</td>
		</tr>
	</c:forEach>
</table>


<script type="text/javascript">
//删除确认
function deleteConfirm(linkId){
	$('#myModal-confirm-yes').attr("href", "javascript:deleteLink("+linkId+")");
	$('#myModal-confirm').modal('show');
}

//删除回调
function deleteLink(linkId){
	$.post("link?action=deletelink", {
		linkId:linkId
	}, function(data) {
		data = eval("("+data+")");
		if(data.info == "success")
			window.location.href = "link?action=linkList";
		else{
			$('#myModal-confirm').modal('hide');			
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
				<h4 class="modal-title">删除链接</h4>
			</div>
			<div class="modal-body">
				<p>确定要删除该友情链接吗？</p>
			</div>
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
			<div class="modal-body text-danger">删除失败！</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal">确定</button>
			</div>
		</div>
	</div>
</div>


