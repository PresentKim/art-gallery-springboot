window.addEventListener('load', function () {
    // 작가 관련 이벤트 핸들러 등록
    let artworkArtistBackup = '';
    const $artistInput = document.getElementById('artist');
    const $artistUnknownCheckbox = document.getElementById('unknown-artist');
    $artistUnknownCheckbox.addEventListener('input', () => {
        if ($artistUnknownCheckbox.checked) {
            artworkArtistBackup = $artistInput.value;
            $artistInput.value = '작자미상';
        } else {
            $artistInput.value = artworkArtistBackup;
        }
    });
    const syncArtistInput = () => $artistUnknownCheckbox.checked = ($artistInput.value === '작자미상');
    $artistInput.addEventListener('input', syncArtistInput);

    // 연도 관련 이벤트 핸들러 등록
    let artworkYearBackup = '';
    const $yearInput = document.getElementById('year');
    const $yearUnknownCheckbox = document.getElementById('unknown-year');
    $yearUnknownCheckbox.addEventListener('change', () => {
        if ($yearUnknownCheckbox.checked) {
            artworkYearBackup = $yearInput.value;
            $yearInput.value = '연도미상';
        } else {
            $yearInput.value = artworkYearBackup;
        }
    });
    const syncYearInput = () => $yearUnknownCheckbox.checked = ($yearInput.value === '연도미상');
    $yearInput.addEventListener('input', syncYearInput);

    // 이미지 파일 선택 이벤트 핸들러 등록
    const $imageInput = document.getElementById('image-file');
    const $imagePreview = document.getElementById('image-preview');
    $imageInput.addEventListener('change', () => {
        let reader = new FileReader();
        reader.onload = () => $imagePreview.src = reader.result;
        reader.readAsDataURL($imageInput.files[0]);
    });

    // 폼 등록 이벤트 핸들러 등록
    const $form = document.getElementById('artwork-form');
    registerDefaultSubmitAjaxHandler($form, '/api/artworks', '/artwork/', 'aseq', '예술품이');
});
