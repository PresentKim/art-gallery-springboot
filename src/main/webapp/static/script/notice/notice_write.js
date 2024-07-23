window.addEventListener('load', function () {
    // 폼 등록 이벤트 핸들러 등록
    const $form = document.getElementById('notice-form');
    registerDefaultSubmitAjaxHandler($form, '/api/notices', '/notice/', 'nseq', '소식지가');
});
