<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.team4.artgallery.enums.ArtworkCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:header>
    <title>예술품 등록</title>
    <link rel="stylesheet" href="<c:url value="/static/stylesheet/artwork/artwork_form.css"/>">
    <script src="<c:url value="/static/script/artwork/artwork_form.js"/>"></script>
</t:header>
<h2 class="artwork-form-header">예술품 등록</h2>
<section class="artwork-form-main">
    <form class="artwork-form" onsubmit="ajaxSubmit(event)"
          method="post" action="<c:url value="/artwork/write"/>" enctype="multipart/form-data">
        <div class="artwork-form_info">
            <ul>
                <li>
                    <label for="artist">작가명</label>
                    <input type="text" name="artist" id="artist" oninput="uncheck('unknown-artist')">
                    <input type="checkbox" name="unknown-artist" id="unknown-artist" onchange="onUnknownArtistChange()">
                    <label for="unknown-artist" class="unknown-label">작자미상</label>
                </li>
                <li>
                    <label for="name">작품명</label>
                    <input type="text" name="name" id="name">
                </li>
                <li>
                    <label for="year">제작연도</label>
                    <input type="text" name="year" id="year" maxlength="4" oninput="uncheck('unknown-year')">
                    <input type="checkbox" name="unknown-year" id="unknown-year" onchange="onUnknownYearChange()">
                    <label for="unknown-year" class="unknown-label">연도미상</label>
                </li>
                <li>
                    <label for="material">재료</label>
                    <input type="text" name="material" id="material">
                </li>
                <li>
                    <label for="size">규격</label>
                    <input type="text" name="size" id="size">
                </li>
                <li>
                    <label for="category">부문</label>
                    <select name="category" id="category">
                        <option value="">카테고리를 선택하세요</option>
                        <c:forEach items="${ArtworkCategory.validValues()}" var="c">
                            <option value="${c.name()}">${c.name()}</option>
                        </c:forEach>
                    </select>
                </li>
                <li>
                    <div>전시상태</div>
                    <input type="radio" name="displayyn" value="Y" id="displayOn" checked>
                    <label for="displayOn">공개</label>
                    <input type="radio" name="displayyn" value="N" id="displayOff">
                    <label for="displayOff">비공개</label>
                </li>
                <li>
                    <label for="imageFile">이미지 등록</label>
                    <input type="file" name="imageFile" id="imageFile" accept="image/*" onchange="updatePreviewImage()">
                </li>
                <li>
                    <img id="image-preview" alt="image-preview" src="">
                </li>
            </ul>
            <div>
                <label for="content">작품설명</label>
                <textarea name="content" id="content"></textarea>
            </div>
        </div>
        <div class="artwork-form-btn">
            <input type="submit" value="등록">
            <input type="button" value="취소" onclick="history.back()">
        </div>
    </form>
</section>
<t:footer/>
