/**
 * AXIOS 요청이 시작할 때, 응답이 완료될 때 body 요소에 `data-loading` 속성을 추가/제거하는 인터셉터
 *
 * @see https://axios-http.com/docs/interceptors
 */

// 요청 시작 시 `data-loading` 속성을 추가
axios.interceptors.request.use(config => {
    document.body.dataset.loading = 'true';
    return config;
}, error => Promise.reject(error));

// 응답 완료 시 `data-loading` 속성을 제거
axios.interceptors.response.use(response => {
    delete document.body.dataset.loading;
    return response;
}, error => Promise.reject(error));
