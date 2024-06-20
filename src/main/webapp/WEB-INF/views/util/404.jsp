<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="random" class="java.util.Random" scope="application"/>
<t:header>
    <title>요청하신 페이지를 찾을 수 없습니다</title>
    <link rel="stylesheet" href="<c:url value="/static/stylesheet/error.css"/>">
</t:header>
<main class="error bg-${random.nextInt(7)}">
    <h1>요청하신 페이지를 찾을 수 없습니다</h1>
</main>
<t:footer/>
