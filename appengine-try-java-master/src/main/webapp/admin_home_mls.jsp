<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>

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
	<h1> MLS Data </h1>
</header>
<div class="container">
 <h3>File Upload:</h3>
Select a file to upload: <br />
<form action="<%= blobstoreService.createUploadUrl("/enqueue") %>" method="post"
                        enctype="multipart/form-data">
<input type="file" id="file" name="file" size="50" />
<br />
<input type="submit" value="Upload File" />
</form>
<table id="example" class="table">
	<thead>
		<td id="tableHeader"> Area </td>
		<td> City </td>
		<td> Zip Codes </td>
	</thead>
	<c:forEach items="${areas}" var="area">
	<tbody>	
		<tr>      
		        <td><a href="view_area?areaId=${area.area}">${area.area}</a></td>
		        <td><c:forEach items="${area.cities}" var="city">${city} </c:forEach></td>
		        <td><c:forEach items="${area.zips}" var="zip">${zip} </c:forEach></td>
	    	</tr>
	</tbody>
    </c:forEach>
</table>
</div>

	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/DataTables-1.10.9.js"></script>	
	<script type="text/javascript" src="js/app.js"></script>
</body>
</html>
