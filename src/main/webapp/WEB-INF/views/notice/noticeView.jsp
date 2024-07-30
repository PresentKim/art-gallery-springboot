<%--@elvariable id="loginMember" type="com.team4.artgallery.entity.MemberEntity"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>소식지 :: ${noticeEntity.title}</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/notice.css"/>">
        <script src="<c:url value="/static/script/notice/notice_view.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<div class="noticeView_container">
    <h2>상세 보기</h2>
    <div class="noticeView_box">
        <div class="noticeView_field">
            <div class="noticeView_label">작성자</div>
            <div class="noticeView_text">${noticeEntity.author.name}</div>
        </div>
        <div class="noticeView_field">
            <div class="noticeView_label">제목</div>
            <div class="noticeView_text">${noticeEntity.title}</div>
        </div>
        <div class="noticeView_field">
            <div class="noticeView_label">내용</div>
            <div class="noticeView_text">
                <pre>${noticeEntity.content}</pre>
            </div>
        </div>
        <div class="noticeView_field">
            <div class="noticeView_label">카테고리</div>
            <div class="noticeView_text">${noticeEntity.category}</div>
        </div>
        <div class="noticeView_field">
            <div class="noticeView_label">작성일</div>
            <div class="noticeView_text"><t:localdate value="${noticeEntity.indate}"/></div>
        </div>
        <div class="noticeView-button">
            <c:if test="${loginMember.admin}">
                <a href="<c:url value="/notice/write?nseq=${noticeEntity.nseq}"/>">수정</a>
                <div onclick="deleteNotice(${noticeEntity.nseq})">삭제</div>
                <a href="<c:url value="/notice"/>">목록</a>
            </c:if>
        </div>
    </div>
</div>

    </jsp:attribute>
</t:layout>
