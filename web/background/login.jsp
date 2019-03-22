<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
	//读取客户端cookie
	Cookie[] cookies = request.getCookies();
	for (int i = cookies.length - 1; cookies != null && i >= 0; i--) {
		if (cookies[i].getName().equals("user")) {
			String userName = cookies[i].getValue().split("-")[0];
			String password = cookies[i].getValue().split("-")[1];
			User user = new User(userName, password);
			pageContext.setAttribute("user", user);
		}
		if(cookies[i].getName().equals("remember")){
			String remember = cookies[i].getValue();
			pageContext.setAttribute("remember", remember);
		}
	}
%>


<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>新闻网后台管理</title>
<link href="${pageContext.request.contextPath}/favicon.ico" rel="shortcut icon">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-theme.min.css">
<script
	src="${pageContext.request.contextPath}/bootstrap/js/jquery-3.3.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<style type="text/css">
body {
	padding-top: 100px;
}

button {
	border-radius: 200px !important;
}
</style>

</head>
<body style="background-color: #504a63">
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">登录</h3>
					</div>
					<div class="panel-body">
						<blockquote>
							<h3>新闻网后台管理系统</h3>
						</blockquote>

						<form id="loginForm" class="form-horizontal" action="${pageContext.request.contextPath}/user?action=login"
							method="post" onsubmit="return checkForm()">
							<div class="form-group">
								<label for="userName" class="col-md-2 control-label">用户名</label>
								<div class="col-md-10">
									<input type="text" class="form-control" id="userName"
										name="userName" value="${user.userName }"
										placeholder="请输入用户名...">
								</div>
							</div>
							<div class="form-group">
								<label for="password" class="col-md-2 control-label">密码</label>
								<div class="col-md-10">
									<input type="password" class="form-control" id="password"
										name="password" value="${user.password }"
										placeholder="请输入密码...">
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-offset-2 col-md-10">
									<div class="checkbox">
										<p>
											<span id="error" class="text-danger">${error }</span>
										</p>
										<label> <input type="checkbox" id="remember"
											name="remember" value="on" ${remember == "on"?"checked":"" }>记住用户名和密码
										</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3 col-md-offset-2">
									<button class="btn btn-primary  btn-lg btn-block" type="submit">登录</button>
								</div>
								<div class="col-md-3 col-md-offset-2">
									<button class="btn btn-default  btn-lg btn-block" type="button">重置</button>
								</div>
							</div>
						</form>
						<hr>
						<p class="text-right">© 2018 WhisperKnight</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		//登录验证
		function checkForm() {
			var userName = document.getElementById("userName").value;
			var password = document.getElementById("password").value;

			if (userName == null || userName == "") {
				document.getElementById("error").innerHTML = "*用户名不能为空!";
				return false;
			}
			if (password == null || password == "") {
				document.getElementById("error").innerHTML = "*密码不能为空!";
				return false;
			}

			return true;
		}

		$(function() {
			//重置按钮
			$("[type='button']").click(function() {
				$(':input').attr("value", "");
			});
		});
	</script>
</body>
</html>