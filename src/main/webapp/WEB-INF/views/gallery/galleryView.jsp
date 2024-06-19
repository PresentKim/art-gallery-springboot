<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/header.jsp">
    <jsp:param name="stylesheet" value="/static/stylesheet/gallery.css"/>
    <jsp:param name="script" value="/static/script/gallery.js"/>
</jsp:include>
<section class="gallery-view">
    <ul class="gallery-header">
        <h1>${galleryDto.title}</h1>
        <li>${galleryDto.content}</li>
        <li>
            <a href="museum.do?command=galleryList&searchWord=${galleryDto.authorName}"> ${galleryDto.authorName}님의
                갤러리 </a>
        </li>
        <li>
            <span>조회수 ${galleryDto.readcount}</span>
            <span>
				<script type="text/javascript" src="https://ssl.pstatic.net/share/js/naver_sharebutton.js"></script>
				<script type="text/javascript">
					new ShareNaver.makeButton({
                        "type": "b"
                    });
				</script>
			</span>
        </li>
        <li>
			<span>
				<input value="목록으로" type="button" class="gbtn-back gallery-btn"
                       onclick="location.href='museum.do?command=galleryList'">
			</span>
        </li>
        <c:if test="${account.id eq galleryDto.authorId}">
            <li class="gbtn">
                <input value="수정하기" type="button" class="gbtn-update gallery-btn"
                       onclick="location.href='museum.do?command=galleryUpdate&mseq=${galleryDto.mseq}'">
                <input value="삭제하기" type="button" class="gbtn-delete gallery-btn"
                       onclick="go_delete(${galleryDto.mseq})">
            </li>
        </c:if>
    </ul>
    <ul class="gallery-main">
        <li>
            <img alt="gallery-img" src="static/image/gallery/${galleryDto.savefilename}">
        </li>
    </ul>
</section>

<jsp:include page="/WEB-INF/views/footer.jsp"/>