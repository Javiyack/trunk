
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<h3><spring:message code="dashboard.rendezvouses.by.user" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${dashboard1[0]}</li>
	<li><spring:message code="dashboard.deviation" />: ${dashboard1[1]}</li>
	</ul>

<h3><spring:message code="dashboard.rendezvouses.ratio.creation" /></h3>

	<ul>
	<li><spring:message code="dashboard.ratio" />: ${dashboard2[0]} %</li>
	</ul>
	
<h3><spring:message code="dashboard.users.per.rendezvous" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${dashboard3[0]}</li>
	<li><spring:message code="dashboard.deviation" />: ${dashboard3[1]}</li>
	</ul>


<h3><spring:message code="dashboard.rendezvouses.rsvp" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${dashboard4[0]}</li>
	<li><spring:message code="dashboard.deviation" />: ${dashboard4[1]}</li>
	</ul>
	

<h3><spring:message code="dashboard.rendezvouses.top10" /></h3>

<display:table class="displaytag" name="dashboard5" id="row">
	
	<spring:message code="rendezvous.name" var="titleName" />
	<display:column title="${titleName}"> <jstl:out value="${row[0].name}"/> </display:column>
	<display:column title="Nº"> <jstl:out value="${row[1]}"/> </display:column>
	 
</display:table >


<h3><spring:message code="dashboard.announcements.ratio" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${dashboard6[0]}</li>
	<li><spring:message code="dashboard.deviation" />: ${dashboard6[1]}</li>
	</ul>



<h3><spring:message code="dashboard.announcements.above.75" /></h3>

<display:table class="displaytag" name="dashboard7" id="row">
	
	<spring:message code="rendezvous.name" var="titleName" />
	<display:column title="${titleName}"> <jstl:out value="${row[0].name}"/> </display:column>
	<display:column title="Nº"> <jstl:out value="${row[1]}"/> </display:column>
	 
</display:table >

<h3><spring:message code="dashboard.rendezvouses.linked" /></h3>

<display:table class="displaytag" name="dashboard8" id="row">
	
	<spring:message code="rendezvous.name" var="titleName" />
	<display:column title="${titleName}"> <jstl:out value="${row[0].name}"/> </display:column>
	<display:column title="Nº"> <jstl:out value="${row[1]}"/> </display:column>
	 
</display:table >


<h3><spring:message code="dashboard.questions.rendezvous" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${dashboard9[0]}</li>
	<li><spring:message code="dashboard.deviation" />: ${dashboard9[1]}</li>
	</ul>

<h3><spring:message code="dashboard.answers.rendezvous" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${dashboard10[0]}</li>
	<li><spring:message code="dashboard.deviation" />: ${dashboard10[1]}</li>
	</ul>

<h3><spring:message code="dashboard.replies.comment" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${dashboard11[0]}</li>
	<li><spring:message code="dashboard.deviation" />: ${dashboard11[1]}</li>
	</ul>
		