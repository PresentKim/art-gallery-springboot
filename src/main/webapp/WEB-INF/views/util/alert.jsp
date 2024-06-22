<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="url" type="java.lang.String"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
    <c:if test="${not empty message}">
    alert('${fn:replace(message, "'", "\\'")}');
    </c:if>

    <c:choose>
    <c:when test="${not empty url}">
    location.href = '${url}';
    </c:when>
    <c:otherwise>
    history.back();
    </c:otherwise>
    </c:choose>
</script>