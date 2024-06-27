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
    <form name="adminForm" method="get" action="<c:url value="/admin/qna"/>" onsubmit="ajaxSubmit(event)">
        <div class="admin-list-btn">
            <!-- 검색 기능을 위해 최상단에 보이지 않는 submit 버튼을 추가 -->
            <input class="fake-submit" type="submit" formmethod="get" formaction="<c:url value="/admin/qna"/>">

            <!-- 기능 버튼 -->
            <div class="admin-list-func-btn">
                <input type="submit" value="삭제" formmethod="post" formaction="<c:url value="/admin/qna/delete"/>">
            </div>

            <!-- 검색 기능 -->
            <div class="admin-list-search">
                <label><input type="text" placeholder="제목 또는 내용 입력하세요" name="keyword" value="${filter.keyword}"></label>
                <input type="submit" value="검색">
            </div>
        </div>
        <ul class="admin-list-header admin-qna-list">
            <li>
                <label><input type="checkbox" onclick="checkAll()" class="select-all-box"></label>
            </li>
            <li>
                <label for="replyyn"></label>
                <select name="replyyn" id="replyyn" class="admin-select" onchange="this.form.submit();">
                    <option value="">전체</option>
                    <option value="Y" <c:if test="${'Y'.equals(filter.replyyn)}">selected</c:if>>답변완료</option>
                    <option value="N" <c:if test="${'N'.equals(filter.replyyn)}">selected</c:if>>대기중</option>
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
                    <label><input name="qseqs" type="checkbox" value="${qnaDto.qseq}" class="check-box"></label>
                </li>
                <li>${qnaDto.hasReply() ? '답변완료' : '대기중'}</li>
                <li>${qnaDto.qseq}</li>
                <li class="view-link"><a href="<c:url value="/qna/${qnaDto.qseq}"/>">${qnaDto.title}</a></li>
                <li>${qnaDto.content}</li>
                <li>${qnaDto.writedate}</li>
            </ul>
        </c:forEach>
    </form>
    <t:pagination/>
</section>

    </jsp:attribute>
</t:layout>
