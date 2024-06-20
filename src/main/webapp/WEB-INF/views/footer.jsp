<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</div>
<footer>
    <div class="footer_top">
        <a href="/" class="logo"> HI Art Gallery </a>
    </div>
    <div class="footer_info">
        <address>(03163) 서울 종로구 인사동길 12 대일빌딩 7층</address>
        <strong>대표전화 <a href="tel:12-345-678">12-345-678</a></strong>
    </div>
    <div class="footer_bottom">
        <ul class="footer_bottom_items">
            <li><a href="#">이용약관</a></li>
            <li><a href="#">개인정보처리방침</a></li>
            <li><a href="#">저작권정책</a></li>
            <li><a href="#">웹 접근성 품질인증</a></li>
            <c:if test="${account.admin}">
                <li>
                    <a href="/admin">관리자 페이지</a>
                </li>
            </c:if>
        </ul>
        <p class="footer_bottom_copyright">© HI Art Gallery. All rights reserved.</p>
    </div>
</footer>
</body>
</html>