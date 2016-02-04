<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<!DOCTYPE>

<html>
<head>
	<title> Customer Details </title>
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
	<h1> Customer Details </h1>
</header>

<div class="container">
	<table id="example" class="table table-hover">
	<thead>
		<tr id="tableHeader">
			<th> Customer Name </th>
			<th> Email </th>
			<th> Phone No. </th>
			<th> Full Report </th>
		</tr>
		</thead>
	<tbody>
	<!--<c:choose>
	<c:when test="${empty rents}"></c:when>
	<c:otherwise>
	<c:forEach items="${rents}" var="rent">
		<tr>      
		        <td>Rent</td>
		        <td>${rent.beds}</td>
		        <td>${rent.baths}</td>
		        <td>$${rent.price}</td>
    		</tr>
    </c:forEach>
    </c:otherwise></c:choose>
    <c:choose>
    <c:when test="${empty buys}"></c:when>
    <c:otherwise>-->
    
    <!--</c:otherwise>
    </c:choose-->
    <c:choose>
    <c:forEach items="${user}" var="user">
		<tr>      
		        <td>${user.fname}</td>
		        <td>${user.email}</td>
		        <td>${user.phone}</td>
		        <td><a href="">Details on ${user.fname}</a></td>
    		</tr>
    </c:forEach>
    </c:choose>
    </tbody>
</table>
</div>

	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/DataTables-1.10.9.js"></script>	
	<script type="text/javascript" src="js/app.js"></script>
</body>
</html>
