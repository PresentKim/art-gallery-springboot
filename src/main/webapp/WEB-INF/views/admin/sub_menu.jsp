<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav class="admin-sub-menu">
    <ul>
        <li><a href="<c:url value="/admin/member"/>">회원 목록</a></li>
        <li><a href="<c:url value="/admin/artwork"/>">예술품 목록</a></li>
        <li><a href="<c:url value="/admin/notice"/>">소식지 목록</a></li>
        <li><a href="<c:url value="/admin/gallery"/>">이용자 갤러리 목록</a></li>
        <li><a href="<c:url value="/admin/qna"/>">문의사항 목록</a></li>
        <li><p onclick="resetDatabase()">데이터베이스 초기화</p></li>
    </ul>
</nav>
