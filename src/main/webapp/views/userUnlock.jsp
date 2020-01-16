<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	function validate() {
		var newPwd=document.getElementById("nwErrPwd").value;
		var cnfPwd=document.getElementById("cfErrPwd").value;
		
		if(newPwd==cnfPwd){
			return true;
		}
		else{
			alert("New Password And Confirm Password Are Not Matched");
			return false;
		}
	}
</script>
</head>
<body>
	<h1>Unlock Form</h1>
	<form:form action="handleUnlockBtn" method="post" modelAttribute="userUnlockInfo">
		<font color="green" size="5">${successMsg}</font>
		<font color="red" size="5">${errorMsg}</font>
		<form:hidden path="userEmail"/>
		<table>
			<tr>
				<td>Email:</td>
				<td>${userUnlockInfo.userEmail}</td>
			</tr>
			<tr>
				<td>Temporary Password:</td>
				<td><form:input path="tempPwd" /></td>
			</tr>
			<tr>
				<td>New Password:</td>
				<td><form:input path="newPwd" id="nwErrPwd" /></td>
			</tr>
			<tr>
				<td>Confirm Password:</td>
				<td><form:input path="cnfPwd" id="cfErrPwd" /></td>
			</tr>
			<tr>
				<td><input type="reset" value="Reset"></td>
				<td><input type="submit" value="Unlock" onclick="return validate()"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>