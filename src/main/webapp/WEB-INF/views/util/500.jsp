<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="random" class="java.util.Random" scope="application"/>

<t:layout>
    <jsp:attribute name="head">
        <title>오류가 발생했습니다</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/error.css"/>">
    </jsp:attribute>

    <jsp:attribute name="content">
        <h1>오류가 발생했습니다</h1>
        <pre>${errorMessage}</pre>
    </jsp:attribute>
</t:layout>
