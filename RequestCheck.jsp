<%@page import="java.util.List"%>
<%@page import="databean.Customer"%>

<jsp:include page="template-cus.jsp" />



<ul class="breadcrumb">
	<li class="active"><a href="customermanage.do">Payment</a> <span
		class="divider"></span></li>
	<li><a href="requestCheck.do">Request Check</a> <span
		class="divider">/</span></li>
</ul>

<jsp:include page="error-list.jsp" />

<form class="bs-example bs-example-form" role="form" method="post"
	action="requestCheck.do">

	<div class="input-group">Cash Balance: ${customer.cash/100}</div>
	Enter Amount:
	<div class="input-group">
		<input type="text" class="form-control" name="amount">
	</div>
	<div>
		<button id="fat-btn" data-loading-text="Loading..." type="button"
			onclick="form.submit();">Process Request</button>
	</div>
</form>

<jsp:include page="template-bottom.jsp" />
