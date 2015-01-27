<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="databean.Customer"%>


<jsp:include page="template-cus.jsp" />

<ul class="breadcrumb">
	<li class="active"><a href="#">Account</a> <span class="divider"></span></li>
	<li><a href="#">Change Password</a> <span class="divider">/</span></li>
</ul>

<div class="row">
	<div class="col-lg-12">
		<h2 class="page-header">Change Password</h2>
			<h4 class="page-body"  style="color:green">${msg}</h4>
			<jsp:include page="error-list.jsp" />

	</div>
</div>

<form method="post">

	<div class="form-group">
		<label>Current Password</label> <input required class="form-control"
			type="password" name="currentPassword" placeholder="Current Password">
	</div>

	<div class="form-group">
		<label>New Password</label> <input required class="form-control"
			type="password" name="newPassword" placeholder="New Password">
	</div>
	<div class="form-group">
		<label>Confirm new Password</label> <input required
			class="form-control" type="password" name="confirmPassword"
			placeholder="Confirm new Password">
	</div>
	<button type="submit" name="action" value="change" class="btn btn-default">Change
		Password</button>
</form>
<p>
	<br>
</p>

<jsp:include page="template-bottom.jsp" />