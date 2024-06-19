<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/views/header.jsp">
    <jsp:param name="stylesheet" value="/static/stylesheet/galleryForm.css"/>
    <jsp:param name="script" value="/static/script/gallery.js"/>
</jsp:include>
<h1 class="gallery-form-header">작품 등록</h1>
<section class="gallery-form-main">
    <form action="museum.do?command=galleryWriteForm" method="post" name="galleryForm" class="gallery-form"
          enctype="multipart/form-data">
        <div class="gallery-form_info">
            <ul class="gallery-form-text">
                <li>
                    <label>작품명</label>
                    <input type="text" placeholder="작품명을 입력하세요" name="title">
                </li>
                <li>
                    <label>작품 설명</label>
                    <textarea name="content"></textarea>
                </li>
            </ul>
            <ul class="gallery-form-img">
                <li>
                    <div>이미지 등록</div>
                    <input type="file" name="image" accept="image/*" onchange="previewImage()">
                </li>
                <li>
                    <img alt="image" src="" name="uploadedImage">
                </li>
            </ul>
        </div>
        <div class="gallery-form_btn">
            <input class="btn" type="button" value="갤러리 등록" onclick="go_save()">
            <input type="hidden" name="authorid" value="${memberDto.id}">
            <input class="btn" type="button" value="목록으로" onclick="location.href='museum.do?command=galleryList'">
        </div>
    </form>
</section>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
