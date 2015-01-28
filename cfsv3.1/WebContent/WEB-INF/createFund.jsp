
<%@page import="java.util.List"%>
<%@page import="databean.Fund"%>

<jsp:include page="template-admin.jsp" />



<ul class="breadcrumb">
	<li class="active"><a href="employeemanage.do">Account</a> <span
		class="divider"></span></li>
	<li><a href="createFund.do">Create Fund</a> <span class="divider">/</span></li>
</ul>
<c:forEach var="error" items="${errors}">
	<h3 style="color: red">${error}</h3>
    </c:forEach>
<div>
	<h2>Create Funds</h2>
	<h4 class="page-body"  style="color:green">${msg}</h4>
	<jsp:include page="error-list.jsp" />
</div>
<div class="panel panel-default">

	<div class="form-group">
		<label><center>Please fill in the fund information
				you want to create:</center></label>

		<table class="table table-bordered table-hover">
			<thead>
				<tr>
					<th>Fund Name</th>
					<th>Symbol</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<form action="createFund.do" method="post">
					<tr>
						<td><input type="text" class="form-control"
							placeholder="name" name="name"></td>
						<td><input type="text" class="form-control"
							placeholder="1-4 charcters" name="symbol"></td>
						<td><button id="fat-btn" class="btn btn-primary"
								data-loading-text="Loading..." type="button"
								onclick="form.submit();">Create</button></td>
					</tr>
				</form>
			</tbody>
		</table>
	</div>
	
</div>
<jsp:include page="template-bottom.jsp" />
