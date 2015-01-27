<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="databean.Customer"%>
<%@ page import="databean.TransactionBean"%>
<%@ page import="databean.FundViewer"%>


<jsp:include page="template-cus.jsp" />


<ul class="breadcrumb">

	<li class="active"><a href="#">Funds</a> <span class="divider">/
	</span></li>

	<li><a href="sellFund.do">Sell Fund</a> <span class="divider">/
	</span></li>
</ul>

<div>
	<h2>Sell Funds</h2>
	<h4 class="page-body" style="color: green">${msg}</h4>
	<jsp:include page="error-list.jsp" />
</div>

<div class="panel panel-default">

	<div class="form-group">

		<label>Please select the following fund you want to sell with
			number of format #.###:</label>

		<table class="table table-bordered table-hover">
			<thead>
				<tr>
					<th>Fund Name</th>
					<th>symbol</th>
					<th>Shares held</th>
					<th>Shares seek to sell</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="fund" items="${fundList}">

					<form action="sellFund.do" method="post">
						<tr>
							<td>${ fund.getName() }</td>
							<td>${ fund.getSymbol() }</td>
							<td style="text-align: right;">${ fund.getShares() }</td>
							<td><input name="shares"
								placeholder="Type in numbers in format 100.123" size="35">
								<input name="fund_id" type="hidden"
								value="${ fund.getFund_id() }" size="35">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="submit" name="action" value="sell"">Submit</button>
							</td>
						</tr>
					</form>

				</c:forEach>
			</tbody>
		</table>
	</div>

</div>
<jsp:include page="template-bottom.jsp" />