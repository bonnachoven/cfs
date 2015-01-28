<%@page import="java.util.List"%>
<%@page import="databean.Employee"%>

<jsp:include page="template-admin.jsp" />
<jsp:include page="error-list.jsp" />


<ul class="breadcrumb">
	<li class="active"><a href="customerSearch.do">Payment</a> <span class="divider"></span></li>
	<li><a href="customerSearch.do">Select Customer</a> <span class="divider">/</span></li>
	<li><a href="depositCheck.do">Deposit Check</a> <span class="divider">/</span></li>
</ul>

<form class="bs-example bs-example-form" role="form" method="post" action="depositCheck.do"> 
           
      <div class="input-group">
     Customer's Name: ${selectedcustomer.lastname} ${selectedcustomer.firstname}
      </div>
      
   	  Enter Amount: 
      <div class="input-group">
         <input type="text" class="form-control"  name="amount" placeholder= "0.00">
         <input type="hidden" class="form-control"  name="customer_id" value="${selectedcustomer.customer_id}">
      </div> 
      <div>
      <button id="fat-btn" class="btn btn-primary" data-loading-text="Loading..." 
   type="button" onclick="form.submit();"> Deposit Check
	</button>
	</div>
   </form> 
   
   <jsp:include page="template-bottom.jsp" />
