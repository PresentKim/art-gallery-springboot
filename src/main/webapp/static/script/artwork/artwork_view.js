async function updateArtworkDisplay($button, aseq) {
    try {
        const prevDisplay = $button.textContent.includes('비');
        const response = await axios.put(`/api/artworks/${aseq}/display`, {
            display: !prevDisplay
        });

        // element의 텍스트를 '비'를 포함하고 있으면 '공개로 전환', 그렇지 않으면 '비공개로 전환'으로 설정
        $button.textContent = prevDisplay ? '공개로 전환' : '비공개로 전환';
        alert(`예술품을 ${$button.textContent}했습니다`);
    } catch (error) {
        defaultAjaxHandler(error.response);
    }
}

async function deleteArtwork(aseq) {
    try {
        await axios.delete(`/api/artworks/${aseq}`);
        alert("예술품이 삭제되었습니다");
        location.href = '/artwork';
    } catch (error) {
        defaultAjaxHandler(error.response);
    }
}