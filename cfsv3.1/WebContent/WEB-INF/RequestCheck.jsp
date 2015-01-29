<%@page import="java.util.List"%>
<%@page import="databean.Customer"%>

<jsp:include page="template-cus.jsp" />
<jsp:include page="error-list.jsp" />


<ul class="breadcrumb">
	<li class="active"><a href="requestCheck.do">Payment</a> <span class="divider"></span></li>
	<li><a href="requestCheck.do">Request Check</a> <span class="divider">/</span></li>
</ul>

<form class="bs-example bs-example-form" role="form" method="post" action="requestCheck.do"> 
           
      <div class="input-group">
      Available Balance:$  ${balance}
      </div>
   	  Enter Amount:
      <div class="input-group">
         <input type="text" class="form-control"  name="amount">
      </div> 
      <div>
      <button id="fat-btn" class="btn btn-primary" data-loading-text="Loading..." 
   type="button" onclick="form.submit();"> Process Request
	</button>
	</div>
   </form> 
   
   <jsp:include page="template-bottom.jsp" />
