window.addEventListener('load', function () {
    // 이미지 파일 선택 이벤트 핸들러 등록
    const $imageInput = document.getElementById('image-file');
    const $imagePreview = document.getElementById('image-preview');
    $imageInput.addEventListener('change', () => {
        let reader = new FileReader();
        reader.onload = () => $imagePreview.src = reader.result;
        reader.readAsDataURL($imageInput.files[0]);
    });

    // 폼 등록 이벤트 핸들러 등록
    const $form = document.getElementById('gallery-form');
    registerDefaultSubmitAjaxHandler($form, '/api/galleries', '/gallery/', 'gseq', '갤러리가');
});
