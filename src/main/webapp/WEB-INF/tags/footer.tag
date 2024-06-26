<%--@elvariable id="loginMember" type="com.team4.artgallery.dto.MemberDto"--%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<footer class="flow-anchor">
    <address>(03163) 서울 종로구 인사동길 12 대일빌딩 7층</address>
    <strong>대표전화 <a href="tel:12-345-678">12-345-678</a></strong>
    <div class="footer_bottom">
        <div class="link-list">
            <a href="#">이용약관</a>
            <a href="#">개인정보처리방침</a>
            <a href="#">저작권정책</a>
            <a href="#">웹 접근성 품질인증</a>
            <c:if test="${loginMember.admin}">
                <a href="<c:url value="/admin"/>">관리자 페이지</a>
            </c:if>
        </div>
        <div class="copyright">© HI Art Gallery. All rights reserved.</div>
    </div>
</footer>
