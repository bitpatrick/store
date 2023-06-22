<%@include file="libs.jsp"%>

<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap demo</title>
</head>
<body>
	<h1>Hello, world!!!</h1>

	<
	<div class="container">
		<div class="row">
			<div class="col-12 col-md-6 col-lg-3">Colonna 1</div>
			<div class="col-12 col-md-6 col-lg-3">Colonna 2</div>
			<div class="col-12 col-md-6 col-lg-3"><button id="myButton" type="button" class="btn btn-danger">Danger</button></div>
			<div class="col-12 col-md-6 col-lg-3">Colonna 4</div>
		</div>
	</div>
	<a href="http://jquery.com/">jQuery</a>

</body>
</html>
<script>
	$(document).ready(function() {

		$("a").click(function(event) {

			alert("Thanks for visiting!");

		});

	});
	
	$(document).ready(function() {
	    var basePath = "<%=request.getContextPath()%>";

	    $("#myButton").click(function(event) {
	        $.ajax({
	            url: basePath + "/api/products-in-stock",
	            method: 'GET',
	            xhrFields: {
	                responseType: 'blob' // Important part to receive a Blob
	            },
	            success: function (data) {
	                var blob = new Blob([data], {type: 'application/pdf'}); // Create a Blob from the PDF Stream
	                var url = URL.createObjectURL(blob);
	                window.open(url, '_blank'); // Open the PDF in new browser tab
	            },
	            error: function(error) {
	                console.log(error);
	            }
	        });
	    });
	});

</script>

