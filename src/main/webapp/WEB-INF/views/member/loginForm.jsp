<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>로그인</title>
    </jsp:attribute>

    <jsp:attribute name="content">

<section class="form-wrapper">
    <form method="post" action="<c:url value="/member/login"/>" onsubmit="ajaxSubmit(event)">
        <input type="hidden" name="returnUrl" value="${returnUrl}">
        <img src="<c:url value="/static/image/ico_login_img.png"/>" alt="form-logo">
        <h2>로그인</h2>
        <div class="field">
            <label for="id">아이디</label>
            <input type="text" name="id" id="id" placeholder="아이디" required>
        </div>
        <div class="field">
            <label for="pwd">비밀번호</label>
            <input type="password" name="pwd" id="pwd" placeholder="비밀번호" required>
        </div>
        <div class="btn-container">
            <input type="submit" value="로그인">
            <a href="<c:url value="/member/contract"/>">회원가입</a>
            <input type="button" value="아이디 찾기" onclick="">
        </div>
    </form>
</section>

    </jsp:attribute>
</t:layout>
