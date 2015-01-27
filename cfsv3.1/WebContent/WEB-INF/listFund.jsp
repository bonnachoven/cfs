

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

					</tr>
				</thead>


				<c:forEach var="fund" items="${FundList}">
					<form id = "${fund.fund_id}" method="post" action="list.do">
						<input type="hidden" value="${fund.name}" name="name">
						<input type="hidden" value="${fund.fund_id}" name="fund_id">
						<input type="hidden" value="${fund.symbol}" name="symbol">
						<table>
							<tr>
								<td><a href="javascript:document.getElementById('${fund.fund_id}').submit();">${fund.name}</a></td>
							</tr>
						</table>

					</form>

				</c:forEach>
		</div>

	</div>




	<td valign="top"></td>