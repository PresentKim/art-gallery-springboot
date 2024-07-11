var artworkArtistBackup = "";
var artworkYearBackup = "";

/**
 * "작자미상" 체크박스의 체크 변경 이벤트 핸들러
 * 체크된 경우 작가명 입력란의 값을 "작자미상"으로 설정하고, 체크 해제된 경우 이전 값으로 설정한다.
 */
function onUnknownArtistChange() {
    let artistInput = document.getElementById("artist");

    if (document.getElementById("unknown-artist").checked) {
        artworkArtistBackup = artistInput.value;
        artistInput.value = "작자미상";
    } else {
        artistInput.value = artworkArtistBackup;
    }
}

/**
 * "연도미상" 체크박스의 체크 변경 이벤트 핸들러
 * 체크된 경우 제작연도 입력란의 값을 "연도미상"으로 설정하고, 체크 해제된 경우 이전 값으로 설정한다.
 */
function onUnknownYearChange() {
    let yearInput = document.getElementById("year");

    if (document.getElementById("unknown-year").checked) {
        artworkYearBackup = yearInput.value;
        yearInput.value = "연도미상";
    } else {
        yearInput.value = artworkYearBackup;
    }
}

/**
 * 주어진 ID의 체크박스를 체크 해제하는 함수
 *
 * @param elementId
 */
function uncheck(elementId) {
    console.log(document.getElementById(elementId));
    document.getElementById(elementId).checked = false;
}

function updatePreviewImage() {
    let reader = new FileReader(); // 파일을 읽을 FileReader 객체 생성
    reader.onload = function () {
        // 파일의 데이터를 img 태그의 src에 설정
        document.getElementById("image-preview").src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]); // 선택한 파일을 읽어들임
}

function submitCreateArtwork($form) {
    handleAxiosFinally(axios({
        url: '/api/artworks',
        method: 'POST',
        data: new FormData($form),
        headers: {'Content-Type': 'multipart/form-data'}
    }));
}

function submitUpdateArtwork($form, aseq) {
    handleAxiosFinally(axios({
        url: `/api/artworks/${aseq}`,
        method: 'PUT',
        data: new FormData($form),
        headers: {'Content-Type': 'multipart/form-data'}
    }));
}
