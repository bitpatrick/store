<%@include file="bootstrap5linksAndMetas.jsp"%>

<nav class="navbar navbar-expand-lg bg-body-tertiary">
	<div class="container-fluid">
		<a class="navbar-brand" href="#">Navbar</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link active"
					aria-current="page" href="#">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="${pageContext.servletContext.contextPath}/purchase">Purchase</a></li>
				<li class="nav-item"></li>
				<li class="nav-item"><a class="nav-link disabled">Disabled</a>
				</li>
			</ul>
		</div>

		<jsp:include page="lightSwitch.jsp" />
		
	</div>
</nav>

<%@include file="bootstrap5scripts.jsp"%>






