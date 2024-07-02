var lastAjaxRequest = null;

/**
 * XMLHttpRequest 객체를 사용하여 요청을 전송하는 함수
 *
 * @param {string} url - 요청 URL
 * @param {FormData|Object} [body] - 요청 바디
 * @param {Object} [option] - 옵션 객체
 * @param {string} [option.method] - 요청 메소드, 기본값은 'POST'
 * @param {string} [option.accept] - Accept 헤더 값, 기본값은 'application/json'
 * @param {string} [option.contentType] - Content-Type 헤더 값
 *
 * @returns {Promise<{status: number, body: Object}>} - 응답 객체를 반환하는 Promise 객체
 */
function sendHttpRequest(url, body = {}, option = {}) {
    // 마지막 요청 취소
    if (lastAjaxRequest) {
        lastAjaxRequest.abort();
    }

    return new Promise((resolve, reject) => {
        // body 값이 리터럴 객체인 경우 문자열로 변환
        // `application/json` 타입은 컨트롤러에서 @RequestBody 로 받아야하기 때문에 복잡해짐.
        // 때문에 JSON 문자열 대신 쿼리 문자열로 전송
        if (!(body instanceof FormData)) {
            body = Object
                .entries(body)
                .filter(([, value]) => value !== null && value !== undefined)
                .map(([key, value]) => `${key}=${value}`).join('&');
        }

        // 요청 메소드가 GET 인 경우 쿼리스트링으로 변환
        const method = option.method || 'POST';
        if (method === 'GET') {
            url += '?' + body;
            body = null;
        }

        // XMLHttpRequest 객체 생성
        const xhr = new XMLHttpRequest();

        // XHR 요청 설정
        xhr.open(method, url, true);
        xhr.setRequestHeader('Accept', option.accept || 'application/json');
        if (option.contentType) {
            xhr.setRequestHeader('Content-Type', option.contentType);
        }

        // XHR 응답 이벤트 핸들러 등록
        xhr.onload = function () {
            // abort()로 취소된 요청은 무시
            if (xhr.status === 0) {
                reject();
            }

            resolve({status: xhr.status, body: JSON.parse(xhr.responseText)});
        };

        // XHR 에러 이벤트 핸들러 등록
        xhr.onerror = reject;

        // XHR 요청 전송
        xhr.send(body);
    });
}

/**
 * AJAX 요청을 전송하는 함수 (body 값이 객체인 경우 쿼리스트링으로 변환)
 *
 * @param {string} url - 요청 URL
 * @param {Object} [body] - 요청 바디 객체
 * @param {string} [method] - 요청 메소드, 기본값 'POST'
 *
 * @returns {Promise<{status: number, body: Object}>} - 응답 객체를 반환하는 Promise 객체
 */
function ajax(url, body = {}, method = 'POST') {
    // 요청 전송
    return sendHttpRequest(url, body, {
        method: method,
        accept: 'application/json',
        contentType: 'application/x-www-form-urlencoded'
    });
}

/**
 * Form 전송 시 요소 유효성 검사 후 AJAX 요청을 처리하는 함수
 * onsubmit 이벤트 핸들러로 사용 (사용 시 이벤트가 취소됨)
 *
 * @param {SubmitEvent} event - 이벤트 객체
 * @param {function} [ajaxHandler] - AJAX 응답 처리 함수
 */
function ajaxSubmit(event, ajaxHandler = defaultAjaxHandler) {
    const form = event.target;

    // form 요소 유효성 검사
    for (const input of form.elements) {
        if (input.required && input.value.trim() === "") {
            alert(getInputName(input) + "을(를) 입력해 주세요.");
            input.focus();

            // 폼 등록 이벤트 취소
            event.preventDefault();
            return;
        }

        // data-require-equals 속성이 있는 경우 값 비교
        const requireEquals = input.dataset.requireEquals;
        if (requireEquals) {
            // 대상 요소가 존재하고 값이 다른 경우 경고 메시지 출력
            const target = form.elements[requireEquals];
            if (target && input.value !== target.value) {
                // data-require-message 속성이 있는 경우 메시지 출력
                alert(input.dataset.requireMessage || getInputName(target) + "와(과) " + getInputName(input) + "이(가) 일치하지 않습니다.");
                input.focus();

                // 폼 등록 이벤트 취소
                event.preventDefault();
                return;
            }
        }
    }

    // 요청 메소드가 GET 인 경우 그대로 전송
    const method = event.submitter.getAttribute("formmethod") || form.method;
    if (method.toUpperCase() === "GET") {
        return;
    }

    // 폼 등록 이벤트 취소
    event.preventDefault();

    // submitter 버튼의 formaction 속성 또는 form 요소의 action 속성으로 요청 URL 결정
    const url = event.submitter.getAttribute("formaction") || form.action;

    // FormData 객체 생성
    const body = new FormData(form);

    // 요청 전송
    sendHttpRequest(url, body, {
        method: method,
        accept: 'application/json',
    }).then(ajaxHandler);
}

/**
 * 기본 AJAX 응답 처리 함수
 * - 응답 메시지가 있으면 alert()로 출력
 * - 응답 데이터에 URL 값이 있으면 해당 주소로 이동
 *
 * .then() 메서드로 체이닝하여 응답 처리 함수를 확장할 수 있음
 *
 * @param {number} response.status - 응답 상태 코드
 * @param {Object} response.body - 응답 객체
 *
 * @returns {{status: number, body: Object}|undefined} - 응답 객체를 반환
 */
function defaultAjaxHandler(response) {
    if (response === undefined || response === null) {
        return;
    }

    const body = response.body;

    // 기본 응답 처리
    if (body.message) alert(body.message);
    if (body.url) {
        if (body.url === ':back') {
            history.back();
        } else if (body.url === ':reload') {
            location.reload();
        } else {
            location.href = body.url;
        }
    }

    return response;
}

function getInputName(input) {
    return (input.labels && input.labels[0] && input.labels[0].innerText) || input.name || input.id;
}