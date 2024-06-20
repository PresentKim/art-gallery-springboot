<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.team4.artgallery.enums.NoticeCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:header>
    <title>관리자 :: 소식지 관리</title>
    <link rel="stylesheet" href="<c:url value="/static/stylesheet/admin.css"/>">
    <script src="<c:url value="/static/script/admin.js"/>"></script>
</t:header>
<%@ include file="/WEB-INF/views/admin/sub_menu.jsp" %>
<section class="admin-list">
    <form method="post" name="adminForm">
        <div class="admin-list-btn">
            <!-- 체크된 id들이 배열값으로 들어오고 String 변환되어 넘어감 -->
            <input type="hidden" name="memberIds">
            <input type="button" value="게시글 등록" onclick="location.href='museum.do?command=insertNoticeForm'">
            <input type="button" value="수정" onclick="updatePost('updateNoticeForm&nseq=', 'li:nth-child(2)')">
            <input type="button" value="삭제" onclick="deletePost('adminDeleteNotice', 'li:nth-child(2)')">
            <input type="text" placeholder="검색어를 입력하세요" name="searchWord" value="${searchWord}">
            <input type="button" value="검색" onclick="searchAdmin('adminNoticeList')">
        </div>
        <ul class="admin-list-header admin-notice-list">
            <li>
                <input type="checkbox" onclick="checkAll()" class="select-all-box">
            </li>
            <li>번호</li>
            <li>
                <select onchange="categoryFilter('adminNoticeList', 'noticeCategory', event)"
                        name="selectCategoryFilter"
                        class="admin-select">
                    <option value="state">분류</option>
                    <c:forEach items="${NoticeCategory.validValues()}" var="c">
                        <c:choose>
                            <c:when test="${c.name().equals(selectedCategory)}">
                                <option value="${c.name()}" selected>${c.name()}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${c.name()}">${c.name()}</option>
                            </c:otherwise>
                        </c:choose>
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
                    <input type="checkbox" class="check-box">
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
</section>
<t:pagination/>
<t:footer/>
