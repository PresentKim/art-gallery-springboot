function submitIdCheck(id) {
    ajax('/member/idCheck', {id}, defaultAjaxHandler.then(function (status) {
        // 결과 상태 코드가 200이면 id 필드에 id를 설정하고, 그렇지 않으면 빈 문자열을 설정
        // 메시지 출력은 defaultAjaxHandler 에서 처리
        document.getElementById('idCheck').value = status === 200 ? id : '';
    }));
}
