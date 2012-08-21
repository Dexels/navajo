<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="/WEB-INF/tld/navajo.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.NavajoContext" scope="session" />	        

			<c:choose>
          		<c:when test="${param['view']=='home' || param['view']==null}">
		            <li class="active"><strong>Home</strong></li>
          		</c:when>
          		<c:otherwise>
		            <li><a href="${pageContext.request.requestURI}?view=home&amp;service=${param['service']}">Home</a></li>
          		</c:otherwise>
          	</c:choose>			
       		<c:if test="${param['service']!=null && param['service']!='' && navajoContext.navajos[param['service']]!=null}">
	          	<c:choose>
	          		<c:when test="${param['view']=='editor' }">
			            <li class="active"><strong>Editor</strong></li>
	          		</c:when>
	          		<c:otherwise>
	          			<c:choose>
	          				<c:when test="${param['service']!=''}">
		      				</c:when>
	          			</c:choose>
	   		            <li><a href="${pageContext.request.requestURI}?view=editor&amp;service=${param['service']}">Editor</a></li>
	    
	          		</c:otherwise>
	          	</c:choose>
        	</c:if>
 
       		
       		<c:if test="${param['service']!=null && param['service']!='' && navajoContext.navajos[param['service']]!=null}">
	          	<c:choose>
	          		<c:when test="${param['view']=='tml' || param['view']=='laszlo'}">
			            <li class="active"><strong>Tml Source</strong></li>
	          		</c:when>
	          		<c:otherwise>
		            		<li><a href="${pageContext.request.requestURI}?view=tml&amp;service=${param['service']}">Tml Source</a></li>
	
	          		</c:otherwise>
	          	</c:choose>
   			</c:if>
      
		   <c:if test="${param['service']!=null && param['service']!=''}">
	         	<c:choose>
	         <c:when test="${param['view']=='tsl'}">
	            <li class="active"><strong>${serverContext.scriptStatus.language}</strong></li>
         	</c:when>
         	<c:otherwise>
	            <li><a href="${pageContext.request.requestURI}?view=tsl&amp;service=${param['service']}">${serverContext.scriptStatus.language}</a></li>
       		</c:otherwise>
       		</c:choose>
  			</c:if>
  			
       		<c:if test="${serverContext.scriptStatus.compiled}">
	          	<c:choose>
	          		<c:when test="${param['view']=='javasource'}">
			            <li class="active"><strong>Compiled</strong></li>
	          		</c:when>
	          		<c:otherwise>
	       				<c:if test="${param['service']!=null && param['service']!=''}">
				            <li><a href="${pageContext.request.requestURI}?view=javasource&amp;service=${param['service']}">Compiled</a></li>
	    				</c:if>
	          		</c:otherwise>
	          	</c:choose>
          	</c:if>

	<%--Removed history and access detail pages for now, check old CVS to retrieve if needed --%>          	
		
          	          