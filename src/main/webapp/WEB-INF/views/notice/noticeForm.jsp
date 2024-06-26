<%--@elvariable id="loginMember" type="com.team4.artgallery.dto.MemberDto"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.team4.artgallery.dto.enums.NoticeCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>${empty noticeDto ? '소식지 등록' : '소식지 수정 :: '}${noticeDto.nseq}</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/notice.css"/>">
    </jsp:attribute>

    <jsp:attribute name="content">

<div id="notice_update_box">
    <h2>소식지 수정</h2>
    <div class="notice_update_innerbox">
        <form class="notice_insertForm-form"
              method="post" enctype="multipart/form-data" onsubmit="ajaxSubmit(event)"
                <c:choose>
                    <c:when test="${empty noticeDto}">
                          action="<c:url value="/notice/write"/>"
                    </c:when>
                    <c:otherwise>
                          action="<c:url value="/notice/update"/>"
                    </c:otherwise>
                </c:choose>
        >
            <c:if test="${not empty noticeDto}">
                <input type="hidden" name="nseq" value="${noticeDto.nseq}">
            </c:if>
            <div class="notice_update_field">
                <label for="id">작성자</label> <input type="text" name="id" id="id" value="${loginMember.id}" readonly/>
            </div>
            <div class="notice_update_field">
                <label for="title">제목</label> <input type="text" name="title" id="title" value="${noticeDto.title}"/>
            </div>
            <div class="notice_update_field">
                <label for="content">내용</label>
                <textarea name="content" id="content" rows="10" cols="100">${noticeDto.content}</textarea>
            </div>
            <div class="notice_update_field">
                <label for="category">카테고리</label>
                <select name="category" id="category">
                    <c:if test="${empty noticeDto}">
                        <option value="" disabled selected>카테고리를 선택하세요</option>
                    </c:if>
                    <c:forEach items="${NoticeCategory.writableValues()}" var="c">
                        <option value="${c.name()}"
                                <c:if test="${c.name().equals(noticeDto.category)}">selected</c:if>>${c.name()}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="notice_update_field_btn">
                <input type="submit" value="${empty noticeDto ? '등록' : '수정'}">
                <input type="button" value="취소" onclick="history.back();"/>
            </div>
        </form>
    </div>
</div>
<div class="updateNotice_bottomClear"></div>

    </jsp:attribute>
</t:layout>
