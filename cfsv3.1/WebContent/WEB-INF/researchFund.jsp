

<html>
<head>

<title>Carnegie Finance System</title>
<link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet">

<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js">
	
</script>
<meta charset="UTF-8">

</head>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="databean.FundViewer"%>
<body>
	<jsp:include page="error-list.jsp" />
	<jsp:include page="template-cus.jsp" />

	<div class="col-lg-6">

		<div class="form-group">

			<label><center>Existing funds for research:</center></label>

			<table class="table table-bordered table-hover">
				<thead>
					<tr>
						<th>Fund Name</th>
						<th>Fund Symbol</th>
						<th>Price</th>
						<th>Price Date</th>

					</tr>
				</thead>
				<tbody>
					<c:forEach var="researchfund" items="${researchfund}">

						<tr>
							<td>${ researchfund.name }</td>
							<td>${ researchfund.symbol }</td>
							<td>${ researchfund.price }</td>
							<td>${ researchfund.price_date }</td>
						</tr>

					</c:forEach>
				</tbody>
			</table>
		</div>

	</div>




	<td valign="top"></td>