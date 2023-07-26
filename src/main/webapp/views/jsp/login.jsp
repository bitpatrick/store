<%@include file="customTags.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Login</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
	<jsp:include page="lightSwitch.jsp" />
	
<c:if test="${not empty param.error}">
    <p><c:out value="${param.error}"/></p>
</c:if>

	<div class="container text-center pt-5">
		<div class="col">
			<div class="row">
				<p class="h1">Login</p>
			</div>
		</div>
	</div>
	<div class="container p-5 my-5 overflow-hidden text-center">
		<form method="post" action="${pageContext.request.contextPath}/login">
			<sec:csrfInput />
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
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>



