<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>회원가입 :: 정보 입력</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/member/join_form.css"/>">
        <script src="<c:url value="/static/script/member/join_form.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<main class="join-form-wrapper">
    <form class="join-form" method="post" action="<c:url value="/member/join"/>" onsubmit="ajaxSubmit(event)">
        <input type="hidden" name="returnUrl" value="${returnUrl}">
        <h2>회원가입</h2>
        <div class="field required">
            <label for="name">이름</label>
            <input type="text" name="name" id="name" required/>
        </div>
        <div class="field required">
            <label for="id">아이디</label>
            <div class="input-group">
                <input type="text" name="id" id="id" required/>
                <input type="hidden" name="idCheck" id="idCheck"
                       data-require-equals="id" data-require-message="아이디 중복 체크를 진행해주세요"/>
                <input type="button" onclick="submitIdCheck(this.form.id.value)" value="중복 체크">
            </div>
        </div>
        <p>아이디는 4자~12자 이내의 영문과 숫자로 공백 없이 입력하시면 됩니다.</p>
        <div class="field required">
            <label for="pwd">비밀번호</label>
            <input type="password" name="pwd" id="pwd" required/>
        </div>
        <div class="field required">
            <label for="pwdCheck">비밀번호 확인</label>
            <input type="password" name="pwdCheck" id="pwdCheck" required
                   data-require-equals="pwd" data-require-message="비밀번호 확인이 일치하지 않습니다"/>
        </div>
        <div class="field required">
            <label for="phone">연락처</label>
            <input type="tel" name="phone" id="phone" required/>
        </div>
        <div class="field required">
            <label for="email">이메일</label>
            <input type="email" name="email" id="email" required/>
        </div>
        <div class="field required">
            <label for="address">주소</label>
            <input type="text" name="address" id="address" required/>
        </div>
        <div class="btn-container">
            <input type="button" value="이전" onclick="history.back();">
            <input type="submit" value="가입">
        </div>
    </form>
</main>

    </jsp:attribute>
</t:layout>
