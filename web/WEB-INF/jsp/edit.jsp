<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" placeholder="${type.title}" name="${type.name()}" size=30
                           value="${resume.getContact(type) }"></dd>
            </dl>
        </c:forEach>

        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${type eq 'OBJECTIVE' || type eq 'PERSONAL'}">
                        <h4>${type.title}</h4>
                        <textarea name="${type}" rows="5" cols="55">${resume.sections.get(type)}</textarea>
                </c:when>
                <c:when test="${type eq 'ACHIEVEMENT' || type eq 'QUALIFICATION'}">
                    <h4>${type.title}</h4>

                    <c:if test="${resume.getSection(type) != null}">
                        <textarea name=${type} rows="5" cols="55">${resume.getSection(type)}</textarea>
                    </c:if>
                    <c:if test="${resume.getSection(type) == null}">
                        <textarea name=${type} rows="5" cols="55"></textarea>
                    </c:if>

                </c:when>
            </c:choose>
        </c:forEach>

        <div style="margin-top: 20px">
            <button type="submit">Сохранить</button>
            <button type="reset" onclick="window.history.back()">Отменить</button>
        </div>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>