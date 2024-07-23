/**
 * 기본 AJAX 응답 처리 함수
 * - 응답 메시지가 있으면 alert()로 출력
 * - 응답 데이터에 URL 값이 있으면 해당 주소로 이동
 *
 * .then() 메서드로 체이닝하여 응답 처리 함수를 확장할 수 있음
 *
 * @param {number} response.status - 응답 상태 코드
 * @param {Object} response.data - 응답 객체
 *
 * @returns {{status: number, data: Object}|undefined} - 응답 객체를 반환
 */
function defaultAjaxHandler(response) {
    if (response === undefined || response === null) {
        return;
    }

    const data = response.data;

    // 기본 응답 처리
    if (data.message) alert(data.message);
    if (data.url) {
        if (data.url === ':back') {
            history.back();
        } else if (data.url === ':reload') {
            location.reload();
        } else {
            location.href = data.url;
        }
    }

    return response;
}


/**
 * AJAX 요청을 전송하는 함수 (body 값이 객체인 경우 쿼리스트링으로 변환)
 *
 * @param {string} url - 요청 URL
 * @param {Object} [data] - 요청 바디 객체
 * @param {string} [method] - 요청 메소드, 기본값 'POST'
 */
function ajax(url, data = {}, method = 'POST') {
    handleAxiosFinally(axios({
        url,
        method,
        data,
        headers: {'content-type': 'application/x-www-form-urlencoded'}
    }))
}

/**
 * Axios 요청에 대한 응답 처리 함수
 * - Axios 요청 성공 시 {@link defaultAjaxHandler}로 응답 처리
 * - Axios 요청 실패 시 {@link defaultAjaxHandler}로 응답 처리
 */
function handleAxiosFinally(axiosPromise) {
    axiosPromise
        .then(defaultAjaxHandler)
        .catch(error => defaultAjaxHandler(error.response));
}

// 폼 요소에 이벤트 핸들러 등록
window.addEventListener('load', function () {
    function validateForm($form) {
        function getInputName(input) {
            return (input.labels && input.labels[0] && input.labels[0].innerText) || input.name || input.id;
        }

        // 브라우저 기본 유효성 검사
        if (!$form.checkValidity()) {
            const $input = $form.querySelector(':invalid');
            if ($input) {
                alert(getInputName($input) + '을(를) 확인해 주세요.');
                $input.focus();
            }
            return false;
        }

        // data-require-equals 속성 값이 있는 요소 유효성 검사
        for (const $input of $form.querySelectorAll('[data-require-equals]')) {
            const requireEquals = $input.dataset.requireEquals;
            // 대상 요소가 존재하고 값이 다른 경우 경고 메시지 출력
            const $target = $form.elements[requireEquals];
            if ($target && $input.value !== $target.value) {
                // data-require-message 속성이 있는 경우 메시지 출력
                alert($input.dataset.requireMessage || getInputName($target) + '와(과) ' + getInputName($input) + '이(가) 일치하지 않습니다.');
                $input.focus();

                return false;
            }
        }

        return true;
    }

    for (const form of document.forms) {
        form.addEventListener('submit', (event) => {
            // form 요소 유효성 검사
            const $form = event.target;
            if (!validateForm($form)) {
                event.preventDefault();
                return;
            }

            // 요청 메소드가 GET 인 경우 그대로 전송
            const method = event.submitter.getAttribute('formmethod') || $form.method;
            if (method.toUpperCase() === 'GET') {
                return;
            }

            // 폼 등록 이벤트 취소
            event.preventDefault();

            $form.dispatchEvent(new Event('submit-ajax'));
        });
    }
});

function registerImagePreviewHandler() {
    const $imageInput = document.getElementById('image-file');
    const $imagePreview = document.getElementById('image-preview');
    $imageInput.addEventListener('change', () => {
        let reader = new FileReader();
        reader.onload = () => $imagePreview.src = reader.result;
        reader.readAsDataURL($imageInput.files[0]);
    });
}

function registerDefaultSubmitAjaxHandler($form, apiUrl, redirectUrl, dataKey, prefixMessage) {
    $form.addEventListener('submit-ajax', () => {
        const axiosOptions = getAxiosOptions($form);

        if ($form.getAttribute(`data-${dataKey}`)) {
            const data = $form.getAttribute(`data-${dataKey}`);
            axios({
                url: `${apiUrl}/${data}`,
                method: 'PUT',
                ...axiosOptions
            })
                .then(() => {
                    alert(`${prefixMessage} 수정되었습니다`);
                    location.href = redirectUrl + data;
                })
                .catch(error => defaultAjaxHandler(error.response));
        } else {
            axios({
                url: apiUrl,
                method: 'POST',
                ...axiosOptions
            })
                .then((json) => {
                    alert(`${prefixMessage} 등록되었습니다`);
                    location.href = redirectUrl + json.data[dataKey];
                })
                .catch(error => defaultAjaxHandler(error.response));
        }
    });
}

function getAxiosOptions($form) {
    return {
        data: new FormData($form),
        headers: {'Content-Type': 'multipart/form-data'}
    };
}