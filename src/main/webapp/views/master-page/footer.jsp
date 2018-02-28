<%--
 * footer.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="date" class="java.util.Date" />

<hr />

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme, Inc.</b>
<a href="term/termsAndConditions.do"><spring:message code="term.terms"/></a>
<a href="term/cookies.do"><spring:message code="term.cookie"/></a>

<div id="overbox3">
	<div id="infobox3">
		<p>
			<spring:message code="term.cookie.banner"/>
			<a href="term/cookies.do"><spring:message code="term.cookie.banner.moreInformation"/></a> 
			<a onclick="aceptar_cookies();" style="cursor: pointer;">X</a>
		</p>
	</div>
</div>