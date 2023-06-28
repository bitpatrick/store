<%@include file="libs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="container">
		<div class="row">
			<div class="col h1 text-center">HOME PAGE</div>
		</div>
		<div class="row">
			<c:forEach var="product" items="${products}">
				<div class="col-lg-4 col-md-6 mb-4">
					<div class="card h-100">
						<img class="card-img-top" src="#}" alt="#">
						<div class="card-body">
							<h5 class="card-title">${product.name()}</h5>
							<p class="card-text">${product.category()}</p>
							<a href="${pageContext.request.contextPath}/details?id=#"
								class="btn btn-primary">See more</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
</body>
</html>

