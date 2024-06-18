<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/views/header.jsp">
	<jsp:param name="stylesheet" value="static/stylesheet/gallery.css" />
	<jsp:param name="script" value="static/script/gallery.js" />
</jsp:include>
<main class="gallery-list">
	<section class="gallery-list-header">
		<form action="museum.do?command=galleryList" method="post" name="searchForm">
			<h1>갤러리 검색</h1>
			<div>
				<input type="text" placeholder="제목 또는 내용을 검색하세요" name="searchWord" value="${searchWord}">
				<input type="submit" value="검색" onclick="return go_search()" class="artwork-search-form_btn">
			</div>
		</form>
	</section>
	<section class="gallery-list-main">
		<div class="gallery-list-main-title">
			<h4>검색결과가 총 ${pagination.itemCount}건 입니다</h4>
			<a href="museum.do?command=galleryWrite" class="gallery-btn">갤러리 등록</a>
		</div>
		<div class="gallery-list-main-content">
			<c:forEach items="${galleryList}" var="mgvo">
				<div onclick="location.href='museum.do?command=galleryView&mseq=${mgvo.mseq}'">
					<img src="static/image/gallery/${mgvo.savefilename}" alt="member_gallery_image" />
					<div class="gallery-list-main-content_info">
						<h1 class="glmc_info-title">${mgvo.title}</h1>
						<p class="glmc_info-name">${mgvo.authorName}님의갤러리</p>
						<span>조회수 : ${mgvo.readcount}</span>
					</div>
				</div>
			</c:forEach>
		</div>
		<%@ include file="/WEB-INF/views/util/pagination.jsp"%>
	</section>
</main>
<jsp:include page="/WEB-INF/views/footer.jsp" />