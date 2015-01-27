<!-- 
Hua-Ming Lee
huamingl
08-600
2014/12/1
 -->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="template-admin.jsp" />

<h2>Error</h2>

<c:forEach var="error" items="${errors}">
	<h3 style="color: red">${error}</h3>
</c:forEach>

<p>
	<c:choose>
		<c:when test="${ (empty employee) }">
				<a href="login.do">login</a>
			</c:when>
	
	</c:choose>
</p>

<jsp:include page="template-bottom.jsp" />
