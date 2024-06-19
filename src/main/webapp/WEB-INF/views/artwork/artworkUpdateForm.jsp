<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.team4.artgallery.enums.ArtworkCategory" %>
<jsp:include page="/WEB-INF/views/header.jsp">
    <jsp:param name="stylesheet" value="static/stylesheet/artwork.css"/>
    <jsp:param name="script" value="static/script/artwork.js"/>
</jsp:include>
<h2 class="artwork-write-form-header">예술품 수정</h2>
<section class="artwork-write-form-main">
    <form method="post" name="artworkWriteForm" action="museum.do?command=artworkUpdateForm" class="artwork-write-form"
          enctype="multipart/form-data">
        <div class="artwork-write-form_info">
            <ul>
                <li>
                    <div>작가명</div>
                    <input type="text" name="artist" value="${artwork.artist}" onchange="changeValue('unknownArtist')">
                    <c:choose>
                        <c:when test="${artwork.artist.equals('작자미상')}">
                            <input type="checkbox" name="unknownArtist" onclick="artistUnknown()" id="unknownArtist"
                                   checked="checked">
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" name="unknownArtist" onclick="artistUnknown()" id="unknownArtist">
                        </c:otherwise>
                    </c:choose>
                    <label for="unknownArtist" class="unknown-label">작자미상</label>
                </li>
                <li>
                    <div>작품명</div>
                    <input type="text" name="artname" value="${artwork.name}">
                </li>
                <li>
                    <div>제작연도</div>
                    <input type="text" name="year" value="${artwork.year}" maxlength="4"
                           onchange="changeValue('unknownYear')">
                    <c:choose>
                        <c:when test="${artwork.year.equals('연도미상')}">
                            <input type="checkbox" name="unknownYear" onclick="yearUnknown()" id="unknownYear"
                                   checked="checked">
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" name="unknownYear" onclick="yearUnknown()" id="unknownYear">
                        </c:otherwise>
                    </c:choose>
                    <label for="unknownYear" class="unknown-label">연도미상</label>
                </li>
                <li>
                    <div>재료</div>
                    <input type="text" name="material" value="${artwork.material}">
                </li>
                <li>
                    <div>규격</div>
                    <input type="text" name="size" value="${artwork.size}">
                </li>
                <li>
                    <div>부문</div>
                    <select name="category">
                        <option value="">카테고리를 선택하세요</option>
                        <c:forEach items="${ArtworkCategory.validValues()}" var="c">
                            <c:choose>
                                <c:when test="${c.name().equals(artwork.category)}">
                                    <option value="${c.name()}" selected>${c.name()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${c.name()}">${c.name()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </li>
                <li>
                    <div>전시상태</div>
                    <c:choose>
                        <c:when test="${artwork.displayyn.equals('Y')}">
                            <input type="radio" name="displayYn" value="Y" id="displayOn" checked="checked">
                            <label for="displayOn">공개</label>
                            <input type="radio" name="displayYn" value="N" id="displayOff">
                            <label for="displayOff">비공개</label>
                        </c:when>
                        <c:otherwise>
                            <input type="radio" name="displayYn" value="Y" id="displayOn">
                            <label for="displayOn">공개</label>
                            <input type="radio" name="displayYn" value="N" id="displayOff" checked="checked">
                            <label for="displayOff">비공개</label>
                        </c:otherwise>
                    </c:choose>
                </li>
                <li>
                    <div>이미지 등록</div>
                    <input type="file" name="image" accept="image/*" onchange="previewImage()">
                </li>
                <li>
                    <img alt="image" src="${artwork.fullSavefilename}" name="uploadedImage">
                </li>
            </ul>
            <div>
                <div>작품설명</div>
                <textarea name="content">${artwork.content}</textarea>
            </div>
        </div>
        <div class="artwork-write-form-btn">
            <input type="button" value="수정" onclick="artworkUpdate(this)">
            <input type="hidden" name="aseq" value="${artwork.aseq}">
            <input type="button" value="취소" onclick="history.back()">
        </div>
    </form>
</section>
<jsp:include page="/WEB-INF/views/footer.jsp"/>