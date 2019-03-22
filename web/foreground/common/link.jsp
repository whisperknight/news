<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row-fluid">
	<div class="col-md-12" style="padding: 0px">
		<div class="link">
			<div class="link-header">
				<p>友情链接</p>
			</div>
			<div class="link-body">
				<ul>
					<c:forEach items="${linkList }" var="link">
					<li><a href="${link.linkUrl }" target="_blank">${link.linkName }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>
