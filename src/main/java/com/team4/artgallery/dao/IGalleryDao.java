package com.team4.artgallery.dao;

import com.team4.artgallery.aspect.annotation.QueryApplied;
import com.team4.artgallery.controller.exception.NotFoundException;
import com.team4.artgallery.controller.exception.SqlException;
import com.team4.artgallery.dto.GalleryDto;
import com.team4.artgallery.dto.filter.KeywordFilter;
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
     * @throws SqlException 추가된 행의 수가 0인 경우 예외 발생 ({@link QueryApplied} 참조)
     */
    @QueryApplied("갤러리 정보를 추가하는 중 오류가 발생했습니다.")
    int createGallery(GalleryDto galleryDto) throws SqlException;


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
     * @param filter     검색 필터
     * @param pagination 페이지네이션 정보
     * @return 검색된 갤러리 목록
     */
    List<GalleryDto> getGalleries(@Param("filter") KeywordFilter filter, @Param("pagination") Pagination pagination);

    /**
     * 검색된 갤러리 개수를 가져옵니다.
     *
     * @param filter 검색 필터
     * @return 검색된 갤러리 개수
     */
    int countGalleries(@Param("filter") KeywordFilter filter);


    /* ========== UPDATE =========== */

    /**
     * 갤러리 정보를 수정합니다.
     *
     * @param galleryDto 수정할 갤러리 정보
     * @return 수정된 행의 수
     * @throws SqlException 수정된 행의 수가 0인 경우 예외 발생 ({@link QueryApplied} 참조)
     */
    @QueryApplied("갤러리 정보를 수정하는 중 오류가 발생했습니다.")
    int updateGallery(GalleryDto galleryDto) throws SqlException;

    /**
     * 갤러리 조회수를 증가시킵니다.
     *
     * @param gseq 갤러리 번호
     * @return 수정된 행의 수
     * @throws NotFoundException 수정된 행의 수가 0인 경우 예외 발생 ({@link QueryApplied} 참조)
     */
    @QueryApplied(value = "갤러리 정보를 찾을 수 없습니다.", exceptionClass = NotFoundException.class)
    int increaseReadCount(int gseq) throws NotFoundException;


    /* ========== DELETE =========== */

    /**
     * 갤러리 정보를 삭제합니다.
     *
     * @param gseq 갤러리 번호
     * @return 삭제된 행의 수
     * @throws NotFoundException 삭제된 행의 수가 0인 경우 예외 발생 ({@link QueryApplied} 참조)
     */
    @QueryApplied(value = "갤러리 정보를 찾을 수 없습니다.", exceptionClass = NotFoundException.class)
    int deleteGallery(int gseq) throws NotFoundException;

    /**
     * 여러 갤러리 정보를 삭제합니다.
     *
     * @param gseqList 갤러리 번호 목록
     * @return 삭제된 행의 수
     * @throws NotFoundException 삭제된 행의 수가 0인 경우 예외 발생 ({@link QueryApplied} 참조)
     */
    @QueryApplied(value = "갤러리 정보를 찾을 수 없습니다.", exceptionClass = NotFoundException.class)
    int deleteGalleries(List<Integer> gseqList) throws NotFoundException;

}
