async function updateArtworkDisplay($button) {
    try {
        const prevDisplay = $button.textContent.includes('비');
        await axios.put(`/api/artworks/${getAseq()}/display`, {
            display: !prevDisplay
        });

        // element의 텍스트를 '비'를 포함하고 있으면 '공개로 전환', 그렇지 않으면 '비공개로 전환'으로 설정
        $button.textContent = prevDisplay ? '공개로 전환' : '비공개로 전환';
        alert(`예술품을 ${$button.textContent}했습니다`);
    } catch (error) {
        defaultAjaxHandler(error.response);
    }
}

async function deleteArtwork() {
    try {
        await axios.delete(`/api/artworks/${getAseq()}`);
        alert('예술품이 삭제되었습니다');
        location.href = '/artwork';
    } catch (error) {
        defaultAjaxHandler(error.response);
    }
}

async function toggleFavorite() {
    try {
        const isFavorite = await checkFavorite();
        if (isFavorite) {
            await axios.delete(`/api/favorites/${getAseq()}`);
            alert('관심 예술품에서 제거되었습니다');
        } else {
            await axios.post(`/api/favorites/${getAseq()}`);
            alert('관심 예술품에 등록되었습니다');
        }
        updateFavoriteButton(!(isFavorite));
    } catch (error) {
        defaultAjaxHandler(error.response);
    }
}

async function checkFavorite() {
    const response = await axios.get(`/api/favorites/${getAseq()}`);
    return response.status === 200;
}

function updateFavoriteButton(isFavorite) {
    const $toggleFavoriteBtn = document.getElementById('toggle-favorite-btn');
    $toggleFavoriteBtn.textContent = isFavorite ? '관심해제' : '관심등록';
}

function getAseq() {
    return location.pathname.split('/').pop();
}

window.addEventListener('load', async function () {
    if (document.getElementById('toggle-favorite-btn')) {
        updateFavoriteButton(await checkFavorite());
    }
});