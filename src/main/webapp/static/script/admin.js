function resetDatabase() {
    if (!confirm('데이터베이스를 초기화하시겠습니까?')) {
        return;
    }

    axios.post('/api/admin/reset')
        .then(() => {
            alert('데이터베이스를 초기화했습니다.');
            location.reload();
        })
        .catch(() => alert('데이터베이스 초기화에 실패했습니다.'));
}

function checkAll() {
    // select-all-box의 체크 상태를 가져옵니다.
    const selectAllBox = document.querySelector('.select-all-box');
    const isChecked = selectAllBox.checked;

    // 모든 체크박스를 가져옵니다.
    const checkBoxes = document.querySelectorAll('.admin-list-main input[type="checkbox"]');

    // 모든 체크박스의 체크 상태를 select-all-box의 상태와 일치시킵니다.
    checkBoxes.forEach(checkBox => {
        checkBox.checked = isChecked;
    });
}

function checkChildCheckbox($ul) {
    const $checkbox = $ul.querySelector('.admin-list-main input[type="checkbox"]');
    if (!$checkbox) {
        alert('체크박스를 찾을 수 없습니다.');
        return;
    }

    $checkbox.checked = !$checkbox.checked;
}

function previewImage($img) {
    const PREVIEW_ID = 'image-full-preview';
    if (document.getElementById(PREVIEW_ID) !== null) {
        return;
    }

    const $preview = document.createElement('div');
    $preview.id = PREVIEW_ID;
    $preview.onclick = function () {
        document.body.removeChild($preview);
    };

    const $previewImage = document.createElement('img');
    $previewImage.src = $img.src;
    $previewImage.alt = '미리보기 이미지';

    $preview.appendChild($previewImage);
    document.body.appendChild($preview);
}

function getSelected() {
    return document.querySelectorAll('ul.admin-list-main:has(input[type="checkbox"]:checked)');
}

function deleteSelected(apiPath) {
    for (const $ul of getSelected()) {
        $ul.classList.add('delete-target');
        axios.delete(apiPath + $ul.dataset.seq)
            .then(() => {
                $ul.classList.add('deleted');
                return new Promise(resolve => setTimeout(resolve, 500));
            })
            .then(() => $ul.remove())
            .catch(error => {
                $ul.classList.remove('deleted');
                $ul.classList.remove('delete-target');
                defaultAjaxHandler(error.response);
            });
    }
}

function updateSelected(urlPath) {
    const targets = getSelected();
    if (targets.length === 0) {
        alert('선택된 요소가 없습니다.');
        return;
    } else if (targets.length > 1) {
        alert('하나의 요소만 선택해주세요.');
        return;
    }

    location.href = urlPath + targets[0].dataset.seq;
}
