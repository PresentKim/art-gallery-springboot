<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>회원가입 :: 약관 동의</title>
    </jsp:attribute>

    <jsp:attribute name="content">

<section class="form-wrapper">
    <form method="get" action="<c:url value="/member/join"/>">
        <input type="hidden" name="returnUrl" value="${returnUrl}"/>
        <h2>가입 약관</h2>
        <div class="field">
            <label for="contract">약관 총칙</label>
            <textarea id="contract" readonly>제 1 조 (목적)
본 약관은 국립중앙박물관 사이트가 제공하는 모든 서비스(이하 "서비스")의 이용조건 및 절차, 이용자와 국립중앙박물관 사이트의 권리, 의무, 책임사항과 기타 필요한 사항을 규정함을 목적으로 합니다.

제 2 조 (약관의 효력과 변경)
국립중앙박물관 사이트는 귀하가 본 약관 내용에 동의하는 경우, 국립중앙박물관 사이트의 서비스 제공 행위 및 귀하의 서비스 사용 행위에 본 약관이 우선적으로 적용됩니다.
국립중앙박물관 사이트는 본 약관을 사전 고지 없이 변경할 수 있고, 변경된 약관은 국립중앙박물관 사이트 내에 공지하거나 e-mail을 통해 회원에게 공지하며, 공지와 동시에 그 효력이 발생됩니다. 이용자가 변경된 약관에 동의하지 않는 경우, 이용자는 본인의 회원등록을 취소(회원탈퇴)할 수 있으며 계속 사용의 경우는 약관 변경에 대한 동의로 간주됩니다.
제 3 조 (약관 외 준칙)
본 약관에 명시되지 않은 사항은 전기통신기본법, 전기통신사업법, 정보통신윤리위원회심의규정, 정보통신 윤리강령, 프로그램보호법 및 기타 관련 법령의 규정에 의합니다.
제 4 조 (용어의 정의)
본 약관에서 사용하는 용어의 정의는 다음과 같습니다.

이용자 : 본 약관에 따라 국립중앙박물관 사이트가 제공하는 서비스를 받는 자.
가입 : 국립중앙박물관 사이트가 제공하는 신청서 양식에 해당 정보를 기입하고, 본 약관에 동의하여 서비스 이용계약을 완료시키는 행위.
회원 : 국립중앙박물관 사이트에 개인정보를 제공하여 회원 등록을 한 자로서 국립중앙박물관 사이트가 제공하는 서비스를 이용할 수 있는 자.
비밀번호 : 이용자와 회원 ID가 일치하는지를 확인하고 통신상의 자신의 비밀보호를 위하여 이용자 자신이 선정한 문자와 숫자의 조합.
탈퇴 : 회원이 이용계약을 종료시키는 행위.
제 2장 서비스 제공 및 이용</textarea>
        </div>
        <div class="field">
            <label for="agree">약관 동의</label>
            <div class="input-group">
                <input id="agree" type="checkbox" required>
                <label for="agree">가입 약관에 동의합니다.</label>
            </div>
        </div>
        <div class="btn-container">
            <input type="submit" value="다음">
        </div>
    </form>
</section>

    </jsp:attribute>
</t:layout>
