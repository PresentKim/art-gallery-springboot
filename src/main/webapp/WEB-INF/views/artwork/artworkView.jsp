<%--@elvariable id="loginMember" type="com.team4.artgallery.dto.MemberDto"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>예술품 :: ${artworkDto.artist} | ${artworkDto.name}</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/artwork/artwork_view.css"/>">
        <script src="<c:url value="/static/script/artwork/artwork_view.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<section class="artwork-view">
    <div class="artwork-view-header">
        <div class="artwork-view-title">
            <span>${artworkDto.artist} |&nbsp;</span> <span>${artworkDto.name} |&nbsp;</span>
            <span>${artworkDto.year}</span>
        </div>
        <div class="artwork-view_btn">
            <c:if test="${not empty loginMember}">
                <div onclick="ajax('/member/mypage/favorite', {aseq: ${artworkDto.aseq}}).then(defaultAjaxHandler)">
                    관심 예술품 +
                </div>

                <c:if test="${loginMember.admin}">
                    <div onclick="toggleArtworkDisplay(this, ${artworkDto.aseq})">
                            ${artworkDto.display ? '비공개' : '공개'}로 전환
                    </div>
                    <a href="<c:url value="/artwork/write?aseq=${artworkDto.aseq}"/>">수정</a>
                    <div onclick="ajax('/artwork/delete', {aseq: ${artworkDto.aseq}}).then(defaultAjaxHandler)">
                        삭제
                    </div>
                </c:if>
            </c:if>

            <a href="<c:url value="/artwork"/>">목록으로</a>
        </div>
    </div>
    <div class="artwork-view_img">
        <img alt="artwork-image" src="${artworkDto.imageSrc}">
        <span> ※ 예술품 이미지는 저작권법에 따라 복제뿐만 아니라 전송, 배포 등 어떠한 방식으로든 무단 이용할 수 없으며, 영리적인 목적으로 사용할 경우 원작자에게 별도의 동의를 받아야함을 알려드립니다. </span>
    </div>
    <div class="artwork-view_info">
        <ul>
            <li><span>작가명</span> <span>${artworkDto.artist}</span></li>
            <li><span>작품명</span> <span>${artworkDto.name}</span></li>
            <li><span>제작연도</span> <span>${artworkDto.year}</span></li>
            <li><span>재료</span> <span>${artworkDto.material}</span></li>
            <li><span>규격</span> <span>${artworkDto.size}</span></li>
            <li><span>부문</span> <span>${artworkDto.category}</span></li>
            <c:if test="${loginMember.admin}">
                <li><span>전시상태</span> <c:choose>
                    <c:when test="${artworkDto.displayyn.equals('Y')}">
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
        <p>${artworkDto.content}</p>
    </div>
</section>

    </jsp:attribute>
</t:layout>
