var lastAjaxRequest = null;

/**
 * AJAX 요청을 전송하는 함수
 */
function ajax(requestUrl, requestBody, ajaxHandler) {
	// 마지막 요청 취소
	if (lastAjaxRequest) {
		lastAjaxRequest.abort();
	}

	// ajax(requestBody, ajaxHandler) 형식의 호출을 지원
	if (typeof requestUrl === 'object') {
		ajaxHandler = requestBody;
		requestBody = requestUrl;
		requestUrl = null;
	}

	// requestUrl이 유효하지 않으면 `location.pathname`를 사용
	if (!requestUrl) {
		requestUrl = location.pathname;
	}

	// ajaxHandler이 유효하지 않으면 `defaultAjaxHandler`를 사용
	if (!ajaxHandler) {
		ajaxHandler = defaultAjaxHandler;
	}

	// requestBody가 유효하고, requestUrl에 '?'가 포함되지 않으면 뒤에 '?' 추가
	if (requestBody && !requestUrl.includes('?')) {
		requestUrl += '?';
	}

	// requestBody가 리터럴 객체인 경우 문자열로 변환
	if (typeof requestBody === 'object') {
		requestBody = Object
			.entries(requestBody)
			.filter(([, value]) => value !== null && value !== undefined)
			.map(([key, value]) => `${key}=${value}`).join('&');
	}

	// requestUrl로 POST 요청 객체 생성
	const xhr = new XMLHttpRequest();
	xhr.open("POST", requestUrl, true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

	// POST 응답 이벤트 핸들러 등록
	xhr.onreadystatechange = function() {
		// 완료 전까지 무시
		if (xhr.readyState !== 4) {
			return;
		}

		// abort()로 취소된 요청은 무시
		if (xhr.status === 0) {
			return;
		}

		// 응답 완료 시 ajaxHandler() 실행
		ajaxHandler(xhr.status, JSON.parse(xhr.responseText));
	};

	// requestBody로 POST 요청 전송
	xhr.send(requestBody);
	lastAjaxRequest = xhr;
}

/**
 * HTML Form 요소를 AJAX 요청으로 전송하는 함수
 * onSubmit 이벤트 핸들러로 사용
 */
function ajaxSubmit(event) {
	// 기본 이벤트 취소
	event.preventDefault();

	var form = event.target;

	// form 요소 유효성 검사
	for (var input of form.elements) {
		if (input.required && input.value.trim() == "") {
			alert(getInputName(input) + "을(를) 입력해 주세요.");
			input.focus();
			return;
		}

		var requireEquals = input.dataset.requireEquals;
		if (requireEquals) {
			var target = form.elements[requireEquals];
			if (target && input.value != target.value) {
				alert(getInputName(target) + "와(과) " + getInputName(input) + "이(가) 일치하지 않습니다.");
				input.focus();
				return;
			}
		}
	}

	// form 요소로부터 requestBody 객체 생성
	const requestBody = {};
	for (var input of form.elements) {
		if (input.name) {
			requestBody[input.name] = input.value;
		}
	}

	// submitter 버튼의 formaction 속성 또는 form 요소의 action 속성으로 요청 URL 결정
	var action = event.submitter.getAttribute("formaction") || form.action;

	// ajax 요청 전송
	ajax(action, requestBody);
}

var defaultAjaxHandler = function(status, response) {
	var url = response.url;
	var message = response.message;
	switch (status) {
		case 200: // OK
			// 200 상태 코드는 url 값이 존재하면 메시지를 생략
			if (url) {
				location.href = url;
			} else if (message) {
				alert(message);
			}
			return;

		case 201: // Created
		case 202: // Accepted
			break;

		case 400: // Bad Request
			message = message || "잘못된 요청입니다.";
			break;

		case 401: // Unauthorized
			message = message || "로그인이 필요합니다";
			break;

		case 403: // Forbidden
			message = message || "접근 권한이 없습니다";
			break;

		case 204: // No Content
		case 404: // Not Found
			message = message || "요청한 페이지를 찾을 수 없습니다";
			break;

		case 500: // Internal Server Error
			message = message || "서버 오류가 발생했습니다.";
			break;

		default:
			message = message || "알 수 없는 오류가 발생했습니다.";
			break;
	}
	if (message) {
		alert(message);
	}
	if (url) {
		location.href = url;
	}
};

function getInputName(input) {
	return (input.labels[0] && input.labels[0].innerText) || input.name || input.id;
}