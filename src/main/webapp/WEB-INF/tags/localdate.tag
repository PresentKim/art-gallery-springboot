<%@ tag pageEncoding="UTF-8" body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag import="java.time.format.DateTimeFormatter" %>
<%@ attribute name="value" required="true" type="java.time.LocalDateTime" %>
<%@ attribute name="pattern" type="java.lang.String" %>

<c:if test="${empty pattern}">
    <c:set var="pattern" value="yyyy-MM-dd"/>
</c:if>

<c:out value="${DateTimeFormatter.ofPattern(pattern).format(value)}"/>
