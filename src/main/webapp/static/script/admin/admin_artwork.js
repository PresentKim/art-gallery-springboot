function updateSelectedArtwork() {
    const targets = getSelected();
    if (targets.length === 0) {
        alert('선택된 작품이 없습니다.');
        return;
    } else if (targets.length > 1) {
        alert('하나의 작품만 선택해주세요.');
        return;
    }

    const aseq = targets[0].dataset.seq;
    location.href = `/artwork/write?aseq=${aseq}`;
}
