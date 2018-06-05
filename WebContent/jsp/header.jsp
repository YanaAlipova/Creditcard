<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Платежная система | <c:out value="${title}" /></title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<!--  
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js"></script>
 -->
</head>
<body>
	<p>
		<c:out value="${errorMessage}" />
	</p>
	<!--  <b>/jsp/header.jsp | хедерок</b> -->
	<p>
		<c:forEach var="msg" items="${message}">
			<c:out value="${msg}" />
			<br />
		</c:forEach>
	</p>