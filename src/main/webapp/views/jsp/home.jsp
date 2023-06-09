<%@include file="libs.jsp"%>
<!DOCTYPE html>
<html>
<head>

<style>

   .product-image {
    height: 500px; /* Imposta l'altezza desiderata */
    width: 100%;  /* Imposta la larghezza desiderata */
    object-fit: scale-down; /* Questa proprietÓ ridimensiona l'immagine per riempire il box (senza distorsioni) */
}

</style>

<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="container">
		<div class="row">
		
		
		
			<c:forEach var="product" items="${products}">
			
				<div class="col-lg-4 col-md-6 mb-4">
					
					<!-- CARD START -->
					<div class="card h-100">
						<img class="card-img-top product-image"
							src="${pageContext.request.contextPath}/static/img/${product.name()}.jpg"
							alt="#">
						<div class="card-body">
							
							<h5 class="card-title">${product.name()}</h5>
							<p class="card-text">${product.category()}</p>

							<div class="d-flex mb-3">
							
								<!-- /PRODUCT DETAIL -->
								<div class="p-2">
									<a href="${pageContext.request.contextPath}/details?id=#"
										class="btn btn-primary">See more</a>
								</div>
								<!-- PRODUCT DETAIL/ -->
								
								<!-- /ADD -->
								<div class="ms-auto p-2">
									<div class="input-group mb-3 flex-nowrap">
									
										<!-- /CART -->
										<span class="input-group-text" id="basic-addon1">
									  		<button type="button" class="btn btn-primary cart-btn" data-product-id="${product.id()}">
										        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart-plus" viewBox="0 0 16 16">
										            <path d="M9 5.5a.5.5 0 0 0-1 0V7H6.5a.5.5 0 0 0 0 1H8v1.5a.5.5 0 0 0 1 0V8h1.5a.5.5 0 0 0 0-1H9V5.5z" />
										            <path d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zm3.915 10L3.102 4h10.796l-1.313 7h-8.17zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0z" />
										        </svg>
										    </button>
										</span>
										<!-- CART/ -->
										
										<!-- /QTY -->
										<div class="form-floating form-outline">
										    <input type="hidden" name="productId" value="${product.id()}">
										    <input type="number" class="form-control quantity-input" id="typeNumber" placeholder="Number" aria-describedby="basic-addon1"  min="0"/>
										    <label for="typeNumber">Number input</label>
										</div>
										<!-- QTY/ -->

									  	
									</div>
								</div>
								<!-- ADD/ -->
								
							</div>
						</div>
					</div>
					<!-- CARD END -->
					
				</div>
			</c:forEach>
		</div>
		
</div>
</body>
</html>

<script>
$(document).ready(function(){
    $(".cart-btn").click(function(event){
        event.preventDefault();

        var productId = $(this).data('product-id');
        var quantity = $(this).siblings('.input-group').find('.quantity-input').val();

        $.post("${pageContext.request.contextPath}/addProduct", { productId: productId, quantity: quantity })
        .done(function(data){
            alert("Product added to cart!");
        })
        .fail(function(){
            alert("Error adding product to cart.");
        });
    });
});
</script>
