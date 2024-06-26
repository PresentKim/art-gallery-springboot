package com.team4.artgallery.dao;

import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.util.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IGalleryDao {

    /* ========== CREATE =========== */

    /**
     * 갤러리 정보를 추가합니다.
     *
     * @param galleryDto 갤러리 정보
     * @return 추가된 행의 수
     */
    int createGallery(GalleryDto galleryDto);


    /* ========== READ =========== */

    /**
     * 갤러리 정보를 가져옵니다.
     *
     * @param gseq 갤러리 번호
     * @return 갤러리 정보
     */
    GalleryDto getGallery(int gseq);

    /**
     * 검색된 갤러리 목록을 가져옵니다.
     *
     * @param search     검색어
     * @param pagination 페이지네이션 정보
     * @return 검색된 갤러리 목록
     */
    List<GalleryDto> getGalleries(@Param("search") String search, @Param("pagination") Pagination pagination);

    /**
     * 검색된 갤러리 개수를 가져옵니다.
     *
     * @param search 검색아
     * @return 검색된 갤러리 개수
     */
    int countGalleries(@Param("search") String search);


    /* ========== UPDATE =========== */

    /**
     * 갤러리 정보를 수정합니다.
     *
     * @param galleryDto 수정할 갤러리 정보
     * @return 수정된 행의 수
     */
    int updateGallery(GalleryDto galleryDto);

    /**
     * 갤러리 조회수를 증가시킵니다.
     *
     * @param gseq 갤러리 번호
     * @return 수정된 행의 수
     */
    int increaseReadCount(int gseq);


    /* ========== DELETE =========== */

    /**
     * 갤러리 정보를 삭제합니다.
     *
     * @param gseq 갤러리 번호
     * @return 삭제된 행의 수
     */
    int deleteGallery(int gseq);

    /**
     * 여러 갤러리 정보를 삭제합니다.
     *
     * @param gseqList 갤러리 번호 목록
     * @return 삭제된 행의 수
     */
    int deleteGalleries(List<Integer> gseqList);

}
