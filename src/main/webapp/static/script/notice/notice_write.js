window.addEventListener('load', function () {
    // 폼 등록 이벤트 핸들러 등록
    const $form = document.getElementById('notice-form');
    $form.addEventListener('submit', (event) => {
        event.preventDefault();
        if (validateForm($form)) {
            const commonData = {
                data: new FormData($form),
                headers: {'Content-Type': 'multipart/form-data'}
            }

            if ($form.getAttribute('data-nseq')) {
                const nseq = $form.getAttribute('data-nseq');
                axios({
                    url: `/api/notices/${nseq}`,
                    method: 'PUT',
                    ...commonData
                })
                    .then(() => {
                        alert('소식지가 수정되었습니다');
                        location.href = `/notice/${nseq}`;
                    })
                    .catch(defaultAjaxHandler);
            } else {
                axios({
                    url: '/api/notices',
                    method: 'POST',
                    ...commonData
                })
                    .then((json) => {
                        alert('소식지가 등록되었습니다');
                        location.href = `/notice/${json.data.nseq}`;
                    })
                    .catch(defaultAjaxHandler);
            }
        } else {
            alert('입력값을 확인해주세요.');
        }
    });
});
