
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="databean.Employee"%>

<jsp:include page="template-admin.jsp" />
<jsp:include page="error-list.jsp" />

<ul class="breadcrumb">
	<li class="active"><a href="employeemanage.do">Payment</a> <span class="divider"></span></li>
	<li><a href="customerSearch.do">Select Customer</a> <span class="divider">/</span></li>
</ul>


   
   <table>
    <div class="input-group">
      Select a customer from the list below! 
      <br />
   	  List of Customers:
   	  </div>
   	  <c:forEach var="list" items="${customerList}">
   	  <tr>
   	  <td><h4><a href="depositCheck.do?customer_id=${list.customer_id}">${list.lastname} ${list.firstname}</a></h4></td>
   	  </tr>
   	  	</c:forEach>
   	  	</table>
   <jsp:include page="template-bottom.jsp" />


