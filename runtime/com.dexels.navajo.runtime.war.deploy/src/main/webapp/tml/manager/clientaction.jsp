<!--server="penelope1.dexels.com/sportlink/knvb/servlet/Postman"	-->
		<c:if test="${param['service']!= null }">
			<c:if test="${param[param['service']]==true}">
				<c:choose>
					<c:when test="${param.inputNavajo!=null}">
							<nav:call service="${param['service']}" navajo="${param['inputNavajo']}"/>
					</c:when>
					<c:otherwise>
						<nav:call service="${param.service}"/>
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:if>

	<c:choose >
		<c:when test="${param['command']=='setFolder'}">
			<c:set target="${serverContext}" property="path" value="${param['folder']}"/>
		</c:when>
	</c:choose>
