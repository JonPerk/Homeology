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

	
</head>

<body>
<header>
	<div id="logo">
		<a href="Admin_home.html"><img src="img/logo.png"/></a>
	</div>
	<h1> Customers </h1>
</header>
<div class="container">
<table id="example" class="table table-hover">
	<thead>
		<tr id="tableHeader">
			<th id="tableHeader"> First Name </th>
			<th> Email </th>
			<th> Matched Areas </th>
		</tr>
	</thead>
	
	<tbody>
	<c:choose>
	<c:when test="${empty users}"></c:when>
	<c:otherwise>
	<c:forEach items="${users}" var="user">
		<tr>      
	        <td>${user.fname}</td>
		        <td>${user.email}</td>
	        <td>
	        <c:choose>
			<c:when test="${empty user.matchedAreas}"></c:when>
			<c:otherwise>
			<c:forEach items="${user.matchedAreas}" var="area">
			<a href="view_area?areaId=${area}">${area}</a>&nbsp;
			</c:forEach>
   			</c:otherwise></c:choose>
		</tr>
    </c:forEach>
    </c:otherwise></c:choose>
</table>
</div>

	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/DataTables-1.10.9.js"></script>	
	<script type="text/javascript" src="js/app.js"></script>
</body>
</html>
