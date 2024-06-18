function swiperRun(swiper) {
    // 이미지 컨테이너 요소와 이미지 요소 배열
    var imageContainer = swiper.querySelectorAll('.main-swiper_image-container')[0];
    var imageCount = imageContainer.children.length;

    // 리모콘 이미지 선택 버튼 생성
    var remoteContainer = swiper.querySelectorAll('.main-swiper_remote-container')[0];
    for (var i = 0; i < imageCount; i++) {
        remoteContainer.appendChild(document.createElement('div'));
    }

    // 리모콘 재생/일시정지 버튼 생성
    var remotePauseButton = document.createElement('div');
    remotePauseButton.classList.add('pause-button');
    remoteContainer.appendChild(remotePauseButton);

    // 이미지 선택 함수
    var imageIndex = 0;
    var timer = -1;

    function selectImage(newIndex) {
        // 이미지 인덱스 계산 (0 ~ imageCount - 1)
        imageIndex = newIndex % imageCount;

        // 이미지 이동 처리
        imageContainer.style.left = -imageIndex * 100 + '%';

        // 버튼 선택 상태 변경
        for (var i = 0; i < imageCount; i++) {
            remoteContainer.children[i].classList.remove('selected');
        }
        remoteContainer.children[imageIndex].classList.add('selected');

        // 이미 타이머가 동작 중이라면 중지하고 다시 시작
        if (timer !== -1) {
            togglePlay();
            togglePlay();
        }
    }

    // 재생 / 일시정지 함수
    function togglePlay() {
        if (timer !== -1) {
            clearInterval(timer);
            timer = -1;
            remotePauseButton.innerText = '▶';
        } else {
            timer = setInterval(() => {
                selectImage(imageIndex + 1);
            }, 3000);
            remotePauseButton.innerText = '||';
        }
    }

    // 이벤트 핸들러 추가
    for (var i = 0; i < imageCount; i++) {
        const index = i;
        remoteContainer.children[i].addEventListener('click', function () {
            selectImage(index);
        });
    }
    remotePauseButton.addEventListener('click', togglePlay);

    // 초기 이미지 선택 및 타이머 시작
    selectImage(0);
    togglePlay();
}


window.addEventListener('load', function () {
    // 스와이퍼 컨테이너 요소 배열
    var swipers = document.querySelectorAll('.main-swiper');
    for (var i = 0; i < swipers.length; i++) {
        swiperRun(swipers[i]);
    }
});
