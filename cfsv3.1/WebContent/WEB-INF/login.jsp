<jsp:include page="template-login-top.jsp" />
<jsp:include page="error-list.jsp" />
<form method="POST">
	<legend>Login</legend>
	<table>
		<tr>
			<td><input type="text" placeholder="User Name" name="username"
				value="${form.username}" /></td>
		</tr>
		<tr>
			<td>&#12288;</td>
		</tr>
		<tr>
			<td><input type="password" name="password"
				placeholder="password" /></td>
		</tr>
		<tr>
			<td>&#12288;</td>
		</tr>
		<tr>
			<td><input type="radio" name="type" value="c" checked="checked">Login
				as customer</td>
			<td><input type="radio" name="type" value="e">Login as
				employee</td>
		</tr>
		<tr>
			<td>&#12288;</td>
		</tr>

		<tr>
			<td><button type="submit" >Login</button></td>
		</tr>
	</table>

</form>
<jsp:include page="template-login-bottom.jsp" />


