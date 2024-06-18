function qnaPwdCheck(qseq, mode, pwd) {
	ajax({ command: 'qnaPwdCheck', qseq, mode, pwd }, function(status, response) {
		if (status === 401) { // Unauthorized
			var pwd = prompt(qseq + "번 QnA 글의 비밀번호를 입력하세요:");
			if (pwd !== null && pwd !== undefined && pwd !== "") {
				qnaPwdCheck(qseq, mode, pwd);
			} else {
				alert("비밀번호 입력이 취소되었습니다.");
			}
			return;
		}

		defaultAjaxHandler(status, response);
	});
}