<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 路径导航 -->
<ol class="breadcrumb">
	<li><a href="${pageContext.request.contextPath}/background/mainTemplate.jsp">主页</a></li>
	<li class="active">新闻管理</li>
	<li class="active"><c:choose>
			<c:when test="${empty news.newsId}">新闻添加</c:when>
			<c:otherwise>新闻修改</c:otherwise>
		</c:choose></li>
</ol>

<form class="form-horizontal" action="news?action=editFinished"
	method="post" onsubmit="return checkForm()"
	enctype="multipart/form-data">

	<input type="hidden" id="newsId" name="newsId" value="${news.newsId }" />

	<div class="form-group">
		<label class="col-sm-2 control-label">新闻标题：</label>
		<div class="col-sm-10">
			<input type="text" class="form-control" id="title" name="title"
				value="${news.title }" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">发布作者：</label>
		<div class="col-sm-5">
			<input type="text" class="form-control" id="author" name="author"
				value="${news.author }" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">所属类别：</label>
		<div class="col-sm-5">
			<select class="form-control" name="typeId" id="typeId">
				<option value="">请选择</option>
				<c:forEach items="${newsTypeList }" var="newsType">
					<option value="${newsType.newsTypeId }"
						${news.typeId==newsType.newsTypeId?"selected":"" }>${newsType.typeName }</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">页面特权：</label>
		<div class="col-sm-10">
			<label class="checkbox-inline"> <input type="checkbox"
				id="isHead" name="isHead" value="1"
				${news.isHead=="1"?"checked":"" }> 头条
			</label> <label class="checkbox-inline"> <input type="checkbox"
				id="isImage" name="isImage" value="1"
				${news.isImage=="1"?"checked":"" }> 轮播
			</label> <label class="checkbox-inline"> <input type="checkbox"
				id="isHot" name="isHot" value="1" ${news.isHot=="1"?"checked":"" }>
				热门
			</label>
		</div>
	</div>

	<!-- 隐藏的轮播图片上传区域 -->
	<div class="form-group" id="viewDiv">
		<label class="col-sm-2 control-label">轮播图片：</label>
		<div class="col-sm-10">
			<div class="input-group">
				<span class="input-group-btn"> <a class="btn btn-default"
					id="upload-button" type="button">上传图片</a>
				</span> <input type="text" class="form-control" id="upload-view" name="upload-view"
					placeholder="图片路径..." readonly = "readonly">
			</div>
		</div>
	</div>
	<input type="file" id="imageName" name="imageName" class="hidden">
	<script type="text/javascript">
		$(function() {
			//初始化
			if ($("#isImage").prop("checked")) {
				$("#viewDiv").show();
				$("#upload-view").val('${news.imageName}');
			} else
				$("#viewDiv").hide();

			//file样式替代方案
			$("#upload-button").click(function() {
				$("#imageName").click().change(function() {
					$("#upload-view").val($("#imageName").val());
				});
			});

			//是否轮播
			$("#isImage").change(function() {
				if ($(this).prop("checked"))
					$("#viewDiv").show();
				else{
					//隐藏并清空
					$("#viewDiv").hide();
					$("#upload-view").val("");
					 var file = document.getElementById("imageName");
			         // for IE, Opera, Safari, Chrome
			         if (file.outerHTML) {
			             file.outerHTML = file.outerHTML;
			         } else { // FF(包括3.5)
			             file.value = "";
			         }
				}
			});
		});
	</script>

	<div class="form-group">
		<div class="col-sm-12">
			<textarea name="content" id="editor1">
    		${news.content }
		</textarea>
			<script>
				CKEDITOR.replace('editor1');
			</script>
		</div>
	</div>

	<div class="text-center">
		<p id="error" class="text-danger">${error }</p>
	</div>

	<div class="col-sm-4 col-sm-offset-4">
		<button type="submit" class="btn btn-primary" style="float: left;">保存</button>
		<button class="btn btn-default" type="button"
			onclick="javascript:history.back()" style="float: right;">返回</button>
	</div>
</form>

<script type="text/javascript">
	function checkForm() {
		var title = $("#title").val();
		var author = $("#author").val();
		var typeId = $("#typeId").val();
		var content = CKEDITOR.instances.editor1.getData();
		if (title == null || $.trim(title) == "") {
			$("#error").html("*请输入标题！");
			return false;
		}
		if (author == null || $.trim(author) == "") {
			$("#error").html("*请输入作者！");
			return false;
		}
		if (typeId == null || $.trim(typeId) == "") {
			$("#error").html("*请选择类别！");
			return false;
		}
		if (content == null || $.trim(content) == "") {
			$("#error").html("*内容不能为空！");
			return false;
		}
		return true;
	}
</script>
