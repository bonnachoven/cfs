<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="databean.Customer"%>
<%@ page import="databean.ViewAccountRecord"%>


<jsp:include page="template-admin.jsp" />

<ul class="breadcrumb">
	<li class="active"><a href="#">Account</a> <span class="divider"></span></li>
	<li><a href="#">View Account</a> <span class="divider">/</span></li>
</ul>







<div>

	<div class="panel panel-default">
		<form method="post">
			<div class="form-group">
				<label>User name</label> <input required class="form-control"
					type="text" name="username" placeholder="customer's user name">

			</div>
			<div class="form-group">
				<button type="submit" name="action" value="view">View
					Account</button>
			</div>
		</form>

	</div>

	<c:choose>
		<c:when test="${ (empty viewcustomer) }">
		</c:when>
		<c:otherwise>
			<div>
				<div class="panel panel-default">

					<div class="panel-heading">
						<h3 class="panel-title">Account Name</h3>
					</div>
					<h3 class="panel-body">${viewcustomer.firstname},${viewcustomer.lastname}</h3>

					<div class="panel-heading">
						<h3 class="panel-title">Address</h3>
					</div>
					<div class="panel-body">
						<address>
							<strong>${viewcustomer.addr_line1}</strong><br />${viewcustomer.addr_line2}
							<br /> ${viewcustomer.city}, ${viewcustomer.state} ${viewcustomer.zip} <br />
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
		</c:otherwise>
	</c:choose>



</div>



<jsp:include page="template-bottom.jsp" />