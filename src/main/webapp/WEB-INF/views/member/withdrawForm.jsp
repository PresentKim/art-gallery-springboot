<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/header.jsp">
	<jsp:param name="stylesheet" value="static/stylesheet/member/login_form.css" />
</jsp:include>
<main class="login-form-wrapper">
	<form class="login-form" method="post" action="museum.do?command=withdraw&returnUrl=${returnUrl}" onsubmit="ajaxSubmit(event)">
		<div class="login_icon_box">
			<div>
				<img src="static/image/ico_login_img.png" style="width: 80px; height: 80px;">
			</div>
			<h2>회원 탈퇴</h2>
		</div>
		<div class="field">
			<label>
				비밀번호
				<input name="pwd" type="password" placeholder="비밀번호" required>
			</label>
		</div>
		<div class="btn">
			<input type="submit" value="회원탈퇴" required>
			<input type="button" value="취소" onclick="history.back();'" required>
		</div>
	</form>
</main>
<jsp:include page="/WEB-INF/views/footer.jsp" />