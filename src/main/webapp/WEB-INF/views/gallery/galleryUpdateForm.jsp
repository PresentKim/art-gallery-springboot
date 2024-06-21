<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>갤러리 수정 :: ${galleryDto.mseq}</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/galleryForm.css"/>">
        <script src="<c:url value="/static/script/gallery.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<h1 class="gallery-form-header">작품 수정</h1>
<section class="gallery-form-main">
    <form action="museum.do?command=galleryUpdateForm" method="post" name="galleryForm" class="gallery-form"
          enctype="multipart/form-data">
        <div class="gallery-form_info">
            <ul class="gallery-form-text">
                <li>
                    <label>작품명</label>
                    <input type="text" placeholder="작품명을 입력하세요" name="title" value="${galleryDto.title}">
                </li>
                <li>
                    <label>작품 설명</label>
                    <textarea name="content">${galleryDto.content}</textarea>
                </li>
            </ul>
            <ul class="gallery-form-img">
                <li>
                    <div>이미지 등록</div>
                    <input type="file" name="image" accept="image/*" onchange="previewImage()">
                </li>
                <li>
                    <img alt="image" src="static/image/gallery/${galleryDto.savefilename}" name="uploadedImage">
                </li>
            </ul>
        </div>
        <div class="gallery-form_btn">
            <input class="btn" type="button" value="수정 등록" onclick="go_update()">
            <input type="hidden" name="authorid" value="${galleryDto.authorId}">
            <input type="hidden" name="mseq" value="${galleryDto.mseq}">
            <input class="btn" type="button" value="목록으로"
                   onclick="location.href='museum.do?command=galleryView&mseq=${galleryDto.mseq}'">
        </div>
    </form>
</section>

    </jsp:attribute>
</t:layout>
