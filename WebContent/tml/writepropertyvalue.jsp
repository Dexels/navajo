<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.dexels.navajo.tipi.appmanager.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="c" uri="../WEB-INF/tags/c.tld"%>
<%@ taglib prefix="nav" uri="../WEB-INF/tags/navajo.tld"%>
<%@ page import="com.dexels.navajo.jsp.NavajoContext"%>
<jsp:useBean id="navajoContext" class="com.dexels.navajo.jsp.NavajoContext" scope="session" />
<jsp:useBean id="manager" class="com.dexels.navajo.tipi.appmanager.ApplicationManager" scope="page" />
	<div>
		<c:choose>
			<c:when test="${navajoContext.property.direction == 'out'}">
				<c:choose>
					<c:when test="${navajoContext.property.type == 'boolean'}">
						<input type="checkbox" name="${navajoContext.propertyPath}" disabled="disabled"/>
					</c:when>
					<c:when test="${navajoContext.property.type == 'selection'}">
						<select name="${navajoContext.propertyPath}" disabled="disabled">
							<c:forEach items="${navajoContext.property.allSelections}" var="selection">
								<c:choose>
									<c:when test="${selection.selected}">
										<option selected="selected" value='${selection.value}'>${selection.name}</option>
									</c:when>
									<c:otherwise>
										<option value='${selection.value}'>${selection.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</c:when>
					<c:when test="${navajoContext.property.type == 'binary'}">
							<a href="binary.jsp?path=${navajoContext.propertyPath}"><img src="binary.jsp?path=${navajoContext.propertyPath}"/></a>
						<c:if test="${navajoContext.property.subTypes['mime']=='image/jpeg'}">
						</c:if>
						
					</c:when>
					<c:otherwise>
						<input type="text" disabled="disabled" value="${navajoContext.property.value}" name="${navajoContext.propertyPath}"/>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${navajoContext.property.type == 'boolean'}">
						<input type="checkbox" name="${navajoContext.propertyPath}"/>
					</c:when>
					<c:when test="${navajoContext.property.type == 'selection'}">
						<select name="${navajoContext.propertyPath}">
							<c:forEach items="${navajoContext.property.allSelections}" var="selection">
								<c:choose>
									<c:when test="${selection.selected}">
										<option selected="selected" value='${selection.value}'>${selection.name}</option>
									</c:when>
									<c:otherwise>
										<option value='${selection.value}'>${selection.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</c:when>
					<c:otherwise>
						<input type="text" value="${navajoContext.property.value}" name="${navajoContext.propertyPath}"/>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</div>
