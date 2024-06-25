<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.team4.artgallery.enums.NoticeCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>관리자 :: 소식지 관리</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/admin.css"/>">
        <script src="<c:url value="/static/script/admin.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<%@ include file="/WEB-INF/views/admin/sub_menu.jsp" %>
<section class="admin-list">
    <form name="adminForm" method="get" action="<c:url value="/admin/notice"/>" onsubmit="ajaxSubmit(event)">
        <div class="admin-list-btn">
            <!-- 검색 기능을 위해 최상단에 보이지 않는 submit 버튼을 추가 -->
            <input class="fake-submit" type="submit" formmethod="get" formaction="<c:url value="/admin/notice"/>">

            <!-- 기능 버튼 -->
            <div class="admin-list-func-btn">
                <input type="submit" value="등록" formmethod="get" formaction="<c:url value="/notice/write"/>">
                <input type="submit" value="수정" formmethod="post" formaction="<c:url value="/admin/notice/update"/>">
                <input type="submit" value="삭제" formmethod="post" formaction="<c:url value="/admin/notice/delete"/>">
            </div>

            <!-- 검색 기능 -->
            <div class="admin-list-search">
                <label><input type="text" placeholder="검색어를 입력해주세요" name="search" value="${filter.search}"></label>
                <input type="submit" value="검색" formmethod="get" formaction="<c:url value="/admin/notice"/>">
            </div>
        </div>
        <ul class="admin-list-header admin-notice-list">
            <li>
                <label><input type="checkbox" onclick="checkAll()" class="select-all-box"></label>
            </li>
            <li>번호</li>
            <li>
                <label for="category"></label>
                <select name="category" id="category" class="admin-select" onchange="this.form.submit();">
                    <option value="">전체</option>
                    <c:forEach items="${NoticeCategory.writableValues()}" var="c">
                        <option value="${c.name()}"
                                <c:if test="${c.name().equals(filter.category)}">selected</c:if>>${c.name()}</option>
                    </c:forEach>
                </select>
            </li>
            <li>제목</li>
            <li>내용</li>
            <li>작성일</li>
            <li>작성자</li>
            <li>조회수</li>
        </ul>
        <c:forEach items="${noticeList}" var="noticeDto">
            <ul class="admin-list-main admin-notice-list" onclick="go_check(event)">
                <li>
                    <label><input name="nseqs" type="checkbox" value="${noticeDto.nseq}" class="check-box"></label>
                </li>
                <li>${noticeDto.nseq}</li>
                <li>${noticeDto.category}</li>
                <li onclick="location.href='museum.do?command=noticeView&nseq=${noticeDto.nseq}'"
                    class="view-link">${noticeDto.title}</li>
                <c:choose>
                    <c:when test="${noticeDto.content.length() > 50}">
                        <li>${noticeDto.content.substring(0, 50)}...</li>
                    </c:when>
                    <c:otherwise>
                        <li>${noticeDto.content}</li>
                    </c:otherwise>
                </c:choose>
                <li>${noticeDto.writedate}</li>
                <li>${noticeDto.author}</li>
                <li>${noticeDto.readcount}</li>
            </ul>
        </c:forEach>
    </form>
    <t:pagination/>
</section>

    </jsp:attribute>
</t:layout>
