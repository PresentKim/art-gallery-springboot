<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.team4.artgallery.enums.NoticeCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>소식지 등록</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/notice.css"/>">
        <script src="<c:url value="/static/script/notice.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<div class="notice_insert_box">
    <h2>소식지 등록</h2>
    <div class="notice_insert_header_box">
        <form class="insertNotice" method="post" name="insertNotice" action="museum.do" enctype="multipart/form-data">
            <input type="hidden" name="command" value="insertNotice"/>
            <div class="notice_insert_field">
                <label for="category">카테고리</label>
                <select name="category" id="category">
                    <option value="">선택하세요</option>
                    <c:forEach items="${NoticeCategory.writableValues()}" var="c">
                        <option value="${c.name()}">${c.name()}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="notice_insert_field">
                <label>
                    제목
                    <input type="text" name="title"/>
                </label>
            </div>
            <div class="notice_insert_field">
                <label>
                    내용
                    <textarea name="content" rows="10" cols="100"></textarea>
                </label>
            </div>
            <!-- 			<div class="notice_insert_field">
                <label>이미지</label> <input type="file" name="image" />
            </div> -->
            <div class="notice_insert_button">
                <input type="submit" value="작성완료" onClick="return noticeCheck()"/>
                <input type="submit" value="취소"
                       onclick="window.location.href = 'museum.do?command=noticeList'; return false">
            </div>
        </form>
    </div>
</div>

    </jsp:attribute>
</t:layout>
