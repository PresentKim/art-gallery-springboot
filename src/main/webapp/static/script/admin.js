function checkAll() {
    // select-all-box의 체크 상태를 가져옵니다.
    const selectAllBox = document.querySelector('.select-all-box');
    const isChecked = selectAllBox.checked;

    // 모든 체크박스를 가져옵니다.
    const checkBoxes = document.querySelectorAll('.check-box');

    // 모든 체크박스의 체크 상태를 select-all-box의 상태와 일치시킵니다.
    checkBoxes.forEach(checkBox => {
        checkBox.checked = isChecked;
    });
}

function checkChildCheckbox($ul) {
    const $checkbox = $ul.querySelector('input[type="checkbox"]');
    if (!$checkbox) {
        alert('체크박스를 찾을 수 없습니다.');
        return;
    }

    $checkbox.checked = !$checkbox.checked;
}
