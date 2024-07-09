function swiperRun(swiper) {
    // 이미지 컨테이너 요소와 이미지 요소 배열
    const imageContainer = swiper.querySelectorAll('.main-swiper_image-container')[0];
    const imageCount = imageContainer.children.length;

    // 리모콘 이미지 선택 버튼 생성
    const remoteContainer = swiper.querySelectorAll('.main-swiper_remote-container')[0];
    for (let i = 0; i < imageCount; i++) {
        remoteContainer.appendChild(document.createElement('div'));
    }

    // 리모콘 재생/일시정지 버튼 생성
    const remotePauseButton = document.createElement('div');
    remotePauseButton.classList.add('pause-button');
    remoteContainer.appendChild(remotePauseButton);

    // 이미지 선택 함수
    let imageIndex = 0;
    let timer = -1;

    function selectImage(newIndex) {
        // 이미지 인덱스 계산 (0 ~ imageCount - 1)
        imageIndex = newIndex % imageCount;

        // 이미지 이동 처리
        imageContainer.style.left = -imageIndex * 100 + '%';

        // 버튼 선택 상태 변경
        for (let i = 0; i < imageCount; i++) {
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
    for (let i = 0; i < imageCount; i++) {
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

function loadRecentNotices(count) {
    function noticeDataToElement(notice) {
        const $title = document.createElement('a');
        $title.href = `/notice/${notice.nseq}`;
        $title.innerText = notice.content;

        const $titleWrapper = document.createElement('div');
        $titleWrapper.classList.add('col_col_title');
        $titleWrapper.appendChild($title);

        const $date = document.createElement('div');
        $date.innerText = new Date(notice.writedate).toLocaleDateString();
        $date.classList.add('col_col_date');

        const $innerRow = document.createElement('li');
        $innerRow.classList.add('main_notice_inner_row');
        $innerRow.appendChild($titleWrapper);
        $innerRow.appendChild($date);

        return $innerRow;
    }

    axios({
        url: `/notice/recent?count=${count}`,
        method: 'GET',
        headers: {'content-type': 'application/x-www-form-urlencoded'}
    }).then(({data}) => {
        // 소식지 목록 요소를 가져와 초기화
        const $noticeList = document.getElementById('notice-list');
        $noticeList.innerHTML = '';

        // 소식지 데이터를 소식지 목록 요소에 추가
        for (let i = 0; i < count; ++i) {
            $noticeList.appendChild(noticeDataToElement(data[i]));
        }
    }).catch(console.error);
}

function loadRandomArtworks(count) {
    function artworkDataToElement(artwork) {
        const $image = document.createElement('img');
        $image.src = artwork.imageSrc;
        $image.alt = artwork.name;

        const $imageWrapper = document.createElement('a');
        $imageWrapper.classList.add('imagelist');
        $imageWrapper.href = `/artwork/${artwork.aseq}`;
        $imageWrapper.target = '_self';
        $imageWrapper.appendChild($image);
        return $imageWrapper;
    }

    axios({
        url: `/artwork/random?count=${count}`,
        method: 'GET',
        headers: {'content-type': 'application/x-www-form-urlencoded'}
    }).then(({data}) => {
        // 예술품 컨테이너 4개를 가져와 초기화
        const artworkContainers = [
            document.getElementById('container1'),
            document.getElementById('container2'),
            document.getElementById('container3'),
            document.getElementById('container4')
        ];
        for (const $container of artworkContainers) {
            $container.innerHTML = '';
        }

        // 예술품 데이터를 각 컨테이너에 골고루 배치
        for (let i = 0; i < count; ++i) {
            artworkContainers[i % 4].appendChild(artworkDataToElement(data[i]));
        }
    }).catch(console.error);
}

window.addEventListener('load', function () {
    // 스와이퍼 컨테이너 요소 배열
    const swipers = document.querySelectorAll('.main-swiper');
    for (let i = 0; i < swipers.length; i++) {
        swiperRun(swipers[i]);
    }

    // 최신 소식지 목록 불러오기
    const NOTICE_COUNT = 5;
    loadRecentNotices(NOTICE_COUNT);

    // 랜덤 예술품 목록 불러오기
    const ARTWORK_COUNT = 24;
    loadRandomArtworks(ARTWORK_COUNT);
});
