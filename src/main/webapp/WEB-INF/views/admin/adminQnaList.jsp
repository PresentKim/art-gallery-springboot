<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
    <title>관리자 :: 문의사항 관리</title>
    <link rel="stylesheet" href="<c:url value="/static/stylesheet/admin.css"/>">
    <script src="<c:url value="/static/script/admin.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<%@ include file="/WEB-INF/views/admin/sub_menu.jsp" %>
<section class="admin-list">
    <form method="post" name="adminForm">
        <div class="admin-list-btn">
            <input type="hidden" name="memberIds">
            <input type="button" value="글 삭제" onclick="deletePost('adminDeleteQna', 'li:nth-child(3)')">
            <input type="text" placeholder="검색어를 입력하세요" name="searchWord" value="${searchWord}">
            <input type="button" value="검색" onclick="searchAdmin('adminQnaList')">
        </div>
        <ul class="admin-list-header admin-qna-list">
            <li>
                <input type="checkbox" onclick="checkAll()" class="select-all-box">
            </li>
            <li>
                <select onchange="displayFilter('adminQnaList', 'isReply')" name="selectDisplayFilter"
                        class="admin-select">
                    <option value="state">답변 여부</option>
                    <option value="Y" <c:if test="'Y'.equals(isReply)">selected</c:if>>YES</option>
                    <option value="N" <c:if test="'N'.equals(isReply)">selected</c:if>>NO</option>
                </select>
            </li>
            <li>번호</li>
            <li>제목</li>
            <li>내용</li>
            <li>작성일</li>
        </ul>
        <c:forEach items="${qnaList}" var="qnaDto">
            <ul class="admin-list-main admin-qna-list" onclick="go_check(event)">
                <li>
                    <input type="checkbox" onclick="" class="check-box">
                </li>
                <li>
                    <c:choose>
                        <c:when test="${empty qnaDto.reply}">NO</c:when>
                        <c:otherwise>YES</c:otherwise>
                    </c:choose>
                </li>
                <li>${qnaDto.qseq}</li>
                <li class="view-link"
                    onclick="location.href='museum.do?command=qnaView&qseq=${qnaDto.qseq}'">${qnaDto.title}</li>
                <li>${qnaDto.content}</li>
                <li>${qnaDto.writedate}</li>
            </ul>
        </c:forEach>
    </form>
</section>
<t:pagination/>

    </jsp:attribute>
</t:layout>
