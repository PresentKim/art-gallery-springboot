window.addEventListener('load', function () {
    // 이미지 파일 선택 이벤트 핸들러 등록
    const $imageInput = document.getElementById('image-file');
    const $imagePreview = document.getElementById('image-preview');
    $imageInput.addEventListener('change', () => {
        let reader = new FileReader();
        reader.onload = () => $imagePreview.src = reader.result;
        reader.readAsDataURL($imageInput.files[0]);
    });

    // 갤러리 폼 등록 이벤트 핸들러 등록
    const $form = document.getElementById('gallery-form');
    $form.addEventListener('submit', (event) => {
        event.preventDefault();
        if ($form.checkValidity()) {
            const commonData = {
                data: new FormData($form),
                headers: {'Content-Type': 'multipart/form-data'}
            }

            if ($form.getAttribute('data-gseq')) {
                const gseq = $form.getAttribute('data-gseq');
                axios({
                    url: `/api/galleries/${gseq}`,
                    method: 'PUT',
                    ...commonData
                })
                    .then(() => {
                        alert('갤러리가 수정되었습니다');
                        location.href = `/gallery/${gseq}`;
                    })
                    .catch(defaultAjaxHandler);
            } else {
                axios({
                    url: '/api/galleries',
                    method: 'POST',
                    ...commonData
                })
                    .then((json) => {
                        alert('갤러리가 등록되었습니다');
                        location.href = `/gallery/${json.data.gseq}`;
                    })
                    .catch(defaultAjaxHandler);
            }
        } else {
            alert('입력값을 확인해주세요.');
        }
    });
});
