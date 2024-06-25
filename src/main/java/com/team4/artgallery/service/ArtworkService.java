package com.team4.artgallery.service;

import com.team4.artgallery.dao.IArtworkDao;
import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ArtworkService {

    @Delegate
    private final IArtworkDao artworkDao;

    private final MultipartFileService fileService;

    /**
     * 예술품 목록을 가져옵니다.
     *
     * @param page   페이지 번호
     * @param filter 검색 조건 (검색 조건이 비어있으면 전체 예술품 목록을 가져옵니다)
     * @param path   페이지 경로
     * @return 예술품 목록과 페이지네이션 정보
     */
    public Pagination.Pair<ArtworkDto> getOrSearchArtworks(int page, IArtworkDao.Filter filter, String path) {
        // 검색 조건이 없을 경우 전체 예술품 목록을 가져옵니다.
        if (filter.isEmpty()) {
            Pagination pagination = new Pagination()
                    .setCurrentPage(page)
                    .setItemCount(countArtworks())
                    .setUrlTemplate("/" + path + "?page=%d");

            return pagination.pair(getArtworks(pagination));
        }

        // 검색 조건이 있을 경우 검색 결과를 가져옵니다.
        Pagination pagination = new Pagination()
                .setCurrentPage(page)
                .setItemCount(countSearchArtworks(filter))
                .setUrlTemplate("/" + path + "?page=%d&" + filter.toUrlParam(false));
        return pagination.pair(searchArtworks(filter, pagination));
    }

    /**
     * 예술품 이미지를 저장하고 ArtworkDto 객체에 이미지 경로를 저장합니다.
     *
     * @param file       이미지 파일
     * @param artworkDto 예술품 정보
     */
    public boolean saveImage(MultipartFile file, ArtworkDto artworkDto) {
        // 이미지 파일을 저장하고 저장된 파일 이름을 ArtworkDto 객체에 저장합니다.
        MultipartFileService.FileNamePair pair = fileService.saveFile(file, "/static/image/artwork");
        if (pair != null) {
            artworkDto.setImage(pair.fileName());
            artworkDto.setSavefilename(pair.saveFileName());
            return true;
        }

        return false;
    }
}
