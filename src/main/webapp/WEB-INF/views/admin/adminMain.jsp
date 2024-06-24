<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>관리자 :: 메인</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/admin.css"/>">
    </jsp:attribute>

    <jsp:attribute name="content">

<ul class="admin-main-menu">
    <li><a href="<c:url value="/admin/member"/>">회원 목록</a></li>
    <li><a href="<c:url value="/admin/artwork"/>">예술품 목록</a></li>
    <li><a href="<c:url value="/admin/notice"/>">소식지 목록</a></li>
    <li><a href="<c:url value="/admin/gallery"/>">이용자 갤러리 목록</a></li>
    <li><a href="<c:url value="/admin/qna"/>">문의사항 목록</a></li>
    <li><a href="#" onclick="ajax('admin/resetDB')">데이터베이스 초기화</a></li>
</ul>

    </jsp:attribute>
</t:layout>
