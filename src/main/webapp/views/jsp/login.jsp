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
	
	<h1>-----------------------------------------------------------------</h1>
	 <p>
      Well, hello there!
    </p>
    <p>
      We're going to now talk to the GitHub API. Ready?
      <a href="https://github.com/login/oauth/authorize?scope=read:user&client_id=727109d7f1ebfe825d11">Click here</a> to begin!
      <a href="${pageContext.request.contextPath}/oauth2/authorization/github">Click here2</a> to begin!
    </p>
    <p>
      If that link doesn't work, remember to provide your own <a href="/apps/building-oauth-apps/authorizing-oauth-apps/">Client ID</a>!
    </p>
	<h1>-----------------------------------------------------------------</h1>
	
</body>
</html>


<%@include file="bootstrap5scripts.jsp"%>

