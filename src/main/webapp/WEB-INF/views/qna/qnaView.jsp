<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:header>
    <title>고객센터 :: ${qnaDto.title}</title>
    <link rel="stylesheet" href="<c:url value="/static/stylesheet/qna/qna_icon.css"/>">
    <link rel="stylesheet" href="<c:url value="/static/stylesheet/qna/qna_view.css"/>">
    <script src="<c:url value="/static/script/qna.js"/>"></script>
</t:header>
<main class="qna-view">
    <div class="qna-view_header">
        <div class="qna-view_header_title">
            <h1>고객센터</h1>
        </div>
        <div class="qna-view_header_buttons">
            <button class="qna-view_submit" onclick="qnaPwdCheck(${qnaDto.qseq}, 'edit')">수정하기</button>
            <button class="qna-view_submit" onclick="qnaPwdCheck(${qnaDto.qseq}, 'delete')">삭제하기</button>
        </div>
    </div>
    <div class="qna-view_body">
        <div class="qna-view_info">
            <h1>${qnaDto.title}</h1>
            <p>
                <strong>작성일</strong>:
                <fmt:formatDate value="${qnaDto.writedate}" pattern="yyyy-MM-dd"/>
            </p>
            <p>
                <strong>작성자</strong>:
                <c:choose>
                    <c:when test="${account.admin}">
                        ${qnaDto.email} (${qnaDto.phone})
                    </c:when>
                    <c:otherwise>${qnaDto.email.substring(0, 3)}**** (010-****-****)</c:otherwise>
                </c:choose>
            </p>
        </div>
        <div class="qna-view_content">
            <c:out value="${qnaDto.content}"/>
        </div>
    </div>
    <div class="qna-view_reply">
        <h2>답변</h2>
        <c:choose>
            <c:when test="${account.admin}">
                <form id="qnaReplyForm" action="museum.do?command=qnaReply" method="post" onsubmit="ajaxSubmit(event)">
                    <input type="hidden" name="qseq" value="${qnaDto.qseq}"/>
                    <textarea name="reply" placeholder="답변을 입력하세요"><c:out value="${qnaDto.reply}"/></textarea>
                    <input type="submit" value="답변 등록"/>
                </form>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${empty qnaDto.reply}">
                        <span class="qna-view_reply_no">답변이 아직 없습니다.</span>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${qnaDto.reply}"/>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
</main>
<t:footer/>
