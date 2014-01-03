<!DOCTYPE html>
<html lang="en">
<%@ page import="com.multi.oauth2.provider.vo.*,java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <head>
    <meta charset="utf-8">
    <title>SportLink</title>
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <link href="css/signin.css" rel="stylesheet">
<%
	UserVO u = (UserVO)session.getAttribute("userVO");
	List<ClientVO> list = (List<ClientVO>)request.getAttribute("list");
%>    


</head>
  <body>
	<p>Name ${sessionScope.user}</p>
	<p>List ${sessionScope.clients}</p>
    <div class="container">
		<table>
			<tr>
				<th>Clients</th>
			</tr>
			<tr>
				<td>tralala</td>
			</tr>			
	<c:forEach var="c" items="${sessionScope.clients}">
	    <c:out value="${window}"/> 
	</c:forEach>
		</table>


    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  </body>
</html>