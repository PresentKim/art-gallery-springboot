<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/header.jsp">
	<jsp:param name="stylesheet" value="static/stylesheet/member/login_form.css" />
</jsp:include>
<main class="login-form-wrapper">
	<form class="login-form" method="post" action="museum.do?command=login&returnUrl=${returnUrl}" onsubmit="ajaxSubmit(event)">
		<div class="login_icon_box">
			<div>
				<img src="static/image/ico_login_img.png" style="width: 80px; height: 80px;">
			</div>
			<h2>LogIn</h2>
		</div>
		<div class="field">
			<label>
				아이디
				<input name="id" type="text" placeholder="아이디" required>
			</label>
		</div>
		<div class="field">
			<label>
				비밀번호
				<input name="pwd" type="password" placeholder="비밀번호" required>
			</label>
		</div>
		<div class="btn">
			<input type="submit" value="로그인" required>
			<input type="button" value="회원가입" onclick="location.href='museum.do?command=contract'" required>
			<input type="button" value="아이디 찾기" onclick="" required>
		</div>
	</form>
</main>
<jsp:include page="/WEB-INF/views/footer.jsp" />