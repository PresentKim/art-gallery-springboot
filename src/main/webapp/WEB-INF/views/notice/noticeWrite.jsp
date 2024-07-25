<%--@elvariable id="loginMember" type="com.team4.artgallery.entity.MemberEntity"--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.team4.artgallery.dto.enums.NoticeCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>${empty noticeDto ? '소식지 등록' : '소식지 수정 :: '}${noticeDto.nseq}</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/notice.css"/>">
        <script src="<c:url value="/static/script/notice/notice_write.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<div id="notice_update_box">
    <h2>소식지 수정</h2>
    <div class="notice_update_innerbox">
        <form id="notice-form" class="notice-form" method="post" data-nseq="${noticeDto.nseq}">
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
                        <%--@elvariable id="c" type="com.team4.artgallery.dto.enums.NoticeCategory"--%>
                        <option value="${c.value}"
                                <c:if test="${c.isEquals(noticeDto.category)}">selected</c:if>>${c.korName}</option>
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
