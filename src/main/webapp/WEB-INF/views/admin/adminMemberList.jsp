<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>관리자 :: 회원 관리</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/admin.css"/>">
        <script src="<c:url value="/static/script/admin.js"/>"></script>
        <script src="<c:url value="/static/script/admin/admin_member.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<%@ include file="/WEB-INF/views/admin/sub_menu.jsp" %>
<section class="admin-list">
    <form name="adminForm" method="get" action="<c:url value="/admin/member"/>">
        <div class="admin-list-btn">
            <!-- 기능 버튼 -->
            <div class="admin-list-func-btn">
                <input type="button" value="관리자 권한 부여" onclick="grantMembers()">
                <input type="button" value="관리자 권한 해제" onclick="revokeMembers()">
                <input type="button" value="회원 삭제" onclick="deleteSelected('/api/members/')">
            </div>

            <!-- 검색 기능 -->
            <div class="admin-list-search">
                <label><input type="text" placeholder="검색어를 입력하세요" name="keyword" value="${filter.keyword}"></label>
                <input type="submit" value="검색">
            </div>
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
        <c:forEach items="${memberList}" var="memberEntity">
            <%--@elvariable id="memberEntity" type="com.team4.artgallery.entity.MemberEntity"--%>
            <ul
                    class="admin-list-main admin-artwork-list"
                    onclick="checkChildCheckbox(this)"
                    data-seq="${memberEntity.id}"
            >
                <li>
                    <label><input type="checkbox"></label>
                </li>
                <li>
                    <span class="id <c:if test="${memberEntity.admin}"> admin-id</c:if>">
                            ${memberEntity.id}
                    </span>
                </li>
                <li>${memberEntity.name}</li>
                <li>${memberEntity.email}</li>
                <li>${memberEntity.indate}</li>
                <li>${memberEntity.phone}</li>
            </ul>
        </c:forEach>
    </form>
    <t:pagination/>
</section>

    </jsp:attribute>
</t:layout>