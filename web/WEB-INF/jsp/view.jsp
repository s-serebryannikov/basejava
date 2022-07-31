<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <%--    view Contact--%>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
        <%--  view section--%>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>

        <c:choose>
        <c:when test="${(sectionEntry.key eq 'OBJECTIVE') || (sectionEntry.key eq'PERSONAL')}">
        <c:if test="${sectionEntry.value != null}">
    <h2><%=sectionEntry.getKey().getTitle()%><br/></h2>
    <div> ${sectionEntry.value}</div>
    </c:if>


    </c:when>
    <c:when test="${(sectionEntry.key eq 'ACHIEVEMENT') || (sectionEntry.key eq 'QUALIFICATION')}">
        <h2><%=sectionEntry.getKey().getTitle()%><br/></h2>
        <c:forEach var="item" items="${sectionEntry.value.content}">
            <ul>
                <li>
                    <p>${item}</p>
                </li>
            </ul>
        </c:forEach>
    </c:when>
    <c:when test="${(sectionEntry.key eq 'EXPERIENCE') || (sectionEntry.key eq 'EDUCATION')}">
        <c:forEach var="organization" items="${sectionEntry.value.organizations}">
            <c:set var="notUrl" value=""/>
            <c:if test="${organization.homePage.url == notUrl}">
                <h3>${organization.homePage.name}</h3>
            </c:if>
            <c:if test="${organization.homePage.url != notUrl}">
                <h3><a href="${organization.homePage.url}">${organization.homePage.name}</a></h3>
            </c:if>
            <c:forEach var="position" items="${organization.positions}">
                <table cellpadding="2">
                    <tr>
                        <td width="20%" style="vertical-align: top">${position.startDate} - ${position.endDate}
                        </td>
                        <td><b>${position}</b><br>${position.title}
                        </td>
                    </tr>
                </table>
            </c:forEach>
        </c:forEach>
    </c:when>
    <c:otherwise>
        Section not exists
    </c:otherwise>
    </c:choose>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
