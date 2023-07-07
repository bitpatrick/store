<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
	
	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	    		<table class="table">
				  <thead>
				    <tr>
				      <th scope="col">#</th>
				      <th scope="col">Name</th>
				      <th scope="col">Qty</th>
				      <th scope="col">Price</th>
				      <th scope="col">#</th>
				    </tr>
				  </thead>
				  <tbody>
				  <c:forEach var="productInCart" items="${productsInCart}" varStatus="status">
				    
				    <tr class="product-row-${productInCart.key.id()}">

				    	<input type="hidden" class="product-id" value="${productInCart.key.id()}">
				    	<th scope="row">${status.count}</th>
				    	<td>${productInCart.key.name()}</td>
				    	<td class="product-qty">${productInCart.value}</td>

				    	<c:set var="total" value="${productInCart.value * productInCart.key.price()}"/>
      					<td><c:out value="${total}" /></td>
      					<td>
      						<div class="btn-group btn-group-sm" role="group" aria-label="Small button group">
  								<button type="button" class="btn btn-outline-primary">+</button>
 								<button type="button" class="btn btn-outline-primary">-</button>
							</div>
						</td>
				    </tr>
				   </c:forEach>
				  </tbody>
				</table>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="container mt-5">
		<div class="row">
			<c:forEach var="product" items="${products}">
				<div class="col-lg-4 col-md-6 mb-4">
					<div class="card">
						<img 
							class="card-img-top product-image"
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
												<svg 
													xmlns="http://www.w3.org/2000/svg" 
													width="16"
													height="16" fill="currentColor" 
													class="bi bi-cart-plus"
													viewBox="0 0 16 16">
														<path d="M9 5.5a.5.5 0 0 0-1 0V7H6.5a.5.5 0 0 0 0 1H8v1.5a.5.5 0 0 0 1 0V8h1.5a.5.5 0 0 0 0-1H9V5.5z" />
														<path d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zm3.915 10L3.102 4h10.796l-1.313 7h-8.17zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0z" />
												</svg>
											</button>
										</span>
										<input 
											type="text" 
											name="quantity" 
											class="form-control"
											placeholder="Qty" 
											aria-label="Qty"
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
<%@include file="jQuery.jsp"%>

<script>
	$(document).ready(function(){
	    
		$('.btn-outline-primary').click(function(){
	        
	    	var productID = $(this).closest('tr').find('.product-id').val();
	        var isIncrement = $(this).text() === "+";  // verifica se l'azione è un incremento o un decremento
	
	        var url = isIncrement ? '${pageContext.request.contextPath}/cart-addProduct/' + productID : '${pageContext.request.contextPath}/cart-removeProduct/${product.id()}';
	
	        $.ajax({
	            type: 'POST',
	            url: url,
	            contentType: 'application/json',
	            success: function(response) {
	                    
	                	// seleziona l'elemento DOM della quantità del prodotto
	                    var qtyElement = $('.product-row-' + productID + ' .product-qty');
	                    var currentQty = parseInt(qtyElement.text());
	                    
	                    // incrementa o decrementa la quantità del prodotto nel DOM
	                    qtyElement.text(isIncrement ? currentQty + 1 : currentQty - 1);
	                    
	                    // riapri la modale
	                    $('#exampleModal').modal('show');
	            }
	        });
	    });
	});
</script>


