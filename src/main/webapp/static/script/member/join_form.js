async function checkIdAvailability(id) {
    try {
        await axios({
            url: '/api/members/check-id',
            method: 'GET',
            headers: {'content-type': 'application/x-www-form-urlencoded'},
            params: {id},
        });

        document.getElementById('idCheck').value = id;
        alert('사용 가능한 아이디입니다.');
    } catch (error) {
        alert('이미 사용중인 아이디입니다.');
    }
}

function searchPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== '' && /[동로가]$/g.test(data.bname)) {
                extraRoadAddr += data.bname;
            }

            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== '' && data.apartment === 'Y') {
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }

            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (extraRoadAddr !== '') {
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 address 필드에 넣는다.
            document.getElementById('address').value = roadAddr + extraRoadAddr;
        }
    }).open();
}

window.addEventListener('load', function () {
    // 폼 등록 이벤트 핸들러 등록
    const $form = document.getElementById('join-form');
    $form.addEventListener('submit-ajax', () => {
        const axiosOptions = getAxiosOptions($form);

        axios({
            url: '/api/members',
            method: 'POST',
            ...axiosOptions
        })
            .then(() => {
                alert('회원가입이 완료되었습니다.');
                location.href = '/member/login?returnUrl=' + getReturnUrl();
            })
            .catch(error => defaultAjaxHandler(error.response));
    });
});
