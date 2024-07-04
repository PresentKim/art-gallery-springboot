package com.team4.artgallery.service;

import com.team4.artgallery.controller.exception.FileException;
import com.team4.artgallery.dao.IGalleryDao;
import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.service.helper.MultipartFileService;
import com.team4.artgallery.service.helper.SessionProvider;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GalleryService {

    @Delegate
    private final IGalleryDao galleryDao;

    private final MultipartFileService fileService;

    private final SessionProvider sessionProvider;

    private String hashGseq(int gseq) {
        return "galleryHash" + gseq;
    }

    /**
     * 갤러리에 대한 조회 기록이 세션에 저장되어 있는지 확인합니다.
     *
     * @param gseq 갤러리 번호 (gallery sequence)
     * @return 조회 기록이세션에 저장되어 있으면 true, 그렇지 않으면 false
     */
    public boolean checkReadStatus(int gseq) {
        return sessionProvider.getSession().getAttribute(hashGseq(gseq)) != null;
    }

    /**
     * 갤러리를 읽은 것으로 처리합니다.
     *
     * @param gseq 갤러리 번호 (gallery sequence)
     */
    public void markAsRead(int gseq) {
        // 갤러리 정보를 가져올 수 없는 경우 무시
        if (galleryDao.getGallery(gseq) == null) {
            return;
        }

        // 갤러리를 읽은 기록이 있는 경우 무시
        if (checkReadStatus(gseq)) {
            return;
        }

        // 갤러리의 조회수를 증가시키고, 갤러리를 읽은 것으로 처리
        galleryDao.increaseReadCount(gseq);
        sessionProvider.getSession().setAttribute(hashGseq(gseq), true);
    }

    /**
     * 갤러리 이미지를 저장하고 GalleryDto 객체에 이미지 경로를 저장합니다.
     *
     * @param file       이미지 파일
     * @param galleryDto 갤러리 정보
     * @throws FileException 이미지 저장에 실패한 경우 예외 발생
     */
    public void saveImage(MultipartFile file, GalleryDto galleryDto) throws FileException {
        // 이미지 파일을 저장하고 저장된 파일 이름을 GalleryDto 객체에 저장합니다.
        MultipartFileService.FileNamePair pair = fileService.saveFile(file, "/static/image/gallery");

        galleryDto.setImage(pair.fileName());
        galleryDto.setSavefilename(pair.saveFileName());
    }

}
