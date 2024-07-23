async function grantMembers() {
    for (const $ul of getSelected()) {
        const id = $ul.getAttribute('data-seq');
        try {
            await axios({
                url: `/api/members/${id}/grant`,
                method: 'PUT',
            });

            toggleAdminClass($ul, true);
        } catch (error) {
            alert('회원 권한 부여에 실패했습니다.');
        }
    }
    alert('선택한 회원들을 관리자로 변경했습니다.');
}

async function revokeMembers() {
    for (const $ul of getSelected()) {
        const id = $ul.getAttribute('data-seq');
        try {
            await axios({
                url: `/api/members/${id}/revoke`,
                method: 'PUT',
            });

            toggleAdminClass($ul, false);
        } catch (error) {
            alert('회원 권한 변경에 실패했습니다.');
        }
    }
    alert('선택한 회원들을 일반 회원으로 변경했습니다.');
}

function toggleAdminClass($ul, isAdmin) {
    const $idSpan = $ul.querySelector('.id');
    $idSpan.classList.toggle('admin-id', isAdmin);
}