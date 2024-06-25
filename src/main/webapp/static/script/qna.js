function qnaAuth(qseq, mode, pwd) {
    ajax(
        '/qna/authorize',
        {qseq, mode, pwd},
        defaultAjaxHandler.then(function () {
            var pwd = prompt(qseq + "번 QnA 글의 비밀번호를 입력하세요:");
            if (pwd !== null && pwd !== undefined && pwd !== "") {
                qnaAuth(qseq, mode, pwd);
            } else {
                alert("비밀번호 입력이 취소되었습니다.");
            }
            return false;
        }, 401)
    );
}