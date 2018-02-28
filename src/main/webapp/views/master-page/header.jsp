<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>


<div>
	<img src="https://static1.squarespace.com/static/57f9bc25b8a79bc648c871ef/t/59cc9d5cedaed87e75ecdc12/1518476813543/?format=1000w" alt="Acme-Rendezvous Co., Inc." style="height:6em; width:15em"/>
</div>
<!-- EXPLORER RANGER MANAGER ADMINISTRATOR AUDITOR SPONSOR + "$") -->
<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<security:authorize access="isAnonymous()">

			<li><a href="user/list.do"><spring:message
						code="master.page.user.list" /></a></li>

			<li><a href="rendezvous/list.do"><spring:message
						code="master.page.rendezvouses" /></a></li>

			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>

			<li><a href="user/create.do"><spring:message
						code="master.page.register" /></a></li>

		</security:authorize>

		<security:authorize access="hasRole('USER')">

			<li><a href="user/list.do"><spring:message
						code="master.page.user.list" /></a></li>

			<li><a class="fNiv"><spring:message
						code="master.page.rendezvouses" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="rendezvous/user/listAll.do"><spring:message
								code="master.page.rendezvouses" /></a></li>
					<li><a href="rendezvous/user/list.do"><spring:message
								code="master.page.myRendezvouses" /></a></li>
					<li><a href="rendezvous/user/listReserved.do"><spring:message
								code="master.page.myReservedRendezvouses" /></a></li>
					<li><a href="rendezvous/user/create.do"><spring:message
								code="master.page.newrendezvous" /></a></li>
				</ul></li>

		</security:authorize>

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li><a href="rendezvous/list.do"><spring:message
						code="master.page.rendezvouses" /> </a></li>

			<li><a href="comment/administrator/list.do"><spring:message
						code="master.page.comments" /> </a></li>

			<li><a href="dashboard/list.do"><spring:message
						code="master.page.dashboard" /> </a></li>

		</security:authorize>


		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv">
				 <security:authentication
						property="principal.username" />
					</a>
				<ul>
					<li class="arrow"></li>
					
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
			
		</security:authorize>		
		
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
