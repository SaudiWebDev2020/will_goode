<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JavaStore</title>
<link rel="stylesheet"
	href="/webjars/bootstrap/4.5.0/css/bootstrap.min.css" />
<script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="jumbotron">
			<h1>Java Store</h1>
		</div>


		<form:form action="/product" method="post" modelAttribute="newProduct">
			<div class="form-group">
				<label>Product Name:</label>
				<form:input path="name" class="form-control" />
				<form:errors path="name" class="text-danger" />
			</div>
			<div class="form-group">
				<label>Product Price:</label>
				<form:input path="price" class="form-control" />
				<form:errors path="price" class="text-danger" />
			</div>
			<div class="form-group">
				<label>Categories:</label>
				<form:textarea path="categoryInput" class="form-control" />
				<small class="form-text text-muted">Seperate each category with a ",".</small>
				<form:errors path="categoryInput" class="text-danger" />
			</div>
			<input type="submit" value="Create Product" class="btn btn-primary" />
		</form:form>

	</div>
</body>
</html>