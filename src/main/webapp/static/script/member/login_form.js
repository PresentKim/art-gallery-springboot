window.addEventListener('load', function () {
    // 폼 등록 이벤트 핸들러 등록
    const $form = document.getElementById('login-form');
    $form.addEventListener('submit-ajax', (event) => {
        const axiosOptions = getAxiosOptions($form);

        axios({
            url: '/api/members/login',
            method: 'POST',
            ...axiosOptions
        })
            .then(() => {
                location.href = decodeURIComponent(getReturnUrl());
            })
            .catch((error) => {
                if (error.response.status === 400) {
                    alert(error.response.data.message);
                } else if (error.response.status === 401) {
                    alert('아이디 또는 비밀번호가 일치하지 않습니다.');
                } else if (error.response.status === 409) {
                    alert('이미 로그인 상태입니다.');
                } else {
                    alert('로그인 중 오류가 발생했습니다.');
                }
            });
    });
});
