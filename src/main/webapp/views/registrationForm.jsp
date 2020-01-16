<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Registration Form</title>
<script type="text/javascript"
	src="/js/jquery.min.js">
</script>
<script type="text/javascript">
	$(document).ready(function(e) {
		$("#emailId").blur(function(event) {
			$("#emailErr").html("");
			var userEmail=$("#emailId").val();
			$.ajax({
	            url : 'validateEmail?email='+userEmail,
	            success : function(data) {
	            	if(data=='Duplicate'){
		                $("#emailErr").html("Email Already Exist, Please Try Another One");
		                $("#emailId").focus();
	            	}
	            }
	        });
		});
	});
</script>
</head>
<body>
	<h1>User Registration Form</h1>
	<font color="green" size="5">${successMsg }</font>
	<font color="red" size="5">${errorMsg }</font>
	<form:form action="registerUser" method="post" modelAttribute="userInfo">
		<table>
			<tr>
				<td>Name:</td>
				<td><form:input path="userName"/></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td>
					<form:input path="userEmail" id="emailId" />
					<div id="emailErr" style="color: red"></div>
				</td>
			</tr>
			<tr>
				<td>Contact Number:</td>
				<td><form:input path="userContactNo"/></td>
			</tr>
			<tr>
				<td><input type="reset" value="Reset"/></td>
				<td><input type="submit" value="Register"/></td>
			</tr>
			
		</table>
	</form:form>
</body>
</html>