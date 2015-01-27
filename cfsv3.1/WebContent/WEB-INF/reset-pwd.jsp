<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="databean.Customer"%>


<jsp:include page="template-admin.jsp" />

<ul class="breadcrumb">
	<li class="active"><a href="employeemanage.do">Account</a> <span
		class="divider"></span></li>
	<li><a href="#">Reset Password</a> <span class="divider">/</span></li>
</ul>

<div class="row">
	<div class="col-lg-12">
		<h2 class="page-header">Reset Password</h2>
		<h4 class="page-body" style="color: green">${msg}</h4>
		<jsp:include page="error-list.jsp" />

	</div>
</div>

<form method="post">
	<div class="form-group">
		<label>User name</label> <input required class="form-control"
			type="text" name="username" placeholder="customer's user name">

	</div>
	<div class="form-group">
		<button type="submit" name="action" value="reset">Reset
			Password</button>
	</div>
</form>
<p>
	<br>
</p>

<jsp:include page="template-bottom.jsp" />