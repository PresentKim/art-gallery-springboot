<%--@elvariable id="loginMember" type="com.team4.artgallery.entity.MemberEntity"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
    <jsp:attribute name="head">
        <title>갤러리 목록</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/gallery.css"/>">
    </jsp:attribute>

    <jsp:attribute name="content">

<main class="gallery-list">
    <section class="gallery-list-header">
        <form action="<c:url value="/gallery"/>" method="get" name="searchForm">
            <h1><label for="keyword">갤러리 검색</label></h1>
            <div>
                <input type="text" placeholder="제목 또는 내용을 검색하세요" name="keyword" id="keyword" value="${filter.keyword}">
                <input type="submit" value="검색" class="gallery-search-form_btn">
            </div>
        </form>
    </section>
    <section class="gallery-list-main">
        <div class="gallery-list-main-title">
            <h4>검색결과가 총 ${pagination.itemCount}건 입니다</h4>
            <c:if test="${not empty loginMember}">
                <a href="<c:url value="/gallery/write"/>" class="gallery-btn">갤러리 등록</a>
            </c:if>
        </div>
        <div class="gallery-list-main-content">
            <c:forEach items="${galleryList}" var="galleryEntity">
                <%--@elvariable id="galleryEntity" type="com.team4.artgallery.entity.GalleryEntity"--%>
                <a href="<c:url value="/gallery/${galleryEntity.gseq}"/>">
                    <img src="<c:url value="${galleryEntity.imageSrc}"/>"
                         alt="gallery_image"/>
                    <div class="gallery-list-main-content_info">
                        <h1 class="glmc_info-title">${galleryEntity.title}</h1>
                        <p class="glmc_info-name">${galleryEntity.author.name}님의갤러리</p>
                        <span>조회수 : ${galleryEntity.readCount}</span>
                    </div>
                </a>
            </c:forEach>
        </div>
        <t:pagination/>
    </section>
</main>

    </jsp:attribute>
</t:layout>