<%--@elvariable id="loginMember" type="com.team4.artgallery.entity.MemberEntity"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>갤러리 :: ${galleryEntity.title}</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/gallery.css"/>">
        <script src="<c:url value="/static/script/gallery/gallery_view.js"/>"></script>
        <script type="text/javascript" src="https://ssl.pstatic.net/share/js/naver_sharebutton.js"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<section class="gallery-view">
    <ul class="gallery-header">
        <h1>${galleryEntity.title}</h1>
        <li>${galleryEntity.content}</li>
        <li>
            <a href="<c:url value="/gallery?keyword=${galleryEntity.author.name}"/>"> ${galleryEntity.author.name}님의
                갤러리 </a>
        </li>
        <li>
            <span>조회수 ${galleryEntity.readCount}</span>
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
            <c:if test="${loginMember.id eq galleryEntity.author.id}">
                <a class="gbtn-update gallery-btn" href="<c:url value="/gallery/write?gseq=${galleryEntity.gseq}"/>">
                    수정
                </a>
            </c:if>
            <c:if test="${loginMember.id eq galleryEntity.author.id or loginMember.admin}">
                    <div class="gbtn-delete gallery-btn" onclick="deleteGallery(${galleryEntity.gseq})">삭제</div>
            </c:if>
        </li>

    </ul>
    <ul class="gallery-main">
        <li>
            <img alt="gallery-img" src="<c:url value="${galleryEntity.imageSrc}"/>">
        </li>
    </ul>
</section>

    </jsp:attribute>
</t:layout>