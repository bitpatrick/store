<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="customTags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="bootstrap5linksAndMetas.jsp"%>
<style>
.product-image {
	height: 500px; /* Imposta l'altezza desiderata */
	width: 100%; /* Imposta la larghezza desiderata */
	object-fit: scale-down;
	/* Questa proprietà ridimensiona l'immagine per riempire il box (senza distorsioni) */
}
</style>
<title>Home</title>
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="container mt-5">
		<div class="row">
			<c:forEach var="product" items="${products}">
				<div class="col-lg-4 col-md-6 mb-4">
					<div class="card">
						<img class="card-img-top product-image"
							src="${pageContext.request.contextPath}/static/img/${product.name()}.jpg"
							alt="#">
						<div class="card-body">
							<h5 class="card-title">${product.name()}</h5>
							<p class="card-text">${product.category()}</p>
							<form
								action="${pageContext.request.contextPath}/cart-addProduct/${product.id()}"
								method="post" class="row row-cols-lg-auto g-3 flex-nowrap">
								<div class="col-12">
									<a href="#" class="btn btn-primary form">Go somewhere</a>
								</div>
								<!-- CART -->
								<div class="col-12 flex-shrink-1">
									<div class="input-group mb-3">
										<span class="input-group-text" id="basic-addon1">
											<button type="submit" class="btn btn-primary">
												<svg xmlns="http://www.w3.org/2000/svg" width="16"
													height="16" fill="currentColor" class="bi bi-cart-plus"
													viewBox="0 0 16 16">
						<path
														d="M9 5.5a.5.5 0 0 0-1 0V7H6.5a.5.5 0 0 0 0 1H8v1.5a.5.5 0 0 0 1 0V8h1.5a.5.5 0 0 0 0-1H9V5.5z" />
						<path
														d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zm3.915 10L3.102 4h10.796l-1.313 7h-8.17zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0z" />
					</svg>
											</button>
										</span> <input type="text" name="quantity" class="form-control"
											placeholder="Qty" aria-label="Qty"
											aria-describedby="basic-addon1">
									</div>
								</div>
							</form>

						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>

<%@include file="bootstrap5scripts.jsp"%>
