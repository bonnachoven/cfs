<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="databean.HistoryBean" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

 
 
<jsp:include page="template-admin.jsp" />

<div class="col-md-10" role="main">
				
		<ul class="breadcrumb">
					
		<li class="active"><a href="adminViewAccount.do">View Account</a> 
		<span class="divider">/ </span></li>
					
		<li><a href="transitionDay.do">TransitionDay</a> 
		<span class="divider">/ </span>			
		</li>		
		</ul>
				
</div> 
 
 
<c:forEach var="error" items="${errors}">
	<h3 style="color: red">${error}</h3>
</c:forEach> 
<div class="col-md-10" role="main" >

Last ended transition day was : ${date}

</div>

<form action="" method="post"> 

<table class="table table-striped">
    <thead>
      <tr>
      	<th>Choose Transition Date:</th> 
      	<th><input type="date" name="date" > </th>
      </tr>
    </thead>
</table>

	
   	<table class="table table-striped">
    <thead>
      <tr>
      	<th>Fund_id</th>
        <th>Fund_Name</th>
        <th>Fund_Symbol</th>
        <th>Price</th>
      </tr>
    </thead>
    <tbody>
    <c:set var="count" value="0" scope="page" />
	<c:forEach var="funds" items="${funds}" >
      <tr>
      	<td name="fund_id">${funds.fund_id}</td>
      	<td>${funds.name}</td>
        <td>${funds.symbol}</td>
        <td><input type="text" name="price${count}"></td>
      </tr>
     	<c:set var="count" value="${count + 1}" scope="page"/>
    </c:forEach>
    </tbody>
  </table>

<br>
<input type="submit" name="action" value="Transit Day">
</form>

<jsp:include page="template-bottom.jsp" />
</body>
</html>