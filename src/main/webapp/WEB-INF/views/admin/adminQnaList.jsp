<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/views/header.jsp">
	<jsp:param name="stylesheet" value="static/stylesheet/admin.css" />
	<jsp:param name="script" value="static/script/admin.js" />
</jsp:include>
<%@ include file="/WEB-INF/views/admin/sub_menu.jsp"%>
<%
String isReply = request.getParameter("isReply") != null ? request.getParameter("isReply") : "";
%>
<section class="admin-list">
	<form method="post" name="adminForm">
		<div class="admin-list-btn">
			<input type="hidden" name="memberIds">
			<input type="button" value="글 삭제" onclick="deletePost('adminDeleteQna', 'li:nth-child(3)')">
			<input type="text" placeholder="검색어를 입력하세요" name="searchWord" value="${searchWord}">
			<input type="button" value="검색" onclick="searchAdmin('adminQnaList')">
		</div>
		<ul class="admin-list-header admin-qna-list">
			<li>
				<input type="checkbox" onclick="checkAll()" class="select-all-box">
			</li>
			<li>
				<select onchange="displayFilter('adminQnaList', 'isReply')" name="selectDisplayFilter" class="admin-select">
					<option value="state">답변 여부</option>
					<option value="Y" <%=isReply.equals("Y") ? "selected" : ""%>>YES</option>
					<option value="N" <%=isReply.equals("N") ? "selected" : ""%>>NO</option>
				</select>
			</li>
			<li>번호</li>
			<li>제목</li>
			<li>내용</li>
			<li>작성일</li>
		</ul>
		<c:forEach items="${qnaList}" var="qvo">
			<ul class="admin-list-main admin-qna-list" onclick="go_check(event)">
				<li>
					<input type="checkbox" onclick="" class="check-box">
				</li>
				<li>
					<c:choose>
						<c:when test="${empty qvo.reply}">NO</c:when>
						<c:otherwise>YES</c:otherwise>
					</c:choose>
				</li>
				<li>${qvo.qseq}</li>
				<li class="view-link" onclick="location.href='museum.do?command=qnaView&qseq=${qvo.qseq}'">${qvo.title}</li>
				<li>${qvo.content}</li>
				<li>${qvo.writedate}</li>
			</ul>
		</c:forEach>
	</form>
</section>
<%@ include file="/WEB-INF/views/util/pagination.jsp"%>
<jsp:include page="/WEB-INF/views/footer.jsp" />