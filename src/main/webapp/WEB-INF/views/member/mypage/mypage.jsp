<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/views/header.jsp">
    <jsp:param name="stylesheet" value="static/stylesheet/member/mypage/mypage.css"/>
</jsp:include>
<main class="mypage-info-container">
    <h1 class="mypage_title">마이 페이지</h1>
    <div class="mypage_info_list">
        <div class="mypage_info_wrapper">
            <div class="mypage_info_label">이름</div>
            <div class="mypage_info_data">${loginUser.name}</div>
        </div>
        <div class="mypage_info_wrapper">
            <div class="mypage_info_label">아이디</div>
            <div class="mypage_info_data">${loginUser.id}</div>
        </div>
        <div class="mypage_info_wrapper">
            <div class="mypage_info_label">연락처</div>
            <div class="mypage_info_data">${loginUser.phone}</div>
        </div>
        <div class="mypage_info_wrapper">
            <div class="mypage_info_label">이메일</div>
            <div class="mypage_info_data">${loginUser.email}</div>
        </div>
    </div>
    <div class="mypage_button_wrapper">
        <a class="mypage_button" href="museum.do?command=mypageEditForm"> 개인정보수정 </a>
        <a class="mypage_button" href="museum.do?command=withdrawForm"> 회원탈퇴 </a>
        <a class="mypage_button" href="museum.do?command=mypageFavoriteList"> 관심예술품 </a>
    </div>
</main>
<jsp:include page="/WEB-INF/views/footer.jsp"/>