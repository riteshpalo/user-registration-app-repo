<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>User Login Form</h1>
	<font color="red" size="5">${errorMsg}</font>
	<form:form action="handleLoginBtn" method="post" modelAttribute="userLoginInfo">
		<table>
			<tr>
				<td>Email:</td>
				<td><form:input path="userEmail"/></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><form:input path="password"/></td>
			</tr>
			<tr>
				<td><input type="reset" value="Reset" /></td>
				<td><input type="submit" value="Login" /></td>
			</tr>
			<tr>
				<td><a href="forgotPassword">Forgot Password</a></td>
				<td><a href="signUp">Sign Up</a></td>
			</tr>
		</table>
	
	</form:form>
</body>
</html>