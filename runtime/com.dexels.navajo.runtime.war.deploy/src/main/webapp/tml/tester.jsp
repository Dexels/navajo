<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="../WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="../WEB-INF/tld/navajo.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<%@ page trimDirectiveWhitespaces="true" %>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.NavajoContext" scope="session" />	        <nav:service service="${param['service']}">
	          <div id="col1_content" class="clearfix">
	            <h3>Message structure</h3>
	            <div class="info">
					<c:import url="tml/writetmltree.jsp"/>
	    	   </div>
	    	   <h3>Methods</h3>
	            
	            <div class="info">
	    			<c:import url="tml/writemethodlist.jsp"/>
	    	   </div>
	    	   <h3>Methods</h3>
				  <div class="info">
		    		<ul>
						<li><a href="aap">Script source</a></li>
						<li><a href="aap">TML source</a></li>
						<li><a href="aap">Documentation</a></li>
					</ul>			
				</div>
	  			</div>
	    	</nav:service>  