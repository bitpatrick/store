<%@include file="customTags.jsp"%>
<!doctype html>
<html lang="en">
<head>
<%@include file="bootstrap5linksAndMetas.jsp"%>
<title>Login page</title>
</head>
<body>
	<jsp:include page="lightSwitch.jsp" />
	<div class="container text-center pt-5">
		<div class="col">
			<div class="row">
				<p class="h1">Login</p>
			</div>
		</div>
	</div>
	<div class="container p-5 my-5 overflow-hidden text-center">
		<form method="post" action="${pageContext.request.contextPath}/login">
			<div class="row gy-1 align-items-center">
				<div class="col-6">
					<div class="p-3">Username:</div>
				</div>
				<div class="col-6">
					<div class="p-3">
						<input class="form-control" type="text"
							placeholder="Default input" aria-label="default input example"
							name="username" />
					</div>
				</div>
				<div class="col-6">
					<div class="p-3">Password:</div>
				</div>
				<div class="col-6">
					<div class="p-3">
						<input class="form-control" type="text"
							placeholder="Default input" aria-label="default input example"
							name="password" />
					</div>
				</div>
				<div class="col-12">
					<div class="form-check">
						<input class="form-check-input" type="checkbox" id="flexCheckDefault" name="remember-me">
						<label class="form-check-label" for="flexCheckDefault">Remember me</label>
					</div>
				</div>
				<div class="col-12">
					<button type="submit" class="btn btn-primary mb-3">Confirm identity</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>


<%@include file="bootstrap5scripts.jsp"%>

