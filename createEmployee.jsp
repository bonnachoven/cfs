<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="databean.Customer"%>
<%@ page import="databean.Employee"%>
	
	<jsp:include page="template-admin.jsp" />
	
	<ul class="breadcrumb">
					
		<li class="active"><a href="#">Account</a> 
		<span class="divider">/ </span></li>
					
		<li><a href="createCustomer.do">Create Employee</a> 
		<span class="divider">/ </span>			
		</li>		
		</ul>
	<div >
		<h2>Create Employee</h2>
		<h4 class="page-body"  style="color:green">${msg}</h4>
		<jsp:include page="error-list.jsp" />
	</div>
	
	<form class="bs-example bs-example-form" role="form" method="post"
		action="createEmployee.do">
		<div class="panel panel-default">


			<div class="input-group">
				<label for="username">User Name*:</label><input required class="form-control" type="text"
					placeholder="Jobs Steve" name="username">
			</div>
			<div class="input-group">

				<label for="firstname">First Name*:</label> <input required class="form-control" type="text"
					placeholder="First Name" name="firstname">

			</div>
			<br>

			<div class="input-group">

				<label for="lastname">Last Name*:</label> <input required class="form-control"type="text"
					placeholder="Last Name" name="lastname">

			</div>
			<div class="input-group">

				<label for="password">Password*:</label> <input required class="form-control" type="password"
					placeholder="Password" name="password">

			</div>
			<br>	
			<button id="fat-btn" class="btn btn-primary"
				data-loading-text="Loading..." type="button"
				onclick="form.submit();">Create Account</button>

			<td valign="top">
		</form>
		<jsp:include page="template-bottom.jsp" />