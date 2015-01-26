<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="databean.Employee"%>
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



<body>
	<div class="container">
		<div class="row">
			<nav class="navbar navbar-default" role="main">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">Carnegie Finance System</a>
				</div>
				<div>
					<p class="navbar-text">Buy and sell funds here!</p>
				</div>
				<div>
					<p class="navbar-text" align="right">Hello ${employee.username}</p>
				</div>
			</nav>
		</div>

		<div class="col-md-2" role="complementary">
				<div class="sidebar-nav">
					<ul class="nav nav-list">
						<li class="nav-header">Account</li>
						<li class="active"><a href="changePassword.do">Change Password</a></li>
						<li><a href="createEmployee.do">Create Employee Account</a></li>
						<li><a href="createCustomer.do">Create Customer Account</a></li>
						<li><a href="resetCustomerPassword.do">Reset Customer Password</a></li>
						<li><a href="viewCusAccount.do">View Account</a></li>
						
					</ul>
					<ul class="nav nav-list">
						<li class="nav-header">Transactions</li>
						<li class="active"><a href="transactionHistory.do">Transaction History</a></li>
						<li><a href="transitionDay.do">Transition Day</a></li>
					</ul>

					<ul class="nav nav-list">
						<li class="nav-header">Funds</li>
						<li class="active"><a href="createFund.do">Create Fund</a></li>
					</ul>

					<ul class="nav nav-list">
						<li class="nav-header">Payment</li>
						<li class="active"><a href="depositCheck.do">Desposit Check</a></li>
					</ul>

				</div>


			</div>
			<div class="col-md-10" role="main">