<%@include file="bootstrap5linksAndMetas.jsp"%>
<%@include file="customTags.jsp"%>
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
				<li class="nav-item"><a class="nav-link" href="${pageContext.servletContext.contextPath}/purchase">Purchase</a></li>
				<li class="nav-item"><a class="nav-link disabled">Disabled</a></li>
				<sec:authorize access="isAuthenticated()">
					<!-- Il codice qui dentro sar� renderizzato solo se l'utente � autenticato -->
					<li class="nav-item ms-auto">
						<button type="button" class="btn btn-secondary d-flex align-items-center" data-bs-toggle="modal" data-bs-target="#cartModal">
						 	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart4" viewBox="0 0 16 16">
						  		<path d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5zM3.14 5l.5 2H5V5H3.14zM6 5v2h2V5H6zm3 0v2h2V5H9zm3 0v2h1.36l.5-2H12zm1.11 3H12v2h.61l.5-2zM11 8H9v2h2V8zM8 8H6v2h2V8zM5 8H3.89l.5 2H5V8zm0 5a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0zm9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0z"/>
							</svg>
						</button>
					</li>
				</sec:authorize>
			</ul>
		</div>
<%-- 		<jsp:include page="lightSwitch.jsp" /> --%>
	</div>
</nav>



<%@include file="bootstrap5scripts.jsp"%>






