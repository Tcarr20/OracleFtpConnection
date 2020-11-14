<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome to the page of mystery! </title>
</head>
<body>
		<h1>Tony was here!</h1>
		
		
	<table>
	  <c:forEach items="${list}" var="item">
	    <tr>
     		<td ><c:out value="${item.getTitle()}" /></td>
     		
     		<td ><c:out value="${item.getDescription()}" /></td>
	    </tr>
	  </c:forEach>
	</table>
</body>



</html>