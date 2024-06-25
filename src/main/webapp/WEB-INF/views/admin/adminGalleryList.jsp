<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <form name="adminForm" method="get" action="<c:url value="/admin/gallery"/>" onsubmit="ajaxSubmit(event)">
        <div class="admin-list-btn">
            <!-- 검색 기능을 위해 최상단에 보이지 않는 submit 버튼을 추가 -->
            <input class="fake-submit" type="submit" formmethod="get" formaction="<c:url value="/admin/gallery"/>">

            <!-- 기능 버튼 -->
            <div class="admin-list-func-btn">
                <input type="submit" value="삭제" formmethod="post" formaction="<c:url value="/admin/gallery/delete"/>">
            </div>

            <!-- 검색 기능 -->
            <div class="admin-list-search">
                <label><input type="text" placeholder="ID 또는 이름을 입력하세요" name="search" value="${search}"></label>
                <input type="submit" value="검색" formmethod="get" formaction="<c:url value="/admin/gallery"/>">
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
            <c:set var="previewId" value="preview-${galleryDto.gseq}-${status.index}"/>
            <ul class="admin-list-main admin-artwork-list" onclick="go_check(event)">
                <li>
                    <label><input name="gseqs" type="checkbox" value="${galleryDto.gseq}" class="check-box"></label>
                </li>
                <li>${galleryDto.gseq}</li>
                <li>${galleryDto.authorId}</li>
                <li>${galleryDto.authorName}</li>
                <li class="view-link" onclick="location.href='museum.do?command=galleryView&gseq=${galleryDto.gseq}'">
                        ${galleryDto.title}
                </li>
                <li>${galleryDto.content}</li>
                <li>${galleryDto.writedate}</li>
                <li>${galleryDto.readcount}</li>
                <li>
                    <img alt="artwork-img" src="/static/image/gallery/${galleryDto.savefilename}"
                         onmouseover="previewImg('${previewId}')"
                         onmouseleave="previewImg('${previewId}')">
                </li>
            </ul>
            <div id="${previewId}" class="preview hidden">
                <img alt="artwork-img" src="/static/image/gallery/${galleryDto.savefilename}">
            </div>
        </c:forEach>
    </form>
    <t:pagination/>
</section>

    </jsp:attribute>
</t:layout>
