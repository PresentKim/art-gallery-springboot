<%--@elvariable id="galleryEntity" type="com.team4.artgallery.entity.GalleryEntity"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>갤러리 ${empty galleryEntity ? '등록' : '수정 :: '}${galleryEntity.gseq}</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/gallery_write.css"/>">
        <script src="<c:url value="/static/script/gallery/gallery_write.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<h2 class="gallery-form-header">${empty galleryEntity ? '갤러리 등록' : '갤러리 수정'}</h2>
<section class="gallery-form-main">
    <form id="gallery-form" class="gallery-form" method="post" data-gseq="${galleryEntity.gseq}">
        <div class="gallery-form_info">
            <ul class="gallery-form-text">
                <li>
                    <label for="title">작품명</label>
                    <input type="text" name="title" id="title" value="${galleryEntity.title}">
                </li>
                <li>
                    <label for="content">작품 설명</label>
                    <textarea name="content" id="content">${galleryEntity.content}</textarea>
                </li>
            </ul>
            <ul class="gallery-form-img">
                <li>
                    <label for="image-file">이미지 등록</label>
                    <input type="file" name="imageFile" id="image-file" accept="image/*">
                </li>
                <li>
                    <img id="image-preview" alt="image-preview" src="${galleryEntity.imageSrc}">
                </li>
            </ul>
        </div>
        <div class="gallery-form_btn">
            <input type="submit" value="${empty galleryEntity ? '등록' : '수정'}">
            <input type="button" value="취소" onclick="history.back()">
        </div>
    </form>
</section>

    </jsp:attribute>
</t:layout>
