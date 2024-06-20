<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:header>
    <title>관리자 :: 메인</title>
    <link rel="stylesheet" href="<c:url value="/static/stylesheet/admin.css"/>">
</t:header>
<ul class="admin-main-menu">
    <li><a href="museum.do?command=adminMemberList">회원 목록</a></li>
    <li><a href="museum.do?command=adminArtworkList">예술품 목록</a></li>
    <li><a href="museum.do?command=adminNoticeList">소식지 목록</a></li>
    <li><a href="museum.do?command=adminGalleryList">이용자 갤러리 목록</a></li>
    <li><a href="museum.do?command=adminQnaList">문의사항 목록</a></li>
    <li><a href="#" onclick="ajax('museum.do?command=adminDbReset')">데이터베이스 초기화</a></li>
</ul>
<t:footer/>
