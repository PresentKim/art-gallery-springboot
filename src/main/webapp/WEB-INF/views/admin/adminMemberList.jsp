<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>관리자 :: 회원 관리</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/admin.css"/>">
        <script src="<c:url value="/static/script/admin.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<%@ include file="/WEB-INF/views/admin/sub_menu.jsp" %>
<section class="admin-list">
    <form name="adminForm" method="post" onsubmit="ajaxSubmit(event)">
        <div class="admin-list-btn">
            <!-- 검색 기능을 위해 최상단에 보이지 않는 submit 버튼을 추가 -->
            <input type="submit" style="display: none;" formmethod="get" formaction="<c:url value="/admin/member"/>">

            <!-- 기능 버튼 -->
            <input type="submit" value="관리자 권한 부여" formaction="<c:url value="/admin/member/grant"/>">
            <input type="submit" value="관리자 권한 해제" formaction="<c:url value="/admin/member/revoke"/>">
            <input type="submit" value="회원 삭제" formaction="<c:url value="/admin/member/delete"/>">

            <!-- 검색 기능 -->
            <label><input type="text" placeholder="검색어를 입력하세요" name="search" value="${search}"></label>
            <input type="submit" value="검색" formmethod="get" formaction="<c:url value="/admin/member"/>">
        </div>
        <ul class="admin-list-header admin-member-list">
            <li>
                <label><input type="checkbox" onclick="checkAll()" class="select-all-box"></label>
            </li>
            <li>아이디</li>
            <li>이름</li>
            <li>이메일</li>
            <li>가입일</li>
            <li>휴대번호</li>
        </ul>
        <c:forEach items="${memberList}" var="memberDto">
            <ul class="admin-list-main admin-member-list" onclick="go_check(event)">
                <li>
                    <label><input name="memberIds" type="checkbox" value="${memberDto.id}" class="check-box"></label>
                </li>
                <li>
                    <span>${memberDto.id}</span>
                    <c:if test="${memberDto.admin}">
                        <span style="color: red;">[admin]</span>
                    </c:if>
                </li>
                <li>${memberDto.name}</li>
                <li>${memberDto.email}</li>
                <li>${memberDto.indate}</li>
                <li>${memberDto.phone}</li>
            </ul>
        </c:forEach>
    </form>
</section>
<t:pagination/>

    </jsp:attribute>
</t:layout>