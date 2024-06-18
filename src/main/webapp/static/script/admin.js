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

// 체크박스 여러개 선택하는 함수
// 체크한 라인의 원하는 selector 값을 추출하여 배열에 담고 String으로 변환하여
// hidden input에 담아 전달되는 url으로 form submit
function selectCheckedList(url, selector) {
    const selectedMembers = [];
    const checkBoxes = document.querySelectorAll('.check-box:checked');

    checkBoxes.forEach(checkBox => {
        const memberRow = checkBox.closest('ul');
        const memberId = memberRow.querySelector(selector).textContent.trim();
        selectedMembers.push(memberId);
    });

    if (selectedMembers.length === 0) {
        alert('한 개 이상 선택하세요');
        return;
    }

    // 선택된 회원 ID들을 숨겨진 input 필드에 저장합니다.
    const memberIdsInput = document.adminForm.memberIds;
    memberIdsInput.value = selectedMembers.join(',');

    // 폼을 제출합니다.
    document.adminForm.action = url;
    document.adminForm.submit();
}

function adminRightsAction(act) {
    document.adminForm.action.value = act;
    selectCheckedList("museum.do?command=grantAdminRights", 'li:nth-child(2)>span:first-child');
}

function go_check(event) {
    // 클릭한 요소가 체크박스가 아닌 경우에만 체크박스를 체크/체크 해제
    if (!event.target.classList.contains('check-box') && !event.target.classList.contains('view-link')) {
        let checkbox = event.currentTarget.querySelector('.check-box');
        checkbox.checked = !checkbox.checked;
    }
}

function updatePost(command, selector) {
    const checkBoxes = document.querySelectorAll('.check-box:checked');

    if (checkBoxes.length > 1) {
        alert("한 개만 선택하세요");
        return;
    }
    const memberRow = checkBoxes[0].closest('ul');
    const seq = memberRow.querySelector(selector).textContent.trim();

    location.href = `museum.do?command=${command}${seq}`;
}

function deletePost(command, selector) {
    let ans = confirm("정말 삭제하시겠습니까?");
    if (ans) {
        selectCheckedList("museum.do?command=" + command, selector);
    } else {
        return;
    }
}

function searchAdmin(command) {
    const form = document.adminForm;
    const searchWord = form.searchWord.value;
    if (!searchWord || searchWord === "") {
        alert("검색어를 입력하세요");
    } else {
        location.href = `museum.do?command=${command}&searchWord=${searchWord}`;
    }
}

function displayFilter(command, param) {
    const form = document.adminForm;
    const selectValue = form.selectDisplayFilter.value;
    let url = `museum.do?command=${command}`;
    if (selectValue === "state") {
        location.href = url;
    } else if (selectValue === "Y") {
        location.href = `${url}&${param}=Y`;
    } else {
        location.href = `${url}&${param}=N`;
    }
}

function categoryFilter(command, param, event) {
    const form = document.adminForm;
    const selectValue = form.selectCategoryFilter.value;
    let url = `museum.do?command=${command}`;
    if (selectValue === "state") {
        location.href = url;
    } else if (selectValue === event.target.value) {
        location.href = `${url}&${param}=${event.target.value}`;
    }
}

function previewImg(id) {
    const preview = document.getElementById(id);
    preview.classList.toggle("hidden");
}