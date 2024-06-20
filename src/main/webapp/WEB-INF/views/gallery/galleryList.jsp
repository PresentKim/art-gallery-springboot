<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:header>
    <title>갤러리 목록</title>
    <link rel="stylesheet" href="<c:url value="/static/stylesheet/gallery.css"/>">
    <script src="<c:url value="/static/script/gallery.js"/>"></script>
</t:header>
<main class="gallery-list">
    <section class="gallery-list-header">
        <form action="museum.do?command=galleryList" method="post" name="searchForm">
            <h1>갤러리 검색</h1>
            <div>
                <input type="text" placeholder="제목 또는 내용을 검색하세요" name="searchWord" value="${searchWord}">
                <input type="submit" value="검색" onclick="return go_search()" class="artwork-search-form_btn">
            </div>
        </form>
    </section>
    <section class="gallery-list-main">
        <div class="gallery-list-main-title">
            <h4>검색결과가 총 ${pagination.itemCount}건 입니다</h4>
            <a href="museum.do?command=galleryWrite" class="gallery-btn">갤러리 등록</a>
        </div>
        <div class="gallery-list-main-content">
            <c:forEach items="${galleryList}" var="galleryDto">
                <div onclick="location.href='museum.do?command=galleryView&mseq=${galleryDto.mseq}'">
                    <img src="static/image/gallery/${galleryDto.savefilename}" alt="member_gallery_image"/>
                    <div class="gallery-list-main-content_info">
                        <h1 class="glmc_info-title">${galleryDto.title}</h1>
                        <p class="glmc_info-name">${galleryDto.authorName}님의갤러리</p>
                        <span>조회수 : ${galleryDto.readcount}</span>
                    </div>
                </div>
            </c:forEach>
        </div>
        <t:pagination/>
    </section>
</main>
<t:footer/>
