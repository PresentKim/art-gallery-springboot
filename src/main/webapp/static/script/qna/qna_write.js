window.addEventListener('load', function () {
    // 폼 등록 이벤트 핸들러 등록
    const $form = document.getElementById('qna-form');
    registerDefaultSubmitAjaxHandler($form, '/api/qnas', '/qna/', 'qseq', '문의글이');
});
