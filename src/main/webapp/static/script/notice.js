function noticeCheck() {
    if (document.insertNotice.pwd.value === "") {
        alert("비밀번호를 입력하세요");
        document.insertNotice.pwd.focus();
        return false;
    } else if (document.insertNotice.email.value === "") {
        alert("이메일을 입력하세요");
        document.insertNotice.email.focus();
        return false;
    } else if (document.insertNotice.title.value === "") {
        alert("제목을 입력하세요");
        document.insertNotice.title.focus();
        return false;
    } else if (document.insertNotice.content.value === "") {
        alert("내용을 입력하세요");
        document.insertNotice.content.focus();
        return false;
    } else {
        return true;
    }

}

function updateNoticeCheck() {
    if (document.insertNotice.pwd.value === "") {
        alert("비밀번호를 입력하세요");
        document.insertNotice.pwd.focus();
        return false;
    } else if (document.insertNotice.email.value === "") {
        alert("이메일을 입력하세요");
        document.insertNotice.email.focus();
        return false;
    } else if (document.insertNotice.title.value === "") {
        alert("제목을 입력하세요");
        document.insertNotice.title.focus();
        return false;
    } else if (document.insertNotice.content.value === "") {
        alert("내용을 입력하세요");
        document.insertNotice.content.focus();
        return false;
    } else {
        return true;
    }
}


function deleteNotice(pwd, nseq) {
    var inputpwd = prompt('삭제에 필요한 비밀번호를 입력하세요', '');
    if (pwd !== inputpwd) {
        alert('비밀번호가 일치하지 않습니다');
    } else {
        location.href = 'museum.do?command=deleteNotice&nseq=' + nseq;
    }
}




