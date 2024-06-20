package com.team4.artgallery.service;

import com.team4.artgallery.dao.IArtworkDao;
import com.team4.artgallery.dto.ArtworkDto;
import com.team4.artgallery.util.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ArtworkService {

    @Delegate
    private final IArtworkDao artworkDao;

    /**
     * 작품 목록을 가져옵니다.
     *
     * @param page   페이지 번호
     * @param filter 검색 조건 (검색 조건이 비어있으면 전체 작품 목록을 가져옵니다)
     * @return 작품 목록과 페이지네이션 정보
     */
    public Pagination.Pair<ArtworkDto> getOrSearchArtworks(int page, IArtworkDao.ArtworkFilter filter) {
        List<ArtworkDto> artworkList;
        Pagination pagination;
        if (filter.isEmpty()) {
            pagination = new Pagination()
                    .setCurrentPage(page)
                    .setItemCount(getAllCount())
                    .setUrlTemplate("/artwork?page=%d");
            artworkList = getArtworks(pagination);
        } else {
            pagination = new Pagination()
                    .setCurrentPage(page)
                    .setItemCount(getSearchCount(filter))
                    .setUrlTemplate("/artwork?page=%d&" + filter.toUrlParam(false));
            artworkList = searchArtworks(filter, pagination);
        }

        return new Pagination.Pair<>(pagination, artworkList);
    }
}
