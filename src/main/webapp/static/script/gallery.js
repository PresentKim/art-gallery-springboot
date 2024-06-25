function updatePreviewImage() {
    let reader = new FileReader(); // 파일을 읽을 FileReader 객체 생성
    reader.onload = function () {
        // 파일의 데이터를 img 태그의 src에 설정
        document.getElementById("image-preview").src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]); // 선택한 파일을 읽어들임
}
