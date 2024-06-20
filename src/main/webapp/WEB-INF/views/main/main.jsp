<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:header>
    <title>하이 미술관</title>
    <link rel="stylesheet" href="<c:url value="/static/stylesheet/main.css"/>">
    <script src="<c:url value="/static/script/main.js"/>"></script>
</t:header>
<!-- 메인 상단 - 옆으로 넘어가는 슬라이드 -->
<div id="main-top-swiper" class="main-swiper" data-aos="fade-up" data-aos-offset="200" data-aos-delay="50"
     data-aos-duration="500" data-aos-easing="ease-in">
    <div class="main-swiper_image-container">
        <img src="static/image/main/main_image1.png">
        <img src="static/image/main/main_image2.jpg">
        <img src="static/image/main/main_image3.jpg">
        <img src="static/image/main/main_image4.jpg">
        <img src="static/image/main/main_image5.jpg">
        <img src="static/image/main/main_image6.jpg">
        <img src="static/image/main/main_image7.jpg">
        <img src="static/image/main/main_image8.jpg">
    </div>
    <div class="main-swiper_remote-container"></div>
</div>
<!-- -- 알립니다 -- -->
<div data-aos="fade-up" data-aos-offset="200" data-aos-delay="50" data-aos-duration="500" data-aos-easing="ease-in"
     class="main_notice_wrap_alarm">
    <h3 class="notice-tit">
        <i></i> 알립니다
    </h3>
    <div class="main_notice_inner">
        <div class="notice-area">
            <div class="notice_content">
                <div class="notice_tabBtn">
                    <button type="button" class="btn  selected" title="선택됨"
                            onClick="location.href='museum.do?command=noticeList&category=공지사항'">공지사항
                    </button>
                    <button type="button" class="btn "
                            onClick="location.href='museum.do?command=noticeList&category=이벤트'">이벤트
                    </button>
                    <button type="button" class="btn "
                            onClick="location.href='museum.do?command=noticeList&category=매거진'">매거진
                    </button>
                    <button type="button" class="btn "
                            onClick="location.href='museum.do?command=noticeList&category=신문'">신문
                    </button>
                </div>
                <div class="notice_inner">
                    <ul>
                        <c:forEach items="${noticeList}" var="n">
                            <li class="main_notice_inner_row">
                                <div class="col_col_title">
                                    <a href="museum.do?command=noticeView&nseq=${n.nseq}"> ${n.content} </a>
                                </div>
                                <div class="col_col_date">
                                    <fmt:formatDate value="${n.writedate}" pattern="yyyy-MM-dd"/>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div id="main-notice-swiper" class="main-swiper" data-aos="fade-up" data-aos-offset="200" data-aos-delay="50"
             data-aos-duration="500" data-aos-easing="ease-in">
            <div class="main-swiper_image-container">
                <img src="static/image/main/side_img01.jpg">
                <img src="static/image/main/side_img02.jpg">
                <img src="static/image/main/side_img03.jpg">
                <img src="static/image/main/side_img04.jpg">
            </div>
            <div class="main-swiper_remote-container"></div>
        </div>
    </div>
</div>
<!----------------------------------------- 좌우로 이동하는 슬라이드 --------------------------------------->
<div data-aos="fade-up" data-aos-offset="200" data-aos-duration="500" data-aos-easing="linear"
     class="main-middle-container">
    <div class="container-box">
        <div class="image-list" id="container1">
            <c:forEach items="${artworkList1}" var="list">
                <div class="imagelist" onclick="location.href='museum.do?command=artworkView&aseq=${list.aseq}'">
                    <img src="${list.fullSavefilename}">
                </div>
            </c:forEach>
        </div>
        <div class="image-list" id="container2">
            <c:forEach items="${artworkList2}" var="list">
                <div class="imagelist" onclick="location.href='museum.do?command=artworkView&aseq=${list.aseq}'">
                    <img src="${list.fullSavefilename}">
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="container-box">
        <div class="image-list" id="container3">
            <c:forEach items="${artworkList3}" var="list">
                <div class="imagelist" onclick="location.href='museum.do?command=artworkView&aseq=${list.aseq}'">
                    <img src="${list.fullSavefilename}">
                </div>
            </c:forEach>
        </div>
        <div class="image-list" id="container4">
            <c:forEach items="${artworkList4}" var="list">
                <div class="imagelist" onclick="location.href='museum.do?command=artworkView&aseq=${list.aseq}'">
                    <img src="${list.fullSavefilename}">
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<t:footer/>
