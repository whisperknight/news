<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 路径导航 -->
<ol class="breadcrumb">
	<li><a href="${pageContext.request.contextPath}/background/mainTemplate.jsp">主页</a></li>
	<li class="active">友情链接管理</li>
	<li class="active"><c:choose>
			<c:when test="${empty link.linkId}">友情链接添加</c:when>
			<c:otherwise>友情链接修改</c:otherwise>
		</c:choose></li>
</ol>

<form class="form-horizontal" action="link?action=editFinished"
	method="post" onsubmit="return checkForm()">

	<div class="form-group">
		<label for="linkName" class="col-sm-2 control-label">链接名称：</label>
		<div class="col-sm-10">
			<input type="text" class="col-sm-10 form-control" id="linkName"
				name="linkName" value="${link.linkName }" />
		</div>
	</div>
	<div class="form-group">
		<label for="linkUrl" class="col-sm-2 control-label">链接地址：</label>
		<div class="col-sm-10">
			<input type="text" class="col-sm-10 form-control" id="linkUrl"
				name="linkUrl" value="${link.linkUrl }"/>
		</div>
	</div>
	<div class="form-group">
		<label for="linkEmail" class="col-sm-2 control-label">联系人邮箱：</label>
		<div class="col-sm-10">
			<input type="text" class="col-sm-10 form-control" id="linkEmail"
				name="linkEmail" value="${link.linkEmail }" />
		</div>
	</div>
	<div class="form-group">
		<label for="orderNum" class="col-sm-2 control-label">优先级(1最高)：</label>
		<div class="col-sm-10">
			<input type="text" class="col-sm-10 form-control" id="orderNum"
				name="orderNum" value="${link.orderNum }" />
		</div>
	</div>

	<input type="hidden" id="linkId" name="linkId" value="${link.linkId }" />

	<div class="col-sm-10 col-sm-offset-2">
		<p id="error" class="text-danger">${error }</p>
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
		var linkName = document.getElementById("linkName").value;
		var linkUrl = document.getElementById("linkUrl").value;
		var linkEmail = document.getElementById("linkEmail").value;
		var orderNum = document.getElementById("orderNum").value;
		if (linkName == null || $.trim(linkName) == "") {
			document.getElementById("error").innerHTML = "*请输入链接名称！";
			return false;
		}
		if (linkUrl == null ||  $.trim(linkUrl) == "") {
			document.getElementById("error").innerHTML = "*请输入链接地址！";
			return false;
		}
		if (orderNum == null ||  $.trim(orderNum) == "") {
			document.getElementById("error").innerHTML = "*请输入优先级！";
			return false;
		} else {
			var type = "^[0-9]*[1-9][0-9]*$";
			var re = new RegExp(type);
			if (orderNum.match(re) == null) {
				document.getElementById("error").innerHTML = "*优先级必须为正整数！";
				return false;
			}
			return true;
		}
		return true;
	}
</script>








