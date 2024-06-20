<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.team4.artgallery.enums.ArtworkCategory" %>
<jsp:include page="/WEB-INF/views/header.jsp">
    <jsp:param name="stylesheet" value="/static/stylesheet/artwork.css"/>
    <jsp:param name="script" value="/static/script/artwork/artwork_write.js"/>
</jsp:include>
<h2 class="artwork-write-form-header">예술품 수정</h2>
<section class="artwork-write-form-main">
    <form name="artworkWriteForm" class="artwork-write-form"
          method="post" action="<c:url value="/artwork/update"/>" enctype="multipart/form-data"
          onsubmit="ajaxSubmit(event)">
        <input type="hidden" name="aseq" value="${artworkDto.aseq}">
        <div class="artwork-write-form_info">
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
                        <option value="">카테고리를 선택하세요</option>
                        <c:forEach items="${ArtworkCategory.validValues()}" var="c">
                            <option value="${c.name()}"
                                    <c:if test="${c.name().equals(artworkDto.category)}">selected</c:if>>${c.name()}</option>
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
                    <img id="image-preview" alt="image-preview" src="${artworkDto.fullSavefilename}">
                </li>
            </ul>
            <div>
                <label for="content">작품설명</label>
                <textarea name="content" id="content">${artworkDto.content}</textarea>
            </div>
        </div>
        <div class="artwork-write-form-btn">
            <input type="submit" value="수정">
            <input type="button" value="취소" onclick="history.back()">
        </div>
    </form>
</section>
<jsp:include page="/WEB-INF/views/footer.jsp"/>