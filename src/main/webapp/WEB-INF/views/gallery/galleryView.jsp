<%--@elvariable id="loginMember" type="com.team4.artgallery.dto.MemberDto"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>갤러리 :: ${galleryDto.title}</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/gallery.css"/>">
        <script src="<c:url value="/static/script/gallery.js"/>"></script>
        <script type="text/javascript" src="https://ssl.pstatic.net/share/js/naver_sharebutton.js"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<section class="gallery-view">
    <ul class="gallery-header">
        <h1>${galleryDto.title}</h1>
        <li>${galleryDto.content}</li>
        <li>
            <a href="<c:url value="/gallery?keyword=${galleryDto.authorName}"/>"> ${galleryDto.authorName}님의 갤러리 </a>
        </li>
        <li>
            <span>조회수 ${galleryDto.readcount}</span>
            <span>
				<script type="text/javascript">
					new ShareNaver.makeButton({
                        "type": "b"
                    });
				</script>
			</span>
        </li>
        <li>
            <a href="<c:url value="/gallery"/>">
                <input value="목록으로" type="button" class="gbtn-back gallery-btn">
            </a>
        </li>
        <li class="gbtn">
            <c:if test="${loginMember.id eq galleryDto.authorId}">
                <a class="gbtn-update gallery-btn" href="<c:url value="/gallery/update?gseq=${galleryDto.gseq}"/>">
                    수정하기
                </a>
            </c:if>
            <c:if test="${loginMember.id eq galleryDto.authorId or loginMember.admin}">
                <div class="gbtn-delete gallery-btn"
                     onclick="ajax('/gallery/delete', 'gseq=${galleryDto.gseq}').then(defaultAjaxHandler)">
                    삭제하기
                </div>
            </c:if>
        </li>

    </ul>
    <ul class="gallery-main">
        <li>
            <img alt="gallery-img" src="<c:url value="/static/image/gallery/${galleryDto.savefilename}"/>">
        </li>
    </ul>
</section>

    </jsp:attribute>
</t:layout>