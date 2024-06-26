<%--@elvariable id="account" type="com.team4.artgallery.dto.MemberDto"--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>마이페이지 :: 회원정보 수정</title>
        <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
        <script src="<c:url value="/static/script/member/join_form.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<section class="form-wrapper wide-form">
    <form method="post" action="<c:url value="/member/mypage/edit"/>" onsubmit="ajaxSubmit(event)">
        <h2>회원정보 수정</h2>
        <div class="field required">
            <label for="name">이름</label>
            <input type="text" name="name" id="name" value="${account.name}" required/>
        </div>
        <div class="field">
            <label for="id">아이디</label>
            <input type="text" name="id" id="id" size="12" value="${account.id}" readonly/>
        </div>
        <p>아이디는 4자~12자 이내의 영문과 숫자로 공백 없이 입력하시면 됩니다.</p>
        <div class="field">
            <label for="pwd">비밀번호</label>
            <input type="password" name="pwd" id="pwd"/>
        </div>
        <div class="field">
            <label for="pwdCheck">비밀번호 확인</label>
            <input type="password" name="pwdCheck" id="pwdCheck"
                   data-require-equals="pwd" data-require-message="비밀번호 확인이 일치하지 않습니다"/>
        </div>
        <p>비밀번호를 변경하시려면 새로운 비밀번호를 입력해주세요.</p>
        <div class="field">
            <label for="phone">연락처</label>
            <input type="tel" name="phone" id="phone" value="${account.phone}" required/>
        </div>
        <div class="field required">
            <label for="email">이메일</label>
            <input type="email" name="email" id="email" value="${account.email}" required/>
        </div>
        <div class="field required">
            <label for="address">주소</label>
            <div class="input-group">
                <input type="text" name="address" id="address" value="${account.address}" required/>
                <input type="button" onclick="searchPostcode()" value="주소 검색">
            </div>
        </div>
        <div class="btn-container">
            <input type="button" value="이전" onclick="history.back();">
            <input type="submit" value="저장">
        </div>
    </form>
</section>

    </jsp:attribute>
</t:layout>