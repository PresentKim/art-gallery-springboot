<%--@elvariable id="account" type="com.team4.artgallery.dto.MemberDto"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.team4.artgallery.enums.NoticeCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>소식지 :: ${pagination.currentPage}</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/notice.css"/>">
        <script src="<c:url value="/static/script/notice.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<div class="notice_box">
    <div class="notice_header_box">
        <c:forEach items="${NoticeCategory.values()}" var="c">
            <a href="<c:url value="/notice?category=${c.name()}"/>" class="notice-list_btn">${c.name()}</a>
        </c:forEach>
        <div class="writebutton">
            <c:if test="${account.admin}">
                <input type="button" value="게시글 등록" onClick="location.href='/notice/write'"/>
            </c:if>
        </div>
    </div>
    <div class="notice_title_box">
        <div class="notice_title_row">
            <div class="notice_title_col col_number">번호</div>
            <div class="notice_title_col col_title">제목</div>
            <div class="notice_title_col col_content">내용</div>
            <div class="notice_title_col col_date">작성일</div>
            <div class="notice_title_col col_author">작성자</div>
            <div class="notice_title_col col_views">조회수</div>
            <div class="notice_title_col col_category">분류</div>
        </div>
        <c:forEach items="${noticeList}" var="noticeDto">
            <div class="row">
                    <%-- <div class="col col_number">${noticeList.nseq}</div> --%>
                <div class="col col_number">
                    <a href="<c:url value="/notice/${noticeDto.nseq}"/>"> ${noticeDto.nseq} </a>
                    &nbsp;
                </div>
                <div class="col col_title">
                    <a href="<c:url value="/notice/${noticeDto.nseq}"/>"> ${noticeDto.title} </a>
                    &nbsp;
                </div>
                <div class="col col_title">
                    <a href="<c:url value="/notice/${noticeDto.nseq}"/>"> ${noticeDto.content} </a>
                    &nbsp;
                </div>
                    <%-- <div class="col col_content">${noticeList.content}</div> --%>
                <div class="col col_date">
                    <fmt:formatDate value="${noticeDto.writedate}" pattern="yyyy-MM-dd"/>
                </div>
                <div class="col col_author">${noticeDto.author}</div>
                <div class="col col_views">${noticeDto.readcount}</div>
                <div class="col col_category">${noticeDto.category}</div>
            </div>
        </c:forEach>
    </div>
    <t:pagination/>
</div>

    </jsp:attribute>
</t:layout>
