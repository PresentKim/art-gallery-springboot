<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/views/header.jsp">
    <jsp:param name="stylesheet" value="/static/stylesheet/notice.css"/>
    <jsp:param name="script" value="/static/script/notice.js"/>
</jsp:include>
<div class="noticeView_container">
    <h2>상세 보기</h2>
    <div class="noticeView_box">
        <div class="noticeView_field">
            <div class="noticeView_label">작성자</div>
            <div class="noticeView_text">${noticeView.author}</div>
        </div>
        <div class="noticeView_field">
            <div class="noticeView_label">제목</div>
            <div class="noticeView_text">${noticeView.title}</div>
        </div>
        <div class="noticeView_field">
            <div class="noticeView_label">내용</div>
            <div class="noticeView_text">
                <pre>${noticeView.content}</pre>
            </div>
        </div>
        <div class="noticeView_field">
            <div class="noticeView_label">카테고리</div>
            <div class="noticeView_text">${noticeView.category}</div>
        </div>
        <div class="noticeView_field">
            <div class="noticeView_label">작성일</div>
            <div class="noticeView_text">
                <fmt:formatDate value="${noticeView.writedate}"/>
            </div>
        </div>
        <div class="noticeView-button">
            <c:if test="${account.isAdmin}">
                <input type="button" class="btn-noticeView" value="수정"
                       onClick="location.href='museum.do?command=updateNoticeForm&nseq=${noticeView.nseq}'"/>
                <input type="button" class="btn-noticeView" value="삭제"
                       onClick="deleteNotice('${account.pwd}', '${noticeView.nseq}')"/>
                <input type="button" class="btn-noticeView" value="목록"
                       onClick="location.href='museum.do?command=noticeList'"/>
            </c:if>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
