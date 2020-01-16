<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Forgot Password</h1>
	<font color="green" size="5">${successMsg }</font>
	<font color="red" size="5">${errorMsg }</font>
	<form:form action="handleSubmitBtn" method="post" modelAttribute="userForgotPwdInfo">
		<table>
			<tr>
				<td>Email:</td>
				<td><form:input path="userEmail"/></td>
			</tr>
			<tr>
				<td><input type="reset" value="Reset"/></td>
				<td><input type="submit" value="Submit"/></td>
			</tr>
		</table>
	</form:form>
</body>
</html>