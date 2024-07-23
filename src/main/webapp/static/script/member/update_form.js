window.addEventListener('load', function () {
    // 폼 등록 이벤트 핸들러 등록
    const $form = document.getElementById('member-update-form');
    $form.addEventListener('submit-ajax', () => {
        const axiosOptions = getAxiosOptions($form);

        axios({
            url: `/api/members/${$form.id.value}`,
            method: 'POST',
            ...axiosOptions
        })
            .then(() => {
                alert('회원 정보 수정이 완료되었습니다.');
                location.href = '/member/mypage';
            })
            .catch(error => defaultAjaxHandler(error.response));
    });
});
