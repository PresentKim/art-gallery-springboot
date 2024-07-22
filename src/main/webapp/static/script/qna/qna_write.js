window.addEventListener('load', function () {
    // 폼 등록 이벤트 핸들러 등록
    const $form = document.getElementById('qna-form');
    $form.addEventListener('submit', (event) => {
        event.preventDefault();
        if ($form.checkValidity()) {
            const commonData = {
                data: new FormData($form),
                headers: {'Content-Type': 'multipart/form-data'}
            }

            if ($form.getAttribute('data-qseq')) {
                const qseq = $form.getAttribute('data-qseq');
                axios({
                    url: `/api/qnas/${qseq}`,
                    method: 'PUT',
                    ...commonData
                })
                    .then(() => {
                        alert('문의글이 수정되었습니다');
                        location.href = `/qna/${qseq}`;
                    })
                    .catch(defaultAjaxHandler);
            } else {
                axios({
                    url: '/api/qnas',
                    method: 'POST',
                    ...commonData
                })
                    .then((json) => {
                        alert('문의글이 등록되었습니다');
                        location.href = `/qna/${json.data.qseq}`;
                    })
                    .catch(defaultAjaxHandler);
            }
        } else {
            alert('입력값을 확인해주세요.');
        }
    });
});
