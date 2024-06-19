<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.team4.artgallery.enums.ArtworkCategory" %>
<jsp:include page="/WEB-INF/views/header.jsp">
    <jsp:param name="stylesheet" value="/static/stylesheet/admin.css"/>
    <jsp:param name="script" value="/static/script/admin.js"/>
</jsp:include>
<%@ include file="/WEB-INF/views/admin/sub_menu.jsp" %>
<%
    String displayState = request.getParameter("displayState") != null ? request.getParameter("displayState") : "";
%>
<section class="admin-list">
    <form action="museum.do" method="post" name="adminForm">
        <div class="admin-list-btn">
            <input type="hidden" name="command" value="adminArtworkList">
            <input type="hidden" name="memberIds">
            <input type="button" value="추가" onclick="location.href='museum.do?command=artworkWrite'">
            <input type="button" value="수정" onclick="updatePost('artworkUpdate&aseq=', 'li:nth-child(3)')">
            <input type="button" value="삭제" onclick="deletePost('adminDeleteArtwork', 'li:nth-child(3)')">
            <input type="text" placeholder="작품명 또는 작가명을 검색하세요" name="searchWord" value="${searchWord}">
            <input type="submit" value="검색">
        </div>
        <ul class="admin-list-header admin-artwork-list">
            <li><input type="checkbox" onclick="checkAll()" class="select-all-box"></li>
            <li><select name=displayState class="admin-select" onchange="this.form.submit();">
                <option value="">전시 상태</option>
                <option value="Y" <%=displayState.equals("Y") ? "selected" : ""%>>Y</option>
                <option value="N" <%=displayState.equals("N") ? "selected" : ""%>>N</option>
            </select></li>
            <li>번호</li>
            <li><select name="category" class="admin-select" onchange="this.form.submit();">
                <option value="">분류</option>
                <c:forEach items="${ArtworkCategory.validValues()}" var="c">
                    <c:choose>
                        <c:when test="${c.name().equals(category)}">
                            <option value="${c.name()}" selected>${c.name()}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${c.name()}">${c.name()}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select></li>
            <li>작품명</li>
            <li>작가명</li>
            <li>제작연도</li>
            <li>재료</li>
            <li>규격</li>
            <li>등록일</li>
            <li>미리보기</li>
        </ul>
        <c:forEach items="${artworkList}" var="artworkDto" varStatus="index">
            <ul class="admin-list-main admin-artwork-list" onclick="go_check(event)">
                <li><input type="checkbox" class="check-box"></li>
                <li>${artworkDto.displayyn}</li>
                <li>${artworkDto.aseq}</li>
                <li>${artworkDto.category}</li>
                <li class="view-link"
                    onclick="location.href='museum.do?command=artworkView&aseq=${artworkDto.aseq}'">${artworkDto.name}</li>
                <li>${artworkDto.artist}</li>
                <li>${artworkDto.year}</li>
                <li>${artworkDto.material}</li>
                <li>${artworkDto.size}</li>
                <li>${artworkDto.indate}</li>
                <li><img alt="artwork-img" src="${artworkDto.fullSavefilename}"
                         onmouseover="previewImg('artwork-${artworkDto.aseq}-${index}')"
                         onmouseleave="previewImg('artwork-${artworkDto.aseq}-${index}')"></li>
            </ul>
            <div id="artwork-${artworkDto.aseq}-${index}" class="preview hidden">
                <img alt="artwork-img" src="${artworkDto.fullSavefilename}">
            </div>
        </c:forEach>
    </form>
</section>
<%@ include file="/WEB-INF/views/util/pagination.jsp" %>
<jsp:include page="/WEB-INF/views/footer.jsp"/>