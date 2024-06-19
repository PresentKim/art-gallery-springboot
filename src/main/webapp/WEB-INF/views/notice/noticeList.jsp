<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.team4.artgallery.enums.NoticeCategory" %>
<jsp:include page="/WEB-INF/views/header.jsp">
    <jsp:param name="stylesheet" value="/static/stylesheet/notice.css"/>
    <jsp:param name="script" value="/static/script/notice.js"/>
</jsp:include>
<div class="notice_box">
    <div class="notice_header_box">
        <c:forEach items="${NoticeCategory.values()}" var="c">
            <a href="museum.do?command=noticeList&category=${c.name()}" class="notice-list_btn">${c.name()}</a>
        </c:forEach>
        <div class="writebutton">
            <c:if test="${account.isAdmin}">
                <input type="button" value="게시글 등록" onClick="location.href='museum.do?command=insertNoticeForm'"/>
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
                    <a href="museum.do?command=noticeView&nseq=${noticeDto.nseq}"> ${noticeDto.nseq} </a>
                    &nbsp;
                </div>
                <div class="col col_title">
                    <a href="museum.do?command=noticeView&nseq=${noticeDto.nseq}"> ${noticeDto.title} </a>
                    &nbsp;
                </div>
                <div class="col col_title">
                    <a href="museum.do?command=noticeView&nseq=${noticeDto.nseq}"> ${noticeDto.content} </a>
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
    <%@ include file="/WEB-INF/views/util/pagination.jsp" %>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>