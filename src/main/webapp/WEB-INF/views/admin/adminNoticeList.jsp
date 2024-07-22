<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.team4.artgallery.dto.enums.NoticeCategory" %>
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
    <form name="adminForm" method="get" action="<c:url value="/admin/notice"/>">
        <div class="admin-list-btn">
            <!-- 기능 버튼 -->
            <div class="admin-list-func-btn">
                <input type="button" value="등록" onclick="location.href = '/notice/write'">
                <input type="button" value="수정" onclick="updateSelected('/notice/write?nseq=')">
                <input type="button" value="삭제" onclick="deleteSelected('/api/notices/')">
            </div>

            <!-- 검색 기능 -->
            <div class="admin-list-search">
                <label><input type="text" placeholder="검색어를 입력해주세요" name="keyword" value="${filter.keyword}"></label>
                <input type="submit" value="검색">
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
                        <%--@elvariable id="c" type="com.team4.artgallery.dto.enums.NoticeCategory"--%>
                        <option value="${c.value}"
                                <c:if test="${c.isEquals(filter.category)}">selected</c:if>>${c.korName}</option>
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
            <ul
                    class="admin-list-main admin-notice-list"
                    onclick="checkChildCheckbox(this)"
                    data-seq="${noticeDto.nseq}"
            >
                <li>
                    <input type="checkbox">
                </li>
                <li>${noticeDto.nseq}</li>
                <li>${noticeDto.category}</li>
                <li class="view-link"><a href="<c:url value="/notice/${noticeDto.nseq}"/>">${noticeDto.title}</a></li>
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
