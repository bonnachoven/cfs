<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<jsp:include page="template-admin.jsp" />

<div class="col-md-10" role="main">
				
		<ul class="breadcrumb">
					
		<li class="active"><a href="adminViewAccount.do">View Account</a> 
		<span class="divider">/ </span></li>
					
		<li><a href="transitionDay.do">TransitionDay</a> 
		<span class="divider">/ </span>	
		
		<li><a href="#">TransitionDayStatus</a> 
		<span class="divider">/ </span>			
		</li>		
		</ul>
				
</div> 
 
 <div class="col-md-10" >
 
 
	All pending transactions were executed

 </div>


<jsp:include page="template-bottom.jsp" />