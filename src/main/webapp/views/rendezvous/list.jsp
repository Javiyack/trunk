<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@page import="org.apache.commons.lang.time.DateUtils"%>
<%@page import="org.hibernate.engine.spi.RowSelection"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jsp:useBean id="date" class="java.util.Date" />

<display:table pagesize="5" class="displaytag" name="rendezvouses" requestURI="${requestUri}" id="row">
	<spring:message code="rendezvous.name" var="rendezvousName" />
	<display:column property="name" title="${rendezvousName}" />
	
	
	<spring:message code="rendezvous.description" var="rendezvousDescription" />
	<display:column property="description" title="${rendezvousDescription}" />

	<spring:message code="moment.format" var="momentFormat" />
	<spring:message code="rendezvous.moment" var="rendezvousMoment" />
	<display:column property="moment" title="${rendezvousMoment}" format="${momentFormat}" />
	
	<spring:message code="rendezvous.picture" var="picture" />
	<display:column title="${picture}">
		<IMG src="${row.picture}" class="tableImg"/>
	</display:column>
	
		<spring:message code="rendezvous.location.longitude" var="rendezvousLongitude" />
	<display:column property="location.longitude" title="${rendezvousLongitude}" />
	<spring:message code="rendezvous.location.latitude" var="rendezvousLongitude" />
	<display:column property="location.latitude" title="${rendezvousLongitude}" />
	
	<spring:message code="rendezvous.user" var="rendezvousUser" />
	<display:column>
		<div>
			<a href="user/display.do?userId=${row.user.id}"> 
				<spring:message code="rendezvous.user" />
			</a>
		</div>
	</display:column>
	
	<spring:message code="rendezvous.attendants" var="rendezvousAttendants" />
	<display:column>
		<div>
			<a href="user/listAttendants.do?rendezvousId=${row.id}"> 
				<spring:message code="rendezvous.attendants" />
			</a>
		</div>
	</display:column>
	
	<spring:message code="rendezvous.announcements" var="rendezvousAnnouncements" />
	<display:column>
		<div>
			<a href="announcement/list.do?rendezvousId=${row.id}"> 
				<spring:message code="rendezvous.announcements" />
			</a>
		</div>
	</display:column>
	
	<spring:message code="rendezvous.similar" var="rendezvousSimilar" />
	<display:column>
		<div>
			<a href="rendezvous/listSimilar.do?rendezvousId=${row.id}"> 
				<spring:message code="rendezvous.similar" />
			</a>
		</div>
	</display:column>
	
	<security:authorize access="hasRole('USER')">
	<display:column>
		<div>
			<a href="comment/user/create.do?rendezvousId=${row.id}"> 
				<spring:message code="rendezvous.comment.write" />
			</a>
		</div>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('USER')">
	<display:column>
		<div>
			<a href="comment/user/list.do?rendezvousId=${row.id}"> 
				<spring:message code="rendezvous.comments" />
			</a>
		</div>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('USER')">
		<display:column>
			<jstl:choose>
				<jstl:when test="${reserved.contains(row) and user ne row.user}">
					<div>
						<a href="rendezvous/user/cancel.do?rendezvousId=${row.id}"> 
							<spring:message code="rendezvous.cancel" />
						</a>
					</div>
				</jstl:when>
				<jstl:when test="${!reserved.contains(row) and user ne row.user}">
					<div>
						<a href="reservation/user/reserve.do?rendezvousId=${row.id}"> 
							<spring:message code="rendezvous.reserve" />
						</a>
					</div>
				</jstl:when>
			</jstl:choose>
		</display:column>
	
		<display:column>
			<jstl:if test="${user == row.user and row.deleted==false and row.draft==true}">
			<div>
				<a href="rendezvous/user/edit.do?rendezvousId=${row.id}"> 
					<spring:message code="rendezvous.edit" />
				</a>
			</div>
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${user == row.user}">
			<div>
				<a href="announcement/user/create.do?rendezvousId=${row.id}"> 
					<spring:message code="rendezvous.announcement.create" />
				</a>
			</div>
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${user == row.user}">
			<div>
				<a href="link/user/create.do?rendezvousId=${row.id}"> 
					<spring:message code="rendezvous.link.create" />
				</a>
			</div>
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${(reservedRendezvous && !row.draft) || user == row.user}">
			<div>
				<a href="question/user/list.do?rendezvousId=${row.id}"> 
					<spring:message code="rendezvous.questions" />
				</a>
			</div>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<spring:message code ="rendezvous.deleted" var="deleted"/>
	<display:column title="${deleted}">
		<jstl:if test="${row.deleted == true }">
			<jstl:out value="${deleted }"/>
		</jstl:if>
	</display:column>

	<spring:message code ="rendezvous.passed" var="passed"/>
	<display:column title="${passed}">
		<jstl:if test="${row.moment < date}">
			<jstl:out value="${passed}"/>
		</jstl:if>
	</display:column>
	
	<spring:message code ="rendezvous.canceled" var="cancelado"/>
	<display:column>
		<jstl:if test="${canceled.contains(row)}">
		<div>
			<jstl:out value="${cancelado}"/>
		</div>
		</jstl:if>
	</display:column>
	
	
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<display:column>
		<div>
			<a href="rendezvous/administrator/delete.do?rendezvousId=${row.id}"> 
				<spring:message code="rendezvous.delete" />
			</a>
		</div>
	</display:column>
	</security:authorize>
	
	
	<display:column>
		<jstl:if test="${user == row.user && row.draft}">
			<div>
				<a href="question/user/create.do?rendezvousId=${row.id}"> 
					<spring:message code="rendezvous.question.add" />
				</a>
			</div>
		</jstl:if>
	</display:column>

</display:table>


<input type="button" name="back"
	value='<spring:message code="rendezvous.back"/>'
	onclick="javascript: relativeRedir('/');" />
	<br />
	
