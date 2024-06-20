function getDisplayAjaxHandler(element) {
    return defaultAjaxHandler.then(function () {
        element.textContent = element.textContent.includes('비') ? '공개로 전환' : '비공개로 전환';
    }, 200);
}