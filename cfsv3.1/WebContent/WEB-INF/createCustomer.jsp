<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="databean.Customer"%>
<%@ page import="databean.Employee"%>
	
	<jsp:include page="template-admin.jsp" />
	
	<ul class="breadcrumb">
					
		<li class="active"><a href="#">Account</a> 
		<span class="divider">/ </span></li>
					
		<li><a href="createCustomer.do">Create Customer</a> 
		<span class="divider">/ </span>			
		</li>		
		</ul>
		<c:forEach var="error" items="${errors}">
	<h3 style="color: red">${error}</h3>
    </c:forEach>
	<div >
		<h2>Create Customer</h2>
		<h4 class="page-body"  style="color:green">${msg}</h4>
		
	</div>

	<form class="bs-example bs-example-form" role="form" method="post" action="createCustomer.do">
		<div class="panel panel-default">
			<div class="input-group">
				<label for="firstname">User Name*:</label><input required class="form-control" type="text"
					placeholder="Jobs Steve" name="username">
			</div>

			<div class="input-group">
				<label for="firstname">First Name*:</label> <input required class="form-control" type="text"
					placeholder="First Name" name="firstname">
			</div>
			<div class="input-group">
				<label for="lastname">Last Name*:</label> <input required class="form-control" type="text"
					placeholder="Last Name" name="lastname">
			</div>
			<div class="input-group">
				<label for="lastname">Password*:</label> <input required class="form-control" type="password"
					placeholder="Password" name="password">
			</div>
			<div class="input-group">
				<label for="addl1">Address Line 1:</label> <input type="text"class="form-control" 
				placeholder="Street address, P.O. box, company name, c/o" name="addr_line1">
			</div>
			<div class="input-group">
				<label for="addl2">Address Line 2:</label> <input type="text"class="form-control"
					placeholder="Apartment, suite, unit, building, floor, etc."name="addr_line2">
			</div>
			<div class="input-group">
				<label for="city">City: </label> <input class="form-control" type="text" placeholder=""
					name="city">
			</div>
			<div class="input-group">
				<label for="state">State/Province/Region: </label> <input class="form-control"
					type="text" placeholder="" name="state">
			</div>
			<div class="input-group">
				<label for="zip">Zipcode:</label> <input class="form-control" type="text" placeholder=""
					name="zip">
			</div>
			<br>
			<div>
				<button id="fat-btn" class="btn btn-primary"
					data-loading-text="Loading..." type="button"
					onclick="form.submit();">Create Account</button>
			</div>
		</div>


		<td valign="top"></td>
	</form>
	<jsp:include page="template-bottom.jsp" />