<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<!DOCTYPE>

<html>
<head>
	<title> Admin Home </title>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<link rel="icon" type="image/ico" href="../img/homeology_logo.ico.png"/>
	<link rel="shortcut icon" href="../img/homeology_logo.ico.png"/>
	<link rel="stylesheet" type="text/css" href="css\homeology.css"/>
	<link rel="stylesheet" type="text/css" href="css\DataTables-1.10.9.css"/>
	<link rel="stylesheet" type="text/css" href="css\bootstrap-3.3.5-dist\css\bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="css\bootstrap-3.3.5-dist\css\bootstrap.min.css"/>
	<link rel="stylesheet" type="text/css" href="css\bootstrap-3.3.5-dist\css\bootstrap-theme.css"/>
	<link rel="stylesheet" type="text/css" href="css\bootstrap-3.3.5-dist\css\bootstrap-theme.min.css"/>
	<style>

	</style>
	
</head>

<body>

<header>
	<div id="logo">
		<a href="Admin_home.html"><img src="img/logo.png"/></a>
	</div>
	<h1> Admin Console </h1>
</header>

<div class="container">
	<table class="table">
	<tr id="tableHeader">
		<td id="tableHeader"> Type </td>
		<td> Beds </td>
		<td> Baths </td>
		<td> Avg. Price </td>
	</tr>
	<c:forEach items="${rents}" var="rent">
		<tr>      
	        <td>Rent</td>
	        <td>${rent.beds}</td>
	        <td>${rent.baths}</td>
	        <td>$${rent.price}</td>
    	</tr>
    </c:forEach>
    <c:forEach items="${buys}" var="buy">
		<tr>      
	        <td>Buy at ${buy.downPayment*100}%</td>
	        <td>${buy.beds}</td>
	        <td>${buy.baths}</td>
	        <td>$${buy.price}</td> 
    	</tr>
    </c:forEach>
</table>
</div>

	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/DataTables-1.10.9.js"></script>	
	<script type="text/javascript" src="js/app.js"></script>
</body>
</html>
