<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/header.jsp">
    <jsp:param name="stylesheet" value="static/stylesheet/artwork.css"/>
    <jsp:param name="script" value="static/script/artwork.js"/>
</jsp:include>
<section class="artwork-view">
    <div class="artwork-view-header">
        <div class="artwork-view-title">
            <span>${artwork.artist} |&nbsp;</span> <span>${artwork.name} |&nbsp;</span> <span>${artwork.year}</span>
        </div>
        <div class="artwork-view_btn">
            <c:choose>
                <c:when test="${isAdmin}">
                    <c:choose>
                        <c:when test="${artwork.displayyn.equals('Y')}">
                            <input type="button" value="비공개로 전환"
                                   onclick="location.href='museum.do?command=artworkDisplaySet&aseq=${artwork.aseq}'">
                        </c:when>
                        <c:otherwise>
                            <input type="button" value="공개로 전환"
                                   onclick="location.href='museum.do?command=artworkDisplaySet&aseq=${artwork.aseq}'">
                        </c:otherwise>
                    </c:choose>
                    <input type="button" value="수정"
                           onclick="location.href='museum.do?command=artworkUpdate&aseq=${artwork.aseq}'"/>
                    <input type="button" value="삭제" onclick="go_deleteArtwork('${artwork.aseq}')"/>
                </c:when>
                <c:otherwise>
                    <input type="button" value="관심 예술품 +"
                           onclick="ajax({command:'mypageFavorite', aseq:${artwork.aseq}})"/>
                </c:otherwise>
            </c:choose>
            <input type="button" value="목록으로"
                   onclick="location.href='museum.do?command=artworkList&category=${category}'"/>
        </div>
    </div>
    <div class="artwork-view_img">
        <img alt="artwork-image" src="${artwork.fullSavefilename}">
        <span> ※ 예술품 이미지는 저작권법에 따라 복제뿐만 아니라 전송, 배포 등 어떠한 방식으로든 무단 이용할 수 없으며, 영리적인 목적으로 사용할 경우 원작자에게 별도의 동의를 받아야함을 알려드립니다. </span>
    </div>
    <div class="artwork-view_info">
        <ul>
            <li><span>작가명</span> <span>${artwork.artist}</span></li>
            <li><span>작품명</span> <span>${artwork.name}</span></li>
            <li><span>제작연도</span> <span>${artwork.year}</span></li>
            <li><span>재료</span> <span>${artwork.material}</span></li>
            <li><span>규격</span> <span>${artwork.size}</span></li>
            <li><span>부문</span> <span>${artwork.category}</span></li>
            <c:if test="${isAdmin}">
                <li><span>전시상태</span> <c:choose>
                    <c:when test="${artwork.displayyn.equals('Y')}">
                        <span>공개</span>
                    </c:when>
                    <c:otherwise>
                        <span>비공개</span>
                    </c:otherwise>
                </c:choose></li>
            </c:if>
        </ul>
    </div>
    <div class="artwork-view_content">
        <p>${artwork.content}</p>
    </div>
</section>
<jsp:include page="/WEB-INF/views/footer.jsp"/>