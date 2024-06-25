<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.team4.artgallery.enums.ArtworkCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>관리자 :: 예술품 관리</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/admin.css"/>">
        <script src="<c:url value="/static/script/admin.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<%@ include file="/WEB-INF/views/admin/sub_menu.jsp" %>
<section class="admin-list">
    <form name="adminForm" method="get" action="<c:url value="/admin/artwork"/>" onsubmit="ajaxSubmit(event)">
        <div class="admin-list-btn">
            <!-- 검색 기능을 위해 최상단에 보이지 않는 submit 버튼을 추가 -->
            <input class="fake-submit" type="submit" formmethod="get" formaction="<c:url value="/admin/artwork"/>">

            <!-- 기능 버튼 -->
            <div class="admin-list-func-btn">
                <input type="submit" value="등록" formmethod="get" formaction="<c:url value="/artwork/write"/>">
                <input type="submit" value="수정" formmethod="post" formaction="<c:url value="/admin/artwork/update"/>">
                <input type="submit" value="삭제" formmethod="post" formaction="<c:url value="/admin/artwork/delete"/>">
            </div>

            <!-- 검색 기능 -->
            <div class="admin-list-search">
                <label><input type="text" placeholder="작품명 또는 작가명을 입력하세요" name="search"
                              value="${filter.search}"></label>
                <input type="submit" value="검색" formmethod="get" formaction="<c:url value="/admin/artwork"/>">
            </div>
        </div>
        <ul class="admin-list-header admin-member-list">
            <li>
                <label><input type="checkbox" onclick="checkAll()" class="select-all-box"></label>
            </li>
            <li>
                <label for="displayyn"></label>
                <select name="displayyn" id="displayyn" class="admin-select" onchange="this.form.submit();">
                    <option value="">전체</option>
                    <option value="Y" <c:if test="${'Y'.equals(filter.displayyn)}">selected</c:if>>공개</option>
                    <option value="N" <c:if test="${'N'.equals(filter.displayyn)}">selected</c:if>>비공개</option>
                </select>
            </li>
            <li>
                <label for="category"></label>
                <select name="category" id="category" class="admin-select" onchange="this.form.submit();">
                    <option value="">전체</option>
                    <c:forEach items="${ArtworkCategory.validValues()}" var="c">
                        <option value="${c.name()}"
                                <c:if test="${c.name().equals(filter.category)}">selected</c:if>>${c.name()}</option>
                    </c:forEach>
                </select>
            </li>
            <li>작품명</li>
            <li>작가명</li>
            <li>제작연도</li>
            <li>재료</li>
            <li>규격</li>
            <li>등록일</li>
            <li>미리보기</li>
        </ul>
        <c:forEach items="${artworkList}" var="artworkDto" varStatus="status">
            <c:set var="previewId" value="preview-${artworkDto.aseq}-${status.index}"/>
            <ul class="admin-list-main admin-artwork-list" onclick="go_check(event)">
                <li>
                    <label><input name="aseqs" type="checkbox" value="${artworkDto.aseq}" class="check-box"></label>
                </li>
                <li>${artworkDto.displayyn}</li>
                <li>${artworkDto.aseq}</li>
                <li>${artworkDto.category}</li>
                <li class="view-link"><a href="<c:url value="/artwork/${artworkDto.aseq}"/>">${artworkDto.name}</a></li>
                <li>${artworkDto.artist}</li>
                <li>${artworkDto.year}</li>
                <li>${artworkDto.material}</li>
                <li>${artworkDto.size}</li>
                <li>${artworkDto.indate}</li>
                <li>
                    <img alt="artwork-img" src="${artworkDto.fullSavefilename}"
                         onmouseover="previewImg('${previewId}')"
                         onmouseleave="previewImg('${previewId}')">
                </li>
            </ul>
            <div id="${previewId}" class="preview hidden">
                <img alt="artwork-img" src="${artworkDto.fullSavefilename}">
            </div>
        </c:forEach>
    </form>
    <t:pagination/>
</section>

    </jsp:attribute>
</t:layout>
