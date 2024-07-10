async function toggleArtworkDisplay(element, aseq) {
    try {
        const response = await axios.put(`/api/artworks/${aseq}/toggleDisplay`);

        // element의 텍스트를 '비'를 포함하고 있으면 '공개로 전환', 그렇지 않으면 '비공개로 전환'으로 설정
        element.textContent = element.textContent.includes('비') ? '공개로 전환' : '비공개로 전환';
        defaultAjaxHandler(response);
    } catch (error) {
        defaultAjaxHandler(error.response);
    }
}
