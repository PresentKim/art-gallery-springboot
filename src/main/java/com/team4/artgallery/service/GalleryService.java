package com.team4.artgallery.service;

import com.team4.artgallery.dao.IGalleryDao;
import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GalleryService {

    @Delegate
    private final IGalleryDao galleryDao;

    private final MultipartFileService fileService;

    /**
     * 갤러리 목록을 가져옵니다.
     *
     * @param page   페이지 번호
     * @param search 검색어  (검색어가 비어있으면 전체 갤러리 목록을 가져옵니다)
     * @return 갤러리 목록과 페이지네이션 정보
     */
    public Pagination.Pair<GalleryDto> getOrSearchGalleries(int page, String search) {
        // 검색어가 비어있을 경우 전체 갤러리 목록을 가져옵니다.
        if (search == null || search.isEmpty()) {
            Pagination pagination = new Pagination()
                    .setCurrentPage(page)
                    .setItemCount(countGalleries())
                    .setUrlTemplate("/gallery?page=%d");

            return pagination.pair(getGalleries(pagination));
        }

        // 검색 조건이 있을 경우 검색 결과를 가져옵니다.
        Pagination pagination = new Pagination()
                .setCurrentPage(page)
                .setItemCount(countSearchGalleries(search))
                .setUrlTemplate("/gallery?page=%d&search=" + search);
        return pagination.pair(searchGalleries(search, pagination));
    }


    /**
     * 갤러리 이미지를 저장하고 GalleryDto 객체에 이미지 경로를 저장합니다.
     *
     * @param file       이미지 파일
     * @param galleryDto 갤러리 정보
     */
    public boolean saveImage(MultipartFile file, GalleryDto galleryDto) {
        // 이미지 파일을 저장하고 저장된 파일 이름을 GalleryDto 객체에 저장합니다.
        MultipartFileService.FileNamePair pair = fileService.saveFile(file, "/static/image/gallery");
        if (pair != null) {
            galleryDto.setImage(pair.fileName());
            galleryDto.setSavefilename(pair.saveFileName());
            return true;
        }

        return false;
    }

}
