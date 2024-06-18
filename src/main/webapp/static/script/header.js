$(document).ready(function ($) {

    var run_delayed = {
        playing: false,

        play: function () {
            $('#logo_outline01').stop().animate({'fill-opacity': 0}, 500);
            $('#logo_outline02').stop().animate({'fill-opacity': 0}, 500);
            $('#logo_outline03').stop().animate({'fill-opacity': 0}, 500);
            $('#logo_outline04').stop().animate({'fill-opacity': 0}, 500);
            $('#logo_outline05').stop().animate({'fill-opacity': 0}, 500);
            $('#logo_outline06').stop().animate({'fill-opacity': 0}, 500);
            $('#logo_outline07').stop().animate({'fill-opacity': 0}, 500);
            $('#logo_outline08').stop().animate({'fill-opacity': 0}, 500);

            new Vivus("header_logo_v", {
                type: "delayed",
                start: "autostart",
                delay: 150,
                duration: 200,
            }, function () {
                $('#logo_outline01').stop().animate({'fill-opacity': 1}, 500);
                $('#logo_outline02').stop().animate({'fill-opacity': 1}, 500);
                $('#logo_outline03').stop().animate({'fill-opacity': 1}, 500);
                $('#logo_outline04').stop().animate({'fill-opacity': 1}, 500);
                $('#logo_outline05').stop().animate({'fill-opacity': 1}, 500);
                $('#logo_outline06').stop().animate({'fill-opacity': 1}, 500);
                $('#logo_outline07').stop().animate({'fill-opacity': 1}, 500);
                $('#logo_outline08').stop().animate({'fill-opacity': 1}, 500);
            });

            this.playing = true;
        },

        stop: function () {
            // Additional logic if needed
            this.playing = false;
        }
    };

    run_delayed.play();

    $('#header_logo_v').click(function (e) {
        e.preventDefault();
        if (!run_delayed.playing) {
            run_delayed.play();
        }
    });


    $('.header_gnb>div a:nth-child(1)').hover(function () {
        $('.header_gnb_list_containner').stop().slideDown()
    }, function () {
        $('.header_gnb_list_containner').stop().slideUp()
    })


    $('.header_gnb>div a:nth-child(2)').hover(function () {
        $('.header_gnb_list_containner01').stop().slideDown()
    }, function () {
        $('.header_gnb_list_containner01').stop().slideUp()
    })

    $('.header_gnb_list_containner').hover(function () {
        $('.header_gnb_list_containner').stop().slideDown()
    }, function () {
        $('.header_gnb_list_containner').stop().slideUp()
    })

    $('.header_gnb_list_containner01').hover(function () {
        $('.header_gnb_list_containner01').stop().slideDown()
    }, function () {
        $('.header_gnb_list_containner01').stop().slideUp()
    })

    // a 태그의 {RETURN_URL} 문자열을 현재 페이지의 URL로 치환
    function getReturnUrlParameter() {
        var urlParams = new URLSearchParams(location.search);
        return urlParams.get('returnUrl') || location.pathname + location.search;
    }

    $('a').map((_, a) => a.href = a.href.replace('{RETURN_URL}', encodeURIComponent(getReturnUrlParameter())))
});



