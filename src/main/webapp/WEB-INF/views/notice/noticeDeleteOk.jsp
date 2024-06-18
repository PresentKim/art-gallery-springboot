<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/header.jsp">
	<jsp:param name="stylesheet" value="static/stylesheet/notice.css" />
	<jsp:param name="script" value="static/script/notice.js" />
</jsp:include>
<script type="text/javascript">
	alert("게시물 삭제가 완료되었습니다");
	location.href = 'museum.do?command=noticeList';
</script>
<jsp:include page="/WEB-INF/views/footer.jsp" />
