<%--@elvariable id="loginMember" type="com.team4.artgallery.entity.MemberEntity"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.team4.artgallery.dto.enums.ArtworkCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>예술품 목록 :: ${filter.category} ${pagination.page}페이지</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/artwork/artwork_list.css"/>">
    </jsp:attribute>

    <jsp:attribute name="content">

<section class="artwork-list-header">
    <form action="<c:url value="/artwork"/>" method="get" name="searchForm" class="artwork-search-form">
        <h1>예술품 검색</h1>
        <div>
            <input type="hidden" name="category" value="${filter.category}">
            <label>
                <input type="text" placeholder="작품명 또는 작가명을 검색하세요" name="keyword" class="artwork-keyword-form_input"
                       value="${filter.keyword}">
            </label>
            <input type="submit" value="검색" class="artwork-search-form_btn">
        </div>
    </form>
    <div class="category-btn-container">
        <c:forEach items="${ArtworkCategory.values()}" var="c">
            <%--@elvariable id="c" type="com.team4.artgallery.dto.enums.ArtworkCategory"--%>
            <a href="<c:url value="/artwork?category=${c.value}&keyword=${filter.keyword}"/>"
               class="artwork-list_btn <c:if test="${c.isEquals(filter.category)}">artwork-list_selected-btn</c:if>">
                    ${c.korName}
            </a>
        </c:forEach>
    </div>
</section>
<main class="artwork-list-main">
    <div class="artwork-list-head">
        <h4>검색결과가 총 ${pagination.itemCount}건 입니다</h4>
        <c:if test="${loginMember.admin}">
            <a href="<c:url value="/artwork/write"/>" class="artwork-list_btn">예술품 등록</a>
        </c:if>
    </div>
    <div class="artwork-list">
        <c:forEach items="${artworkList}" var="artwork">
            <div onclick="location.href='/artwork/${artwork.aseq}'">
                <img src="${artwork.imageSrc}" alt="artwork_image" class="artwork-list_img"/>
                <div class="artwork-list_info">
                    <span>${artwork.artist}</span>
                    <p>${artwork.name}</p>
                    <span>${artwork.year}</span>
                </div>
            </div>
        </c:forEach>
    </div>
    <t:pagination/>
</main>

    </jsp:attribute>
</t:layout>