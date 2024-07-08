async function qnaAuth(qseq, mode, pwd) {
    try {
        const response = await axios({
            url: '/qna/authorize',
            method: 'POST',
            headers: {'content-type': 'application/x-www-form-urlencoded'},
            data: {qseq, mode, pwd},
        });

        defaultAjaxHandler(response);
    } catch (error) {
        // 결과 상태 코드가 401이 아니면 메시지 출력
        if (error.response.status !== 401) {
            defaultAjaxHandler(error.response);
            return;
        }

        // 비밀번호가 입력되지 않았거나, 취소되었을 경우 중단
        pwd = prompt(`${qseq}번 QnA 글의 비밀번호를 입력하세요:`);
        if (pwd === null || pwd === undefined || pwd === "") {
            alert("비밀번호 입력이 취소되었습니다.");
            return;
        }

        // 비밀번호를 입력하고 다시 인증 시도
        await qnaAuth(qseq, mode, pwd);
    }
}