function go_next() {
    if (document.contractForm.agree[1].checked === true) {
        alert('회원 약관에 동의 하셔야 회원으로 가입이 가능합니다');
    } else {
        document.contractForm.submit();
    }
}
