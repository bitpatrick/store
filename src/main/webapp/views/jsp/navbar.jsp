<%@include file="customTags.jsp"%>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Navbar</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
	<div class="container-fluid">
		<a class="navbar-brand" href="#">Navbar</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav align-items-baseline" style="width: 100%;">
				<li class="nav-item"><a class="nav-link active" aria-current="page" href="#">Home</a></li>
<%-- 				<li class="nav-item"><a class="nav-link" href="${pageContext.servletContext.contextPath}/purchase">Purchase</a></li> --%>
				<li class="nav-item"><a class="nav-link disabled">Disabled</a></li>
				<li class="nav-item dropdown">
		        	<a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
		            	<i class="bi bi-translate"></i>
		            </a>
		            <ul class="dropdown-menu">
			            <li><a class="dropdown-item" href="#">Italiano</a></li>
			            <li><hr class="dropdown-divider"></li>
			            <li><a class="dropdown-item" href="#">English</a></li>
		        	</ul>
		        </li>
		        <form class="d-flex" role="search">
			        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
			        <button class="btn btn-outline-success" type="submit">Search</button>
			    </form>
				<sec:authorize access="isAuthenticated()">
					<!-- Il codice qui dentro sarà renderizzato solo se l'utente è autenticato -->
					<li class="nav-item ms-auto"><a class="nav-link active" aria-current="page" href="${pageContext.servletContext.contextPath}/logout">Logout</a></li>
					<li class="nav-item mx-1">
						<button type="button" class="btn btn-secondary d-flex align-items-center" data-bs-toggle="modal" data-bs-target="#cartModal">
						 	<i class="bi bi-cart4"></i>
						</button>
					</li>
					<li class="nav-item mx-1">
						<button type="button" class="btn btn-secondary d-flex align-items-center" data-bs-toggle="modal" data-bs-target="#piggyModal">
						 	<i class="bi bi-piggy-bank"></i>
						</button>
					</li>
				</sec:authorize>
				<sec:authorize access="isAnonymous()">
					<li class="nav-item ms-auto"><a class="nav-link active" aria-current="page" href="${pageContext.servletContext.contextPath}/login">Login</a></li>
				</sec:authorize>
			</ul>
		</div>
<%-- 		<jsp:include page="lightSwitch.jsp" /> --%>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</nav>

