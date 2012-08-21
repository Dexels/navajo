<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="../WEB-INF/tld/c.tld"%>
<%@ taglib prefix="nav" uri="../WEB-INF/tld/navajo.tld"%>
<%@ page import="com.dexels.navajo.client.context.NavajoContext"%>
<jsp:useBean id="navajoContext" type="com.dexels.navajo.client.context.NavajoContext" scope="session" />
	<div>
		<c:choose>
			<c:when test="${navajoContext.property.direction == 'out'}">
				<c:choose>
					<c:when test="${navajoContext.property.type == 'boolean'}">
						<c:choose>
							<c:when test="${navajoContext.property.value=='true'}">
								<input type="checkbox" name="${navajoContext.propertyPath}" checked="checked" disabled="disabled"/>
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="${navajoContext.propertyPath}" disabled="disabled"/>
							</c:otherwise>
						</c:choose>

					</c:when>
					<c:when test="${navajoContext.property.type == 'selection'}">
							<c:choose>
								<c:when test="${navajoContext.property.cardinality == '1'}">
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
								<c:otherwise>
									<select name="${navajoContext.propertyPath}" disabled="disabled" multiple="multiple" size="5">
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
								</c:otherwise>
							</c:choose>
		



					</c:when>
					<c:when test="${navajoContext.property.type == 'binary'}">
							<a href="tml/binary.jsp?path=${navajoContext.propertyPath}">Download binary</a>
						<c:if test="${navajoContext.property.subTypes['mime']=='image/jpeg'}">
							<a href="tml/binary.jsp?path=${navajoContext.propertyPath}"><img src="tml/binary.jsp?path=${navajoContext.propertyPath}"/></a>
						</c:if>
						<c:if test="${navajoContext.property.subTypes['mime']=='image/png'}">
							<a href="tml/binary.jsp?path=${navajoContext.propertyPath}"><img src="tml/binary.jsp?path=${navajoContext.propertyPath}"/></a>
						</c:if>
						<c:if test="${navajoContext.property.subTypes['mime']=='image/gif'}">
							<a href="tml/binary.jsp?path=${navajoContext.propertyPath}"><img src="tml/binary.jsp?path=${navajoContext.propertyPath}"/></a>
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
						<c:choose>
							<c:when test="${navajoContext.property.value=='true'}">
								<input type="checkbox" name="${navajoContext.propertyPath}" checked="checked"/>
								<input type="hidden" name="${navajoContext.propertyPath}" value="off">
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="${navajoContext.propertyPath}"/>
								<input type="hidden" name="${navajoContext.propertyPath}" value="off">
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test="${navajoContext.property.type == 'selection'}">

							<c:choose>
								<c:when test="${navajoContext.property.cardinality == '1'}">
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
									<select name="${navajoContext.propertyPath}" multiple="multiple" size="5">
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
								</c:otherwise>
							</c:choose>
		
						
						
						
					</c:when>
					<c:when test="${navajoContext.property.type == 'binary'}">
						Binary type: ${navajoContext.property.length } subtypes: ${navajoContext.property.name}
						<c:if test="${navajoContext.property.subTypes['mime']=='image/jpeg'}">
							<a href="tml/binary.jsp?path=${navajoContext.propertyPath}">
								<img src="tml/binary.jsp?path=${navajoContext.propertyPath}"/>
							</a>
							${navajoContext.property.subTypes['mime']=='image/jpeg'}
						</c:if>
					</c:when>
					
					<c:otherwise>
						<input type="text" size="70" value="${navajoContext.property.value}" name="${navajoContext.propertyPath}"/>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</div>
