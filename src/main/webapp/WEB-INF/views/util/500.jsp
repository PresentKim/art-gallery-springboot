<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="random" class="java.util.Random" scope="application"/>
<jsp:include page="/WEB-INF/views/header.jsp">
    <jsp:param name="stylesheet" value="/static/stylesheet/error.css"/>
</jsp:include>
<main class="error bg-${random.nextInt(7)}">
    <h1>오류가 발생했습니다</h1>
    <pre>${errorMessage}</pre>
</main>
<jsp:include page="/WEB-INF/views/footer.jsp"/>