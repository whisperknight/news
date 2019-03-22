<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 路径导航 -->
<ol class="breadcrumb">
	<li><a href="${pageContext.request.contextPath}/background/mainTemplate.jsp">主页</a></li>
	<li class="active">新闻类别管理</li>
	<li class="active"><c:choose>
			<c:when test="${empty newsType.newsTypeId}">新闻类别添加</c:when>
			<c:otherwise>新闻类别修改</c:otherwise>
		</c:choose></li>
</ol>

<form class="form-horizontal" action="newsType?action=editFinished"
	method="post" onsubmit="return checkForm()">

	<div class="form-group">
		<label for="typeName" class="col-sm-2 control-label">类别名称：</label>
		<div class="col-sm-10">
			<input type="text" class="col-sm-10 form-control" id="typeName"
				name="typeName" value="${newsType.typeName }" />
		</div>
	</div>

	<input type="hidden" id="newsTypeId" name="newsTypeId"
		value="${newsType.newsTypeId }" />

	<div class="col-sm-10 col-sm-offset-2">
		<p id="error" class="text-danger"></p>
	</div>

	<div class="col-sm-10 col-sm-offset-2">
		<button type="submit" class="btn btn-primary">保存</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button class="btn btn-default" type="button"
			onclick="javascript:history.back()">返回</button>
	</div>
</form>

<script type="text/javascript">
	function checkForm() {
		var typeName = document.getElementById("typeName").value;
		if (typeName == null || $.trim(typeName) == "") {
			document.getElementById("error").innerHTML = "*请输入类别名称！";
			return false;
		}

		return true;
	}
</script>
