function toggleArtworkDisplay(element, aseq) {
    ajax('/artwork/toggleArtworkDisplay', {aseq}, 'POST')
        .then(defaultAjaxHandler)
        .then(() => {
            element.textContent = element.textContent.includes('비') ? '공개로 전환' : '비공개로 전환';
        });
}