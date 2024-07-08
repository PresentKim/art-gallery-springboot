<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="head" required="false" fragment="true" description="<head> 태그에 삽입될 내용" %>
<%@ attribute name="content" required="false" fragment="true" description="<div class=content-wrap> 태그에 삽입될 내용" %>

<!DOCTYPE html>
<html>
<head>
    <!-- CSS -->
    <link rel="stylesheet" href="<c:url value="/static/stylesheet/index.css"/>">

    <!-- JavaScript -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="<c:url value="/static/script/ajax.js"/>"></script>
    <script src="https://unpkg.com/aos@2.2/dist/aos.js"></script>
    <script src="https://cdn.jsdelivr.net/jquery.drawsvg/1/jquery.drawsvg.min.js"></script>
    <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.min.js"></script>
    <script src="https://cdn.jsdelivr.net/vivus/0.2.1/vivus.min.js"></script>
    <script src="<c:url value="/static/script/header.js"/>"></script>

    <c:if test="${not empty head and not empty head.toString().trim()}">
        <!-- Custom head -->
        <jsp:invoke fragment="head"/>
    </c:if>
    <c:if test="${empty head || head.toString().contains('<title>')}">
        <!-- Default Title -->
        <title>하이 미술관</title>
    </c:if>

    <!-- Meta -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<t:header/>
<div class="content-wrap">
    <jsp:invoke fragment="content"/>
</div>
<t:footer/>
</body>
</html>
