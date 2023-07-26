<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@include file="customTags.jsp"%>

<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.GrantedAuthority" %>

<!DOCTYPE html>
<html>
<head>
	<style>
	
		.product-image {
			height: 500px; /* Imposta l'altezza desiderata */
			width: 100%; /* Imposta la larghezza desiderata */
			object-fit: scale-down;
			/* Questa proprietà ridimensiona l'immagine per riempire il box (senza distorsioni) */
		}
		
		.btn-fixed-width {
		    width: 40px;  /* Sostituisci con la larghezza desiderata */
		}
		
	</style>
	<title>Home</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
	<jsp:include page="navbar.jsp" />
	
	<!-- ADDED PRODUCT MODAL -->
	<div id="successModal" class="modal" tabindex="-1">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title">ADDED PRODUCT</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- CART MODAL -->
	<div id="cartModal" class="modal" tabindex="-1">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title">CART</h1>
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
  								<button type="button" class="btn btn-outline-primary btn-fixed-width">+</button>
 								<button type="button" class="btn btn-outline-primary btn-fixed-width">-</button>
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
	
	<!-- PIGGY MODAL -->
	<div id="piggyModal" class="modal" tabindex="-1">
	 	<div class="modal-dialog">
	    	<div class="modal-content">
	  			<div class="modal-header">
	        	<h5 class="modal-title">PIGGY</h5>
	        	<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      	</div>
	      	<div class="modal-body">
	        	<p>Puggy Modal body text goes here.</p>
	      	</div>
	      	<div class="modal-footer">
	        	<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        	<button type="button" class="btn btn-primary">Save changes</button>
	      	</div>
	    </div>
	  </div>
	</div>
	
	<!-- CONTAINER -->
	<div class="container mt-5">
		<!-- FIRST ROW -->
		<div class="row">
			<c:forEach var="product" items="${products}">
				<div class="col-lg-4 col-md-6 mb-4">
					<!-- CARD -->
					<div class="card">
						<img 
							class="card-img-top product-image"
							src="${pageContext.request.contextPath}/static/img/${product.name()}.jpg"
							alt="#">
						<div class="card-body">
							<h5 class="card-title">${product.name()}</h5>
							<p class="card-text">${product.category()}</p>
							<div class="d-flex">
									<a href="#" class="btn btn-primary btn-sm mt-2 mb-2 me-auto">Go somewhere</a>
									
									<sec:authorize access="isAuthenticated()">
											<!-- CART -->
											<input type="hidden" name="productId" value="${product.id()}"> <!-- questo campo nascosto contiene l'ID del prodotto -->
											<div class="input-group input-group-sm w-50">
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
													type="number" 
													min="0" 
													name="quantityProduct${product.id()}" 
													class="form-control"
													placeholder="Qty" 
													aria-label="Qty"
													aria-describedby="basic-addon1">
											</div>
									</sec:authorize>
									
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</body>
</html>

<script>
	
	$(document).ready(function(){
		
		$(".btn.btn-primary").click(function(event) {
		    event.preventDefault(); // preveniamo l'azione di default del form
    
		    var productId = $(this).parent().parent().siblings('input[name="productId"]').val(); // ottieni l'ID del prodotto
		    var quantityInput = $('input[name="quantityProduct' + productId + '"]'); // ottieni l'input della quantità
		    var quantity = quantityInput.val(); // ottieni la quantità

		    // verifica se quantity e productId non sono vuoti
		    if(quantity !== '' && productId !== '') {
		        $.ajax({
		            url: '${pageContext.request.contextPath}/cart-addProduct/' + productId, // URL a cui inviare la richiesta, aggiungi l'ID del prodotto
		            type: 'GET',
		            contentType: 'application/json',
		            data: {
		                qty: quantity // inviamo la quantità come dato alla richiesta
		            },
		            success: function(response) {
		                
		            	// Se la chiamata ha avuto successo, riempi la modale con il messaggio di successo e mostrala
		                var successMessage = `
		                    <div class="alert alert-success">
		                        <strong>Success!</strong> successful action.
		                    </div>
		                `;
		                $('#successModal .modal-body').html(successMessage);
		                $('#successModal').modal('show');
		           
		            },
		            error: function(xhr, status, error) {
		                
		            	// Se c'è un errore, mostra un alert con l'errore
		            	var errorMessage = `
		                    <div class="alert alert-danger">
		                        <strong>Success!</strong> error action.
		                    </div>
		                `;
		                $('#successModal .modal-body').html(errorMessage);
		                $('#successModal').modal('show');
		            }
					
		        });
		    } else {
		        alert("Inserisci una quantità e assicurati che il prodotto sia valido.");
		    }
		    
		 	// Qui resetta il campo di input
            // quantityInput.val(''); 
		 	
		});
		
		// Definisci la variabile all'esterno del click handler
		var i = 1;
		
		$('.btn-outline-primary').click(function(){
			
	    	var productID = $(this).closest('tr').find('.product-id').val();
	        var isIncrement = $(this).text() === "+";  // verifica se l'azione è un incremento o un decremento
	
	        var url = isIncrement ? '${pageContext.request.contextPath}/cart-addProduct/' + productID : '${pageContext.request.contextPath}/cart-removeProduct/${product.id()}';
	
	        $.ajax({
	            type: 'GET',
	            url: url,
	            contentType: 'application/json',
	            data: {
	                qty: 1 // inviamo la quantità come dato alla richiesta
	            },
	            success: function(response) {
	                    
	                	// seleziona l'elemento DOM della quantità del prodotto
	                    var qtyElement = $('.product-row-' + productID + ' .product-qty');
	                    var currentQty = parseInt(qtyElement.text());
	                    var idSuccesMessage = "successMessage" + i;
	                    
	                 	// Se la chiamata ha avuto successo, riempi la modale con il messaggio di successo e mostrala
	                   var successMessage = 
			                '<div id="successMessage' + i + '" class="alert alert-success">' +
			                '<strong>Success!</strong> successful action.' +
			                '</div>';
		                
		                $('#cartModal .modal-body').prepend(successMessage);
		                
		            	// Rimuovi il messaggio di successo dopo 5 secondi
		                // utilizza una IIFE per fissare il valore corrente di "i"
		                (function(currentI) {
		                    setTimeout(function() {
		                        $('#successMessage' + currentI).fadeOut('slow', function() {
		                            $(this).remove();
		                        });
		                    }, 5000); // 5000 ms = 5 s
		                })(i);
		                
		                i++;
	                    
	                    // incrementa o decrementa la quantità del prodotto nel DOM
	                    qtyElement.text(isIncrement ? currentQty + 1 : currentQty - 1);
	                 	
	            },
	        	error: function(jqXHR, textStatus, errorThrown) {
	            	if (jqXHR.status === 500) {
		                
	            		// Creare un elemento di allerta se il server restituisce un errore 500
						 var alertMessage = 
				                '<div id="alertMessage' + i + '" class="alert alert-danger" role="alert">' +
				                '<strong>No Product in Stock</strong>' +
				                '</div>';
		                
		            	// Aggiungi l'elemento di allerta al DOM (aggiusta il selettore a seconda di dove vuoi che appaia l'allerta)
		                $('#cartModal .modal-body').prepend(alertMessage);
		            	
		             	// Rimuovi il messaggio di successo dopo 5 secondi
		                // utilizza una IIFE per fissare il valore corrente di "i"
		                (function(currentI) {
		                    setTimeout(function() {
		                        $('#alertMessage' + currentI).fadeOut('slow', function() {
		                            $(this).remove();
		                        });
		                    }, 5000); // 5000 ms = 5 s
		                })(i);
		                
		                i++;
	
		            }
		        }
	        });
	    });
	});
	
	// Aggiungi un evento listener alla modale
	$('#successModal').on('hidden.bs.modal', function (e) {
	  // quando la modale viene chiusa, ricarica la pagina
	  window.location.reload();
	});
	
</script>
