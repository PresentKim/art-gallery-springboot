<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>관리자 :: 갤러리 관리</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/admin.css"/>">
        <script src="<c:url value="/static/script/admin.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<%@ include file="/WEB-INF/views/admin/sub_menu.jsp" %>
<section class="admin-list">
    <form name="adminForm" method="get" action="<c:url value="/admin/gallery"/>">
        <div class="admin-list-btn">
            <!-- 기능 버튼 -->
            <div class="admin-list-func-btn">
                <input type="button" value="삭제" onclick="deleteSelected('/api/galleries/')">
            </div>

            <!-- 검색 기능 -->
            <div class="admin-list-search">
                <label>
                    <input type="text" placeholder="ID 또는 이름을 입력하세요" name="keyword" value="${filter.keyword}">
                </label>
                <input type="submit" value="검색"/>">
            </div>
        </div>
        <ul class="admin-list-header admin-artwork-list">
            <li>
                <label><input type="checkbox" onclick="checkAll()" class="select-all-box"></label>
            </li>
            <li>번호</li>
            <li>ID</li>
            <li>이름</li>
            <li>제목</li>
            <li>내용</li>
            <li>등록일</li>
            <li>조회수</li>
            <li>미리보기</li>
        </ul>
        <c:forEach items="${galleryList}" var="galleryDto" varStatus="status">
            <ul
                    class="admin-list-main admin-artwork-list"
                    onclick="checkChildCheckbox(this)"
                    data-seq="${galleryDto.gseq}"
            >
                <li>
                    <label><input type="checkbox"></label>
                </li>
                <li>${galleryDto.gseq}</li>
                <li>${galleryDto.authorId}</li>
                <li>${galleryDto.authorName}</li>
                <li class="view-link"><a href="<c:url value="/gallery/${galleryDto.gseq}"/>">${galleryDto.title}</a>
                </li>
                <li>${galleryDto.content}</li>
                <li>${galleryDto.writedate}</li>
                <li>${galleryDto.readcount}</li>
                <li>
                    <img alt="artwork-img" src="${galleryDto.imageSrc}" onclick="previewImage(this)">
                </li>
            </ul>
        </c:forEach>
    </form>
    <t:pagination/>
</section>

    </jsp:attribute>
</t:layout>
