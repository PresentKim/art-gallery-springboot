<%--@elvariable id="loginMember" type="com.team4.artgallery.dto.MemberDto"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>고객센터 :: ${pagination.currentPage}페이지</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/qna/qna_icon.css"/>">
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/qna/qna_list.css"/>">
        <script src="<c:url value="/static/script/qna.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<main class="qna-list">
    <div class="qna-list_header">
        <div class="qna-list_header_title">
            <h1>고객센터</h1>
            <p>
                총
                <span>${pagination.itemCount}</span>
                건이 검색되었습니다
            </p>
        </div>
        <a href="<c:url value="/qna/write"/>">
            <button class="qna-list_write-button">문의하기</button>
        </a>
    </div>
    <div class="qna-list_body">
        <table class="qna-list_table">
            <thead>
            <tr>
                <th data-title="번호">번호</th>
                <th data-title="제목">제목</th>
                <th data-title="날짜">날짜</th>
                <th data-title="상태">상태</th>
            </thead>
            <tbody>
            <c:forEach items="${qnaList}" var="qnaDto">
                <c:set var="qnaPassKey" value="qnaPass${qnaDto.qseq}"/>
                <tr onclick="qnaAuth(${qnaDto.qseq}, 'view')">
                    <td data-title="번호">${qnaDto.qseq}</td>
                    <td data-title="제목">
                        <div>
                            <c:choose>
                                <c:when test="${sessionScope[qnaPassKey]}">
                                    <i class="qna-icon owner"></i>
                                </c:when>
                                <c:when test="${qnaDto.display}">
                                    <i class="qna-icon public"></i>
                                </c:when>
                                <c:when test="${loginMember.admin}">
                                    <i class="qna-icon admin"></i>
                                </c:when>
                                <c:otherwise>
                                    <i class="qna-icon private"></i>
                                </c:otherwise>
                            </c:choose>
                            <span> ${qnaDto.title}</span>
                        </div>
                    </td>
                    <td data-title="날짜"><fmt:formatDate value="${qnaDto.writedate}" pattern="yyyy-MM-dd"/></td>
                    <td data-title="상태"><c:choose>
                        <c:when test="${empty qnaDto.reply}">대기중</c:when>
                        <c:otherwise>완료</c:otherwise>
                    </c:choose></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <t:pagination/>
    </div>
</main>

    </jsp:attribute>
</t:layout>
