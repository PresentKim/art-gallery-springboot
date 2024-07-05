<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.team4.artgallery.dto.enums.NoticeCategory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="head">
        <title>하이 미술관</title>
        <link rel="stylesheet" href="<c:url value="/static/stylesheet/main.css"/>">
        <script src="<c:url value="/static/script/main.js"/>"></script>
    </jsp:attribute>

    <jsp:attribute name="content">

<!-- 메인 상단 - 옆으로 넘어가는 슬라이드 -->
<div id="main-top-swiper" class="main-swiper" data-aos="fade-up" data-aos-offset="200" data-aos-delay="50"
     data-aos-duration="500" data-aos-easing="ease-in">
    <div class="main-swiper_image-container">
        <img alt="main_image1" src="<c:url value="/static/image/main/main_image1.png"/>"/>
        <img alt="main_image2" src="<c:url value="/static/image/main/main_image2.jpg"/>"/>
        <img alt="main_image3" src="<c:url value="/static/image/main/main_image3.jpg"/>"/>
        <img alt="main_image4" src="<c:url value="/static/image/main/main_image4.jpg"/>"/>
        <img alt="main_image5" src="<c:url value="/static/image/main/main_image5.jpg"/>"/>
        <img alt="main_image6" src="<c:url value="/static/image/main/main_image6.jpg"/>"/>
        <img alt="main_image7" src="<c:url value="/static/image/main/main_image7.jpg"/>"/>
        <img alt="main_image8" src="<c:url value="/static/image/main/main_image8.jpg"/>"/>
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
                    <c:forEach items="${NoticeCategory.values()}" var="c">
                        <%--@elvariable id="c" type="com.team4.artgallery.dto.enums.NoticeCategory"--%>
                        <a class="btn" href="<c:url value="/notice?category=${c.value}"/>">${c.korName}</a>
                    </c:forEach>
                </div>
                <div class="notice_inner">
                    <ul>
                        <c:forEach items="${noticeList}" var="noticeDto">
                            <li class="main_notice_inner_row">
                                <div class="col_col_title">
                                    <a href="<c:url value="/notice/${noticeDto.nseq}"/>"> ${noticeDto.content} </a>
                                </div>
                                <div class="col_col_date">
                                    <fmt:formatDate value="${noticeDto.writedate}" pattern="yyyy-MM-dd"/>
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
                <img alt="side_img01" src="<c:url value="/static/image/main/side_img01.jpg"/>">
                <img alt="side_img02" src="<c:url value="/static/image/main/side_img02.jpg"/>">
                <img alt="side_img03" src="<c:url value="/static/image/main/side_img03.jpg"/>">
                <img alt="side_img04" src="<c:url value="/static/image/main/side_img04.jpg"/>">
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
            <c:forEach items="${artworkList1}" var="artworkDto">
                    <a class="imagelist" href="<c:url value="/artwork/${artworkDto.aseq}"/>">
                        <img alt="${artworkDto.name}" src="${artworkDto.imageSrc}">
                    </a>
            </c:forEach>
        </div>
        <div class="image-list" id="container2">
            <c:forEach items="${artworkList2}" var="artworkDto">
                <a class="imagelist" href="<c:url value="/artwork/${artworkDto.aseq}"/>">
                    <img alt="${artworkDto.name}" src="${artworkDto.imageSrc}">
                </a>
            </c:forEach>
        </div>
    </div>
    <div class="container-box">
        <div class="image-list" id="container3">
            <c:forEach items="${artworkList3}" var="artworkDto">
                <a class="imagelist" href="<c:url value="/artwork/${artworkDto.aseq}"/>">
                    <img alt="${artworkDto.name}" src="${artworkDto.imageSrc}">
                </a>
            </c:forEach>
        </div>
        <div class="image-list" id="container4">
            <c:forEach items="${artworkList4}" var="artworkDto">
                <a class="imagelist" href="<c:url value="/artwork/${artworkDto.aseq}"/>">
                    <img alt="${artworkDto.name}" src="${artworkDto.imageSrc}">
                </a>
            </c:forEach>
        </div>
    </div>
</div>

    </jsp:attribute>
</t:layout>
