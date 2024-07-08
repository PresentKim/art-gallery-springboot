<%--@elvariable id="artworkDto" type="com.team4.artgallery.dto.ArtworkDto"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.team4.artgallery.dto.enums.ArtworkCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>${empty artworkDto ? '예술품 등록' : '예술품 수정 :: '}${artworkDto.aseq}</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/artwork/artwork_write.css"/>">
        <script src="<c:url value="/static/script/artwork/artwork_write.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<h2 class="artwork-form-header">${empty artworkDto ? '예술품 등록' : '예술품 수정'}</h2>
<section class="artwork-form-main">
    <form class="artwork-form" method="post" action="<c:url value="/artwork/write"/>" enctype="multipart/form-data">
        <c:if test="${not empty artworkDto}">
            <input type="hidden" name="aseq" value="${artworkDto.aseq}">
        </c:if>
        <div class="artwork-form_info">
            <ul>
                <li>
                    <label for="artist">작가명</label>
                    <input type="text" name="artist" id="artist" value="${artworkDto.artist}"
                           oninput="uncheck('unknown-artist')">
                    <input type="checkbox" name="unknown-artist" id="unknown-artist" onchange="onUnknownArtistChange()"
                           <c:if test="${artworkDto.artist.equals('작자미상')}">checked</c:if>>
                    <label for="unknown-artist" class="unknown-label">작자미상</label>
                </li>
                <li>
                    <label for="name">작품명</label>
                    <input type="text" name="name" id="name" value="${artworkDto.name}">
                </li>
                <li>
                    <label for="year">제작연도</label>
                    <input type="text" name="year" id="year" value="${artworkDto.year}" maxlength="4"
                           oninput="uncheck('unknown-year')">
                    <input type="checkbox" name="unknown-year" id="unknown-year" onchange="onUnknownYearChange()"
                           <c:if test="${artworkDto.year.equals('연도미상')}">checked</c:if>>
                    <label for="unknown-year" class="unknown-label">연도미상</label>
                </li>
                <li>
                    <label for="material">재료</label>
                    <input type="text" name="material" id="material" value="${artworkDto.material}">
                </li>
                <li>
                    <label for="size">규격</label>
                    <input type="text" name="size" id="size" value="${artworkDto.size}">
                </li>
                <li>
                    <label for="category">부문</label>
                    <select name="category" id="category">
                        <c:if test="${empty artworkDto}">
                            <option value="" disabled selected>카테고리를 선택하세요</option>
                        </c:if>
                        <c:forEach items="${ArtworkCategory.validValues()}" var="c">
                            <%--@elvariable id="c" type="com.team4.artgallery.dto.enums.ArtworkCategory"--%>
                            <option value="${c.value}"
                                    <c:if test="${c.isEquals(artworkDto.category)}">selected</c:if>>${c.korName}</option>
                        </c:forEach>
                    </select>
                </li>
                <li>
                    <div>전시상태</div>
                    <input type="radio" name="displayyn" value="Y" id="displayOn"
                           <c:if test="${artworkDto.display}">checked</c:if>>
                    <label for="displayOn">공개</label>
                    <input type="radio" name="displayyn" value="N" id="displayOff"
                           <c:if test="${!artworkDto.display}">checked</c:if>>
                    <label for="displayOff">비공개</label>
                </li>
                <li>
                    <label for="imageFile">이미지 등록</label>
                    <input type="file" name="imageFile" id="imageFile" accept="image/*" onchange="updatePreviewImage()">
                </li>
                <li>
                    <img id="image-preview" alt="image-preview" src="${artworkDto.imageSrc}">
                </li>
            </ul>
            <div>
                <label for="content">작품설명</label>
                <textarea name="content" id="content">${artworkDto.content}</textarea>
            </div>
        </div>
        <div class="artwork-form-btn">
            <input type="submit" value="${empty artworkDto ? '등록' : '수정'}">
            <input type="button" value="취소" onclick="history.back()">
        </div>
    </form>
</section>

    </jsp:attribute>
</t:layout>
