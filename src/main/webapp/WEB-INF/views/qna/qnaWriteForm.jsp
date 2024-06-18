<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/header.jsp">
    <jsp:param name="stylesheet" value="static/stylesheet/qna/qna_icon.css"/>
    <jsp:param name="stylesheet" value="static/stylesheet/qna/qna_write.css"/>
</jsp:include>
<main class="qna-write">
    <div class="qna-write_header">
        <div class="qna-write_header_title">
            <h1>
                <c:choose>
                    <c:when test="${empty qnaVO}">새로운 문의 작성</c:when>
                    <c:otherwise>${qnaVO.qseq}번 문의 수정</c:otherwise>
                </c:choose>
            </h1>
        </div>
    </div>
    <form class="qna-write_body" action="museum.do?command=qnaWrite" method="post" onsubmit="ajaxSubmit(event)">
        <input type="hidden" name="qseq" value="${qnaVO.qseq}"/>
        <div class="qna-write_input-wrapper">
            <i class="qna-icon title"></i>
            <input name="title" type="text" maxlength="100" autocomplete="off" value="${qnaVO.title}" placeholder=" "
                   required/>
            <label for="title">문의 제목</label>
        </div>
        <div class="qna-write_input-wrapper">
            <input name="publicyn" type="checkbox"
                   <c:if test="${qnaVO.isPublic()}">checked</c:if> />
            <label for="publicyn">전체 공개 여부</label>
        </div>
        <div class="qna-write_input-wrapper">
            <i class="qna-icon pwd"></i>
            <input name="pwd" type="password" maxlength="45" autocomplete="off" value="${qnaVO.pwd}" placeholder=" "
                   required/>
            <label for="pwd">글 비밀번호</label>
        </div>
        <div class="qna-write_input-wrapper">
            <i class="qna-icon email"></i>
            <input name="email" type="email" maxlength="45" autocomplete="email" value="${qnaVO.email}" placeholder=" "
                   required/>
            <label for="email">작성자 이메일</label>
        </div>
        <div class="qna-write_input-wrapper">
            <i class="qna-icon phone"></i>
            <input name="phone" type="tel" maxlength="45" autocomplete="tel" value="${qnaVO.phone}" placeholder=" "
                   required/>
            <label for="phone">작성자 휴대폰</label>
        </div>
        <div class="qna-write_input-wrapper">
            <i class="qna-icon content"></i>
            <textarea name="content" placeholder=" " required
                      oninput="this.parentNode.dataset.textareaInput = this.value">${qnaVO.content}</textarea>
            <label for="content">문의 내용</label>
        </div>
        <div class="qna-write_button-wrapper">
            <input class="qna-write_button " type="submit" value="등록"/>
            <label for="content">
                <i class="qna-icon upload"></i>
            </label>
        </div>
        <div class="qna-write_button-wrapper">
            <input class="qna-write_button " type="button" value="취소" onclick="history.back();"/>
            <label for="content">
                <i class="qna-icon cancel"></i>
            </label>
        </div>
    </form>
</main>
<jsp:include page="/WEB-INF/views/footer.jsp"/>