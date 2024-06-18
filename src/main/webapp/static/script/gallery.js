function go_save() {
    let form = document.galleryForm;
    if (form.title.value === "") {
        alert("갤러리명을 입력하세요");
        form.title.focus();
    } else if (form.content.value === "") {
        alert("갤러리 설명을 입력하세요");
        form.content.focus();
    } else if (form.image.value === "") {
        alert("이미지를 첨부해주세요");
        form.image.focus();
    } else {
        form.submit();
    }
}

function go_update() {
    let form = document.galleryForm;
    if (form.title.value === "") {
        alert("갤러리명을 입력하세요");
        form.title.focus();
    } else if (form.content.value === "") {
        alert("갤러리 설명을 입력하세요");
        form.content.focus();
    } else if (form.image.value === "") {
        confirm("이미지가 변경되지 않았습니다. 이미지 수정없이 등록하시겠습니까?") ?
            form.submit() : form.image.focus();
    } else {
        form.submit();
    }
}

function go_search() {
    let inputText = document.searchForm.searchWord.value;
    if (inputText === "") {
        alert("검색어를 입력하세요");
        return false;
    } else {
        return true;
    }
}

function go_gallery() {
    let form = document.galleryList;
    if (form.id.value === "") {
        alert("로그인이 필요합니다 로그인 후 이용해주세요");
        form.id.focus();
    } else {
        form.action = 'museum.do?command=galleryWrite';
        form.submit();
    }
}

function go_delete(mseq) {
    confirm("삭제하시겠습니까?") ?
        location.href = "museum.do?command=galleryDelete&mseq=" + mseq : "";
}

function previewImage(input) {
    let reader = new FileReader(); // 파일을 읽을 FileReader 객체 생성
    let form = document.galleryForm;
    reader.onload = function () {
        let output = form.uploadedImage;
        output.src = reader.result; // 파일의 데이터를 img 태그의 src에 설정
    };
    reader.readAsDataURL(event.target.files[0]); // 선택한 파일을 읽어들임
}