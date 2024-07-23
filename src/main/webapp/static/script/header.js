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

    const gnbContainerPairs = [
        [
            $('.header_gnb>div a:nth-child(1)'),
            $('.header_gnb_list_container:nth-child(2)')
        ],
        [
            $('.header_gnb>div a:nth-child(2)'),
            $('.header_gnb_list_container:nth-child(3)')
        ]
    ];
    for (const [$gnb, $container] of gnbContainerPairs) {
        $gnb.hover(
            () => $container.stop().slideDown(),
            () => $container.stop().slideUp()
        );

        $container.hover(
            () => $container.stop().slideDown(),
            () => $container.stop().slideUp()
        );
    }

});

function getReturnUrl() {
    const urlParams = new URLSearchParams(location.search);
    const returnUrl = urlParams.get('returnUrl') || location.pathname + location.search;
    return encodeURIComponent(returnUrl);
}

function onClickLoginButton() {
    location.href = `/member/login?returnUrl=${getReturnUrl()}`;
}

function onClickSignupButton() {
    location.href = `/member/contract?returnUrl=${getReturnUrl()}`;
}

function onClickLogoutButton() {
    axios({
        url: '/api/members/logout',
        method: 'POST'
    })
        .then(() => {
            alert('로그아웃 되었습니다.');
            location.reload();
        })
        .catch((error) => {
            if (error.response.status === 401) {
                alert('이미 로그아웃 상태입니다.');
            } else {
                alert('로그인 중 오류가 발생했습니다.');
                console.log(error);
            }
        });
}