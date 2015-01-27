
<%@page import="java.util.List"%>
<%@page import="databean.Fund"%>

<jsp:include page="template-admin.jsp" />
<jsp:include page="error-list.jsp" />


<ul class="breadcrumb">
	<li class="active"><a href="employeemanage.do">Account</a> <span class="divider"></span></li>
	<li><a href="adminViewAccount.do">View Account</a> <span class="divider">/</span></li>
</ul>



 <form class="bs-example bs-example-form" role="form" method="post" action="createFund.do">
      <div class="input-group">
      Fund Name:
         <input type="text" class="form-control" placeholder="name" name="fundName" > 
      </div>
   	  Ticker:
      <div class="input-group">
         <input type="text" class="form-control"  placeholder="1-4 charcters" name="ticker">
      </div> 
      <div>
      <button id="fat-btn" class="btn btn-primary" data-loading-text="Loading..." 
   type="button" onclick="form.submit();"> Create Fund
	</button>
	</div>
   </form> 
   
   <jsp:include page="template-bottom.jsp" />