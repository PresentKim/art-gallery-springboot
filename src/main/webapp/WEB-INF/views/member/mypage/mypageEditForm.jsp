<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/header.jsp">
	<jsp:param name="stylesheet" value="static/stylesheet/member/join_form.css" />
</jsp:include>
<main class="join-form-wrapper">
	<form class="join-form" method="post" action="museum.do?command=mypageEdit" onsubmit="ajaxSubmit(event)">
		<h2>회원정보 수정</h2>
		<div class="field">
			<span>*</span>
			<label for="name">이름</label>
			<input type="text" name="name" value="${loginUser.name}" required />
		</div>
		<div class="field">
			<span>*</span>
			<label for="id">아이디</label>
			<div>
				<input type="text" name="id" id="id" size="12" value="${loginUser.id}" readonly />
			</div>
		</div>
		<p style="font-size: 13px;">아이디는 4자~12자 이내의 영문과 숫자로 공백 없이 입력하시면 됩니다. 영문 대소문자를 구분하지 않습니다.</p>
		<div class="field">
			<span>*</span>
			<label for="pwd">비밀번호</label>
			<input type="password" name="pwd" id="pwd" required />
		</div>
		<div class="field">
			<span>*</span>
			<label for="pwdCheck">비밀번호 확인</label>
			<input type="password" name="pwdCheck" id="pwdCheck" data-require-equals="pwd" required />
		</div>
		<div class="field">
			<span>*</span>
			<label for="phone">연락처</label>
			<input type="tel" name="phone" id="phone" value="${loginUser.phone}" required />
		</div>
		<div class="field">
			<span>*</span>
			<label for="email">이메일</label>
			<input type="email" name="email" id="email" value="${loginUser.email}" required />
		</div>
		<div class="btn">
			<input type="button" value="이전" onclick="history.back();">
			<input type="submit" value="저장">
		</div>
	</form>
</main>
<jsp:include page="/WEB-INF/views/footer.jsp" />