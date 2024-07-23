window.addEventListener('load', function () {
    // 이미지 파일 미리보기 핸들러 등록
    registerImagePreviewHandler();

    // 폼 등록 이벤트 핸들러 등록
    const $form = document.getElementById('gallery-form');
    registerDefaultSubmitAjaxHandler($form, '/api/galleries', '/gallery/', 'gseq', '갤러리가');
});
