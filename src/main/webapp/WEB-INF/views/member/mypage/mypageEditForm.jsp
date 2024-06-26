<%--@elvariable id="account" type="com.team4.artgallery.dto.MemberDto"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>마이페이지 :: 회원정보 수정</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/member/join_form.css"/>">
    </jsp:attribute>

    <jsp:attribute name="content">

<main class="join-form-wrapper">
    <form class="join-form" method="post" action="<c:url value="/member/mypage/edit"/>" onsubmit="ajaxSubmit(event)">
        <h2>회원정보 수정</h2>
        <div class="field">
            <span>*</span>
            <label for="name">이름</label>
            <input type="text" name="name" id="name" value="${account.name}" required/>
        </div>
        <div class="field">
            <span>*</span>
            <label for="id">아이디</label>
            <div>
                <input type="text" name="id" id="id" size="12" value="${account.id}" readonly/>
            </div>
        </div>
        <p style="font-size: 13px;">아이디는 4자~12자 이내의 영문과 숫자로 공백 없이 입력하시면 됩니다. 영문 대소문자를 구분하지 않습니다.</p>
        <div class="field">
            <span>*</span>
            <label for="pwd">비밀번호</label>
            <input type="password" name="pwd" id="pwd" required/>
        </div>
        <div class="field">
            <span>*</span>
            <label for="pwdCheck">비밀번호 확인</label>
            <input type="password" name="pwdCheck" id="pwdCheck" data-require-equals="pwd" required/>
        </div>
        <div class="field">
            <span>*</span>
            <label for="phone">연락처</label>
            <input type="tel" name="phone" id="phone" value="${account.phone}" required/>
        </div>
        <div class="field">
            <span>*</span>
            <label for="email">이메일</label>
            <input type="email" name="email" id="email" value="${account.email}" required/>
        </div>
        <div class="field">
            <span>*</span>
            <label for="address">주소</label>
            <input type="text" name="address" id="address" value="${account.address}" required/>
        </div>
        <div class="btn">
            <input type="button" value="이전" onclick="history.back();">
            <input type="submit" value="저장">
        </div>
    </form>
</main>

    </jsp:attribute>
</t:layout>