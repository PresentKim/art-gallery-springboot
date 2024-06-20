var lastAjaxRequest = null;

/**
 * XMLHttpRequest 객체를 사용하여 POST 요청을 전송하는 함수
 *
 * @param xhr - XMLHttpRequest 객체
 * @param requestBody - POST 요청 바디
 * @param ajaxHandler - 응답 처리 함수
 */
function sendHttpRequest(xhr, requestBody, ajaxHandler) {
    // 마지막 요청 취소 후 갱신
    if (lastAjaxRequest) {
        lastAjaxRequest.abort();
    }
    lastAjaxRequest = xhr;

    // ajaxHandler 값이 유효하지 않으면 `defaultAjaxHandler`를 사용
    if (!ajaxHandler) {
        ajaxHandler = defaultAjaxHandler;
    }

    // POST 응답 이벤트 핸들러 등록
    xhr.onload = function () {
        // abort()로 취소된 요청은 무시
        if (xhr.status === 0) {
            return;
        }

        // 응답 완료 시 ajaxHandler() 실행
        ajaxHandler(xhr.status, JSON.parse(xhr.responseText));
    };

    // requestBody 그대로 POST 요청 전송
    xhr.send(requestBody);
}

/**
 * AJAX 요청을 전송하는 함수 (requestBody 값이 객체인 경우 쿼리스트링으로 변환)
 *
 * @param {string} requestUrl - 요청 URL
 * @param {Object} requestBody - 요청 바디 객체
 * @param {function} ajaxHandler - 응답 처리 함수
 */
function ajax(requestUrl, requestBody, ajaxHandler) {
    // requestBody 값이 리터럴 객체인 경우 문자열로 변환
    if (typeof requestBody === 'object') {
        requestBody = Object
            .entries(requestBody)
            .filter(([, value]) => value !== null && value !== undefined)
            .map(([key, value]) => `${key}=${value}`).join('&');
    }

    // requestUrl 값으로 POST 요청 객체 생성
    const xhr = new XMLHttpRequest();
    xhr.open("POST", requestUrl, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    // POST 요청 전송
    sendHttpRequest(xhr, requestBody, ajaxHandler);
}

/**
 * Form 전송 시 요소 유효성 검사 후 AJAX 요청을 처리하는 함수
 * onsubmit 이벤트 핸들러로 사용 (사용 시 이벤트가 취소됨)
 */
function ajaxSubmit(event, ajaxHandler) {
    // 기본 이벤트 취소
    event.preventDefault();

    var form = event.target;

    // form 요소 유효성 검사
    for (var input of form.elements) {
        if (input.required && input.value.trim() === "") {
            alert(getInputName(input) + "을(를) 입력해 주세요.");
            input.focus();
            return;
        }

        var requireEquals = input.dataset.requireEquals;
        if (requireEquals) {
            var target = form.elements[requireEquals];
            if (target && input.value !== target.value) {
                alert(getInputName(target) + "와(과) " + getInputName(input) + "이(가) 일치하지 않습니다.");
                input.focus();
                return;
            }
        }
    }

    // submitter 버튼의 formaction 속성 또는 form 요소의 action 속성으로 요청 URL 결정
    var requestUrl = event.submitter.getAttribute("formaction") || form.action;

    // requestUrl 값으로 POST 요청 객체 생성
    const xhr = new XMLHttpRequest();
    xhr.open("POST", requestUrl, true);

    // POST 요청 전송
    sendHttpRequest(
        xhr,
        new FormData(form),
        ajaxHandler);
}

var defaultAjaxHandler = function (status, response) {
    if (response.message) alert(response.message);
    if (response.url) location.href = response.url;
};
defaultAjaxHandler.then = function (ajaxHandler, expectStatus) {
    return function (status, response) {
        if (
            (expectStatus === undefined || expectStatus === status)
            && ajaxHandler(status, response) === false
        ) {
            // ajaxHandler()가 false 를 반환하면 기본 처리를 중단
            return;
        }
        defaultAjaxHandler(status, response);
    };
};

function getInputName(input) {
    return (input.labels[0] && input.labels[0].innerText) || input.name || input.id;
}