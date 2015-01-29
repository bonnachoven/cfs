<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="databean.Customer"%>
<%@ page import="databean.ViewAccountRecord"%>


<jsp:include page="template-cus.jsp" />

<ul class="breadcrumb">
	<li class="active"><a href="#">Account</a> <span class="divider"></span></li>
	<li><a href="#">View Account</a> <span class="divider">/</span></li>
</ul>


<div>	
	<div class="panel panel-default">

		<div class="panel-heading">
			<h3 class="panel-title">Account Name</h3>
		</div>
		<h3 class="panel-body">${customer.firstname},${customer.lastname}</h3>

		<div class="panel-heading">
			<h3 class="panel-title">Address</h3>
		</div>
		<div class="panel-body">
			<address>
				<strong>${customer.addr_line1}</strong><br />${customer.addr_line2}
				<br /> ${customer.city}, ${customer.state} ${customer.zip} <br />
			</address>
		</div>

		<div class="panel-heading">
			<h3 class="panel-title">Last Trading Date</h3>
		</div>
		<h4 class="panel-body">${lastTransactionDay}</h4>

		<div class="panel-heading">
			<h3 class="panel-title">Cash Balance</h3>
		</div>
		<h3 class="panel-body">${cash}</h3>

		<div class="panel-heading">
			<h3 class="panel-title">Shares of Funds</h3>
		</div>
		<div class="panel-body">
			<table class="table table-hover">

				<h3>Total Value: $ ${value}</h3>
				<thead>
					<tr>
						<th>Name</th>
						<th>Ticker</th>
						<th>Shares</th>
						<th>Latest Price</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="records" items="${records}">
						<tr>
							<td>${records.fundName}</td>
							<td>${records.fundTicker}</td>
							<td>${records.shares}</td>
							<td>${records.currentPrice}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>

</div>
<jsp:include page="template-bottom.jsp" />